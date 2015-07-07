/**
 * 
 */
package org.openmrs.module.teammodule.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamMemberService;
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
@RequestMapping(value = "/module/teammodule/editTeam")
public class TeamEditFormController {
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	// SessionFactory sessionFactory;
	// Configuration config = new Configuration();
	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/teammodule/teamEditForm";
	//private final String SUCCESS_REDIRECT_LINK = "redirect:/module/teammodule/teamForm/editTeam";

	/**
	 * Initially called after the formBackingObject method to get the landing
	 * form name
	 * 
	 * @return String form view name
	 */
	@ModelAttribute("teamData")
	public Team populateTeamM(@RequestParam(value = "teamId",required=true)int teamId) {
		Team teamData = new Team();

		teamData = Context.getService(TeamService.class).getTeam(teamId);
		return teamData;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showFormEidt(Model model, HttpServletRequest request, @RequestParam(value = "teamId", required = true) int teamId, @ModelAttribute("teamData") Team team) {
		Team teamData = new Team();

		teamData = Context.getService(TeamService.class).getTeam(teamId);
		String error = request.getParameter("error");
		model.addAttribute("error", error);
		String edit = request.getParameter("edit");
		model.addAttribute("edit", edit);

		// TO MAKE TEAM LOCATION UNEDITABLE
		/*
		 * Integer locationId = teamData.getLocation().getLocationId(); Location
		 * location = Context.getLocationService().getLocation(locationId);
		 * List<Location> locationList = new ArrayList<Location>();
		 * locationList.add(location);
		 */
		
		Location l = Context.getLocationService().getLocation(team.getLocation().getLocationId());
		List<Location> location = Context.getLocationService().getAllLocations();

		model.addAttribute("teamData", teamData);
		model.addAttribute("location", location);
		model.addAttribute("defaultLocation", l);
		model.addAttribute("teamId", teamId);
		// model.addAttribute("memberData", teamMember);

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
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmitEdit(HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, HttpServletRequest request,
	/*
	 * @RequestParam(value = "teamIdentifier", required = false) Integer
	 * identifier,
	 */@ModelAttribute("teamData") Team team, BindingResult errors, Model model) {

		if (errors.hasErrors()) {
			// return error view
		}
		String error = "";

		Context.getService(TeamService.class).saveTeam(team);
		String edit = "Team edited successfully";
		model.addAttribute("edit", edit);

		model.addAttribute("error", error);
		// Context.getService(TeamMemberService.class).save(teamMember);

		return "redirect:/module/teammodule/editTeam.form?teamId=" + team.getTeamId();
	}

}
