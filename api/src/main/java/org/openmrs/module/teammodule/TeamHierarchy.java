package org.openmrs.module.teammodule;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;
import org.openmrs.BaseOpenmrsData;
public class TeamHierarchy extends BaseOpenmrsData implements Serializable {

	Integer teamRoleId;
	
	String name;
	
	Boolean ownsTeam;

	Set<TeamHierarchy> reportTo=new HashSet<>();
	
	public Integer getTeamRoleId() {
		return teamRoleId;
	}

	public void setTeamRoleId(Integer teamRoleId) {
		this.teamRoleId = teamRoleId;
	}

	public Set<TeamHierarchy> getReportTo() {
		return reportTo;
	}

	public void setReportTo(Set<TeamHierarchy> reportTo) {
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
		// TODO Auto-generated method stub
		return null;
	}

	public void setId(Integer arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
