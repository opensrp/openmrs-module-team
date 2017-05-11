package org.openmrs.module.teammodule;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;


@SuppressWarnings("serial")
public class TeamHierarchy extends BaseOpenmrsData implements Serializable {

	private Integer teamHierarchyId;
	
	private String name;
	
	private Boolean ownsTeam;

	private TeamHierarchy reportTo;

	public Integer getTeamHierarchyId() {
		return teamHierarchyId;
	}

	public void setTeamHierarchyId(Integer teamHierarchyId) {
		this.teamHierarchyId = teamHierarchyId;
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
		return getTeamHierarchyId();
	}

	public void setId(Integer arg0) {
		setTeamHierarchyId(arg0);
	}
	

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public TeamHierarchy getReportTo() {
		return reportTo;
	}

	public void setReportTo(TeamHierarchy reportTo) {
		this.reportTo = reportTo;
	}

}
