package org.openmrs.module.mergepatientdata.resource;

import java.util.Set;

public class Role {
	
	private String role;
	
	private Set<Privilege> privileges;
	
	private Set<Role> inheritedRoles;
	
	private Set<Role> childRoles;
	
	public Role() {
		
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public Set<Privilege> getPrivileges() {
		return privileges;
	}
	
	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}
	
	public Set<Role> getInheritedRoles() {
		return inheritedRoles;
	}
	
	public void setInheritedRoles(Set<Role> inheritedRoles) {
		this.inheritedRoles = inheritedRoles;
	}
	
	public Set<Role> getChildRoles() {
		return childRoles;
	}
	
	public void setChildRoles(Set<Role> childRoles) {
		this.childRoles = childRoles;
	}
	
}
