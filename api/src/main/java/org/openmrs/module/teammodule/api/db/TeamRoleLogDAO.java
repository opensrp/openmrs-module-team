package org.openmrs.module.teammodule.api.db;

import java.util.List;

import org.openmrs.module.teammodule.TeamRoleLog;

public interface TeamRoleLogDAO {

	public void saveTeamRoleLog(TeamRoleLog teamRoleLog);
	
	public TeamRoleLog getTeamRoleLog(Integer id);
	
	public List<TeamRoleLog> getAllLogs(Integer offset, Integer pageSize);
	
	public void purgeTeamRoleLog(TeamRoleLog teamRoleLog);
	
	public void updateTeamRoleLog(TeamRoleLog teamRoleLog);
	
	public List<TeamRoleLog> searchTeamRoleLog(String teamRole, Integer offset, Integer pageSize);

	public TeamRoleLog getTeamRoleLog(String uuid);

	public List<TeamRoleLog> searchTeamRoleLog(Integer teamRoleId, Integer offset, Integer pageSize);
}
