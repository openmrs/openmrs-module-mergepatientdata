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
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class ExportPatientDataPageController {
	
	private final Logger log = LoggerFactory.getLogger(ExportPatientDataPageController.class);
	
	public String controller(@SpringBean("mpdcient") MPDClient client, HttpServletResponse response) {
		MergePatientDataConfigurationServiceImpl configService = new MergePatientDataConfigurationServiceImpl();
		configService.generateConfiguration();
		File encryptedFile = client.exportData(configService.getMPDConfiguration());
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
		}
		catch (IOException e) {
			log.error(e.getMessage());
		}
		return "redirect:/mergepatientdata/mergepatientdata.page";
	}
}
