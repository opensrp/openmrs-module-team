/**
 * 
 */
package org.openmrs.module.teammodule;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//import javax.persistence.ManyToOne;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.User;
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
	
	private Boolean isTeamLead;
		
	private Boolean isRetired;
	
	private Date dateCreated;
	
	private User creator;
	
	private User changedBy;
	
	private Date dateChanged;
	
	private Boolean voided;
	
	private User voidedBy;
	
	private String voidedReason;

	private String uuid;
	
	private Set<Location> location = new HashSet<Location>();
	
	private Set<Patient> patients = new HashSet<Patient>();
	
//	private Set<Team> subTeams = new HashSet<Team>(0);

	private TeamHierarchy teamRole;
	
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

	public Boolean getIsTeamLead() {
		return isTeamLead;
	}

	public void setIsTeamLead(Boolean isTeamLead) {
		this.isTeamLead = isTeamLead;
	}

	public Boolean getIsRetired() {
		return isRetired;
	}


	public void setIsRetired(Boolean isRetired) {
		this.isRetired = isRetired;
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

	public Set<Location> getLocation() {
		return location;
	}

	public void setLocation(Set<Location> location) {
		this.location = location;
	}

	public Set<Patient> getPatients() {
		return patients;
	}

	public void setPatients(Set<Patient> patients) {
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
		return teamRole;
	}

	public void setTeamRole(TeamHierarchy teamRole) {
		this.teamRole = teamRole;
	}
}
