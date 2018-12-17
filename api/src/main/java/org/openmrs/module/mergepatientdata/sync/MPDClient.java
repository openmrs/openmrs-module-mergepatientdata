package org.openmrs.module.mergepatientdata.sync;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openmrs.module.mergepatientdata.MergePatientDataConstants;
import org.openmrs.module.mergepatientdata.api.MergePatientDataExportService;
import org.openmrs.module.mergepatientdata.api.MergePatientDataImportService;
import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataExportServiceImpl;
import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataImportServiceImpl;
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.api.model.config.MPDConfiguration;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataUtils;
import org.openmrs.module.mergepatientdata.enums.Operation;
import org.openmrs.module.mergepatientdata.resource.MergeAbleResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MPDClient {
	
	private final Logger log = LoggerFactory.getLogger(MPDClient.class);
	
	private List<MergeAbleResource> supportedClasses;
	
	MergePatientDataExportService exportService;
	
	MergePatientDataImportService importService;
	
	PaginatedAuditMessage auditor = new PaginatedAuditMessage();
	
	public MPDClient() {
	}
	
	public File exportData(MPDConfiguration configuration) {
		auditor.setResources(new ArrayList<>());
		auditor.setFailureDetails(new ArrayList<>());
		auditor.setOperation(Operation.EXPORT);
		log.info("Started exporting process");
		List<Class> resourceTypesToExport = MergePatientDataUtils.getRequiredTypesToMerge(configuration,
		    MergePatientDataConstants.EXPORT_GENERAL_NAME, auditor);
		exportService = new MergePatientDataExportServiceImpl();
		return exportService.exportMergeAblePatientData(resourceTypesToExport, auditor, 
				configuration.getGeneral().getLocalInstanceId());
	}
	
	public PaginatedAuditMessage importData(MPDConfiguration configuration, File zippedZile) {
		auditor.setResources(new ArrayList<>());
		auditor.setFailureDetails(new ArrayList<>());
		auditor.setOperation(Operation.IMPORT);
		log.info("Starting importing process");
		List<Class> resourceClassesToImport = MergePatientDataUtils.getRequiredTypesToMerge(configuration,
		    MergePatientDataConstants.IMPORT_GENERAL_NAME, auditor);
		importService = new MergePatientDataImportServiceImpl();
		importService.importMPD(resourceClassesToImport, zippedZile, auditor);
		return auditor;
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
