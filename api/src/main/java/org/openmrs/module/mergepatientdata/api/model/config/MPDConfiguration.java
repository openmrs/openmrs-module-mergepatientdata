package org.openmrs.module.mergepatientdata.api.model.config;

public class MPDConfiguration {
	
	private GeneralConfiguration general;
	
	private MethodConfiguration exporting;
	
	private MethodConfiguration importing;
	
	public MPDConfiguration(GeneralConfiguration general, MethodConfiguration exporting, MethodConfiguration importing) {
		super();
		this.general = general;
		this.exporting = exporting;
		this.importing = importing;
	}
	
	public GeneralConfiguration getGeneral() {
		return general;
	}
	
	public void setGeneral(GeneralConfiguration general) {
		this.general = general;
	}
	
	public MethodConfiguration getExporting() {
		return exporting;
	}
	
	public void setExporting(MethodConfiguration exporting) {
		this.exporting = exporting;
	}
	
	public MethodConfiguration getImporting() {
		return importing;
	}
	
	public void setImporting(MethodConfiguration importing) {
		this.importing = importing;
	}
	
}
