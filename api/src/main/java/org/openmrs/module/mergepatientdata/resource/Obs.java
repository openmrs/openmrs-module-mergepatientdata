package org.openmrs.module.mergepatientdata.resource;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.openmrs.BaseOpenmrsObject;

public class Obs implements MergeAbleResource {
	
	protected String uuid;
	
	protected Integer obsId;
	
	protected Concept concept;
	
	protected Date obsDatetime;
	
	protected String accessionNumber;
	
	protected Obs obsGroup;
	
	protected Set<Obs> groupMembers;
	
	protected Concept valueCoded;
	
	protected ConceptName valueCodedName;
	
	protected Integer valueGroupId;
	
	protected Date valueDatetime;
	
	protected Double valueNumeric;
	
	protected String valueModifier;
	
	protected String valueText;
	
	protected String valueComplex;
	
	protected String comment;
	
	protected Person person;
	
	protected Location location;
	
	protected Encounter encounter;
	
	private Obs previousVersion;
	
	private String formNamespaceAndPath;
	
	private Boolean dirty = Boolean.FALSE;
	
	private Integer personId;
	
	private Boolean hasGroupMembers;
	
	/** Constructors */
	public Obs() {
		
	}
	
	public Obs(Integer id) {
		this.obsId = id;
	}
	
	public Obs(org.openmrs.Obs obs, Boolean setComplexMetadata) {
		this.uuid = obs.getUuid();
		this.person = new Person (obs.getPerson());
		if (person != null) {
			this.personId = person.getPersonId();
		}
		this.obsId = obs.getId();
		this.concept = new Concept(obs.getConcept(), true);
		this.obsDatetime = obs.getObsDatetime();
		this.accessionNumber = obs.getAccessionNumber();
		this.valueCoded = obs.getValueCoded() != null ? new Concept(obs.getValueCoded(), true) : null;
		this.valueCodedName = obs.getValueCodedName() != null ? new ConceptName(obs.getValueCodedName()) : null;
		this.valueGroupId = obs.getValueGroupId();
		this.valueDatetime = obs.getValueDatetime();
		this.valueNumeric = obs.getValueNumeric();
		this.valueModifier = obs.getValueModifier();
		this.valueText = obs.getValueText();
		this.valueComplex = obs.getValueComplex();
		this.comment = obs.getComment();
		this.location = obs.getLocation() != null ? new Location(obs.getLocation(), true) : null;
		this.dirty = obs.isDirty();
		this.hasGroupMembers = obs.hasGroupMembers();	
		if (setComplexMetadata) {
			this.obsGroup = obs.getObsGroup() != null ? new Obs(obs.getObsGroup(), false) : null;
			this.previousVersion = obs.getPreviousVersion() != null ? new Obs(obs.getPreviousVersion(), false) : null;
			if (obs.getGroupMembers() != null) {
				Set<Obs> mpdObsGroupMembers = new HashSet<>();
				for (org.openmrs.Obs member : obs.getGroupMembers()) {
					Obs mpdMember = new Obs(member, false);
					mpdObsGroupMembers.add(mpdMember);
				}
				this.groupMembers = mpdObsGroupMembers;
			}
		} else {
			// Just set the ids
			this.previousVersion = obs.getPreviousVersion() != null ? new Obs(obs.getPreviousVersion().getId()) : null;
			this.obsGroup = obs.getObsGroup() != null ? new Obs(obs.getObsGroup().getId()) : null;
			if (obs.getGroupMembers() != null) {
				Set<Obs> mpdObsGroupMembers = new HashSet<>();
				for (org.openmrs.Obs member : obs.getGroupMembers()) {
					Obs mpdMember = new Obs(member.getId());
					mpdObsGroupMembers.add(mpdMember);
				}
				this.groupMembers = mpdObsGroupMembers;
			}
		}	
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.Obs obs = new org.openmrs.Obs();
		obs.setUuid(uuid);
		obs.setId(obsId);
		obs.setObsDatetime(obsDatetime);
		obs.setAccessionNumber(accessionNumber);
		obs.setValueGroupId(valueGroupId);
		obs.setValueDatetime(valueDatetime);
		obs.setValueNumeric(valueNumeric);
		obs.setValueModifier(valueModifier);
		obs.setValueText(valueText);
		obs.setValueComplex(valueComplex);
		obs.setComment(comment);
		obs.setObsGroup(conversionHelper(obsGroup));
		if (person != null) {
			obs.setPerson((org.openmrs.Person) person.getOpenMrsObject());
		}
		if (location != null) {
			obs.setLocation((org.openmrs.Location) location.getOpenMrsObject());
		}
		if (previousVersion != null) {
			obs.setPreviousVersion(conversionHelper(previousVersion));
		}
		if (concept != null) {
			obs.setConcept((org.openmrs.Concept) concept.getOpenMrsObject());
		}
		if (valueCoded != null) {
			obs.setValueCoded((org.openmrs.Concept) valueCoded.getOpenMrsObject());
		}
		if (valueCodedName != null) {
			obs.setValueCodedName((org.openmrs.ConceptName) valueCodedName.getOpenMrsObject());
		}
		if (this.groupMembers != null) {
			Set<org.openmrs.Obs> openmrsGroupMembers = new HashSet<>();
			for (Obs member : groupMembers) {
				openmrsGroupMembers.add(conversionHelper(member));
			}
			obs.setGroupMembers(openmrsGroupMembers);
		}
		return obs;	
	}
	
