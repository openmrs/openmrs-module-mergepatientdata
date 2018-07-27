package org.openmrs.module.mergepatientdata.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.resource.Encounter;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class EncounterTest extends BaseModuleContextSensitiveTest {
	
	private org.openmrs.Encounter openmrsEncounter;
	
	@Before
	public void setup() {
		openmrsEncounter = Context.getEncounterService().getEncounter(3);
	}
	
	@Test
	public void constructorForEncounter_shouldConstructCopyOfEncounter() {
		Encounter mpdEncounter = new Encounter(openmrsEncounter, true);
		Assert.assertNotNull(mpdEncounter);
		Assert.assertEquals(openmrsEncounter.getUuid(), mpdEncounter.getUuid());
		Assert.assertEquals(openmrsEncounter.getObs().size(), mpdEncounter.getObs().size());
		Assert.assertEquals(openmrsEncounter.getEncounterDatetime(), mpdEncounter.getEncounterDatetime());
		Assert.assertEquals(openmrsEncounter.getEncounterType().getUuid(), mpdEncounter.getEncounterType().getUuid());
		Assert.assertEquals(openmrsEncounter.getForm().getUuid(), mpdEncounter.getForm().getUuid());
	}
	
}
