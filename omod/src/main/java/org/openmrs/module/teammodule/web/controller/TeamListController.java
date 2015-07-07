/**
 * 
 */
package org.openmrs.module.teammodule.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.api.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Muhammad Safwan
 * 
 */
@Controller
@RequestMapping(value = "module/teammodule/teamList")
public class TeamListController {
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	SessionFactory sessionFactory;
	// Configuration config = new Configuration();
	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/teammodule/teamList";

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Model model) {
		// Team team = new Team();
		// model.addAttribute("teamData", team);
		List<Team> team;

		if (Context.isAuthenticated()) {
			team = Context.getService(TeamService.class).getAllTeams(true);
			model.addAttribute("team", team);
		}
		return SUCCESS_FORM_VIEW;
	}

	//not used till now
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, BindingResult errors, HttpServletRequest request,Model model) {

		if (errors.hasErrors()) {
			// return error view
		}
		return "redirect:/module/teammodule/teamForm.form?type=add";
	}

}
