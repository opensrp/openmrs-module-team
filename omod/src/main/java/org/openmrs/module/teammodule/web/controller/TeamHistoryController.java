/**
 * 
 */
package org.openmrs.module.teammodule.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
//import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamLead;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamLeadService;
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
@RequestMapping(value = "module/teammodule/teamHistory")
public class TeamHistoryController {

	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());

	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/teammodule/teamHistory";

	/**
	 * Initially called after the formBackingObject method to get the landing
	 * form name
	 * 
	 * @return String form view name
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Model model, HttpServletRequest request) {
		List<Map<String, Object>> teamLeadList = new ArrayList<Map<String, Object>>();
		List<String> parsedJoinDate = new ArrayList<String>();
		List<String> parsedLeaveDate = new ArrayList<String>();
		//List<String> gender = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar start = new GregorianCalendar();
		
		Calendar end = new GregorianCalendar();
		
		

		String teamId = request.getParameter("teamId");

		// System.out.println(teamId);
		Team team = Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId));
		// List<List<Object>> list =
		// Context.getAdministrationService().executeSQL("select team_member_id from team_lead where team_id = "
		// + teamId, false);

		// Multiple leads

		List<TeamLead> teamLead = Context.getService(TeamLeadService.class).getTeamLeads(team);
		// System.out.println(teamLead);
		// Date dateCreated = null;
		// Date leaveDate = null;

		if (teamLead != null) {

			for (int i = 0; i < teamLead.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				String name = "";
				TeamMember tm = teamLead.get(i).getTeamMember();
				String gName = tm.getPerson().getGivenName();
				String mName = tm.getPerson().getMiddleName();
				String fName = tm.getPerson().getFamilyName();

				if (gName != null) {
					name = name + " " + gName;
				}
				if (mName != null) {
					name = name + " " + mName;
				}
				if (fName != null) {
					name = name + " " + fName;
				}
				
				

				// dateCreated = teamLead.get(i).getDateCreated();
				if (teamLead.get(i).getJoinDate() != null) {
					String joinDate = sdf.format(teamLead.get(i).getJoinDate());
					parsedJoinDate.add(joinDate);
					start.setTime(teamLead.get(i).getJoinDate());
				}else{
					parsedJoinDate.add(null);
				}
				// Date dateVoided = teamlead.getDateVoided();
				if (teamLead.get(i).getLeaveDate() != null) {
					String leaveDate = sdf.format(teamLead.get(i).getLeaveDate());
					parsedLeaveDate.add(leaveDate);
					end.setTime(teamLead.get(i).getLeaveDate());
				}else{
					if(teamLead.get(i).getVoided() == false){
						if(team.getVoided()){
							parsedLeaveDate.add("Team Voided");
						}else{
							parsedLeaveDate.add("Present");
						}
					}else{
						parsedLeaveDate.add(null);
					}
				}
				int diffYear = end.get(Calendar.YEAR) - start.get(Calendar.YEAR);
				int diffMonth = diffYear * 12 + end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
				int diffDay = diffYear * 12 * 365 + end.get(Calendar.DAY_OF_YEAR) - start.get(Calendar.DAY_OF_YEAR);
				
				String duration = diffYear + " years, " + diffMonth + " months, " +diffDay + " days";
				map.put("duration", duration);
				//System.out.println(duration);
				
				map.put("gender", teamLead.get(i).getTeamMember().getPerson().getGender());
				
				/*if (teamLead.get(i).getVoided() == false) {
					map.put("parsedLeaveDate", "Present");
				} else {*/
					map.put("parsedLeaveDate", parsedLeaveDate);
				//}
				// System.out.println(name);
				map.put("name", name);
				// map.put("dateCreated", dateCreated);
				map.put("parsedJoinDate", parsedJoinDate);	
				teamLeadList.add(map);
				// System.out.println(teamLeadList);
			}
		}

		// Original code for one lead

		/*
		 * TeamLead lead =
		 * Context.getService(TeamLeadService.class).getTeamLead(team); if (lead
		 * != null) { TeamMember tm = lead.getTeamMember(); String gName =
		 * tm.getPerson().getGivenName(); String mName =
		 * tm.getPerson().getMiddleName(); String fName =
		 * tm.getPerson().getFamilyName();
		 * 
		 * if (gName != null) { name = name + " " + gName; } if (mName != null)
		 * { name = name + " " + mName; } if (fName != null) { name = name + " "
		 * + fName; } } dateCreated = lead.getDateCreated(); // create variable
		 * here // Date dateVoided = lead.getDateVoided(); leaveDate =
		 * lead.getLeaveDate(); // create variable here if (leaveDate == null) {
		 * String present = "Present"; model.addAttribute("dateVoided",
		 * present); } else { model.addAttribute("leaveDate", leaveDate); }
		 * 
		 * model.addAttribute("lead", lead);
		 */

		// model.addAttribute("dateCreated", dateCreated);
		// model.addAttribute("name", name);
		//System.out.println(teamLeadList);
		model.addAttribute("teamLeadList", teamLeadList);
		model.addAttribute("team", team);
		return SUCCESS_FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, BindingResult errors) {

		if (errors.hasErrors()) {
			// return error view
		}

		return SUCCESS_FORM_VIEW;
	}

}
