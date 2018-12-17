package org.openmrs.module.mergepatientdata.api;

import java.io.File;

import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.sync.MPDStore;

public interface MergePatientDataEncryptionService {
	
	public File serialize(MPDStore store);
	
	public File encrypt(File inputFile, PaginatedAuditMessage auditor, Integer batch);
	
	public MPDStore deserialize(File inputFile);
	
	public File decrypt(File inputFile, PaginatedAuditMessage auditor);
	
}
