package org.openmrs.module.mergepatientdata.sync;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.openmrs.module.mergepatientdata.Resource;
import org.openmrs.module.mergepatientdata.enums.MergeAbleDataCategory;
import org.openmrs.module.mergepatientdata.resource.MergeAbleResource;

public class MergeAbleBatchRepo extends LinkedHashMap<MergeAbleDataCategory, ArrayList<Resource>> {
	
	private static final long serialVersionUID = 1L;
	
	public MergeAbleBatchRepo() {
		super();
	}
	
	public MergeAbleBatchRepo add(MergeAbleDataCategory key, ArrayList<Resource> value) {
		put(key, value);
		return this;
	}
	
}
