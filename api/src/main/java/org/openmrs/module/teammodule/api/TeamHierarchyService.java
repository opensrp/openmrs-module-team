package org.openmrs.module.teammodule.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.teammodule.TeamHierarchy;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TeamHierarchyService extends OpenmrsService {

	public void saveTeamHierarchy(TeamHierarchy teamHierarchy);
	
	public TeamHierarchy getTeamRoleById(Integer id);
	
	public List<TeamHierarchy> getAllTeamHierarchy();
	
	public void purgeTeamRole(TeamHierarchy TeamRole);
	
	public List<TeamHierarchy> searchTeamRoleByRole(String role);
		
	public TeamHierarchy getTeamRoleByUuid(String uuid);		
}
