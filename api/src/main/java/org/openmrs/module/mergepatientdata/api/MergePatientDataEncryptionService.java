package org.openmrs.module.mergepatientdata.api;

import java.io.File;

import org.openmrs.module.mergepatientdata.sync.MergeAbleBatchRepo;

public interface MergePatientDataEncryptionService {
	
	public File serialize(MergeAbleBatchRepo repo);
	
	public File encrypt(File inputFile);
	
	public MergeAbleBatchRepo deserialize(File inputFile);
	
	public File decrypt(File inputFile);
	
}
