package org.openmrs.module.mergepatientdata.resource;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.BaseOpenmrsObject;

public class FormField extends BaseOpenmrsMetadata implements MergeAbleResource {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer formFieldId;
	
	protected FormField parent;
	
	protected Form form;
	
	protected Field field;
	
	protected Integer fieldNumber;
	
	protected String fieldPart;
	
	protected Integer pageNumber;
	
	protected Integer minOccurs;
	
	protected Integer maxOccurs;
	
	protected Boolean required = false;
	
	protected Float sortWeight;
	
	// Constructors
	
	public FormField() {
		
	}
	
	public FormField(org.openmrs.FormField formField, Boolean initializeComplexMetaData) {
		if (formField == null) {
			return;
		}
		this.setUuid(formField.getUuid());
		this.formFieldId = formField.getFormFieldId();
		this.fieldNumber = formField.getFieldNumber();
		this.fieldPart = formField.getFieldPart();
		this.pageNumber = formField.getPageNumber();
		this.minOccurs = formField.getMinOccurs();
		this.maxOccurs = formField.getMaxOccurs();
		this.required = formField.getRequired();
		if (initializeComplexMetaData) {
			this.sortWeight = formField.getSortWeight();
			this.parent = formField.getParent() != null ? new FormField(formField.getParent(), false) : null;
			this.form = formField.getForm() != null ? new Form(formField.getForm(), false) : null;
			this.field = formField.getField() != null ? new Field(formField.getField(), true) : null;
		}
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.FormField formField = new org.openmrs.FormField();
		formField.setUuid(getUuid());
		formField.setFormFieldId(formFieldId);
		formField.setFieldNumber(fieldNumber);
		formField.setFieldPart(fieldPart);
		formField.setPageNumber(pageNumber);
		formField.setMinOccurs(minOccurs);
		formField.setMaxOccurs(maxOccurs);
		formField.setRequired(required);
		formField.setSortWeight(sortWeight);
		if (this.parent != null) {
			formField.setParent((org.openmrs.FormField) parent.getOpenMrsObject());
		}
		if (this.form != null) {
			formField.setForm((org.openmrs.Form) form.getOpenMrsObject());
		}
		if (this.field != null) {
			formField.setField((org.openmrs.Field) field.getOpenMrsObject());
		}
		return formField;
	}
	
	@Override
	public Integer getId() {
		return formFieldId;
	}
	
	@Override
	public void setId(Integer id) {
		setFormFieldId(id);
	}
	
	public Integer getFormFieldId() {
		return formFieldId;
	}
	
	public void setFormFieldId(Integer formFieldId) {
		this.formFieldId = formFieldId;
	}
	
	public FormField getParent() {
		return parent;
	}
	
	public void setParent(FormField parent) {
		this.parent = parent;
	}
	
	public Form getForm() {
		return form;
	}
	
	public void setForm(Form form) {
		this.form = form;
	}
	
	public Field getField() {
		return field;
	}
	
	public void setField(Field field) {
		this.field = field;
	}
	
	public Integer getFieldNumber() {
		return fieldNumber;
	}
	
	public void setFieldNumber(Integer fieldNumber) {
		this.fieldNumber = fieldNumber;
	}
	
	public String getFieldPart() {
		return fieldPart;
	}
	
	public void setFieldPart(String fieldPart) {
		this.fieldPart = fieldPart;
	}
	
	public Integer getPageNumber() {
		return pageNumber;
	}
	
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public Integer getMinOccurs() {
		return minOccurs;
	}
	
	public void setMinOccurs(Integer minOccurs) {
		this.minOccurs = minOccurs;
	}
	
	public Integer getMaxOccurs() {
		return maxOccurs;
	}
	
	public void setMaxOccurs(Integer maxOccurs) {
		this.maxOccurs = maxOccurs;
	}
	
	public Boolean getRequired() {
		return required;
	}
	
	public void setRequired(Boolean required) {
		this.required = required;
	}
	
	public Float getSortWeight() {
		return sortWeight;
	}
	
	public void setSortWeight(Float sortWeight) {
		this.sortWeight = sortWeight;
	}
	
}
