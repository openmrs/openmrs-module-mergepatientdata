package org.openmrs.module.mergepatientdata.api.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.api.MergePatientDataExportService;
import org.openmrs.module.mergepatientdata.api.PatientResourceService;
import org.openmrs.module.mergepatientdata.api.utils.ObjectUtils;
import org.openmrs.module.mergepatientdata.enums.MergeAbleDataCategory;
import org.openmrs.module.mergepatientdata.resource.MergeAbleResource;
import org.openmrs.module.mergepatientdata.resource.Patient;
import org.openmrs.module.mergepatientdata.sync.MergeAbleBatchRepo;

public class MergePatientDataExportServiceImpl implements MergePatientDataExportService {
	
	MergeAbleBatchRepo repo = new MergeAbleBatchRepo();
	
	@Override
	public MergeAbleBatchRepo exportMergeAblePatientData(List<? extends MergeAbleResource> resourceClassesToExport) {
		for (MergeAbleResource resource : resourceClassesToExport) {
			if (resource.getClass().isAssignableFrom(Patient.class)) {
				PatientResourceService patientResourceService = Context.getService(PatientResourceService.class);
				Set<org.openmrs.Patient> openmrsPatients = new HashSet(patientResourceService.getAllPatients());
				ArrayList<Patient> patients = (ArrayList<Patient>) ObjectUtils.getMPDObject(openmrsPatients);
				repo.add(MergeAbleDataCategory.PATIENT, patients);
			}
		}
		return repo;
	}
	
}
