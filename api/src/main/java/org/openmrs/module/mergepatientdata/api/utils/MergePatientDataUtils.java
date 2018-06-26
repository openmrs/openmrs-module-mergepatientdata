package org.openmrs.module.mergepatientdata.api.utils;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.MergePatientDataConstants;
import org.openmrs.module.mergepatientdata.Resource;
import org.openmrs.module.mergepatientdata.api.PatientResourceService;
import org.openmrs.module.mergepatientdata.api.exceptions.MPDException;
import org.openmrs.module.mergepatientdata.api.impl.PatientResourceServiceImpl;
import org.openmrs.module.mergepatientdata.api.model.config.ClassConfiguration;
import org.openmrs.module.mergepatientdata.api.model.config.MPDConfiguration;
import org.openmrs.module.mergepatientdata.api.model.config.MethodConfiguration;
import org.openmrs.module.mergepatientdata.enums.MergeAbleDataCategory;
import org.openmrs.module.mergepatientdata.resource.MergeAbleResource;
import org.openmrs.module.mergepatientdata.resource.Patient;
import org.openmrs.module.mergepatientdata.sync.MPDClient;
import org.openmrs.module.mergepatientdata.sync.MergeAbleBatchRepo;
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
	public static List<Class> getRequiredTypesToMerge(MPDConfiguration configuration, String method) {
		
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
							} else {
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
	public static String getClassName(List<? extends Object> dataList) {
		Class type = null;
		
		if (dataList != null && dataList.size() > 0) {
			type = dataList.get(0).getClass();
		} else {
			log.warn("openmrsData looks empty :{}", dataList);
			//TODO If its empty, It means that Particular resource has no data in DB
		}
		if (type != null) {
			log.info("Getting class name :{}", type.getSimpleName());
			return type.getSimpleName();
		}
		
		log.warn("Failed to get Class Name");
		return null;
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
	public static void mergeResourceToOpenmrsDataBase(MergeAbleBatchRepo mergeableData, MergeAbleDataCategory resourceType,
	        List<Class> resourceTypesToImport) {
		if (resourceType == MergeAbleDataCategory.PATIENT) {
			//Check whether its really required
			if (ObjectUtils.typeRequired(Patient.class, resourceTypesToImport)) {
				List<Resource> patients =  mergeableData.get(resourceType);
				for (Resource patient : patients) {
					Patient pat = (Patient) patient;
					System.out.println("Patient value " + pat.getPerson().getGender());
				}
				//new PatientResourceServiceImpl().savePatients(patients);
			}
		}
		//Cater for other Resources here..
	}
	
}
