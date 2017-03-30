package org.openmrs.module.teammodule;

/*import javax.persistence.Access;
 import javax.persistence.AccessType;*/


import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Location;

/**
 * @author Muhammad Safwan & shakeeb raza
 * 
 */

 //@Access(AccessType.PROPERTY)
public class TeamLocation extends BaseOpenmrsData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer teamLocationId;

	private Team team;
	
	private Location location;
	
	//private Boolean isRetired;

	
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Integer arg0) {
		// TODO Auto-generated method stub
		
	}


}
