package org.openmrs.module.teammodule.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.teammodule.TeamMemberLocation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TeamMemberLocationService extends OpenmrsService {

	public void saveTeamMemberLocation(TeamMemberLocation teamLocation);
	
	public TeamMemberLocation getTeamMemberLocation(int id);
	
	public TeamMemberLocation getTeamMemberLocation(String uuid);

	public void purgeTeamMemberLocation(TeamMemberLocation teamMemberLocation);
	
	public List<TeamMemberLocation> searchLocationByLocation(String location);

	public List<TeamMemberLocation> getAllLocation();
	
	public TeamMemberLocation getTeamMemberLocationByTeamMemberId(Integer id);
}
