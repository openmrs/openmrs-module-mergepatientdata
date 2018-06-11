package org.openmrs.module.mergepatientdata.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.PatientIdentifier;
import org.openmrs.PersonAddress;
import org.openmrs.PersonName;
import org.openmrs.module.mergepatientdata.api.utils.ObjectUtils;

public class Patient implements MergeAbleResource {
	
	private String uuid;
	
	private Person person;
	
	private List<Identifier> identifiers = new ArrayList<Identifier>();
	
	public Patient() {
		
	}
	
	public Patient(org.openmrs.Patient openmrsPatient) {
		this.uuid = openmrsPatient.getUuid();
		this.person = new Person(openmrsPatient.getPerson());
		this.identifiers = (List<Identifier>) ObjectUtils.getMPDObject(openmrsPatient.getIdentifiers());
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.Patient patient = new org.openmrs.Patient();
        patient.setUuid(uuid);

        Set<PatientIdentifier> patientIdentifierList = new TreeSet<>();
        for (Identifier identifier : identifiers) {
            patientIdentifierList.add((PatientIdentifier) identifier.getOpenMrsObject());
        }
        patient.setIdentifiers(patientIdentifierList);
        patient.setGender(person.getGender());
        patient.setBirthdate(person.getBirthdate());
        patient.setDead(person.getDead());
        patient.setDeathDate(person.getDeathDate());
        patient.setCauseOfDeath(person.getCauseOfDeath());

        Set<PersonName> personNameSet = new TreeSet<>();
        PersonName preferredName = null;
        if (person.getPreferredName() != null) {
            preferredName = (PersonName) person.getPreferredName().getOpenMrsObject();
        }
        for (org.openmrs.module.mergepatientdata.resource.PersonName name : person.getNames()) {
            PersonName openmrsName = (PersonName)name.getOpenMrsObject();
            if (preferredName != null && preferredName.equalsContent(openmrsName)) {
                openmrsName.setPreferred(true);
            }
            personNameSet.add(openmrsName);
        }
        patient.setNames(personNameSet);

        Set<PersonAddress> personAddressesSet = new TreeSet<>();
        PersonAddress preferredAddress = null;
        if (person.getPreferredAddress() != null) {
            preferredAddress = (PersonAddress) person.getPreferredAddress().getOpenMrsObject();
        }
        for (org.openmrs.module.mergepatientdata.resource.PersonAddress address : person.getAddresses()) {
            PersonAddress openmrsAddress = (PersonAddress) address.getOpenMrsObject();
            if (preferredAddress != null && preferredAddress.equalsContent(openmrsAddress)) {
                openmrsAddress.setPreferred(true);
            }
            personAddressesSet.add(openmrsAddress);
        }
        patient.setAddresses(personAddressesSet);

        patient.setVoided(person.getVoided());
        patient.setDeathdateEstimated(person.getDeathdateEstimated());
        patient.setBirthtime(person.getBirthtime());

        return patient;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public List<Identifier> getIdentifiers() {
		return identifiers;
	}
	
	public void setIdentifiers(List<Identifier> identifiers) {
		this.identifiers = identifiers;
	}
	
	@Override
	public String toString() {
		return "PatientUuid#" + this.getUuid() + " , PersonGender#" + person.getGender();
	}
	
}
