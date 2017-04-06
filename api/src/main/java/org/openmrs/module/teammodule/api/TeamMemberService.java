/**
 * 
 */
package org.openmrs.module.teammodule.api;

import java.util.Date;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.teammodule.TeamMember;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Muhammad Safwan
 *
 */
@Transactional 
public interface TeamMemberService extends OpenmrsService {
	
	public TeamMember getTeamMemberById(Integer id);
	
	public TeamMember getTeamMemberByName(String name);
	
	public List<TeamMember> getTeamMemberByIdentifier(String identifier);

	public TeamMember getTeamMemberByUuid(String uuid);

	public List<TeamMember> getTeamMemberByTeamId(Integer teamId);
	
	public List<TeamMember> getTeamMemberByTeamName(String teamName);

	public List<TeamMember> getTeamMemberByPersonId(Integer personId);
	
	public List<TeamMember> getTeamMembersByDate(Date joinDateFrom, Date joinDateTo);
	
	public List<TeamMember> getTeamMembersByTeamLead(boolean isTeamLead);
	
	public List<TeamMember> getTeamMemberByTeamRoleId(Integer teamRoleId);

	public List<TeamMember> getTeamMemberByRetired(boolean retired);
	
	public List<TeamMember> getTeamMemberByLocationId(Integer id);
					
	public List<TeamMember> getTeamMemberByPatientId(Integer id);
	
	public List<TeamMember> getAllTeamMember(boolean isRetired);
	
	public List<TeamMember> getTeamMemberByTeam(Integer teamId, String teamName, Integer teamLeadId, Boolean retired);
	
	public List<TeamMember> getTeamMemberByTeamWithPage(Integer teamId, String teamName, Integer teamLeadId, Boolean retired);
	
	public void save(TeamMember teamMember);
	
	public void saveLocation(Location location);
	
	public void savePatient(Patient patient);
	
	public void purgeMember(TeamMember teamMember);
	
	public void update(TeamMember teamMember);
	
	public List<TeamMember> searchTeamMember(String name);

	public List<TeamMember> searchTeamMemberByTeam(String name, Integer teamId);
}
