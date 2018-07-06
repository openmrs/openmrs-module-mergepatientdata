package org.openmrs.module.mergepatientdata.api.utils;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.MergePatientDataConstants;
import org.openmrs.module.mergepatientdata.api.PatientResourceService;
import org.openmrs.module.mergepatientdata.api.exceptions.MPDException;
import org.openmrs.module.mergepatientdata.api.impl.PatientResourceServiceImpl;
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.api.model.config.ClassConfiguration;
import org.openmrs.module.mergepatientdata.api.model.config.MPDConfiguration;
import org.openmrs.module.mergepatientdata.api.model.config.MethodConfiguration;
import org.openmrs.module.mergepatientdata.enums.MergeAbleDataCategory;
import org.openmrs.module.mergepatientdata.enums.Status;
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
		List<Class> requiredResourceTypes = new ArrayList();
		
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
								    "Requested Resource '" + clazz.getClassTitle() + "' isn't supported yet!");
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
			
			if (configuration == null) {
				log.warn("configuration is null");
				//TODO whats next
			} else {
				log.warn("method is null");
				//TODO whats next
			}
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
		throw new MPDException("Failed to Load Resource Name. Could be Openmrs Database is empty");
		
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
	public static void mergeResourceToOpenmrsDataBase(MPDStore store, MergeAbleDataCategory resourceType,
	        List<Class> resourceTypesToImport, PaginatedAuditMessage auditor) {
		if (resourceType == MergeAbleDataCategory.PATIENT) {
			//Check whether its really required
			if (ObjectUtils.typeRequired(Patient.class, resourceTypesToImport)) {
				new PatientResourceServiceImpl().savePatients(store.getPatients(), auditor);
			}
		}
		//TODO :- Cater for other Resources here..
	}
	
}
