/**
 * 
 */
package org.openmrs.module.teammodule.api.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamMemberService;
import org.openmrs.module.teammodule.api.db.TeamMemberDAO;
//import org.openmrs.module.teammodule.api.db.Team;

/**
 * @author Muhammad Safwan
 * 
 */
public class TeamMemberServiceImpl extends BaseOpenmrsService implements TeamMemberService {

	@SuppressWarnings("unused")
	private final Log log = LogFactory.getLog(this.getClass());

	private TeamMemberDAO dao;

	/**
	 * @return
	 */
	public TeamMemberDAO getDao() {
		return dao;
	}

	/**
	 * @param dao
	 */

	public void setDao(TeamMemberDAO dao) {
		this.dao = dao;
	}

	public void save(TeamMember teamMember) {
		dao.save(teamMember);
	}
	
	public void saveLocation(Location location){
		dao.saveLocation(location);
	}

	public List<TeamMember> getTeamMembers(Team team, String name, Integer teamLeadId, Boolean retired) {
		return dao.getTeamMembers(team, name, teamLeadId, retired);
	}

	public List<TeamMember> getTeamMembers(Integer id) {
		return dao.getTeamMembers(id);
	}

	public List<TeamMember> getAllMembers(boolean retired) {
		return dao.getAllMembers(retired);
	}

	public TeamMember getMember(int id) {
		return dao.getMember(id);
	}
	
	public List<TeamMember> getMemberByPersonId(int id) {
		return dao.getMemberByPersonId(id);
	}

	public List<TeamMember> getMember(String name) {
		return dao.getMember(name);
	}

	public List<TeamMember> getMembers(Date joinDateFrom, Date joinDateTo) {
		return dao.getMembers(joinDateFrom, joinDateTo);
	}

	public void purgeMember(TeamMember teamMember) {
		dao.purgeMember(teamMember);
	}

	public void update(TeamMember teamMember) {
		dao.update(teamMember);
	}
	
	public List<TeamMember> searchMember(String name) {
		return dao.searchMember(name);
	}
	
	public List<TeamMember> searchMemberByTeam(String name,int teamId){
		return dao.searchMemberByTeam(name, teamId);
	}
	
	public TeamMember getTeamMember(String uuid){
		return dao.getTeamMember(uuid);
	}
}
