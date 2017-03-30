package org.openmrs.module.teammodule;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.module.teammodule.TeamConstants.TeamAction;

public class TeamRoleLog extends BaseOpenmrsData implements Serializable{
	Integer logId;

	TeamHierarchy teamRole;

	TeamAction action;

	@JsonProperty
	String dataOld;

	@JsonProperty
	String dataNew;

	@JsonProperty
	String log;

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public TeamHierarchy getTeamRole() {
		return teamRole;
	}

	public void setTeamRole(TeamHierarchy teamRole) {
		this.teamRole = teamRole;
	}

	public TeamAction getAction() {
		return action;
	}

	public void setAction(TeamAction action) {
		this.action = action;
	}

	public String getDataOld() {
		return dataOld;
	}

	public void setDataOld(String dataOld) {
		this.dataOld = dataOld;
	}

	public String getDataNew() {
		return dataNew;
	}

	public void setDataNew(String dataNew) {
		this.dataNew = dataNew;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setId(Integer arg0) {
		// TODO Auto-generated method stub
		
	}

}
