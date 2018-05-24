package org.openmrs.module.mergepatientdata.resource;

import java.util.Date;
import java.util.Objects;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.PersonAddress;

public class Address implements MergeAbleResource {
	
	//Fields
	private String uuid;
    private String address1;
    private String address2;
    private String cityVillage;
    private String stateProvince;
    private String country;
    private String postalCode;
    private String countyDistrict;
    private String address3;
    private String address4;
    private String address5;
    private String address6;
    private Date startDate;
    private Date endDate;
    private String latitude;
    private String longitude;

    public Address() {
    	
    }

    
    public String getFullAddressString() {
        StringBuilder address = new StringBuilder();
        return  address.append(address1).append(",").append(address2).append(",")
                .append(cityVillage).append(",").append(stateProvince).append(",")
                .append(country).append(",").append(postalCode).append(",")
                .append(countyDistrict).append(",").append(address3).append(",")
                .append(address4).append(",").append(address5).append(",")
                .append(address6).append(",").append(startDate).append(",")
                .append(endDate).append(",").append(latitude).append(",")
                .append(longitude)
                .toString();
    }

	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		PersonAddress address = new PersonAddress();
        address.setAddress1(address1);
        address.setAddress2(address2);
        address.setAddress3(address3);
        address.setAddress4(address4);
        address.setAddress5(address5);
        address.setAddress6(address6);
        address.setStartDate(startDate);
        address.setEndDate(endDate);
        address.setLatitude(latitude);
        address.setLongitude(longitude);
        address.setCountyDistrict(countyDistrict);
        address.setPostalCode(postalCode);
        address.setCountry(country);
        address.setStateProvince(stateProvince);
        address.setCityVillage(cityVillage);
        address.setUuid(uuid);
        return address;
	}


	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public String getAddress1() {
		return address1;
	}


	public void setAddress1(String address1) {
		this.address1 = address1;
	}


	public String getAddress2() {
		return address2;
	}


	public void setAddress2(String address2) {
		this.address2 = address2;
	}


	public String getCityVillage() {
		return cityVillage;
	}


	public void setCityVillage(String cityVillage) {
		this.cityVillage = cityVillage;
	}


	public String getStateProvince() {
		return stateProvince;
	}


	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getPostalCode() {
		return postalCode;
	}


	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}


	public String getCountyDistrict() {
		return countyDistrict;
	}


	public void setCountyDistrict(String countyDistrict) {
		this.countyDistrict = countyDistrict;
	}


	public String getAddress3() {
		return address3;
	}


	public void setAddress3(String address3) {
		this.address3 = address3;
	}


	public String getAddress4() {
		return address4;
	}


	public void setAddress4(String address4) {
		this.address4 = address4;
	}


	public String getAddress5() {
		return address5;
	}


	public void setAddress5(String address5) {
		this.address5 = address5;
	}


	public String getAddress6() {
		return address6;
	}


	public void setAddress6(String address6) {
		this.address6 = address6;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Address address = (Address) o;
        return Objects.equals(uuid, address.uuid) &&
                Objects.equals(address1, address.address1) &&
                Objects.equals(address2, address.address2) &&
                Objects.equals(cityVillage, address.cityVillage) &&
                Objects.equals(stateProvince, address.stateProvince) &&
                Objects.equals(country, address.country) &&
                Objects.equals(postalCode, address.postalCode) &&
                Objects.equals(countyDistrict, address.countyDistrict) &&
                Objects.equals(address3, address.address3) &&
                Objects.equals(address4, address.address4) &&
                Objects.equals(address5, address.address5) &&
                Objects.equals(address6, address.address6) &&
                Objects.equals(startDate, address.startDate) &&
                Objects.equals(endDate, address.endDate) &&
                Objects.equals(latitude, address.latitude) &&
                Objects.equals(longitude, address.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, address1, address2, cityVillage, stateProvince, country, postalCode, countyDistrict,
                address3, address4, address5, address6, startDate, endDate, latitude, longitude);
    }

}
