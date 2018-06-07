package org.openmrs.module.mergepatientdata.api;

import java.io.File;

import org.openmrs.module.mergepatientdata.sync.MergeAbleBatchRepo;

public interface MergePatientDataEncryptionService {
	
	public File serialize(MergeAbleBatchRepo repo);
	
	public void encrypt(File inputFile, File outputFile);
	
	public void deserialize();
	
	public void decrypt(File inputFile, File outputFile);
	
}
