package org.openmrs.module.mergepatientdata.resource;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.attribute.AttributeType;
import org.openmrs.attribute.BaseAttributeType;

public class ConceptAttributeType extends BaseAttributeType<Concept> implements AttributeType<Concept>, MergeAbleResource {
	
	private Integer conceptAttributeTypeId;
	
	public ConceptAttributeType(org.openmrs.ConceptAttributeType attributeType) {
		this.conceptAttributeTypeId = attributeType.getConceptAttributeTypeId();
		this.setUuid(attributeType.getUuid());
		this.setDescription(attributeType.getDescription());
		this.setDatatypeClassname(attributeType.getDatatypeClassname());
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.ConceptAttributeType attType = new org.openmrs.ConceptAttributeType();
		attType.setUuid(getUuid());
		attType.setConceptAttributeTypeId(conceptAttributeTypeId);
		attType.setDescription(getDescription());
		attType.setDatatypeClassname(getDatatypeClassname());
		return attType;
	}
	
	public Integer getConceptAttributeTypeId() {
		return conceptAttributeTypeId;
	}
	
	public void setConceptAttributeTypeId(Integer conceptAttributeTypeId) {
		this.conceptAttributeTypeId = conceptAttributeTypeId;
	}
	
	@Override
	public Integer getId() {
		return getConceptAttributeTypeId();
	}
	
	@Override
	public void setId(Integer id) {
		setConceptAttributeTypeId(id);
	}
}
