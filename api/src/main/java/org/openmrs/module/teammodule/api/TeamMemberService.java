/**
 * 
 */
package org.openmrs.module.teammodule.api;

import java.util.Date;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamMember;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Muhammad Safwan
 *
 */
@Transactional 
public interface TeamMemberService extends OpenmrsService {
	
	public TeamMember getTeamMember(Integer id);

	public TeamMember getTeamMemberByUuid(String uuid);
	
	public List<TeamMember> getTeamMemberByPersonId(Integer personId);
	
	public List<TeamMember> getTeamMemberByDate(Date joinDateFrom, Date joinDateTo);
		
	public List<TeamMember> getTeamMemberByLocationId(Integer id);
						
	public List<TeamMember> getTeamMemberByPatientId(Integer id);

	public List<TeamMember> getAllTeamMember(Integer id, boolean isRetired);
	
	public List<TeamMember> getTeamMemberByTeam(Team team, String teamName, Integer teamLeadId, Boolean retired);
	
	public List<TeamMember> getTeamMemberByTeamWithPage(Team team, String teamName, Integer teamLeadId, Boolean retired, Integer pageSize);
	
	public void save(TeamMember teamMember);
	
	public void saveLocation(Location location);
	
	public void savePatient(Patient patient);
	
	public void purgeMember(TeamMember teamMember);
	
	public void update(TeamMember teamMember);
	
	public List<TeamMember> searchTeamMember(String name);

	public List<TeamMember> searchTeamMemberByTeam(String name, Integer teamId);

	public List<TeamMember> searchTeamMember(String id, String supervisor, String teamRole, String team, String location);

	public List<TeamMember> searchTeamMemberWithPage(String id, String supervisor, String teamRole, String team, String location, Integer pageSize);
}
