package org.openmrs.module.teammodule.api.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.teammodule.TeamHierarchy;
import org.openmrs.module.teammodule.api.TeamHierarchyService;
import org.openmrs.module.teammodule.api.db.TeamHierarchyDAO;

public class TeamHierarchyServiceImpl extends BaseOpenmrsService implements TeamHierarchyService  {

private final Log log = LogFactory.getLog(this.getClass());
	
	private TeamHierarchyDAO dao;

	
	public TeamHierarchyDAO getDao() {
		return dao;
	}

	public void setDao(TeamHierarchyDAO dao) {
		this.dao = dao;
	}

	public Log getLog() {
		return log;
	}

	public void saveTeamHierarchy(TeamHierarchy TeamHierarchy) {
		dao.saveTeamRole(TeamHierarchy);
	}

	public List<TeamHierarchy> getAllTeamHierarchy() {
		return dao.getAllTeamHierarchy();
	}

	public void purgeTeamRole(TeamHierarchy TeamRole) {
		dao.purgeTeamRole(TeamRole);
	}

	public List<TeamHierarchy> searchTeamRoleByRole(String role) {
		return dao.searchTeamRoleByRole(role);
	}

	public TeamHierarchy getTeamRoleById(Integer id) {
		return dao.getTeamRoleById(id);
	}
	
	public TeamHierarchy getTeamRoleByUuid(String uuid) {
		return dao.getTeamRoleByUuid(uuid);
	}

}
