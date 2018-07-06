package org.openmrs.module.mergepatientdata.api.model.config;

public class GeneralConfiguration {
	
	private String localInstanceId;
	
	private boolean persistSuccessAudit;
	
	private boolean persistFailureAudit;
	
	public GeneralConfiguration() {
		
	}
	
	public GeneralConfiguration(String localInstanceId, String parentId, boolean persistSuccessAudit,
	    boolean persistFailureAudit) {
		super();
		this.localInstanceId = localInstanceId;
		this.persistSuccessAudit = persistSuccessAudit;
		this.persistFailureAudit = persistFailureAudit;
	}
	
	public String getLocalInstanceId() {
		return localInstanceId;
	}
	
	public void setLocalInstanceId(String localInstanceId) {
		this.localInstanceId = localInstanceId;
	}
	
	public boolean isPersistSuccessAudit() {
		return persistSuccessAudit;
	}
	
	public void setPersistSuccessAudit(boolean persistSuccessAudit) {
		this.persistSuccessAudit = persistSuccessAudit;
	}
	
	public boolean isPersistFailureAudit() {
		return persistFailureAudit;
	}
	
	public void setPersistFailureAudit(boolean persistFailureAudit) {
		this.persistFailureAudit = persistFailureAudit;
	}
	
}
