/**
 * 
 */
package org.openmrs.module.teammodule.api.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamHierarchy;
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
	public void saveTeamMember(TeamMember teamMember) {
		this.dao.saveTeamMember(teamMember);	
	}

	@Override
	public void purgeTeamMember(TeamMember teamMember) {
		this.dao.purgeTeamMember(teamMember);
	}

	@Override
	public void updateTeamMember(TeamMember teamMember) {
		this.dao.updateTeamMember(teamMember);
	}

	@Override
	public List<TeamMember> getTeamMemberByPersonId(Integer personId) {
		return this.dao.getTeamMemberByPersonId(personId);
	}
	
	@Override
	public List<TeamMember> getAllTeamMember(Integer id, boolean voided) {
		return this.dao.getAllTeamMember(id, voided);
	}
	
	@Override
	public List<TeamMember> searchTeamMember(Date joinDateFrom, Date joinDateTo, String name) {
		return this.dao.searchTeamMember(joinDateFrom, joinDateTo, name);
	}
	
	@Override
	public List<TeamMember> searchTeamMemberByTeam(Integer teamId) {
		return this.dao.searchTeamMemberByTeam(teamId);
	}
	
	@Override
	public List<TeamMember> searchTeamMember(String identifier, Integer supervisorId, TeamHierarchy teamRoleId, Team teamId, Location locationId, Integer offset, Integer pageSize) {
		return this.dao.searchTeamMember(identifier, supervisorId, teamRoleId, teamId, locationId, offset, pageSize);
	}
}
