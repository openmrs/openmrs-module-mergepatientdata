package org.openmrs.module.mergepatientdata.resource;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.ConceptNameType;
import org.openmrs.module.mergepatientdata.api.utils.ObjectUtils;

public class ConceptName implements MergeAbleResource {
	
	private Integer conceptNameId;
	
	private Concept concept;
	
	private String name;
	
	private Locale locale;
	
	private User creator;
	
	private Date dateCreated;
	
	private Boolean voided = false;
	
	private User voidedBy;
	
	private Date dateVoided;
	
	private String voidReason;
	
	private String uuid;
	
	private Collection<ConceptNameTag> tags;
	
	// For now am using Openmrs type. I think I won't cause Hibernate issues
	private ConceptNameType conceptNameType;
	
	private Boolean localePreferred = false;
	
	private User changedBy;
	
	private Date dateChanged;
	
	@SuppressWarnings("unchecked")
	public ConceptName(org.openmrs.ConceptName conceptName) {
		this.conceptNameId = conceptName.getConceptNameId();
		this.name = conceptName.getName();
		this.voided = conceptName.getVoided();
		this.dateVoided = conceptName.getDateVoided();
		this.dateCreated = conceptName.getDateCreated();
		this.locale = conceptName.getLocale();
		if (conceptName.getTags() != null && !conceptName.getTags().isEmpty()) {
			this.tags = (Collection<ConceptNameTag>) ObjectUtils
			        .getMPDResourceObjectsFromOpenmrsResourceObjects((Set<? extends OpenmrsObject>) conceptName.getTags());
		}
		this.conceptNameType = conceptName.getConceptNameType();
		this.concept = new Concept(conceptName.getConcept(), false);
		this.uuid = conceptName.getUuid();
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.ConceptName openmrsConceptName = new org.openmrs.ConceptName();
		openmrsConceptName.setConceptNameId(conceptNameId);
		openmrsConceptName.setName(name);
		openmrsConceptName.setVoided(voided);
		openmrsConceptName.setDateCreated(dateCreated);
		openmrsConceptName.setLocale(locale);
		Collection<org.openmrs.ConceptNameTag> tags = new HashSet<>();
		if (this.tags != null) {
			for (ConceptNameTag tag : this.tags) {
				org.openmrs.ConceptNameTag openmrsTag = (org.openmrs.ConceptNameTag) tag.getOpenMrsObject();
				tags.add(openmrsTag);
			}
		}
		openmrsConceptName.setTags(tags);
		openmrsConceptName.setConceptNameType(conceptNameType);
		openmrsConceptName.setConcept((org.openmrs.Concept) concept.getOpenMrsObject());
		openmrsConceptName.setUuid(uuid);
		return openmrsConceptName;
	}
	
	public Integer getConceptNameId() {
		return conceptNameId;
	}
	
	public void setConceptNameId(Integer conceptNameId) {
		this.conceptNameId = conceptNameId;
	}
	
	public Concept getConcept() {
		return concept;
	}
	
	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Locale getLocale() {
		return locale;
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public User getCreator() {
		return creator;
	}
	
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public Boolean getVoided() {
		return voided;
	}
	
	public void setVoided(Boolean voided) {
		this.voided = voided;
	}
	
	public User getVoidedBy() {
		return voidedBy;
	}
	
	public void setVoidedBy(User voidedBy) {
		this.voidedBy = voidedBy;
	}
	
	public Date getDateVoided() {
		return dateVoided;
	}
	
	public void setDateVoided(Date dateVoided) {
		this.dateVoided = dateVoided;
	}
	
	public String getVoidReason() {
		return voidReason;
	}
	
	public void setVoidReason(String voidReason) {
		this.voidReason = voidReason;
	}
	
	public Collection<ConceptNameTag> getTags() {
		return tags;
	}
	
	public void setTags(Collection<ConceptNameTag> tags) {
		this.tags = tags;
	}
	
	public ConceptNameType getConceptNameType() {
		return conceptNameType;
	}
	
	public void setConceptNameType(ConceptNameType conceptNameType) {
		this.conceptNameType = conceptNameType;
	}
	
	public Boolean getLocalePreferred() {
		return localePreferred;
	}
	
	public void setLocalePreferred(Boolean localePreferred) {
		this.localePreferred = localePreferred;
	}
	
	public User getChangedBy() {
		return changedBy;
	}
	
	public void setChangedBy(User changedBy) {
		this.changedBy = changedBy;
	}
	
	public Date getDateChanged() {
		return dateChanged;
	}
	
	public void setDateChanged(Date dateChanged) {
		this.dateChanged = dateChanged;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
