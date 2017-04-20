package org.openmrs.module.teammodule.api.db;

import java.util.List;

import org.openmrs.module.teammodule.TeamLog;

public interface TeamLogDAO {

	public void saveTeamLog(TeamLog teamLog);
	
	public TeamLog getTeamLog(int id);
	
	public List<TeamLog> getAllLogs(int pageIndex);
	
	public void purgeTeamLog(TeamLog teamLog);
	
	public List<TeamLog> searchTeamLogByTeam(int team, int pageIndex);

	public TeamLog getTeamLog(String uuid);
}
