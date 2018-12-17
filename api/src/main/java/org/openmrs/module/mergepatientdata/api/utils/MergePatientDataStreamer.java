package org.openmrs.module.mergepatientdata.api.utils;

import org.openmrs.api.context.Context;

/**
 * Enables streaming of data in a given Operation It uses a pattern of dividing a large batch of
 * data and breaking it down to using smaller files.
 */
public class MergePatientDataStreamer {
	
	/**
	 * Determined by max number of <code>Patient</code>s in a batch.
	 */
	private final int maxBatchSize = 200;
	
	public double getAvailableBatches() {
		return Context.getPatientService().getAllPatients().size() / maxBatchSize;
	}
	
}
