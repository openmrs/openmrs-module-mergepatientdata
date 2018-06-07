package org.openmrs.module.mergepatientdata.api.impl;

import org.openmrs.module.mergepatientdata.MergePatientDataConctants;
import org.openmrs.module.mergepatientdata.api.MergePatientDataConfigurationService;
import org.openmrs.module.mergepatientdata.api.model.config.MPDConfiguration;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataConfigurationUtils;
import org.openmrs.module.mergepatientdata.enums.ResourcePathType;

public class MergePatientDataConfigurationServiceImpl implements MergePatientDataConfigurationService {
	
	MPDConfiguration configuration;
	
	public MergePatientDataConfigurationServiceImpl() {
		String customFilePath = MergePatientDataConfigurationUtils.getCustomConfigFilePath();
		
		this.configuration = MergePatientDataConfigurationUtils.fileExits(customFilePath) ? MergePatientDataConfigurationUtils
		        .parseJsonToMPDConfig(customFilePath, ResourcePathType.ASOLUTE) : MergePatientDataConfigurationUtils
		        .parseJsonToMPDConfig(MergePatientDataConctants.DEFAULT_CONFIG_FILE_NAME, ResourcePathType.RELATIVE);
		
	}
	
	@Override
	public void saveConfiguration(MPDConfiguration configuration) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void saveConfiguration(String jsonConfiguration) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public MPDConfiguration getMPDConfiguration() {
		return configuration;
	}
	
}
