package org.openmrs.module.mergepatientdata.resource;

/**
 * Represents a {@link Person} who provides care to Patient during an {@link Encounter}
 */
public class Provider {
	
	private Integer providerId;
	
	private Person person;
	
	private String identifier;
	
	public Provider() {
	}
	
	public Integer getProviderId() {
		return providerId;
	}
	
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
}
