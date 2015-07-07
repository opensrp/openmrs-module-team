/**
 * 
 */
package org.openmrs.module.teammodule.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamMemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import java.util.Calendar;
//import java.util.GregorianCalendar;

/**
 * @author Muhammad Safwan
 * 
 */

@Controller
@RequestMapping(value = "module/teammodule/searchedMember")
public class SearchedMemberController {

	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());

	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/teammodule/searchedMember";

	/**
	 * Initially called after the formBackingObject method to get the landing
	 * form name
	 * 
	 * @return String form view name
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Model model, HttpServletRequest request) {

		String searchMember = request.getParameter("searchMember");
		String dateFrom = request.getParameter("from");
		String dateTo = request.getParameter("to");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		List<String> parsedJoinDate = new ArrayList<String>();
		List<TeamMember> teamMember = null;
		List<TeamMember> dateSearch = null;

		Date joinFrom = null;
		Date joinTo = null;
		try {
			joinFrom = new SimpleDateFormat("dd/MM/yyyy").parse(dateFrom);
			joinTo = new SimpleDateFormat("dd/MM/yyyy").parse(dateTo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!searchMember.isEmpty()) {
			teamMember = (List<TeamMember>) Context.getService(TeamMemberService.class).searchMember(searchMember);
			for (int i = 0; i < teamMember.size(); i++) {
				if (teamMember.get(i).getJoinDate() != null) {
					String date = sdf.format(teamMember.get(i).getJoinDate());
					parsedJoinDate.add(date);
				} else {
					parsedJoinDate.add(null);
				}
			}
			model.addAttribute("parsedJoinDate", parsedJoinDate);
			model.addAttribute("searchMember", searchMember);
			model.addAttribute("teamMember", teamMember);
		} else {
			dateSearch = Context.getService(TeamMemberService.class).getMembers(joinFrom, joinTo);
			for (int i = 0; i < dateSearch.size(); i++) {
				if (dateSearch.get(i).getJoinDate() != null) {
					String date = sdf.format(dateSearch.get(i).getJoinDate());
					parsedJoinDate.add(date);
				} else {
					parsedJoinDate.add(null);
				}
			}
			model.addAttribute("parsedJoinDate", parsedJoinDate);
			model.addAttribute("dateSearch", dateSearch);
			model.addAttribute("dateTo", dateTo);
			model.addAttribute("dateFrom", dateFrom);
		}

	
			
		

		return SUCCESS_FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(Model model, HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, BindingResult errors) {

		if (errors.hasErrors()) {
			// return error view
		}

		return SUCCESS_FORM_VIEW;
	}

}
