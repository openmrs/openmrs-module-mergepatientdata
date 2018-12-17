package org.openmrs.module.mergepatientdata.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.Encounter;
import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataConfigurationServiceImpl;
import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataImportServiceImpl;
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataTestUtils;
import org.openmrs.module.mergepatientdata.enums.Operation;
import org.openmrs.module.mergepatientdata.resource.Patient;
import org.openmrs.module.mergepatientdata.sync.MPDClient;
import org.springframework.beans.factory.annotation.Autowired;

public class MergePatientDataImportServiceTest extends MergePatientDataTestUtils {
	
	@Autowired
	public MPDClient client;
	
	public List<Class> typesToImport;
	
	public MergePatientDataImportService importService;
	
	public PaginatedAuditMessage auditor;
	
	public MergePatientDataConfigurationService configService;
	
	@Before
	public void setup() {
		importService = new MergePatientDataImportServiceImpl();
		typesToImport = new ArrayList<>();
		typesToImport.add(Patient.class);
		auditor = new PaginatedAuditMessage();
		auditor.setResources(new ArrayList<>());
		auditor.setFailureDetails(new ArrayList<>());
		auditor.setOperation(Operation.EXPORT);
		configService = new MergePatientDataConfigurationServiceImpl();
		
	}
	
	@Test
	public void importMPD_shouldSuccessfullyImportPatients() {
		// get file to import data from 
		File zippedDataFile = client.exportData(configService.getMPDConfiguration());
		// purge Samuel from db
		Context.getPatientService().purgePatient(sam);
		// import the data again
		importService.importMPD(typesToImport, zippedDataFile, auditor);
		Assert.assertNotNull(Context.getPatientService().getPatientByUuid(sam.getUuid()));
	}
	
	@Ignore("Untill update for patientId in the Encounter model after merging Patients to a new DB is made")
	@Test
	public void importMPD_shouldImportEncounter() {
		typesToImport.add(org.openmrs.module.mergepatientdata.resource.Encounter.class);
		Encounter enc = super.buildEncounter();
		enc.setPatient(sam);
		Context.getEncounterService().saveEncounter(enc);
		
		File zippedDataFile = client.exportData(configService.getMPDConfiguration());
		Context.getEncounterService().purgeEncounter(enc);
		Context.getPatientService().purgePatient(sam);
		// import the data again
		importService.importMPD(typesToImport, zippedDataFile, auditor);
		
		Assert.assertEquals(1, Context.getEncounterService().getEncountersByPatient(sam).size());
	}
	
}
