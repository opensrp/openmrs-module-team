package org.openmrs.module.teammodule;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;


@SuppressWarnings("serial")
public class TeamHierarchy extends BaseOpenmrsData implements Serializable {

	private Integer teamRoleId;
	
	private String name;
	
	private Boolean ownsTeam;

	private TeamMember reportTo;

	public Integer getTeamRoleId() {
		return teamRoleId;
	}

	public void setTeamRoleId(Integer teamRoleId) {
		this.teamRoleId = teamRoleId;
	}

	public TeamMember getReportTo() {
		return reportTo;
	}

	public void setReportTo(TeamMember reportTo) {
		this.reportTo = reportTo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getOwnsTeam() {
		return ownsTeam;
	}

	public void setOwnsTeam(Boolean ownsTeam) {
		this.ownsTeam = ownsTeam;
	}

	public Integer getId() {
		return getTeamRoleId();
	}

	public void setId(Integer arg0) {
		setTeamRoleId(arg0);
	}
	

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

}
