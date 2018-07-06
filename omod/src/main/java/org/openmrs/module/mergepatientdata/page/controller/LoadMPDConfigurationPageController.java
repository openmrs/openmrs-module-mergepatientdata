package org.openmrs.module.mergepatientdata.page.controller;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.mergepatientdata.api.MergePatientDataConfigurationService;
import org.openmrs.module.mergepatientdata.api.exceptions.MPDException;
import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataConfigurationServiceImpl;
import org.openmrs.module.mergepatientdata.api.model.config.MPDConfiguration;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataConfigurationUtils;
import org.openmrs.module.uicommons.util.InfoErrorMessageUtil;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class LoadMPDConfigurationPageController {
	
	public static final String SAVE_CONFIG_SUCCESS = "mergepatientdata.configuration.save.success.label";
	
	public static final String SAVE_CONFIG_FAILURE = "mergepatientdata.configuration.save.error.label";
	
	@Autowired
	@Qualifier("messageSourceService")
	private MessageSourceService messageSourceService;
	
	MergePatientDataConfigurationService configService = new MergePatientDataConfigurationServiceImpl();
	
	public void controller(PageModel model) {
		String json = MergePatientDataConfigurationUtils.writeMPDConfigToJsonString(configService.getMPDConfiguration());
		model.addAttribute("configuration", json);
		model.addAttribute("importStatus", "");
	}
	
	public String post(PageModel model, @RequestParam("json") String json, HttpSession session, UiUtils ui) {
		boolean wasSaved = configService.saveConfiguration(json);
		if (wasSaved) {
			InfoErrorMessageUtil.flashInfoMessage(session, ui.message(SAVE_CONFIG_SUCCESS));
		} else {
			InfoErrorMessageUtil.flashErrorMessage(session, ui.message(SAVE_CONFIG_FAILURE));
			;
		}
		return "redirect:mergepatientdata/LoadMPDConfiguration.page";
	}
	
	@ResponseBody
	@RequestMapping("mergepatientdata/verifyJson")
	public SimpleObject verifyJson(@RequestParam("json") String json) {
		SimpleObject result = new SimpleObject();
		boolean isValid = MergePatientDataConfigurationUtils.isValidJson(json);
		result.put("isValid", isValid);
		return result;
	}
	
	@RequestMapping(value = "/mergepatientdata/ImportMPDConfigurationFile", method = RequestMethod.POST)
	public String importSyncConfiguration(@RequestParam("file") MultipartFile file, ModelMap model) throws MPDException,
	        IOException {
		StringWriter writer = null;
		try {
			writer = new StringWriter();
			IOUtils.copy(file.getInputStream(), writer, "UTF-8");
			
			MPDConfiguration mpdConfiguration = MergePatientDataConfigurationUtils.parseJsonStringToMpdConfiguration(writer
			        .toString());
			configService.saveConfiguration(mpdConfiguration);
			model.addAttribute("importStatus", "");
			return "redirect:/mergepatientdata/mergepatientdata.page";
		}
		catch (Exception e) {
			model.addAttribute("importStatus",
			    messageSourceService.getMessage("mergepatientdata.configuration.errors.invalidFile"));
		}
		finally {
			IOUtils.closeQuietly(writer);
		}
		
		return "redirect:/mergepatientdata/LoadMPDConfiguration.page";
	}
	
}
