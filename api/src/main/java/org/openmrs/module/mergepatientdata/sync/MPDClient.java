package org.openmrs.module.mergepatientdata.sync;

import java.util.List;

import org.openmrs.OpenmrsObject;
import org.openmrs.module.mergepatientdata.MergePatientDataConctants;
import org.openmrs.module.mergepatientdata.api.model.config.MPDConfiguration;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataUtils;
import org.openmrs.module.mergepatientdata.resource.MergeAbleResource;
import org.springframework.stereotype.Component;

@Component("mergepatientdata.MPDClient")
public class MPDClient {
	
	private List<MergeAbleResource> supportedClasses;
	
	public void exportData(MPDConfiguration configuration) {
		MergePatientDataUtils.getRequiredTypesToMerge(configuration, MergePatientDataConctants.EXPORT_GENERAL_NAME);
		
	}
	
	public void importData() {
		
	}
	
	public List<MergeAbleResource> getSupportedClasses() {
		return supportedClasses;
	}
	
	public void setSupportedClasses(List<MergeAbleResource> supportedClasses) {
		this.supportedClasses = supportedClasses;
	}
}
