package org.openmrs.module.mergepatientdata.api.model.config;

public class ClassConfiguration {
	
	private String classTitle;
	
	private boolean enabled;
	
	private String openmrsClass;
	
	public ClassConfiguration(String classTitle, boolean enabled, String openmrsClass) {
		super();
		this.classTitle = classTitle;
		this.enabled = enabled;
		this.openmrsClass = openmrsClass;
	}
	
	public ClassConfiguration() {
	}
	
	public String getClassTitle() {
		return classTitle;
	}
	
	public void setClassTitle(String classTitle) {
		this.classTitle = classTitle;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getOpenmrsClass() {
		return openmrsClass;
	}
	
	public void setOpenmrsClass(String openmrsClass) {
		this.openmrsClass = openmrsClass;
	}
	
}
