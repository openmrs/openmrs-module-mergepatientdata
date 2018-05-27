package org.openmrs.module.mergepatientdata.api.impl;

import java.util.List;

import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.APIException;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.mergepatientdata.api.PatientResourceService;
import org.openmrs.module.mergepatientdata.api.dao.PatientResourceServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PatientResourceServiceImpl extends BaseOpenmrsService implements PatientResourceService {
	
	@Autowired
	PatientResourceServiceDao dao;
	

	@Override
	public Patient savePatient(Patient patient) throws APIException {
		return dao.savePatient(patient);
	}

	@Override
	public Patient getPatient(Integer patientId) throws APIException {
		
		return dao.getPatient(patientId);
	}

	@Override
	public Patient getPatientOrPromotePerson(Integer patientOrPersonId) throws APIException {
		
		return dao.getPatientOrPromotePerson(patientOrPersonId);
	}

	@Override
	public PatientIdentifier getPatientIdentifierByUuid(String uuid) throws APIException {
	
		return dao.getPatientIdentifierByUuid(uuid);
	}

	@Override
	public List<Patient> getAllPatients() throws APIException {
		
		return dao.getAllPatients();
	}

	@Override
	public List<Patient> getAllPatients(boolean includeVoided) throws APIException {
		
		return dao.getAllPatients(includeVoided);
	}

	@Override
	public List<Patient> getPatients(String name, String identifier, List<PatientIdentifierType> identifierTypes,
			boolean matchIdentifierExactly) throws APIException {
		
		return dao.getPatients(name, identifier, identifierTypes, matchIdentifierExactly);
	}

	@Override
	public List<PatientIdentifier> getPatientIdentifiers(String identifier,
			List<PatientIdentifierType> patientIdentifierTypes, List<Location> locations, List<Patient> patients,
			Boolean isPreferred) throws APIException {
		
		return dao.getPatientIdentifiers(identifier, patientIdentifierTypes, locations, patients, isPreferred);
	}

	@Override
	public PatientIdentifierType savePatientIdentifierType(PatientIdentifierType patientIdentifierType)
			throws APIException {
		
		return dao.savePatientIdentifierType(patientIdentifierType);
	}

	@Override
	public List<PatientIdentifierType> getAllPatientIdentifierTypes() throws APIException {
		
		return dao.getAllPatientIdentifierTypes();
	}

	@Override
	public List<PatientIdentifierType> getAllPatientIdentifierTypes(boolean includeRetired) throws APIException {
		
		return dao.getAllPatientIdentifierTypes(includeRetired);
	}

	@Override
	public List<PatientIdentifierType> getPatientIdentifierTypes(String name, String format, Boolean required,
			Boolean hasCheckDigit) throws APIException {
		
		return dao.getPatientIdentifierTypes(name, format, required, hasCheckDigit);
	}

	@Override
	public PatientIdentifierType getPatientIdentifierType(Integer patientIdentifierTypeId) throws APIException {
		
		return dao.getPatientIdentifierType(patientIdentifierTypeId);
	}

	@Override
	public PatientIdentifierType getPatientIdentifierTypeByUuid(String uuid) throws APIException {
		
		return dao.getPatientIdentifierTypeByUuid(uuid);
	}

	@Override
	public PatientIdentifierType getPatientIdentifierTypeByName(String name) throws APIException {
		
		return dao.getPatientIdentifierTypeByName(name);
	}

	public PatientResourceServiceDao getDao() {
		return dao;
	}

	public void setDao(PatientResourceServiceDao dao) {
		this.dao = dao;
	}

}
