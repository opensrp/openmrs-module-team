package org.openmrs.module.teammodule;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Location;
import org.openmrs.Patient;

public class TeamMemberPatientRelation extends BaseOpenmrsData implements Serializable{

	private int id;

	private TeamMember member;
	
	private String status;
	
	private Patient patient;

	private Date assignmentDate;
	
	private Location location;
	
	private String reason;

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

	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
		
	}
	
	
}
