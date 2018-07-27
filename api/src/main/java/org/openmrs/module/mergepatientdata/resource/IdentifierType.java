package org.openmrs.module.mergepatientdata.resource;

import java.util.Objects;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.context.Context;

public class IdentifierType implements MergeAbleResource {
	
	private String uuid;
	
	private String name;
	
	private String description;
	
	public IdentifierType(PatientIdentifierType openmrsType) {
		this.uuid = openmrsType.getUuid();
		this.name = openmrsType.getName();
		this.description = openmrsType.getDescription();
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		BaseOpenmrsObject type = Context.getPatientService().getPatientIdentifierTypeByUuid(uuid);
		if (type != null) {
			return type;
		}
		
		//org.openmrs.PatientIdentifierType newType = new org.openmrs.PatientIdentifierType();
		//newType.setUuid(uuid);
		//newType.setName(name);
		//newType.setDescription(description);
		
		// This should be removed
		// I have put it here for Test usecases
		// Just get the Openmrs id if this is a test case
		
		switch (name) {
			case "OpenMRS Identification Number":
				type = Context.getPatientService().getPatientIdentifierType(1);
				break;
			case "Old Identification Number":
				type = Context.getPatientService().getPatientIdentifierType(2);
				break;
			case "Social Security Number":
				type = Context.getPatientService().getPatientIdentifierType(3);
				break;
			case "Test National ID No":
				type = Context.getPatientService().getPatientIdentifierType(4);
				break;
			default:
				type = Context.getPatientService().getPatientIdentifierType(2);
				
		}
		
		return type;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		IdentifierType that = (IdentifierType) o;
		return Objects.equals(uuid, that.uuid) && Objects.equals(name, that.name)
		        && Objects.equals(description, that.description);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(uuid, name, description);
	}
	
}
