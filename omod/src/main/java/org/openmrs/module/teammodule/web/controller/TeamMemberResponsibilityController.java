package org.openmrs.module.teammodule.web.controller;

/**
*
* @author Shakeeb Raza
*
**/

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
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.TeamMemberPatientRelation;
import org.openmrs.module.teammodule.api.TeamMemberPatientRelationService;
import org.openmrs.module.teammodule.api.TeamMemberService;
import org.openmrs.module.teammodule.api.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class TeamMemberResponsibilityController {

	private final String teamMemberResponsibility = "/module/teammodule/teamMemberResponsibility";
	private final String teamMemberResponsibilityDetail = "/module/teammodule/teamMemberResponsibilityDetails";
	private final String teamMemberResponsibilityAdd = "/module/teammodule/teamMemberResponsibilityAdd";
		
	@RequestMapping(value = "module/teammodule/teamMemberResponsibility.form", method = RequestMethod.GET)
	public String showTeamMemberResponsibility(Model model, HttpServletRequest request,@RequestParam("teamId")String teamId) {
		Team team = Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId));
		
		/*List<TeamMember> teamMember = Context.getService(TeamMemberService.class).getTeamMemberByTeam(team, null, null, null);*/
		List<TeamMember> teamMember = Context.getService(TeamMemberService.class).searchTeamMemberByTeam(team.getId());
		
		model.addAttribute("teamName", team.getTeamName());
		model.addAttribute("teamMember", teamMember.size());
		
		List<Map> list=new ArrayList<Map>();
		Map m;
		for (int i=0;i<teamMember.size();i++)
		{
		m=new HashedMap();
		
		/*List<TeamMemberPatientRelation> tprs =Context.getService(TeamMemberPatientRelationService.class).getTeamPatientRelations(teamMember.get(i));*/
		List<TeamMemberPatientRelation> tprs =Context.getService(TeamMemberPatientRelationService.class).getTeamMemberPatientRelations(teamMember.get(i));
		
		m.put("size", tprs.size());
		m.put("size", 0);
		m.put("memberId", teamMember.get(i).getTeamMemberId());
		Set<Location> location = teamMember.get(i).getLocations();
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
		TeamMember teamMember = Context.getService(TeamMemberService.class).getTeamMember(Integer.valueOf(memberId));
		model.addAttribute("memberId", memberId);
		model.addAttribute("memberName", teamMember.getPerson().getNames());
		
		/*List<TeamMemberPatientRelation> tprs =Context.getService(TeamMemberPatientRelationService.class).getTeamPatientRelations(teamMember);*/
		List<TeamMemberPatientRelation> tprs =Context.getService(TeamMemberPatientRelationService.class).getTeamMemberPatientRelations(teamMember);
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
			
			/*Date assignmentDate = tprs.get(i).getAssignmentDate();*/
			Date assignmentDate = tprs.get(i).getDateAssigned();
			
			m.put("assignmentDate", assignmentDate);
			String reason = tprs.get(i).getReason();
			m.put("reason", reason);
			m.put("memberPatientId",tprs.get(i).getId());
			list.add(m);
			model.addAttribute("list", list);
		}
		
		return teamMemberResponsibilityDetail;
	}
	
	@RequestMapping(value = "module/teammodule/teamMemberResponsibilityAdd.form", method = RequestMethod.GET)
	public String TeamMemberResponsibilityAdd(Model model, HttpServletRequest request,@RequestParam("memberId")String memberId) {
		
		List<Map> list=new ArrayList<Map>();
		Map m;
		TeamMember teamMember = Context.getService(TeamMemberService.class).getTeamMember(Integer.valueOf(memberId));
		model.addAttribute("memberId", memberId);
		model.addAttribute("memberName", teamMember.getPerson().getNames());
		List<Patient> patient=Context.getService(PatientService.class).getAllPatients(true);
		model.addAttribute("patient", patient);
		return teamMemberResponsibilityAdd;
	}
	
	@RequestMapping(value = "module/teammodule/teamMemberResponsibilityAddData.form", method = RequestMethod.GET)
	public String TeamMemberResponsibilityAddData(Model model, HttpServletRequest request,@RequestParam("patientText")int patientText, @RequestParam("memberId")int memberId
			,@RequestParam("reason")String reason,@RequestParam("status")String status) {
		List<Map> list=new ArrayList<Map>();
		Map m;
		Patient patient=Context.getService(PatientService.class).getPatient(patientText);
		TeamMember teamMember = Context.getService(TeamMemberService.class).getTeamMember(memberId);
		Date date=new Date();
		/*TeamMemberPatientRelation tmpr=new TeamMemberPatientRelation();
		tmpr.setAssignmentDate(date);
		tmpr.setStatus(status);
		tmpr.setReason(reason);
		tmpr.setMember(teamMember);
		tmpr.setPatient(patient);
		TeamMemberPatientRelation memberPatientRelation = Context.getService(TeamMemberPatientRelationService.class).getTeamPatientRelations(tmpr);
		if(memberPatientRelation!=null)
		{
			model.addAttribute("exception", "Patient Already been Added");
			return teamMemberResponsibilityAdd;
		}
		Context.getService(TeamMemberPatientRelationService.class).save(tmpr);*/
		return teamMemberResponsibilityDetail;
		
	}
	
	@RequestMapping(value = "module/teammodule/teamMemberResponsibilityUnAssign.form", method = RequestMethod.GET)
	@ResponseBody
	public void TeamMemberResponsibilityUnAssign(Model model, HttpServletRequest request,@RequestParam("memberPatientId")int memberPatientId) {
		/*TeamMemberPatientRelation teamMemberPatientObject = Context.getService(TeamMemberPatientRelationService.class).getTeamPatientRelation(memberPatientId);
		Context.getService(TeamMemberPatientRelationService.class).delete(teamMemberPatientObject);*/
	}
}