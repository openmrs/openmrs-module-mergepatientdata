package org.openmrs.module.mergepatientdata.api.impl;

import java.io.File;

import org.openmrs.module.mergepatientdata.MergePatientDataConstants;
import org.openmrs.module.mergepatientdata.api.MergePatientDataConfigurationService;
import org.openmrs.module.mergepatientdata.api.exceptions.MPDException;
import org.openmrs.module.mergepatientdata.api.model.config.MPDConfiguration;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataConfigurationUtils;
import org.openmrs.module.mergepatientdata.enums.ResourcePathType;
import org.openmrs.util.OpenmrsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MergePatientDataConfigurationServiceImpl implements MergePatientDataConfigurationService {
	
	private final Logger log = LoggerFactory.getLogger(MergePatientDataConfigurationServiceImpl.class);
	
	MPDConfiguration configuration;
	
	public MergePatientDataConfigurationServiceImpl() {
		generateConfiguration();
	}
	
	@Override
	public void saveConfiguration(MPDConfiguration configuration) {
		MergePatientDataConfigurationUtils.writeSyncConfigurationToJsonFile(configuration,
		    MergePatientDataConfigurationUtils.getCustomConfigFilePath());
	}
	
	@Override
	public boolean saveConfiguration(String jsonConfiguration) {
		if (MergePatientDataConfigurationUtils.isValidJson(jsonConfiguration)) {
			try {
				MPDConfiguration customConfig = MergePatientDataConfigurationUtils
				        .parseJsonStringToMpdConfiguration(jsonConfiguration);
				MergePatientDataConfigurationUtils.writeSyncConfigurationToJsonFile(customConfig,
				    MergePatientDataConfigurationUtils.getCustomConfigFilePath());
				return true;
			}
			catch (MPDException e) {
				return false;
			}
		}
		
		return false;
	}
	
	@Override
	public MPDConfiguration getMPDConfiguration() {
		return configuration;
	}
	
	private void generateConfiguration() {
		log.info("Retrieving configuration");
		String customFilePath = MergePatientDataConfigurationUtils.getCustomConfigFilePath();
		log.info("Custom Config file path : {}", customFilePath);
		this.configuration = MergePatientDataConfigurationUtils.fileExits(customFilePath) ? MergePatientDataConfigurationUtils
		        .parseJsonToMPDConfig(customFilePath, ResourcePathType.ASOLUTE) : MergePatientDataConfigurationUtils
		        .parseJsonToMPDConfig(MergePatientDataConstants.DEFAULT_CONFIG_FILE_NAME, ResourcePathType.RELATIVE);
	}
	
}
