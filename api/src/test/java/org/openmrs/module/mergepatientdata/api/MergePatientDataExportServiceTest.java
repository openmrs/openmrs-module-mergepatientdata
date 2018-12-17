package org.openmrs.module.mergepatientdata.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataConfigurationServiceImpl;
import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataExportServiceImpl;
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataTestUtils;
import org.openmrs.module.mergepatientdata.enums.Operation;
import org.openmrs.module.mergepatientdata.resource.Encounter;
import org.openmrs.module.mergepatientdata.resource.Patient;
import org.openmrs.module.mergepatientdata.sync.MPDClient;
import org.springframework.beans.factory.annotation.Autowired;

public class MergePatientDataExportServiceTest extends MergePatientDataTestUtils {
	
	List<Class> typesToExport;
	
	MergePatientDataExportService exportService;
	
	PaginatedAuditMessage auditor;
	
	@Autowired
	MPDClient client;
	
	MergePatientDataConfigurationService configService;
	
	@Before
	public void setup() {
		auditor = new PaginatedAuditMessage();
		auditor.setResources(new ArrayList<>());
		auditor.setFailureDetails(new ArrayList<>());
		auditor.setOperation(Operation.EXPORT);
		typesToExport = new ArrayList<>();
		typesToExport.add(Patient.class);
		exportService = new MergePatientDataExportServiceImpl();
		configService = new MergePatientDataConfigurationServiceImpl();
		
	}
	
	@Test
	public void exportMergeAblePatientData_shouldExportPatientDataToZippedFile() {
		File zippedMpdData = exportService.exportMergeAblePatientData(typesToExport, auditor, "Test_Server");
		Assert.assertTrue(zippedMpdData.isFile());
		// purge Samuel
		Context.getPatientService().purgePatient(sam);
		Assert.assertNull(Context.getPatientService().getPatientByUuid(sam.getUuid()));
		Assert.assertEquals(5, (Object) auditor.getResourceCount().get("Patient"));
		client.importData(configService.getMPDConfiguration(), zippedMpdData);
		// prove that Samuel was initially exported
		Assert.assertNotNull(Context.getPatientService().getPatientByUuid(sam.getUuid()));
		
	}
	
	@Ignore("Untill update for patientId in the Encounter model after merging Patients to a new DB is made")
	@Test
	public void exportMergeAblePatientData_shouldAlsoExportEncountersToZippedFile() throws Exception {
		typesToExport.add(Encounter.class);
		org.openmrs.Encounter enc = buildEncounter();
		enc.setPatient(sam);
		Context.getEncounterService().saveEncounter(enc);
		File zippedMpdData = exportService.exportMergeAblePatientData(typesToExport, auditor, "Test_Server");
		Assert.assertTrue(zippedMpdData.isFile());
		// purge Samuel
		Context.getEncounterService().purgeEncounter(enc);
		Context.getPatientService().purgePatient(sam);
		Assert.assertTrue(Context.getEncounterService().getEncountersByPatient(sam).isEmpty());
		Assert.assertEquals(5, (Object) auditor.getResourceCount().get("Encounter"));
		client.importData(configService.getMPDConfiguration(), zippedMpdData);
		// prove an Encounter was initially exported
		Assert.assertEquals(1, Context.getEncounterService().getEncountersByPatient(sam).size());
	}
	
	@Test()
	public void exportMergeAblePatientData_shouldPassWithNullServerId() {
		File zippedMpdData = exportService.exportMergeAblePatientData(typesToExport, auditor, null);
		Assert.assertTrue(zippedMpdData.isFile());
	}
}
