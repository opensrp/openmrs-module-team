package org.openmrs.module.teammodule.api.db;

import java.util.List;

import org.openmrs.module.teammodule.TeamMemberLog;

public interface TeamMemberLogDAO {

	public void saveTeamMemberLog(TeamMemberLog teamMemberLog);
	
	public void updateTeamMemberLog(TeamMemberLog teamMemberlog);

	public TeamMemberLog getTeamMemberLog(int id);
	
	public TeamMemberLog getTeamMemberLog(String id);

	public List<TeamMemberLog> getAllLogs(Integer offset, Integer pageSize);
	
	public void purgeTeamMemberLog(TeamMemberLog teamMemberLog);
	
	public List<TeamMemberLog> searchTeamMemberLogByTeamMember(int teamMember);
}
