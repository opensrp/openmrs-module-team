/**
 * 
 */
package org.openmrs.module.teammodule.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamSupervisor;
import org.openmrs.module.teammodule.TeamMember;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Muhammad Safwan
 * 
 */
@Transactional
public interface TeamSupervisorService extends OpenmrsService {

	public void save(TeamSupervisor teamSupervisor);

	public List<TeamMember> getTeamMembers(Integer teamSupervisorId);

	public TeamSupervisor getTeamSupervisor(Team team);

	public List<TeamSupervisor> getTeamSupervisors(Team team);

	public void update(TeamSupervisor teamSupervisor);
	
	public void purgeTeamSupervisor(TeamSupervisor teamSupervisor);
	
	public TeamSupervisor getTeamSupervisor(String uuid);

	public TeamSupervisor getTeamSupervisor(Integer id);
}
