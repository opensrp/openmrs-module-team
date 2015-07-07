/**
 * 
 */
package org.openmrs.module.teammodule.api.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamLead;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamLeadService;
import org.openmrs.module.teammodule.api.db.TeamLeadDAO;


/**
 * @author Muhammad Safwan
 *
 */
public class TeamLeadServiceImpl extends BaseOpenmrsService implements TeamLeadService {
	
	@SuppressWarnings("unused")
	private final Log log = LogFactory.getLog(this.getClass());
	
	private TeamLeadDAO dao;

	public TeamLeadDAO getDao() {
		return dao;
	}

	public void setDao(TeamLeadDAO dao) {
		this.dao = dao;
	}

	public void save(TeamLead teamLead) {
		 dao.save(teamLead);
	}

	public List<TeamMember> getTeamMembers(Integer teamLeadId) {
		return dao.getTeamMembers(teamLeadId);
	}	
	
	public List<TeamLead> getTeamLeads(Team team){
		return dao.getTeamLeads(team);
	}
	
	public TeamLead getTeamLead(Team team){
		return dao.getTeamLead(team);
	}
	
	public void update(TeamLead teamLead) {
		dao.update(teamLead);
	}
	
	public void purgeTeamLead(TeamLead teamLead){
		dao.purgeTeamLead(teamLead);
	}
	
	public TeamLead getTeamLead(String uuid){
		return dao.getTeamLead(uuid);
	}

}
