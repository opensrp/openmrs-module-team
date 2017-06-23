package org.openmrs.module.teammodule;

import java.io.Serializable;


import org.codehaus.jackson.annotate.JsonProperty;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;

@SuppressWarnings("serial")
public class TeamRoleLog extends BaseOpenmrsData implements Serializable{

	private Integer logId;

	private TeamRole teamRole;

	private String action;

	@JsonProperty
	private String dataOld;

	@JsonProperty
	private String dataNew;

	@JsonProperty
	private String log;
	
	private String uuid;

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public TeamRole getTeamRole() {
		return teamRole;
	}

	public void setTeamRole(TeamRole teamRole) {
		this.teamRole = teamRole;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
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
		return getLogId();
	}

	public void setId(Integer arg0) {
		setLogId(arg0);		
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
