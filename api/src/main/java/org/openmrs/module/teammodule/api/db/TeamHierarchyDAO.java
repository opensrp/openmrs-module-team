package org.openmrs.module.teammodule.api.db;

import java.util.List;

import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamHierarchy;

public interface TeamHierarchyDAO {

	public void saveTeamRole(TeamHierarchy TeamRole);
	
	public TeamHierarchy getTeamRole(int id);
	
	public List<TeamHierarchy> getAllTeams();
	
	public void purgeTeamRole(TeamHierarchy TeamRole);
	
	public List<TeamHierarchy> searchTeamRoleByRole(String role);
}
