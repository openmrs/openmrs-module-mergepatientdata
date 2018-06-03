package org.openmrs.module.mergepatientdata.api.model.config;

import java.util.List;

public class MethodConfiguration {
	
	private boolean enabled;
	
	private List<ClassConfiguration> classes;
	
	public MethodConfiguration(boolean enabled, List<ClassConfiguration> classes) {
		super();
		this.enabled = enabled;
		this.classes = classes;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public List<ClassConfiguration> getClasses() {
		return classes;
	}
	
	public void setClasses(List<ClassConfiguration> classes) {
		this.classes = classes;
	}
	
}
