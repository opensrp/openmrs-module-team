/**
 * 
 */
package org.openmrs.module.teammodule.web.controller;

//import java.util.ArrayList;
//import java.util.Date;
//import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
//import org.openmrs.module.teammodule.Team;
//import org.openmrs.module.teammodule.TeamLead;
//import org.openmrs.module.teammodule.api.TeamLeadService;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamMemberService;
import org.openmrs.module.teammodule.api.TeamService;
//import org.openmrs.module.teammodule.api.TeamService;
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
@RequestMapping(value = "module/teammodule/allMember")
public class AllMember {

	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());

	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/teammodule/allMember";

	/**
	 * Initially called after the formBackingObject method to get the landing
	 * form name
	 * 
	 * @return String form view name
	 */
	@SuppressWarnings("unused")
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Model model, HttpServletRequest request) {

		List<TeamMember> allMembers;
		List<String> parsedDate = new ArrayList<String>();
		String dateFrom = null;
		String dateTo = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		List<TeamMember> dateSearch = null;
		List<Team> teams = Context.getService(TeamService.class).getAllTeams(false);

		TeamMember searchMember = new TeamMember();

		String searchedMember = null;
		Team team = null;
		// try {
		/*String teamId = request.getParameter("team");
		if(teamId == ""){
			teamId = null;
		}else{
			team = Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId));
		}*/
		searchedMember = request.getParameter("searchMember");
		if(searchedMember == ""){
			searchedMember = null;
		}
			
		dateFrom = request.getParameter("from");
		dateTo = request.getParameter("to");
		/*
		 * } catch (Exception e) { searchedMember = ""; dateFrom = ""; dateTo =
		 * ""; }
		 */

		Date joinFrom = null;
		Date joinTo = null;
		try {
			joinFrom = new SimpleDateFormat("dd/MM/yyyy").parse(dateFrom);
			joinTo = new SimpleDateFormat("dd/MM/yyyy").parse(dateTo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (searchedMember != null || (!dateFrom.isEmpty() && !dateTo.isEmpty())) {
			if (searchedMember != null) {
				allMembers = (List<TeamMember>) Context.getService(TeamMemberService.class).searchMember(searchedMember);
				for (int i = 0; i < allMembers.size(); i++) {
					if (allMembers.get(i).getJoinDate() != null) {
						String date = sdf.format(allMembers.get(i).getJoinDate());
						parsedDate.add(date);
					} else {
						parsedDate.add(null);
					}
				}
				model.addAttribute("parsedDate", parsedDate);
				model.addAttribute("searchedMember", searchedMember);
				model.addAttribute("allMembers", allMembers);
			} else {
				dateSearch = Context.getService(TeamMemberService.class).getMembers(joinFrom, joinTo);
				for (int i = 0; i < dateSearch.size(); i++) {
					if (dateSearch.get(i).getJoinDate() != null) {
						String date = sdf.format(dateSearch.get(i).getJoinDate());
						parsedDate.add(date);
					} else {
						parsedDate.add(null);
					}
				}
				model.addAttribute("parsedDate", parsedDate);
				model.addAttribute("allMembers", dateSearch);
				model.addAttribute("dateTo", dateTo);
				model.addAttribute("dateFrom", dateFrom);
			}

		}/*else if(team != null){
			allMembers = Context.getService(TeamMemberService.class).getTeamMembers(team, null, null, false);
			for (int i = 0; i < allMembers.size(); i++) {
				if (allMembers.get(i).getJoinDate() != null) {
					String date = sdf.format(allMembers.get(i).getJoinDate());
					parsedDate.add(date);
				} else {
					parsedDate.add(null);
				}
			}
			model.addAttribute("parsedDate", parsedDate);
			model.addAttribute("searchedMember", searchedMember);
			model.addAttribute("allMembers", allMembers);
		}*/
		
		
		
		else {
			allMembers = Context.getService(TeamMemberService.class).getAllMembers(true);
			for (int i = 0; i < allMembers.size(); i++) {
				if (allMembers.get(i).getJoinDate() != null) {
					String date = sdf.format(allMembers.get(i).getJoinDate());
					parsedDate.add(date);
				} else {
					parsedDate.add(null);
				}
				model.addAttribute("allMembers", allMembers);
				model.addAttribute("searchMember", searchMember);
				model.addAttribute("parsedDate", parsedDate);
			}
		}

		model.addAttribute("teams", teams);
		return SUCCESS_FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(Model model, HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, BindingResult errors,
			@ModelAttribute("searchMember") TeamMember searchMember, HttpServletRequest request) {

		if (errors.hasErrors()) {
			// return error view
		}
		String memberName = request.getParameter("memberName");
		String joinDateFrom = request.getParameter("joinDateFrom");
		String joinDateTo = request.getParameter("joinDateTo");
		
		//String team = request.getParameter("team");

		return "redirect:/module/teammodule/allMember.form?searchMember=" + memberName + "&from=" + joinDateFrom + "&to=" + joinDateTo;

	}

}
