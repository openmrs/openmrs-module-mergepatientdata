package org.openmrs.module.mergepatientdata.api.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.Location;
import org.openmrs.LocationTag;
import org.openmrs.OpenmrsObject;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAddress;
import org.openmrs.PersonName;
import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.api.PatientResourceService;
import org.openmrs.module.mergepatientdata.api.impl.PatientResourceServiceImpl;
import org.openmrs.module.mergepatientdata.resource.Identifier;
import org.openmrs.module.mergepatientdata.resource.Patient;
import org.openmrs.module.mergepatientdata.resource.Person;
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
		service = new PatientResourceServiceImpl();
	}
	
	@Test
	public void getMPDResourceObjectsFromOpenmrsResourceObjects_shouldReturnMPDPatientObjects() {
		Set<org.openmrs.Patient> openmrsPatients = new HashSet(service.getAllPatients());
		@SuppressWarnings("unchecked")
		List<Patient> patients = (List<Patient>) ObjectUtils
		        .getMPDResourceObjectsFromOpenmrsResourceObjects(openmrsPatients);
		Assert.isTrue(patients.size() == 4);
		
	}
	
	@Test
	public void convertToMPDPatient_shouldConvertOpenmrsPatientToMPD() {
		PatientIdentifierType patientIdentifierType = Context.getPatientService().getAllPatientIdentifierTypes(false).get(0);
		org.openmrs.Patient patient = new org.openmrs.Patient();
		PersonName pName = new PersonName();
		pName.setGivenName("Tom");
		pName.setMiddleName("E.");
		pName.setFamilyName("Patient");
		patient.addName(pName);
		patient.setGender("male");
		PersonAddress pAddress = new PersonAddress();
		pAddress.setAddress1("123 My street");
		pAddress.setAddress2("Apt 402");
		pAddress.setCityVillage("Anywhere city");
		pAddress.setCountry("Some Country");
		Set<PersonAddress> pAddressList = patient.getAddresses();
		pAddressList.add(pAddress);
		patient.setAddresses(pAddressList);
		patient.addAddress(pAddress);
		PatientIdentifier patientIdentifier1 = new PatientIdentifier();
		Location parentLocation = new Location();
		parentLocation.setAddress1("Mutungo");
		Location location = new Location(10);
		location.setAddress1("Kireka");
		location.addTag(new LocationTag(1));
		location.setParentLocation(parentLocation);
		patientIdentifier1.setLocation(location);
		patientIdentifier1.setIdentifier("012345678");
		patientIdentifier1.setDateCreated(new Date());
		patientIdentifier1.setIdentifierType(patientIdentifierType);
		patient.addIdentifier(patientIdentifier1);
		List<OpenmrsObject> patients = new ArrayList<>();
		patients.add(patient);
		List<Patient> pats = ObjectUtils.convertToMPDPatient(patients);
		Patient mpdPatient = pats.get(0);
		
		//Test the returned Patient
		org.openmrs.module.mergepatientdata.resource.PersonName name = mpdPatient.getPerson().getPreferredName();

		
		Person person = mpdPatient.getPerson();
		Identifier identifier = mpdPatient.getPatientIdentifier();
		Assert.isTrue(name.getGivenName().equals(pName.getGivenName()), "Names should be equal ");
		Assert.isTrue(name.getMiddleName().equals(pName.getMiddleName()), "Names should be equal ");
		Assert.isTrue(name.getFamilyName().equals(pName.getFamilyName()), "Names should be equal ");
		Assert.isTrue(person.getGender().equals(patient.getGender()), "The gender should be equal");
		Assert.isTrue(mpdPatient.getAddress().getAddress1().equals(pAddress.getAddress1()), "The Addresses should be equal");
		Assert.isTrue(mpdPatient.getAddress().getAddress2().equals(pAddress.getAddress2()), "The Addresses should be equal");
		Assert.isTrue(mpdPatient.getAddress().getCityVillage().equals(pAddress.getCityVillage()), "The Addresses should be equal");

		Assert.isTrue(identifier.getLocation().getId().equals(patient.getPatientIdentifier().getLocation().getId()));
		Assert.isTrue(identifier.getLocation().getTags().iterator().next().getId().equals(patient.getPatientIdentifier().getLocation().getTags().iterator().next().getId()));

		Assert.isTrue(identifier.getLocation().getParentLocation().getAddress1().equals(patient.getPatientIdentifier().getLocation().getParentLocation().getAddress1()));

		Assert.isTrue(identifier.getIdentifier().equals(patient.getPatientIdentifier().getIdentifier()));
			
	}
}
