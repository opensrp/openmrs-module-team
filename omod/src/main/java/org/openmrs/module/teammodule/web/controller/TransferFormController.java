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
		TeamMember transfer = new TeamMember();
		List<Team> teams = Context.getService(TeamService.class).getAllTeams(true);

		String teamId = request.getParameter("teamId");
		String memberId = request.getParameter("memberId");
		model.addAttribute("teamId", teamId);
		model.addAttribute("memberId", memberId);
		
		model.addAttribute("teams", teams);

		// ** must be added otherwise won't work
		model.addAttribute("transfer", transfer);
		return SUCCESS_FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, 
			 BindingResult errors, HttpServletRequest request, Model model) {

		String teamId = request.getParameter("teamId");
		String memberId = request.getParameter("memberId");
		
		Team team = Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId));
		TeamLead teamLead = Context.getService(TeamLeadService.class).getTeamLead(team);
	
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

		teamMember.setIsTeamLead(isTeamLead);
		teamMember.setLeaveDate(new Date());
		teamMember.setVoided(true);
		teamMember.setVoidReason("Transferred");
		teamMember.setDateVoided(new Date());
		Context.getService(TeamMemberService.class).update(teamMember);
		
		// creating new record
		TeamMember transfer = new TeamMember();

		Team transferTeam = Context.getService(TeamService.class).getTeam(request.getParameter("transferredTeam"));
		Location transferLocation = Context.getLocationService().getLocation(Integer.parseInt(request.getParameter("transferredLocation")));
		transfer.setIdentifier(teamMember.getIdentifier());

		transfer.setPerson(teamMember.getPerson());
		transfer.setPersonId(teamMember.getPersonId());
		transfer.setJoinDate(new Date());
		// TeamLead existingLead =
		// Context.getService(TeamLeadService.class).getTeamLead(team);
		if (errors.hasErrors()) {
			// return error view
			model.addAttribute("teamId", teamId);
			model.addAttribute("memberId", memberId);
		}
		//String memberId = request.getParameter("memberId");
		transfer.setIsTeamLead(false);
		transfer.setTeam(transferTeam);
		transfer.getLocation().add(transferLocation);
		transfer.setUuid(UUID.randomUUID().toString());
		
		Context.getService(TeamMemberService.class).save(transfer);
		
		return "redirect:/module/teammodule/teamMember/list.form?teamId=" + transferTeam.getTeamId();

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
