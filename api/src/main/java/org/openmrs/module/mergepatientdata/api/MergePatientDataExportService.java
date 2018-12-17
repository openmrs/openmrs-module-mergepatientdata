package org.openmrs.module.mergepatientdata.api;

import java.io.File;
import java.util.List;

import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.resource.MergeAbleResource;

public interface MergePatientDataExportService {
	
	/**
	 * Its where all the business logic for the export Operation is found.<br>
	 * data is exported in batches, the default <code>maxBatchSize</code> is 200.<br>
	 * batchSize is determined by number of {@link org.openmrs.Patient}s.
	 * 
	 * @param resourceClassesToExport {@link MergeAbleResource} types to partake in this operation
	 * @param auditor Auditor
	 * @param thisInstanceId server id
	 * @return zipped file containing all batches
	 */
	public File exportMergeAblePatientData(List<Class> resourceClassesToExport, PaginatedAuditMessage auditor,
	        String thisInstanceId);
	
}
