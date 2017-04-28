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
	
	private Set<Location> location = new HashSet<Location>();
	
	private Set<TeamMemberPatientRelation> patients = new HashSet<TeamMemberPatientRelation>();
	
//	private Set<Team> subTeams = new HashSet<Team>(0);

	private TeamHierarchy teamHierarchy;
	
	private String provider;
	
	private boolean isDataProvider;
	
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

	public Set<Location> getLocation() {
		return location;
	}

	public void setLocation(Set<Location> location) {
		this.location = location;
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

	public TeamHierarchy getTeamRole() {
		return getTeamHierarchy();
	}

	public void setTeamRole(TeamHierarchy teamRole) {
		this.setTeamHierarchy(teamRole);
	}

	public TeamHierarchy getTeamHierarchy() {
		return teamHierarchy;
	}

	public void setTeamHierarchy(TeamHierarchy teamHierarchy) {
		this.teamHierarchy = teamHierarchy;
	}
	
	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	public boolean getIsDataProvider() {
		return isDataProvider;
	}

	public void setIsDataProvider(boolean isDataProvider) {
		this.isDataProvider = isDataProvider;
	}
}
