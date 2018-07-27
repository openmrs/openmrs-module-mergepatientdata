package org.openmrs.module.mergepatientdata.resource;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.BaseOpenmrsObject;

public class FieldType extends BaseOpenmrsMetadata implements MergeAbleResource {
	
	private static final long serialVersionUID = 1L;
	
	private Integer fieldTypeId;
	
	private Boolean isSet = false;
	
	public FieldType(org.openmrs.FieldType type) {
		this.setUuid(type.getUuid());
		this.setDateChanged(getDateChanged());
		this.setDescription(getDescription());
		this.setName(getName());
		this.setIsSet(type.getIsSet());
		this.fieldTypeId = type.getFieldTypeId();
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.FieldType type = new org.openmrs.FieldType();
		type.setUuid(getUuid());
		type.setDateChanged(getDateChanged());
		type.setDescription(getDescription());
		type.setName(getName());
		type.setIsSet(getIsSet());
		type.setFieldTypeId(getFieldTypeId());
		return type;
	}
	
	@Override
	public Integer getId() {
		return fieldTypeId;
	}
	
	@Override
	public void setId(Integer id) {
		setFieldTypeId(id);
	}
	
	public Integer getFieldTypeId() {
		return fieldTypeId;
	}
	
	public void setFieldTypeId(Integer fieldTypeId) {
		this.fieldTypeId = fieldTypeId;
	}
	
	public Boolean getIsSet() {
		return isSet;
	}
	
	public void setIsSet(Boolean isSet) {
		this.isSet = isSet;
	}
}
