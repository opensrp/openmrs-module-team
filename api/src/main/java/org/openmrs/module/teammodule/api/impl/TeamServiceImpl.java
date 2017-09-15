/**
 * 
 */
package org.openmrs.module.teammodule.api.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
//import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.api.TeamService;
import org.openmrs.module.teammodule.api.db.TeamDAO;

/**
 * @author Muhammad Safwan
 *
 */
public class TeamServiceImpl extends BaseOpenmrsService implements TeamService {
	
	@SuppressWarnings("unused")
	private final Log log = LogFactory.getLog(this.getClass());
	
	private TeamDAO dao;	
	
	/**
	 * @return
	 */
	public TeamDAO getDao() {
		return dao;
	}

	/**
	 * @param dao
	 */

	public void setDao(TeamDAO dao) {
		this.dao = dao;
	}
	
/*	public void setTeamDAO(TeamDAO dao){
		this.dao = dao;
	}*/

	public void saveTeam(Team team) {
		 dao.saveTeam(team);
	}

	public Team getTeam(Integer id) {
		return dao.getTeam(id);
	}

	public Team getTeamByUuid(String uuid) {
		return dao.getTeamByUuid(uuid);
	}

	public List<Team> getAllTeams(Boolean voided, Integer offset, Integer pageSize) {
		return dao.getAllTeams(voided,offset, pageSize);
	}

	public void purgeTeam(Team team){
		dao.purgeTeam(team);
	}

	public List<Team> searchTeam(String nameOrIdentifier){
		return dao.searchTeam(nameOrIdentifier);
	}
	
	public Team getTeamBySupervisor(Integer teamSupervisorId) {
		return dao.getTeamBySupervisor(teamSupervisorId);
	}

	@Override
	public List<Team> getTeamByLocation(Integer locationId, Integer offset, Integer pageSize) {
		return dao.getTeamByLocation(locationId,offset, pageSize);
	}

	@Override
	public void updateTeam(Team team) {
		dao.updateTeam(team);
	}

	@Override
	public Team getTeam(String teamName, Integer locationid) {
		return dao.getTeam(teamName, locationid);
	}

	@Override
	public List<Team> getSubTeams(Integer teamSupervisorId) {
		return dao.getSubTeams(teamSupervisorId);
	}

	@Override
	public Team getTeamByUUID(String uuid) {
		return dao.getTeamByUUID(uuid);
		
	}
}
