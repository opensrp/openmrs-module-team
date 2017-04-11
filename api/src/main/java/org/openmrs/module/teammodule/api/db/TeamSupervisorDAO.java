/**
 * 
 */
package org.openmrs.module.teammodule.api.db;

import java.util.List;

import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamSupervisor;
import org.openmrs.module.teammodule.TeamMember;

/**
 * @author Muhammad Safwan
 * 
 */
public interface TeamSupervisorDAO {

	public void save(TeamSupervisor teamSupervisor);

	public List<TeamMember> getTeamMembers(Integer teamSupervisorId);

	public TeamSupervisor getTeamSupervisor(Team team);

	public List<TeamSupervisor> getTeamSupervisors(Team team);

	public void update(TeamSupervisor teamSupervisor);
	
	public void purgeTeamSupervisor(TeamSupervisor teamSupervisor);
	
	public TeamSupervisor getTeamSupervisor(String uuid);

}
