package org.openmrs.module.teammodule.api.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.teammodule.TeamMemberLog;
import org.openmrs.module.teammodule.api.TeamMemberLogService;
import org.openmrs.module.teammodule.api.db.TeamMemberLogDAO;

public class TeamMemberLogServiceImpl extends BaseOpenmrsService implements TeamMemberLogService  {

private final Log log = LogFactory.getLog(this.getClass());
	
	private TeamMemberLogDAO dao;

	
	public TeamMemberLogDAO getDao() {
		return dao;
	}

	public void setDao(TeamMemberLogDAO dao) {
		this.dao = dao;
	}

	public Log getLog() {
		return log;
	}

	public void saveTeamMemberLog(TeamMemberLog teamMemberlog) {
		dao.saveTeamMemberLog(teamMemberlog);
	}

	public List<TeamMemberLog> getAllLogs() {
		return dao.getAllLogs();
	}

	public void purgeTeamMemberLog(TeamMemberLog teamMemberLog) {
		dao.purgeTeamMemberLog(teamMemberLog);
	}

	public List<TeamMemberLog> searchTeamMemberLogByTeamMember(int teamMember) {
		return dao.searchTeamMemberLogByTeamMember(teamMember);
	}

	public TeamMemberLog getTeamMemberLog(int id) {
		return dao.getTeamMemberLog(id);
	}

	public TeamMemberLog getTeamMemberLog(String uuid) {
		return dao.getTeamMemberLog(uuid);
	}
}
