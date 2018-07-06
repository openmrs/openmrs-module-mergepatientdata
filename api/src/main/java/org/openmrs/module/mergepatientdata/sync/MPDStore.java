package org.openmrs.module.mergepatientdata.sync;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.module.mergepatientdata.enums.MergeAbleDataCategory;
import org.openmrs.module.mergepatientdata.resource.Location;
import org.openmrs.module.mergepatientdata.resource.Patient;

public class MPDStore {

	public MPDStore() {}
	
	public String originId;
	public List<Patient> patients;
	public List<Location> locations;
	public List<MergeAbleDataCategory> types = new ArrayList<>();
	
	public List<MergeAbleDataCategory> getTypes() {
		return types;
	}
	public void setTypes(List<MergeAbleDataCategory> types) {
		this.types = types;
	}
	public List<Patient> getPatients() {
		return patients;
	}
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
	public List<Location> getLocations() {
		return locations;
	}
	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}
	public void addType(MergeAbleDataCategory type) {
		this.types.add(type);
	}
	public boolean hastData() {
		if (this.patients != null || this.locations != null) {
			return true;
		}
		return false;
	}
	public String getOriginId() {
		return originId;
	}
	public void setOriginId(String originId) {
		this.originId = originId;
	}

	//TODO :- Add other Resources
		
}
