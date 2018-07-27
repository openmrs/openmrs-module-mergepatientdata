package org.openmrs.module.mergepatientdata.api.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAddress;
import org.openmrs.PersonName;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.enums.MergeAbleDataCategory;
import org.openmrs.module.mergepatientdata.enums.Operation;
import org.openmrs.module.mergepatientdata.resource.Concept;
import org.openmrs.module.mergepatientdata.resource.Encounter;
import org.openmrs.module.mergepatientdata.resource.EncounterType;
import org.openmrs.module.mergepatientdata.resource.Location;
import org.openmrs.module.mergepatientdata.resource.Obs;
import org.openmrs.module.mergepatientdata.resource.Patient;
import org.openmrs.module.mergepatientdata.resource.Person;
import org.openmrs.module.mergepatientdata.sync.MPDStore;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.util.DateUtil;

public class MergePatientDataUtilsTest extends BaseModuleContextSensitiveTest {
	
	private MPDStore store;
	
	private List<Class> typesToImport;
	
	private PaginatedAuditMessage auditor;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		auditor = new PaginatedAuditMessage();
		auditor.setResources(new ArrayList<>());
		auditor.setFailureDetails(new ArrayList<>());
		auditor.setOperation(Operation.IMPORT);
		store = new MPDStore();
		store.setOriginId("dummy_server");
		store.getTypes().add(MergeAbleDataCategory.PATIENT);
		List<Patient> patients = (List<Patient>) ObjectUtils.getMPDResourceObjectsFromOpenmrsResourceObjects(
				new HashSet<>(Context.getPatientService().getAllPatients()));
		store.setEncounters(new ArrayList<>()); 
		store.getEncounters().add(buildEncounter());
		store.getEncounters().add(buildEncounter());
		for (Patient pat : patients) {
			// Make some changes
			if (pat.getPerson().getGender().equals("M")) {
				pat.getPerson().setGender("F");
			} else {
				pat.getPerson().setGender("M");
			}
		}
		store.setPatients(patients);
		typesToImport = new ArrayList<>();
		typesToImport.add(Patient.class);
	}
	
	@Test
	public void mergeResourceToOpenmrsDataBase_shouldUpdatePatientResource() {
		MergePatientDataUtils.mergeResourceToOpenmrsDataBase(store, typesToImport, auditor);
	}
	
	@Test
	public void mergeResourceToOpenmrsDataBase_shouldSaveNewEncounterResources() {
		typesToImport.add(Encounter.class);
		store.getTypes().add(MergeAbleDataCategory.ENCOUNTER);
		MergePatientDataUtils.mergeResourceToOpenmrsDataBase(store, typesToImport, auditor);
	}
	
	@Test
	public void mergeResourceToOpenmrsDataBase_shouldUpdateExistingEncounter() {
		typesToImport.add(Encounter.class);
		store.getTypes().add(MergeAbleDataCategory.ENCOUNTER);
		EncounterService es = Context.getEncounterService();
		
		// get the encounter from the database
		Encounter encounter = new Encounter(es.getEncounter(3), true);
		// save the current values for comparison later
		Patient origPatient = encounter.getPatient();
		Location origLocation = encounter.getLocation();
		Date origDate = encounter.getEncounterDatetime();
		
		// add values that are different than the ones in the initialData.xml
		// file
		Location loc2 = new Location();
		loc2.setId(2);
		Date d2 = new Date();
		Patient pat2 = new Patient();
		pat2.setId(2);
		
		encounter.setLocation(loc2);
		encounter.setEncounterDatetime(d2);
		encounter.setPatient(pat2);
		
		org.openmrs.Encounter updatedEnc = (org.openmrs.Encounter) encounter.getOpenMrsObject();
		store.getEncounters().add(encounter);
		MergePatientDataUtils.mergeResourceToOpenmrsDataBase(store, typesToImport, auditor);
		assertFalse("The location should be different", origLocation.getId().equals(loc2.getId()));
		assertTrue("The location should be different", updatedEnc.getLocation().getId().equals(loc2.getId()));
		assertFalse("Make sure the dates changed slightly", origDate.equals(d2));
		assertTrue("The date needs to have been set",
		    DateUtil.truncateToSeconds(updatedEnc.getEncounterDatetime()).equals(DateUtil.truncateToSeconds(d2)));
		assertFalse("The patient should be different", origPatient.getId().equals(pat2.getId()));
		assertTrue("The patient should have been set", updatedEnc.getPatient().getId().equals(pat2.getId()));
	}
	
	@Test
	public void mergeResourceToOpenmrsDataBase_shouldSaveObs() {
		typesToImport.add(Encounter.class);
		store.getTypes().add(MergeAbleDataCategory.ENCOUNTER);
		Encounter newEnc = buildEncounter();
		Obs someObs = buildObs();
		// Some hard coded uuid
		someObs.setUuid("5h5x710b-dm563-490b7-b449-6e0j739nv630");
		Set<Obs> obsz = new HashSet<>();
		obsz.add(someObs);
		newEnc.setObs(obsz);
		store.setEncounters(new ArrayList<>());
		store.getEncounters().add(newEnc);
		MergePatientDataUtils.mergeResourceToOpenmrsDataBase(store, typesToImport, auditor);
		assertNotNull(Context.getObsService().getObsByUuid("5h5x710b-dm563-490b7-b449-6e0j739nv630"));
		
	}
	
	@Test
	public void mergeResourceToOpenmrsDataBase_shouldUpdateModifiedIdsOnSavingPatientResource() {
		typesToImport.add(Encounter.class);
		store.getTypes().add(MergeAbleDataCategory.ENCOUNTER);
		
		org.openmrs.Patient patient = new org.openmrs.Patient(99);
		patient.setUuid("5h5x710b-dm563-490b7-b449-6e0j739nv630");
		patient.setGender("M");
		
		org.openmrs.PatientIdentifier identifier = new org.openmrs.PatientIdentifier("QWEET", Context.getPatientService().getPatientIdentifierType(2),
		        Context.getLocationService().getLocation(1));
		identifier.setPreferred(true);
		Set<PatientIdentifier> patientIdentifiers = new LinkedHashSet<>();
		patientIdentifiers.add(identifier);
		//patient.setIdentifiers(patientIdentifiers);
		patient.addIdentifier(identifier);
		PersonName name = new PersonName("givenName", "middleName", "familyName");
		patient.addName(name);
		PersonAddress address = new PersonAddress();
		address.setAddress1("some address");
		patient.addAddress(address);
		Patient mpdPatient = new Patient(patient);
		store.getPatients().add(mpdPatient);
		
		// Construct Encounter
		Encounter enc = buildEncounter();
		enc.setPatient(new Patient(patient));
		Obs someObs = buildObs();
		Set<Obs> obsz = new HashSet<>();
		obsz.add(someObs);
		enc.setObs(obsz);
		store.setEncounters(new ArrayList<>());
		store.getEncounters().add(enc);
		
		MergePatientDataUtils.mergeResourceToOpenmrsDataBase(store, typesToImport, auditor);
		org.openmrs.Patient updatedPatient = Context.getPatientService().getPatientByUuid("5h5x710b-dm563-490b7-b449-6e0j739nv630");
		org.openmrs.Encounter updatedEncounter = Context.getEncounterService().getEncounterByUuid(enc.getUuid());
		assertNotNull(updatedPatient);
		assertNotEquals(patient.getId(), updatedPatient.getId());
		assertEquals(updatedPatient.getId(), updatedEncounter.getPatient().getId());
	}
	
	@Test
	public void mergeResourceToOpenmrsDataBase_shouldUpdateModifiedIdsOnSavingEncounterResource() {
		typesToImport.add(Encounter.class);
		store.getTypes().add(MergeAbleDataCategory.ENCOUNTER);
		Set<Obs> obsz = new HashSet<>();
		for (int i = 0; i > 5; i++) {
			Obs obs = buildObs();
			obs.setObsId(i);
			obsz.add(obs);
		}
		Encounter enc = buildEncounter();
		enc.setUuid("5h5x710b-dm563-490b7-b449-6e0j73nvh095");
		enc.setObs(obsz);
		enc.setId(3);
		
		store.getEncounters().add(enc);
		MergePatientDataUtils.mergeResourceToOpenmrsDataBase(store, typesToImport, auditor);
		org.openmrs.Encounter savedEncounter = Context.getEncounterService().getEncounterByUuid("5h5x710b-dm563-490b7-b449-6e0j73nvh095");
		assertNotNull(savedEncounter);
		Set<org.openmrs.Obs> savedObs = savedEncounter.getObs();
		org.openmrs.Concept obsConcept = Context.getConceptService().getConcept(5497);
		for (org.openmrs.Obs obs : savedObs) {
			org.openmrs.Concept concept = obs.getConcept();
			assertEquals(savedEncounter.getId(), obs.getEncounter().getId());
			assertEquals(obsConcept.getId(), concept.getId());
			assertEquals(obsConcept.getDatatype(), concept.getDatatype());
		}
		
	}
	
	private Encounter buildEncounter() {
		// First, create a new Encounter
		Encounter enc = new Encounter();
		double randomFigure = Math.random();
		enc.setUuid("" + randomFigure);
		enc.setLocation(new Location(Context.getLocationService().getLocation(1), true));
		enc.setEncounterType(new EncounterType(Context.getEncounterService().getEncounterType(1)));
		enc.setEncounterDatetime(new Date());
		enc.setPatient(new Patient(Context.getPatientService().getPatient(2)));
		return enc;
	}
	
	private Obs buildObs() {
		Obs newObs = new Obs();
		newObs.setConcept(new Concept(Context.getConceptService().getConcept(5497), true));
		newObs.setValueNumeric(50d);
		Location loc2 = new Location();
		loc2.setId(2);
		newObs.setLocation(loc2);
		newObs.setObsDatetime(new Date());
		newObs.setPerson(new Person(Context.getPersonService().getPerson(1)));
		return newObs;
	}
	
}
