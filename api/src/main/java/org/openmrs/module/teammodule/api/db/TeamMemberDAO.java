/**
 * 
 */
package org.openmrs.module.teammodule.api.db;

import java.util.Date;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamRole;
import org.openmrs.module.teammodule.TeamMember;

/**
 * @author Zohaib Masood and Shakeeb Raza
 *
 */
public interface TeamMemberDAO {
	
	public TeamMember getTeamMember(Integer id);
		
	public TeamMember getTeamMemberByUuid(String uuid);
	
	public List<TeamMember> getTeamMemberByPersonId(int personId);

	public List<TeamMember> getAllTeamMember(Integer id, Boolean voided, Integer offset, Integer pageSize);
	
	public void saveTeamMember(TeamMember teamMember);
	
	public void purgeTeamMember(TeamMember teamMember);
	
	public List<TeamMember> getTeamMemberByPersonIdentifier(String personIdentifier);
	
	public void updateTeamMember(TeamMember teamMember);
	
	public List<TeamMember> searchTeamMemberByTeam(Integer teamId);
	
	public List<TeamMember> searchTeamMember(String nameOrIdentifier, Integer supervisor, Integer teamRole,
			Integer team, Integer location, Date joinDateFrom, Date joinDateTo, String name,
			Boolean isdataprovider, Integer offset, Integer pageSize);

	public int countTeamMemberByTeam(Integer teamId);

	public int countTeamMemberByTeamRole(Integer teamId);
}
