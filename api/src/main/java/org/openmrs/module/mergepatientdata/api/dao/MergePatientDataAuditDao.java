package org.openmrs.module.mergepatientdata.api.dao;

import java.util.List;

import org.openmrs.module.mergepatientdata.api.model.audit.MergePatientDataAuditMessage;
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;

public interface MergePatientDataAuditDao {
	
	public MergePatientDataAuditMessage saveMessage(MergePatientDataAuditMessage message);
	
	public PaginatedAuditMessage getAuditMessages(int page, int pageSize);
	
	public MergePatientDataAuditMessage getMessageByUuid(String uuid);
	
}
