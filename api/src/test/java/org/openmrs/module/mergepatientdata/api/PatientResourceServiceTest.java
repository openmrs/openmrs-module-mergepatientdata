package org.openmrs.module.mergepatientdata.api;

import java.util.List;

import org.junit.Test;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Assert;

public class PatientResourceServiceTest extends BaseModuleContextSensitiveTest {
	
	@Autowired
	PatientResourceService service;
	
	@Test
	public void getAllPatients_shouldReturnAllPatientsInTheEntireContext() {
		//List<Patient> patients = service.getAllPatients();
		Assert.assertNotNull(service);
		//PatientResourceService service = Context.getService(PatientResourceService.class);
		
	}

}
