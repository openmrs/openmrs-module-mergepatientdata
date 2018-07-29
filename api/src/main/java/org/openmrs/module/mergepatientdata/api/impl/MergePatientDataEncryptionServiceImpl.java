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
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.MergePatientDataConstants;
import org.openmrs.module.mergepatientdata.api.MergePatientDataAuditService;
import org.openmrs.module.mergepatientdata.api.MergePatientDataEncryptionService;
import org.openmrs.module.mergepatientdata.api.exceptions.MPDException;
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataConfigurationUtils;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataEncryptionUtils;
import org.openmrs.module.mergepatientdata.enums.Status;
import org.openmrs.module.mergepatientdata.sync.MPDStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MergePatientDataEncryptionServiceImpl implements MergePatientDataEncryptionService {
	
	private final Logger log = LoggerFactory.getLogger(MergePatientDataEncryptionServiceImpl.class);
	
	private MergePatientDataAuditService auditService;
	
	@Override
	public File serialize(MPDStore store) {
		String filePath = MergePatientDataEncryptionUtils.getSerializedFilePath();
		log.info("Serializing to '" + filePath + "'");
		
		// Stop gson from storing bytes in runtime memory
		// This causes out of memory problems
		// TODO should resort something like streaming @see https://sites.google.com/site/gson/streaming
		//byte[] bytes = gson.toJson(store).getBytes();
		File inputfile = new File(filePath);
		
		try {
			FileWriter writer = new FileWriter(inputfile);
			//BufferedWriter bwriter = new BufferedWriter(writer);
			Gson gson = new GsonBuilder().create();
			gson.toJson(store, writer);
			
			writer.close();
		}
		catch (IOException e) {
			log.error(e.getMessage());
		}
		
		return inputfile;
	}
	
	@Override
	public MPDStore deserialize(File file) {
		Gson gson = new Gson();
		MPDStore store = null;
		try {
			
			InputStream input = new FileInputStream(file);
			BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
			store = gson.fromJson(buffer, MPDStore.class);
			buffer.close();
			
		}
		catch (IOException e) {
			log.error(e.getMessage());
		}
		finally {
			MergePatientDataConfigurationUtils.cleanMPDWorkDir();
		}
		return store;
	}
	
	@Override
	public File encrypt(File inputFile, PaginatedAuditMessage auditor) {
		log.info("Encrypting data");
		String outputFilePath = MergePatientDataEncryptionUtils.getEncryptedMPDFilePath();
		File outputFile = new File(outputFilePath);
		doCryptography(Cipher.ENCRYPT_MODE, inputFile, outputFile, auditor);
		auditor.setStatus(Status.Success);
		auditService = Context.getService(MergePatientDataAuditService.class);
		auditService.saveAuditMessage(auditor);
		return outputFile;
	}
	
	@Override
	public File decrypt(File inputFile, PaginatedAuditMessage auditor) {
		File outputFile = new File(MergePatientDataEncryptionUtils.getSerializedFilePath());
		doCryptography(Cipher.DECRYPT_MODE, inputFile, outputFile, auditor);
		if (inputFile.exists()) {
			inputFile.delete();
		}
		return outputFile;
	}
	
	private void doCryptography(int cipherMode, File input, File output, PaginatedAuditMessage auditor) {
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
			auditor.getFailureDetails().add(e1.getMessage());
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
			auditor.getFailureDetails().add(e.getMessage());
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
			//Delete the invalid file
			input.delete(); 
			throw new MPDException(e);
		}
	}
}
