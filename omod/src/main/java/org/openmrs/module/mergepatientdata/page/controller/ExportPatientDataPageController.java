package org.openmrs.module.mergepatientdata.page.controller;

import javax.servlet.http.HttpSession;

import org.openmrs.module.mergepatientdata.api.MergePatientDataConfigurationService;
import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataConfigurationServiceImpl;
import org.openmrs.module.mergepatientdata.sync.MPDClient;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.stereotype.Controller;

@Controller
public class ExportPatientDataPageController {
	
	public String controller(PageModel model, @SpringBean("mergepatientdata.MPDClient") MPDClient client,
	        HttpSession session, UiUtils ui) {
		MergePatientDataConfigurationService configService = new MergePatientDataConfigurationServiceImpl();
		client.exportData(configService.getMPDConfiguration());
		return "redirect:/mergepatientdata/mergepatientdata.page";
	}
}
