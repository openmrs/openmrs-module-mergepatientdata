package org.openmrs.module.mergepatientdata.sync;

import java.util.List;

import org.openmrs.module.mergepatientdata.MergePatientDataConstants;
import org.openmrs.module.mergepatientdata.api.MergePatientDataExportService;
import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataExportServiceImpl;
import org.openmrs.module.mergepatientdata.api.model.config.MPDConfiguration;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataUtils;
import org.openmrs.module.mergepatientdata.resource.MergeAbleResource;
import org.springframework.beans.factory.annotation.Autowired;

public class MPDClient {
	
	private List<MergeAbleResource> supportedClasses;
	
	MergePatientDataExportService exportService;
	
	public MPDClient() {	
	}
	
	public void exportData(MPDConfiguration configuration) {
		List<Class> resourceClassesToExport = MergePatientDataUtils.getRequiredTypesToMerge(
		    configuration, MergePatientDataConstants.EXPORT_GENERAL_NAME);
		exportService = new MergePatientDataExportServiceImpl();
		exportService.exportMergeAblePatientData(resourceClassesToExport);
	}
	
	public void importData() {
		
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
