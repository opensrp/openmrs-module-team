/**
 * 
 */
package org.openmrs.module.teammodule.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamMemberService;
import org.openmrs.module.teammodule.api.TeamService;
import org.openmrs.util.OpenmrsConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Muhammad Safwan
 * 
 *  @author Shakeeb Raza
 */
@Controller
@RequestMapping(value = "/module/teammodule/teamMemberEditForm")
public class TeamMemberEditFormController {
	
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());

	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/teammodule/teamMemberEditForm";

	// Made command Object (memberData)

	@ModelAttribute("memberData")
	public TeamMember populateTeamMember(@RequestParam(value = "teamMemberId", required = true) int teamMemberId) {
		TeamMember memberData = new TeamMember();

		memberData = Context.getService(TeamMemberService.class).getTeamMember(teamMemberId);
		return memberData;
	}

	// Was becoming reference object here

	@RequestMapping(method = RequestMethod.GET)
	public String showFormEidt(Model model, HttpServletRequest request) {

		Integer teamMemberId = null;
		String caption = "";

		String teamMemberIdUI = request.getParameter("teamMemberId");
		if(!(teamMemberIdUI.equals(""))) { teamMemberId = Integer.parseInt(teamMemberIdUI); }
		TeamMember teamMember = Context.getService(TeamMemberService.class).getTeamMember(teamMemberId);

		if (teamMember.getTeam().getSupervisor().getId() == teamMember.getId()) { caption = "Edit Team Lead"; } 
		else { caption = "Edit Member"; }

		System.out.println("\n teamMemberId: " + teamMemberId);
		model.addAttribute("teamMemberId", teamMemberId);

		System.out.println("\n caption: " + caption);
		model.addAttribute("caption", caption);

		String gender = teamMember.getPerson().getGender();
		System.out.println("\n gender: " + gender);
		model.addAttribute("gender", gender);
		
		String error = request.getParameter("error");
		System.out.println("\n error: " + error);
		model.addAttribute("error", error);
		
		System.out.println("\n personId: " + teamMember.getPerson().getId());
		model.addAttribute("personId", teamMember.getPerson().getId());
		
		System.out.println("\n teamDate: " + teamMember.getTeam().getDateCreated());
		model.addAttribute("teamDate", teamMember.getTeam().getDateCreated());

		List<Location> allLocations = Context.getLocationService().getAllLocations();
		model.addAttribute("locationWidgetType", Context.getAdministrationService().saveGlobalProperty(new GlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_LOCATION_WIDGET_TYPE, "default")));
		
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
	public String onSubmitEdit(HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, HttpServletRequest request,
	@ModelAttribute("memberData") TeamMember teamMember, BindingResult errors, Model model,
			@RequestParam(value = "teamMemberId", required = true) int teamMemberId) {

		String error = "";
//		if (errors.hasErrors()) { /* return error view */ }
		
		if (teamMember.getPerson().getGivenName().isEmpty() || teamMember.getPerson().getFamilyName().isEmpty()) {
			error = "Name and Family Name can't be empty";
			model.addAttribute("error", error);
		} else {
			if (teamMember.isVoided()) {
				//teamMember.setIsRetired(true);
				teamMember.setDateVoided(new Date());
			}
			
			if (error.isEmpty()) {
				// System.out.println(error);
				
				/*Context.getService(TeamMemberService.class).update(teamMember);*/
				Context.getService(TeamMemberService.class).updateTeamMember(teamMember);
				
				String edit = "Member edited successfully";
				model.addAttribute("edit", edit);
			}
		}

		// here before
		// Context.getService(TeamLeadService.class).update(teamLead);
		return "redirect:/module/teammodule/teamMember/list.form?teamId="+teamMember.getTeam().getId();

		// return
		// "redirect:/module/teammodule/teamMemberForm/editMember.form?person_id="
		// + personId + "&teamId=" + teamId + "&teamMemberId=" + teamMemberId;

	}
}