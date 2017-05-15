/**
 * 
 */
package org.openmrs.module.teammodule;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.Location;
import org.openmrs.Person;
import org.openmrs.module.teammodule.Team;


/**
 * @author Muhammad Safwan
 *
 */

@SuppressWarnings("serial")
public class TeamMember extends BaseOpenmrsData implements Serializable {
	
	private Integer teamMemberId;

	private String identifier;
	
	private Team team;
	
	private Person person;
	
	private Date joinDate;
	
	private Date leaveDate;
	
	private Set<Location> locations = new HashSet<Location>();
	
	private Set<TeamMemberPatientRelation> patients = new HashSet<TeamMemberPatientRelation>();
	
	private TeamRole teamRole;
		
	private Boolean isDataProvider;
	
	public TeamMember() { }

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}


	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Person getPerson() {
		return person;
	}


	public void setPerson(Person person) {
		this.person = person;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public Set<Location> getLocations() {
		return locations;
	}

	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}

	public Set<TeamMemberPatientRelation> getPatients() {
		return patients;
	}

	public void setPatients(Set<TeamMemberPatientRelation> patients) {
		this.patients = patients;
	}

	public Integer getTeamMemberId() {
		return teamMemberId;
	}

	public void setTeamMemberId(Integer teamMemberId) {
		this.teamMemberId = teamMemberId;
		
	}
	
	public Integer getId() {
		return getTeamMemberId();
	}


	public void setId(Integer arg0) {
		setTeamMemberId(arg0);
	}	

	public TeamRole getTeamRole() {
		return teamRole;
	}

	public void setTeamRole(TeamRole teamRole) {
		this.teamRole = teamRole;
	}
	
	public Boolean getIsDataProvider() {
		return isDataProvider;
	}

	public void setIsDataProvider(Boolean isDataProvider) {
		this.isDataProvider = isDataProvider;
	}
}
