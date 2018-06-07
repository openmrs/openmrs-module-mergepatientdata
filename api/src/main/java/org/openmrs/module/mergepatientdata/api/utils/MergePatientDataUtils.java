package org.openmrs.module.mergepatientdata.api.utils;

import java.util.List;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.module.mergepatientdata.MergePatientDataConctants;
import org.openmrs.module.mergepatientdata.api.model.config.ClassConfiguration;
import org.openmrs.module.mergepatientdata.api.model.config.MPDConfiguration;
import org.openmrs.module.mergepatientdata.api.model.config.MethodConfiguration;
import org.openmrs.module.mergepatientdata.resource.MergeAbleResource;
import org.openmrs.module.mergepatientdata.sync.MPDClient;
import org.openmrs.module.mergepatientdata.sync.MPDClientClassBuilder;
import org.openmrs.module.mergepatientdata.sync.MergeAbleBatchRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class MergePatientDataUtils {
	
	private static MPDClientClassBuilder mpdHelper;
	
	public static List<? extends MergeAbleResource> getRequiredTypesToMerge(MPDConfiguration configuration, String method) {
		
		List<ClassConfiguration> clazzes;
		List requiredResources = null;
		
		switch (method) {
			case MergePatientDataConctants.EXPORT_GENERAL_NAME:
				MethodConfiguration exportConfig = configuration.getExporting();
				clazzes = exportConfig.getClasses();
				for (ClassConfiguration clazz : clazzes) {
					System.out.println("class name : " + clazz.getClassTitle());
					Class<? extends MergeAbleResource> resourceType = getMPDTypeFromOpenmrsClassName(clazz.getClassTitle());
					if (resourceType != null) {
						requiredResources.add(resourceType);
					}
				}
			case MergePatientDataConctants.IMPORT_GENERAL_NAME:
				MethodConfiguration importConfig = configuration.getImporting();
				clazzes = importConfig.getClasses();
				for (ClassConfiguration clazz : clazzes) {
					Class<? extends MergeAbleResource> resourceType = getMPDTypeFromOpenmrsClassName(clazz.getClassTitle());
					if (resourceType != null) {
						requiredResources.add(resourceType);
					}
				}
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
	
	private static Class<? extends MergeAbleResource> getMPDTypeFromOpenmrsClassName(String openmrsClassName) {
		mpdHelper = new MPDClientClassBuilder();
		MPDClient client = mpdHelper.getClient();
		System.out.println(client);
		List<MergeAbleResource> supportedClasses = client.getSupportedClasses();
		
		for (MergeAbleResource object : supportedClasses) {
			String objectName = object.getClass().getSimpleName();
			if (objectName.equals(openmrsClassName)) {
				return object.getClass();
			} else {
				continue;
			}
		}
		
		return null;
	}
	
	private MergePatientDataUtils() {
	}
	
}
