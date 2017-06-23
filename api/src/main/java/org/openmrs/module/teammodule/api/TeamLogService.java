package org.openmrs.module.teammodule.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.teammodule.TeamLog;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TeamLogService extends OpenmrsService {

	public void saveTeamLog(TeamLog teamLog);
	
	public TeamLog getTeamLog(Integer id);
	
	public List<TeamLog> getAllLogs(Integer offset, Integer pageSize);
	
	public void purgeTeamLog(TeamLog teamLog);
	
	public void updateTeamLog(TeamLog teamLog);

	public List<TeamLog> searchTeamLogByTeam(Integer team, Integer offset, Integer pageSize);

	public TeamLog getTeamLog(String uuid);
}
