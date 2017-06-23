/**
 * 
 */
package org.openmrs.module.teammodule.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.Person;
import org.openmrs.PersonName;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamRoleService;
import org.openmrs.module.teammodule.api.TeamService;
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
 */

@Controller
@RequestMapping(value = "/module/teammodule/teamMemberAddForm")
public class TeamMemberAddForm {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/teammodule/teamMemberAddForm";

	@ModelAttribute("memberData")
	public TeamMember populateTeamMember() {
		TeamMember memberData = new TeamMember();
		Person person = new Person();
		person.setPersonDateCreated(new Date());
		memberData.setPerson(person);
		memberData.getPerson().addName(new PersonName());

		return memberData;
	}

	/**
	 * Initially called after the formBackingObject method to get the landing
	 * form name
	 * 
	 * @return String form view name
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showForm( @ModelAttribute("existingMember") TeamMember teamMember, Model model, HttpServletRequest request) throws IOException {
		String error = request.getParameter("error"); model.addAttribute("error", error);
		String saved = request.getParameter("saved"); model.addAttribute("saved", saved);
		model.addAttribute("allRoles",Context.getUserService().getAllRoles());
		List<Location> allLocations = Context.getLocationService().getAllLocations(); 
		model.addAttribute("allLocations",allLocations);
		model.addAttribute("location", allLocations.get(0));
		model.addAttribute("selectedLocation",allLocations.get(0));
		model.addAttribute("allTeamRole",Context.getService(TeamRoleService.class).getAllTeamRole(true, false, null, null));
		model.addAttribute("allTeams",Context.getService(TeamService.class).getAllTeams(false, null, null));
		model.addAttribute("teamId",1);
		//model.addAttribute("json", getRoleAsJson());
		
		//System.out.println("\n locationWidgetType: " + Context.getAdministrationService().saveGlobalProperty(new GlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_LOCATION_WIDGET_TYPE, "default")) + "\n");
		//model.addAttribute("locationWidgetType", Context.getAdministrationService().saveGlobalProperty(new GlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_LOCATION_WIDGET_TYPE, "default")));

		return SUCCESS_FORM_VIEW;

	}
	
	@RequestMapping(value="/getUserName", method = RequestMethod.GET)
	public User checkUserName(Model model, HttpServletRequest request) throws IOException {

		String userName = request.getParameter("userName");
		 //model.addAttribute("json", getRoleAsJson());
		return Context.getUserService().getUserByUsername(userName);
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
	public String onSubmit(HttpSession httpSession, Model model, @ModelAttribute("anyRequestObject") Object anyRequestObject, HttpServletRequest request
			, BindingResult errors
			, @ModelAttribute("memberData") TeamMember teamMember
			, @RequestParam(required = false, value = "userName") String username
			, @RequestParam(required = false, value = "password") String password
			, @RequestParam(required = false, value = "roles") List<Role> roles
			, @RequestParam(required = false, value = "userId") Integer userId
			, @RequestParam(required = false, value = "existingPerson") String existingPerson
			) {
		
		System.out.println("errors: " + errors);
		System.out.println("teamMember: " + teamMember);
		System.out.println("username: " + username);
		System.out.println("password: " + password);
		System.out.println("roles: " + roles);
		System.out.println("userId: " + userId);
		System.out.println("existingPerson: " + existingPerson);
		
		
		
		
//		if (errors.hasErrors()) {
//			System.out.println("errors: " + errors);
//			// return error view
//		}
//		
//		String tId = request.getParameter("teamId");
//		String pId = request.getParameter("person_id");
//		
//		String error = "";
//
//		TeamMember teamSupervisor = new TeamMember();
//		Person person = null;
//		if (pId == "" || pId == null) {
//			 person = Context.getPersonService().savePerson(teamMember.getPerson());
//			teamMember.setPerson(person);
//		} else {
//			 person = Context.getPersonService().getPerson(Integer.parseInt(pId));
//			teamMember.setPerson(person);
//		}
//		
//		Team team = Context.getService(TeamService.class).getTeam(Integer.parseInt(tId));
//		teamMember.setTeam(team);
//		//teamMember.setLocation(teamMember.getLocation());
//
//		if (teamMember.getJoinDate() == null) {
//			teamMember.setJoinDate(new Date());
//		} else {
//
//		}
//		if (teamMember.getPerson().getDateCreated() == null) {
//			teamMember.getPerson().setDateCreated(new Date());
//		}
//		if (error.isEmpty()) {
//
//			if (teamMember.getId() == teamMember.getTeam().getSupervisor().getId()) {
//			
//				TeamMember tl = Context.getService(TeamMemberService.class).getTeamMember(team.getSupervisor().getId());
//				if (tl == null) {
//					
//					/*Context.getService(TeamMemberService.class).save(teamMember);*/
//					Context.getService(TeamMemberService.class).saveTeamMember(teamMember);
//					
//					teamSupervisor.setTeam(team);
//					if (teamMember.getJoinDate() == null) {
//						teamSupervisor.setJoinDate(new Date());
//					} else {
//						teamSupervisor.setJoinDate(teamMember.getJoinDate());
//					}
//					teamSupervisor.setUuid(UUID.randomUUID().toString());
//					
//					/*Context.getService(TeamMemberService.class).save(teamSupervisor);*/
//					Context.getService(TeamMemberService.class).saveTeamMember(teamSupervisor);
//							
//				} else {
//					error = "Team Supervisor for this team already exists. ";
//					model.addAttribute("error", error);
//				}
//			
//			}	
//		}
//	
//		model.addAttribute("teamId", tId);
//		User user=null;
//		if (error.isEmpty()) {
//			user=new User(person);
//			user.setUsername(username);
//			user.setUuid(UUID.randomUUID().toString());
//			if(!password.isEmpty() && !username.isEmpty())
//			Context.getUserService().createUser(user, password);
//			teamMember.setUuid(UUID.randomUUID().toString());
//			
//			/*Context.getService(TeamMemberService.class).save(teamMember);*/
//			Context.getService(TeamMemberService.class).saveTeamMember(teamMember);
//			
//			String saved = "Member saved successfully";
//			Provider provider = new Provider();
//			provider.setPerson(person);
//			provider.setName(person.getGivenName() + " " + person.getFamilyName());
//			provider.setIdentifier(teamMember.getIdentifier());
//			Context.getProviderService().saveProvider(provider);
//			model.addAttribute("saved", saved);
//		}
//		
//		for(int i = 0; i < teamMember.getLocation().size(); i++){
//			Integer locationId = teamMember.getLocation().iterator().next().getLocationId();
//			
//			//System.out.println(locationId);
//			Location location = Context.getLocationService().getLocation(locationId);
//			
//			
//			System.out.println("location: " + location);
//
//			
//			if (location.getDateCreated() == null) {
//				location.setDateCreated(new Date());
//			}			
//			teamMember.getLocation().add(location);
//			
//			/*Context.getService(TeamMemberService.class).saveLocation(location);*/
//			Context.getLocationService().saveLocation(location);
//			
//		}
//		
//		for(int i = 0; i < user.getAllRoles().size(); i++){
//			String role = user.getRoles().iterator().next().getRole();
//			Context.getService(UserService.class).saveRole(new Role(role));
//		}
//		return "redirect:/module/teammodule/teamMemberAddForm.form?teamId=" + tId;
		return "redirect:/module/teammodule/teamMemberAddForm.form";
	}
}
