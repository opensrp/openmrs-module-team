/**
 * 
 */
package org.openmrs.module.teammodule.api;

import java.util.Date;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamRole;
import org.openmrs.module.teammodule.TeamMember;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zohaib Masood
 *
 */
@Transactional 
public interface TeamMemberService extends OpenmrsService {
	
	public TeamMember getTeamMember(Integer id);

	public TeamMember getTeamMemberByUuid(String uuid);
	
	public List<TeamMember> getTeamMemberByPersonId(Integer personId);

	public List<TeamMember> getAllTeamMember(Integer id, Boolean voided, Integer offset, Integer pageSize);
		
	public void saveTeamMember(TeamMember teamMember);
	
	public void purgeTeamMember(TeamMember teamMember);
	
	public void updateTeamMember(TeamMember teamMember);
	
	public List<TeamMember> searchTeamMemberByTeam(Integer teamId);

	public List<TeamMember> searchTeamMember(String identifier, Integer supervisorId, Integer teamRoleId, Integer teamId, Integer locationId, Date joinDateFrom, Date joinDateTo, String name,Boolean isdataprovider, Integer offset, Integer pageSize);

	public int countTeamMemberByTeam(Integer teamId);

	public int countTeamMemberByTeamRole(Integer teamRoleId);
}
