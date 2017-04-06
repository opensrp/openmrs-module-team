package org.openmrs.module.teammodule.api.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.teammodule.TeamMemberLocation;
import org.openmrs.module.teammodule.api.TeamMemberLocationService;
import org.openmrs.module.teammodule.api.db.TeamMemberLocationDAO;

public class TeamMemberLocationServiceImpl extends BaseOpenmrsService implements TeamMemberLocationService  {

private final Log log = LogFactory.getLog(this.getClass());
	
	private TeamMemberLocationDAO dao;

	
	public TeamMemberLocationDAO getDao() {
		return dao;
	}

	public void setDao(TeamMemberLocationDAO dao) {
		this.dao = dao;
	}

	public Log getLog() {
		return log;
	}

	public void saveTeamMemberLocation(TeamMemberLocation teamMemberLocation) {
		dao.saveTeamMemberLocation(teamMemberLocation);
	}

	public void purgeTeamMemberLocation(TeamMemberLocation teamMemberLocation) {
		dao.purgeTeamMemberLocation(teamMemberLocation);
	}

	public List<TeamMemberLocation> searchLocationByLocation(String Location) {
		return dao.searchLocationByLocation(Location);
	}

	public TeamMemberLocation getTeamMemberLocation(int id) {
		return dao.getTeamMemberLocation(id);
	}
	
	public TeamMemberLocation getTeamMemberLocation(String uuid) {
		return dao.getTeamMemberLocation(uuid);
	}
	
	public List<TeamMemberLocation> getAllLocation() {
		return dao.getAllLocation();
	}
	
	public TeamMemberLocation getTeamMemberLocationByTeamMemberId(Integer id) {
		return dao.getTeamMemberLocationByTeamMemberId(id);
	}
}
