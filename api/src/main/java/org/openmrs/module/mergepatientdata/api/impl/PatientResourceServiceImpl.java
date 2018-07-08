package org.openmrs.module.mergepatientdata.api.impl;

import java.util.HashMap;
import java.util.List;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.mergepatientdata.api.PatientResourceService;
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.api.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PatientResourceServiceImpl extends BaseOpenmrsService implements PatientResourceService {
	
	private final static Logger log = LoggerFactory.getLogger(PatientResourceServiceImpl.class);
	
	@Override
	public Patient savePatient(Patient patient, PaginatedAuditMessage auditor) throws APIException {
		try {
			return Context.getPatientService().savePatient(patient);
		}
		catch (org.openmrs.api.ValidationException e) {
			//Tried to save an invalid Patient
			log.warn("Tried to merge an invalid Patient, {}", e.getMessage());
			auditor.setHasErrors(true);
			auditor.getFailureDetails().add(
			    "Failed to Merge 'Patient#" + patient.getPatientIdentifier().getIdentifier() + "' rationale: "
			            + e.getMessage());
			//Check whether Patient already exists
			Patient existingPatient = Context.getPatientService().getPatientByUuid(patient.getUuid());
			if (existingPatient != null) {
				if (existingPatient.getId() == patient.getId()) {
					log.error("Patient: {} already exists", patient.getGivenName());
				}
			}
			
		}
		
		return null;
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
	
	@Override
	public void savePatients(List<org.openmrs.module.mergepatientdata.resource.Patient> patients,
	        PaginatedAuditMessage auditor) {
		List<org.openmrs.Patient> openmrsPatients = (List<org.openmrs.Patient>) ObjectUtils
		        .getOpenmrsResourceObjectsFromMPDResourceObjects(patients);
		
		if (openmrsPatients != null && !openmrsPatients.isEmpty()) {
			PatientResourceService patientService = new PatientResourceServiceImpl();
			int counter = 0;
			for (org.openmrs.Patient patient : openmrsPatients) {
				Patient savedPatient = patientService.savePatient(patient, auditor);
				if (savedPatient != null) {
					//Then the Patient was saved
					counter++;
				}
				HashMap<String, Integer> resourceCounter = new HashMap<>();
				resourceCounter.put("Patient", counter);
				auditor.setResourceCount(resourceCounter);
			}
		} else {
			auditor.getFailureDetails().add("Tried to Merge Empty/null Patient List.");
		}
		
	}
}
