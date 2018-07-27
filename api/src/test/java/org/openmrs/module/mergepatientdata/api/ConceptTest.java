package org.openmrs.module.mergepatientdata.api;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.Obs;
import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.resource.Concept;
import org.openmrs.module.mergepatientdata.resource.ConceptMap;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import com.google.gson.Gson;

import org.junit.Assert;

public class ConceptTest extends BaseModuleContextSensitiveTest {
	
	org.openmrs.Concept openmrsConcept;
	
	@Before
	public void setup() {
		openmrsConcept = Context.getConceptService().getConcept(3);
	}
	
	@Test
	public void constructorForConcept_shouldMakeCopyOfOpenmrsConcept() {
		Concept mpdCopy = new Concept(openmrsConcept, true);
		Assert.assertNotNull(mpdCopy);
		Assert.assertEquals(openmrsConcept.getUuid(), mpdCopy.getUuid());
		Assert.assertEquals(openmrsConcept.getDatatype().getDescription(), mpdCopy.getDatatype().getDescription());
		Assert.assertEquals(openmrsConcept.getConceptClass().getName(), mpdCopy.getConceptClass().getName());
		Assert.assertEquals(openmrsConcept.getSet(), mpdCopy.getSet());
		Assert.assertEquals(openmrsConcept.getConceptId(), mpdCopy.getConceptId());
	}
	
	@Test
	public void constructorForConcept_shouldCopyConceptMappingsAndNames() {
		org.openmrs.ConceptMap OCM = null;
		ConceptMap MCM = null;
		openmrsConcept = Context.getConceptService().getConcept(5089);
		// Do deep testing
		Concept mpdCopy = new Concept(openmrsConcept, true);
		Assert.assertEquals(openmrsConcept.getConceptMappings().size(), mpdCopy.getConceptMappings().size());
		for (org.openmrs.ConceptMap openmrsConceptMap : openmrsConcept.getConceptMappings()) {
			OCM = openmrsConceptMap;
			break;
		}
		for (ConceptMap mpdConceptMap : mpdCopy.getConceptMappings()) {
			if (OCM.getUuid().equals(mpdConceptMap.getUuid())) {
				MCM = mpdConceptMap;
				break;
			}
		}
		Assert.assertNotNull(MCM);
		Assert.assertEquals(OCM.getConceptMapId(), MCM.getConceptMapId());
		Assert.assertEquals(openmrsConcept.getNames().size(), mpdCopy.getNames().size());
	}
	
	@Test
	public void constructorForConcept_shouldCopyConceptSets() {
		openmrsConcept = Context.getConceptService().getConcept(23);
		Concept mpdCopy = new Concept(openmrsConcept, true);
		Assert.assertEquals(openmrsConcept.getConceptSets().size(), mpdCopy.getConceptSets().size());
	}
	
	@Test
	public void shouldBeSerializableToJson() {
		Gson gson = new Gson();
		Concept con1 = new Concept(openmrsConcept, true);
		Assert.assertNotNull(gson.toJson(con1));
		// Try out with ConceptSets
		openmrsConcept = Context.getConceptService().getConcept(23);
		Concept con2 = new Concept(openmrsConcept, true);
		Assert.assertNotNull(gson.toJson(con2));
		// Try out with more complex Concepts
		openmrsConcept = Context.getConceptService().getConcept(23);
		Concept con3 = new Concept(openmrsConcept, true);
		Assert.assertNotNull(gson.toJson(con3));
		
		Obs obs = Context.getObsService().getObs(11);
		org.openmrs.Concept observed = obs.getConcept();
		Concept con4 = new Concept(observed, true);
		Assert.assertNotNull(gson.toJson(con4));
	}
	
}
