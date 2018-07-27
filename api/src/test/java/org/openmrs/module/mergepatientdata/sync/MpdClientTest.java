package org.openmrs.module.mergepatientdata.sync;

import java.io.File;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mergepatientdata.api.MergePatientDataConfigurationService;
import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataConfigurationServiceImpl;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class MpdClientTest extends BaseModuleContextSensitiveTest {
	
	@Autowired
	MPDClient client;
	
	MergePatientDataConfigurationService configService;
	
	File encryptedFile = null;
	
	@Before
	public void setup() {
		configService = new MergePatientDataConfigurationServiceImpl();
	}
	
	@Test
	public void exportData_shouldExportDataToAnEncryptedFile() {
		File resultingEncryptedFile = client.exportData(configService.getMPDConfiguration());
		Assert.assertTrue(resultingEncryptedFile.isFile());
	}
}
