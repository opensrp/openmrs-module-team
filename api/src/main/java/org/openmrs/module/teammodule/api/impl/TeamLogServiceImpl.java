package org.openmrs.module.teammodule.api.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamLog;
import org.openmrs.module.teammodule.TeamLog;
import org.openmrs.module.teammodule.api.TeamLeadService;
import org.openmrs.module.teammodule.api.TeamLogService;
import org.openmrs.module.teammodule.api.TeamLogService;
import org.openmrs.module.teammodule.api.db.TeamLogDAO;
import org.openmrs.module.teammodule.api.db.TeamLeadDAO;

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

	public List<TeamLog> getAllLogs() {
		// TODO Auto-generated method stub
		return dao.getAllLogs();
	}

	public void purgeTeamLog(TeamLog TeamLog) {
		// TODO Auto-generated method stub
		dao.purgeTeamLog(TeamLog);
	}

	public List<TeamLog> searchTeamLogByTeam(int team) {
			// TODO Auto-generated method stub
		return dao.searchTeamLogByTeam(team);
	}

	public TeamLog getTeamLog(int id) {
		// TODO Auto-generated method stub
		return dao.getTeamLog(id);
	}


}
