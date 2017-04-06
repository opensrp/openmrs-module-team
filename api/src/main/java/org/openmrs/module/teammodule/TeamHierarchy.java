package org.openmrs.module.teammodule;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;


@SuppressWarnings("serial")
public class TeamHierarchy extends BaseOpenmrsData implements Serializable {

	private Integer teamRoleId;
	
	private String name;
	
	private Boolean ownsTeam;

	private Set<TeamHierarchy> reportTo=new HashSet<>();
	
	private Date dateCreated;

	private User creator;

	private User changedBy;

	private Date dateChanged;

	private Boolean voided;

	private User voidedBy;

	private String voidedReason;

	private String uuid;

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
		return getTeamRoleId();
	}

	public void setId(Integer arg0) {
		setTeamRoleId(arg0);
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
