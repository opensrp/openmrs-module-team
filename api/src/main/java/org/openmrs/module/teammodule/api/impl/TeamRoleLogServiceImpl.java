package org.openmrs.module.teammodule.api.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.teammodule.TeamRoleLog;
import org.openmrs.module.teammodule.api.TeamRoleLogService;
import org.openmrs.module.teammodule.api.db.TeamRoleLogDAO;

public class TeamRoleLogServiceImpl extends BaseOpenmrsService implements TeamRoleLogService  {

private final Log log = LogFactory.getLog(this.getClass());
	
	private TeamRoleLogDAO dao;

	
	public TeamRoleLogDAO getDao() {
		return dao;
	}

	public void setDao(TeamRoleLogDAO dao) {
		this.dao = dao;
	}

	public Log getLog() {
		return log;
	}

	public void saveTeamRoleLog(TeamRoleLog teamRolelog) {
		dao.saveTeamRoleLog(teamRolelog);
	}

	public List<TeamRoleLog> getAllLogs(Integer offset, Integer pageSize) {
		return dao.getAllLogs(offset, pageSize);
	}

	public void purgeTeamRoleLog(TeamRoleLog TeamRoleLog) {
		dao.purgeTeamRoleLog(TeamRoleLog);
	}

	public List<TeamRoleLog> searchTeamRoleLogByTeamRole(String teamRole,Integer offset, Integer pageSize) {
		return dao.searchTeamRoleLogByTeamRole(teamRole,offset, pageSize);
	}

	public TeamRoleLog getTeamRoleLog(int id) {
		return dao.getTeamRoleLog(id);
	}

	public TeamRoleLog getTeamRoleLog(String uuid) {
		return dao.getTeamRoleLog(uuid);
	}

}
