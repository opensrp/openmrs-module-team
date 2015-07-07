/**
 * 
 */
package org.openmrs.module.teammodule.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamLead;
import org.openmrs.module.teammodule.TeamMember;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Muhammad Safwan
 * 
 */
@Transactional
public interface TeamLeadService extends OpenmrsService {

	public void save(TeamLead teamLead);

	public List<TeamMember> getTeamMembers(Integer teamLeadId);

	public TeamLead getTeamLead(Team team);

	public List<TeamLead> getTeamLeads(Team team);

	public void update(TeamLead teamLead);
	
	public void purgeTeamLead(TeamLead teamLead);
	
	public TeamLead getTeamLead(String uuid);

}
