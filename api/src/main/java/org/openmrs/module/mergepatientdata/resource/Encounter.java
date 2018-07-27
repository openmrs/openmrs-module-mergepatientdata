package org.openmrs.module.mergepatientdata.resource;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.BaseOpenmrsObject;

public class Encounter extends BaseOpenmrsMetadata implements MergeAbleResource {
	
	private static final long serialVersionUID = 1L;
	
	private Integer encounterId;
	
	private Date encounterDatetime;
	
	private Patient patient;
	
	private Location location;
	
	private Form form;
	
	private EncounterType encounterType;
	
	private Set<Obs> obs;
	
	public Encounter() {
		
	}
	
	public Encounter(org.openmrs.Encounter encounter, Boolean initializeComplexMetaData) {
		this.setEncounterId(encounter.getEncounterId());
		this.setUuid(encounter.getUuid());
		this.encounterDatetime = encounter.getEncounterDatetime();
		this.patient = new Patient(encounter.getPatient());
		this.location = new Location(encounter.getLocation(), true);
		this.form = new Form(encounter.getForm(), true);
		this.encounterType = new EncounterType(encounter.getEncounterType());
		if (initializeComplexMetaData) {
			if (encounter.getObs() != null) {
				Set<Obs> observations = new HashSet<>();
				for (org.openmrs.Obs obs : encounter.getObs()) {
					observations.add(new Obs(obs, true));
				}
				this.setObs(observations);
			}
		}
	}
	
	@Override
	public Integer getId() {
		return encounterId;
	}
	
	@Override
	public void setId(Integer id) {
		setEncounterId(id);
	}
	
	@Override
	public BaseOpenmrsObject getOpenMrsObject() {
		org.openmrs.Encounter encounter = new org.openmrs.Encounter();
		encounter.setEncounterId(encounterId);
		encounter.setUuid(getUuid());
		encounter.setEncounterDatetime(encounterDatetime);
		if (this.patient != null) {
			encounter.setPatient((org.openmrs.Patient) patient.getOpenMrsObject());
		}
		if (this.location != null) {
			encounter.setLocation((org.openmrs.Location) location.getOpenMrsObject());
		}
		if (this.form != null) {
			encounter.setForm((org.openmrs.Form) form.getOpenMrsObject());
		}
		if (this.encounterType != null) {
			encounter.setEncounterType((org.openmrs.EncounterType) encounterType.getOpenMrsObject());
		}
		if (this.getObs() != null) {
			Set<org.openmrs.Obs> observations = new HashSet<>();
			for (Obs obs : this.getObs()) {
				org.openmrs.Obs observation = (org.openmrs.Obs) obs.getOpenMrsObject();
				observations.add(observation);
			}
			encounter.setObs(observations);
		}
		return encounter;
	}
	
	public Integer getEncounterId() {
		return encounterId;
	}
	
	public void setEncounterId(Integer encounterId) {
		this.encounterId = encounterId;
	}
	
	public Date getEncounterDatetime() {
		return encounterDatetime;
	}
	
	public void setEncounterDatetime(Date encounterDatetime) {
		this.encounterDatetime = encounterDatetime;
	}
	
	public Patient getPatient() {
		return patient;
	}
	
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Form getForm() {
		return form;
	}
	
	public void setForm(Form form) {
		this.form = form;
	}
	
	public EncounterType getEncounterType() {
		return encounterType;
	}
	
	public void setEncounterType(EncounterType encounterType) {
		this.encounterType = encounterType;
	}
	
	public Set<Obs> getObs() {
		return obs;
	}
	
	public void setObs(Set<Obs> obs) {
		this.obs = obs;
	}
	
}
