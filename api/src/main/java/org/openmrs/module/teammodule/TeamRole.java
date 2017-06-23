package org.openmrs.module.teammodule;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;


@SuppressWarnings("serial")
public class TeamRole extends BaseOpenmrsData implements Serializable {

	private Integer teamRoleId;
	
	private String name;

	private String identifier;

	private Boolean ownsTeam;

	private TeamRole reportTo;

	public Integer getTeamRoleId() {
		return teamRoleId;
	}

	public void setTeamRoleId(Integer teamRoleId) {
		this.teamRoleId = teamRoleId;
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

	public TeamRole getReportTo() {
		return reportTo;
	}

	public void setReportTo(TeamRole reportTo) {
		this.reportTo = reportTo;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
}
