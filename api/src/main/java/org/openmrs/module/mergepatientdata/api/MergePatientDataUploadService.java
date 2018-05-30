package org.openmrs.module.mergepatientdata.api;

import java.io.File;

import org.openmrs.module.mergepatientdata.api.model.audit.AuditMessage;

public interface MergePatientDataUploadService {
	
	/*
	 * This method uploads the {@Link MergeAbleResource} data
	 */
	public AuditMessage upLoadMergeAblePatientData(File mpdfile);
	
}
