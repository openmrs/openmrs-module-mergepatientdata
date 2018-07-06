package org.openmrs.module.mergepatientdata.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.api.MergePatientDataAuditService;
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.enums.Operation;
import org.openmrs.module.mergepatientdata.enums.Status;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class MergepatientdataAuditServiceTest extends BaseModuleContextSensitiveTest {
	
	public MergePatientDataAuditService service;
	
	public List<String> resources;
	
	public List<String> errorDetails;
	
	@Before
	public void setup() {
		service = Context.getService(MergePatientDataAuditService.class);
		resources = new ArrayList<>();
		resources.add("Patient");
		errorDetails = new ArrayList<>();
		errorDetails.add("Failed to Merge Patient with identifier: 1000K, identifier already taken");
		errorDetails.add("Failed to Merge Patient with identifier: 1000V, identifier already taken");
	}
	
	@Test
	public void saveAuditMessage_shouldSaveMessage() {
		PaginatedAuditMessage message = new PaginatedAuditMessage();
		message.setOperation(Operation.EXPORT);
		message.setStatus(Status.Failure);
		message.setResources(resources);
		message.setFailureDetails(errorDetails);
		Assert.assertNotNull(service.saveAuditMessage(message));
	}
}
