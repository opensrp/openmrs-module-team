/**
 * 
 */
package org.openmrs.module.teammodule.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamHierarchy;
import org.openmrs.module.teammodule.api.TeamHierarchyService;
import org.openmrs.module.teammodule.api.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Muhammad Safwan
 * 
 */
@Controller
@RequestMapping(value = "/module/teammodule")
public class TeamHierarchyEditFormController {
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	// SessionFactory sessionFactory;
	// Configuration config = new Configuration();
	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/teammodule/teamHierarchyEditForm";

	// private final String SUCCESS_REDIRECT_LINK =
	// "redirect:/module/teammodule/teamForm/editTeam";

	/**
	 * Initially called after the formBackingObject method to get the landing
	 * form name
	 * 
	 * @return String form view name
	 */
	
	@RequestMapping(value = "editHierarchyTeam", method = RequestMethod.GET)
	public String showFormEdit(
			Model model,
			HttpServletRequest request,
			@RequestParam(value = "location", required = true) String locationUuid,
			@RequestParam(value = "name", required = true) String teamName,
			@RequestParam(value = "hierarchy", required = true) String hierarchy) {
		Team teamData = new Team();
		Location location = Context.getLocationService().getLocationByUuid(
				locationUuid);
		teamData = Context.getService(TeamService.class).getTeam(teamName,
				location.getLocationId());
		TeamHierarchy teamHierarchy = Context.getService(TeamHierarchyService.class).getTeamRoleByUuid(hierarchy);
		String error = request.getParameter("error");
		model.addAttribute("error", error);
		String edit = request.getParameter("edit");
		model.addAttribute("edit", edit);
		model.addAttribute("teamData", teamData);
		model.addAttribute("teamHierarchy", teamHierarchy);
		return SUCCESS_FORM_VIEW;
	}

	/**
	 * All the parameters are optional based on the necessity
	 * 
	 * @param httpSession
	 * @param anyRequestObject
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = "editHierarchyTeam", method = RequestMethod.POST)
	public String onSubmitEdit(HttpSession httpSession,
			@ModelAttribute("anyRequestObject") Object anyRequestObject,
			HttpServletRequest request,
			/*
			 * @RequestParam(value = "teamIdentifier", required = false) Integer
			 * identifier,
			 */@ModelAttribute("teamData") Team team, BindingResult errors,
			Model model) {

		if (errors.hasErrors()) {
			// return error view
		}
		String error = "";

		Context.getService(TeamService.class).saveTeam(team);
		String edit = "Team edited successfully";
		model.addAttribute("edit", edit);

		model.addAttribute("error", error);
		// Context.getService(TeamMemberService.class).save(teamMember);

		return "redirect:/module/teammodule/editHierarchyTeam.form?teamId="
				+ team.getTeamId();
	}

}
