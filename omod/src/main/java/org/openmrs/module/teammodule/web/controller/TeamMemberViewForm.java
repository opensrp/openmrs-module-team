/**
 * 
 */

package org.openmrs.module.teammodule.web.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamHierarchy;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.TeamMemberPatientRelation;
import org.openmrs.module.teammodule.api.TeamHierarchyService;
import org.openmrs.module.teammodule.api.TeamMemberPatientRelationService;
import org.openmrs.module.teammodule.api.TeamMemberService;
import org.openmrs.module.teammodule.api.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Zohaib Masood
 */

@Controller
@RequestMapping(value = "module/teammodule/teamMemberView")
public class TeamMemberViewForm {

	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());

	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/teammodule/teamMemberView";

	/**
	 * Initially called after the formBackingObject method to get the landing
	 * form name
	 * 
	 * @return String form view name
	 */
	@SuppressWarnings({"rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Model model, HttpServletRequest request) {
		List<TeamMember> teamMembers = (List<TeamMember>) Context.getService(TeamMemberService.class).getAllTeamMember(null, true);
		
		List<Integer> teamMemberIds = new ArrayList<>();
		List<String> teamMemberLocations = new ArrayList<>();
		List<String> teamMemberPatients = new ArrayList<>();
		List<String> reportsTo = new ArrayList<>();
		List<String> subTeams = new ArrayList<>();
		List<String> subRoles = new ArrayList<>();
		
		List<String> allSupervisorIds = new ArrayList<>();
		List<String> allSupervisorNames = new ArrayList<>();
		List<String> allTeamRoleIds = new ArrayList<>();
		List<String> allTeamRoleNames = new ArrayList<>();
		List<String> allTeamIds = new ArrayList<>();
		List<String> allTeamNames = new ArrayList<>();
		
		List<String> allLocations = new ArrayList<>();

		System.out.println("size: " + teamMembers.size());
		
		for (int i = 0; i < teamMembers.size(); i++) {			
			if(teamMembers.get(i) == null) {}
			else {
				//TEAM MEMBER ID FOR MODEL
				teamMemberIds.add(teamMembers.get(i).getId());
				//System.out.println("teamMemberIds: " + teamMemberIds);
				
				//TEAM MEMBER LOCATION NAME FOR MODEL LAYER
				Set<Location> tml = teamMembers.get(i).getLocation();
				if(tml == null) { teamMemberLocations.add("No Location"); }
				else { String locationNames = "";
					System.out.println(tml.size());
					for (Iterator iterator = tml.iterator(); iterator.hasNext();) { 
						if(iterator.hasNext()) { locationNames += ((Location) iterator.next()).getName() + ", "; }
						else { locationNames += ((Location) iterator.next()).getName() + ""; }
					} teamMemberLocations.add(locationNames); 
				}
				System.out.println("teamMemberLocations: " + teamMemberLocations);

				//TEAM MEMBER PATIENT NAMES FOR MODEL LAYER
				List<TeamMemberPatientRelation> tmp = Context.getService(TeamMemberPatientRelationService.class).getTeamPatientRelations(teamMembers.get(i));
				if(tmp == null) { teamMemberPatients.add(Integer.toString(0)); } 
				else { teamMemberPatients.add(Integer.toString(tmp.size())); }
				//System.out.println("teamMemberPatients: " + teamMemberPatients);
				
				//TEAM MEMBER REPORTS TO TEAM LEAD NAME FOR MODEL LAYER
				if(teamMembers.get(i).getTeamRole() == null) { reportsTo.add("No Report To"); }
				else {
					if(teamMembers.get(i).getTeamRole().getReportTo() == null) { reportsTo.add("No Report To"); }
					else { reportsTo.add(teamMembers.get(i).getTeamRole().getReportTo().getPerson().getPersonName().toString()); } 
				}
				//System.out.println("reportsTo: " + reportsTo);

				//TEAM MEMBER SUB ROLE NAME FOR MODEL LAYER
				if(teamMembers.get(i).getTeamRole() == null) { subRoles.add("No Sub Role"); }
				else { subRoles.add(teamMembers.get(i).getTeamRole().getName().toString()); }
				//System.out.println("subRoles: " + subRoles);

				//TEAM MEMBER SUB TEAM NAME FOR MODEL LAYER
				List<TeamMember> tm = Context.getService(TeamMemberService.class).getTeamMemberByPersonId(teamMembers.get(i).getPerson().getId());
				if(tm == null) { subTeams.add("No Sub Team"); }
				else { String teamNames = "";
					for (int j = 0; j < tm.size(); j++) { 
						if(j==tm.size()-1) { teamNames += tm.get(j).getTeam().getTeamName() + ""; }
						else { teamNames += tm.get(j).getTeam().getTeamName() + ", "; }
					} subTeams.add(teamNames);
				}
				//System.out.println("subTeams: " + subTeams);
				
				/*//ALL SUPERVISORS FOR MODEL LAYER
				allSupervisorIds.add(Integer.toString(teamMembers.get(i).getTeamRole().getReportTo().getId()));
				System.out.println("allSupervisorIds: " + allSupervisorIds);
				
				//ALL TEAM ROLES FOR MODEL LAYER
				allTeamRoleIds.add(Integer.toString(teamMembers.get(i).getTeamRole().getId()));
				System.out.println("allTeamRoleIds: " + allTeamRoleIds);

				//ALL TEAMS FOR MODEL LAYER
				for (int j = 0; j < tm.size(); j++) { allTeamIds.add(Integer.toString(tm.get(j).getTeam().getId())); }
				System.out.println("allTeamIds: " + allTeamIds);

				//ALL LOCATIONS FOR MODEL LAYER
				for (int j = 0; j < tml.size(); j++) { allLocations.add(teamMembers.get(i).getLocation().toString().replaceAll("[", "").replaceAll("]", "")); }
				System.out.println("allLocations: " + allLocations);*/
			}
		}
		
		System.out.println("teamMembers: " + teamMembers);
		System.out.println("teamMemberIds: " + teamMemberIds);
		System.out.println("teamMemberLocations: " + teamMemberLocations);
		System.out.println("teamMemberPatients: " + teamMemberPatients);
		System.out.println("reportsTo: " + reportsTo);
		System.out.println("subRoles: " + subRoles);
		System.out.println("subTeams: " + subTeams);
		
		model.addAttribute("teamMembers", teamMembers);
		model.addAttribute("teamMemberIds", teamMemberIds);
		model.addAttribute("teamMemberLocations", teamMemberLocations);
		model.addAttribute("teamMemberPatients", teamMemberPatients);
		model.addAttribute("reportsTo", reportsTo);
		model.addAttribute("subRoles", subRoles);
		model.addAttribute("subTeams", subTeams);
		
		/*//REMOVE DUPLICATES FROM allLocations
		Set<String> allLocationSet = new HashSet<>();
		allLocationSet.addAll(allLocations);
		allLocations.clear();
		allLocations.addAll(allLocationSet);

		//REMOVE DUPLICATES FROM allTeamIds
		Set<String> allTeamSet = new HashSet<>();
		allTeamSet.addAll(allTeamIds);
		allTeamIds.clear();
		allTeamIds.addAll(allTeamSet);
		
		//REMOVE DUPLICATES FROM allSupervisorIds
		Set<String> allSupervisorSet = new HashSet<>();
		allSupervisorSet.addAll(allSupervisorIds);
		allSupervisorIds.clear();
		allSupervisorIds.addAll(allSupervisorSet);
		
		//REMOVE DUPLICATES FROM allTeamRoleIds
		Set<String> allTeamRoleSet = new HashSet<>();
		allTeamRoleSet.addAll(allTeamRoleIds);
		allTeamRoleIds.clear();
		allTeamRoleIds.addAll(allTeamRoleSet);*/

	/*	for (int j = 0; j < allSupervisorIds.size(); j++) { TeamSupervisor ts = Context.getService(TeamSupervisorService.class).getTeamSupervisor(Integer.parseInt(allSupervisorIds.get(j))); if(ts == null) { allSupervisorNames.add(""); } else { allSupervisorNames.add(ts.getTeamMember().getPerson().getPersonName().toString()); } }
	*/	for (int j = 0; j < allTeamRoleIds.size(); j++) { TeamHierarchy th = Context.getService(TeamHierarchyService.class).getTeamRoleById(Integer.parseInt(allTeamRoleIds.get(j))); if(th == null) { allTeamRoleNames.add(""); } else { allTeamRoleNames.add(th.getName().toString()); } }
		for (int j = 0; j < allTeamIds.size(); j++) { Team t = Context.getService(TeamService.class).getTeam(Integer.parseInt(allTeamIds.get(j))); if(t == null) { allTeamNames.add(""); } else { allTeamNames.add(t.getTeamName().toString()); } }
		
		/*System.out.println("allSupervisorIds: " + allSupervisorIds);
		System.out.println("allSupervisorNames: " + allSupervisorNames);
		System.out.println("allTeamRoleIds: " + allTeamRoleIds);
		System.out.println("allTeamRoleNames: " + allTeamRoleNames);
		System.out.println("allTeamIds: " + allTeamIds);
		System.out.println("allTeamNames: " + allTeamNames);
		System.out.println("allLocations: " + allLocations);*/

		model.addAttribute("allSupervisorIds", allSupervisorIds);
		model.addAttribute("allSupervisorNames", allSupervisorNames);

		model.addAttribute("allTeamRoleIds", allTeamRoleIds);
		model.addAttribute("allTeamRoleNames", allTeamRoleNames);

		model.addAttribute("allTeamIds", allTeamIds);
		model.addAttribute("allTeamNames", allTeamNames);
		
		model.addAttribute("allLocations", allLocations);
		
		
		
		return SUCCESS_FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(Model model, HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, BindingResult errors,
			@ModelAttribute("filterTeamMember") TeamMember searchMember, HttpServletRequest request) {

		if (errors.hasErrors()) {
			System.out.println("ERROR");
			return "redirect:/module/teammodule/teamMemberView.form";
		}
		
		String teamMemberIdStr = request.getParameter("filterById");
		String supervisorIdStr = request.getParameter("filterBySupervisor");
		String teamRoleIdStr = request.getParameter("filterByTeamRole");
		String teamIdStr = request.getParameter("filterByTeam");
		String locationIdStr = request.getParameter("filterByLocation");
		
		System.out.println(teamMemberIdStr + ", " + supervisorIdStr + ", " + teamRoleIdStr + ", " + teamIdStr + ", " + locationIdStr);
		
		Integer teamMemberId=null, supervisorId=null, teamId=null, teamRoleId=null, locationId=null;
		
		if(teamMemberIdStr.matches("\\d+")) { teamMemberId = Integer.parseInt(teamMemberIdStr); }
		if(teamIdStr.matches("\\d+")) { teamId = Integer.parseInt(teamIdStr); }
		if(teamRoleIdStr.matches("\\d+")) { teamRoleId = Integer.parseInt(teamRoleIdStr); }
		if(supervisorIdStr.matches("\\d+")) { supervisorId = Integer.parseInt(supervisorIdStr); }
		if(locationIdStr.matches("\\d+")) { locationId = Integer.parseInt(locationIdStr); }

		System.out.println(teamMemberId + ", " + supervisorId + ", " + teamRoleId + ", " + teamId + ", " + locationId );
		
/*		List<TeamMember> newTeamMembers = new ArrayList<>();
		List<TeamMember> teamMembers = (List<TeamMember>) Context.getService(TeamMemberService.class).getAllTeamMember(true);
		for (int i = 0; i < teamMembers.size(); i++) {			
			if(teamMembers.get(i).getTeamMemberId() == teamMemberId) {
			}
		}		
*/		
//		TeamHierarchy tr = Context.getService(TeamHierarchyService.class).getTeamRole(teamRole);
		
//		return "redirect:/module/teammodule/teamMemberView.form";
		return "redirect:/module/teammodule/teamMemberView.form?teamMemberId=" + teamMemberIdStr + "&supervisorId=" + supervisorIdStr + "&teamRoleId=" + teamRoleIdStr + "&teamId=" + teamIdStr + "&locationId=" + locationIdStr;
	}
}