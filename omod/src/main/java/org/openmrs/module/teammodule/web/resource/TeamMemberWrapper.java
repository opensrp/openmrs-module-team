/**
 * 
 */
package org.openmrs.module.teammodule.web.resource;

import org.openmrs.User;
import org.openmrs.module.teammodule.TeamMember;

/**
 * @author Safwan
 *
 */
public class TeamMemberWrapper {
	
	public TeamMember teamMember;
	
	public User user;
	
	public String uuid;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public TeamMemberWrapper(TeamMember teamMember){
		this.teamMember = teamMember;
		this.uuid = teamMember.getUuid();
	}
	
	public TeamMember getTeamMember() {
		return teamMember;
	}

	public void setTeamMember(TeamMember teamMember) {
		this.teamMember = teamMember;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	

}
