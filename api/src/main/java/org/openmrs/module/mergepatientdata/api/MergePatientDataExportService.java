package org.openmrs.module.mergepatientdata.api;

import java.io.File;
import java.util.List;

import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.resource.MergeAbleResource;

public interface MergePatientDataExportService {
	
	/*
	 * This method uploads the {@Link MergeAbleResource} data
	 */
	public File exportMergeAblePatientData(List<Class> resourceClassesToExport, PaginatedAuditMessage auditor,
	        String thisInstanceId);
	
}
