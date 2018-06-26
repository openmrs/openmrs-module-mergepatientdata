package org.openmrs.module.mergepatientdata.api;

import java.io.File;
import java.util.List;

import org.openmrs.api.OpenmrsService;

public interface MergePatientDataImportService extends OpenmrsService {
	
	public void importMPD(List<Class> classesToImport, File encryptedFile);
	
}
