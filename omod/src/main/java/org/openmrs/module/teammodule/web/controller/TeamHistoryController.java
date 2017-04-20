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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import java.util.List;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamMemberService;
import org.openmrs.module.teammodule.api.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
	private final String SUCCESS_FORM_VIEW = "/module/teammodule/team.form";

	/**
	 * Initially called after the formBackingObject method to get the landing
	 * form name
	 * 
	 * @return String form view name
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> showForm(HttpServletRequest request) {
		List<Map<String, Object>> teamSupervisorList = new ArrayList<Map<String, Object>>();
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
		// Context.getAdministrationService().executeSQL("select team_member_id from team_Supervisor where team_id = "
		// + teamId, false);

		// Multiple Supervisors

		List<TeamMember> teamSupervisor = Context.getService(TeamMemberService.class).getTeamMemberByTeam(team, null, null, null);
		// System.out.println(teamSupervisor);
		// Date dateCreated = null;
		// Date leaveDate = null;

		if (teamSupervisor != null) {

			for (int i = 0; i < teamSupervisor.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				String name = "";
				TeamMember tm = teamSupervisor.get(i);
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
				
				@SuppressWarnings("unused")
				Period period;
				
				// dateCreated = teamSupervisor.get(i).getDateCreated();
				if (teamSupervisor.get(i).getJoinDate() != null) {
					String joinDate = sdf.format(teamSupervisor.get(i).getJoinDate());
					parsedJoinDate.add(joinDate);
					start.setTime(teamSupervisor.get(i).getJoinDate());
				}else{
					parsedJoinDate.add(null);
				}
				// Date dateVoided = teamSupervisor.getDateVoided();
				if (teamSupervisor.get(i).getLeaveDate() != null) {
					String leaveDate = sdf.format(teamSupervisor.get(i).getLeaveDate());
					parsedLeaveDate.add(leaveDate);
					end.setTime(teamSupervisor.get(i).getLeaveDate());
				}else{
					if(teamSupervisor.get(i).getVoided() == false){
						if(team.getVoided()){
							parsedLeaveDate.add("Team Voided");
						}else{
							parsedLeaveDate.add("Present");
						}
					}else{
						parsedLeaveDate.add(null);
					}
				}
				 LocalDate dateStart = new LocalDate(start.get(Calendar.YEAR)+1, start.get(Calendar.MONTH)+1, end.get(Calendar.DAY_OF_MONTH)+1);
		         LocalDate dateEnd= new LocalDate(end.get(Calendar.YEAR)+1, end.get(Calendar.MONTH)+1,start.get(Calendar.DAY_OF_MONTH)+1);
		    	 Days day = Days.daysBetween(dateStart, dateEnd);
		    	 String duration="Days: "+day.getDays();	
		    	 map.put("duration", duration);
				 map.put("gender", teamSupervisor.get(i).getPerson().getGender());
				
				/*if (teamSupervisor.get(i).getVoided() == false) {
					map.put("parsedLeaveDate", "Present");
				} else {*/
					map.put("parsedLeaveDate", parsedLeaveDate);
				//}
				// System.out.println(name);
				map.put("name", name);
				// map.put("dateCreated", dateCreated);
				map.put("parsedJoinDate", parsedJoinDate);	
				teamSupervisorList.add(map);
				// System.out.println(teamSupervisorList);
			}
		}

		// Original code for one Supervisor

		/*
		 * TeamSupervisor Supervisor =
		 * Context.getService(TeamSupervisorService.class).getTeamSupervisor(team); if (Supervisor
		 * != null) { TeamMember tm = Supervisor.getTeamMember(); String gName =
		 * tm.getPerson().getGivenName(); String mName =
		 * tm.getPerson().getMiddleName(); String fName =
		 * tm.getPerson().getFamilyName();
		 * 
		 * if (gName != null) { name = name + " " + gName; } if (mName != null)
		 * { name = name + " " + mName; } if (fName != null) { name = name + " "
		 * + fName; } } dateCreated = Supervisor.getDateCreated(); // create variable
		 * here // Date dateVoided = Supervisor.getDateVoided(); leaveDate =
		 * Supervisor.getLeaveDate(); // create variable here if (leaveDate == null) {
		 * String present = "Present"; model.addAttribute("dateVoided",
		 * present); } else { model.addAttribute("leaveDate", leaveDate); }
		 * 
		 * model.addAttribute("Supervisor", Supervisor);
		 */

		// model.addAttribute("dateCreated", dateCreated);
		// model.addAttribute("name", name);
		//System.out.println(teamSupervisorList);
		return teamSupervisorList;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, BindingResult errors) {

		if (errors.hasErrors()) {
			// return error view
		}

		return SUCCESS_FORM_VIEW;
	}

}
