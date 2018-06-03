package org.openmrs.module.mergepatientdata.api.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.openmrs.OpenmrsObject;
import org.openmrs.module.mergepatientdata.MergePatientDataConctants;
import org.openmrs.module.mergepatientdata.resource.Identifier;
import org.openmrs.module.mergepatientdata.resource.Location;
import org.openmrs.module.mergepatientdata.resource.MergeAbleResource;
import org.openmrs.module.mergepatientdata.resource.Patient;
import org.openmrs.module.mergepatientdata.resource.PersonName;

public class ObjectUtils {
	
	public static List<? extends MergeAbleResource> getMPDObject(Set<? extends Object> openmrsDataSet) {
		List<? extends MergeAbleResource> mergeAbleObjects = null;
		if (openmrsDataSet != null) {
			mergeAbleObjects = new ArrayList<MergeAbleResource>();
			List<? extends OpenmrsObject> MPDList = (List<? extends OpenmrsObject>) openmrsDataSet.stream().collect(
			    Collectors.toList());
			String clazz = MergePatientDataUtils.getClassName(MPDList);
			switch (clazz) {
				case MergePatientDataConctants.PATIENT_RESOURCE_NAME:
					return convertToMPDPatient(MPDList);
					
				case MergePatientDataConctants.LOCATION_RESOURCE_NAME:
					return convertToMPDLocation(MPDList);
					
				case MergePatientDataConctants.PERSON_NAME_RESOURCE_NAME:
					return convertToMPDPersonName(MPDList);
				default: //TODO Should throw a MergePatientDataUnknownTypeExption
					return null;
			}
			
		}
		return null;
	}
	
	public static List<Patient> convertToMPDPatient(List<? extends OpenmrsObject> MPDList) {
		List<Patient> patients = new ArrayList<Patient>();
		for (OpenmrsObject pat : MPDList) {
			if (org.openmrs.Patient.class.isAssignableFrom(pat.getClass())) {
				org.openmrs.Patient openmrsPatient = (org.openmrs.Patient) pat;
				Patient MPDPatient = new Patient(openmrsPatient);
				patients.add(MPDPatient);
			}
		}
		return patients;
	}
	
	public static List<Location> convertToMPDLocation(List<? extends OpenmrsObject> MPDList) {
		List<Location> locations = new ArrayList<Location>();
		for (OpenmrsObject loc : MPDList) {
			if (org.openmrs.Location.class.isAssignableFrom(loc.getClass())) {
				org.openmrs.Location openmrsLocation = (org.openmrs.Location) loc;
				Location location = new Location(openmrsLocation);
				locations.add(location);
			}
		}
		return locations;
	}
	
	public static List<PersonName> convertToMPDPersonName(List<? extends OpenmrsObject> MPDList) {
		List<PersonName> personNames = new ArrayList<PersonName>();
		for (OpenmrsObject name : MPDList) {
			if (org.openmrs.PersonName.class.isAssignableFrom(name.getClass())) {
				org.openmrs.PersonName openmrsName = (org.openmrs.PersonName) name;
				PersonName MPDPersonName = new PersonName(openmrsName);
				personNames.add(MPDPersonName);
			}
		}
		return personNames;
	}
	
	public static List<Identifier> convertToMPDIdentifier(List<? extends OpenmrsObject> MPDList) {
		List<Identifier> patientIdentifiers = new ArrayList<Identifier>();
		for (OpenmrsObject id : MPDList) {
			if (org.openmrs.PatientIdentifier.class.isAssignableFrom(id.getClass())) {
				org.openmrs.PatientIdentifier openmrsId = (org.openmrs.PatientIdentifier) id;
				Identifier MPDPatientIdentifier = new Identifier(openmrsId);
				patientIdentifiers.add(MPDPatientIdentifier);
			}
		}
		return patientIdentifiers;
	}
}
