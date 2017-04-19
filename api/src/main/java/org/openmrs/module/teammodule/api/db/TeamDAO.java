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
	
	public Team getTeam(int id);
	
	public Team getTeam(String name);
	
	public void updateTeam(Team team);
	
	public List<Team> getAllTeams(boolean retired, int pageIndex);
	
	public void purgeTeam(Team team);
	
	public List<Team> searchTeam(String name);

	public Team getTeamBySupervisor(int teamSupervisor);

	List<Team> getTeambyLocation(int locationId, int pageIndex);
}
