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

	public Team getTeam(int id) {
		return dao.getTeam(id);
	}

	public Team getTeam(String name) {
		return dao.getTeam(name);
	}

	public List<Team> getAllTeams(boolean retired) {
		return dao.getAllTeams(retired);
	}

/*	public List<TeamMember> getAllMembers(boolean retired) {

		return dao.getAllMembers(retired);
	}*/

	public void purgeTeam(Team team){
		dao.purgeTeam(team);
	}

	public List<Team> searchTeam(String name){
		return dao.searchTeam(name);
	}
	
}
