package org.openmrs.module.mergepatientdata.api.utils;

import java.util.List;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.module.mergepatientdata.resource.MergeAbleResource;

public class MergePatientDataUtils {
	
	public static List<? extends MergeAbleResource> changeFromOpenmrsToMPD(List<? extends BaseOpenmrsData> openmrsData) {
		
		return null;
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
	
	private MergePatientDataUtils() {}
	
}
