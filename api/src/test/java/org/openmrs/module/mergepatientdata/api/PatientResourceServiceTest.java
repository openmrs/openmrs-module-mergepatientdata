package org.openmrs.module.mergepatientdata.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.api.utils.ObjectUtils;
import org.openmrs.module.mergepatientdata.enums.Operation;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PatientResourceServiceTest extends BaseModuleContextSensitiveTest {
	
	private Patient patient;
	
	@Autowired
	PatientResourceService service;
	
	PaginatedAuditMessage auditor;
	
	@Before
	public void setup() {
		patient = new Patient();
		PersonName pName = new PersonName();
		pName.setGivenName("Samuel");
		pName.setMiddleName("Dari");
		pName.setFamilyName("Family");
		
		patient.addName(pName);
		
		PersonAddress address = new PersonAddress();
		address.setAddress1("Address");
		
		patient.addAddress(address);
		patient.setBirthdate(new Date());
		patient.setGender("male");
		
		PatientIdentifier identifier = new PatientIdentifier();
		identifier.setIdentifier("839");
		identifier.setLocation(new Location(1));
		identifier.setIdentifierType(Context.getPatientService().getAllPatientIdentifierTypes(false).get(0));
		PatientIdentifier identifier2 = new PatientIdentifier();
		identifier2.setIdentifier("890");
		identifier2.setLocation(new Location(1));
		identifier2.setIdentifierType(Context.getPatientService().getAllPatientIdentifierTypes(false).get(0));
		identifier2.setPreferred(true);
		Set<PatientIdentifier> ids = new TreeSet<>();
		ids.add(identifier);
		ids.add(identifier2);
		patient.setIdentifiers(ids);
		auditor = new PaginatedAuditMessage();
		auditor.setResources(new ArrayList<>());
		auditor.setFailureDetails(new ArrayList<>());
		auditor.setOperation(Operation.EXPORT);
	}
	
	@Test
	public void getAllPatients_shouldReturnAllPatientsInTheEntireContext() {
		List<Patient> patients = service.getAllPatients();
		Assert.assertNotNull(patients);
		
	}
	
	@Test
	public void savePatient_shouldSavePatient() {
		service.savePatient(patient, auditor);
		Assert.assertEquals(5, service.getAllPatients().size());
	}
	
	@Test
	public void savePatients_shaouldSavePatients() {
		Set<Patient> openmrsPatients = new HashSet<>();
		openmrsPatients.add(patient);
		List<org.openmrs.module.mergepatientdata.resource.Patient> patients = (List<org.openmrs.module.mergepatientdata.resource.Patient>) ObjectUtils.getMPDResourceObjectsFromOpenmrsResourceObjects( openmrsPatients);
		service.savePatients(patients, auditor, new ArrayList<>());
		Assert.assertEquals(5, Context.getPatientService().getAllPatients().size());
	}
}
