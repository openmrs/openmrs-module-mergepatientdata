package org.openmrs.module.mergepatientdata.page.controller;

import java.io.File;
import javax.servlet.http.HttpSession;

import org.openmrs.module.mergepatientdata.api.MergePatientDataConfigurationService;
import org.openmrs.module.mergepatientdata.api.exceptions.MPDException;
import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataConfigurationServiceImpl;
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataConfigurationUtils;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataEncryptionUtils;
import org.openmrs.module.mergepatientdata.enums.Status;
import org.openmrs.module.mergepatientdata.sync.MPDClient;
import org.openmrs.module.uicommons.util.InfoErrorMessageUtil;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class ImportPatientDataFromFileSystemPageController {
	
	private PaginatedAuditMessage auditor = null;
	
	private final Logger log = LoggerFactory.getLogger(ImportPatientDataFromFileSystemPageController.class);
	
	private static final String OPERATION_FAILURE = "mergepatientdata.refApp.operation.error.label";
	
	private static final String OPERATION_SUCCESS = "mergepatientdata.refApp.operation.success.label";
	
	public String controller(@SpringBean("mpdcient") MPDClient client, HttpSession session, PageModel model, UiUtils ui) {
		MergePatientDataConfigurationService configService = new MergePatientDataConfigurationServiceImpl();
		File zippedDataFile = new File(MergePatientDataEncryptionUtils.getMpdZipFilePath());
		if (zippedDataFile.exists()) {
			client.importData(configService.getMPDConfiguration(), zippedDataFile);
		} else {
			// first do a sanity check through all the files
			for (File file : MergePatientDataConfigurationUtils.getMPDWorkingDir().listFiles()) {
				if (file == null || file.isDirectory()) {
					continue;
				}
				String fileName = file.getName();
				String extension = fileName.substring(fileName.indexOf("."), fileName.length());
				if (extension.equals(".zip") && fileName.startsWith("Data")) {
					client.importData(configService.getMPDConfiguration(), file);
					break;
				}
			}
			
		}
		try {
			auditor = client.importData(configService.getMPDConfiguration(), new File(zippedDataFile.getAbsolutePath()));
		}
		catch (MPDException e) {
			log.error(e.getMessage());
			InfoErrorMessageUtil.flashErrorMessage(session, ui.message(OPERATION_FAILURE));
		}
		
		if (auditor != null) {
			if (!auditor.isHasErrors() && auditor.getStatus().equals(Status.Success)) {
				InfoErrorMessageUtil.flashInfoMessage(session, ui.message(OPERATION_SUCCESS));
			} else {
				InfoErrorMessageUtil.flashErrorMessage(session, ui.message(OPERATION_FAILURE));
			}
		} else {
			InfoErrorMessageUtil.flashErrorMessage(session, ui.message(OPERATION_FAILURE));
		}
		return "redirect:/mergepatientdata/mergepatientdata.page";
	}
	
}
