package org.openmrs.module.mergepatientdata.resource;

import java.util.Objects;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.api.context.Context;

public class LocationTag implements MergeAbleResource {
	
	private String uuid;
	
	private String name;
	
	private String description;
	
	private boolean retired;
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		if (uuid != null) {
			org.openmrs.LocationTag locationTag = Context.getLocationService().getLocationTagByUuid(uuid);
			if (locationTag != null) {
				return locationTag;
			}
		}
		
		return Context.getLocationService().getLocationTagByName(name);
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean isRetired() {
		return retired;
	}
	
	public void setRetired(boolean retired) {
		this.retired = retired;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		LocationTag that = (LocationTag) o;
		return Objects.equals(uuid, that.uuid) && Objects.equals(name, that.name)
		        && Objects.equals(description, that.description) && Objects.equals(retired, that.retired);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(uuid, name, description, retired);
	}
	
}
