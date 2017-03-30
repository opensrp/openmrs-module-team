package org.openmrs.module.teammodule;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.module.teammodule.TeamConstants.TeamAction;

public class TeamLog extends BaseOpenmrsData implements Serializable{
	Integer logId;

	Team team;

	TeamAction action;

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

	public TeamAction getAction() {
		return action;
	}

	public void setAction(TeamAction action) {
		this.action = action;
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
