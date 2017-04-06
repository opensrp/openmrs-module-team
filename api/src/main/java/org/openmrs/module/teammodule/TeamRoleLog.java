package org.openmrs.module.teammodule;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;
import org.openmrs.module.teammodule.TeamConstants.TeamAction;

@SuppressWarnings("serial")
public class TeamRoleLog extends BaseOpenmrsData implements Serializable{

	private Integer logId;

	private TeamHierarchy teamRole;

	private TeamAction action;

	@JsonProperty
	private String dataOld;

	@JsonProperty
	private String dataNew;

	@JsonProperty
	private String log;
	
	private Date dateCreated;

	private User creator;

	private User changedBy;

	private Date dateChanged;

	private Boolean voided;

	private User voidedBy;

	private String voidedReason;

	private String uuid;

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
		return getLogId();
	}

	public void setId(Integer arg0) {
		setLogId(arg0);		
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public User getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(User changedBy) {
		this.changedBy = changedBy;
	}

	public Date getDateChanged() {
		return dateChanged;
	}

	public void setDateChanged(Date dateChanged) {
		this.dateChanged = dateChanged;
	}

	public Boolean getVoided() {
		return voided;
	}

	public void setVoided(Boolean voided) {
		this.voided = voided;
	}

	public User getVoidedBy() {
		return voidedBy;
	}

	public void setVoidedBy(User voidedBy) {
		this.voidedBy = voidedBy;
	}

	public String getVoidedReason() {
		return voidedReason;
	}

	public void setVoidedReason(String voidedReason) {
		this.voidedReason = voidedReason;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
