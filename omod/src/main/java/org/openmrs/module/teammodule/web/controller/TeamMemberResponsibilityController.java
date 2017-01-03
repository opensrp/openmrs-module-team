package org.openmrs.module.teammodule.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PersonName;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.TeamMemberPatientRelation;
import org.openmrs.module.teammodule.api.TeamMemberPatientRelationService;
import org.openmrs.module.teammodule.api.TeamMemberService;
import org.openmrs.module.teammodule.api.TeamService;
import org.openmrs.module.teammodule.api.impl.TeamMemberPatientRelationServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class TeamMemberResponsibilityController {

	private final String teamMemberResponsibility = "/module/teammodule/teamMemberResponsibility";
	private final String teamMemberResponsibilityDetail = "/module/teammodule/teamMemberResponsibilityDetails";
	
	@RequestMapping(value = "module/teammodule/teamMemberResponsibility.form", method = RequestMethod.GET)
	public String showTeamMemberResponsibility(Model model, HttpServletRequest request,@RequestParam("teamId")String teamId) {
		Team team = Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId));
		List<TeamMember> teamMember = Context.getService(TeamMemberService.class).getTeamMembersPage(team, null, null, null);
		model.addAttribute("teamName", team.getTeamName());
		
		List<Map> list=new ArrayList<Map>();
		Map m;
		for (int i=0;i<teamMember.size();i++)
		{
		m=new HashedMap();
		List<TeamMemberPatientRelation> tprs =Context.getService(TeamMemberPatientRelationService.class).getTeamPatientRelation(teamMember.get(i).getTeamMemberId());
		m.put("size", tprs.size());
		m.put("memberId", teamMember.get(i).getTeamMemberId());
		Set<Location> location = teamMember.get(i).getLocation();
		m.put("location",location);
		list.add(m);
		}
		model.addAttribute("list", list);
		return teamMemberResponsibility;
	}
	
	@RequestMapping(value = "module/teammodule/teamMemberResponsibilityDetails.form", method = RequestMethod.GET)
	public String showTeamMemberResponsibilityDetail(Model model, HttpServletRequest request,@RequestParam("memberId")String memberId) {
		
		List<Map> list=new ArrayList<Map>();
		Map m;
		TeamMember teamMember = Context.getService(TeamMemberService.class).getMember(Integer.valueOf(memberId));
		model.addAttribute("memberId", memberId);
		model.addAttribute("memberName", teamMember.getPerson().getNames());
		
		List<TeamMemberPatientRelation> tprs =Context.getService(TeamMemberPatientRelationService.class).getTeamPatientRelation(Integer.valueOf(memberId));
		for(int i=0;i<tprs.size();i++)
		{
			m=new HashedMap();
			Location location = tprs.get(i).getLocation();
			m.put("location",location);
			Patient patient=tprs.get(i).getPatient();
			Integer age = patient.getAge();
			m.put("age", age);
			Set<PersonName> name = patient.getNames();
			m.put("name", name);
			Set<PatientIdentifier> id = patient.getIdentifiers();
			m.put("id", id);
			String status = tprs.get(i).getStatus();
			m.put("status", status);
			Date assignmentDate = tprs.get(i).getAssignmentDate();
			m.put("assignmentDate", assignmentDate);
			String reason = tprs.get(i).getReason();
			m.put("reason", reason);
			
			list.add(m);
			model.addAttribute("list", list);
		}
		
		return teamMemberResponsibilityDetail;
	}
	
}
