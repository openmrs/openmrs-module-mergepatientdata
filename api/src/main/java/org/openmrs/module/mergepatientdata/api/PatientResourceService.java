package org.openmrs.module.mergepatientdata.api;

import java.util.List;

import org.openmrs.PatientIdentifierType;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.mergepatientdata.resource.Patient;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PatientResourceService extends OpenmrsService {
	
	public org.openmrs.Patient savePatient(org.openmrs.Patient patient) throws APIException;
	
	public void savePatients(List<Patient> patients);
	
	public List<org.openmrs.Patient> getAllPatients() throws APIException;
	
	public List<org.openmrs.Patient> getAllPatients(boolean includeVoided) throws APIException;
	
	public List<org.openmrs.Patient> getPatients(String name, String identifier,
	        List<PatientIdentifierType> identifierTypes, boolean matchIdentifierExactly) throws APIException;
	
}
