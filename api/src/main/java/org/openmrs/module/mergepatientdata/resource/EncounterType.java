package org.openmrs.module.mergepatientdata.resource;

import org.openmrs.BaseOpenmrsObject;

public class EncounterType extends BaseOpenmrsObject implements MergeAbleResource {
	
	private static final long serialVersionUID = 1L;
	
	private Integer encounterTypeId;
	
	private Privilege viewPrivilege;
	
	private Privilege editPrivilege;
	
	public EncounterType(org.openmrs.EncounterType type) {
		this.setUuid(type.getUuid());
		this.editPrivilege = new Privilege(type.getEditPrivilege());
		this.viewPrivilege = new Privilege(type.getViewPrivilege());
		this.encounterTypeId = type.getEncounterTypeId();
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.EncounterType type = new org.openmrs.EncounterType();
		type.setUuid(getUuid());
		if (editPrivilege != null) {
			type.setEditPrivilege((org.openmrs.Privilege) editPrivilege.getOpenMrsObject());
		}
		if (viewPrivilege != null) {
			type.setViewPrivilege((org.openmrs.Privilege) viewPrivilege.getOpenMrsObject());
		}
		type.setEncounterTypeId(encounterTypeId);
		return type;
	}
	
	@Override
	public Integer getId() {
		return encounterTypeId;
	}
	
	@Override
	public void setId(Integer id) {
		setEncounterTypeId(id);
	}
	
	public Integer getEncounterTypeId() {
		return encounterTypeId;
	}
	
	public void setEncounterTypeId(Integer encounterTypeId) {
		this.encounterTypeId = encounterTypeId;
	}
	
	public Privilege getViewPrivilege() {
		return viewPrivilege;
	}
	
	public void setViewPrivilege(Privilege viewPrivilege) {
		this.viewPrivilege = viewPrivilege;
	}
	
	public Privilege getEditPrivilege() {
		return editPrivilege;
	}
	
	public void setEditPrivilege(Privilege editPrivilege) {
		this.editPrivilege = editPrivilege;
	}
	
}
