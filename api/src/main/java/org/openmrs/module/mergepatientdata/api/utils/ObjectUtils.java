package org.openmrs.module.mergepatientdata.api.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.OpenmrsObject;
import org.openmrs.module.mergepatientdata.MergePatientDataConstants;
import org.openmrs.module.mergepatientdata.api.exceptions.MPDException;
import org.openmrs.module.mergepatientdata.resource.ConceptAnswer;
import org.openmrs.module.mergepatientdata.resource.ConceptAttribute;
import org.openmrs.module.mergepatientdata.resource.ConceptDescription;
import org.openmrs.module.mergepatientdata.resource.ConceptMap;
import org.openmrs.module.mergepatientdata.resource.ConceptName;
import org.openmrs.module.mergepatientdata.resource.ConceptNameTag;
import org.openmrs.module.mergepatientdata.resource.ConceptSet;
import org.openmrs.module.mergepatientdata.resource.Encounter;
import org.openmrs.module.mergepatientdata.resource.Identifier;
import org.openmrs.module.mergepatientdata.resource.Location;
import org.openmrs.module.mergepatientdata.resource.LocationTag;
import org.openmrs.module.mergepatientdata.resource.MergeAbleResource;
import org.openmrs.module.mergepatientdata.resource.Patient;
import org.openmrs.module.mergepatientdata.resource.PersonAddress;
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
	public static Collection<? extends MergeAbleResource> getMPDResourceObjectsFromOpenmrsResourceObjects(
	        Set<? extends OpenmrsObject> openmrsDataSet) throws MPDException {
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
					
				case MergePatientDataConstants.PERSON_ADDRESS_RESOURCE_NAME:
					return convertToMPDPersonAddress(openmrsResourceObjects);
					
				case MergePatientDataConstants.PATIENT_IDENTIFIER_RESOURCE_NAME:
					return convertToMPDPatientIdentifier(openmrsResourceObjects);
					
				case MergePatientDataConstants.LOCATION_TAG_RESOURCE_NAME:
					return convertToMPDLocationTag(openmrsResourceObjects);
					
				case MergePatientDataConstants.CONCEPTNAME_RESOURCE_NAME:
					return convertToMPDConcepName(openmrsResourceObjects);
					
				case MergePatientDataConstants.CONCEPTANSWER_RESOURCE_NAME:
					return convertToMpdConceptAnswer(openmrsResourceObjects);
					
				case MergePatientDataConstants.CONCEPTDESCRIPTION_RESOURCE_NAME:
					return convertToMpdConceptDesc(openmrsResourceObjects);
					
				case MergePatientDataConstants.CONCEPTSET_RESOURCE_NAME:
					return convertToMpdConceptSet(openmrsResourceObjects);
					
				case MergePatientDataConstants.CONCEPTATTR_RESOURCE_NAME:
					return convertToMpdConceptAttr(openmrsResourceObjects);
					
				case MergePatientDataConstants.CONCEPTMAP_RESOURCE_NAME:
					return convertToMpdConceptMap(openmrsResourceObjects);
					
				case MergePatientDataConstants.CONCEPTNAMETAG_RESOURCE_NAME:
					return convertToMpdConceptNameTag(openmrsResourceObjects);
					
				case MergePatientDataConstants.ENCOUNTER_RESOURCE_NAME:
					return convertToMpdEncounter(openmrsResourceObjects);
					
				default:
					throw new MPDException("UnSupported Type : " + clazz);
			}
			
		} else {
			log.warn("OpenmrsDataSet is null");
		}
		return null;
	}
	
	public static List<? extends OpenmrsObject> getOpenmrsResourceObjectsFromMPDResourceObjects(
	        List<? extends MergeAbleResource> mpdList) throws MPDException {
		if (mpdList != null) {
			String clazz = MergePatientDataUtils.getClassName(mpdList);
			switch (clazz) {
				case MergePatientDataConstants.PATIENT_RESOURCE_NAME:
					log.debug("Converting to MPD PatientResource");
					return covertToOpenmrsPatientObjects(mpdList);
					
				case MergePatientDataConstants.LOCATION_RESOURCE_NAME:
					log.debug("Converting to MPD LocationResource");
					return covertToOpenmrsLocationObjects(mpdList);
					
				case MergePatientDataConstants.ENCOUNTER_RESOURCE_NAME:
					return convertToOpenmrsEncounterObjects(mpdList);
				default:
					throw new MPDException("Un Supported Type : " + clazz);
			}
		} else {
			log.warn("mpdList is null");
		}
		
		return null;
	}
	
	private static List<org.openmrs.Location> covertToOpenmrsLocationObjects(List<? extends MergeAbleResource> mpdList) {
		List<org.openmrs.Location> openmrsLocations = new ArrayList<>();	
		for (MergeAbleResource resourceObject : mpdList) {
			Location location = (Location) resourceObject;
			org.openmrs.Location openmrsPatient = (org.openmrs.Location) location.getOpenMrsObject();
			openmrsLocations.add(openmrsPatient);
		}
		return openmrsLocations;
	}
	
	private static List<? extends OpenmrsObject> convertToOpenmrsEncounterObjects(
			List<? extends MergeAbleResource> mpdList) {
		List<org.openmrs.Encounter> openmrsEncounterObjects = new ArrayList<>();
		for (MergeAbleResource resourceObject : mpdList) {
			Encounter enc = (Encounter) resourceObject;
			org.openmrs.Encounter encounter = (org.openmrs.Encounter) enc.getOpenMrsObject();
			openmrsEncounterObjects.add(encounter);
		}
		return openmrsEncounterObjects;
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
					Location location = new Location(openmrsLocation, true);
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
	
	private static List<? extends MergeAbleResource> convertToMPDPersonAddress(
	        List<? extends OpenmrsObject> openmrsResourceObjects) {
		
		List<PersonAddress> personAddresses = new ArrayList<PersonAddress>();
		
		for (OpenmrsObject personAddress : openmrsResourceObjects) {
			if (org.openmrs.PersonAddress.class.isAssignableFrom(personAddress.getClass())) {
				org.openmrs.PersonAddress openmrsAddress = (org.openmrs.PersonAddress) personAddress;
				PersonAddress address = new PersonAddress(openmrsAddress);
				personAddresses.add(address);
			}
		}
		
		return personAddresses;
	}
	
	private static List<? extends MergeAbleResource> convertToMPDPatientIdentifier(
	        List<? extends OpenmrsObject> openmrsResourceObjects) {
		
		List<Identifier> identifiers = new ArrayList<Identifier>();
		
		for (OpenmrsObject patientIdentifier : openmrsResourceObjects) {
			if (org.openmrs.PatientIdentifier.class.isAssignableFrom(patientIdentifier.getClass())) {
				org.openmrs.PatientIdentifier openmrsPatId = (org.openmrs.PatientIdentifier) patientIdentifier;
				Identifier id = new Identifier(openmrsPatId);
				identifiers.add(id);
			}
		}
		return identifiers;
	}
	
	private static List<? extends MergeAbleResource> convertToMPDConcepName(
			List<? extends OpenmrsObject> openmrsResourceObjects) {
		List<ConceptName> names = new ArrayList<>();
		for (OpenmrsObject name : openmrsResourceObjects) {
			if (org.openmrs.ConceptName.class.isAssignableFrom(name.getClass())) {
				org.openmrs.ConceptName ocn = (org.openmrs.ConceptName) name;
				ConceptName mcn = new ConceptName(ocn);
				names.add(mcn);
			}
		}
		return names;
	}
	
	private static Collection<? extends MergeAbleResource> convertToMpdConceptAnswer(
			List<? extends OpenmrsObject> openmrsResourceObjects) {
		List<ConceptAnswer> result = new ArrayList<>();
		for (OpenmrsObject ans : openmrsResourceObjects) {
			org.openmrs.ConceptAnswer oAns = (org.openmrs.ConceptAnswer) ans;
			ConceptAnswer mAns = new ConceptAnswer(oAns);
			result.add(mAns);
		}
		return result;
	}
	
	private static Collection<? extends MergeAbleResource> convertToMpdConceptSet(
			List<? extends OpenmrsObject> openmrsResourceObjects) {
		List<ConceptSet> result = new ArrayList<>();
		for (OpenmrsObject set : openmrsResourceObjects) {
			org.openmrs.ConceptSet oSet = (org.openmrs.ConceptSet) set;
			ConceptSet mSet = new ConceptSet(oSet);
			result.add(mSet);
		}
		return result;
	}
	
	private static Collection<? extends MergeAbleResource> convertToMpdConceptAttr(
			List<? extends OpenmrsObject> openmrsResourceObjects) {
		List<ConceptAttribute> result = new ArrayList<>();
		for (OpenmrsObject att : openmrsResourceObjects) {
			org.openmrs.ConceptAttribute oAtt = (org.openmrs.ConceptAttribute) att;
			ConceptAttribute mAtt = new ConceptAttribute(oAtt);
			result.add(mAtt);
		}
		return result;
	}
	
	private static Collection<? extends MergeAbleResource> convertToMpdConceptMap(
			List<? extends OpenmrsObject> openmrsResourceObjects) {
		Set<ConceptMap> result = new HashSet<>();
		for (OpenmrsObject map : openmrsResourceObjects) {
			org.openmrs.ConceptMap oMap = (org.openmrs.ConceptMap) map;
			ConceptMap mMap = new ConceptMap(oMap);
			result.add(mMap);
		}
		return result;
	}
	
	private static Collection<? extends MergeAbleResource> convertToMpdConceptNameTag(
			List<? extends OpenmrsObject> openmrsResourceObjects) {
		List<ConceptNameTag> result = new ArrayList<>();
		for (OpenmrsObject tag : openmrsResourceObjects) {
			org.openmrs.ConceptNameTag oTag = (org.openmrs.ConceptNameTag) tag;
			ConceptNameTag mTag = new ConceptNameTag(oTag);
			result.add(mTag);
		}
		return result;
	}
	
	private static Collection<? extends MergeAbleResource> convertToMpdConceptDesc(
			List<? extends OpenmrsObject> openmrsResourceObjects) {
		List<ConceptDescription> descriptions = new ArrayList<>();
		for (OpenmrsObject description : openmrsResourceObjects) {
			org.openmrs.ConceptDescription desc = (org.openmrs.ConceptDescription) description;
			ConceptDescription mpdDesc = new ConceptDescription(desc);
			descriptions.add(mpdDesc);
		}
		return descriptions;
	}
	
	private static Collection<? extends MergeAbleResource> convertToMpdEncounter(
			List<? extends OpenmrsObject> openmrsResourceObjects) {
		List<Encounter> encounters = new ArrayList<>();
		for (OpenmrsObject encounter : openmrsResourceObjects) {
			org.openmrs.Encounter enc = (org.openmrs.Encounter) encounter;
			Encounter mpdEnc = new Encounter(enc, true);
			encounters.add(mpdEnc);
		}
		return encounters;
	}
	
	private static List<? extends MergeAbleResource> convertToMPDLocationTag(
	        List<? extends OpenmrsObject> openmrsResourceObjects) {
		List<LocationTag> tags = new ArrayList<LocationTag>();
		
		for (OpenmrsObject locTag : openmrsResourceObjects) {
			if (org.openmrs.LocationTag.class.isAssignableFrom(locTag.getClass())) {
				org.openmrs.LocationTag tag = (org.openmrs.LocationTag) locTag;
				LocationTag mpdTag = new LocationTag(tag);
				tags.add(mpdTag);
			}
		}
		return tags;
	}
	
	/**
	 * Checks whether a given Resource type is enabled
	 * 
	 * @param resourceType
	 * @param resourceTypesToImport
	 * @return
	 */
	public static boolean typeRequired(Class resourceType, List<Class> resourceTypesToImport) {
		boolean typeRequired = false;
		for (Class type : resourceTypesToImport) {
			if (type.isAssignableFrom(resourceType)) {
				typeRequired = true;
			}
		}
		return typeRequired;
	}
	
	public static void addItemsToListWithoutDuplication(List<Encounter> encounterStoreList, List<Encounter> sourceOfItems) {
		if (encounterStoreList == null || sourceOfItems == null) {
			return;
		}
		List<Encounter> filteredEncounterList = new ArrayList<>();
		outerLoop : for (Encounter candidate : sourceOfItems) {
			if (filteredEncounterList.isEmpty()) {
				filteredEncounterList.add(candidate);
			} else {
				// Check whether this Item isn't a duplicate
				for (Encounter presentEnc : filteredEncounterList) {
					if (presentEnc.getUuid().equals(candidate.getUuid())) {
						continue outerLoop;
					}
				}
				// Could be its not a duplicate
				filteredEncounterList.add(candidate);
			}
		}
		encounterStoreList.addAll(filteredEncounterList);
	}
}
