package org.openmrs.module.mergepatientdata.api;

import java.io.File;

import org.openmrs.module.mergepatientdata.sync.MergeAbleBatchRepo;

public interface MergePatientDataEncryptionService {
	
	public void serialize(MergeAbleBatchRepo repo);
	
	public void encrypt(String key, File inputFile, File outputFile);
	
	public void deserialize();
	
	public void decrypt(String key, File inputFile, File outputFile);
	
	public void doCryptography(int cipherMode, File input, File output, String key);
}
