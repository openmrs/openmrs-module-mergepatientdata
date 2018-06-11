package org.openmrs.module.mergepatientdata.api.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.api.MergePatientDataEncryptionService;
import org.openmrs.module.mergepatientdata.api.MergePatientDataExportService;
import org.openmrs.module.mergepatientdata.api.PatientResourceService;
import org.openmrs.module.mergepatientdata.api.utils.ObjectUtils;
import org.openmrs.module.mergepatientdata.enums.MergeAbleDataCategory;
import org.openmrs.module.mergepatientdata.resource.MergeAbleResource;
import org.openmrs.module.mergepatientdata.resource.Patient;
import org.openmrs.module.mergepatientdata.sync.MergeAbleBatchRepo;

public class MergePatientDataExportServiceImpl implements MergePatientDataExportService {
	
	MergeAbleBatchRepo repo = new MergeAbleBatchRepo();
	
	MergePatientDataEncryptionService encryptionService = new MergePatientDataEncryptionServiceImpl();
	
	@Override
	public MergeAbleBatchRepo exportMergeAblePatientData(List<Class> resourceClassesToExport) {
		for (Class resource : resourceClassesToExport) {
			if (resource.isAssignableFrom(Patient.class)) {
				PatientResourceService patientResourceService = Context.getService(PatientResourceService.class);
				Set<org.openmrs.Patient> openmrsPatients = new HashSet(patientResourceService.getAllPatients());
				ArrayList<Patient> patients = (ArrayList<Patient>) ObjectUtils.getMPDObject(openmrsPatients);
				repo.add(MergeAbleDataCategory.PATIENT, patients);
				File serializedData = encryptionService.serialize(repo);
				//encryptionService.encrypt(serializedData, outputFile);
			}
		}
		return repo;
	}
	
}
