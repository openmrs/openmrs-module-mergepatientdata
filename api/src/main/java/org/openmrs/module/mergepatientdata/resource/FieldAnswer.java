package org.openmrs.module.mergepatientdata.resource;

import java.util.Date;

import org.openmrs.BaseOpenmrsObject;

public class FieldAnswer extends BaseOpenmrsObject implements MergeAbleResource {
	
	private static final long serialVersionUID = 3530655290180077546L;
	
	private Date dateCreated;
	
	private Concept concept;
	
	private User creator;
	
	private Field field;
	
	private boolean dirty;
	
	public FieldAnswer(org.openmrs.FieldAnswer answer) {
		this.setUuid(answer.getUuid());
		this.field = new Field(answer.getField(), false);
		this.concept = new Concept(answer.getConcept(), true);
		this.dirty = answer.getDirty();
		this.dateCreated = answer.getDateCreated();
	}
	
	@Override
	public Integer getId() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void setId(Integer id) {
		throw new UnsupportedOperationException();
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public Concept getConcept() {
		return concept;
	}
	
	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	
	public User getCreator() {
		return creator;
	}
	
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	public Field getField() {
		return field;
	}
	
	public void setField(Field field) {
		this.field = field;
	}
	
	public boolean isDirty() {
		return dirty;
	}
	
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.FieldAnswer answer = new org.openmrs.FieldAnswer();
		answer.setConcept((org.openmrs.Concept) concept.getOpenMrsObject());
		answer.setField((org.openmrs.Field) field.getOpenMrsObject());
		answer.setUuid(getUuid());
		answer.setDateCreated(getDateCreated());
		return answer;
	}
	
}
