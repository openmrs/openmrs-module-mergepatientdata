package org.openmrs.module.mergepatientdata.resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.mergepatientdata.api.utils.ObjectUtils;

public class Location implements MergeAbleResource {
	
	private Integer id;
	
	private String uuid;
	
	private String name;
	
	private String description;
	
	private String cityVillage;
	
	private String stateProvince;
	
	private String country;
	
	private String postalCode;
	
	private String latitude;
	
	private String longitude;
	
	private String countryDistrict;
	
	private String address1;
	
	private String address2;
	
	private String address3;
	
	private String address4;
	
	private String address5;
	
	private String address6;
	
	private String address7;
	
	private String address8;
	
	private String address9;
	
	private String address10;
	
	private String address11;
	
	private String address12;
	
	private String address13;
	
	private String address14;
	
	private String address15;
	
	private Set<LocationTag> tags;
	
	private Location parentLocation;
	
	private Set<Location> childLocations;
	
	private Boolean retired;
	
	/**
	 * @param openmrsLocation
	 * @param primaryMetaData If false @Field parentLocation & childLocations will set to null.It
	 *            prevents {@link java.lang.StackOverflowError} Exception.
	 */
	public Location(org.openmrs.Location openmrsLocation, Boolean primaryMetaData) {
		this.id = openmrsLocation.getId();
		this.uuid = openmrsLocation.getUuid();
		this.description = openmrsLocation.getDescription();
		this.name = openmrsLocation.getName();
		this.cityVillage = openmrsLocation.getCityVillage();
		this.stateProvince = openmrsLocation.getStateProvince();
		this.country = openmrsLocation.getCountry();
		this.postalCode = openmrsLocation.getPostalCode();
		this.latitude = openmrsLocation.getLatitude();
		this.longitude = openmrsLocation.getLongitude();
		this.countryDistrict = openmrsLocation.getCountyDistrict();
		this.address1 = openmrsLocation.getAddress1();
		this.address2 = openmrsLocation.getAddress2();
		this.address3 = openmrsLocation.getAddress3();
		this.address4 = openmrsLocation.getAddress4();
		this.address5 = openmrsLocation.getAddress5();
		this.address6 = openmrsLocation.getAddress6();
		
		if (openmrsLocation.getTags() != null && openmrsLocation.getTags().size() > 0) {
			this.tags = new HashSet(ObjectUtils.getMPDResourceObjectsFromOpenmrsResourceObjects(openmrsLocation.getTags()));
		}
		this.retired = openmrsLocation.getRetired();
		
		//this.parentLocation = openmrsLocation.getParentLocation();
		//this.childLocations = openmrsLocation.getChildLocations();
		
		//@since platform 2.*
		this.address7 = openmrsLocation.getAddress7();
		this.address8 = openmrsLocation.getAddress8();
		this.address9 = openmrsLocation.getAddress9();
		this.address10 = openmrsLocation.getAddress10();
		this.address11 = openmrsLocation.getAddress11();
		this.address12 = openmrsLocation.getAddress12();
		this.address13 = openmrsLocation.getAddress13();
		this.address14 = openmrsLocation.getAddress14();
		this.address15 = openmrsLocation.getAddress15();
		
		if ((openmrsLocation.getParentLocation() != null || openmrsLocation.getChildLocations() != null) && primaryMetaData) {
			initializeMoreMetaData(this, openmrsLocation);
		}
	}
	
	private void initializeMoreMetaData(Location location, org.openmrs.Location openmrsLocation) {
		if (openmrsLocation.getParentLocation() != null) {
			this.parentLocation = new Location(openmrsLocation.getParentLocation(), false);
		}
		
		if (openmrsLocation.getChildLocations() != null) {
			this.childLocations = new HashSet<>();
			for (org.openmrs.Location childLoc : openmrsLocation.getChildLocations()) {
				Location mpdChildLoc = new Location(childLoc, false);
				this.childLocations.add(mpdChildLoc);
			}
		}
		
	}
	
	public Location() {
		
	}
	
