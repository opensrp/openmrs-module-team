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

	public void updateTeamMemberLog(TeamMemberLog teamMemberlog) {
		dao.updateTeamMemberLog(teamMemberlog);
	}

	public List<TeamMemberLog> getAllLogs(Integer offset, Integer pageSize) {
		return dao.getAllLogs(offset, pageSize);
	}

	public void purgeTeamMemberLog(TeamMemberLog teamMemberLog) {
		dao.purgeTeamMemberLog(teamMemberLog);
	}

	public List<TeamMemberLog> searchTeamMemberLogByTeamMember(Integer teamMember, Integer offset, Integer pageSize) {
		return dao.searchTeamMemberLogByTeamMember(teamMember, offset, pageSize);
	}

	public TeamMemberLog getTeamMemberLog(Integer id) {
		return dao.getTeamMemberLog(id);
	}

	public TeamMemberLog getTeamMemberLog(String uuid) {
		return dao.getTeamMemberLog(uuid);
	}
}
