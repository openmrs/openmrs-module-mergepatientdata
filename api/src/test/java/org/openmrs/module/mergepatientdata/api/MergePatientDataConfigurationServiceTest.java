package org.openmrs.module.mergepatientdata.api;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataConfigurationServiceImpl;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class MergePatientDataConfigurationServiceTest extends BaseModuleContextSensitiveTest {
	
	MergePatientDataConfigurationServiceImpl configService;
	
	@Before
	public void setup() {
		configService = new MergePatientDataConfigurationServiceImpl();
	}
	
	@Test
	public void getCustomConfigFilePath_shouldReturnAbsoluteCustomFilePath() {
		configService.getCustomConfigFilePath();
	}

}