package org.openmrs.module.mergepatientdata.resource;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.attribute.Attribute;
import org.openmrs.attribute.BaseAttribute;

public class ConceptAttribute extends BaseAttribute<ConceptAttributeType, Concept> implements Attribute<ConceptAttributeType, Concept>, MergeAbleResource {
	
	private static final long serialVersionUID = 1L;
	
	private Integer conceptAttributeId;
	
	public ConceptAttribute(org.openmrs.ConceptAttribute att) {
		this.conceptAttributeId = att.getConceptAttributeId();
		this.setAttributeType(new ConceptAttributeType(att.getAttributeType()));
		this.setConcept(new Concept(att.getConcept(), false));
		this.setDateCreated(att.getDateCreated());
		this.setUuid(att.getUuid());
		this.setOwner(new Concept(att.getOwner(), false));
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.ConceptAttribute attribute = new org.openmrs.ConceptAttribute();
		attribute.setConceptAttributeId(conceptAttributeId);
		attribute.setAttributeType((org.openmrs.ConceptAttributeType) this.getAttributeType().getOpenMrsObject());
		attribute.setOwner((org.openmrs.Concept) this.getOwner().getOpenMrsObject());
		attribute.setUuid(this.getUuid());
		attribute.setDateCreated(getDateCreated());
		return attribute;
	}
	
	public Concept getConcept() {
		return getOwner();
	}
	
	public void setConcept(Concept concept) {
		setOwner(concept);
	}
	
	public Integer getConceptAttributeId() {
		return this.conceptAttributeId;
	}
	
	public void setConceptAttributeId(Integer conceptAttributeId) {
		this.conceptAttributeId = conceptAttributeId;
	}
	
	@Override
	public Integer getId() {
		return getConceptAttributeId();
	}
	
	@Override
	public void setId(Integer id) {
		setConceptAttributeId(id);
	}
	
}
