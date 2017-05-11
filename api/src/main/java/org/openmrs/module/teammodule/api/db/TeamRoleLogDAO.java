package org.openmrs.module.teammodule.api.db;

import java.util.List;

import org.openmrs.module.teammodule.TeamHierarchyLog;

public interface TeamHierarchyLogDAO {

	public void saveTeamHierarchyLog(TeamHierarchyLog teamRoleLog);
	
	public TeamHierarchyLog getTeamHierarchyLog(int id);
	
	public List<TeamHierarchyLog> getAllLogs(Integer offset, Integer pageSize);
	
	public void purgeTeamHierarchyLog(TeamHierarchyLog teamHierarchyLog);
	
	public List<TeamHierarchyLog> searchTeamHierarchyLog(String teamHierarchy, Integer offset, Integer pageSize);

	public TeamHierarchyLog getTeamHierarchyLog(String uuid);
}
