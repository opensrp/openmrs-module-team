/**
 *  @author Shakeeb Raza
 */
package org.openmrs.module.teammodule.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.teammodule.Team;
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
@RequestMapping(value = "module/teammodule/team.form")
public class TeamController {
	HttpServletRequest request;
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());

	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/teammodule/team";

	/**
	 * Initially called after the formBackingObject method to get the landing
	 * form name
	 * 
	 * @return String form view name
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Model model, HttpServletRequest request) {
/*		List<Team> team;
		List<TeamMember> teamMember;
		List<TeamLocation> teamLocation;
		
		TeamSupervisor Supervisor;
		Team searchTeam = new Team();
//		TeamLocation searchTeamLocation = new TeamLocation();
		
		String searchedTeam = "";
		try {
			searchedTeam = request.getParameter("searchTeam");
		} catch (Exception e) {
			searchedTeam = "";
		}
		List<String> parsedDate = new ArrayList<String>();
		List<Integer> length = new ArrayList<Integer>();
		List<String> teamSupervisor = new ArrayList<String>();
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		if (Context.isAuthenticated()) {
			if (searchedTeam != null) {
				team = Context.getService(TeamService.class).searchTeam(searchedTeam);
				teamLocation = new ArrayList<>();
				for (int i = 0; i < team.size(); i++) {
					teamLocation.add(Context.getService(TeamLocationService.class).getTeamLocationByTeamId(team.get(i).getId()));
				}
			} else {
				team = Context.getService(TeamService.class).getAllTeams(true);
				teamLocation = new ArrayList<>();
				for (int i = 0; i < team.size(); i++) {
					teamLocation.add(Context.getService(TeamLocationService.class).getTeamLocationByTeamId(team.get(i).getId()));
				}
			}
			for (int i = 0; i < team.size(); i++) {
				teamMember = Context.getService(TeamMemberService.class).getTeamMemberByTeam(team.get(i), null, null, false);
				System.out.println(team.get(i).getTeamId());
				System.out.println(teamMember);
				System.out.println(team.get(i).getUuid());
				//membersNotVoided = Context.getService(TeamMemberService.class).getTeamMembers(team.get(i), null, null, true);
				String date = sdf.format(team.get(i).getDateCreated());
				parsedDate.add(date);
				System.out.println(teamMember.size());
				length.add(teamMember.size());
				Supervisor = Context.getService(TeamSupervisorService.class).getTeamSupervisor(team.get(i));

				if (Supervisor != null && Supervisor.isVoided() != true) { // change made
																// here
					TeamMember tm = Supervisor.getTeamMember();
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

					teamSupervisor.add(name);
				} else {
					teamSupervisor.add(null);
				}

			}			
			Collection<Privilege> privilege = Context.getUserContext().getAuthenticatedUser().getPrivileges();
			
			model.addAttribute("team", team);
			model.addAttribute("length", length);
			model.addAttribute("searchTeam", searchTeam);
			model.addAttribute("teamSupervisor", teamSupervisor);
			model.addAttribute("parsedDate", parsedDate);
			model.addAttribute("searchedTeam", searchedTeam);
			model.addAttribute("privilege", privilege);
			model.addAttribute("teamLocation", teamLocation);
		}
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
	public String onSubmit(Model model, HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, BindingResult errors, @ModelAttribute("searchTeam") Team searchTeam) {

		if (errors.hasErrors()) {
			// return error view
		}

		return "redirect:/module/teammodule/team.form?searchTeam=" + searchTeam.getTeamName();
	}

}