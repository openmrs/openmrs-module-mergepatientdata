package org.openmrs.module.mergepatientdata.api.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.MergePatientDataConstants;
import org.openmrs.module.mergepatientdata.api.MergePatientDataAuditService;
import org.openmrs.module.mergepatientdata.api.MergePatientDataEncryptionService;
import org.openmrs.module.mergepatientdata.api.MergePatientDataImportService;
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataConfigurationUtils;
import org.openmrs.module.mergepatientdata.api.utils.MergePatientDataUtils;
import org.openmrs.module.mergepatientdata.enums.Status;
import org.openmrs.module.mergepatientdata.resource.Encounter;
import org.openmrs.module.mergepatientdata.sync.MPDStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MergePatientDataImportServiceImpl implements MergePatientDataImportService {
	
	private final Logger log = LoggerFactory.getLogger(MergePatientDataImportServiceImpl.class);
	
	MergePatientDataEncryptionService encryptionService;
	
	MergePatientDataAuditService auditService;
	
	@Override
	public void importMPD(List<Class> resourceTypesToImport, File zippedDataFile, PaginatedAuditMessage auditor) {
		byte[] buffer = new byte[1024];
		encryptionService = new MergePatientDataEncryptionServiceImpl();
		// extract the zip file 
		File directoryToExtractFilesTo = new File(MergePatientDataConfigurationUtils.getMPDWorkingDir(),
		        MergePatientDataConstants.MPD_BATCH_DIR_NAME);
		if (directoryToExtractFilesTo.exists()) {
			// delete the old one
			directoryToExtractFilesTo.delete();
		}
		directoryToExtractFilesTo.mkdir();
		
		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zippedDataFile));
			ZipEntry entry = zis.getNextEntry();
			while (entry != null) {
				File encryptedFile = new File(directoryToExtractFilesTo, entry.getName());
				FileOutputStream fos = new FileOutputStream(encryptedFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				entry = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
		}
		catch (IOException e) {
			log.error(e.getMessage());
		}
		
		// now do the import from each file
		for (File encryptedFile : directoryToExtractFilesTo.listFiles()) {
			String name = encryptedFile.getName();
			String extension = name.substring(name.indexOf("."), name.length());
			if (!extension.equals(".mpd")) {
				continue;
			}
			File serializedFile = encryptionService.decrypt(encryptedFile, auditor);
			MPDStore mergeableData = encryptionService.deserialize(serializedFile);
			auditor.setOrigin(mergeableData.getOriginId());
			log.debug("Importing data from " + mergeableData.getOriginId());
			MergePatientDataUtils.mergeResourceToOpenmrsDataBase(mergeableData, resourceTypesToImport, auditor);
		}
		if (!auditor.isHasErrors()) {
			auditor.setStatus(Status.Success);
		} else {
			auditor.setStatus(Status.Failure);
		}
		auditService = Context.getService(MergePatientDataAuditService.class);
		auditService.saveAuditMessage(auditor);
	}
	
}
