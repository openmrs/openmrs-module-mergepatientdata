package org.openmrs.module.mergepatientdata.resource;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.BaseOpenmrsObject;

public class ConceptMapType extends BaseOpenmrsMetadata implements MergeAbleResource {
	
	private static final long serialVersionUID = 1L;
	
	private Integer conceptMapTypeId;
	
	private Boolean isHidden = Boolean.FALSE;
	
	public ConceptMapType(org.openmrs.ConceptMapType mapType) {
		this.conceptMapTypeId = mapType.getConceptMapTypeId();
		this.setUuid(mapType.getUuid());
		this.setName(mapType.getName());
		this.setDescription(mapType.getDescription());
		this.isHidden = mapType.getIsHidden();
	}
	
	@Override
	public Integer getId() {
		return getConceptMapTypeId();
	}
	
	@Override
	public void setId(Integer id) {
		setConceptMapTypeId(id);
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.ConceptMapType mapType = new org.openmrs.ConceptMapType();
		mapType.setConceptMapTypeId(conceptMapTypeId);
		mapType.setUuid(getUuid());
		mapType.setIsHidden(isHidden);
		mapType.setName(getName());
		mapType.setDescription(getDescription());
		return mapType;
	}
	
	public Integer getConceptMapTypeId() {
		return conceptMapTypeId;
	}
	
	public void setConceptMapTypeId(Integer conceptMapTypeId) {
		this.conceptMapTypeId = conceptMapTypeId;
	}
	
	public Boolean getIsHidden() {
		return isHidden;
	}
	
	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}
	
}