	// region getters
	public String getUuid() {
		return uuid;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getCityVillage() {
		return cityVillage;
	}
	
	public String getStateProvince() {
		return stateProvince;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String getPostalCode() {
		return postalCode;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public String getCountryDistrict() {
		return countryDistrict;
	}
	
	public String getAddress1() {
		return address1;
	}
	
	public String getAddress2() {
		return address2;
	}
	
	public String getAddress3() {
		return address3;
	}
	
	public String getAddress4() {
		return address4;
	}
	
	public String getAddress5() {
		return address5;
	}
	
	public String getAddress6() {
		return address6;
	}
	
	public Set<LocationTag> getTags() {
		return tags;
	}
	
	public Location getParentLocation() {
		return parentLocation;
	}
	
	public Set<Location> getChildLocations() {
		return childLocations;
	}
	
	public Boolean getRetired() {
		return retired;
	}
	
	// endregion
	
	// region setters
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setCityVillage(String cityVillage) {
		this.cityVillage = cityVillage;
	}
	
	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public void setCountryDistrict(String countryDistrict) {
		this.countryDistrict = countryDistrict;
	}
	
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	
	public void setAddress4(String address4) {
		this.address4 = address4;
	}
	
	public void setAddress5(String address5) {
		this.address5 = address5;
	}
	
	public void setAddress6(String address6) {
		this.address6 = address6;
	}
	
	public void setTags(Set<LocationTag> tags) {
		this.tags = tags;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getAddress7() {
		return address7;
	}
	
	public void setAddress7(String address7) {
		this.address7 = address7;
	}
	
	public String getAddress8() {
		return address8;
	}
	
	public void setAddress8(String address8) {
		this.address8 = address8;
	}
	
	public String getAddress9() {
		return address9;
	}
	
	public void setAddress9(String address9) {
		this.address9 = address9;
	}
	
	public String getAddress10() {
		return address10;
	}
	
	public void setAddress10(String address10) {
		this.address10 = address10;
	}
	
	public String getAddress11() {
		return address11;
	}
	
	public void setAddress11(String address11) {
		this.address11 = address11;
	}
	
	public String getAddress12() {
		return address12;
	}
	
	public void setAddress12(String address12) {
		this.address12 = address12;
	}
	
	public String getAddress13() {
		return address13;
	}
	
	public void setAddress13(String address13) {
		this.address13 = address13;
	}
	
	public String getAddress14() {
		return address14;
	}
	
	public void setAddress14(String address14) {
		this.address14 = address14;
	}
	
	public String getAddress15() {
		return address15;
	}
	
	public void setAddress15(String address15) {
		this.address15 = address15;
	}
	
	public void setParentLocation(Location parentLocation) {
		this.parentLocation = parentLocation;
	}
	
	public void setChildLocations(Set<Location> childLocations) {
		this.childLocations = childLocations;
	}
	
	public void setRetired(Boolean retired) {
		this.retired = retired;
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		if (uuid != null) {
			org.openmrs.Location omrsLocation = Context.getLocationService().getLocationByUuid(uuid);
			if (omrsLocation != null) {
				return omrsLocation;
			}
		}
		
		org.openmrs.Location omrsLocation = Context.getLocationService().getLocation(name);
		// If location with given name already exists then we can just return it.
		if (omrsLocation != null) {
			return omrsLocation;
		}
		
		omrsLocation = new org.openmrs.Location();
		omrsLocation.setId(id);
		omrsLocation.setUuid(uuid);
		omrsLocation.setName(name);
		omrsLocation.setDescription(description);
		omrsLocation.setCityVillage(cityVillage);
		omrsLocation.setStateProvince(stateProvince);
		omrsLocation.setCountry(country);
		omrsLocation.setPostalCode(postalCode);
		omrsLocation.setLatitude(latitude);
		omrsLocation.setLongitude(longitude);
		omrsLocation.setCountyDistrict(countryDistrict);
		omrsLocation.setAddress1(address1);
		omrsLocation.setAddress2(address2);
		omrsLocation.setAddress3(address3);
		omrsLocation.setAddress4(address4);
		omrsLocation.setAddress5(address5);
		omrsLocation.setAddress6(address6);
		if (tags != null) {
			for (LocationTag locationTag : tags) {
				omrsLocation.addTag((org.openmrs.LocationTag) locationTag.getOpenMrsObject());
			}
		}

		if (this.parentLocation != null) {
			omrsLocation.setParentLocation((org.openmrs.Location) this.parentLocation.getOpenMrsObject());
		}
		
		if (this.childLocations != null && !this.childLocations.isEmpty()) {
			@SuppressWarnings("unchecked")
			Set<org.openmrs.Location> childLocs = new HashSet<>((List<org.openmrs.Location>) 
				    ObjectUtils.getOpenmrsResourceObjectsFromMPDResourceObjects( new ArrayList<>(childLocations)));
		omrsLocation.setChildLocations(childLocs);
		}
		
		omrsLocation.setRetired(retired);
		
		return omrsLocation;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		Location location = (Location) o;
		return Objects.equals(uuid, location.uuid) && Objects.equals(name, location.name)
		        && Objects.equals(description, location.description) && Objects.equals(cityVillage, location.cityVillage)
		        && Objects.equals(stateProvince, location.stateProvince) && Objects.equals(country, location.country)
		        && Objects.equals(postalCode, location.postalCode) && Objects.equals(latitude, location.latitude)
		        && Objects.equals(longitude, location.longitude)
		        && Objects.equals(countryDistrict, location.countryDistrict) && Objects.equals(address1, location.address1)
		        && Objects.equals(address2, location.address2) && Objects.equals(address3, location.address3)
		        && Objects.equals(address4, location.address4) && Objects.equals(address5, location.address5)
		        && Objects.equals(address6, location.address6) && Objects.equals(tags, location.tags)
		        && Objects.equals(parentLocation, location.parentLocation)
		        && Objects.equals(childLocations, location.childLocations) && Objects.equals(retired, location.retired);
		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(uuid, name, description, cityVillage, stateProvince, country, postalCode, latitude, longitude,
		    countryDistrict, address1, address2, address3, address4, address5, address6, tags, parentLocation,
		    childLocations, retired);
	}
	
}
