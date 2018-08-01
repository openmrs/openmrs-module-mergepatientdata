package org.openmrs.module.mergepatientdata.resource;

import java.util.Objects;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.User;

public class PersonName implements MergeAbleResource {
	
	private Integer id;
	
	private String uuid;
	
	private String givenName;
	
	private String middleName;
	
	private String familyName;
	
	private String familyName2;
	
	private boolean voided;
	
	public PersonName(org.openmrs.PersonName openmrsName) {
		if (openmrsName == null) {
			return;
		}
		//this.id = openmrsName.getId();
		this.uuid = openmrsName.getUuid();
		this.givenName = openmrsName.getGivenName();
		this.middleName = openmrsName.getMiddleName();
		this.familyName = openmrsName.getFamilyName();
		this.familyName2 = openmrsName.getFamilyName2();
		this.voided = openmrsName.getVoided();
		
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.PersonName personName = new org.openmrs.PersonName();
		//personName.setId(id);
		personName.setUuid(uuid);
		personName.setGivenName(givenName);
		personName.setMiddleName(middleName);
		personName.setFamilyName(familyName);
		personName.setFamilyName2(familyName2);
		personName.setVoided(voided);
		return personName;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getGivenName() {
		return givenName;
	}
	
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	
	public String getMiddleName() {
		return middleName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getFamilyName() {
		return familyName;
	}
	
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	
	public String getFamilyName2() {
		return familyName2;
	}
	
	public void setFamilyName2(String familyName2) {
		this.familyName2 = familyName2;
	}
	
	public boolean isVoided() {
		return voided;
	}
	
	public void setVoided(boolean voided) {
		this.voided = voided;
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
		
		PersonName that = (PersonName) o;
		return Objects.equals(uuid, that.uuid) && Objects.equals(givenName, that.givenName)
		        && Objects.equals(middleName, that.middleName) && Objects.equals(familyName, that.familyName)
		        && Objects.equals(familyName2, that.familyName2) && Objects.equals(voided, that.voided);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(uuid, givenName, middleName, familyName, familyName2, voided);
	}
	
}
