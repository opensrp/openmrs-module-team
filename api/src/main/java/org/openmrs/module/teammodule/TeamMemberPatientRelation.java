package org.openmrs.module.teammodule;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Location;
import org.openmrs.Patient;

@SuppressWarnings("serial")
public class TeamMemberPatientRelation extends BaseOpenmrsData implements Serializable{

	private Integer teamMemberPatientRelationId;

	private TeamMember teamMember;
	
	private String status;
	
	private Patient patient;

	private Date dateAssigned;

	private Location location;
	
	private String reason;

	public TeamMember getTeamMember() {
		return teamMember;
	}

	public void setTeamMember(TeamMember teamMember) {
		this.teamMember = teamMember;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Date getDateAssigned() {
		return dateAssigned;
	}

	public void setDateAssigned(Date dateAssigned) {
		this.dateAssigned = dateAssigned;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getTeamMemberPatientRelationId() {
		return this.teamMemberPatientRelationId;
	}

	public void setTeamMemberPatientRelationId(Integer teamMemberPatientRelationId) {
		this.teamMemberPatientRelationId = teamMemberPatientRelationId;
	}

	public Integer getId() {
		return getTeamMemberPatientRelationId();
	}

	public void setId(Integer arg0) {
		setTeamMemberPatientRelationId(arg0);
	}
}
