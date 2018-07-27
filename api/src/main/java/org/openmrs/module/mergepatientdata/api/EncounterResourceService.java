package org.openmrs.module.mergepatientdata.api;

import java.util.List;

import org.openmrs.module.mergepatientdata.resource.Encounter;

public interface EncounterResourceService {
	
	public org.openmrs.Encounter saveEncounter(org.openmrs.Encounter encounter);
	
	public void saveEncounters(List<Encounter> encounters);
}
