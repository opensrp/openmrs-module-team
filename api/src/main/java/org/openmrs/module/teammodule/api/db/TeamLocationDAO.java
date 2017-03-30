package org.openmrs.module.teammodule.api.db;

import java.util.List;

import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamHierarchy;
import org.openmrs.module.teammodule.TeamLocation;

public interface TeamLocationDAO {

	public void saveTeamLocation(TeamLocation teamLocation);
	
	public TeamLocation getTeamLocation(int id);
	
	public void purgeTeamLocation(TeamLocation teamLocation);
	
	public List<TeamLocation> searchLocationByLocation(String location);
}
