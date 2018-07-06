package org.openmrs.module.mergepatientdata.page.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataConfigurationServiceImpl;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataConfigurationUtils;
import org.openmrs.module.mergepatientdata.sync.MPDClient;
import org.openmrs.module.uicommons.util.InfoErrorMessageUtil;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExportPatientDataPageController {
	
	private final Logger log = LoggerFactory.getLogger(ExportPatientDataPageController.class);
	
	private static final String OPERATION_SUCCESS = "mergepatientdata.refApp.operation.success.label";
	
	public String controller(@SpringBean("mpdcient") MPDClient client, HttpServletResponse response, HttpSession session,
	        UiUtils ui) {
		MergePatientDataConfigurationServiceImpl configService = new MergePatientDataConfigurationServiceImpl();
		configService.generateConfiguration();
		File encryptedFile = client.exportData(configService.getMPDConfiguration());
		
		if (encryptedFile != null) {
			byte inputData[] = null;
			try {
				FileInputStream inputStream = new FileInputStream(encryptedFile);
				
				//Tell browser about the download
				response.setContentType("APPLICATION/OCTET-STREAM");
				response.setHeader("Content-Disposition", "attachment; filename=" + encryptedFile.getName());
				ServletOutputStream outPutStream = response.getOutputStream();
				Path path = Paths.get(encryptedFile.getAbsolutePath());
				inputData = Files.readAllBytes(path);
				outPutStream.write(inputData);
				outPutStream.close();
				inputStream.close();
				MergePatientDataConfigurationUtils.cleanMPDWorkDir();
				InfoErrorMessageUtil.flashInfoMessage(session, ui.message(OPERATION_SUCCESS));
			}
			catch (IOException e) {
				log.error(e.getMessage());
			}
		} else {
			//Means a serious internal error occurred, couldn't finish Operation
		}
		InfoErrorMessageUtil.flashInfoMessage(session, ui.message(OPERATION_SUCCESS));
		
		return "redirect:/mergepatientdata/mergepatientdata.page";
	}
	
}
