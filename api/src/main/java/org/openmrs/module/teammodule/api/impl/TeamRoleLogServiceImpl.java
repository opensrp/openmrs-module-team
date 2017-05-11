package org.openmrs.module.teammodule.api.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.teammodule.TeamHierarchyLog;
import org.openmrs.module.teammodule.api.TeamHierarchyLogService;
import org.openmrs.module.teammodule.api.db.TeamHierarchyLogDAO;

public class TeamHierarchyLogServiceImpl extends BaseOpenmrsService implements TeamHierarchyLogService  {

private final Log log = LogFactory.getLog(this.getClass());
	
	private TeamHierarchyLogDAO dao;

	
	public TeamHierarchyLogDAO getDao() {
		return dao;
	}

	public void setDao(TeamHierarchyLogDAO dao) {
		this.dao = dao;
	}

	public Log getLog() {
		return log;
	}

	public void saveTeamHierarchyLog(TeamHierarchyLog teamHierarchylog) {
		dao.saveTeamHierarchyLog(teamHierarchylog);
	}

	public List<TeamHierarchyLog> getAllLogs(Integer offset, Integer pageSize) {
		return dao.getAllLogs(offset, pageSize);
	}

	public void purgeTeamHierarchyLog(TeamHierarchyLog TeamHierarchyLog) {
		dao.purgeTeamHierarchyLog(TeamHierarchyLog);
	}

	public List<TeamHierarchyLog> searchTeamHierarchyLog(String teamHierarchy,Integer offset, Integer pageSize) {
		return dao.searchTeamHierarchyLog(teamHierarchy,offset, pageSize);
	}

	public TeamHierarchyLog getTeamHierarchyLog(int id) {
		return dao.getTeamHierarchyLog(id);
	}

	public TeamHierarchyLog getTeamHierarchyLog(String uuid) {
		return dao.getTeamHierarchyLog(uuid);
	}

}
