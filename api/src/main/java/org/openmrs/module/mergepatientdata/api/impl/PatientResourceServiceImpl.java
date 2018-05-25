package org.openmrs.module.mergepatientdata.api.impl;

import java.util.List;

import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.APIException;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.api.PatientResourceService;

public class PatientResourceServiceImpl implements PatientResourceService {
	
	PatientService patientService = Context.getPatientService();

	@Override
	public void onShutdown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartup() {
		// TODO Auto-generated method stub

	}

	@Override
	public Patient savePatient(Patient patient) throws APIException {
		return patientService.savePatient(patient);
	}

	@Override
	public Patient getPatient(Integer patientId) throws APIException {
		
		return patientService.getPatient(patientId);
	}

	@Override
	public Patient getPatientOrPromotePerson(Integer patientOrPersonId) throws APIException {
		
		return patientService.getPatientOrPromotePerson(patientOrPersonId);
	}

	@Override
	public PatientIdentifier getPatientIdentifierByUuid(String uuid) throws APIException {
	
		return patientService.getPatientIdentifierByUuid(uuid);
	}

	@Override
	public List<Patient> getAllPatients() throws APIException {
		
		return patientService.getAllPatients();
	}

	@Override
	public List<Patient> getAllPatients(boolean includeVoided) throws APIException {
		
		return patientService.getAllPatients(includeVoided);
	}

	@Override
	public List<Patient> getPatients(String name, String identifier, List<PatientIdentifierType> identifierTypes,
			boolean matchIdentifierExactly) throws APIException {
		
		return patientService.getPatients(name, identifier, identifierTypes, matchIdentifierExactly);
	}

	@Override
	public List<PatientIdentifier> getPatientIdentifiers(String identifier,
			List<PatientIdentifierType> patientIdentifierTypes, List<Location> locations, List<Patient> patients,
			Boolean isPreferred) throws APIException {
		
		return patientService.getPatientIdentifiers(identifier, patientIdentifierTypes, locations, patients, isPreferred);
	}

	@Override
	public PatientIdentifierType savePatientIdentifierType(PatientIdentifierType patientIdentifierType)
			throws APIException {
		
		return patientService.savePatientIdentifierType(patientIdentifierType);
	}

	@Override
	public List<PatientIdentifierType> getAllPatientIdentifierTypes() throws APIException {
		
		return patientService.getAllPatientIdentifierTypes();
	}

	@Override
	public List<PatientIdentifierType> getAllPatientIdentifierTypes(boolean includeRetired) throws APIException {
		
		return patientService.getAllPatientIdentifierTypes(includeRetired);
	}

	@Override
	public List<PatientIdentifierType> getPatientIdentifierTypes(String name, String format, Boolean required,
			Boolean hasCheckDigit) throws APIException {
		
		return patientService.getPatientIdentifierTypes(name, format, required, hasCheckDigit);
	}

	@Override
	public PatientIdentifierType getPatientIdentifierType(Integer patientIdentifierTypeId) throws APIException {
		
		return patientService.getPatientIdentifierType(patientIdentifierTypeId);
	}

	@Override
	public PatientIdentifierType getPatientIdentifierTypeByUuid(String uuid) throws APIException {
		
		return patientService.getPatientIdentifierTypeByUuid(uuid);
	}

	@Override
	public PatientIdentifierType getPatientIdentifierTypeByName(String name) throws APIException {
		
		return patientService.getPatientIdentifierTypeByName(name);
	}

}
