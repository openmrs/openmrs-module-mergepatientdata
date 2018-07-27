package org.openmrs.module.mergepatientdata.api;

import java.util.List;

import org.openmrs.Concept;

public interface ConceptResourceService {
	
	public Concept saveConcept(Concept concept);
	
	public Concept getConcept(String nameOrName);
	
	public List<Concept> getAllConcepts();
}
