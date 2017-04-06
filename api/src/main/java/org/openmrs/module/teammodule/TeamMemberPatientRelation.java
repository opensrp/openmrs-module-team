package org.openmrs.module.teammodule;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.User;

@SuppressWarnings("serial")
public class TeamMemberPatientRelation extends BaseOpenmrsData implements Serializable{

	private Integer teamMemberPatientId;

	private TeamMember member;
	
	private String status;
	
	private Patient patient;

	private Date assignmentDate;

	private Location location;
	
	private String reason;
	
	private Date dateCreated;

	private User creator;

	private User changedBy;

	private Date dateChanged;

	private Boolean voided;

	private User voidedBy;

	private String voidedReason;

	private String uuid;

	public TeamMember getMember() {
		return member;
	}

	public void setMember(TeamMember member) {
		this.member = member;
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

	public Date getAssignmentDate() {
		return assignmentDate;
	}

	public void setAssignmentDate(Date assignmentDate) {
		this.assignmentDate = assignmentDate;
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

	public Integer getTeamMemberPatientId() {
		return this.teamMemberPatientId;
	}

	public void setTeamMemberPatientId(Integer teamMemberPatientId) {
		this.teamMemberPatientId = teamMemberPatientId;
	}

	public Integer getId() {
		return getTeamMemberPatientId();
	}

	public void setId(Integer arg0) {
		setTeamMemberPatientId(arg0);
	}

	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public User getCreator() {
		return creator;
	}
	
	public void setCreator(User creator) {
		this.creator = creator;
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
	
	public String getVoidedReason() {
		return voidedReason;
	}
	
	public void setVoidedReason(String voidedReason) {
		this.voidedReason = voidedReason;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}	
}
