package org.openmrs.module.mergepatientdata.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.mergepatientdata.api.PatientResourceService;
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.api.utils.ObjectUtils;
import org.openmrs.module.mergepatientdata.resource.Encounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PatientResourceServiceImpl extends BaseOpenmrsService implements PatientResourceService {
	
	private final static Logger log = LoggerFactory.getLogger(PatientResourceServiceImpl.class);
	
	@Override
	public Patient savePatient(Patient patient, PaginatedAuditMessage auditor) throws APIException {
		try {
			Patient exitingPatient = null;
			if (patient.getUuid() != null) {
				exitingPatient = Context.getPatientService().getPatientByUuid(patient.getUuid());
				if (exitingPatient != null
				        && patient.getPatientIdentifier().getIdentifier()
				                .equals(exitingPatient.getPatientIdentifier().getIdentifier())) {
					Context.clearSession();
					Context.closeSessionWithCurrentUser();
					// Update Existing Patient
					// For now we aren't updating the patient
					return exitingPatient;
				} else {
					Context.clearSession();
					return Context.getPatientService()
					        .savePatient(inspectPatientPropertiesAndModifyThemAccordingly(patient));
				}
			}
			
		}
		catch (org.openmrs.api.ValidationException e) {
			// Tried to save an invalid Patient
			log.error("Tried to merge an invalid Patient, {}", e.getMessage());
			auditor.setHasErrors(true);
			auditor.getFailureDetails().add(
			    "Failed to Merge 'Patient#" + patient.getPatientIdentifier().getIdentifier() + "' rationale: "
			            + e.getMessage());
		}
		return null;
		
	}
	
	private void updateEncountersOfTheIdentifiersIfRequired(Integer oldId, Patient updatedPatient, List<Encounter> encounters) {
		if (encounters == null) {
			return;
		}
		for (Encounter enc : encounters) {
			if (enc.getPatient().getUuid().equals(updatedPatient.getUuid())) {
				if (enc.getPatient().getId() == oldId) {
					// Update it of the new Patient Id
					enc.getPatient().setId(updatedPatient.getId());
				}
			}
		}
	}
	
	@Override
	public List<Patient> getAllPatients() throws APIException {
		return Context.getPatientService().getAllPatients();
	}
	
	@Override
	public List<Patient> getAllPatients(boolean includeVoided) throws APIException {
		return Context.getPatientService().getAllPatients(includeVoided);
	}
	
	@Override
	public List<Patient> getPatients(String name, String identifier, List<PatientIdentifierType> identifierTypes,
	        boolean matchIdentifierExactly) throws APIException {
		return Context.getPatientService().getPatients(name, identifier, identifierTypes, matchIdentifierExactly);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<org.openmrs.Patient> savePatients(List<org.openmrs.module.mergepatientdata.resource.Patient> patients,
	        PaginatedAuditMessage auditor, List<Encounter> encounters) {
		List<org.openmrs.Patient> savedPatients = new ArrayList<>();
		
		List<org.openmrs.Patient> openmrsPatients = (List<org.openmrs.Patient>) ObjectUtils
		        .getOpenmrsResourceObjectsFromMPDResourceObjects(patients);
		
		if (openmrsPatients != null && !openmrsPatients.isEmpty()) {
			PatientResourceService patientService = new PatientResourceServiceImpl();
			int counter = 0;
			for (org.openmrs.Patient patient : openmrsPatients) {
				Integer oldId = patient.getId();
				Patient savedPatient = patientService.savePatient(patient, auditor);
				
				// TODO cater for auditing of updated Resources
				if (savedPatient != null) {
					updateEncountersOfTheIdentifiersIfRequired(oldId, savedPatient, encounters);
					savedPatients.add(savedPatient);
					counter++;
				}
				HashMap<String, Integer> resourceCounter = new HashMap<>();
				resourceCounter.put("Patient", counter);
				auditor.setResourceCount(resourceCounter);
			}
		} else {
			auditor.getFailureDetails().add("Tried to Merge Empty/null Patient List.");
		}
		return savedPatients;
	}
	
	/**
	 * To make saving a new Patient possible, some fields need to be set to null This is required to
	 * prevent Hibernate problems
	 * 
	 * @param patient
	 * @return
	 */
	private Patient inspectPatientPropertiesAndModifyThemAccordingly(Patient patient) {
		patient.setId(null);
		patient.setPersonId(null);
		
		if (patient.getPersonName() != null) {
			patient.getPersonName().setId(null);
		}
		Set<PatientIdentifier> identifiers = patient.getIdentifiers();
		for (PatientIdentifier id : identifiers) {
			id.setId(null);
			id.setPatient(null);
		}
		return patient;
	}
}
