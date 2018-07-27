package org.openmrs.module.mergepatientdata.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.resource.Obs;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import com.google.gson.Gson;

public class ObsTest extends BaseModuleContextSensitiveTest {
	
	org.openmrs.Obs openmrsObs;
	
	@Before
	public void setup() {
		openmrsObs = Context.getObsService().getObs(9);
	}
	
	@Test
	public void constructorForObs_shouldMakeCopyOfOpenmrsObs() {
		Obs obs = new Obs(openmrsObs, true);
		Assert.assertNotNull(obs);
		Assert.assertEquals(openmrsObs.getUuid(), obs.getUuid());
		Assert.assertEquals(openmrsObs.getAccessionNumber(), obs.getAccessionNumber());
		Assert.assertEquals(openmrsObs.getComment(), obs.getComment());
		Assert.assertEquals(openmrsObs.getValueComplex(), obs.getValueComplex());
		if (openmrsObs.getValueCoded() != null) {
			Assert.assertEquals(openmrsObs.getValueCoded().getUuid(), obs.getValueCoded().getUuid());
		}
	}
	
}
