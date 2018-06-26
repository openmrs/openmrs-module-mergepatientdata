package org.openmrs.module.mergepatientdata.resource;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.openmrs.Concept;
import org.openmrs.module.mergepatientdata.api.utils.ObjectUtils;

public class Person {
	
	private String uuid;
	
	private PersonName preferredName;
	
	private Address preferredAddress;
	
	private List<PersonName> names;
	
	private List<PersonAddress> addresses;
	
	private Date birthdate;
	
	private Boolean birthdateEstimated;
	
	private Integer age;
	
	private String gender;
	
	private Boolean dead;
	
	private Concept causeOfDeath;
	
	private Date deathDate;
	
	private Boolean voided;
	
	private Boolean deathdateEstimated;
	
	private Date birthtime;
	
	public Person() {
		
	}
	
	public Person(org.openmrs.Person openmrsPerson) {
		this.uuid = openmrsPerson.getUuid();
		this.preferredName = new PersonName(openmrsPerson.getPersonName());
		this.addresses = (List<PersonAddress>) ObjectUtils.getMPDResourceObjectsFromOpenmrsResourceObjects(openmrsPerson
		        .getAddresses());
		this.names = (List<PersonName>) ObjectUtils
		        .getMPDResourceObjectsFromOpenmrsResourceObjects(openmrsPerson.getNames());
		this.birthdate = openmrsPerson.getBirthdate();
		this.birthdateEstimated = openmrsPerson.getBirthdateEstimated();
		this.age = openmrsPerson.getAge();
		this.gender = openmrsPerson.getGender();
		this.dead = openmrsPerson.getDead();
		this.causeOfDeath = openmrsPerson.getCauseOfDeath();
		this.deathDate = openmrsPerson.getDeathDate();
		this.voided = openmrsPerson.getVoided();
		this.deathdateEstimated = openmrsPerson.getDeathdateEstimated();
		this.birthdate = openmrsPerson.getBirthdate();
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public PersonName getPreferredName() {
		return preferredName;
	}
	
	public void setPreferredName(PersonName preferredName) {
		this.preferredName = preferredName;
	}
	
	public Address getPreferredAddress() {
		return preferredAddress;
	}
	
	public void setPreferredAddress(Address preferredAddress) {
		this.preferredAddress = preferredAddress;
	}
	
	public List<PersonName> getNames() {
		return names;
	}
	
	public void setNames(List<PersonName> names) {
		this.names = names;
	}
	
	public List<PersonAddress> getAddresses() {
		return addresses;
	}
	
	public void setAddresses(List<PersonAddress> addresses) {
		this.addresses = addresses;
	}
	
	public Date getBirthdate() {
		return birthdate;
	}
	
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
	public Boolean getBirthdateEstimated() {
		return birthdateEstimated;
	}
	
	public void setBirthdateEstimated(Boolean birthdateEstimated) {
		this.birthdateEstimated = birthdateEstimated;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Boolean getDead() {
		return dead;
	}
	
	public void setDead(Boolean dead) {
		this.dead = dead;
	}
	
	public Concept getCauseOfDeath() {
		return causeOfDeath;
	}
	
	public void setCauseOfDeath(Concept causeOfDeath) {
		this.causeOfDeath = causeOfDeath;
	}
	
	public Date getDeathDate() {
		return deathDate;
	}
	
	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}
	
	public Boolean getVoided() {
		return voided;
	}
	
	public void setVoided(Boolean voided) {
		this.voided = voided;
	}
	
	public Boolean getDeathdateEstimated() {
		return deathdateEstimated;
	}
	
	public void setDeathdateEstimated(Boolean deathdateEstimated) {
		this.deathdateEstimated = deathdateEstimated;
	}
	
	public Date getBirthtime() {
		return birthtime;
	}
	
	public void setBirthtime(Date birthtime) {
		this.birthtime = birthtime;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		Person person = (Person) o;
		return Objects.equals(uuid, person.uuid) && Objects.equals(preferredName, person.preferredName)
		        && Objects.equals(preferredAddress, person.preferredAddress) && Objects.equals(names, person.names)
		        && Objects.equals(addresses, person.addresses)
		        && Objects.equals(birthdateEstimated, person.birthdateEstimated) && Objects.equals(age, person.age)
		        && Objects.equals(gender, person.gender) && Objects.equals(dead, person.dead)
		        && Objects.equals(causeOfDeath, person.causeOfDeath) && Objects.equals(deathDate, person.deathDate)
		        && Objects.equals(voided, person.voided) && Objects.equals(deathdateEstimated, person.deathdateEstimated);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(uuid, preferredName, preferredAddress, names, addresses, birthdateEstimated, age, gender, dead,
		    causeOfDeath, deathDate, voided, deathdateEstimated);
	}
	
}
