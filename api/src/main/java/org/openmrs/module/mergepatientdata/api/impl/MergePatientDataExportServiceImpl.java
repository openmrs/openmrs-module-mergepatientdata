package org.openmrs.module.mergepatientdata.api.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.Resource;
import org.openmrs.module.mergepatientdata.api.MergePatientDataEncryptionService;
import org.openmrs.module.mergepatientdata.api.MergePatientDataExportService;
import org.openmrs.module.mergepatientdata.api.PatientResourceService;
import org.openmrs.module.mergepatientdata.api.utils.ObjectUtils;
import org.openmrs.module.mergepatientdata.enums.MergeAbleDataCategory;
import org.openmrs.module.mergepatientdata.resource.MergeAbleResource;
import org.openmrs.module.mergepatientdata.resource.Patient;
import org.openmrs.module.mergepatientdata.sync.MergeAbleBatchRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MergePatientDataExportServiceImpl implements MergePatientDataExportService {
	
	private final Logger log = LoggerFactory.getLogger(MergePatientDataExportServiceImpl.class);
	
	MergeAbleBatchRepo repo = new MergeAbleBatchRepo();
	
	MergePatientDataEncryptionService encryptionService = new MergePatientDataEncryptionServiceImpl();
	
	@SuppressWarnings("unchecked")
	@Override
	public File exportMergeAblePatientData(List<Class> resourceClassesToExport) {
		log.info("starting to export data");
		for (Class resource : resourceClassesToExport) {
			
			if (resource.isAssignableFrom(Patient.class)) {
				PatientResourceService patientResourceService = new PatientResourceServiceImpl();
				Set<org.openmrs.Patient> openmrsPatients = new HashSet(patientResourceService.getAllPatients());
				log.info("Retrieving patients");
				ArrayList<Resource> patients = (ArrayList<Resource>) ObjectUtils
				        .getMPDResourceObjectsFromOpenmrsResourceObjects(openmrsPatients);
				log.info("Adding Patients to : {}", repo.getClass().getSimpleName());
				repo.add(MergeAbleDataCategory.PATIENT, patients);
				File serializedData = encryptionService.serialize(repo);
				return encryptionService.encrypt(serializedData);
			}
		}
		return null;
		
	}
	
}
