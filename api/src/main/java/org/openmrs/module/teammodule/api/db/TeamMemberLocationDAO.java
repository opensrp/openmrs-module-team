package org.openmrs.module.teammodule.api.db;

import java.util.List;

import org.openmrs.module.teammodule.TeamMemberLocation;

public interface TeamMemberLocationDAO {

	public void saveTeamMemberLocation(TeamMemberLocation teamMemberLocation);
	
	public TeamMemberLocation getTeamMemberLocation(int id);

	public TeamMemberLocation getTeamMemberLocation(String uuid);

	public void purgeTeamMemberLocation(TeamMemberLocation teamMemberLocation);
	
	public List<TeamMemberLocation> searchLocationByLocation(String location);

	public List<TeamMemberLocation> getAllLocation();
	
	public TeamMemberLocation getTeamMemberLocationByTeamMemberId(Integer id);
}
