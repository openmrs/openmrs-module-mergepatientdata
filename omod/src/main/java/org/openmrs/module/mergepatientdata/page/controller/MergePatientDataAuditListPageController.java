package org.openmrs.module.mergepatientdata.page.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.api.MergePatientDataAuditService;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.stereotype.Controller;

@Controller
public class MergePatientDataAuditListPageController {
	
	public void controller(PageModel model) {
		//model.addAttribute("data", Context.getService(MergePatientDataAuditService.class).getAuditMessages());
	}
	
}
