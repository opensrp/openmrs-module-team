/**
 * 
 */
package org.openmrs.module.teammodule.api.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamMemberService;
import org.openmrs.module.teammodule.api.db.TeamMemberDAO;

/**
 * @author Muhammad Safwan
 * 
 */
public class TeamMemberServiceImpl extends BaseOpenmrsService implements TeamMemberService {

	@SuppressWarnings("unused")
	private final Log log = LogFactory.getLog(this.getClass());

	private TeamMemberDAO dao;

	/**
	 * @return
	 */
	public TeamMemberDAO getDao() {
		return dao;
	}

	/**
	 * @param dao
	 */
	public void setDao(TeamMemberDAO dao) {
		this.dao = dao;
	}

	@Override
	public TeamMember getTeamMemberById(Integer id) {
		return this.dao.getTeamMemberById(id);
	}

	@Override
	public TeamMember getTeamMemberByName(String name) {
		return this.dao.getTeamMemberByName(name);
	}

	@Override
	public List<TeamMember> getTeamMemberByIdentifier(String identifier) {
		return this.dao.getTeamMemberByIdentifier(identifier);
	}

	@Override
	public TeamMember getTeamMemberByUuid(String uuid) {
		return this.dao.getTeamMemberByUuid(uuid);
	}

	@Override
	public List<TeamMember> getTeamMemberByTeamId(Integer teamId) {
		return this.dao.getTeamMemberByTeamId(teamId);
	}


	@Override
	public List<TeamMember> getTeamMemberByTeamName(String name) {
		return this.dao.getTeamMemberByTeamName(name);
	}

	@Override
	public List<TeamMember> getTeamMemberByPersonId(Integer personId) {
		return this.dao.getTeamMemberByPersonId(personId);
	}

	@Override
	public List<TeamMember> getTeamMembersByDate(Date joinDateFrom, Date joinDateTo) {
		return this.dao.getTeamMembersByDate(joinDateFrom, joinDateTo);
	}

	@Override
	public List<TeamMember> getTeamMembersByTeamLead(boolean isTeamLead) {
		return this.dao.getTeamMembersByTeamLead(isTeamLead);
	}

	@Override
	public List<TeamMember> getTeamMemberByRetired(boolean retired) {
		return this.dao.getTeamMemberByRetired(retired);
	}

	@Override
	public List<TeamMember> getTeamMemberByTeamRoleId(Integer teamRoleId) {
		return this.dao.getTeamMemberByTeamRoleId(teamRoleId);
	}
	
	@Override
	public List<TeamMember> getTeamMemberByLocationId(Integer id) {
		return this.dao.getTeamMemberByLocationId(id);
	}

	@Override
	public List<TeamMember> getTeamMemberByPatientId(Integer id) {
		return this.dao.getTeamMemberByPatientId(id);
	}

	@Override
	public List<TeamMember> getTeamMemberByTeam(Integer teamId, String teamName, Integer teamLeadId, Boolean retired) {
		return this.dao.getTeamMemberByTeam(teamId, teamName, teamLeadId, retired);
	}
	
	@Override
	public List<TeamMember> getTeamMemberByTeamWithPage(Integer teamId, String teamName, Integer teamLeadId, Boolean retired) {
		return this.dao.getTeamMemberByTeamWithPage(teamId, teamName, teamLeadId, retired);
	}	
	
	@Override
	public List<TeamMember> getAllTeamMember(boolean isRetired) {
		return this.dao.getAllTeamMember(isRetired);
	}
	
	@Override
	public void save(TeamMember teamMember) {
		this.dao.save(teamMember);	
	}

	@Override
	public void saveLocation(Location location) {
		this.dao.saveLocation(location);		
	}

	@Override
	public void savePatient(Patient patient) {
		this.dao.savePatient(patient);
	}

	@Override
	public void purgeMember(TeamMember teamMember) {
		this.dao.purgeMember(teamMember);
	}

	@Override
	public void update(TeamMember teamMember) {
		this.dao.update(teamMember);
	}
	
	@Override
	public List<TeamMember> searchTeamMember(String name) {
		return this.dao.searchTeamMember(name);
	}
	
	@Override
	public List<TeamMember> searchTeamMemberByTeam(String name, Integer teamId) {
		return this.dao.searchTeamMemberByTeam(name, teamId);
	}
}
