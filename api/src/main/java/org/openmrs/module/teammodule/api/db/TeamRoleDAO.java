package org.openmrs.module.teammodule.api.db;

import java.util.List;

import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.TeamRole;

public interface TeamRoleDAO {

	public void saveTeamRole(TeamRole TeamRole);
	
	public TeamRole getTeamRoleById(Integer id);
	
	public List<TeamRole> getAllTeamRole();
	
	public void purgeTeamRole(TeamRole TeamRole);
	
	public List<TeamRole> searchTeamRoleByRole(String role);
	
	public TeamRole getTeamRoleByUuid(String uuid);

	public List<TeamRole> getSubTeamRoles(TeamMember teamMember);

	public List<TeamRole> searchTeamRoleReportBy(int id);
}
