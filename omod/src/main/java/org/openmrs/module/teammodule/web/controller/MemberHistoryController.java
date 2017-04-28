/**
 * 
 */
package org.openmrs.module.teammodule.web.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
//import org.openmrs.module.teammodule.api.TeamService;
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
@RequestMapping(value = "module/teammodule/memberHistory")
public class MemberHistoryController {

	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());

	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/teammodule/memberHistory";

	/**
	 * Initially called after the formBackingObject method to get the landing
	 * form name
	 * 
	 * @return String form view name
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Model model, HttpServletRequest request) {
		String personId = request.getParameter("personId");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");		//Added this to bring the format required
		List<List<Object>> list = Context.getAdministrationService().executeSQL("select team_id from team_member where person_id = " + personId, false);

		String[] team = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			team[i] = list.get(i).get(0).toString();
		}

		list.clear();

		for (int i = 0; i < team.length; i++) {
			list.add(Context.getAdministrationService().executeSQL("select name from team where team_id = " + team[i], false).get(0));
		}

		String[] teamName = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			teamName[i] = list.get(i).get(0).toString(); //error here
			System.out.println(teamName[i]);
		}

		list.clear();
		
		list = Context.getAdministrationService().executeSQL("select join_date from team_member where person_id = " + personId, false);
		//System.out.println(list);
		String[] joinDate = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			Object o = list.get(i).get(0);		//list of list of objects
			if (o != null) {	
				joinDate[i] = sdf.format(o);	//previously o.toString();
			}else{
				joinDate[i] = "";
			}
			
		}
		// System.out.println(joinDate);

		list.clear();
		list = Context.getAdministrationService().executeSQL("select leave_date from team_member where person_id = " + personId, false);

		String[] leaveDate = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {		
			Object o = list.get(i).get(0);		//list of list of objects
			if (o != null) {
				leaveDate[i] = sdf.format(o);  	//previously o.toString();
			}else{
				leaveDate[i] = "Present";
			}
			// System.out.println(teamName[i]);
		}


		model.addAttribute("teamName", teamName);
		model.addAttribute("join", joinDate);
		model.addAttribute("leave", leaveDate);
		model.addAttribute("personId", personId);
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
