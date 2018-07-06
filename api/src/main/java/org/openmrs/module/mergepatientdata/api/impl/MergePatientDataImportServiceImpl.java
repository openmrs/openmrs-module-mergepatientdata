package org.openmrs.module.mergepatientdata.api.impl;

import java.io.File;
import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.api.MergePatientDataAuditService;
import org.openmrs.module.mergepatientdata.api.MergePatientDataEncryptionService;
import org.openmrs.module.mergepatientdata.api.MergePatientDataImportService;
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataUtils;
import org.openmrs.module.mergepatientdata.enums.MergeAbleDataCategory;
import org.openmrs.module.mergepatientdata.enums.Status;
import org.openmrs.module.mergepatientdata.sync.MPDStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MergePatientDataImportServiceImpl implements MergePatientDataImportService {
	
	private final Logger log = LoggerFactory.getLogger(MergePatientDataImportServiceImpl.class);
	
	MergePatientDataEncryptionService encryptionService;
	
	MergePatientDataAuditService auditService;
	
	@Override
	public void importMPD(List<Class> resourceTypesToImport, File fileToImportFrom, PaginatedAuditMessage auditor) {
		log.debug("Starting import..");
		encryptionService = new MergePatientDataEncryptionServiceImpl();
		File serializedFile = encryptionService.decrypt(fileToImportFrom, auditor);
		MPDStore mergeableData = encryptionService.deserialize(serializedFile);
		auditor.setOrigin(mergeableData.getOriginId());
		log.debug("Importing data from " + mergeableData.getOriginId());
		List<MergeAbleDataCategory> types = mergeableData.getTypes();
		for (MergeAbleDataCategory type : types) {
			MergePatientDataUtils.mergeResourceToOpenmrsDataBase(mergeableData, type, resourceTypesToImport, auditor);
		}
		if (!auditor.isHasErrors()) {
			auditor.setStatus(Status.Success);
		} else {
			auditor.setStatus(Status.Failure);
		}
		auditService = Context.getService(MergePatientDataAuditService.class);
		auditService.saveAuditMessage(auditor);
	}
	
}
