/**
 * 
 */
package org.openmrs.module.teammodule.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;
//import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
@RequestMapping(value = "module/teammodule/searchedTeam")
public class SearchedTeamController {

	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());

	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/teammodule/searchedTeam";

	/**
	 * Initially called after the formBackingObject method to get the landing
	 * form name
	 * 
	 * @return String form view name
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Model model, HttpServletRequest request) {

		Team searchTeam = new Team();
		String searchedTeam = request.getParameter("searchTeam");
		List<String> parsedDate = new ArrayList<String>();
		List<Integer> length = new ArrayList<Integer>();
		List<String> teamLead = new ArrayList<String>();
		List<TeamMember> teamMember;
		TeamLead lead;
		List<Team> team = Context.getService(TeamService.class).searchTeam(searchedTeam);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		for (int i = 0; i < team.size(); i++) {
			String date = sdf.format(team.get(i).getDateCreated());
			teamMember = Context.getService(TeamMemberService.class).getTeamMembers(team.get(i), null, null,true);
			parsedDate.add(date);
			length.add(teamMember.size());
			lead = Context.getService(TeamLeadService.class).getTeamLead(team.get(i));

			if (lead != null && lead.isVoided() != true) { // change made here
				TeamMember tm = lead.getTeamMember();
				String gName = tm.getPerson().getGivenName();
				String mName = tm.getPerson().getMiddleName();
				String fName = tm.getPerson().getFamilyName();
				String name = "";
				if (gName != null) {
					name = name + " " + gName;
				}
				if (mName != null) {
					name = name + " " + mName;
				}
				if (fName != null) {
					name = name + " " + fName;
				}

				teamLead.add(name);
			} else {
				teamLead.add(null);
			}
		}
		model.addAttribute("team", team);
		model.addAttribute("parsedDate", parsedDate);
		model.addAttribute("searchedTeam",searchedTeam);
		model.addAttribute("length", length);
		/*
		 * String dateFrom = request.getParameter("from"); String dateTo =
		 * request.getParameter("to"); System.out.println("From:" + dateFrom);
		 * System.out.println("To:" + dateTo); Date joinFrom = null; Date joinTo
		 * = null; try { joinFrom = new
		 * SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",
		 * Locale.ENGLISH).parse(dateFrom); joinTo = new
		 * SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",
		 * Locale.ENGLISH).parse(dateTo); } catch (Exception e) {
		 * e.printStackTrace(); }
		 * 
		 * System.out.println(joinFrom); System.out.println(joinTo);
		 * List<TeamMember> teamMember =
		 * Context.getService(TeamMemberService.class
		 * ).searchMember(searchMember);
		 * 
		 * List<TeamMember> dateSearch =
		 * Context.getService(TeamMemberService.class).getMembers(joinFrom,
		 * joinTo);
		 * 
		 * System.out.println(teamMember); if (!teamMember.isEmpty()) {
		 * model.addAttribute("teamMember", teamMember); } else {
		 * model.addAttribute("dateSearch", dateSearch); }
		 */
		model.addAttribute("searchTeam", searchTeam);

		return SUCCESS_FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(Model model, HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, BindingResult errors, @ModelAttribute("searchTeam") Team team) {

		if (errors.hasErrors()) {
			// return error view
		}

		return SUCCESS_FORM_VIEW;
	}

}
