package org.openmrs.module.mergepatientdata.resource;

import java.util.Objects;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.PatientIdentifierType;

public class Identifier implements MergeAbleResource {
	
	private Integer id;
	
	private String uuid;
	
	private String identifier;
	
	private IdentifierType identifierType;
	
	private Location location;
	
	private Boolean preferred;
	
	private Boolean voided;
	
	private Patient patient;
	
	public Identifier(org.openmrs.PatientIdentifier openmrPatientIdentifier) {
		this.id = openmrPatientIdentifier.getId();
		this.uuid = openmrPatientIdentifier.getUuid();
		this.identifier = openmrPatientIdentifier.getIdentifier();
		this.identifierType = new IdentifierType(openmrPatientIdentifier.getIdentifierType());
		this.location = new Location(openmrPatientIdentifier.getLocation(), true);
		this.preferred = openmrPatientIdentifier.getPreferred();
		this.voided = openmrPatientIdentifier.getVoided();
		this.patient = createMpdIdentifierTypePatient(openmrPatientIdentifier.getPatient());
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.PatientIdentifier patientIdentifier = new org.openmrs.PatientIdentifier();
		patientIdentifier.setId(id);
		patientIdentifier.setUuid(this.uuid);
		patientIdentifier.setIdentifier(this.identifier);
		if (identifierType != null) {
			patientIdentifier.setIdentifierType((PatientIdentifierType) identifierType.getOpenMrsObject());
		}
		if (location != null) {
			patientIdentifier.setLocation((org.openmrs.Location) location.getOpenMrsObject());
		}
		patientIdentifier.setPreferred(this.preferred);
		patientIdentifier.setVoided(this.voided);
		patientIdentifier.setPatient(createOpenmrsIdentifierTypePatient(this.patient));
		return patientIdentifier;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	public IdentifierType getIdentifierType() {
		return identifierType;
	}
	
	public void setIdentifierType(IdentifierType identifierType) {
		this.identifierType = identifierType;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Boolean getPreferred() {
		return preferred;
	}
	
	public void setPreferred(Boolean preferred) {
		this.preferred = preferred;
	}
	
	public Boolean getVoided() {
		return voided;
	}
	
	public void setVoided(Boolean voided) {
		this.voided = voided;
	}
	
	public Patient getPatient() {
		return patient;
	}
	
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	private Patient createMpdIdentifierTypePatient(org.openmrs.Patient patient) {
		if (patient == null) {
			return null;
		}
		Patient pat = new Patient();
		pat.setId(patient.getId());
		pat.setUuid(patient.getUuid());
		pat.setDateCreated(patient.getDateCreated());
		return pat;
	}
	
	private org.openmrs.Patient createOpenmrsIdentifierTypePatient(Patient patient) {
		if (patient == null) {
			return null;
		}
		org.openmrs.Patient pat = new org.openmrs.Patient();
		pat.setId(patient.getId());
		pat.setUuid(patient.getUuid());
		pat.setDateCreated(patient.getDateCreated());
		return pat;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		Identifier that = (Identifier) o;
		return Objects.equals(uuid, that.uuid) && Objects.equals(identifier, that.identifier)
		        && Objects.equals(identifierType, that.identifierType) && Objects.equals(location, that.location)
		        && Objects.equals(preferred, that.preferred) && Objects.equals(voided, that.voided);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(uuid, identifier, identifierType, location, preferred, voided);
	}
	
}
