package org.openmrs.module.mergepatientdata.resource;

import java.util.Set;

import org.openmrs.ConceptSource;

public class ConceptReferenceTerm {
	
	private Integer conceptReferenceTermId;
	
	// For now will depend on Openmrs Type
	private ConceptSource conceptSource;
	
	private String code;
	
	private String version;
	
	private Set<ConceptReferenceTermMap> conceptReferenceTermMaps;
	
	public ConceptReferenceTerm(org.openmrs.ConceptReferenceTerm term) {
		this.conceptReferenceTermId = term.getConceptReferenceTermId();
		this.code = term.getCode();
		this.version = term.getVersion();
		
	}
	
	public Integer getConceptReferenceTermId() {
		return conceptReferenceTermId;
	}
	
	public void setConceptReferenceTermId(Integer conceptReferenceTermId) {
		this.conceptReferenceTermId = conceptReferenceTermId;
	}
	
	public ConceptSource getConceptSource() {
		return conceptSource;
	}
	
	public void setConceptSource(ConceptSource conceptSource) {
		this.conceptSource = conceptSource;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public Set<ConceptReferenceTermMap> getConceptReferenceTermMaps() {
		return conceptReferenceTermMaps;
	}
	
	public void setConceptReferenceTermMaps(Set<ConceptReferenceTermMap> conceptReferenceTermMaps) {
		this.conceptReferenceTermMaps = conceptReferenceTermMaps;
	}
	
}
