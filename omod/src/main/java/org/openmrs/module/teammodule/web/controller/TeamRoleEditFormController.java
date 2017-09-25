/**
 *  @author Shakeeb Raza
 */
package org.openmrs.module.teammodule.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamRole;
import org.openmrs.module.teammodule.api.TeamRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Muhammad Safwan
 * 
 */

@Controller
@RequestMapping(value = "/module/teammodule/editRole")
public class TeamRoleEditFormController {
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());

	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/teammodule/TeamRoleEditForm";


	/**
	 * Initially called after the formBackingObject method to get the landing
	 * form name
	 * 
	 * @return String form view name
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Model model, HttpServletRequest request,@RequestParam(value = "roleId", required = true) String uuid, @ModelAttribute("teamData") Team team) {

		System.out.println(uuid);
		TeamRole roleData = Context.getService(TeamRoleService.class).getTeamRoleByUuid(uuid);
		List<TeamRole> roles = Context.getService(TeamRoleService.class).getAllTeamRole(true, false, null, null);

		
		System.out.println(uuid);
		System.out.println(roles);
		System.out.println(roleData);

		model.addAttribute("reportsToOptions", roles);
		model.addAttribute("reportsTo", roleData.getReportTo());
		model.addAttribute("roleData", roleData);
		
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
	public String onSubmit(HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, HttpServletRequest request,
	@ModelAttribute("roleData") TeamRole teamRole,  Model model) {
		String reportsToUuid = request.getParameter("reportsTo"); 
		System.out.println("reportsTo: " + reportsToUuid);
		TeamRole tr = Context.getService(TeamRoleService.class).getTeamRoleByUuid(reportsToUuid); 
		if(tr!=null) { teamRole.setReportTo(tr); }
		//teamRole.setUuid(UUID.randomUUID().toString());
		Context.getService(TeamRoleService.class).saveTeamRole(teamRole);
		String saved = "Role Updated Successfully";
		model.addAttribute("saved", saved);
		TeamRole roleData = Context.getService(TeamRoleService.class).getTeamRoleById(teamRole.getTeamRoleId());
		model.addAttribute("roleData", roleData);
		
		List<TeamRole> roles = Context.getService(TeamRoleService.class).getAllTeamRole(true, false, null, null);
		model.addAttribute("reportsTo", roles);

		return SUCCESS_FORM_VIEW;
	}

	

}
