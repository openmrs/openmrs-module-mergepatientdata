package org.openmrs.module.mergepatientdata.resource;

import java.util.Date;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Drug;

public class ConceptAnswer implements MergeAbleResource {
	
	private Integer conceptAnswerId;
	
	private Concept concept;
	
	private Concept answerConcept;
	
	// For now we are depending on Openmrs Type, hope it won't cause issues
	private Drug answerDrug;
	
	private User creator;
	
	private Date dateCreated;
	
	private Double sortWeight;
	
	private Date dateChanged;
	
	public ConceptAnswer(org.openmrs.ConceptAnswer openmrsAns) {
		this.conceptAnswerId = openmrsAns.getConceptAnswerId();
		this.concept = new Concept(openmrsAns.getConcept(), false);
		this.answerConcept = new Concept(openmrsAns.getAnswerConcept(), false);
		this.sortWeight = openmrsAns.getSortWeight();
		this.answerDrug = openmrsAns.getAnswerDrug();
		this.dateCreated = openmrsAns.getDateCreated();
		this.dateChanged = openmrsAns.getDateChanged();
		
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.ConceptAnswer answer = new org.openmrs.ConceptAnswer();
		answer.setConceptAnswerId(conceptAnswerId);
		answer.setConcept((org.openmrs.Concept) concept.getOpenMrsObject());
		answer.setAnswerConcept((org.openmrs.Concept) answerConcept.getOpenMrsObject());
		answer.setSortWeight(sortWeight);
		answer.setAnswerDrug(answerDrug);
		answer.setDateCreated(dateCreated);
		answer.setDateChanged(dateChanged);
		return answer;
	}
	
	public Date getDateChanged() {
		return dateChanged;
	}
	
	public void setDateChanged(Date dateChanged) {
		this.dateChanged = dateChanged;
	}
	
	public Integer getConceptAnswerId() {
		return conceptAnswerId;
	}
	
	public void setConceptAnswerId(Integer conceptAnswerId) {
		this.conceptAnswerId = conceptAnswerId;
	}
	
	public Concept getConcept() {
		return concept;
	}
	
	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	
	public Concept getAnswerConcept() {
		return answerConcept;
	}
	
	public void setAnswerConcept(Concept answerConcept) {
		this.answerConcept = answerConcept;
	}
	
	public Drug getAnswerDrug() {
		return answerDrug;
	}
	
	public void setAnswerDrug(Drug answerDrug) {
		this.answerDrug = answerDrug;
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
	
	public Double getSortWeight() {
		return sortWeight;
	}
	
	public void setSortWeight(Double sortWeight) {
		this.sortWeight = sortWeight;
	}
	
}
