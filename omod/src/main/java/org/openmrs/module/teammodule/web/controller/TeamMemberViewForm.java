/**
 * 
 */

package org.openmrs.module.teammodule.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.TeamMember;
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
	/**
	 * @param model
	 * @param request
	 * @return
	 */

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Model model, HttpServletRequest request) {
		
		try {
			model.addAttribute("allTeams", Context.getService(TeamService.class).getAllTeams(false, 0, 1000));
			model.addAttribute("allSupervisors", Context.getService(TeamMemberService.class).searchTeamMember(null, null, null, null, null, 0, 1000));
			model.addAttribute("allTeamHierarchys", Context.getService(TeamHierarchyService.class).getAllTeamHierarchy());
			model.addAttribute("allLocations", Context.getLocationService().getAllLocations());
		}
		catch(Exception e) { e.printStackTrace(); throw new RuntimeException(e); }
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