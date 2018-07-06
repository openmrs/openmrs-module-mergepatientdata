package org.openmrs.module.mergepatientdata.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataExportServiceImpl;
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.enums.Operation;
import org.openmrs.module.mergepatientdata.resource.Patient;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class MergePatientDataExportServiceTest extends BaseModuleContextSensitiveTest {
	
	List<Class> typesToExport;
	
	MergePatientDataExportService exportService;
	
	PaginatedAuditMessage auditor;
	
	@Before
	public void setup() {
		auditor = new PaginatedAuditMessage();
		auditor.setResources(new ArrayList<>());
		auditor.setFailureDetails(new ArrayList<>());
		auditor.setOperation(Operation.EXPORT);
		typesToExport = new ArrayList<>();
		typesToExport.add(Patient.class);
		exportService = new MergePatientDataExportServiceImpl();
	}
	
	/**
	 * TODO : Some mocking required to add more meaning to this TestCase.
	 */
	@Test
	public void exportMergeAblePatientData_shouldExportSerializedPatientDataToDummyFile() {
		File encryptedFile = exportService.exportMergeAblePatientData(typesToExport, auditor, "Dummy_Server");
		Assert.assertTrue(encryptedFile.isFile());
	}
}
