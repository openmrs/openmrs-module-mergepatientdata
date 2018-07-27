package org.openmrs.module.mergepatientdata.resource;

public class ConceptReferenceTermMap {
	
	private Integer conceptReferenceTermMapId;
	
	private ConceptReferenceTerm termA;
	
	private ConceptReferenceTerm termB;
	
	public Integer getConceptReferenceTermMapId() {
		return conceptReferenceTermMapId;
	}
	
	public void setConceptReferenceTermMapId(Integer conceptReferenceTermMapId) {
		this.conceptReferenceTermMapId = conceptReferenceTermMapId;
	}
	
	public ConceptReferenceTerm getTermA() {
		return termA;
	}
	
	public void setTermA(ConceptReferenceTerm termA) {
		this.termA = termA;
	}
	
	public ConceptReferenceTerm getTermB() {
		return termB;
	}
	
	public void setTermB(ConceptReferenceTerm termB) {
		this.termB = termB;
	}
	
}
