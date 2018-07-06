package org.openmrs.module.mergepatientdata.api;

import org.openmrs.module.mergepatientdata.api.model.audit.MergePatientDataAuditMessage;
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;

public interface MergePatientDataAuditService {
	
	public MergePatientDataAuditMessage saveAuditMessage(PaginatedAuditMessage message);
	
	public MergePatientDataAuditMessage getMessageByUuid(String uuid);
	
	public String getAuditMessages(int page, int pageSize);
	
}