	/**
	 * Convenience method for converting {@link #obsGroup}, {@link #previousVersion} &&
	 * {@link #groupMembers} to {@link org.openmrs.BaseChangeableOpenmrsData} Obs Objects. Note that
	 * {@link #getOpenMrsObject()} would act recursive if an attempt is made to convert these fields
	 * 
	 * @param openmrsObs
	 * @return
	 */
	public org.openmrs.Obs conversionHelper(Obs mpdObs) {
		if (mpdObs == null) {
			return null;
		}
		org.openmrs.Obs obs = new org.openmrs.Obs();
		if (mpdObs.getPerson() != null) {
			obs.setPerson((org.openmrs.Person) mpdObs.getPerson().getOpenMrsObject());
		}
		if (mpdObs.getConcept() != null) {
			obs.setConcept((org.openmrs.Concept) mpdObs.getConcept().getOpenMrsObject());
		}
		if (mpdObs.getValueCoded() != null) {
			obs.setValueCoded((org.openmrs.Concept) mpdObs.getValueCoded().getOpenMrsObject());
		}
		if (mpdObs.getValueCodedName() != null) {
			obs.setValueCodedName((org.openmrs.ConceptName) mpdObs.getValueCodedName().getOpenMrsObject());
		}
		if (mpdObs.getLocation() != null) {
			obs.setLocation((org.openmrs.Location) mpdObs.getLocation().getOpenMrsObject());
		}
		obs.setId(mpdObs.getObsId());
		obs.setObsDatetime(mpdObs.getObsDatetime());
		obs.setAccessionNumber(mpdObs.getAccessionNumber());
		obs.setValueGroupId(mpdObs.getValueGroupId());
		obs.setValueDatetime(mpdObs.getValueDatetime());
		obs.setValueNumeric(mpdObs.getValueNumeric());
		obs.setValueModifier(mpdObs.getValueModifier());
		obs.setValueText(mpdObs.getValueText());
		obs.setValueComplex(mpdObs.getValueComplex());
		obs.setComment(mpdObs.getComment());
		return obs;
	}
	
	public Integer getObsId() {
		return obsId;
	}
	
	public void setObsId(Integer obsId) {
		this.obsId = obsId;
	}
	
	public Concept getConcept() {
		return concept;
	}
	
	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	
	public Date getObsDatetime() {
		return obsDatetime;
	}
	
	public void setObsDatetime(Date obsDatetime) {
		this.obsDatetime = obsDatetime;
	}
	
	public String getAccessionNumber() {
		return accessionNumber;
	}
	
	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}
	
	public Obs getObsGroup() {
		return obsGroup;
	}
	
	public void setObsGroup(Obs obsGroup) {
		this.obsGroup = obsGroup;
	}
	
	public Obs getPreviousVersion() {
		return previousVersion;
	}
	
	public void setPreviousVersion(Obs previousVersion) {
		this.previousVersion = previousVersion;
	}
	
	public Boolean getDirty() {
		return dirty;
	}
	
	public void setDirty(Boolean dirty) {
		this.dirty = dirty;
	}
	
	public String getFormNamespaceAndPath() {
		return formNamespaceAndPath;
	}
	
	public void setFormNamespaceAndPath(String formNamespaceAndPath) {
		this.formNamespaceAndPath = formNamespaceAndPath;
	}
	
	public Integer getPersonId() {
		return personId;
	}
	
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	
	public Boolean getHasGroupMembers() {
		return hasGroupMembers;
	}
	
	public void setHasGroupMembers(Boolean hasGroupMembers) {
		this.hasGroupMembers = hasGroupMembers;
	}
	
	public Set<Obs> getGroupMembers() {
		return groupMembers;
	}
	
	public void setGroupMembers(Set<Obs> groupMembers) {
		this.groupMembers = groupMembers;
	}
	
	public Concept getValueCoded() {
		return valueCoded;
	}
	
	public void setValueCoded(Concept valueCoded) {
		this.valueCoded = valueCoded;
	}
	
	public ConceptName getValueCodedName() {
		return valueCodedName;
	}
	
	public void setValueCodedName(ConceptName valueCodedName) {
		this.valueCodedName = valueCodedName;
	}
	
	public Integer getValueGroupId() {
		return valueGroupId;
	}
	
	public void setValueGroupId(Integer valueGroupId) {
		this.valueGroupId = valueGroupId;
	}
	
	public Date getValueDatetime() {
		return valueDatetime;
	}
	
	public void setValueDatetime(Date valueDatetime) {
		this.valueDatetime = valueDatetime;
	}
	
	public Double getValueNumeric() {
		return valueNumeric;
	}
	
	public void setValueNumeric(Double valueNumeric) {
		this.valueNumeric = valueNumeric;
	}
	
	public String getValueModifier() {
		return valueModifier;
	}
	
	public void setValueModifier(String valueModifier) {
		this.valueModifier = valueModifier;
	}
	
	public String getValueText() {
		return valueText;
	}
	
	public void setValueText(String valueText) {
		this.valueText = valueText;
	}
	
	public String getValueComplex() {
		return valueComplex;
	}
	
	public void setValueComplex(String valueComplex) {
		this.valueComplex = valueComplex;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Encounter getEncounter() {
		return encounter;
	}
	
	public void setEncounter(Encounter encounter) {
		this.encounter = encounter;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
