package org.openmrs.module.mergepatientdata.resource;

import java.util.HashSet;
import java.util.Set;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.BaseOpenmrsObject;

public class Field extends BaseOpenmrsMetadata implements MergeAbleResource {
	
	private static final long serialVersionUID = 1L;
	
	private Integer fieldId;
	
	private FieldType fieldType;
	
	private Concept concept;
	
	private String tableName;
	
	private String attributeName;
	
	private String defaultValue;
	
	private Boolean selectMultiple = false;
	
	private Set<FieldAnswer> answers;
	
	public Field(org.openmrs.Field field, Boolean initializeComplexMetaData) {
		this.setUuid(field.getUuid());
		this.setDescription(field.getDescription());
		this.setName(field.getName());
		this.fieldId = field.getFieldId();
		this.concept = new Concept(field.getConcept(), true);
		this.tableName = field.getTableName();
		this.attributeName = field.getAttributeName();
		this.defaultValue = field.getDefaultValue();
		this.selectMultiple = field.getSelectMultiple();
		if (initializeComplexMetaData) {
			this.fieldType = new FieldType(field.getFieldType());
			if (field.getAnswers() != null) {
				answers = new HashSet<>();
				for (org.openmrs.FieldAnswer answer : field.getAnswers()) {
					answers.add(new FieldAnswer(answer));
				}
			}
		}
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.Field field = new org.openmrs.Field();
		field.setUuid(this.getUuid());
		field.setDescription(getDescription());
		field.setName(getName());
		field.setFieldId(fieldId);
		field.setConcept((org.openmrs.Concept) concept.getOpenMrsObject());
		field.setTableName(tableName);
		field.setAttributeName(attributeName);
		field.setSelectMultiple(selectMultiple);
		if (fieldType != null) {
			field.setFieldType((org.openmrs.FieldType) fieldType.getOpenMrsObject());
		}
		if (this.answers != null) {
			Set<org.openmrs.FieldAnswer> answers = new HashSet<>();
			for (FieldAnswer ans : this.answers) {
				answers.add((org.openmrs.FieldAnswer) ans.getOpenMrsObject());
			}
		    field.setAnswers(answers);
		}
		return field;
	}
	
	public Integer getFieldId() {
		return fieldId;
	}
	
	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}
	
	public FieldType getFieldType() {
		return fieldType;
	}
	
	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}
	
	public Concept getConcept() {
		return concept;
	}
	
	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getAttributeName() {
		return attributeName;
	}
	
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public Boolean getSelectMultiple() {
		return selectMultiple;
	}
	
	public void setSelectMultiple(Boolean selectMultiple) {
		this.selectMultiple = selectMultiple;
	}
	
	public Set<FieldAnswer> getAnswers() {
		return answers;
	}
	
	public void setAnswers(Set<FieldAnswer> answers) {
		this.answers = answers;
	}
	
	@Override
	public Integer getId() {
		return fieldId;
	}
	
	@Override
	public void setId(Integer id) {
		setFieldId(id);
		
	}
	
}
