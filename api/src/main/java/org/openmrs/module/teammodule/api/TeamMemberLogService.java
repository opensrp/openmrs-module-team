package org.openmrs.module.teammodule.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.teammodule.TeamMemberLog;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TeamMemberLogService extends OpenmrsService {

	public void saveTeamMemberLog(TeamMemberLog teamMemberLog);
	
	public TeamMemberLog getTeamMemberLog(int id);
	
	public TeamMemberLog getTeamMemberLog(String uuid);

	public List<TeamMemberLog> getAllLogs(Integer pageSize);
	
	public void purgeTeamMemberLog(TeamMemberLog teamMemberLog);
	
	public List<TeamMemberLog> searchTeamMemberLogByTeamMember(int teamMember);

}
