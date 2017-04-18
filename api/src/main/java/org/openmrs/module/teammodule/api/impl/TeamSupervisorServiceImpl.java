/**
 * 
 */
package org.openmrs.module.teammodule.api.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamSupervisor;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamSupervisorService;
import org.openmrs.module.teammodule.api.db.TeamSupervisorDAO;


/**
 * @author Muhammad Safwan
 *
 */
public class TeamSupervisorServiceImpl extends BaseOpenmrsService implements TeamSupervisorService {
	
	@SuppressWarnings("unused")
	private final Log log = LogFactory.getLog(this.getClass());
	
	private TeamSupervisorDAO dao;

	public TeamSupervisorDAO getDao() {
		return dao;
	}

	public void setDao(TeamSupervisorDAO dao) {
		this.dao = dao;
	}

	public void save(TeamSupervisor teamSupervisor) {
		 dao.save(teamSupervisor);
	}

	public List<TeamMember> getTeamMembers(Integer teamSupervisorId) {
		return dao.getTeamMembers(teamSupervisorId);
	}	
	
	public List<TeamSupervisor> getTeamSupervisors(Team team){
		return dao.getTeamSupervisors(team);
	}
	
	public TeamSupervisor getTeamSupervisor(Team team){
		return dao.getTeamSupervisor(team);
	}
		
	public void update(TeamSupervisor teamSupervisor) {
		dao.update(teamSupervisor);
	}
	
	public void purgeTeamSupervisor(TeamSupervisor teamSupervisor){
		dao.purgeTeamSupervisor(teamSupervisor);
	}
	
	public TeamSupervisor getTeamSupervisor(String uuid){
		return dao.getTeamSupervisor(uuid);
	}

	public TeamSupervisor getTeamSupervisor(Integer id){
		return dao.getTeamSupervisor(id);
	}
}
