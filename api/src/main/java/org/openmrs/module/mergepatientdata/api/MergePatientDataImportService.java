package org.openmrs.module.mergepatientdata.api;

import java.io.File;
import java.util.List;

import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;

public interface MergePatientDataImportService {
	
	public void importMPD(List<Class> classesToImport, File encryptedFile, PaginatedAuditMessage auditor);
	
}
