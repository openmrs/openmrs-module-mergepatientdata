package org.openmrs.module.mergepatientdata.web.controller.rest;

import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.api.MergePatientDataAuditService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/rest/mpd", produces = MediaType.APPLICATION_JSON_VALUE)
public class MergePatientDataAuditRestController {
	
	private MergePatientDataAuditService auditService;
	
	@RequestMapping(value = "/messages", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getAuditMessageList(@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
	        @RequestParam(value = "pageSize", defaultValue = "100") int pageSize) {
		auditService = Context.getService(MergePatientDataAuditService.class);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
		        .body(auditService.getAuditMessages(pageIndex, pageSize));
		
	}
}
