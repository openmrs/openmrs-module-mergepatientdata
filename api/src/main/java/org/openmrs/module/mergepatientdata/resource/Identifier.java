package org.openmrs.module.mergepatientdata.resource;

import java.util.Objects;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.PatientIdentifierType;

public class Identifier implements MergeAbleResource {
	
	private String uuid;
	
	private String identifier;
	
	private IdentifierType identifierType;
	
	private Location location;
	
	private Boolean preferred;
	
	private Boolean voided;
	
	public Identifier(org.openmrs.PatientIdentifier openmrPatientIdentifier) {
		this.uuid = openmrPatientIdentifier.getUuid();
		this.identifier = openmrPatientIdentifier.getIdentifier();
		this.identifierType = new IdentifierType(openmrPatientIdentifier.getIdentifierType());
		this.location = new Location(openmrPatientIdentifier.getLocation());
		this.preferred = openmrPatientIdentifier.getPreferred();
		this.voided = openmrPatientIdentifier.getVoided();
		
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.PatientIdentifier patientIdentifier = new org.openmrs.PatientIdentifier();
		patientIdentifier.setUuid(uuid);
		patientIdentifier.setIdentifier(identifier);
		if (identifierType != null) {
			patientIdentifier.setIdentifierType((PatientIdentifierType) identifierType.getOpenMrsObject());
		}
		if (location != null) {
			patientIdentifier.setLocation((org.openmrs.Location) location.getOpenMrsObject());
		}
		patientIdentifier.setPreferred(preferred);
		patientIdentifier.setVoided(voided);
		
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
