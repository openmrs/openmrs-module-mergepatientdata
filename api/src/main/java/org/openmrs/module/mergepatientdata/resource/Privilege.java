package org.openmrs.module.mergepatientdata.resource;

import org.openmrs.BaseOpenmrsObject;

public class Privilege extends BaseOpenmrsObject implements MergeAbleResource {
	
	private String privilege;
	
	public Privilege(org.openmrs.Privilege privilege) {
		if (privilege == null) {
			return;
		}
		this.setUuid(privilege.getUuid());
		this.privilege = privilege.getPrivilege();
	}
	
	public String getPrivilege() {
		return privilege;
	}
	
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	
	@Override
	public Integer getId() {
		return null;
	}
	
	@Override
	public void setId(Integer id) {
		
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.Privilege privilege = new org.openmrs.Privilege();
		privilege.setUuid(getUuid());
		privilege.setPrivilege(this.privilege);
		return privilege;
	}
	
}
