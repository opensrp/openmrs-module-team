/**
 * 
 */
package org.openmrs.module.teammodule.web.controller;

//import java.util.ArrayList;
//import java.util.List;

//import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//import net.sf.ehcache.hibernate.HibernateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
//import org.openmrs.module.teammodule.api.TeamMemberService;
//import org.openmrs.module.teammodule.api.TeamMemberService;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamMember;
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
@RequestMapping(value = "/module/teammodule/addTeam")
public class TeamAddFormController {
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	// SessionFactory sessionFactory;
	// Configuration config = new Configuration();
	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/teammodule/teamAddForm";
	//private final String SUCCESS_REDIRECT_LINK = "redirect:/module/teammodule/addTeam";

	/**
	 * Initially called after the formBackingObject method to get the landing
	 * form name
	 * 
	 * @return String form view name
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Model model, HttpServletRequest request) {

		Team team = new Team();
		List<Location> location = Context.getLocationService().getAllLocations();
		// System.out.println(location);
		// TeamMember teamMember = new TeamMember();
		String error = request.getParameter("error");
		model.addAttribute("error", error);
		String saved = request.getParameter("saved");
		model.addAttribute("saved", saved);
		model.addAttribute("teamData", team);
		model.addAttribute("location", location);
		
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
	// not coming here .. going in teamForm's POST method
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, HttpServletRequest request,
	/*
	 * @RequestParam(value = "teamIdentifier", required = false) Integer
	 * identifier,
	 */@ModelAttribute("teamData") Team team, BindingResult errors, /*
											 * @ModelAttribute("memberData")
											 * TeamMember teamMember,
											 */Model model) {
		String error = "";
		if (errors.hasErrors()) {
			// return error view
		}
		
		team.setUuid(UUID.randomUUID().toString());

		Context.getService(TeamService.class).saveTeam(team);
		String saved = "Team saved successfully";
		model.addAttribute("saved", saved);
		
		List<Location> location = Context.getLocationService().getAllLocations();
		model.addAttribute("location", location);
		model.addAttribute("error", error);
		// Context.getService(TeamMemberService.class).save(teamMember);

		return SUCCESS_FORM_VIEW;
	}

	/*@RequestMapping(method = RequestMethod.GET, value = "teamForm")
	public String showTeamForm(Model model, HttpServletRequest request) {

		Team teamData = new Team();
		// TeamMember teamMember = new TeamMember();
		model.addAttribute("teamData", teamData);
		String error = request.getParameter("error");
		model.addAttribute("error", error);
		System.out.println(error);
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
	/*@RequestMapping(method = RequestMethod.POST, value = "teamForm")
	public String onSubmitTeamForm(HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, BindingResult errors, HttpServletRequest request,
	/*
	 * @RequestParam(value = "teamIdentifier", required = false) Integer
	 * identifier,
	 @ModelAttribute("teamData") Team team, @ModelAttribute("memberData") TeamMember teamMember, Model model) {

		if (errors.hasErrors()) {
			// return error view
		}

		// Context.getService(TeamService.class).saveTeam(team);

		return SUCCESS_REDIRECT_LINK;
	}*/

}
