package org.openmrs.module.mergepatientdata.api.impl;

import java.io.File;

import org.openmrs.module.mergepatientdata.MergePatientDataConstants;
import org.openmrs.module.mergepatientdata.api.MergePatientDataConfigurationService;
import org.openmrs.module.mergepatientdata.api.model.config.MPDConfiguration;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataConfigurationUtils;
import org.openmrs.module.mergepatientdata.enums.ResourcePathType;
import org.openmrs.util.OpenmrsUtil;

public class MergePatientDataConfigurationServiceImpl implements MergePatientDataConfigurationService {
	
	MPDConfiguration configuration;
	
	public MergePatientDataConfigurationServiceImpl() {
		String customFilePath = getCustomConfigFilePath();
		
		this.configuration = MergePatientDataConfigurationUtils.fileExits(customFilePath) ? MergePatientDataConfigurationUtils
		        .parseJsonToMPDConfig(customFilePath, ResourcePathType.ASOLUTE) : MergePatientDataConfigurationUtils
		        .parseJsonToMPDConfig(MergePatientDataConstants.DEFAULT_CONFIG_FILE_NAME, ResourcePathType.RELATIVE);
		
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
	
	public String getCustomConfigFilePath() {
		File mpdWorkingDir = OpenmrsUtil.getDirectoryInApplicationDataDirectory(MergePatientDataConstants.MPD_DIR);
		System.out.println("configDir " + mpdWorkingDir.getAbsolutePath());
		return new File(mpdWorkingDir, MergePatientDataConstants.CONFIG_FILE_NAME).getAbsolutePath();
	}
	
}
