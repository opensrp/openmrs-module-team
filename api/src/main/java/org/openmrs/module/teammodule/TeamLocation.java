package org.openmrs.module.teammodule;

/*import javax.persistence.Access;
 import javax.persistence.AccessType;*/


import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Location;
import org.openmrs.User;

/**
 * @author Muhammad Safwan & shakeeb raza
 * 
 */

 //@Access(AccessType.PROPERTY)
@SuppressWarnings("serial")
public class TeamLocation extends BaseOpenmrsData implements Serializable {

	private Integer teamLocationId;

	private Team team;
	
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
	
	public TeamLocation() {

	}

	public Integer getTeamLocationId() {
		return teamLocationId;
	}

	public void setTeamLocationId(Integer teamLocationId) {
		this.teamLocationId = teamLocationId;
	}
	
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public Integer getId() {
		return getTeamLocationId();
	}

	@Override
	public void setId(Integer arg0) {
		setTeamLocationId(arg0);
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
