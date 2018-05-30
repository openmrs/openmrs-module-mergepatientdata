package org.openmrs.module.mergepatientdata.api;

import org.openmrs.module.mergepatientdata.api.model.audit.AuditMessage;

public interface MergePatientDataDownloadService {
	
	/*
	 * This method downloads the {@Link MergeAbleResource} data
	 */
	public AuditMessage downloadMergeAblePatientData();
	
}
