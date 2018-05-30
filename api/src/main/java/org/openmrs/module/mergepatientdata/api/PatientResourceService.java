package org.openmrs.module.mergepatientdata.api;

import java.util.List;

import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PatientResourceService extends OpenmrsService {
	
	public Patient savePatient(Patient patient) throws APIException;
	
	public Patient getPatient(Integer patientId) throws APIException;
	
	Patient getPatientOrPromotePerson(Integer patientOrPersonId) throws APIException;
	
	public PatientIdentifier getPatientIdentifierByUuid(String uuid) throws APIException;
	
	public List<Patient> getAllPatients() throws APIException;
	
	public List<Patient> getAllPatients(boolean includeVoided) throws APIException;
	
	public List<Patient> getPatients(String name, String identifier, List<PatientIdentifierType> identifierTypes,
	        boolean matchIdentifierExactly) throws APIException;
	
	public List<PatientIdentifier> getPatientIdentifiers(String identifier,
	        List<PatientIdentifierType> patientIdentifierTypes, List<Location> locations, List<Patient> patients,
	        Boolean isPreferred) throws APIException;
	
	public PatientIdentifierType savePatientIdentifierType(PatientIdentifierType patientIdentifierType) throws APIException;
	
	public List<PatientIdentifierType> getAllPatientIdentifierTypes() throws APIException;
	
	public List<PatientIdentifierType> getAllPatientIdentifierTypes(boolean includeRetired) throws APIException;
	
	public List<PatientIdentifierType> getPatientIdentifierTypes(String name, String format, Boolean required,
	        Boolean hasCheckDigit) throws APIException;
	
	public PatientIdentifierType getPatientIdentifierType(Integer patientIdentifierTypeId) throws APIException;
	
	public PatientIdentifierType getPatientIdentifierTypeByUuid(String uuid) throws APIException;
	
	public PatientIdentifierType getPatientIdentifierTypeByName(String name) throws APIException;
	
}
