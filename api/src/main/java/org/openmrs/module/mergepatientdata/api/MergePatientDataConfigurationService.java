package org.openmrs.module.mergepatientdata.api;

import org.openmrs.module.mergepatientdata.api.model.config.MPDConfiguration;

public interface MergePatientDataConfigurationService {
	
	public void saveConfiguration(MPDConfiguration configuration);
	
	public void saveConfiguration(String jsonConfiguration);
	
	public MPDConfiguration getMPDConfiguration();
	
	//public Errors validateConfiguration()
	
}
