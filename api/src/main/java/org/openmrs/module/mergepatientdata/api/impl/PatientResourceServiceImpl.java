package org.openmrs.module.mergepatientdata.api.impl;

import java.util.List;

import org.openmrs.Patient;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.APIException;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.mergepatientdata.api.PatientResourceService;
import org.openmrs.module.mergepatientdata.api.exceptions.MPDException;
import org.openmrs.module.mergepatientdata.api.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class PatientResourceServiceImpl extends BaseOpenmrsService implements PatientResourceService {
	
	@Override
	public Patient savePatient(Patient patient) throws APIException {
		
		return Context.getPatientService().savePatient(patient);
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
	public void savePatients(List<org.openmrs.module.mergepatientdata.resource.Patient> patients) {
		System.out.println("Saving " + patients.size() + " Patients");
		List<org.openmrs.Patient> openmrsPatients = (List<org.openmrs.Patient>) ObjectUtils
		        .getOpenmrsResourceObjectsFromMPDResourceObjects(patients);
		
		if (openmrsPatients != null && !openmrsPatients.isEmpty()) {
			PatientResourceService patientService = Context.getService(PatientResourceService.class);
			for (org.openmrs.Patient patient : openmrsPatients) {
				System.out.println("Saving Patient: " + patient.getGivenName() );
				patientService.savePatient(patient);
			}
		} else {
			System.out.println("OpenmrsPatients List invalid : " + openmrsPatients);
		}
		
	}
	
}
