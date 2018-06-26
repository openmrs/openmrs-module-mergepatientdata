package org.openmrs.module.mergepatientdata.api.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.openmrs.module.mergepatientdata.MergePatientDataConstants;
import org.openmrs.module.mergepatientdata.Resource;
import org.openmrs.module.mergepatientdata.api.MergePatientDataEncryptionService;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataConfigurationUtils;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataEncryptionUtils;
import org.openmrs.module.mergepatientdata.enums.MergeAbleDataCategory;
import org.openmrs.module.mergepatientdata.resource.MergeAbleResource;
import org.openmrs.module.mergepatientdata.sync.MergeAbleBatchRepo;
import org.openmrs.util.OpenmrsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MergePatientDataEncryptionServiceImpl implements MergePatientDataEncryptionService {
	
	private final Logger log = LoggerFactory.getLogger(MergePatientDataEncryptionServiceImpl.class);
	
	@Override
	public File serialize(MergeAbleBatchRepo repo) {
		String filePath = MergePatientDataEncryptionUtils.getSerializedFilePath();
		Gson gson = new Gson();
		log.info("Serializing to '" + filePath + "'");
		byte[] bytes = gson.toJson(repo).getBytes();
		File inputfile = new File(filePath);
		
		try {
			FileWriter writer = new FileWriter(inputfile);
			BufferedWriter bwriter = new BufferedWriter(writer);
			
			for (int i = 0; i < bytes.length; i++) {
				bwriter.write(bytes[i]);
			}
			log.info("serialized data");
			bwriter.close();
			writer.close();
		}
		catch (IOException e) {
			log.error(e.getMessage());
		}
		return inputfile;
	}
	
	@Override
	public MergeAbleBatchRepo deserialize(File file) {
		Gson gson = new Gson();
		MergeAbleBatchRepo repo = null;
		try {
			
			InputStream input = new FileInputStream(file);
			BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
			Type mergeAbleBatchRepoType =  new TypeToken<LinkedHashMap<MergeAbleDataCategory, Resource>>(){}.getType();
			repo = gson.fromJson(buffer, mergeAbleBatchRepoType);
			buffer.close();
			
		}
		catch (IOException e) {
			log.error(e.getMessage());
		}
		return repo;
		
	}
	
	@Override
	public File encrypt(File inputFile) {
		log.info("Encrypting data");
		String outputFilePath = MergePatientDataEncryptionUtils.getEncryptedMPDFilePath();
		File outputFile = new File(outputFilePath);
		doCryptography(Cipher.ENCRYPT_MODE, inputFile, outputFile);
		return outputFile;
	}
	
	@Override
	public File decrypt(File inputFile) {
		File outputFile = new File(MergePatientDataEncryptionUtils.getSerializedFilePath());
		doCryptography(Cipher.DECRYPT_MODE, inputFile, outputFile);
		return outputFile;
	}
	
	private void doCryptography(int cipherMode, File input, File output) {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        byte rawKey[] = { (byte) 0xA5, (byte) 0x01, (byte) 0x7B, (byte) 0xE5,
				(byte) 0x23, (byte) 0xCA, (byte) 0xD4, (byte) 0xD2,
				(byte) 0xC6, (byte) 0x5F, (byte) 0x7D, (byte) 0x8B,
				(byte) 0x0B, (byte) 0x9A, (byte) 0x3C, (byte) 0xF1 };
        
		Key secretKey = new SecretKeySpec(rawKey, MergePatientDataConstants.ALGORITHM);
		
		Cipher cipher = null;
	    byte[] data = null;
	    byte[] outputBytes = null;
	    
	    try {
			inputStream = new FileInputStream(input);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			//TODO: Try look through the mpd dir and edit the file name. Chances are high for the name to differ "name(2).mpd"
			//TODO: Clean the working dir
		}
		try {
			cipher = Cipher.getInstance(MergePatientDataConstants.TRANSFORMATION);
			cipher.init(cipherMode, secretKey);
			Path path = Paths.get(input.getAbsolutePath());
		    data = Files.readAllBytes(path);
			inputStream.read(data);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException e) {
			log.error(e.getMessage());
		} 
		try {
			if (data != null) {
				outputBytes = cipher.doFinal(data);
				outputStream = new FileOutputStream(output);
				outputStream.write(outputBytes);
				inputStream.close();
				outputStream.close();	
			} else {
				throw new NullPointerException();
			}
		} catch (IllegalBlockSizeException | BadPaddingException | IOException e) {
			log.error(e.getMessage());
		}
		System.out.println("file created at: " + output.getAbsolutePath());
	}
}
