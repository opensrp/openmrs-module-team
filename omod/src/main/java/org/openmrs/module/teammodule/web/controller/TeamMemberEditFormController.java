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
//import org.hibernate.SessionFactory;
//import org.openmrs.module.teammodule.Team;
//import org.openmrs.module.teammodule.api.TeamService;

/**
 * @author Muhammad Safwan
 * 
 */
@Controller
@RequestMapping(value = "/module/teammodule/teamMemberEditForm")
public class TeamMemberEditFormController {
	protected final Log log = LogFactory.getLog(getClass());
	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/teammodule/teamMemberEditForm";

	// private final String SUCCESS_REDIRECT_LINK =
	// "redirect:/module/teammodule/teamMemberForm/teamMemberForm.form";

	/**
	 * Initially called after the formBackingObject method to get the landing
	 * form name
	 * 
	 * @return String form view name
	 */
	/*
	 * @RequestMapping(method = RequestMethod.GET, value = "addMember.form")
	 * public String showForm(@ModelAttribute("existingMember") TeamMember
	 * teamMember, Model model, HttpServletRequest request) {
	 * 
	 * TeamMember memberData = new TeamMember(); // String id =
	 * request.getParameter("teamId"); model.addAttribute("memberData",
	 * memberData); // Context.getService(TeamService.class).getTeam(id); String
	 * error = request.getParameter("error"); model.addAttribute("error",
	 * error); String saved = request.getParameter("saved");
	 * model.addAttribute("saved", saved); return SUCCESS_FORM_VIEW;
	 * 
	 * }
	 * 
	 * private Boolean isNewUser(User user) { return user == null ? true :
	 * user.getUserId() == null; }
	 * 
	 * /** All the parameters are optional based on the necessity
	 * 
	 * @param httpSession
	 * 
	 * @param anyRequestObject
	 * 
	 * @param errors
	 * 
	 * @return
	 */

	/*
	 * @RequestMapping(method = RequestMethod.POST, value = "addMember.form")
	 * public String onSubmit(HttpSession httpSession,
	 * 
	 * @ModelAttribute("anyRequestObject") Object anyRequestObject,
	 * BindingResult errors, HttpServletRequest request, /* @RequestParam(value
	 * = "teamParam", required = false) String teamId,
	 * 
	 * @ModelAttribute("memberData") TeamMember teamMember,
	 * 
	 * @RequestParam(required = false, value = "userId") Integer userId,
	 * 
	 * @RequestParam(required = false, value = "createNewPerson") String
	 * createNewPerson, @ModelAttribute("user") User user, Model model) {
	 * 
	 * if (errors.hasErrors()) { // return error view }
	 * 
	 * model.addAttribute("isNewUser", isNewUser(user)); if (isNewUser(user) ||
	 * Context.hasPrivilege(PrivilegeConstants.EDIT_USER_PASSWORDS)) {
	 * model.addAttribute("modifyPasswords", true); }
	 * 
	 * if (createNewPerson != null) { model.addAttribute("createNewPerson",
	 * createNewPerson); }
	 * 
	 * if (!isNewUser(user)) { model.addAttribute("changePassword", new
	 * UserProperties(user.getUserProperties()).isSupposedToChangePassword()); }
	 * 
	 * // Context.getService(TeamService.class).saveTeam(team); //
	 * request.getParameter(teamId); // Team team = //
	 * Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId));
	 * String tId = request.getParameter("teamId"); String pId =
	 * request.getParameter("person_id"); String error = ""; //
	 * System.out.println("personId:" + pId); Person person = new Person();
	 * TeamLead teamLead = new TeamLead();
	 * 
	 * if (1 == 2/* teamMember.getMemberName().isEmpty() ||
	 * teamMember.getMemberFamilyName().isEmpty() ) { error =
	 * "Name and Family Name can't be empty"; model.addAttribute("error",
	 * error); } else { PersonName pName = null;/* new
	 * PersonName(teamMember.getMemberName(), null,
	 * teamMember.getMemberFamilyName());
	 * 
	 * if (pId.isEmpty()) { // person.setGender(teamMember.getGender());
	 * person.addName(pName); person =
	 * Context.getPersonService().savePerson(person); } else { person =
	 * Context.getPersonService().getPerson(Integer.parseInt(pId)); } Team team
	 * = Context.getService(TeamService.class).getTeam(Integer.parseInt(tId));
	 * // Location location = // Context.getLocationService().getLocation(arg0);
	 * teamMember.setTeam(team); teamMember.setPerson(person);
	 * 
	 * if (teamMember.getJoinDate() == null) { teamMember.setJoinDate(new
	 * Date()); }
	 * 
	 * Context.getService(TeamMemberService.class).save(teamMember);
	 * 
	 * if (teamMember.getIsTeamLead().booleanValue()) { TeamLead tl =
	 * Context.getService(TeamLeadService.class).getTeamLead(team); if (tl ==
	 * null) { teamLead.setTeam(team); teamLead.setTeamMember(teamMember); if
	 * (teamMember.getJoinDate() == null) { teamLead.setJoinDate(new Date()); }
	 * Context.getService(TeamLeadService.class).save(teamLead); } else { error
	 * = "Team Lead for this team already exists. "; model.addAttribute("error",
	 * error); } } model.addAttribute("teamId", tId); if (error.isEmpty()) {
	 * String saved = "Member saved successfully"; model.addAttribute("saved",
	 * saved); } } return
	 * "redirect:/module/teammodule/teamMemberForm/addMember.form?teamId=" +
	 * tId; }
	 */

