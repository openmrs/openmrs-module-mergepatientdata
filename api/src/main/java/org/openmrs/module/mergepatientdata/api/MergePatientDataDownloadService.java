package org.openmrs.module.mergepatientdata.api;

import org.openmrs.module.mergepatientdata.api.model.audit.MergePatientDataAuditMessage;

public interface MergePatientDataDownloadService {
	
	/*
	 * This method downloads the {@Link MergeAbleResource} data
	 */
	public MergePatientDataAuditMessage downloadMergeAblePatientData();
	
}
