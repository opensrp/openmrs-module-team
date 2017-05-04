package org.openmrs.module.teammodule.api;

import java.util.List;

import org.openmrs.Location;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamMember;
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
	
	public Team getTeam(Integer id);
	
	public Team getTeam(String teamName, int locationid);
	
	public Team getTeamBySupervisor(TeamMember teamSupervisor);

	public Team getTeam(String uuid);
	
	public List<Team> getTeambyLocation(Location locationId, Integer offset, Integer pageSize);
	
	public void updateTeam(Team team);
	
	public List<Team> getAllTeams(boolean voided, Integer offset, Integer pageSize);
	
	public void purgeTeam(Team team);
	
	public List<Team> searchTeam(String name);

}
