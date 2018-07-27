package org.openmrs.module.mergepatientdata.resource;

import java.util.Date;

import org.openmrs.BaseOpenmrsObject;

public class ConceptSet implements MergeAbleResource {
	
	private Integer conceptSetId;
	
	private Concept concept; // concept in the set
	
	private Concept conceptSet; // parent concept that uses this set
	
	private Double sortWeight;
	
	private User creator;
	
	private Date dateCreated;
	
	public ConceptSet(org.openmrs.ConceptSet set) {
		this.conceptSetId = set.getConceptSetId();
		this.concept = new Concept(set.getConcept(), false);
		this.conceptSet = new Concept(set.getConceptSet(), false);
		this.sortWeight = set.getSortWeight();
		this.dateCreated = set.getDateCreated();
		// Set super User as creator
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.ConceptSet set = new org.openmrs.ConceptSet();
		set.setConceptSetId(conceptSetId);
		set.setConcept((org.openmrs.Concept) concept.getOpenMrsObject());
		set.setConceptSet((org.openmrs.Concept) conceptSet.getOpenMrsObject());
		set.setSortWeight(sortWeight);
		set.setDateCreated(dateCreated);
		return set;
	}
	
	public Integer getConceptSetId() {
		return conceptSetId;
	}
	
	public void setConceptSetId(Integer conceptSetId) {
		this.conceptSetId = conceptSetId;
	}
	
	public Concept getConcept() {
		return concept;
	}
	
	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	
	public Concept getConceptSet() {
		return conceptSet;
	}
	
	public void setConceptSet(Concept conceptSet) {
		this.conceptSet = conceptSet;
	}
	
	public Double getSortWeight() {
		return sortWeight;
	}
	
	public void setSortWeight(Double sortWeight) {
		this.sortWeight = sortWeight;
	}
	
	public User getCreator() {
		return creator;
	}
	
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
}
