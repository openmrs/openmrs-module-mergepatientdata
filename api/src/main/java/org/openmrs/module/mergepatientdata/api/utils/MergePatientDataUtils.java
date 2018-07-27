package org.openmrs.module.mergepatientdata.api.utils;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.module.mergepatientdata.MergePatientDataConstants;
import org.openmrs.module.mergepatientdata.api.exceptions.MPDException;
import org.openmrs.module.mergepatientdata.api.impl.EncounterResourceServiceImpl;
import org.openmrs.module.mergepatientdata.api.impl.PatientResourceServiceImpl;
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.api.model.config.ClassConfiguration;
import org.openmrs.module.mergepatientdata.api.model.config.MPDConfiguration;
import org.openmrs.module.mergepatientdata.api.model.config.MethodConfiguration;
import org.openmrs.module.mergepatientdata.enums.MergeAbleDataCategory;
import org.openmrs.module.mergepatientdata.resource.Encounter;
import org.openmrs.module.mergepatientdata.resource.MergeAbleResource;
import org.openmrs.module.mergepatientdata.resource.Patient;
import org.openmrs.module.mergepatientdata.sync.MPDClient;
import org.openmrs.module.mergepatientdata.sync.MPDStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MergePatientDataUtils {
	
	private final static Logger log = LoggerFactory.getLogger(MergePatientDataUtils.class);
	
	private static MPDClient client;
	
	@Autowired
	public void setClient(MPDClient client) {
		MergePatientDataUtils.client = client;
	}
	
	/**
	 * Analyzes a configuration and returns required {@link MergeAbleResource}
	 * {@link java.lang.Class}s to sync.
	 * 
	 * @param configuration
	 * @param method. Could be export/import
	 * @return requiredResourceTypes to sync
	 */
	public static List<Class> getRequiredTypesToMerge(MPDConfiguration configuration, String method,
	        PaginatedAuditMessage auditor) {
		List<ClassConfiguration> clazzes;
		List<Class> requiredResourceTypes = new ArrayList<>();	
		if (configuration != null && method != null) {	
			switch (method) {
				case MergePatientDataConstants.EXPORT_GENERAL_NAME:
					
					MethodConfiguration exportConfig = configuration.getExporting();
					clazzes = exportConfig.getClasses();
					for (ClassConfiguration clazz : clazzes) {			
						//Check whether resource type is required/enabled
						if (clazz.isEnabled()) {
							Class resourceType = getMPDTypeFromOpenmrsClassName(clazz.getClassTitle());
							if (resourceType != null) {
								requiredResourceTypes.add(resourceType);
								auditor.getResources().add(resourceType.getSimpleName());
							} else {
								auditor.setHasErrors(true);
								auditor.getFailureDetails().add(
								    "Requested Resource '" + clazz.getClassTitle() + "' isn't supported.");
								//TODO Should handle cases were Resource in null, probably its not yet supported.
							}
						}
						
					}
					break;
				
				case MergePatientDataConstants.IMPORT_GENERAL_NAME:
					
					MethodConfiguration importConfig = configuration.getImporting();
					clazzes = importConfig.getClasses();
					for (ClassConfiguration clazz : clazzes) {
						Class resourceType = getMPDTypeFromOpenmrsClassName(clazz.getClassTitle());
						if (resourceType != null) {
							requiredResourceTypes.add(resourceType);
						}
					}
					break;
			}
			
		} else {
			log.warn("Configuration is invalid, null arguments are detected for crucial params");
		}
		return requiredResourceTypes;
	}
	
	/**
	 * @param openmrsData
	 * @return
	 */
	public static String getClassName(List<? extends Object> dataList) throws MPDException {
		Class type = null;
		if (dataList != null && dataList.size() > 0) {
			type = dataList.get(0).getClass();
			if (type != null) {
				log.info("Getting class name :{}", type.getSimpleName());
				return type.getSimpleName();
			}
		} else {
			log.warn("openmrsData looks empty :{}", dataList);
		}
		throw new MPDException("Failed to Load Resource Name. Its most probably its null in the Database" + dataList);
		
	}
	
	/**
	 * Takes a valid {@link OpenmrsObject} name and returns matching {@link MergeAbleResource} type.
	 * 
	 * @param openmrsClassName
	 * @return {@link MergeAbleResource} type name
	 */
	private static Class getMPDTypeFromOpenmrsClassName(String openmrsClassName) {
		List<MergeAbleResource> supportedClasses = client.getSupportedClasses();
		
		if (openmrsClassName != null) {
			for (MergeAbleResource object : supportedClasses) {
				String objectName = object.getClass().getSimpleName();
				if (objectName.equals(openmrsClassName)) {
					return object.getClass();
				} else {
					continue;
				}
			}
		} else {
			log.warn("openmrsClassName is null : {}", openmrsClassName);
		}
		log.warn("Failed to get matching class ");
		return null;
	}
	
	/**
	 * Merges a given ResouceType to the database.
	 * 
	 * @param resourceType
	 * @param resourceTypesToImport
	 */
	public static void mergeResourceToOpenmrsDataBase(MPDStore store, List<Class> resourceTypesToImport,
	        PaginatedAuditMessage auditor) {
		List<org.openmrs.Patient> savedPatients = null;
		for (MergeAbleDataCategory resourceCategory : store.getTypes()) {
			if (resourceCategory == MergeAbleDataCategory.PATIENT) {
				// Check whether its really required
				if (ObjectUtils.typeRequired(Patient.class, resourceTypesToImport)) {
					savedPatients = new PatientResourceServiceImpl().savePatients(store.getPatients(), auditor,
					    store.getEncounters());
				}
			}
			if (resourceCategory == MergeAbleDataCategory.ENCOUNTER) {
				if (ObjectUtils.typeRequired(Encounter.class, resourceTypesToImport)) {
					if (savedPatients == null) {
						auditor.getFailureDetails().add("Found no Patient Resource To Merge Encounter Data Against");
						return;
					}
					new EncounterResourceServiceImpl().saveEncounters(MergePatientDataUtils
					        .filterOutEncountersToMergeAgainstPatients(store.getEncounters(), savedPatients));
				}
			}
		}
	}
	
	/**
	 * Filters out {@link Encounter}s whose {@link Patient}s aren't included in Patient list
	 * 
	 * @param encounters encounters list to be filtered
	 * @param patients patients whose encounters should be returned
	 * @return filtered {@link Encounter} list
	 */
	private static List<Encounter> filterOutEncountersToMergeAgainstPatients(List<Encounter> encounters, List<org.openmrs.Patient> patients) {
		if (encounters == null || patients == null) {
			return null;
		}
		List<Encounter> qualifyingEncounters = new ArrayList<>();
		for (org.openmrs.Patient patient : patients) {
			for (Encounter candidate : encounters) {
				if (candidate.getPatient().getId() == patient.getId()) {
					// Make sure its not included yet
					if (!encounterAlreadyIncludedToList(qualifyingEncounters, candidate)) {
						qualifyingEncounters.add(candidate);
					}
				}
			}
		}
		return qualifyingEncounters;
	}
	
	/**
	 * Checks whether an Encounter is already included in provided list
	 * 
	 * @param encounters
	 * @param candidate Encounter to test against
	 * @return
	 */
	private static boolean encounterAlreadyIncludedToList(List<Encounter> encounters, Encounter candidate) {
		for (Encounter enc : encounters) {
			if (enc.getId() == candidate.getId()) {
				return true;
			}
		}
		return false;
	}
	
}
