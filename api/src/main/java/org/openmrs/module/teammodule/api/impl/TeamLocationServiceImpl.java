package org.openmrs.module.teammodule.api.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamLocation;
import org.openmrs.module.teammodule.api.TeamLeadService;
import org.openmrs.module.teammodule.api.TeamLocationService;
import org.openmrs.module.teammodule.api.db.TeamLocationDAO;
import org.openmrs.module.teammodule.api.db.TeamLeadDAO;

public class TeamLocationServiceImpl extends BaseOpenmrsService implements TeamLocationService  {

private final Log log = LogFactory.getLog(this.getClass());
	
	private TeamLocationDAO dao;

	
	public TeamLocationDAO getDao() {
		return dao;
	}

	public void setDao(TeamLocationDAO dao) {
		this.dao = dao;
	}

	public Log getLog() {
		return log;
	}

	public void saveTeamLocation(TeamLocation TeamLocation) {
		dao.saveTeamLocation(TeamLocation);
	}

	public void purgeTeamLocation(TeamLocation teamLocation) {
		// TODO Auto-generated method stub
		dao.purgeTeamLocation(teamLocation);
	}

	public List<TeamLocation> searchLocationByLocation(String Location) {
		// TODO Auto-generated method stub
		return dao.searchLocationByLocation(Location);
	}

	public TeamLocation getTeamLocation(int id) {
		// TODO Auto-generated method stub
		return dao.getTeamLocation(id);
	}

}
