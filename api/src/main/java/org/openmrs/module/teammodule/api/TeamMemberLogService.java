package org.openmrs.module.teammodule.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.teammodule.TeamMemberLog;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TeamMemberLogService extends OpenmrsService {

	public void saveTeamMemberLog(TeamMemberLog teamMemberLog);

	public void updateTeamMemberLog(TeamMemberLog teamMemberLog);

	public TeamMemberLog getTeamMemberLog(Integer id);
	
	public TeamMemberLog getTeamMemberLog(String uuid);

	public List<TeamMemberLog> getAllLogs(Integer offset, Integer pageSize);
	
	public void purgeTeamMemberLog(TeamMemberLog teamMemberLog);
	
	public List<TeamMemberLog> searchTeamMemberLogByTeamMember(Integer teamMemberId, Integer offset, Integer pageSize);

}
