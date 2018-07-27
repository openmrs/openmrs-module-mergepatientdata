package org.openmrs.module.mergepatientdata.api.impl;

import java.util.List;

import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.api.ConceptResourceService;

public class ConceptResourceServiceImpl implements ConceptResourceService {
	
	@Override
	public Concept saveConcept(Concept concept) {
		return Context.getConceptService().saveConcept(concept);
	}
	
	@Override
	public Concept getConcept(String nameOrName) {
		return Context.getConceptService().getConcept(nameOrName);
	}
	
	@Override
	public List<Concept> getAllConcepts() {
		return Context.getConceptService().getAllConcepts();
	}
	
}
