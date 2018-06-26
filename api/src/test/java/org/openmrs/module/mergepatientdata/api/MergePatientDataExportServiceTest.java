package org.openmrs.module.mergepatientdata.api;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.Location;
import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataExportServiceImpl;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class MergePatientDataExportServiceTest extends BaseModuleContextSensitiveTest {
	
	List<Class> typesToExport;
	
	MergePatientDataExportService exportService;
	
	@Before
	public void setup() {
		typesToExport = new ArrayList<>();
		typesToExport.add(Location.class);
		exportService = new MergePatientDataExportServiceImpl();
	}
	
	/**
	 * TODO : Some mocking required to add more meaning to this TestCase.
	 */
	@Test
	public void exportMergeAblePatientData_shouldExportSerializedPatientDataToDummyFile() {
		exportService.exportMergeAblePatientData(typesToExport);
	}
}
