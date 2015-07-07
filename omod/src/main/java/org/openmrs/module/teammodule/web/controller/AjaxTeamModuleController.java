/**
 * 
 */
package org.openmrs.module.teammodule.web.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamMemberService;
import org.openmrs.module.teammodule.api.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Muhammad Safwan
 * 
 */

@Controller
@RequestMapping(value = "/module/teammodule/ajax/")
public class AjaxTeamModuleController {

	@RequestMapping(value = "getTeams")
	@ResponseBody
	public String getTeams(HttpServletRequest request) {
		List<Team> team = Context.getService(TeamService.class).getAllTeams(false);
		String error = "";
		// System.out.println("here");
		String teamName = request.getParameter("teamName");
		String locationId = request.getParameter("locationId");
		for (int i = 0; i < team.size(); i++) {
			if (teamName.equals(team.get(i).getTeamName())) {
				if (Integer.parseInt(locationId) == team.get(i).getLocation().getLocationId()) {
					error = "Team already exists with same name and location";
					break;
				}
			} else {

			}
		}
		return error;
	}

	@RequestMapping(value = "getMembers")
	@ResponseBody
	public String getMember(HttpServletRequest request) {

		String error = "";
		// System.out.println("here");
		String identifier = request.getParameter("identifier");
		String teamId = request.getParameter("teamId");
		Team team = Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId));
		List<TeamMember> teamMember = Context.getService(TeamMemberService.class).getTeamMembers(team, null, null, null);
		// System.out.println(teamId);
		// String locationId = request.getParameter("locationId");
		for (int i = 0; i < teamMember.size(); i++) {
			if (identifier.equals(teamMember.get(i).getIdentifier())) {
				// System.out.println(teamMember.get(i).getTeam().getTeamId());
				// if (Integer.parseInt(teamId) ==
				// teamMember.get(i).getTeam().getTeamId()) {
				error = "Member exists with same identifier";
				break;
				// }
			} else {

			}
		}
		return error;
	}

	@RequestMapping(value = "getEditMembers")
	@ResponseBody
	public String getMemberEdit(HttpServletRequest request) {

		String error = "";
		// System.out.println("here");
		String identifier = request.getParameter("identifier");
		String teamId = request.getParameter("teamId");
		String teamMemberId = request.getParameter("teamMemberId");
		Team team = Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId));
		List<TeamMember> teamMember = Context.getService(TeamMemberService.class).getTeamMembers(team, null, null, null);
		// System.out.println(teamId);
		// String locationId = request.getParameter("locationId");
		for (int i = 0; i < teamMember.size(); i++) {
			if (identifier.equals(teamMember.get(i).getIdentifier())) {
				// System.out.println(teamMember.get(i).getTeam().getTeamId());
				if (Integer.parseInt(teamMemberId) != teamMember.get(i).getTeamMemberId()) {
					error = "Member exists with same identifier";
					break;
				}
			} else {

			}
		}
		return error;
	}
	
	@RequestMapping(value = "getLocations")
	@ResponseBody
	public Location getLocations(HttpServletRequest request) {
		String teamId = request.getParameter("teamId");
		Team team = Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId));
		Location location = team.getLocation();
		Set<Location> childLocation = location.getChildLocations();
		if( childLocation == null || childLocation.equals(null) || childLocation.equals("")){
			return null;
		}
		//String error = "";

		/*for (int i = 0; i < team.size(); i++) {
			if (teamName.equals(team.get(i).getTeamName())) {
				if (Integer.parseInt(locationId) == team.get(i).getLocation().getLocationId()) {
					error = "Team already exists with same name and location";
					break;
				}
			} else {

			}
		}*/
		return location;
	}

}
