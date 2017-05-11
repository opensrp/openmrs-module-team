package org.openmrs.module.teammodule.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.teammodule.TeamHierarchyLog;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TeamHierarchyLogService extends OpenmrsService {

	public void saveTeamHierarchyLog(TeamHierarchyLog teamHierarchyLog);
	
	public TeamHierarchyLog getTeamHierarchyLog(int id);
	
	public List<TeamHierarchyLog> getAllLogs(Integer offset, Integer pageSize);
	
	public void purgeTeamHierarchyLog(TeamHierarchyLog teamHierarchyLog);
	
	public List<TeamHierarchyLog> searchTeamHierarchyLog(String teamHierarchyId,Integer offset, Integer pageSize);

	public TeamHierarchyLog getTeamHierarchyLog(String uuid);
}
