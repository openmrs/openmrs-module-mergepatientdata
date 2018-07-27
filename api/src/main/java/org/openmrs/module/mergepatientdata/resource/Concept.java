package org.openmrs.module.mergepatientdata.resource;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.OpenmrsObject;
import org.openmrs.customdatatype.CustomValueDescriptor;
import org.openmrs.customdatatype.Customizable;
import org.openmrs.module.mergepatientdata.api.utils.ObjectUtils;

// By default all Users should be Super Users
public class Concept implements Customizable<ConceptAttribute>, MergeAbleResource {
	
    private Integer conceptId;
    
    private String uuid;
	
	private Boolean retired = false;
	
	private User retiredBy;
	
	private Date dateRetired;
	
	private String retireReason;
		
	private ConceptDatatype datatype;
	
	private ConceptClass conceptClass;
	
	private Boolean set = false;
	
	private String version;
	
	private User creator;
	
	private Date dateCreated;
	
	private User changedBy;
	
	private Date dateChanged;
	
	private Collection<ConceptName> names;
	
	private Collection<ConceptAnswer> answers;
	
	private Collection<ConceptSet> conceptSets;
	
	private Collection<ConceptDescription> descriptions;
	
	private Collection<ConceptMap> conceptMappings;
	
	private Map<Locale, List<ConceptName>> compatibleCache;

	private Set<ConceptAttribute> attributes = new LinkedHashSet<>();
	
	public Concept() {}

	@SuppressWarnings("unchecked")
	public Concept(org.openmrs.Concept concept, Boolean setComplexMetadata) {
		if (concept == null) {
			return;
		}
		this.conceptId = concept.getId();
		this.uuid = concept.getUuid();
		this.retired = concept.getRetired();
		this.datatype = new ConceptDatatype(concept.getDatatype());
		this.conceptClass = new ConceptClass(concept.getConceptClass());
		this.retired = concept.getRetired();
		this.dateCreated = concept.getDateCreated();
		this.retireReason = concept.getRetireReason();
		this.dateRetired = concept.getDateRetired();
		
		if (setComplexMetadata) {
			this.names = (Collection<ConceptName>) ObjectUtils.getMPDResourceObjectsFromOpenmrsResourceObjects(
		    		(Set<? extends OpenmrsObject>) concept.getNames());
		    if (concept.getAnswers() != null && !concept.getAnswers().isEmpty()) {
		    	this.answers = (Collection<ConceptAnswer>) ObjectUtils.getMPDResourceObjectsFromOpenmrsResourceObjects(
			    		(Set<? extends OpenmrsObject>) concept.getAnswers());
		    }
		    if (concept.getDescription() != null) {
		    	this.descriptions = (Collection<ConceptDescription>) ObjectUtils.getMPDResourceObjectsFromOpenmrsResourceObjects(
			    		(Set<? extends OpenmrsObject>) concept.getDescriptions());
		    }
		    if (concept.getConceptSets() != null && !concept.getConceptSets().isEmpty()) {
		    	this.conceptSets = (Collection<ConceptSet>) ObjectUtils.getMPDResourceObjectsFromOpenmrsResourceObjects(
			    		(Set<? extends OpenmrsObject>) concept.getConceptSets());
		    }
		    if (concept.getAttributes() != null && !concept.getAttributes().isEmpty()) {
		    	this.attributes = (Set<ConceptAttribute>) ObjectUtils.getMPDResourceObjectsFromOpenmrsResourceObjects(
			    		(Set<? extends OpenmrsObject>) concept.getAttributes());
		    } 
		    if (concept.getConceptMappings() != null && !concept.getConceptMappings().isEmpty()) {
		    	this.conceptMappings = (Set<ConceptMap>) ObjectUtils.getMPDResourceObjectsFromOpenmrsResourceObjects(
			    		(Set<? extends OpenmrsObject>) concept.getConceptMappings());
		    }
		}
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.Concept openmrsConcept = new org.openmrs.Concept();
		openmrsConcept.setConceptId(conceptId);
		openmrsConcept.setUuid(uuid);
		openmrsConcept.setRetired(retired);
		openmrsConcept.setDatatype((org.openmrs.ConceptDatatype) datatype.getOpenMrsObject());
		openmrsConcept.setConceptClass((org.openmrs.ConceptClass) conceptClass.getOpenMrsObject());
		openmrsConcept.setRetired(retired);
		openmrsConcept.setDateCreated(dateCreated);
		openmrsConcept.setRetireReason(retireReason);
		openmrsConcept.setDateRetired(dateRetired);
		Collection<org.openmrs.ConceptName> openmrsConceptNames = new HashSet<>();
		Collection<org.openmrs.ConceptAnswer> openmrsConceptAnswers = new HashSet<>();
		Collection<org.openmrs.ConceptDescription> openmrsDecriptions = new HashSet<>();
		Collection<org.openmrs.ConceptMap> openmrsConceptMappings = new HashSet<>();
		Collection<org.openmrs.ConceptSet> openmrsConceptSets = new HashSet<>();
		Set<org.openmrs.ConceptAttribute> openmrsConceptAttributes = new LinkedHashSet<>();
		if (this.names != null) {
			for (ConceptName name : names) {
				org.openmrs.ConceptName openmrsConceptName = (org.openmrs.ConceptName) name.getOpenMrsObject();
				openmrsConceptNames.add(openmrsConceptName);
			}
		}
		if (this.answers != null) {
			for (ConceptAnswer answer : answers) {
				org.openmrs.ConceptAnswer openmrsAnswer = (org.openmrs.ConceptAnswer) answer.getOpenMrsObject();
				openmrsConceptAnswers.add(openmrsAnswer);
			}	
		}
		if (this.descriptions != null) {
			for (ConceptDescription desc : descriptions) {
				org.openmrs.ConceptDescription openmrsDescription = (org.openmrs.ConceptDescription) desc.getOpenMrsObject();
				openmrsDecriptions.add(openmrsDescription);
			}
		}
		if (this.conceptMappings != null) {
			for (ConceptMap map : conceptMappings) {
				org.openmrs.ConceptMap openmrsConceptMapping = (org.openmrs.ConceptMap) map.getOpenMrsObject();
				openmrsConceptMappings.add(openmrsConceptMapping);
			}	
		}
		for (ConceptAttribute attribute : attributes) {
			org.openmrs.ConceptAttribute openmrsAttribute = (org.openmrs.ConceptAttribute) attribute.getOpenMrsObject();
			openmrsConceptAttributes.add(openmrsAttribute);
		}
		if (this.conceptSets != null) {
			for (ConceptSet set : conceptSets) {
				org.openmrs.ConceptSet openmrsSet = (org.openmrs.ConceptSet) set.getOpenMrsObject();
				openmrsConceptSets.add(openmrsSet);
			}
		}
		
		openmrsConcept.setNames(openmrsConceptNames);
		openmrsConcept.setAnswers(openmrsConceptAnswers);
		openmrsConcept.setConceptMappings(openmrsConceptMappings);
		openmrsConcept.setDescriptions(openmrsDecriptions);
		openmrsConcept.setConceptMappings(openmrsConceptMappings);
		return openmrsConcept;
	}