	// Made command Object (memberData)

	@ModelAttribute("memberData")
	public TeamMember populateTeamMember(@RequestParam(value = "teamMemberId", required = true) int teamMemberId) {
		TeamMember memberData = new TeamMember();

		memberData = Context.getService(TeamMemberService.class).getMember(teamMemberId);
		return memberData;
	}

	// Was becoming reference object here

	@RequestMapping(method = RequestMethod.GET)
	public String showFormEidt(Model model, HttpServletRequest request, @RequestParam(value = "teamId", required = true) int teamId,

	@RequestParam(value = "teamMemberId", required = true) int teamMemberId, @RequestParam(value = "person_id", required = true) int personId) {

		String error = request.getParameter("error");
		model.addAttribute("error", error);
		String edit = request.getParameter("edit");
		model.addAttribute("edit", edit);
		//String teamId = request.getParameter("teamId");
		model.addAttribute("teamId", teamId);
		model.addAttribute("teamMemberId", teamMemberId);
		model.addAttribute("personId",personId);
		
		Team team = Context.getService(TeamService.class).getTeam(teamId);
		Date teamDate = team.getDateCreated();
		model.addAttribute("teamDate", teamDate);

		TeamMember teamMember = Context.getService(TeamMemberService.class).getMember(teamMemberId);
		List<Location> allLocations = Context.getLocationService().getAllLocations();
		String caption = "";
		if (teamMember.getIsTeamLead() == true) {
			caption = "Edit Team Lead";
			model.addAttribute("caption", caption);
		} else {
			caption = "Edit Member";
			model.addAttribute("caption", caption);
		}
		String gender = teamMember.getPerson().getGender();
		System.out.println(gender);
		model.addAttribute("gender", gender);
		Location location = team.getLocation();
		model.addAttribute("location", location);
		model.addAttribute("allLocations",allLocations);
		 //model.addAttribute("json", getHierarchyAsJson());
		 model.addAttribute("locationWidgetType",
		 Context.getAdministrationService().saveGlobalProperty( 
				new GlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_LOCATION_WIDGET_TYPE, "default")));
		//model.addAttribute("personId", personId);

