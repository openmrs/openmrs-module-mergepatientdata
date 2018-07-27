package org.openmrs.module.mergepatientdata.resource;

import java.util.Date;

import org.openmrs.BaseOpenmrsObject;

public class ConceptMap implements MergeAbleResource {
	
	private Integer conceptMapId;
	
	private Concept concept;
	
	// For now this is redundant 
	private ConceptReferenceTerm conceptReferenceTerm;
	
	private ConceptMapType conceptMapType;
	
	private User creator;
	
	private User changedBy;
	
	private Date dateCreated;
	
	private Date dateChanged;
	
	private String uuid;
	
	public ConceptMap(org.openmrs.ConceptMap map) {
		this.conceptMapId = map.getConceptMapId();
		this.concept = new Concept(map.getConcept(), false);
		this.conceptMapType = new ConceptMapType(map.getConceptMapType());
		this.uuid = map.getUuid();
		this.dateCreated = map.getDateCreated();
		this.dateChanged = map.getDateChanged();
	}
	
	public Integer getConceptMapId() {
		return conceptMapId;
	}
	
	public void setConceptMapId(Integer conceptMapId) {
		this.conceptMapId = conceptMapId;
	}
	
	public Concept getConcept() {
		return concept;
	}
	
	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	
	public ConceptReferenceTerm getConceptReferenceTerm() {
		return conceptReferenceTerm;
	}
	
	public void setConceptReferenceTerm(ConceptReferenceTerm conceptReferenceTerm) {
		this.conceptReferenceTerm = conceptReferenceTerm;
	}
	
	public ConceptMapType getConceptMapType() {
		return conceptMapType;
	}
	
	public void setConceptMapType(ConceptMapType conceptMapType) {
		this.conceptMapType = conceptMapType;
	}
	
	public User getCreator() {
		return creator;
	}
	
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	public User getChangedBy() {
		return changedBy;
	}
	
	public void setChangedBy(User changedBy) {
		this.changedBy = changedBy;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public Date getDateChanged() {
		return dateChanged;
	}
	
	public void setDateChanged(Date dateChanged) {
		this.dateChanged = dateChanged;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.ConceptMap openmrsMap = new org.openmrs.ConceptMap();
		openmrsMap.setUuid(uuid);
		openmrsMap.setConceptMapId(conceptMapId);
		openmrsMap.setConcept((org.openmrs.Concept) this.concept.getOpenMrsObject());
		openmrsMap.setConceptMapType((org.openmrs.ConceptMapType) conceptMapType.getOpenMrsObject());
		// TODO :-Should set the Super User as the default user in all cases
		openmrsMap.setCreator(null);
		openmrsMap.setDateCreated(dateCreated);
		openmrsMap.setDateChanged(dateChanged);
		return openmrsMap;
	}
	
}
