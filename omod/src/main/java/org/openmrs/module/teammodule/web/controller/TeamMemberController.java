/**
 * 
 */
package org.openmrs.module.teammodule.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
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
//import java.util.Date;
//import org.openmrs.PersonName;
//import org.openmrs.module.teammodule.Team;
//import org.openmrs.module.teammodule.TeamSupervisor;
//import org.openmrs.module.teammodule.api.TeamSupervisorService;
//import org.openmrs.module.teammodule.api.TeamService;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Muhammad Safwan
 * 
 * @author Shakeeb Raza
 * 
 */
@Controller
@RequestMapping(value = "module/teammodule/teamMember/")
public class TeamMemberController {
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());

	/** Success form views name */
	private final String SHOW = "/module/teammodule/teamMember";

	/**
	 * Initially called after the formBackingObject method to get the landing
	 * form name
	 * 
	 * @return String form view name
	 */
	@RequestMapping(method = RequestMethod.GET, value = "list.form")
	public String showForm(Model model, HttpServletRequest request) {
		List<TeamMember> teamMember;
		// Person person;
		// List<Person> personList = new ArrayList<Person>();
		String teamId = request.getParameter("teamId");
		Team team = Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId));
		String memberName = request.getParameter("member");
		String changeSupervisor = request.getParameter("changeLead");
		System.out.println(memberName);
		String caption = "";
		// int teamIden = Integer.parseInt(teamId);
		List<String> genderList = new ArrayList<String>();
		List<String> joinDate = new ArrayList<String>();
		TeamMember teamSupervisor = Context.getService(TeamMemberService.class).getTeamMember(team.getSupervisor().getId());

		if (Context.isAuthenticated()) {
			if (memberName == null) {
				teamMember = Context.getService(TeamMemberService.class).getTeamMemberByTeam(team, null, null, null);
			} else {
				teamMember = Context.getService(TeamMemberService.class).searchTeamMemberByTeam(memberName, Integer.parseInt(teamId));
			}
			for (int i = 0; i < teamMember.size(); i++) {
				/*
				 * person =
				 * Context.getPersonService().getPerson(teamMember.get(i
				 * ).getPersonId()); personList.add(person);
				 */
				if (teamMember.get(i).getJoinDate() != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String date = sdf.format(teamMember.get(i).getJoinDate());
					joinDate.add(date);
				} else {
					joinDate.add("");
				}

				/*
				 * date = teamMember.get(i).getLeaveDate(); leaveDate.add(date);
				 */
			}
			if(changeSupervisor == null){
				caption = team.getTeamName() + " Members";
				model.addAttribute("edit", caption);			
			}else{
				caption = "Change Team Lead";
				model.addAttribute("edit",caption);
			}
			model.addAttribute("teamMember", teamMember);
			// model.addAttribute("name", personList);
			model.addAttribute("team", team);
			// model.addAttribute("teamIden", teamIden);
			model.addAttribute("gender", genderList);
			model.addAttribute("join", joinDate);
			model.addAttribute("teamLead", teamSupervisor);
			model.addAttribute("member", memberName);
			// model.addAttribute("leave", leaveDate);
		}

		return SHOW;
	}

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "listPopup.form")
	@ResponseBody
	public ArrayList showFormPopup(Model model, HttpServletRequest request) throws JSONException {
		List<TeamMember> teamMember,teamMemberTemp;
		// Person person;
		// List<Person> personList = new ArrayList<Person>();
		String teamId = request.getParameter("teamId");
		Team team = Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId));
		String memberName = request.getParameter("member");
		String changeSupervisor = request.getParameter("changeLead");
		System.out.println(memberName);
		String caption = "";
		// int teamIden = Integer.parseInt(teamId);
		List<String> genderList = new ArrayList<String>();
		List<String> joinDate = new ArrayList<String>();
		TeamMember teamSupervisor = Context.getService(TeamMemberService.class).getTeamMember(team.getSupervisor().getId());
		ArrayList arr = new ArrayList();
		Map<String, String>m=new HashedMap();
		
		if (Context.isAuthenticated()) {
			if (memberName == null) {
				teamMember = Context.getService(TeamMemberService.class).getTeamMemberByTeam(team, null, null, null);
				teamMemberTemp = Context.getService(TeamMemberService.class).getTeamMemberByTeamWithPage(team, null, null, null, 100);
				} else {
				teamMember = Context.getService(TeamMemberService.class).searchTeamMemberByTeam(memberName, Integer.parseInt(teamId));
			}
			for (int i = 0; i < teamMember.size(); i++) {
				if (teamMember.get(i).getJoinDate() != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String date = sdf.format(teamMember.get(i).getJoinDate());
					joinDate.add(date);
				} else {
					joinDate.add("");
				}
			}
			if(changeSupervisor == null){
				caption = team.getTeamName() + " Members";
				m.put("edit", caption);			
			}else{
				caption = "Change Team Lead";
				m.put("edit",caption);
			}
			
			for(int i=0;i<teamMember.size();i++)
			{	m=new HashedMap();
				m.put("teamMemberId", String.valueOf(teamMember.get(i).getTeamMemberId()));
				m.put("personName", teamMember.get(i).getPerson().getGivenName() + teamMember.get(i).getPerson().getFamilyName());
				m.put("join", joinDate.get(i));
				m.put("gender", teamMember.get(i).getPerson().getGender());
				m.put("teamId",teamId);
				arr.add(m);
			}
		}
		
		return arr;
	}
	/**
	 * All the parameters are optional based on the necessity
	 * 
	 * @param httpSession
	 * @param anyRequestObject
	 * @param errors
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "list.form")
	public String onSubmit(HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, BindingResult errors,HttpServletRequest request,@ModelAttribute("searchMember") TeamMember searchMember) {

		String teamId = request.getParameter("teamId");
		String member = request.getParameter("memberName");
		System.out.println(member);
		if (errors.hasErrors()) {
			// return error view
		}

		return "redirect:/module/teammodule/teamMember/list.form?teamId="+teamId+"&member="+member;
	}

	@RequestMapping(method = RequestMethod.GET, value = "changeTeamLead.form")
	public String showFormEdit(Model model, HttpServletRequest request) {
		String teamMemberId = request.getParameter("teamMemberId");
		String teamId = request.getParameter("teamId");
		String teamSupervisorId = request.getParameter("teamLeadId");

		List<String> joinDate = new ArrayList<String>();
		// Person person;
		// List<Person> personList = new ArrayList<Person>();
		TeamMember tm = null;
		Team team = Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId));
		TeamMember teamSupervisor = Context.getService(TeamMemberService.class).getTeamMember(team.getSupervisor().getId());
		//TeamMember tm = Context.getService(TeamMemberService.class).getMember(Integer.parseInt(teamMemberId));
		if (teamSupervisor != null) {
			teamSupervisor.setVoided(true);
			teamSupervisor.setVoidReason("Team Lead Changed");
			teamSupervisor.setLeaveDate(new Date());
			teamSupervisor.setDateVoided(new Date());
			Context.getService(TeamMemberService.class).update(teamSupervisor);
			tm = Context.getService(TeamMemberService.class).getTeamMember(Integer.parseInt(teamSupervisorId));
			tm.setIsTeamLead(false);
			Context.getService(TeamMemberService.class).update(tm);
		}
		tm = Context.getService(TeamMemberService.class).getTeamMember(Integer.parseInt(teamMemberId));
		teamSupervisor = new TeamMember();
		teamSupervisor.setTeam(team);
		teamSupervisor.setJoinDate(new Date());
		//teamSupervisor.setTeamMember(tm);
		if(teamSupervisor.getUuid() == null){
			teamSupervisor.setUuid(UUID.randomUUID().toString());
		}	
		Context.getService(TeamMemberService.class).save(teamSupervisor);
		teamSupervisor = Context.getService(TeamMemberService.class).getTeamMember(team.getSupervisor().getId());	
		tm.setIsTeamLead(true);
		Context.getService(TeamMemberService.class).update(tm);
		List<TeamMember> teamMember = Context.getService(TeamMemberService.class).getTeamMemberByTeam(team, null, null, false);

		for (int i = 0; i < teamMember.size(); i++) {
			// person =
			// Context.getPersonService().getPerson(teamMember.get(i).getPersonId());
			// personList.add(person);
			if (teamMember.get(i).getJoinDate() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String date = sdf.format(teamMember.get(i).getJoinDate());
				joinDate.add(date);
			} else {
				joinDate.add("");
			}
		}
		model.addAttribute("teamMember", teamMember);
		// model.addAttribute("name", personList);
		model.addAttribute("join", joinDate);
		model.addAttribute("teamLead", teamSupervisor);
		model.addAttribute("team", team);
		return SHOW;
	}

	@RequestMapping(method = RequestMethod.POST, value = "changeTeamLead.form")
	public String onSubmitEdit(HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, BindingResult errors) {

		if (errors.hasErrors()) {
			// return error view
		}
		return SHOW;
	}

}