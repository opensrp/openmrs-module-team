package org.openmrs.module.teammodule;

/*import javax.persistence.Access;
 import javax.persistence.AccessType;*/


import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Location;
import org.openmrs.module.teammodule.*;
/**
 * @author Muhammad Safwan & shakeeb raza
 * 
 */

 //@Access(AccessType.PROPERTY)
@SuppressWarnings("serial")
public class Team extends BaseOpenmrsData implements Serializable {

	private Integer teamId;

	private String teamIdentifier;

	private String teamName;
	
	private TeamMember supervisor;
	
	private String uuid;
	
	private Boolean isRetired;

	private Location location;
	
	
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Team() {

	}

	//@Access(AccessType.PROPERTY)
	public Integer getTeamId() {
		return this.teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public String getTeamIdentifier() {
		return teamIdentifier;
	}

	public void setTeamIdentifier(String teamIdentifier) {
		this.teamIdentifier = teamIdentifier;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Integer getId() {
		return getTeamId();
	}

	public void setId(Integer arg0) {
		setTeamId(arg0);
	}

	public TeamMember getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(TeamMember supervisor) {
		this.supervisor = supervisor;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Boolean IsRetired() {
		return isRetired;
	}

	public void IsRetired(Boolean isRetired) {
		this.isRetired = isRetired;
	}
}
