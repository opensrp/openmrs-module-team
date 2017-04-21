/**
 *  @author Shakeeb Raza
 */
package org.openmrs.module.teammodule.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Privilege;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamLead;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamLeadService;
import org.openmrs.module.teammodule.api.TeamMemberService;
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
@RequestMapping(value = "module/teammodule/team.form")
public class TeamController {
	HttpServletRequest request;
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());

	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/teammodule/team";

	/**
	 * Initially called after the formBackingObject method to get the landing
	 * form name
	 * 
	 * @return String form view name
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Model model, HttpServletRequest request) {
		try{
		List<Map<String, Object>> teamData = new ArrayList<>();
		String searchedTeam = request.getParameter("searchTeam");

			List<Team> teams = new ArrayList<>();
			if (searchedTeam != null) {
				teams  = Context.getService(TeamService.class).searchTeam(searchedTeam);
			} else {
				teams = Context.getService(TeamService.class).getAllTeams(true);
			}
			
			for (Team tm : teams) {
				List<TeamMember> teamMember = Context.getService(TeamMemberService.class).getTeamMembers(tm, null, null, false);
				TeamLead lead = Context.getService(TeamLeadService.class).getTeamLead(tm);

				Map<String, Object> map = new HashMap<>();
				map .put("team", tm);
				map.put("membersCount", teamMember.size());
				map.put("searchTeam", searchedTeam);
				map.put("teamLead", lead!=null&&!lead.isVoided()?lead:null);
				map.put("searchedTeam", searchedTeam);
				
				teamData.add(map);
			}
			
			model.addAttribute("teams", teamData);
		}
		catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
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
	public String onSubmit(Model model, HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, BindingResult errors, @ModelAttribute("searchTeam") Team searchTeam) {

		if (errors.hasErrors()) {
			// return error view
		}

		return "redirect:/module/teammodule/team.form?searchTeam=" + searchTeam.getTeamName();
	}

}
