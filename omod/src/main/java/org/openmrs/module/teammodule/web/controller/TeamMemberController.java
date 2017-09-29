/**
 * 
 */

package org.openmrs.module.teammodule.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.api.TeamRoleService;
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
@RequestMapping(value = "module/teammodule/teamMember")
public class TeamMemberController {

	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());

	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/teammodule/teamMember";

	/**
	 * Initially called after the formBackingObject method to get the landing
	 * form name
	 * 
	 * @return String form view name
	 */
	/**
	 * @param model
	 * @param request
	 * @return
	 */

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Model model, HttpServletRequest request) {
		try {
			model.addAttribute("allTeams", Context.getService(TeamService.class).getAllTeams(null, null, null));
			model.addAttribute("allSupervisors", Context.getService(TeamMemberService.class).searchTeamMember(null, null, null, null, null, null, null, null, null, 0, 1000));
			model.addAttribute("allTeamRoles", Context.getService(TeamRoleService.class).getAllTeamRole(null, null, null, null));
			model.addAttribute("allLocations", Context.getLocationService().getAllLocations());
			model.addAttribute("allTeamMembers", Context.getService(TeamMemberService.class).getAllTeamMember(null, true, null, null));
		}
		catch(Exception e) { e.printStackTrace(); throw new RuntimeException(e); }
		return SUCCESS_FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(Model model, HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, BindingResult errors,
			/*@ModelAttribute("filterTeamMember") TeamMember searchMember,*/ HttpServletRequest request) {

		
		try {
			if (errors.hasErrors()) {
				System.out.println("ERROR: " + errors);
				return "redirect:/module/teammodule/teamMember.form";
			}
			
			String id = "", supervisorId = "", teamRoleId = "", teamId = "", locationId = "";
			
			if(request.getParameter("identifier") != null) { id = request.getParameter("identifier"); }
			if(request.getParameter("supervisor") != null) { supervisorId = request.getParameter("supervisor"); }
			if(request.getParameter("role") != null) { teamRoleId = request.getParameter("role"); }
			if(request.getParameter("team") != null) { teamId = request.getParameter("team"); }
			if(request.getParameter("location") != null) { locationId = request.getParameter("location"); }
			
			if(id.equals("") ) { id = null; }
			if(supervisorId.equals("") ) { supervisorId = null; }
			if(teamId.equals("") ) { teamId = null; }
			if(teamRoleId.equals("") ) { teamRoleId = null; }
			if(locationId.equals("") ) { locationId = null; }

			model.addAttribute("id", id);
			if(supervisorId != null) { 
				if(supervisorId.matches("^[+-]?\\d+$")) { model.addAttribute("supervisorId", (Context.getService(TeamMemberService.class).getTeamMember(Integer.parseInt(supervisorId))).getId()); } 
				else { model.addAttribute("supervisorId", (Context.getService(TeamMemberService.class).getTeamMemberByUuid(supervisorId)).getId()); } 
			} else { model.addAttribute("supervisorId", ""); }
			if(teamId != null) { 
				if(teamId.matches("^[+-]?\\d+$")) { model.addAttribute("teamId", (Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId))).getId()); } 
				else { model.addAttribute("teamId", (Context.getService(TeamService.class).getTeamByUuid(teamId)).getId()); } 
			} else { model.addAttribute("teamId", ""); }
			if(teamRoleId != null) { 
				if(teamRoleId.matches("^[+-]?\\d+$")) { model.addAttribute("teamRoleId", (Context.getService(TeamRoleService.class).getTeamRoleById(Integer.parseInt(teamRoleId))).getId()); } 
				else { model.addAttribute("teamRoleId", (Context.getService(TeamRoleService.class).getTeamRoleByUuid(teamRoleId)).getId()); } 
			} else { model.addAttribute("teamRoleId", ""); }
			if(locationId != null) { 
				if(locationId.matches("^[+-]?\\d+$")) { model.addAttribute("locationId", (Context.getLocationService().getLocation(Integer.parseInt(locationId))).getId()); } 
				else { model.addAttribute("locationId", (Context.getLocationService().getLocation(locationId)).getId()); } 
			} else { model.addAttribute("locationId", ""); }
			
			model.addAttribute("allTeams", Context.getService(TeamService.class).getAllTeams(false, 0, 1000));
			model.addAttribute("allSupervisors", Context.getService(TeamMemberService.class).searchTeamMember(null, null, null, null, null, null, null, null, null, 0, 1000));
			model.addAttribute("allTeamRoles", Context.getService(TeamRoleService.class).getAllTeamRole(true, false, null, null));
			model.addAttribute("allLocations", Context.getLocationService().getAllLocations());
			model.addAttribute("allTeamMembers", Context.getService(TeamMemberService.class).getAllTeamMember(null, true, null, null));
		}
		catch(Exception e) { e.printStackTrace(); throw new RuntimeException(e); }
		return SUCCESS_FORM_VIEW;
	}
}