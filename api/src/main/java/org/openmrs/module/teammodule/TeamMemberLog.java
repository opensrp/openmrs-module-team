package org.openmrs.module.teammodule;

import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonProperty;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.module.teammodule.TeamMember;


@SuppressWarnings("serial")
public class TeamMemberLog extends BaseOpenmrsData implements Serializable{

	private Integer logId;

	private TeamMember teamMember;

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

	public void setAction(String action) {
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

	public TeamMember getTeamMember() {
		return teamMember;
	}

	public void setTeamMember(TeamMember teamMember) {
		this.teamMember = teamMember;
	}

	public Integer getId() {
		return getLogId();
	}

	public void setId(Integer arg0) {
		setLogId(arg0);
	}
}
