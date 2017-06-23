package org.openmrs.module.teammodule.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.teammodule.TeamRoleLog;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TeamRoleLogService extends OpenmrsService {

	public void saveTeamRoleLog(TeamRoleLog teamRoleLog);
	
	public TeamRoleLog getTeamRoleLog(int id);
	
	public List<TeamRoleLog> getAllLogs(Integer offset, Integer pageSize);
	
	public void purgeTeamRoleLog(TeamRoleLog teamRoleLog);
	
	public void updateTeamRoleLog(TeamRoleLog teamRoleLog);
	
	public List<TeamRoleLog> searchTeamRoleLog(String teamRoleId,Integer offset, Integer pageSize);

	public TeamRoleLog getTeamRoleLog(String uuid);

	public List<TeamRoleLog> searchTeamRoleLog(Integer teamRoleId,Integer offset, Integer pageSize);
}
