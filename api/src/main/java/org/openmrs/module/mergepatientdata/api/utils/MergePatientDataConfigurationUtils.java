package org.openmrs.module.mergepatientdata.api.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.module.mergepatientdata.MergePatientDataConstants;
import org.openmrs.module.mergepatientdata.api.exceptions.MPDException;
import org.openmrs.module.mergepatientdata.api.impl.MergePatientDataEncryptionServiceImpl;
import org.openmrs.module.mergepatientdata.api.model.config.MPDConfiguration;
import org.openmrs.module.mergepatientdata.enums.ResourcePathType;
import org.openmrs.util.OpenmrsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MergePatientDataConfigurationUtils {
	
	private static final Logger log = LoggerFactory.getLogger(MergePatientDataConfigurationUtils.class);
	
	public static String getCustomConfigFilePath() {
		File mpdWorkingDir = OpenmrsUtil.getDirectoryInApplicationDataDirectory(MergePatientDataConstants.MPD_DIR);
		return new File(mpdWorkingDir, MergePatientDataConstants.CONFIG_FILE_NAME).getAbsolutePath();
	}
	
	public static boolean fileExits(String path) {
		log.info("Checking :{} exists", path);
		boolean exist = new File(path).exists();
		if (!exist) {
			log.warn("File '" + path + "' doesn't exist");
		}
		return exist;
	}
	
	public static String readResourceFileRelativePath(String path) throws MPDException {
		log.info("Reading Resource relative path :{}", path);
		try (InputStream in = MergePatientDataConfigurationUtils.class.getClassLoader().getResourceAsStream(path)) {
			if (in == null) {
				throw new MPDException("Resource '" + path + "' does not exist");
			}	
			return IOUtils.toString(in);
		} catch(IOException e) {
			throw new MPDException(e);
		}
	}
	
	/**
	 * @param path
	 * @return
	 * @throws MPDException
	 */
	public static String readResourceFileAbsolutePath(String path) throws MPDException  {
		log.info("Reading Resource Absolute path :{}", path);
	    File initialFile = new File(path);
	    try (InputStream in = new FileInputStream(initialFile)) {
	    	if (in == null) {
	             throw new MPDException("Resource '" + path + "' doesn't exist");
	         }
	         return IOUtils.toString(in);
	    	
	    } catch (IOException e) {
            throw new MPDException(e);
        }
	         
	          
	  }
	
	public static MPDConfiguration parseJsonToMPDConfig(String path, ResourcePathType pathType) {
		ObjectMapper mapper = new ObjectMapper();
		log.info("Parsing json to MPDConfiguration from file :" + path);
		try {
			String resourceFile = pathType.equals(ResourcePathType.ASOLUTE) ? readResourceFileAbsolutePath(path)
			        : readResourceFileRelativePath(path);
			return mapper.readValue(resourceFile, MPDConfiguration.class);
		}
		catch (IOException e) {
			throw new MPDException(e);
		}
	}
	
	public static void cleanMPDWorkDir() {
		String encrytptedFilePath = MergePatientDataEncryptionUtils.getEncryptedMPDFilePath();
		String serializedFilePath = MergePatientDataEncryptionUtils.getSerializedFilePath();
		if (MergePatientDataConfigurationUtils.fileExits(encrytptedFilePath)) {
			new File(encrytptedFilePath).delete();
		}
		if (MergePatientDataConfigurationUtils.fileExits(serializedFilePath)) {
			new File(serializedFilePath).delete();
		}
	}
	
	public static File getMPDWorkingDir() {
		return OpenmrsUtil.getDirectoryInApplicationDataDirectory(MergePatientDataConstants.MPD_DIR);
	}
	
}
