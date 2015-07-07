package org.openmrs.module.teammodule.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.teammodule.Team;
//import org.openmrs.module.teammodule.TeamMember;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Muhammad Safwan
 *
 */
@Transactional 
public interface TeamService extends OpenmrsService {
	
	//public void setTeamDAO(TeamDAO dao);

	public void saveTeam(Team team);
	
	public Team getTeam(int id);
	
	public Team getTeam(String name);
	
	//public Team getTeam(String uuid);
	
	//public void updateTeam(Team team);
	
	//public List<TeamMember> getAllMembers(boolean retired);
	
	public List<Team> getAllTeams(boolean retired);
	
	public void purgeTeam(Team team);
	
	public List<Team> searchTeam(String name);

}
