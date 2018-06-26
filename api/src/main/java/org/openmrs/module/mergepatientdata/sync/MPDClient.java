package org.openmrs.module.mergepatientdata.sync;

import java.io.File;
import java.util.List;

import org.openmrs.module.mergepatientdata.MergePatientDataConstants;
import org.openmrs.module.mergepatientdata.api.MergePatientDataExportService;
import org.openmrs.module.mergepatientdata.api.MergePatientDataImportService;
import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataExportServiceImpl;
import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataImportServiceImpl;
import org.openmrs.module.mergepatientdata.api.model.config.MPDConfiguration;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataUtils;
import org.openmrs.module.mergepatientdata.resource.MergeAbleResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MPDClient {
	
	private final Logger log = LoggerFactory.getLogger(MPDClient.class);
	
	private List<MergeAbleResource> supportedClasses;
	
	MergePatientDataExportService exportService;
	
	MergePatientDataImportService importService;
	
	public MPDClient() {
	}
	
	public File exportData(MPDConfiguration configuration) {
		log.info("Started exporting process");
		List<Class> resourceClassesToExport = MergePatientDataUtils.getRequiredTypesToMerge(configuration,
		    MergePatientDataConstants.EXPORT_GENERAL_NAME);
		exportService = new MergePatientDataExportServiceImpl();
		return exportService.exportMergeAblePatientData(resourceClassesToExport);
	}
	
	public void importData(MPDConfiguration configuration, File encryptedFile) {
		log.info("Starting importing process");
		List<Class> resourceClassesToImport = MergePatientDataUtils.getRequiredTypesToMerge(configuration,
		    MergePatientDataConstants.IMPORT_GENERAL_NAME);
		importService = new MergePatientDataImportServiceImpl();
		importService.importMPD(resourceClassesToImport, encryptedFile);
		
	}
	
	public List<MergeAbleResource> getSupportedClasses() {
		return supportedClasses;
	}
	
	@Autowired
	public void setSupportedClasses(List<MergeAbleResource> supportedClasses) {
		this.supportedClasses = supportedClasses;
	}
	
	public void setExportService(MergePatientDataExportService exportService) {
		this.exportService = exportService;
	}
	
}
