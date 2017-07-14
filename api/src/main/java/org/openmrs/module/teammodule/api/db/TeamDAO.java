/**
 * 
 */
package org.openmrs.module.teammodule.api.db;

import java.util.List;

import org.openmrs.module.teammodule.Team;
/**
 * @author Muhammad Safwan
 *
 */
public interface TeamDAO {

	public void saveTeam(Team team);
	
	public Team getTeam(Integer id);
	
	public Team getTeam(String teamName, Integer locationId);
	
	public Team getTeam(String name);
	
	public void updateTeam(Team team);
	
	public List<Team> getAllTeams(boolean voided, Integer offset, Integer pageSize);
	
	public void purgeTeam(Team team);
	
	public List<Team> searchTeam(String name);

	public Team getTeamBySupervisor(Integer teamSupervisorId);

	public List<Team> getTeamByLocation(Integer locationId, Integer offset, Integer pageSize);

	public List<Team> getSubTeams(Integer teamSupervisorId);
}
