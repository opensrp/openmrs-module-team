package org.openmrs.module.teammodule;

/*import javax.persistence.Access;
 import javax.persistence.AccessType;*/


import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Location;
import org.openmrs.User;

/**
 * @author Muhammad Safwan & Shakeeb Raza & Zohaib Masood
 * 
 */

@SuppressWarnings("serial")
public class TeamMemberLocation extends BaseOpenmrsData implements Serializable {

	private Integer teamMemberLocationId;

	private TeamMember teamMember;
	
	private Location location;
	
	//private Boolean isRetired;
	
	private Date dateCreated;

	private User creator;

	private User changedBy;

	private Date dateChanged;

	private Boolean voided;

	private User voidedBy;

	private String voidedReason;

	private String uuid;
	
	public TeamMemberLocation() {

	}

	public Integer getTeamMemberLocationId() {
		return teamMemberLocationId;
	}

	public void setTeamMemberLocationId(Integer teamMemberLocationId) {
		this.teamMemberLocationId = teamMemberLocationId;
	}

	public TeamMember getTeamMember() {
		return teamMember;
	}

	public void setTeamMember(TeamMember teamMember) {
		this.teamMember = teamMember;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Integer getId() {
		return getTeamMemberLocationId();
	}

	public void setId(Integer arg0) {
		setTeamMemberLocationId(arg0);
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
