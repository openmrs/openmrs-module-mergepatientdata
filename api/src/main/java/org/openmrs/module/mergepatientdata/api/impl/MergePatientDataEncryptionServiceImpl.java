package org.openmrs.module.mergepatientdata.api.impl;

import java.io.BufferedReader;
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
	
	/**
	 * @see org.openmrs.module.mergepatientdata.api.MergePatientDataEncryptionService#serialize(MPDStore)
	 */
	@Override
	public File serialize(MPDStore store) {
		String filePath = MergePatientDataEncryptionUtils.getMpdBatchDirPath();
		log.info("Serializing to '" + filePath + "'");
		File inputfile = new File(filePath, MergePatientDataConstants.MPD_SERIALIZED_FILE_NAME);
		
		try {
			FileWriter writer = new FileWriter(inputfile);
			Gson gson = new GsonBuilder().create();
			gson.toJson(store, writer);
			
			writer.close();
		}
		catch (IOException e) {
			log.error(e.getMessage());
		}
		return inputfile;
	}
	
	/**
	 * @see org.openmrs.module.mergepatientdata.api.MergePatientDataEncryptionService#deserialize(File)
	 */
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
	
	/**
	 * @see org.openmrs.module.mergepatientdata.api.MergePatientDataEncryptionService#encrypt(File,
	 *      PaginatedAuditMessage)
	 */
	@Override
	public File encrypt(File inputFile, PaginatedAuditMessage auditor, Integer batch) {
		log.info("Encrypting data");
		File outputFile = new File(MergePatientDataEncryptionUtils.getMpdBatchDirPath(), batch + "-"
		        + MergePatientDataConstants.ENCRYPTED_PATIENT_DATA_FILE_NAME);
		doCryptography(Cipher.ENCRYPT_MODE, inputFile, outputFile, auditor);
		auditor.setStatus(Status.Success);
		return outputFile;
	}
	
	/**
	 * @see org.openmrs.module.mergepatientdata.api.MergePatientDataEncryptionService#decrypt(File,
	 *      PaginatedAuditMessage)
	 */
	@Override
	public File decrypt(File inputFile, PaginatedAuditMessage auditor) {
		File outputFile = new File(MergePatientDataEncryptionUtils.getSerializedFilePath());
		doCryptography(Cipher.DECRYPT_MODE, inputFile, outputFile, auditor);
		if (inputFile.exists()) {
			inputFile.delete();
		}
		return outputFile;
	}
	
	/**
	 * Convenience method delegated to do an encryption plus its reverse
	 * 
	 * @param cipherMode
	 * @param input
	 * @param output
	 * @param auditor
	 */
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
			log.error(e1.getMessage());
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
			// Delete the invalid file
			input.delete(); 
			throw new MPDException(e);
		}
	}
}
