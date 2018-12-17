package org.openmrs.module.mergepatientdata.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.PatientIdentifier;
import org.openmrs.PersonAddress;
import org.openmrs.PersonName;
import org.openmrs.module.mergepatientdata.api.utils.ObjectUtils;

public class Patient extends BaseOpenmrsMetadata implements MergeAbleResource {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private Person person;
	
	private org.openmrs.module.mergepatientdata.resource.PersonAddress address;
	
	private Identifier patientIdentifier;
	
	private List<Identifier> identifiers = new ArrayList<Identifier>();
	
	public Patient() {
	}
	
	@SuppressWarnings("unchecked")
	public Patient(org.openmrs.Patient openmrsPatient) {
		this.id = openmrsPatient.getId();
		this.setUuid(openmrsPatient.getUuid());
		this.setDateChanged(openmrsPatient.getDateChanged());
		this.setDateCreated(openmrsPatient.getDateCreated());
		this.person = new Person(openmrsPatient.getPerson());
		this.identifiers = (List<Identifier>) ObjectUtils.getMPDResourceObjectsFromOpenmrsResourceObjects(openmrsPatient
		        .getIdentifiers());
		this.address = new org.openmrs.module.mergepatientdata.resource.PersonAddress(openmrsPatient.getPersonAddress());
		this.patientIdentifier = new Identifier(openmrsPatient.getPatientIdentifier());
	}
	
	public Identifier getPatientIdentifier() {
		return patientIdentifier;
	}
	
	public void setPatientIdentifier(Identifier patientIdentifier) {
		this.patientIdentifier = patientIdentifier;
	}
	
	public org.openmrs.module.mergepatientdata.resource.PersonAddress getAddress() {
		return address;
	}
	
	public void setAddress(org.openmrs.module.mergepatientdata.resource.PersonAddress address) {
		this.address = address;
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.Patient patient = new org.openmrs.Patient();
		patient.setId(id);
        patient.setUuid(getUuid());
        patient.setDateChanged(getDateChanged());
        patient.setDateCreated(getDateCreated());
        Set<PatientIdentifier> patientIdentifierList = new TreeSet<>();
        for (Identifier identifier : this.identifiers) {
            patientIdentifierList.add((PatientIdentifier) identifier.getOpenMrsObject());
        }
        patient.setIdentifiers(patientIdentifierList);
        if (person != null) {
        	patient.setGender(person.getGender());
            patient.setBirthdate(person.getBirthdate());
            patient.setDead(person.getDead());
            patient.setDeathDate(person.getDeathDate());
            if (person.getCauseOfDeath() != null) {
            	org.openmrs.Concept causeOfDeath = new org.openmrs.Concept();
            	causeOfDeath.setUuid(person.getCauseOfDeath().getUuid());
            	patient.setCauseOfDeath(causeOfDeath);
            }
            Set<PersonName> personNameSet = new TreeSet<>();
            PersonName preferredName = null;
            if (person.getPreferredName() != null) {
                preferredName = (PersonName) person.getPreferredName().getOpenMrsObject();
            }
            if (person.getNames() != null) {
            	for (org.openmrs.module.mergepatientdata.resource.PersonName name : person.getNames()) {
                    PersonName openmrsName = (PersonName)name.getOpenMrsObject();
                    if (preferredName != null && preferredName.equalsContent(openmrsName)) {
                        openmrsName.setPreferred(true);
                    }
                    personNameSet.add(openmrsName);
                }
                patient.setNames(personNameSet);
            }

            Set<PersonAddress> personAddressesSet = new TreeSet<>();
            PersonAddress preferredAddress = null;
            if (person.getPreferredAddress() != null) {
                preferredAddress = (PersonAddress) person.getPreferredAddress().getOpenMrsObject();
            }
            if (person.getAddresses() != null) {
            	for (org.openmrs.module.mergepatientdata.resource.PersonAddress address : person.getAddresses()) {
                    PersonAddress openmrsAddress = (PersonAddress) address.getOpenMrsObject();
                    if (preferredAddress != null && preferredAddress.equalsContent(openmrsAddress)) {
                        openmrsAddress.setPreferred(true);
                    }
                    personAddressesSet.add(openmrsAddress);
                }
            }
            patient.setAddresses(personAddressesSet);
            patient.setVoided(person.getVoided());
            patient.setDeathdateEstimated(person.getDeathdateEstimated());
            patient.setBirthtime(person.getBirthtime());
        }
        return patient;
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
	
	@Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
}
