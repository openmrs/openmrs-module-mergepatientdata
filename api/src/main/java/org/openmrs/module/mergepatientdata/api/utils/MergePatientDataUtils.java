package org.openmrs.module.mergepatientdata.api.utils;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.module.mergepatientdata.MergePatientDataConstants;
import org.openmrs.module.mergepatientdata.api.model.config.ClassConfiguration;
import org.openmrs.module.mergepatientdata.api.model.config.MPDConfiguration;
import org.openmrs.module.mergepatientdata.api.model.config.MethodConfiguration;
import org.openmrs.module.mergepatientdata.resource.MergeAbleResource;
import org.openmrs.module.mergepatientdata.sync.MPDClient;
import org.openmrs.module.mergepatientdata.sync.MergeAbleBatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MergePatientDataUtils {
	
	private static MPDClient client;
	
	@Autowired
	public void setClient(MPDClient client) {
		MergePatientDataUtils.client = client;
	}

	public static List<Class> getRequiredTypesToMerge(MPDConfiguration configuration, String method) {
		
		List<ClassConfiguration> clazzes;
		List<Class> requiredResources = new ArrayList();
		
		switch (method) {
			case MergePatientDataConstants.EXPORT_GENERAL_NAME:
				MethodConfiguration exportConfig = configuration.getExporting();
				clazzes = exportConfig.getClasses();
				for (ClassConfiguration clazz : clazzes) {
					Class resourceType = getMPDTypeFromOpenmrsClassName(clazz.getClassTitle());
					if (resourceType != null) {
						requiredResources.add(resourceType);
					}
				}
				break;
			case MergePatientDataConstants.IMPORT_GENERAL_NAME:
				MethodConfiguration importConfig = configuration.getImporting();
				clazzes = importConfig.getClasses();
				for (ClassConfiguration clazz : clazzes) {
					Class resourceType = getMPDTypeFromOpenmrsClassName(clazz.getClassTitle());
					if (resourceType != null) {
						requiredResources.add(resourceType);
					}
				}
				break;
		}
		
		return requiredResources;
	}
	
	public static String getClassName(List<? extends Object> openmrsData) {
		Class type = null;
		if (openmrsData != null && openmrsData.size() > 0) {
			type = openmrsData.get(0).getClass();
		} else {
			//TODO
		}
		if (type != null) {
			return type.getSimpleName();
		}
		return null;
	}
	
	private static Class getMPDTypeFromOpenmrsClassName(String openmrsClassName) {
		List<MergeAbleResource> supportedClasses = client.getSupportedClasses();
		
		for (MergeAbleResource object : supportedClasses) {
			String objectName = object.getClass().getSimpleName();
			if (objectName.equals(openmrsClassName)) {
				return  object.getClass();
			} else {
				continue;
			}
		}
		
		return null;
	}
	
}
