package org.openmrs.module.mergepatientdata.sync;

import java.io.File;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PersonAddress;
import org.openmrs.PersonName;
import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.api.MergePatientDataConfigurationService;
import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataConfigurationServiceImpl;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataTestUtils;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class MpdClientTest extends MergePatientDataTestUtils {
	
	@Autowired
	private MPDClient client;
	
	MergePatientDataConfigurationService configService;
	
	@Before
	public void setup() {
		configService = new MergePatientDataConfigurationServiceImpl();
	}
	
	@Test
	public void exportData_shouldExportDataToAZipFile() {
		File resultingEncryptedFile = client.exportData(configService.getMPDConfiguration());
		Assert.assertTrue(resultingEncryptedFile.isFile());
	}
	
	@Test
	public void importData_shouldImportDataFromZipFile() {
		File zipDataFile = client.exportData(configService.getMPDConfiguration());
		// purge Samuel
		Context.getPatientService().purgePatient(sam);
		Assert.assertNull(Context.getPatientService().getPatientByUuid(sam.getUuid()));
		client.importData(configService.getMPDConfiguration(), zipDataFile);
		Assert.assertNotNull(Context.getPatientService().getPatientByUuid(sam.getUuid()));
		
	}
	
}
