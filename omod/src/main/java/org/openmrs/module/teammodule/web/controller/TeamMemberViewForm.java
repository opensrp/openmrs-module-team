/**
 * 
 */
package org.openmrs.module.teammodule.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.TeamSupervisor;
import org.openmrs.module.teammodule.TeamHierarchy;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.TeamMemberLocation;
import org.openmrs.module.teammodule.TeamMemberPatientRelation;
import org.openmrs.module.teammodule.api.TeamSupervisorService;
import org.openmrs.module.teammodule.api.TeamHierarchyService;
import org.openmrs.module.teammodule.api.TeamMemberLocationService;
import org.openmrs.module.teammodule.api.TeamMemberPatientRelationService;
import org.openmrs.module.teammodule.api.TeamMemberService;
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
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Model model, HttpServletRequest request) {
		List<TeamMember> teamMembers = (List<TeamMember>) Context.getService(TeamMemberService.class).getAllTeamMember(true);
		
		List<Integer> teamMemberIds = new ArrayList<>();
		List<String> teamMemberLocations = new ArrayList<>();
		List<String> teamMemberPatients = new ArrayList<>();
		List<String> reportsTo = new ArrayList<>();
		List<String> subTeams = new ArrayList<>();

		for (int i = 0; i < teamMembers.size(); i++) {
			//List<String> patientNames = new ArrayList<>();
			
			//TEAM MEMBER ID FOR MODEL
			teamMemberIds.add(teamMembers.get(i).getId());
			
			//TEAM MEMBER LOCATION NAME FOR MODEL
			TeamMemberLocation tml = Context.getService(TeamMemberLocationService.class).getTeamMemberLocationByTeamMemberId(teamMembers.get(i).getId());
			teamMemberLocations.add(tml.getLocation().getName());
			
			//TEAM MEMBER PATIENT NAMES FOR MODEL
			List<TeamMemberPatientRelation> tmp = Context.getService(TeamMemberPatientRelationService.class).getTeamPatientRelationByTeamMember(teamMembers.get(i));
			String patientNames = "";
			for (int j = 0; j < tmp.size(); j++) { 
				if(i==tmp.size()-1) { patientNames += tmp.get(i).getPatient().getPersonName().toString(); }
				else { patientNames += tmp.get(i).getPatient().getPersonName().toString() + ", "; }
			} teamMemberPatients.add(patientNames);
			
			//TEAM MEMBER REPORTS TO TEAM LEAD NAME FOR MODEL
			if(teamMembers.get(i).getIsTeamLead()){ reportsTo.add(""); }
			else { TeamSupervisor tl = Context.getService(TeamSupervisorService.class).getTeamSupervisor(teamMembers.get(i).getTeam()); reportsTo.add(tl.getTeamMember().getPerson().getPersonName().toString()); }
			/*TeamSupervisor tl = Context.getService(TeamSupervisorService.class).getTeamSupervisor(teamMembers.get(i).getTeam());
			reportsTo.add(tl.getTeamMember().getPerson().getPersonName().toString());*/
			
			//TEAM MEMBER SUB TEAM NAME FOR MODEL
			List<TeamMember> tm = Context.getService(TeamMemberService.class).getTeamMemberByPersonId(teamMembers.get(i).getPerson().getId());
			for (int j = 0; j < tm.size(); j++) { subTeams.add(tm.get(j).getTeam().getTeamName()); }
			
		}
		
		System.out.println(teamMembers);
		System.out.println(teamMemberIds);
		System.out.println(teamMemberLocations);
		System.out.println(reportsTo);
		System.out.println(subTeams);
		System.out.println(teamMemberPatients);
		
		model.addAttribute("teamMembers", teamMembers);
		model.addAttribute("teamMemberIds", teamMemberIds);
		model.addAttribute("teamMemberLocations", teamMemberLocations);
		model.addAttribute("teamMemberPatients", teamMemberPatients);
		model.addAttribute("reportsTo", reportsTo);
		model.addAttribute("subTeams", subTeams);
		
		
//		TeamHierarchy th = Context.getService(TeamHierarchyService.class).getTeamRoleById(1);
//		System.out.println(th.getId());
//		System.out.println(th.getName());
//		System.out.println(th.getTeamRoleId());
//		System.out.println(th.getReportTo());
//		System.out.println(th.getOwnsTeam());
		
		
		
		return SUCCESS_FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(Model model, HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, BindingResult errors, @ModelAttribute("searchMember") TeamMember searchMember, HttpServletRequest request) {
		return "redirect:/module/teammodule/teamMemberView.form";
//		return "redirect:/module/teammodule/teamMemberView.form?searchMember=" + memberName + "&from=" + joinDateFrom + "&to=" + joinDateTo;

	}

}