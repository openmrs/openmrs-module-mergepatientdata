package org.openmrs.module.mergepatientdata.api.impl;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.openmrs.module.mergepatientdata.api.MergePatientDataEncryptionService;
import org.openmrs.module.mergepatientdata.api.MergePatientDataImportService;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataUtils;
import org.openmrs.module.mergepatientdata.enums.MergeAbleDataCategory;
import org.openmrs.module.mergepatientdata.sync.MergeAbleBatchRepo;

public class MergePatientDataImportServiceImpl implements MergePatientDataImportService {
	
	MergePatientDataEncryptionService encryptionService;
	
	@Override
	public void importMPD(List<Class> resourceTypesToImport, File fileToImportFrom) {
		encryptionService = new MergePatientDataEncryptionServiceImpl();
		File serializedFile = encryptionService.decrypt(fileToImportFrom);
		MergeAbleBatchRepo mergeableData = encryptionService.deserialize(serializedFile);
		System.out.println("MergeAbleBatchRepo : " + mergeableData);
		Set<MergeAbleDataCategory> types = mergeableData.keySet();
		for (Object type : types) {
			MergeAbleDataCategory resourceType = (MergeAbleDataCategory) type;
			MergePatientDataUtils.mergeResourceToOpenmrsDataBase(mergeableData, resourceType, resourceTypesToImport);
		}
	}
	
	@Override
	public void onStartup() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onShutdown() {
		// TODO Auto-generated method stub
		
	}
	
}
