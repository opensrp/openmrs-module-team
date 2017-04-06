package org.openmrs.module.teammodule.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.teammodule.TeamLocation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TeamLocationService extends OpenmrsService {

		public void saveTeamLocation(TeamLocation teamLocation);
		
		public TeamLocation getTeamLocation(int id);
		
		public void purgeTeamLocation(TeamLocation teamLocation);
		
		public List<TeamLocation> searchLocationByLocation(String location);

		public List<TeamLocation> getAllLocation();
		
		public TeamLocation getTeamLocationByTeamId(Integer id);

		public TeamLocation getTeamLocation(String uuid);
}
