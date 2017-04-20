package org.openmrs.module.teammodule.api.db;

import java.util.List;

import org.openmrs.module.teammodule.TeamRoleLog;

public interface TeamRoleLogDAO {

	public void saveTeamRoleLog(TeamRoleLog teamRoleLog);
	
	public TeamRoleLog getTeamRoleLog(int id);
	
	public List<TeamRoleLog> getAllLogs(int pageIndex);
	
	public void purgeTeamRoleLog(TeamRoleLog teamRoleLog);
	
	public List<TeamRoleLog> searchTeamRoleLogByTeamRole(String teamRole, int pageIndex);

	public TeamRoleLog getTeamRoleLog(String uuid);
}
