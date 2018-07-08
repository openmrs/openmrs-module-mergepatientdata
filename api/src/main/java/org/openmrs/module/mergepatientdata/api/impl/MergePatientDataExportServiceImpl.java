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
import org.openmrs.module.mergepatientdata.api.utils.ObjectUtils;
import org.openmrs.module.mergepatientdata.enums.MergeAbleDataCategory;
import org.openmrs.module.mergepatientdata.enums.Status;
import org.openmrs.module.mergepatientdata.resource.Patient;
import org.openmrs.module.mergepatientdata.sync.MPDStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MergePatientDataExportServiceImpl implements MergePatientDataExportService {
	
	private final Logger log = LoggerFactory.getLogger(MergePatientDataExportServiceImpl.class);
	
	MPDStore store = new MPDStore();
	
	MergePatientDataAuditService auditService;
	
	MergePatientDataEncryptionService encryptionService = new MergePatientDataEncryptionServiceImpl();
	
	@SuppressWarnings("unchecked")
	@Override
	public File exportMergeAblePatientData(List<Class> resourceClassesToExport, PaginatedAuditMessage auditor, String thisInstanceId) {
		log.debug("starting to export data from: " + thisInstanceId);
		store.setOriginId(thisInstanceId);
		auditor.setOrigin(MergePatientDataConstants.THIS_INSTANCE_NAME + "(" + thisInstanceId + ")");
		for (Class resource : resourceClassesToExport) {
			HashMap<String, Integer> resourceMapCounter = null;
			if (resource.isAssignableFrom(Patient.class)) {
				store.addType(MergeAbleDataCategory.PATIENT);
				PatientResourceService patientResourceService = new PatientResourceServiceImpl();
				Set<org.openmrs.Patient> openmrsPatients = new HashSet(patientResourceService.getAllPatients());
				
				try {
					resourceMapCounter = resourceMapCounter == null ? new HashMap<>() : resourceMapCounter;
					List<Patient> patients = (List<Patient>) ObjectUtils
					        .getMPDResourceObjectsFromOpenmrsResourceObjects(openmrsPatients);
					store.setPatients(patients);
					resourceMapCounter.put("Patient", patients.size());
					auditor.setResourceCount(resourceMapCounter);
				} catch (MPDException e) {
					log.error(e.getMessage());
					auditor.getFailureDetails().add(e.getMessage());
				}
				continue; //Just continue
			}	
			//TODO :- Cater for other Resources
		}
		if (store.hastData()) {
			File serializedData = encryptionService.serialize(store);
			return encryptionService.encrypt(serializedData, auditor);	
		} else {
			//Required data not found
			auditor.getFailureDetails().add("Required data not found!");
			auditor.setStatus(Status.Failure);
		}
		auditService = Context.getService(MergePatientDataAuditService.class);
		auditService.saveAuditMessage(auditor);
		return null;
	}
}
