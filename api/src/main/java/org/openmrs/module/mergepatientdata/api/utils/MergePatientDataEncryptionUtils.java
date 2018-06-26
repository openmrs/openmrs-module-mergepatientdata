package org.openmrs.module.mergepatientdata.api.utils;

import java.io.File;
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
	
}
