package org.openmrs.module.mergepatientdata.api.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.openmrs.OpenmrsObject;
import org.openmrs.module.mergepatientdata.MergePatientDataConstants;
import org.openmrs.module.mergepatientdata.enums.MergeAbleDataCategory;
import org.openmrs.module.mergepatientdata.resource.Identifier;
import org.openmrs.module.mergepatientdata.resource.Location;
import org.openmrs.module.mergepatientdata.resource.MergeAbleResource;
import org.openmrs.module.mergepatientdata.resource.Patient;
import org.openmrs.module.mergepatientdata.resource.PersonName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectUtils {
	
	private final static Logger log = LoggerFactory.getLogger(ObjectUtils.class);
	
	/**
	 * Converts {@link OpenmrsObject}s to {@link MergeAbleResource} {@link Object}s
	 * 
	 * @param openmrsDataSet set of data to convert
	 * @return {@link MergeAbleResource}s
	 */
	public static List<? extends MergeAbleResource> getMPDResourceObjectsFromOpenmrsResourceObjects(
	        Set<? extends OpenmrsObject> openmrsDataSet) {
		
		if (openmrsDataSet != null) {
			log.info("Starting to convert..");
			List<? extends OpenmrsObject> openmrsResourceObjects = (List<? extends OpenmrsObject>) openmrsDataSet.stream()
			        .collect(Collectors.toList());
			String clazz = MergePatientDataUtils.getClassName(openmrsResourceObjects);
			switch (clazz) {
				case MergePatientDataConstants.PATIENT_RESOURCE_NAME:
					log.info("Converting to MPD PatientResource");
					return convertToMPDPatient(openmrsResourceObjects);
					
				case MergePatientDataConstants.LOCATION_RESOURCE_NAME:
					log.info("Converting to MPD LocationResource");
					return convertToMPDLocation(openmrsResourceObjects);
					
				case MergePatientDataConstants.PERSON_NAME_RESOURCE_NAME:
					return convertToMPDPersonName(openmrsResourceObjects);
					
				default:
					//TODO Should throw a MergePatientDataUnknownTypeExption
					return null;
			}
			
		} else {
			//TOD 
			log.warn("OpenmrsDataSet is null");
		}
		return null;
	}
	
	public static List<? extends OpenmrsObject> getOpenmrsResourceObjectsFromMPDResourceObjects(
	        List<? extends MergeAbleResource> mpdList) {
		if (mpdList != null) {
			String clazz = MergePatientDataUtils.getClassName(mpdList);
			for (Object patient : mpdList) {
				System.out.println("Object Class Name from loop: " + patient.getClass().getName());
			}
			System.out.println("Resource Class Name: " + clazz);
			switch (clazz) {
				case MergePatientDataConstants.PATIENT_RESOURCE_NAME:
					log.info("Converting to MPD PatientResource");
					return covertToOpenmrsPatientObjects(mpdList);
					
				case MergePatientDataConstants.LOCATION_RESOURCE_NAME:
					log.info("Converting to MPD LocationResource");
					return covertToOpenmrsLocationObjects(mpdList);
					
				default:
					//TODO Should throw a MergePatientDataUnknownTypeExption
					return null;
			}
		} else {
			log.warn("mpdList is null");
		}
		
		return null;
	}
	
	private static List<org.openmrs.Location> covertToOpenmrsLocationObjects(List<? extends MergeAbleResource> mpdList) {
		
		List<org.openmrs.Location> openmrsLocations = new ArrayList<org.openmrs.Location>();
		
		for (MergeAbleResource resourceObject : mpdList) {
			Location location = (Location) resourceObject;
			org.openmrs.Location openmrsPatient = (org.openmrs.Location) location.getOpenMrsObject();
			openmrsLocations.add(openmrsPatient);
		}
		return openmrsLocations;
	}
	
	private static List<org.openmrs.Patient> covertToOpenmrsPatientObjects(List<? extends MergeAbleResource> mpdList) {
		List<org.openmrs.Patient> openmrsPatients = new ArrayList<org.openmrs.Patient>();
		
		for (MergeAbleResource resourceObject : mpdList) {
			Patient patient = (Patient) resourceObject;
			org.openmrs.Patient openmrsPatient = (org.openmrs.Patient) patient.getOpenMrsObject();
			openmrsPatients.add(openmrsPatient);
		}
		
		return openmrsPatients;
	}
	
	public static List<Patient> convertToMPDPatient(List<? extends OpenmrsObject> MPDList) {
		List<Patient> patients = new ArrayList<Patient>();
		if (MPDList != null) {
			for (OpenmrsObject pat : MPDList) {
				if (org.openmrs.Patient.class.isAssignableFrom(pat.getClass())) {
					org.openmrs.Patient openmrsPatient = (org.openmrs.Patient) pat;
					Patient MPDPatient = new Patient(openmrsPatient);
					patients.add(MPDPatient);
				}
			}
		} else {
			log.warn("MPDList is null");
		}
		return patients;
	}
	
	public static List<Location> convertToMPDLocation(List<? extends OpenmrsObject> MPDList) {
		List<Location> locations = new ArrayList<Location>();
		
		if (MPDList != null) {
			for (OpenmrsObject loc : MPDList) {
				if (org.openmrs.Location.class.isAssignableFrom(loc.getClass())) {
					org.openmrs.Location openmrsLocation = (org.openmrs.Location) loc;
					Location location = new Location(openmrsLocation);
					locations.add(location);
				}
			}
		} else {
			log.warn("MPDList is null");
		}
		return locations;
	}
	
	public static List<PersonName> convertToMPDPersonName(List<? extends OpenmrsObject> MPDList) {
		List<PersonName> personNames = new ArrayList<PersonName>();
		
		if (MPDList != null) {
			for (OpenmrsObject name : MPDList) {
				if (org.openmrs.PersonName.class.isAssignableFrom(name.getClass())) {
					org.openmrs.PersonName openmrsName = (org.openmrs.PersonName) name;
					PersonName MPDPersonName = new PersonName(openmrsName);
					personNames.add(MPDPersonName);
				}
			}
		} else {
			log.warn("MPDList is null");
		}
		return personNames;
	}
	
	public static List<Identifier> convertToMPDIdentifier(List<? extends OpenmrsObject> MPDList) {
		List<Identifier> patientIdentifiers = new ArrayList<Identifier>();
		if (MPDList != null) {
			for (OpenmrsObject id : MPDList) {
				if (org.openmrs.PatientIdentifier.class.isAssignableFrom(id.getClass())) {
					org.openmrs.PatientIdentifier openmrsId = (org.openmrs.PatientIdentifier) id;
					Identifier MPDPatientIdentifier = new Identifier(openmrsId);
					patientIdentifiers.add(MPDPatientIdentifier);
				}
			}
		} else {
			log.warn("MPDList is null");
		}
		return patientIdentifiers;
	}
	
	/**
	 * Checks whether a given Resource type is enabled
	 * 
	 * @param resourceType
	 * @param resourceTypesToImport
	 * @return
	 */
	public static boolean typeRequired(Class resourceType, List<Class> resourceTypesToImport) {
		boolean required = false;
		for (Class type : resourceTypesToImport) {
			if (type.isAssignableFrom(resourceType)) {
				required = true;
			}
		}
		return required;
	}
	
}
