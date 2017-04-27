/**
 * 
 */

package org.openmrs.module.teammodule.web.controller;

import java.util.ArrayList;
import java.util.HashSet;
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
		List<TeamMember> teamMembers = (List<TeamMember>) Context.getService(TeamMemberService.class).getAllTeamMember(null, true, null, null);
		
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
		List<String> allLocationIds = new ArrayList<>();
		List<String> allLocationNames = new ArrayList<>();

		Set<String> temp = new HashSet<>();
		
		//System.out.println("size: " + teamMembers.size());
		
		for (int i = 0; i < teamMembers.size(); i++) {			
			if(teamMembers.get(i) == null) { }
			else {
				//TEAM MEMBER ID FOR MODEL
				teamMemberIds.add(teamMembers.get(i).getId());
				
				//TEAM MEMBER LOCATION NAME FOR MODEL LAYER
				Set<Location> tml = teamMembers.get(i).getLocation();
				if(tml == null) { teamMemberLocations.add("No Location"); }
				else { String locationNames = "";
					for (Iterator iterator = tml.iterator(); iterator.hasNext();) {
						Location location = (Location) iterator.next();
						locationNames += location.getName();
						if(iterator.hasNext()) { locationNames += ", "; }
					} teamMemberLocations.add(locationNames); 
				}

				//TEAM MEMBER PATIENT NAMES FOR MODEL LAYER
				Set<TeamMemberPatientRelation> tmp = teamMembers.get(i).getPatients();
				if(tmp == null) { teamMemberPatients.add(Integer.toString(0)); } 
				else { teamMemberPatients.add(Integer.toString(tmp.size())); }
				
				//TEAM MEMBER REPORTS TO TEAM LEAD NAME FOR MODEL LAYER
				if(teamMembers.get(i).getTeamRole() == null) { reportsTo.add("No Report To"); }
				else {
					if(teamMembers.get(i).getTeamRole().getReportTo() == null) { reportsTo.add("No Report To"); }
					else { reportsTo.add(teamMembers.get(i).getTeamRole().getReportTo().getPerson().getPersonName().toString()); } 
				}

				//TEAM MEMBER SUB ROLE NAME FOR MODEL LAYER
				if(teamMembers.get(i).getTeamRole() == null) { subRoles.add("No Sub Role"); }
				else { subRoles.add(teamMembers.get(i).getTeamRole().getName().toString()); }

				//TEAM MEMBER SUB TEAM NAME FOR MODEL LAYER
				List<TeamMember> tm = Context.getService(TeamMemberService.class).getTeamMemberByPersonId(teamMembers.get(i).getPerson().getId());
				if(tm == null) { subTeams.add("No Sub Team"); }
				else { String teamNames = "";
					for (int j = 0; j < tm.size(); j++) { 
						if(j==tm.size()-1) { teamNames += tm.get(j).getTeam().getTeamName() + ""; }
						else { teamNames += tm.get(j).getTeam().getTeamName() + ", "; }
					} subTeams.add(teamNames);
				}
				
				//ALL SUPERVISORS FOR MODEL LAYER
				allSupervisorIds.add(Integer.toString(teamMembers.get(i).getTeam().getSupervisor().getId()));
				
				//ALL TEAM ROLES FOR MODEL LAYER
				allTeamRoleIds.add(Integer.toString(teamMembers.get(i).getTeamRole().getId()));

				//ALL TEAMS FOR MODEL LAYER
				for (int j = 0; j < tm.size(); j++) { allTeamIds.add(Integer.toString(tm.get(j).getTeam().getId())); }

				//ALL LOCATIONS FOR MODEL LAYER
				for (Iterator iterator = tml.iterator(); iterator.hasNext();) { allLocationIds.add(Integer.toString(((Location) iterator.next()).getId())); } 
			}
		}
				
		//REMOVE DUPLICATES FROM allLocationIds
		temp.addAll(allLocationIds);
		allLocationIds.clear();
		allLocationIds.addAll(temp);
		temp.clear();
		
		//REMOVE DUPLICATES FROM allTeamIds
		temp.addAll(allTeamIds);
		allTeamIds.clear();
		allTeamIds.addAll(temp);
		temp.clear();
		
		//REMOVE DUPLICATES FROM allSupervisorIds
		temp.addAll(allSupervisorIds);
		allSupervisorIds.clear();
		allSupervisorIds.addAll(temp);
		temp.clear();
		
		//REMOVE DUPLICATES FROM allTeamRoleIds
		temp.addAll(allTeamRoleIds);
		allTeamRoleIds.clear();
		allTeamRoleIds.addAll(temp);
		temp.clear();

		for (int j = 0; j < allSupervisorIds.size(); j++) { TeamMember tm = Context.getService(TeamMemberService.class).getTeamMember(Integer.parseInt(allSupervisorIds.get(j))); if(tm == null) { allSupervisorNames.add("Null Supervisor"); } else { allSupervisorNames.add(tm.getPerson().getPersonName().toString()); } }
		for (int j = 0; j < allTeamRoleIds.size(); j++) { TeamHierarchy th = Context.getService(TeamHierarchyService.class).getTeamRoleById(Integer.parseInt(allTeamRoleIds.get(j))); if(th == null) { allTeamRoleNames.add("Null Team Role"); } else { allTeamRoleNames.add(th.getName().toString()); } }
		for (int j = 0; j < allTeamIds.size(); j++) { Team t = Context.getService(TeamService.class).getTeam(Integer.parseInt(allTeamIds.get(j))); if(t == null) { allTeamNames.add("Null Team"); } else { allTeamNames.add(t.getTeamName().toString()); } }
		for (int j = 0; j < allLocationIds.size(); j++) { Location l = Context.getLocationService().getLocation(Integer.parseInt(allLocationIds.get(j))); if(l == null) { allLocationNames.add("Null Location"); } else { allLocationNames.add(l.getName().toString()); } }

		/*System.out.println("teamMembers: " + teamMembers);
		System.out.println("teamMemberIds: " + teamMemberIds);
		System.out.println("teamMemberLocations: " + teamMemberLocations);
		System.out.println("teamMemberPatients: " + teamMemberPatients);
		System.out.println("reportsTo: " + reportsTo);
		System.out.println("subRoles: " + subRoles);
		System.out.println("subTeams: " + subTeams);

		System.out.println();
		System.out.println();
		
		System.out.println("allSupervisorIds: " + allSupervisorIds);
		System.out.println("allSupervisorNames: " + allSupervisorNames);
		System.out.println("allTeamRoleIds: " + allTeamRoleIds);
		System.out.println("allTeamRoleNames: " + allTeamRoleNames);
		System.out.println("allTeamIds: " + allTeamIds);
		System.out.println("allTeamNames: " + allTeamNames);
		System.out.println("allLocationIds: " + allLocationIds);
		System.out.println("allLocationNames: " + allLocationNames);

		System.out.println();
		System.out.println();*/

		model.addAttribute("teamMembers", teamMembers);
		model.addAttribute("teamMemberIds", teamMemberIds);
		model.addAttribute("teamMemberLocations", teamMemberLocations);
		model.addAttribute("teamMemberPatients", teamMemberPatients);
		model.addAttribute("reportsTo", reportsTo);
		model.addAttribute("subRoles", subRoles);
		model.addAttribute("subTeams", subTeams);
		
		model.addAttribute("allSupervisorIds", allSupervisorIds);
		model.addAttribute("allSupervisorNames", allSupervisorNames);
		model.addAttribute("allTeamRoleIds", allTeamRoleIds);
		model.addAttribute("allTeamRoleNames", allTeamRoleNames);
		model.addAttribute("allTeamIds", allTeamIds);
		model.addAttribute("allTeamNames", allTeamNames);
		model.addAttribute("allLocationIds", allLocationIds);
		model.addAttribute("allLocationNames", allLocationNames);

		return SUCCESS_FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(Model model, HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, BindingResult errors,
			@ModelAttribute("filterTeamMember") TeamMember searchMember, HttpServletRequest request) {

		if (errors.hasErrors()) {
			System.out.println("ERROR: " + errors);
			return "redirect:/module/teammodule/teamMemberView.form";
		}
		
		return "redirect:/module/teammodule/teamMemberView.form";
	}
}