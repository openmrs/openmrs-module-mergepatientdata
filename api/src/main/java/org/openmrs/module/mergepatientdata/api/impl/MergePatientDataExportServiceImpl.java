package org.openmrs.module.mergepatientdata.api.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.MergePatientDataConstants;
import org.openmrs.module.mergepatientdata.api.MergePatientDataAuditService;
import org.openmrs.module.mergepatientdata.api.MergePatientDataEncryptionService;
import org.openmrs.module.mergepatientdata.api.MergePatientDataExportService;
import org.openmrs.module.mergepatientdata.api.PatientResourceService;
import org.openmrs.module.mergepatientdata.api.exceptions.MPDException;
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataEncryptionUtils;
import org.openmrs.module.mergepatientdata.api.utils.ObjectUtils;
import org.openmrs.module.mergepatientdata.enums.MergeAbleDataCategory;
import org.openmrs.module.mergepatientdata.enums.Status;
import org.openmrs.module.mergepatientdata.resource.Encounter;
import org.openmrs.module.mergepatientdata.resource.Patient;
import org.openmrs.module.mergepatientdata.sync.MPDStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MergePatientDataExportServiceImpl implements MergePatientDataExportService {
	
	private final Logger log = LoggerFactory.getLogger(MergePatientDataExportServiceImpl.class);
	
	MPDStore store = new MPDStore();
	
	MergePatientDataAuditService auditService;
	
	MergePatientDataEncryptionService encryptionService = new MergePatientDataEncryptionServiceImpl();
	
	@SuppressWarnings("unchecked")
	@Override
	public File exportMergeAblePatientData(List<Class> resourceClassesToExport, PaginatedAuditMessage auditor, String thisInstanceId) {
		int counter = 0;
		int batchCounter = 0;
		log.debug("starting to export data from: " + thisInstanceId);
		store.setOriginId(thisInstanceId);
		auditor.setOrigin(MergePatientDataConstants.THIS_INSTANCE_NAME + "(" + thisInstanceId + ")");
		// setup working dir
		if (!new File(MergePatientDataEncryptionUtils.getMpdBatchDirPath()).exists()) {
			MergePatientDataEncryptionUtils.makeMpdBatchDir();
		}
		// it no longer makes sense iterating over these resources
		// TODO remove this
		for (Class resource : resourceClassesToExport) {
			if (resource.isAssignableFrom(Patient.class)) {
				store.addType(MergeAbleDataCategory.PATIENT);
				PatientResourceService patientResourceService = new PatientResourceServiceImpl();
				Set<org.openmrs.Patient> openmrsPatients = new HashSet<>();
				for (org.openmrs.Patient patient : patientResourceService.getAllPatients(false)) {
					if (counter == 200) {
						batchCounter++;
						// pause iteration, save the batch
						processPatientBatchForExport(openmrsPatients, auditor);
						processEncountersForExport(auditor, resourceClassesToExport);
						processAndSaveBatchToFile(auditor, batchCounter);
						// empty list
						openmrsPatients.clear();
						counter = 0;
					}
					openmrsPatients.add(patient);
					counter++;
				}
				// if openmrsPatients list isn't empty at this point, it means 
				// some data that doesn't reach a batch threshold isn't yet saved
				// to a file.
				// then save it too.
				if (!openmrsPatients.isEmpty()) {
					processPatientBatchForExport(openmrsPatients, auditor);
					processEncountersForExport(auditor, resourceClassesToExport);
					processAndSaveBatchToFile(auditor, batchCounter);
				}
			} 
		}
		HashMap<String, Integer> resourceCounter = new HashMap<String, Integer>();
		if (store.getEncounters() != null) {
			resourceCounter.put("Encounter", store.getEncounters().size());
		}
		if (store.getPatients() != null) {
			resourceCounter.put("Patient", store.getPatients().size());
		}
		auditor.setResourceCount(resourceCounter);
		auditService = Context.getService(MergePatientDataAuditService.class);
		auditService.saveAuditMessage(auditor);
		return MergePatientDataEncryptionUtils.processMpdZippedData();
	}
	
	/**
	 * Convenient method for processing {@link Encounter}s for export
	 * 
	 * @param resource
	 * @param auditor
	 */
	@SuppressWarnings("unchecked")
	private void processEncountersForExport(PaginatedAuditMessage auditor, List<Class> resourceClassesToExport) {
		if (resourceClassesToExport.contains(Encounter.class)) {
			store.addType(MergeAbleDataCategory.ENCOUNTER);
			auditor.getResources().add(Encounter.class.getSimpleName());
			// Check whether we have Patients
			if (store.getPatients() != null) {
				store.setEncounters(new ArrayList<>());
				List<Patient> patientsWhoseEncountersAreRequired = store.getPatients();
				List<Encounter> encounterCandidates = new ArrayList<>();
				for (Patient pat : patientsWhoseEncountersAreRequired) {
					List<org.openmrs.Encounter> encounters = Context.getEncounterService().
							getEncountersByPatientIdentifier(pat.getPatientIdentifier().getIdentifier());
					try {
						if (encounters != null) {
							List<Encounter> mpdEncounters = (List<Encounter>) ObjectUtils.
									getMPDResourceObjectsFromOpenmrsResourceObjects(new HashSet<>(encounters));
							for (Encounter enc : mpdEncounters) {
								encounterCandidates.add(enc);
							}
						}
					} catch (MPDException e) {
						log.error(e.getMessage());
						auditor.getFailureDetails().add(e.getMessage());
					}
				}
				// Add Encounters but not including duplicates
				ObjectUtils.addItemsToListWithoutDuplication(store.getEncounters(), encounterCandidates);
			} else {
				auditor.getFailureDetails().add("Patient Resource Required to Export Encounter Resource");
				auditor.setStatus(Status.Failure);
			}
		}
		
	}
	
	/**
	 * Convenient method for Processing {@link Patient}s for export in a batch
	 * 
	 * @param openmrsPatients
	 * @param auditor
	 */
	@SuppressWarnings("unchecked")
	private void processPatientBatchForExport(Set<org.openmrs.Patient> openmrsPatients, PaginatedAuditMessage auditor) {
		try {
			// Get Patients in batches
			List<Patient> patients = (List<Patient>) ObjectUtils
			        .getMPDResourceObjectsFromOpenmrsResourceObjects(openmrsPatients);
			store.setPatients(patients);
		}
		catch (MPDException e) {
			log.error(e.getMessage());
			auditor.getFailureDetails().add(e.getMessage());
		}
		
	}
	
	/**
	 * Encrypts available batch and store it in a file
	 * 
	 * @param batch this the batch index
	 * @param auditor
	 */
	private void processAndSaveBatchToFile(PaginatedAuditMessage auditor, Integer batch) {
		if (store.hastData()) {
			File serializedData = encryptionService.serialize(store);
			encryptionService.encrypt(serializedData, auditor, batch);
		} else {
			// Required data not found
			auditor.getFailureDetails().add("Required data not found!");
			auditor.setStatus(Status.Failure);
		}
	}
	
}
