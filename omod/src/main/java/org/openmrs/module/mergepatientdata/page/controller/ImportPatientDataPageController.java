package org.openmrs.module.mergepatientdata.page.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.openmrs.module.mergepatientdata.api.MergePatientDataConfigurationService;
import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataConfigurationServiceImpl;
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataConfigurationUtils;
import org.openmrs.module.mergepatientdata.enums.Status;
import org.openmrs.module.mergepatientdata.sync.MPDClient;
import org.openmrs.module.uicommons.util.InfoErrorMessageUtil;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class ImportPatientDataPageController {
	
	private final Logger log = LoggerFactory.getLogger(ImportPatientDataPageController.class);
	
	private static final String OPERATION_FAILURE = "mergepatientdata.refApp.operation.error.label";
	
	private static final String OPERATION_SUCCESS = "mergepatientdata.refApp.operation.success.label";
	
	public String controller(@SpringBean("mpdcient") MPDClient client, @RequestParam("file") CommonsMultipartFile file,
	        HttpSession session, PageModel model, UiUtils ui) {
		PaginatedAuditMessage auditor = null;
		MergePatientDataConfigurationService configService = new MergePatientDataConfigurationServiceImpl();
		
		File encryptedFile = new File(MergePatientDataConfigurationUtils.getMPDWorkingDir(), file.getOriginalFilename());
		byte byteArray[] = file.getBytes();
		try {
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(encryptedFile));
			outputStream.write(byteArray);
			outputStream.flush();
			outputStream.close();
		}
		catch (IOException e) {
			log.error(e.getMessage());
		}
		
		try {
			auditor = client.importData(configService.getMPDConfiguration(), new File(encryptedFile.getAbsolutePath()));
		}
		catch (Exception e) {
			log.error(e.getMessage());
			InfoErrorMessageUtil.flashErrorMessage(session, ui.message(OPERATION_FAILURE));
		}
		if (auditor != null) {
			if (!auditor.isHasErrors() && auditor.getStatus().equals(Status.Success)) {
				InfoErrorMessageUtil.flashInfoMessage(session, ui.message(OPERATION_SUCCESS));
			}
		} else {
			InfoErrorMessageUtil.flashErrorMessage(session, ui.message(OPERATION_FAILURE));
		}
		return "redirect:/mergepatientdata/mergepatientdata.page";
	}
	
}
