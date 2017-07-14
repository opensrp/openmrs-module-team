package org.openmrs.module.teammodule.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.teammodule.Team;
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
	
	public Team getTeam(String teamName, Integer locationid);
	
	public Team getTeamBySupervisor(Integer teamSupervisorId);

	public Team getTeam(String uuid);
	
	public List<Team> getTeamByLocation(Integer locationId, Integer offset, Integer pageSize);
	
	public void updateTeam(Team team);
	
	public List<Team> getAllTeams(boolean voided, Integer offset, Integer pageSize);
	
	public void purgeTeam(Team team);
	
	public List<Team> searchTeam(String name);

	public List<Team> getSubTeams(Integer teamMemberId);
}
