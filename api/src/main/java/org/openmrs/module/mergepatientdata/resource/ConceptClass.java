package org.openmrs.module.mergepatientdata.resource;

import org.openmrs.BaseOpenmrsObject;

public class ConceptClass implements MergeAbleResource {
	
	public String uuid;
	
	public Integer conceptClassId;
	
	public String name;
	
	public String description;
	
	public ConceptClass(org.openmrs.ConceptClass conceptClass) {
		this.uuid = conceptClass.getUuid();
		this.conceptClassId = conceptClass.getConceptClassId();
		this.name = conceptClass.getName();
		this.description = conceptClass.getDescription();
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.ConceptClass conceptClass = new org.openmrs.ConceptClass(conceptClassId);
		conceptClass.setUuid(uuid);
		conceptClass.setName(name);
		conceptClass.setDescription(description);
		return conceptClass;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Integer getConceptClassId() {
		return conceptClassId;
	}
	
	public void setConceptClassId(Integer conceptClassId) {
		this.conceptClassId = conceptClassId;
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
}
