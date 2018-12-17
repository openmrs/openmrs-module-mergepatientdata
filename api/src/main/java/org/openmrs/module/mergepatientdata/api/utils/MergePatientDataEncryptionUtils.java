package org.openmrs.module.mergepatientdata.api.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.openmrs.module.mergepatientdata.MergePatientDataConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MergePatientDataEncryptionUtils {
	
	private final static Logger log = LoggerFactory.getLogger(MergePatientDataEncryptionUtils.class);
	
	public static String getEncryptedMPDFilePath() {
		return new File(MergePatientDataConfigurationUtils.getMPDWorkingDir(),
		        MergePatientDataConstants.ENCRYPTED_PATIENT_DATA_FILE_NAME).getAbsolutePath();
	}
	
	public static String getSerializedFilePath() {
		return new File(MergePatientDataConfigurationUtils.getMPDWorkingDir(),
		        MergePatientDataConstants.MPD_SERIALIZED_FILE_NAME).getAbsolutePath();
	}
	
	public static boolean makeMpdBatchDir() {
		return new File(MergePatientDataConfigurationUtils.getMPDWorkingDir(), MergePatientDataConstants.MPD_BATCH_DIR_NAME)
		        .mkdir();
	}
	
	public static String getMpdBatchDirPath() {
		return new File(MergePatientDataConfigurationUtils.getMPDWorkingDir(), MergePatientDataConstants.MPD_BATCH_DIR_NAME)
		        .getAbsolutePath();
	}
	
	/**
	 * convenient method that processes all batch encrypted files and puts puts them in one file
	 * availing it for download
	 * 
	 * @return zip file
	 */
	public static File processMpdZippedData() {
		InputStream in = null;
		ZipOutputStream zos = null;
		final byte[] buffer = new byte[1024];
		// create a recursive mechanism that adds Zipped File entries to the zip file
		// the file added should only be denoted with a .mpd extension
		// delete the dir that initially contained the transfered files
		// return the zip file
		File mpdBatchesDirectory = new File(getMpdBatchDirPath());
		File zipFile = buildZippedFile();
		if (mpdBatchesDirectory.exists() && mpdBatchesDirectory.isDirectory()) {
			// add data to the zip file
			try {
				zos = new ZipOutputStream(new FileOutputStream(zipFile));
				// iterate over files and add the required one to the zip file
				for (File file : mpdBatchesDirectory.listFiles()) {
					if (file.isDirectory()) {
						// just skip that
						continue;
					}
					// first make sure the file is valid
					// we only need those with .mpd extension
					String name = file.getName();
					String extension = name.substring(name.indexOf("."), name.length());
					if (!extension.equals(".mpd")) {
						continue;
					}
					ZipEntry entry = new ZipEntry(file.getName());
					zos.putNextEntry(entry);
					// copy this file to the zip file
					in = new FileInputStream(file);
					int length;
					while ((length = in.read(buffer)) > 0) {
						zos.write(buffer, 0, length);
					}
					in.close();
				}
				zos.close();
			}
			catch (IOException e) {
				log.error(e.getMessage());
			}
			finally {
				try {
					if (in != null) {
						in.close();
					}
					if (zos != null) {
						zos.close();
					}
				}
				catch (IOException e) {
					log.error(e.getMessage());
				}
				
			}
		}
		return zipFile;
	}
	
	/**
	 * convenient method to create the initial zip file
	 * 
	 * @should create zip file if not extisting
	 * @return zip file
	 */
	private static File buildZippedFile() {
		//create the zip file
		File zipFile = new File(getMpdZipFilePath());
		if (!zipFile.exists()) {
			try {
				zipFile.createNewFile();
			}
			catch (IOException e) {
				log.error(e.getMessage());
			}
		}
		return zipFile;
	}
	
	/**
	 * convenient method used to get absolute path to zip data file
	 * 
	 * @return path
	 */
	public static String getMpdZipFilePath() {
		return new File(MergePatientDataConfigurationUtils.getMPDWorkingDir(), MergePatientDataConstants.MPD_BATCH_DIR_NAME
		        + ".zip").getAbsolutePath();
	}
}