	@Override
	public List<ConceptAttribute> getActiveAttributes() {
		return getAttributes().stream()
				.filter(attr -> !attr.getVoided())
				.collect(Collectors.toList());
	}

	@Override
	public List<ConceptAttribute> getActiveAttributes(CustomValueDescriptor ofType) {
		return getAttributes().stream()
				.filter(attr -> attr.getAttributeType().equals(ofType) && !attr.getVoided())
				.collect(Collectors.toList());
	}

	@Override
	public Set<ConceptAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new LinkedHashSet<ConceptAttribute>();
		}
		return attributes;
	}

	@Override
	public void addAttribute(ConceptAttribute attribute) {
		getAttributes().add(attribute);
		attribute.setOwner(this);
	}

	public Integer getConceptId() {
		return conceptId;
	}

	public void setConceptId(Integer conceptId) {
		this.conceptId = conceptId;
	}

	public Boolean getRetired() {
		return retired;
	}

	public void setRetired(Boolean retired) {
		this.retired = retired;
	}

	public User getRetiredBy() {
		return retiredBy;
	}

	public void setRetiredBy(User retiredBy) {
		this.retiredBy = retiredBy;
	}

	public Date getDateRetired() {
		return dateRetired;
	}

	public void setDateRetired(Date dateRetired) {
		this.dateRetired = dateRetired;
	}

	public String getRetireReason() {
		return retireReason;
	}

	public void setRetireReason(String retireReason) {
		this.retireReason = retireReason;
	}

	public ConceptDatatype getDatatype() {
		return datatype;
	}

	public void setDatatype(ConceptDatatype datatype) {
		this.datatype = datatype;
	}

	public ConceptClass getConceptClass() {
		return conceptClass;
	}

	public void setConceptClass(ConceptClass conceptClass) {
		this.conceptClass = conceptClass;
	}

	public Boolean getSet() {
		return set;
	}

	public void setSet(Boolean set) {
		this.set = set;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

	public Collection<ConceptName> getNames() {
		return names;
	}

	public void setNames(Collection<ConceptName> names) {
		this.names = names;
	}

	public Collection<ConceptAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(Collection<ConceptAnswer> answers) {
		this.answers = answers;
	}

	public Collection<ConceptSet> getConceptSets() {
		return conceptSets;
	}

	public void setConceptSets(Collection<ConceptSet> conceptSets) {
		this.conceptSets = conceptSets;
	}

	public Collection<ConceptDescription> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(Collection<ConceptDescription> descriptions) {
		this.descriptions = descriptions;
	}

	public Collection<ConceptMap> getConceptMappings() {
		return conceptMappings;
	}

	public void setConceptMappings(Collection<ConceptMap> conceptMappings) {
		this.conceptMappings = conceptMappings;
	}

	public Map<Locale, List<ConceptName>> getCompatibleCache() {
		return compatibleCache;
	}

	public void setCompatibleCache(Map<Locale, List<ConceptName>> compatibleCache) {
		this.compatibleCache = compatibleCache;
	}

	public void setAttributes(Set<ConceptAttribute> attributes) {
		this.attributes = attributes;
	}
    
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
