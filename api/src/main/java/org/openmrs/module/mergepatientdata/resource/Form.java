package org.openmrs.module.mergepatientdata.resource;

import java.util.HashSet;
import java.util.Set;

import org.openmrs.BaseOpenmrsObject;

public class Form extends BaseOpenmrsObject implements MergeAbleResource {
	
	private static final long serialVersionUID = 1L;
	
	private Integer formId;
	
	private String name;
	
	private String description;
	
	private String version;
	
	private Integer build;
	
	private Boolean published = false;
	
	private EncounterType encounterType;
	
	private Set<FormField> formFields;
	
	public Form(org.openmrs.Form form, Boolean initializeComplexMetaData) {
		if (form == null) {
			return;
		}
		this.name = form.getName();
		this.description = form.getDescription();
		this.setUuid(form.getUuid());
		this.formId = form.getFormId();
		this.version = form.getVersion();
		this.published = form.getPublished();
		this.build = form.getBuild();
		if (form.getEncounterType() != null) {
			this.encounterType = new EncounterType(form.getEncounterType());
		}
		if (initializeComplexMetaData) {
			if (form.getFormFields() != null) {
				formFields = new HashSet<>();
				for (org.openmrs.FormField field : form.getFormFields()) {
					formFields.add(new FormField(field, true));
				}
			}
		}
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.Form form = new org.openmrs.Form();
		form.setName(name);
		form.setDescription(description);
		form.setUuid(getUuid());
		form.setFormId(formId);
		form.setVersion(version);
		form.setPublished(published);
		form.setBuild(build);
		if (this.encounterType != null) {
			form.setEncounterType((org.openmrs.EncounterType) encounterType.getOpenMrsObject());
		}
		if (this.formFields != null) {
			Set<org.openmrs.FormField> fields = new HashSet<>();
			for (FormField mpdField : this.formFields) {
				fields.add((org.openmrs.FormField) mpdField.getOpenMrsObject());
			}
			form.setFormFields(fields);
		}
		return form;
	}
	
	@Override
	public Integer getId() {
		return formId;
	}
	
	@Override
	public void setId(Integer id) {
		setFormId(id);
	}
	
	public Integer getFormId() {
		return formId;
	}
	
	public void setFormId(Integer formId) {
		this.formId = formId;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public Integer getBuild() {
		return build;
	}
	
	public void setBuild(Integer build) {
		this.build = build;
	}
	
	public Boolean getPublished() {
		return published;
	}
	
	public void setPublished(Boolean published) {
		this.published = published;
	}
	
	public EncounterType getEncounterType() {
		return encounterType;
	}
	
	public void setEncounterType(EncounterType encounterType) {
		this.encounterType = encounterType;
	}
	
	public Set<FormField> getFormFields() {
		return formFields;
	}
	
	public void setFormFields(Set<FormField> formFields) {
		this.formFields = formFields;
	}
}
