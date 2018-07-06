package org.openmrs.module.mergepatientdata.page.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.api.MergePatientDataAuditService;
import org.openmrs.module.mergepatientdata.api.model.audit.MergePatientDataAuditMessage;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

public class AuditDetailsPageController {
	
	private static final String AUDI_FAILURE_NAME = "Failure";
	
	private boolean hasErrors = false;
	
	MergePatientDataAuditService service;
	
	public void controller(PageModel model, @RequestParam(value = "messageUuid", required = true) String uuid,
	        @RequestParam(value = "backPage", required = true) String previousPage,
	        @RequestParam(value = "backPageIndex", required = true) String previuosPageIndex) {
		Gson gson = new Gson();
		service = Context.getService(MergePatientDataAuditService.class);
		MergePatientDataAuditMessage auditMessage = service.getMessageByUuid(uuid);
		model.addAttribute("audit", auditMessage);
		if (auditMessage.getResources() != null && !auditMessage.getResources().isEmpty()) {
			String jsonResourcesObject = auditMessage.getResources();
			List<String> resources = gson.fromJson(jsonResourcesObject, ArrayList.class);
			model.addAttribute("resources", resources);
		}
		if (auditMessage.getStatus().equals(AUDI_FAILURE_NAME)) {
			hasErrors = true;
			String jsonErrorLogs = auditMessage.getFailureDetails();
			List<String> logs = gson.fromJson(jsonErrorLogs, ArrayList.class);
			model.addAttribute("errorLogs", logs);
		}
		
		if (auditMessage.getJsonResourceCounterObject() != null) {
			Map<String, Integer> resourceCounter = gson.fromJson(auditMessage.getJsonResourceCounterObject(), HashMap.class);
			model.addAttribute("resourceCounter", resourceCounter);
		} else {
			model.addAttribute("resourceCounter", null);
		}
		
		model.addAttribute("hasErrors", hasErrors);
	}
	
}
