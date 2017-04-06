/**
 * 
 */
package org.openmrs.module.teammodule;

import java.io.Serializable;

//import java.util.List;

import java.util.Date;

import org.openmrs.BaseOpenmrsData;

/**
 * @author Muhammad Safwan
 * 
 */

@SuppressWarnings("serial")
public class TeamLead extends BaseOpenmrsData implements Serializable {

	private int teamLeadId;
	
	//private String isTeamLead;

	private Integer teamId;

	private Team team;

	private int teamMemberId;

	private TeamMember teamMember;

	private Date joinDate;
	
	private Date leaveDate;
	
	private String uuid;

	//private List<TeamMember> teamMembers;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getPersonId() {
		return teamLeadId;
	}

	public void setPersonId(Integer personId) {
		this.teamLeadId = personId;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	/*public List<TeamMember> getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(List<TeamMember> teamMembers) {
		this.teamMembers = teamMembers;
	}*/

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public int getTeamMemberId() {
		return teamMemberId;
	}

	public void setTeamMemberId(int teamMemberId) {
		this.teamMemberId = teamMemberId;
	}

	public TeamMember getTeamMember() {
		return teamMember;
	}

	public void setTeamMember(TeamMember teamMember) {
		this.teamMember = teamMember;
	}

	public Integer getId() {
		return getTeamId();
	}

	public void setId(Integer arg0) {
		setTeamId(arg0);
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

/*	public String getIsTeamLead() {
		return isTeamLead;
	}

	public void setIsTeamLead(String isTeamLead) {
		this.isTeamLead = isTeamLead;
	}*/

	public int getTeamLeadId() {
		return teamLeadId;
	}

	public void setTeamLeadId(int teamLeadId) {
		this.teamLeadId = teamLeadId;
	}
}
