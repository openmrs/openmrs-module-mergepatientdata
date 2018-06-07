package org.openmrs.module.mergepatientdata.sync;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mergepatientdata.api.MergePatientDataConfigurationService;
import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataConfigurationServiceImpl;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class MPDClientTest extends BaseModuleContextSensitiveTest {

	@Autowired
	public MPDClient client;
	
	MergePatientDataConfigurationService configService;
	
	
	@Before
	public void setup() {
		configService = new MergePatientDataConfigurationServiceImpl();
	}
	
	@Test
	public void export_shouldSerializePatientData() {
		client.exportData(configService.getMPDConfiguration());
	}
}
