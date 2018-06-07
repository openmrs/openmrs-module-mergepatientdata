package org.openmrs.module.mergepatientdata.api.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.api.PatientResourceService;
import org.openmrs.module.mergepatientdata.resource.Patient;
import org.openmrs.module.mergepatientdata.sync.MPDClient;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class ObjectUtilsTest extends BaseModuleContextSensitiveTest {
	
	private final Logger log = LoggerFactory.getLogger(ObjectUtilsTest.class);
	
	PatientResourceService service; 
	
	@Before
	public void setup() {
		service = Context.getService(PatientResourceService.class);
	}
	
	@Test
	public void getMPDAObject_shouldReturnPatientObjects() {
		Set<org.openmrs.Patient> openmrsPatients = new HashSet(service.getAllPatients());
		List<Patient> patients = (List<Patient>) ObjectUtils.getMPDObject(openmrsPatients);
		Assert.isTrue(patients.size() == 4);
		
	}
	
	@Test
	public void getMPDObject_shouldReturnLocationObjects() {
		
	}
}
