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
			model.addAttribute("allTeamMembers", Context.getService(TeamMemberService.class).getAllTeamMember(null, null, null, null));
		}
		catch(Exception e) { e.printStackTrace(); throw new RuntimeException(e); }
		return SUCCESS_FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(Model model, HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject,
			BindingResult errors, HttpServletRequest request) {
		
		try {
			model.addAttribute("allTeams", Context.getService(TeamService.class).getAllTeams(null, null, null));
			model.addAttribute("allSupervisors", Context.getService(TeamMemberService.class).searchTeamMember(null, null, null, null, null, null, null, null, null, 0, 1000));
			model.addAttribute("allTeamRoles", Context.getService(TeamRoleService.class).getAllTeamRole(null, null, null, null));
			model.addAttribute("allLocations", Context.getLocationService().getAllLocations());
			model.addAttribute("allTeamMembers", Context.getService(TeamMemberService.class).getAllTeamMember(null, null, null, null));
			model.addAttribute("team",request.getParameter("team"));
			model.addAttribute("role",request.getParameter("role"));
		}
		catch(Exception e) { e.printStackTrace(); throw new RuntimeException(e); }
		return SUCCESS_FORM_VIEW;
	}
}