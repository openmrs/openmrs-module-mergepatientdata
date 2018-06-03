package org.openmrs.module.mergepatientdata.api.impl;

import java.io.File;

import org.openmrs.module.mergepatientdata.MergePatientDataConctants;
import org.openmrs.module.mergepatientdata.api.MergePatientDataEncryptionService;
import org.openmrs.module.mergepatientdata.sync.MergeAbleBatchRepo;
import org.openmrs.util.OpenmrsUtil;

import com.google.gson.Gson;

public class MergePatientDataEncryptionServiceImpl implements MergePatientDataEncryptionService {
	
	@Override
	public void serialize(MergeAbleBatchRepo repo) {
		String filePath = getSerializedFilePath();
		Gson gson = new Gson();
		byte[] bytes;
	}
	
	@Override
	public void encrypt(String key, File inputFile, File outputFile) {
		
	}
	
	@Override
	public void deserialize() {
		
	}
	
	@Override
	public void decrypt(String key, File inputFile, File outputFile) {
		
	}
	
	@Override
	public void doCryptography(int cipherMode, File input, File output, String key) {
		
	}
	
	private String getSerializedFilePath() {
		File mpdFileFolder = OpenmrsUtil.getDirectoryInApplicationDataDirectory(MergePatientDataConctants.MPD_DIR);
		return new File(mpdFileFolder, MergePatientDataConctants.MPD_SERIALIZED_FILE_NAME).getAbsolutePath();
	}
}
