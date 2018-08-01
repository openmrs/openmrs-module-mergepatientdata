package org.openmrs.module.mergepatientdata.api;

import java.util.List;

import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.resource.Encounter;

public interface EncounterResourceService {
	
	public org.openmrs.Encounter saveEncounter(org.openmrs.Encounter encounter, PaginatedAuditMessage auditor);
	
	public void saveEncounters(List<Encounter> encounters, PaginatedAuditMessage auditor);
}
