package org.openmrs.module.mergepatientdata.api.dao;

import java.util.List;

import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.APIException;
import org.openmrs.api.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("mergepatientdata.PatientResourceServiceDao")
public class PatientResourceServiceDao {

	@Autowired
	@Qualifier("patientService")
	PatientService patientService;
	
	public Patient savePatient(Patient patient) throws APIException {
		return patientService.savePatient(patient);
	}

	public Patient getPatient(Integer patientId) throws APIException {
		
		return patientService.getPatient(patientId);
	}
	
	public Patient getPatientOrPromotePerson(Integer patientOrPersonId) throws APIException {
		
		return patientService.getPatientOrPromotePerson(patientOrPersonId);
	}

	public PatientIdentifier getPatientIdentifierByUuid(String uuid) throws APIException {
	
		return patientService.getPatientIdentifierByUuid(uuid);
	}

	public List<Patient> getAllPatients() throws APIException {
		
		return patientService.getAllPatients();
	}

	public List<Patient> getAllPatients(boolean includeVoided) throws APIException {
		
		return patientService.getAllPatients(includeVoided);
	}

	public List<Patient> getPatients(String name, String identifier, List<PatientIdentifierType> identifierTypes,
			boolean matchIdentifierExactly) throws APIException {
		
		return patientService.getPatients(name, identifier, identifierTypes, matchIdentifierExactly);
	}

	public List<PatientIdentifier> getPatientIdentifiers(String identifier,
			List<PatientIdentifierType> patientIdentifierTypes, List<Location> locations, List<Patient> patients,
			Boolean isPreferred) throws APIException {
		
		return patientService.getPatientIdentifiers(identifier, patientIdentifierTypes, locations, patients, isPreferred);
	}

	public PatientIdentifierType savePatientIdentifierType(PatientIdentifierType patientIdentifierType)
			throws APIException {
		
		return patientService.savePatientIdentifierType(patientIdentifierType);
	}

	public List<PatientIdentifierType> getAllPatientIdentifierTypes() throws APIException {
		
		return patientService.getAllPatientIdentifierTypes();
	}

	public List<PatientIdentifierType> getAllPatientIdentifierTypes(boolean includeRetired) throws APIException {
		
		return patientService.getAllPatientIdentifierTypes(includeRetired);
	}

	public List<PatientIdentifierType> getPatientIdentifierTypes(String name, String format, Boolean required,
			Boolean hasCheckDigit) throws APIException {
		
		return patientService.getPatientIdentifierTypes(name, format, required, hasCheckDigit);
	}

	public PatientIdentifierType getPatientIdentifierType(Integer patientIdentifierTypeId) throws APIException {
		
		return patientService.getPatientIdentifierType(patientIdentifierTypeId);
	}

	public PatientIdentifierType getPatientIdentifierTypeByUuid(String uuid) throws APIException {
		
		return patientService.getPatientIdentifierTypeByUuid(uuid);
	}

	public PatientIdentifierType getPatientIdentifierTypeByName(String name) throws APIException {
		
		return patientService.getPatientIdentifierTypeByName(name);
	}

}
