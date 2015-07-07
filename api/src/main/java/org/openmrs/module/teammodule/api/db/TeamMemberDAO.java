/**
 * 
 */
package org.openmrs.module.teammodule.api.db;

import java.util.Date;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamMember;

/**
 * @author Muhammad Safwan
 *
 */
public interface TeamMemberDAO {
	
	public void save(TeamMember teamMember);
	
	public void saveLocation(Location location);
	
	public List<TeamMember> getTeamMembers(Team team, String name, Integer teamLeadId, Boolean retired);
	
	public List<TeamMember> getTeamMembers(Integer id);
	
	public List<TeamMember> getAllMembers(boolean retired);
	
	public List<TeamMember> getMembers(Date joinDateFrom, Date joinDateTo);
	
	public TeamMember getMember(int id);
	
	public List<TeamMember> getMember(String name);
	
	public List<TeamMember> getMemberByPersonId(int id);
	
	public List<TeamMember> searchMember(String name);
	
	List<TeamMember> searchMemberByTeam(String name,int teamId);
	
	public TeamMember getTeamMember(String uuid);
	
	public void purgeMember(TeamMember teamMember);
	
	public void update(TeamMember teamMember);
	
	//public SQLQuery getCount(Integer teamId);
	
	//public List<TeamMember> getLikeMember(String name);

}
