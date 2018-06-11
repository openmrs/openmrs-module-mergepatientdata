package org.openmrs.module.mergepatientdata.api.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.module.mergepatientdata.MergePatientDataConstants;
import org.openmrs.module.mergepatientdata.api.exceptions.MPDException;
import org.openmrs.module.mergepatientdata.api.model.config.MPDConfiguration;
import org.openmrs.module.mergepatientdata.enums.ResourcePathType;
import org.openmrs.util.OpenmrsUtil;

public class MergePatientDataConfigurationUtils {
	
	public static String getCustomConfigFilePath() {
		File mpdWorkingDir = OpenmrsUtil.getDirectoryInApplicationDataDirectory(MergePatientDataConstants.MPD_DIR);
		return new File(mpdWorkingDir, MergePatientDataConstants.CONFIG_FILE_NAME).getAbsolutePath();
	}
	
	public static boolean fileExits(String path) {
		return new File(path).exists();
	}
	
	public static String readResourceFileRelativePath(String path) throws MPDException {
		try (InputStream in = MergePatientDataConfigurationUtils.class.getClassLoader().getResourceAsStream(path)) {
			if (in == null) {
				throw new MPDException("Resource '" + path + "' does not exist");
			}	
			return IOUtils.toString(in);
		} catch(IOException e) {
			throw new MPDException(e);
		}
	}
	
	public static String readResourceFileAbsolutePath(String file) throws MPDException  {
	        File initialFile = new File(file);

	        try (InputStream in = new FileInputStream(initialFile)) {
	            if (in == null) {
	                throw new MPDException("Resource '" + file + "' doesn't exist");
	            }
	            return IOUtils.toString(in);
	        } catch (IOException e) {
	            throw new MPDException(e);
	        }
	    }
	
	public static MPDConfiguration parseJsonToMPDConfig(String path, ResourcePathType pathType) {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			String resourceFile = pathType.equals(ResourcePathType.ASOLUTE) ? readResourceFileAbsolutePath(path)
			        : readResourceFileRelativePath(path);
			return mapper.readValue(resourceFile, MPDConfiguration.class);
			
		}
		catch (IOException e) {
			throw new MPDException(e);
		}
	}
	
}
