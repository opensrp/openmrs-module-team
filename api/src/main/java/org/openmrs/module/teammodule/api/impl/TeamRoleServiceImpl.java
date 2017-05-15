package org.openmrs.module.teammodule.api.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.TeamRole;
import org.openmrs.module.teammodule.api.TeamRoleService;
import org.openmrs.module.teammodule.api.db.TeamRoleDAO;

public class TeamRoleServiceImpl extends BaseOpenmrsService implements TeamRoleService  {

private final Log log = LogFactory.getLog(this.getClass());
	
	private TeamRoleDAO dao;

	
	public TeamRoleDAO getDao() {
		return dao;
	}

	public void setDao(TeamRoleDAO dao) {
		this.dao = dao;
	}

	public Log getLog() {
		return log;
	}

	public void saveTeamRole(TeamRole TeamRole) {
		dao.saveTeamRole(TeamRole);
	}

	public List<TeamRole> getAllTeamRole() {
		return dao.getAllTeamRole();
	}

	public void purgeTeamRole(TeamRole TeamRole) {
		dao.purgeTeamRole(TeamRole);
	}

	public List<TeamRole> searchTeamRoleByRole(String role) {
		return dao.searchTeamRoleByRole(role);
	}

	public TeamRole getTeamRoleById(Integer id) {
		return dao.getTeamRoleById(id);
	}
	
	public TeamRole getTeamRoleByUuid(String uuid) {
		return dao.getTeamRoleByUuid(uuid);
	}

	@Override
	public List<TeamRole> getSubTeamRoles(TeamMember teamMember) {
		return dao.getSubTeamRoles(teamMember);
	}

	@Override
	public List<TeamRole> searchTeamRoleReportBy(int id) {
		return dao.searchTeamRoleReportBy(id);
	}
}
