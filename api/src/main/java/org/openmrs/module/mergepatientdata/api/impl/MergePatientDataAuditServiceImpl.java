package org.openmrs.module.mergepatientdata.api.impl;

import java.util.HashMap;
import java.util.List;

import org.openmrs.module.mergepatientdata.api.MergePatientDataAuditService;
import org.openmrs.module.mergepatientdata.api.dao.MergePatientDataAuditDao;
import org.openmrs.module.mergepatientdata.api.model.audit.MergePatientDataAuditMessage;
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.enums.Status;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Transactional
public class MergePatientDataAuditServiceImpl implements MergePatientDataAuditService {
	
	private MergePatientDataAuditDao dao;
	
	public void setDao(MergePatientDataAuditDao dao) {
		this.dao = dao;
	}
	
	@Override
	public MergePatientDataAuditMessage saveAuditMessage(PaginatedAuditMessage message) {
		List<String> resourceList = message.getResources();
		List<String> errorDetailsMap = message.getFailureDetails();
		HashMap<String, Integer> mapResourceCounter = message.getResourceCount();
		Gson gson = new Gson();
		String resources = gson.toJson(resourceList);
		String errorDetails = gson.toJson(errorDetailsMap);
		String jsonMapResourceCounter = null;
		if (mapResourceCounter != null) {
			jsonMapResourceCounter = gson.toJson(mapResourceCounter);
			
		}
		if (message.getStatus() == null) {
			//Operation completed unexpectedly
			message.setStatus(Status.Failure);
		}
		return dao.saveMessage(new MergePatientDataAuditMessage(message.getOperation().name(), resources, message
		        .getTimeStamp(), message.getOrigin(), message.getStatus().name(), errorDetails, jsonMapResourceCounter));
		
	}
	
	@Override
	public MergePatientDataAuditMessage getMessageByUuid(String uuid) {
		return dao.getMessageByUuid(uuid);
	}
	
	@Override
	public String getAuditMessages(int page, int pageSize) {
		PaginatedAuditMessage message = dao.getAuditMessages(page, pageSize);
		Gson gson = new GsonBuilder().registerTypeAdapter(PaginatedAuditMessage.class,
		    new PaginatedAuditMessage.AuditPageSerializationHandler()).create();
		return gson.toJson(message);
	}
	
}