		/*
		 * TeamMember memberData = new TeamMember();
		 * 
		 * Person person = Context.getPersonService().getPerson(personId);
		 * PersonName pName =
		 * Context.getPersonService().getPersonName(personId);
		 * memberData.setMemberName(pName.toString());
		 * System.out.println(memberData.getMemberName());
		 * //System.out.println(pName);
		 * 
		 * 
		 * memberData =
		 * Context.getService(TeamMemberService.class).getMember(teamMemberId);
		 * // memberData.setPerson(person); model.addAttribute("memberData",
		 * memberData);
		 */

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
	/* @RequestParam(value = "teamId", required = true) int teamId, */@ModelAttribute("memberData") TeamMember teamMember, BindingResult errors, Model model,
			@RequestParam(value = "teamMemberId", required = true) int teamMemberId/*
																					 * ,
																					 * 
																					 * @
																					 * RequestParam
																					 * (
																					 * value
																					 * =
																					 * "person_id"
																					 * ,
																					 * required
																					 * =
																					 * true
																					 * )
																					 * String
																					 * personId
																					 */) {

		// String teamId = request.getParameter("teamId");
		// teamId = teamId.replace(",", "");
		// personId = personId.replace(",", "");
		String error = "";
		// Team team =
		// Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId));
		// TeamLead teamLead;
		// Person person =
		// Context.getPersonService().getPerson(Integer.parseInt(personId));

		/*
		 * if (errors.hasErrors()) { // return error view }
		 */
		if (teamMember.getPerson().getGivenName().isEmpty() || teamMember.getPerson().getFamilyName().isEmpty()) {
			error = "Name and Family Name can't be empty";
			model.addAttribute("error", error);
		} else {

			/*
			 * teamMember.setJoinDate(teamMember.getJoinDate());
			 * teamMember.setGender(teamMember.getGender());
			 * teamMember.setLeaveDate(teamMember.getLeaveDate());
			 * teamMember.setIdentifier(teamMember.getIdentifier());
			 */
			// teamMember.setTeam(team);
			// teamMember.setPerson(person);
			// Context.getService(TeamMemberService.class).update(teamMember);
			// this if else can be changed into single if by checking just false
			// but it's kept in case something needs to be done later
			/*
			 * if (teamMember.getIsTeamLead().booleanValue()) { TeamLead tl =
			 * Context.getService(TeamLeadService.class).getTeamLead(team); if
			 * (tl == null) { teamLead = new TeamLead();
			 * teamMember.setIsTeamLead(true); teamLead.setTeam(team);
			 * teamLead.setPersonId(Integer.parseInt(personId));
			 * teamLead.setTeamMember(teamMember); if (teamMember.getJoinDate()
			 * == null) { teamLead.setJoinDate(new Date()); } else {
			 * teamLead.setJoinDate(teamMember.getJoinDate()); }
			 * Context.getService(TeamLeadService.class).save(teamLead); } else
			 * { if (tl.getTeamMember().getTeamMemberId() ==
			 * teamMember.getTeamMemberId()) { if (teamMember.getLeaveDate() !=
			 * null) { tl.setLeaveDate(teamMember.getLeaveDate());
			 * tl.setVoided(true); tl.setVoidReason("Left"); } } else { error =
			 * "Team Lead for this team already exists. ";
			 * model.addAttribute("error", error); } }
			 * 
			 * } else { teamLead =
			 * Context.getService(TeamLeadService.class).getTeamLead(team); if
			 * (teamLead != null && teamLead.getTeamMember().getTeamMemberId()
			 * == teamMember.getTeamMemberId()) { teamLead.setVoided(true);
			 * teamLead.setDateVoided(new Date());
			 * teamMember.setIsTeamLead(false);
			 * teamLead.setJoinDate(teamLead.getJoinDate()); if
			 * (teamMember.getLeaveDate() == null) { teamLead.setLeaveDate(new
			 * Date()); }
			 * Context.getService(TeamLeadService.class).update(teamLead); }
			 * 
			 * //
			 * Context.getService(TeamMemberService.class).update(teamMember);
			 * 
			 * }
			 */
			if (teamMember.isVoided()) {
				// teamMember.setIsRetired(true);
				teamMember.setDateVoided(new Date());

			}
			
			if (error.isEmpty()) {
				// System.out.println(error);
				Context.getService(TeamMemberService.class).update(teamMember);
				String edit = "Member edited successfully";
				model.addAttribute("edit", edit);
			}
		}

		// here before
		// Context.getService(TeamLeadService.class).update(teamLead);
		return "redirect:/module/teammodule/teamMember/list.form?teamId="+teamMember.getTeamId();

		// return
		// "redirect:/module/teammodule/teamMemberForm/editMember.form?person_id="
		// + personId + "&teamId=" + teamId + "&teamMemberId=" + teamMemberId;

	}

	/*
	 * @RequestMapping(method = RequestMethod.GET, value = "teamMemberForm")
	 * public String showTeamForm(Model model, HttpServletRequest request) {
	 * 
	 * // Team teamData = new Team(); TeamMember teamMember = new TeamMember();
	 * // model.addAttribute("teamData", teamData);
	 * model.addAttribute("memberData", teamMember); return SUCCESS_FORM_VIEW; }
	 *//**
	 * All the parameters are optional based on the necessity
	 * 
	 * @param httpSession
	 * @param anyRequestObject
	 * @param errors
	 * @return
	 */
	/*
	 * @RequestMapping(method = RequestMethod.POST, value = "teamMemberForm")
	 * public String onSubmitTeamForm(HttpSession httpSession,
	 * @ModelAttribute("anyRequestObject") Object anyRequestObject,
	 * BindingResult errors, HttpServletRequest request,
	 * 
	 * @RequestParam(value = "teamIdentifier", required = false) Integer
	 * identifier,
	 * 
	 * @ModelAttribute("teamData") Team team,
	 * 
	 * @RequestParam(value = "teamId", required = true) Team teamId,
	 * @ModelAttribute("memberData") TeamMember teamMember, Model model) {
	 * 
	 * if (errors.hasErrors()) { // return error view }
	 * 
	 * Context.getService(TeamMemberService.class).save(teamMember);
	 * 
	 * return SUCCESS_REDIRECT_LINK; }
	 */
}
