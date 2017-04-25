package org.openmrs.module.teammodule.api.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.teammodule.TeamLog;
import org.openmrs.module.teammodule.api.TeamLogService;
import org.openmrs.module.teammodule.api.db.TeamLogDAO;

public class TeamLogServiceImpl extends BaseOpenmrsService implements TeamLogService  {

private final Log log = LogFactory.getLog(this.getClass());
	
	private TeamLogDAO dao;

	
	public TeamLogDAO getDao() {
		return dao;
	}

	public void setDao(TeamLogDAO dao) {
		this.dao = dao;
	}

	public Log getLog() {
		return log;
	}

	public void saveTeamLog(TeamLog teamlog) {
		dao.saveTeamLog(teamlog);
	}

	public List<TeamLog> getAllLogs(Integer offset, Integer pageSize) {
		return dao.getAllLogs(offset, pageSize);
	}

	public void purgeTeamLog(TeamLog TeamLog) {
		dao.purgeTeamLog(TeamLog);
	}

	public List<TeamLog> searchTeamLogByTeam(int team,Integer offset, Integer pageSize) {
		return dao.searchTeamLogByTeam(team, offset, pageSize);
	}

	public TeamLog getTeamLog(int id) {
		return dao.getTeamLog(id);
	}

	public TeamLog getTeamLog(String uuid) {
		return dao.getTeamLog(uuid);
	}
}
