package org.openmrs.module.teammodule.api.db;

import java.util.List;

import org.openmrs.module.teammodule.TeamHierarchy;

public interface TeamHierarchyDAO {

	public void saveTeamRole(TeamHierarchy TeamRole);
	
	public TeamHierarchy getTeamRoleById(int id);
	
	public List<TeamHierarchy> getAllTeamHierarchy();
	
	public void purgeTeamRole(TeamHierarchy TeamRole);
	
	public List<TeamHierarchy> searchTeamRoleByRole(String role);
	
	public TeamHierarchy getTeamRoleByUuid(String uuid);
}
