package org.openmrs.module.mergepatientdata.page.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataConfigurationServiceImpl;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataConfigurationUtils;
import org.openmrs.module.mergepatientdata.sync.MPDClient;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class ImportPatientDataPageController {
	
	private final Logger log = LoggerFactory.getLogger(ImportPatientDataPageController.class);
	
	public String controller(@SpringBean("mpdcient") MPDClient client, @RequestParam("file") CommonsMultipartFile file,
	        HttpSession session) {
		MergePatientDataConfigurationServiceImpl configService = new MergePatientDataConfigurationServiceImpl();
		configService.generateConfiguration();
		
		File encryptedFile = new File(MergePatientDataConfigurationUtils.getMPDWorkingDir(), file.getOriginalFilename());
		if (!encryptedFile.exists()) {
			
		}
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
		client.importData(configService.getMPDConfiguration(), new File(encryptedFile.getAbsolutePath()));
		
		return "redirect:/mergepatientdata/mergepatientdata.page";
	}
	
}
