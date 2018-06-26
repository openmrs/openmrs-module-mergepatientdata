package org.openmrs.module.mergepatientdata.sync;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataConfigurationServiceImpl;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Assert;

public class MPDClientTest extends BaseModuleContextSensitiveTest {
	
	@Autowired
	public MPDClient client;
	public static final String pallasoUuid = "49f9c38f-b292-41f2-9e3b-e95e2a0dbfd1";
	MergePatientDataConfigurationServiceImpl configService;
	PatientService patientService;
	
	@Before
	public void setup() {
		configService = new MergePatientDataConfigurationServiceImpl();
		configService.generateConfiguration();
		patientService = Context.getPatientService();
	}
	
	/**
	 * TODO : Do some mocking to add some sense to this Testcase
	 */
	//@Test
	public void export_shouldSerializePatientData() {
		client.exportData(configService.getMPDConfiguration());
	}
	
	@Test
	public void import_shouldImportData() {
		client.importData(configService.getMPDConfiguration(), new File(
		        "C:\\Users\\Samuel34\\openmrs\\MPD\\mpd\\encrytedMergeableData.mpd"));
		List<Patient> patients = patientService.getAllPatients();
		
	}
}
