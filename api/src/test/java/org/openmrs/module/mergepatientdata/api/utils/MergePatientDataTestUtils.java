package org.openmrs.module.mergepatientdata.api.utils;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PersonAddress;
import org.openmrs.PersonName;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Contains common methods used when making tests.
 */
public abstract class MergePatientDataTestUtils extends BaseModuleContextSensitiveTest {
	
	public org.openmrs.Patient sam;
	
	@Before
	public void init() {
		sam = createBasicPatient("Samuel", "Tandy", "Samuels", "810");
		sam.setUuid("AAAAIIIMAAA90812");
		Context.getPatientService().savePatient(sam);
		
	}
	
	/**
	 * Used for creating Patients with basic data
	 * 
	 * @param givenName
	 * @param middleName
	 * @param familyName
	 * @param identifierString
	 * @return
	 */
	public Patient createBasicPatient(String givenName, String middleName, String familyName, String identifierString) {
		Patient patient = new Patient();
		PersonName pName = new PersonName();
		pName.setGivenName(givenName);
		pName.setMiddleName(middleName);
		pName.setFamilyName(familyName);
		
		patient.addName(pName);
		
		PersonAddress address = new PersonAddress();
		address.setAddress1("Some Address");
		
		patient.addAddress(address);
		patient.setBirthdate(new Date());
		patient.setGender("male");
		
		PatientIdentifier identifier = new PatientIdentifier();
		identifier.setIdentifier(identifierString);
		identifier.setLocation(new Location(1));
		identifier.setPreferred(true);
		identifier.setIdentifierType(Context.getPatientService().getAllPatientIdentifierTypes(false).get(0));
		
		Set<PatientIdentifier> ids = new TreeSet<>();
		ids.add(identifier);
		patient.setIdentifiers(ids);
		return patient;
	}
	
	/**
	 * Used for creating basic Encounter
	 * 
	 * @return
	 */
	public org.openmrs.Encounter buildEncounter() {
		org.openmrs.Encounter enc = new org.openmrs.Encounter();
		enc.setLocation(Context.getLocationService().getLocation(1));
		enc.setEncounterType(Context.getEncounterService().getEncounterType(1));
		enc.setEncounterDatetime(new Date());
		enc.addProvider(Context.getEncounterService().getEncounterRole(1), Context.getProviderService().getProvider(1));
		return enc;
	}
	
}
