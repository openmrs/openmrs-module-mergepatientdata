package org.openmrs.module.mergepatientdata.api.impl;

import java.util.List;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.ConceptName;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.FormField;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.api.EncounterResourceService;
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.api.utils.ObjectUtils;
import org.openmrs.module.mergepatientdata.resource.Encounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncounterResourceServiceImpl implements EncounterResourceService {
	
	private final static Logger log = LoggerFactory.getLogger(EncounterResourceServiceImpl.class);
	
	public EncounterResourceServiceImpl() {
	}
	
	@Override
	public org.openmrs.Encounter saveEncounter(org.openmrs.Encounter encounter, PaginatedAuditMessage auditor) {
		if (encounter == null) {
			return null;
		}
		try {
			return Context.getEncounterService().saveEncounter(encounter);
		}
		catch (org.openmrs.api.ValidationException e) {
			log.error("Tried to merge an invalid Encounter, {}", e.getMessage());
			auditor.setHasErrors(true);
			auditor.getFailureDetails().add(
			    "Failed to Merge 'Encounter' of patient "
			            + Context.getPatientService().getPatient(encounter.getPatient().getId()).getGivenName()
			            + "' rationale: " + e.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void saveEncounters(List<Encounter> list, PaginatedAuditMessage auditor) {
		if (list != null && list.isEmpty()) {
			return;
		}
		
		List<org.openmrs.Encounter> encounters = (List<org.openmrs.Encounter>) ObjectUtils
		        .getOpenmrsResourceObjectsFromMPDResourceObjects(list);
		
		int counter = 0;
		
		for (org.openmrs.Encounter encounter : encounters) {
			// We are currently not supporting visits
			encounter.setVisit(null);
			// Check whether Encounter is new
			if (encounter.getId() != null) {
				org.openmrs.Encounter enc = Context.getEncounterService().getEncounterByUuid(encounter.getUuid());
				if (enc != null) {
			
					if (encounter.getPatient().getId() == enc.getPatient().getId()) {
						// Clear the Session to make the update possible
						Context.clearSession();
						// Mean while, don't update, just continue
						continue;
					} else {
						log.warn("Encounter " + encounter.getId() + " seems to be existing but on a wrong Patient");
						// This means that this Encounter already exists but was assigned to a wrong Patient
						// TODO Implement something better to solve this
						// Lets void this Encounter found
						enc.setVoided(true);
						enc.setVoidReason("This Encounter was assigned to a wrong Patient During Transer");
						Context.getEncounterService().saveEncounter(enc);
						// Lets Do the actual update
						encounter.setId(null);
						encounter.setUuid(null);
						//Context.getEncounterService().purgeEncounter(enc);
						inspectEncounterPropertiesAndModifyIfRequired(encounter);
				
					}
				} else {
					encounter.setId(null);
					inspectEncounterPropertiesAndModifyIfRequired(encounter);
				}
			}
			org.openmrs.Encounter savedEncounter = saveEncounter(encounter, auditor);
			if (savedEncounter != null) {
				counter++;
			}
		}
		auditor.getResourceCount().put("Encounter", counter);
	}
	
	private org.openmrs.Encounter inspectEncounterPropertiesAndModifyIfRequired(org.openmrs.Encounter enc) {
		log.debug("Started inspecting properties of " + enc);
		// Update Location Resource
		Location location = enc.getLocation();
		Integer oldLocationId;
		Integer newLocationId;
		if (location != null) {
			oldLocationId = location.getId();
			if (location.getUuid() != null) {
				Location existingLocation = Context.getLocationService().getLocationByUuid(location.getUuid());
				// Check if its already existing
				if (existingLocation != null) {
					if (existingLocation.getLocationId().equals(location.getLocationId())
					        && existingLocation.getUuid().equals(location.getUuid())) {
						// Its most likely to be already existing
						// For now lets just set the value to original value
						// TODO Implement something better
						enc.setLocation(existingLocation);
					} else {
						location.setId(null);
						location = Context.getLocationService().saveLocation(location);
						enc.setLocation(location);
					}
				} else {
					location.setId(null);
					location = Context.getLocationService().saveLocation(location);
					enc.setLocation(location);
				}
			}
		}
		
		// Update Form
		Form form = enc.getForm();
		if (form != null) {
			if (form.getUuid() != null) {
				Form existingForm = Context.getFormService().getFormByUuid(form.getUuid());
				// Check if Form really exists
				if (existingForm != null) {
					if (existingForm.getName().equals(form.getName())) {
						enc.setForm(existingForm);
					} else {
						form.setId(null);
						for (FormField field : form.getFormFields()) {
							field.setId(null);
						}
						// Save a fresh Form
						form = Context.getFormService().saveForm(form);
						enc.setForm(form);
					}
				} else {
					form.setId(null);
					for (FormField field : form.getFormFields()) {
						field.setId(null);
					}
					// Save a fresh Form
					form = Context.getFormService().saveForm(form);
					enc.setForm(form);
				}
			}
		}
		
		// Update EncounterType
		EncounterType type = enc.getEncounterType();
		if (type != null) {
			if (type.getUuid() != null) {
				EncounterType existingType = Context.getEncounterService().getEncounterTypeByUuid(type.getUuid());
				// Check if it really exists
				if (existingType != null) {
					if (existingType.getUuid().equals(type.getUuid())) {
						enc.setEncounterType(existingType);
					} else {
						type.setId(null);
						type = Context.getEncounterService().saveEncounterType(type);
						enc.setEncounterType(type);
					}
				} else {
					type.setId(null);
					type = Context.getEncounterService().saveEncounterType(type);
					enc.setEncounterType(type);
				}
			}
		}
		
		// Now Update The Obs About the Encounter Modifications
		Set<Obs> observations = enc.getObs();
		enc.setObs(null);
		enc = Context.getEncounterService().saveEncounter(enc);
		for (Obs obs : observations) {
			
			obs.setPerson(enc.getPatient());
			
			obs.setEncounter(new org.openmrs.Encounter(enc.getId()));
			// Checkout whether this obs is already existing
			if (obs.getId() != null) {
				Obs existingObs = Context.getObsService().getObsByUuid(obs.getUuid());
				if (existingObs != null) {
					// Prove that its really existing
					// TODO this is quite to compare using uuids again
					if (existingObs.getUuid().equals(obs.getUuid())) {
						//obs = existingObs;
						obs.setUuid(null);
						inspectObsPropertiesAndModifyIfRequired(obs, enc);
						//obs.setPerson(enc.getPatient());
						
					} else {
						inspectObsPropertiesAndModifyIfRequired(obs, enc);
					}
				} else {
					inspectObsPropertiesAndModifyIfRequired(obs, enc);
				}
			} else {
				inspectObsPropertiesAndModifyIfRequired(obs, enc);
			}
		}
		
		enc.setObs(observations);
		log.debug("Ended Inspecting Encounter properties");
		return enc;
	}
	
	private Obs inspectObsPropertiesAndModifyIfRequired(Obs obs, org.openmrs.Encounter enc) {
		log.debug("Inspecting Obs " + obs);
		obs.setId(null);
		// Since an Encounter is for one specific Patient, lets assume also the Obs is for one Patient
		obs.setPerson(new Person(enc.getPatient().getId()));
		inspectConceptPropertiesAndModifyIfRequired(obs.getConcept(), obs);
		inspectConceptPropertiesAndModifyIfRequired(obs.getValueCoded(), obs);
		ConceptName name = obs.getValueCodedName();
		if (name != null) {
			ConceptName existingName = Context.getConceptService().getConceptName(name.getId());
			if (existingName != null) {
				if (existingName.getUuid().equals(name.getUuid())) {
					obs.setValueCodedName(existingName);
				} else {
					name.setId(null);
				}
			} else {
				name.setId(null);
			}
		}
		return obs;
	}
	
	private Concept inspectConceptPropertiesAndModifyIfRequired(Concept concept, Obs obs) {
		log.debug("Inspecting Concept " + concept);
		if (concept != null) {
			// Prove that the concept exists
			Concept existingConcept = Context.getConceptService().getConcept(concept.getId());
			if (existingConcept != null) {
				if (existingConcept.getUuid().equals(concept.getUuid())) {
					obs.setConcept(existingConcept);
				} else {
					concept.setId(null);
					concept = Context.getConceptService().saveConcept(concept);
				}
			} else {
				concept.setId(null);
				concept = Context.getConceptService().saveConcept(concept);
			}
		}
		return concept;
	}
}
