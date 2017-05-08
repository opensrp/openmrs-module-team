package org.openmrs.module.teammodule;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.codehaus.jackson.annotate.JsonProperty;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;
import org.openmrs.module.teammodule.TeamConstants.TeamAction;

@SuppressWarnings("serial")
public class TeamLog extends BaseOpenmrsData implements Serializable {

	private Integer logId;

	private Team team;

	private String action;

	@JsonProperty
	private String dataNew;

	@JsonProperty
	private String dataOld;

	@JsonProperty
	private String log;

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(TeamAction action) {
		this.action = action.name();
	}
	
	 void setAction(String action) {
		this.action = action;
	}

	public String getDataNew() {
		return dataNew;
	}

	public void setDataNew(String dataNew) {
		this.dataNew = dataNew;
	}

	public String getDataOld() {
		return dataOld;
	}

	public void setDataOld(String dataOld) {
		this.dataOld = dataOld;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
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
}
