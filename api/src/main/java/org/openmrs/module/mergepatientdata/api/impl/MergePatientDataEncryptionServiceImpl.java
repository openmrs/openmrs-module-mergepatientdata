package org.openmrs.module.mergepatientdata.api.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.openmrs.module.mergepatientdata.MergePatientDataConstants;
import org.openmrs.module.mergepatientdata.api.MergePatientDataEncryptionService;
import org.openmrs.module.mergepatientdata.sync.MergeAbleBatchRepo;
import org.openmrs.util.OpenmrsUtil;

import com.google.gson.Gson;

public class MergePatientDataEncryptionServiceImpl implements MergePatientDataEncryptionService {
	
	@Override
	public File serialize(MergeAbleBatchRepo repo) {
		String filePath = getSerializedFilePath();
		Gson gson = new Gson();
		byte[] bytes = gson.toJson(repo).getBytes();
		System.out.println("Hey there");
		File inputfile = new File(filePath);
		
		System.out.println(filePath);
		
		try {
			
			FileWriter writer = new FileWriter(inputfile);
			BufferedWriter bwriter = new BufferedWriter(writer);
			
			for (int i = 0; i < bytes.length; i++) {
				bwriter.write(bytes[i]);
			}
			bwriter.close();
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return inputfile;
	}
	
	@Override
	public void encrypt(File inputFile, File outputFile) {
		doCryptography(Cipher.ENCRYPT_MODE, inputFile, outputFile, MergePatientDataConstants.KEY);
	}
	
	@Override
	public void deserialize() {
		
	}
	
	@Override
	public void decrypt(File inputFile, File outputFile) {
		doCryptography(Cipher.DECRYPT_MODE, inputFile, outputFile, MergePatientDataConstants.KEY);
	}
	
	public static void doCryptography(int cipherMode, File input, File output, String key) {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
		Key secretKey = new SecretKeySpec(key.getBytes(), MergePatientDataConstants.ALGORITHM);
		Cipher cipher = null;
	    byte[] inputBytes = null;
	    byte[] outputBytes = null;
		try {
			cipher = Cipher.getInstance(MergePatientDataConstants.TRANSFORMATION);
			cipher.init(cipherMode, secretKey);
			inputStream = new FileInputStream(input);
			inputBytes = new byte[(int) input.length()];
			inputStream.read(inputBytes);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException e) {
			
		} 
		try {
			outputBytes = cipher.doFinal(inputBytes);
			outputStream = new FileOutputStream(output);
			outputStream.write(outputBytes);
			inputStream.close();
			outputStream.close();
		} catch (IllegalBlockSizeException | BadPaddingException | IOException e) {
			
		}
	}
	
	private String getSerializedFilePath() {
		File mpdFileFolder = OpenmrsUtil.getDirectoryInApplicationDataDirectory(MergePatientDataConstants.MPD_DIR);
		return new File(mpdFileFolder, MergePatientDataConstants.MPD_SERIALIZED_FILE_NAME).getAbsolutePath();
	}
}
