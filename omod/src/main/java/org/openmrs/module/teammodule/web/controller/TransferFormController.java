/**
 * 
 */
package org.openmrs.module.teammodule.web.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.Person;
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
@RequestMapping(value = "module/teammodule/transferForm")
public class TransferFormController {

	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());

	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/teammodule/transferForm";

	/**
	 * Initially called after the formBackingObject method to get the landing
	 * form name
	 * 
	 * @return String form view name
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Model model, HttpServletRequest request) {
		// ** must be added too
		TeamMember transfer = new TeamMember();
		List<Team> teams = Context.getService(TeamService.class).getAllTeams(true);
		String teamId = request.getParameter("teamId");
		String error = request.getParameter("errorMessage");
		Team team = Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId));
		// System.out.println(team);
		TeamLead teamLead = Context.getService(TeamLeadService.class).getTeamLead(team);
		
		String memberId = request.getParameter("memberId");
		TeamMember teamMember = Context.getService(TeamMemberService.class).getMember(Integer.parseInt(memberId));
		Boolean isTeamLead = teamMember.getIsTeamLead();
		if (teamLead != null && teamLead.getTeamMember().getTeamMemberId() == Integer.parseInt(memberId)) {
			// System.out.println("inside");
			teamLead.setLeaveDate(new Date());
			teamLead.setVoided(true);
			teamLead.setDateVoided(new Date());
			teamLead.setVoidReason("Transferred");
			Context.getService(TeamLeadService.class).update(teamLead);
		}
		/*
		 * teamMember.setPerson(Context.getPersonService().getPerson(teamMember.
		 * getPersonId())); teamMember.setTeam(team);
		 */
		teamMember.setIsTeamLead(isTeamLead);
		teamMember.setLeaveDate(new Date());
		teamMember.setVoided(true);
		teamMember.setVoidReason("Transferred");
		teamMember.setDateVoided(new Date());
		Context.getService(TeamMemberService.class).update(teamMember);
		model.addAttribute("teams", teams);
		model.addAttribute("errorMessage", error);

		// ** must be added otherwise won't work
		model.addAttribute("transfer", transfer);
		model.addAttribute("teamId", teamId);
		model.addAttribute("teamMember", teamMember);
		model.addAttribute("location",team.getLocation());
		//teamMember.setTeamId(null);
		return SUCCESS_FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, @ModelAttribute("transfer") TeamMember teamMember,
			 BindingResult errors, HttpServletRequest request, Model model) {
		// String errorMessage;
		// TeamLead teamLead = new TeamLead();
		Person person = Context.getPersonService().getPerson(teamMember.getPersonId());
		teamMember.setIdentifier(teamMember.getIdentifier());

		teamMember.setPerson(person);
		teamMember.setPersonId(teamMember.getPersonId());
		teamMember.setJoinDate(new Date());
		// TeamLead existingLead =
		// Context.getService(TeamLeadService.class).getTeamLead(team);
		if (errors.hasErrors()) {
			// return error view
		}
		//String memberId = request.getParameter("memberId");
		teamMember.setIsTeamLead(false);
		//System.out.println(teamMember.getTeam().getTeamId());
		Team team = Context.getService(TeamService.class).getTeam(teamMember.getTeam().getTeamId());
		//teamMember.setLocation(team.getLocation());
		teamMember.setTeam(team);
		teamMember.setUuid(UUID.randomUUID().toString());
		Context.getService(TeamMemberService.class).save(teamMember);
		
		for(int i = 0; i < teamMember.getLocation().size(); i++){
			Integer locationId = teamMember.getLocation().iterator().next().getLocationId();
			Location location = Context.getLocationService().getLocation(locationId);
			teamMember.getLocation().add(location);		
			Context.getService(TeamMemberService.class).saveLocation(location);
			
		}
		
		return "redirect:/module/teammodule/teamMember/list.form?teamId=" + teamMember.getTeamId();

		/*
		 * if(teamMember.getIsTeamLead().booleanValue() && existingLead==null){
		 * teamLead.setTeam(team); teamLead.setTeamMember(teamMember); if
		 * (teamMember.getJoinDate() == null) { teamLead.setJoinDate(new
		 * Date()); } Context.getService(TeamLeadService.class).save(teamLead);
		 * Context.getService(TeamMemberService.class).save(teamMember);
		 * List<Team> teams =
		 * Context.getService(TeamService.class).getAllTeams(true);
		 * model.addAttribute("teams", teams);
		 * model.addAttribute("errorMessage",null); return
		 * "redirect:/module/teammodule/teamMember.form?teamId=" +
		 * teamMember.getTeamId(); }else{ errorMessage = "Team Lead exists";
		 * model.addAttribute("errorMessage",errorMessage); return
		 * SUCCESS_FORM_VIEW; }
		 */

	}

}
