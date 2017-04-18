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
import org.openmrs.module.teammodule.Team;
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
	public TeamMember getTeamMember(Integer id) {
		return this.dao.getTeamMember(id);
	}

	@Override
	public TeamMember getTeamMemberByUuid(String uuid) {
		return this.dao.getTeamMemberByUuid(uuid);
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
	public List<TeamMember> getTeamMemberByPersonId(Integer personId) {
		return this.dao.getTeamMemberByPersonId(personId);
	}
	
	@Override
	public List<TeamMember> getTeamMemberByDate(Date joinDateFrom, Date joinDateTo) {
		return this.dao.getTeamMemberByDate(joinDateFrom, joinDateTo);
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
	public List<TeamMember> getTeamMemberByTeam(Team team, String teamName, Integer teamLeadId, Boolean retired) {
		return this.dao.getTeamMemberByTeam(team, teamName, teamLeadId, retired);
	}
	
	@Override
	public List<TeamMember> getTeamMemberByTeamWithPage(Team team, String teamName, Integer teamLeadId, Boolean retired, Integer pageSize) {
		return this.dao.getTeamMemberByTeamWithPage(team, teamName, teamLeadId, retired, pageSize);
	}	
	
	@Override
	public List<TeamMember> getAllTeamMember(Integer id, boolean isRetired) {
		return this.dao.getAllTeamMember(id, isRetired);
	}
	
	@Override
	public List<TeamMember> searchTeamMember(String name) {
		return this.dao.searchTeamMember(name);
	}
	
	@Override
	public List<TeamMember> searchTeamMemberByTeam(String name, Integer teamId) {
		return this.dao.searchTeamMemberByTeam(name, teamId);
	}
	
	@Override
	public List<TeamMember> searchTeamMember(String id, String supervisor, String teamRole, String team, String location) {
		return this.dao.searchTeamMember(id, supervisor, teamRole, team, location);
	}
	
	@Override
	public List<TeamMember> searchTeamMemberWithPage(String id, String supervisor, String teamRole, String team, String location, Integer pageSize) {
		return this.dao.searchTeamMemberWithPage(id, supervisor, teamRole, team, location, pageSize);
	}
}
