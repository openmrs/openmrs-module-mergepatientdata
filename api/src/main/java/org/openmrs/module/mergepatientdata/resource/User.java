package org.openmrs.module.mergepatientdata.resource;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.openmrs.BaseOpenmrsObject;

/**
 * Planning to come up with a better design. Merged data should all default to the Super User.
 */
public class User implements MergeAbleResource {
	
	private Integer userId;
	
	private Person person;
	
	private String systemId;
	
	private String username;
	
	private Set<Role> roles;
	
	private Map<String, String> userProperties;
	
	private List<Locale> proficientLocales = null;
	
	private String parsedProficientLocalesProperty = "";
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public String getSystemId() {
		return systemId;
	}
	
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public Map<String, String> getUserProperties() {
		return userProperties;
	}
	
	public void setUserProperties(Map<String, String> userProperties) {
		this.userProperties = userProperties;
	}
	
	public List<Locale> getProficientLocales() {
		return proficientLocales;
	}
	
	public void setProficientLocales(List<Locale> proficientLocales) {
		this.proficientLocales = proficientLocales;
	}
	
	public String getParsedProficientLocalesProperty() {
		return parsedProficientLocalesProperty;
	}
	
	public void setParsedProficientLocalesProperty(String parsedProficientLocalesProperty) {
		this.parsedProficientLocalesProperty = parsedProficientLocalesProperty;
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		
		return null;
	}
	
}
