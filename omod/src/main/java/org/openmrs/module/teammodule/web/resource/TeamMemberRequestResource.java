/**
 * 
 */
package org.openmrs.module.teammodule.web.resource;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamHierarchy;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamHierarchyService;
import org.openmrs.module.teammodule.api.TeamMemberService;
import org.openmrs.module.teammodule.api.TeamService;
import org.openmrs.module.teammodule.rest.v1_0.resource.TeamModuleResourceController;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

/**
 * @author Muhammad Safwan
 * 
 */

@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teammember", supportedClass = TeamMember.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*", "1.12.*" })
public class TeamMemberRequestResource extends DataDelegatingCrudResource<TeamMember> {

	@Override
	public TeamMember newDelegate() {
		return new TeamMember();
	}

	@Override
	public TeamMember save(TeamMember delegate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;

		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
			if (rep instanceof DefaultRepresentation) {
				description.addProperty("teamMemberId");
				description.addProperty("identifier");
				description.addProperty("person");
				description.addProperty("uuid");
				description.addProperty("location");
				description.addProperty("team");
				description.addProperty("patients", Representation.REF);
				description.addProperty("teamHierarchy");
				description.addProperty("reportTo");
				description.addProperty("subTeam");
				description.addProperty("subTeamHierarchy");
				description.addProperty("personId");
			} else if (rep instanceof FullRepresentation) {
				description.addProperty("teamMemberId");
				description.addProperty("identifier");
				description.addProperty("person");
				description.addProperty("uuid");
				description.addProperty("location");
				description.addProperty("team");
				description.addProperty("patients", Representation.REF);
				description.addProperty("teamHierarchy");
				description.addProperty("reportTo");
				description.addProperty("subTeam");
				description.addProperty("subTeamHierarchy");
				description.addProperty("personId");
			}
		}
		return description;
	}

	@Override
	public TeamMember getByUniqueId(String uuid) {
		//System.out.println("Inside");
		Person person = Context.getPersonService().getPersonByUuid(uuid);
		//System.out.println(person);
		if (person != null) {
			Integer id = person.getPersonId();
			List<TeamMember> tm = Context.getService(TeamMemberService.class).getTeamMemberByPersonId(id);
			id = tm.get(0).getTeamMemberId();
			TeamMember member = Context.getService(TeamMemberService.class).getTeamMember(id);
			return member;
		}
		return Context.getService(TeamMemberService.class).getTeamMemberByUuid(uuid);

	}

	@Override
	protected void delete(TeamMember delegate, String reason, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void purge(TeamMember delegate, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub

	}

	@Override
	public SimpleObject search(RequestContext context) {
		if(context.getParameter("q") != null) {
			System.out.println("q: " + context.getParameter("q"));
			List<TeamMember> teamMembers = Context.getService(TeamMemberService.class).searchTeamMember(null, null, context.getParameter("q"), null, null);
			System.out.println("teamMembers: " + teamMembers);
			return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
		}
		else if(context.getParameter("id") != null) {
			System.out.println("id: " + context.getParameter("id"));
			TeamMember teamMember = Context.getService(TeamMemberService.class).getTeamMember(Integer.parseInt(context.getParameter("id")));
			List<TeamMember> teamMembers = new ArrayList<>();
			teamMembers.add(teamMember);
			System.out.println("teamMembers: " + teamMembers);
			return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
		}
		// FOR UI
		else if(context.getParameter("get") != null) {
			//GET 'ALL' TEAM MEMBERS 'ON PAGE LOAD' - teamMemberView.form
			if(context.getParameter("get").equals("all")) {
				List<TeamMember> teamMembers = (List<TeamMember>) Context.getService(TeamMemberService.class).getAllTeamMember(null, true, null, null);
				System.out.println("teamMembers: " + teamMembers);
				return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
			}
			//GET 'FILTERED' TEAM MEMBERS 'ON FILTER BUTTON CLICK' - teamMemberView.form
			else if(context.getParameter("get").equals("filter")) {
				String id = "", supervisorId = "", teamRoleId = "", teamId = "", locationId = "";
				
				if(context.getParameter("identifier") != null) { id = context.getParameter("identifier"); }
				if(context.getParameter("supervisor") != null) { supervisorId = context.getParameter("supervisor"); }
				if(context.getParameter("role") != null) { teamRoleId = context.getParameter("role"); }
				if(context.getParameter("team") != null) { teamId = context.getParameter("team"); }
				if(context.getParameter("location") != null) { locationId = context.getParameter("location"); }

				//System.out.println("\nFrom UI: " + id + ", " + supervisorId + ", " + teamRoleId + ", " + teamId + ", " + locationId + "\n");

				if(id.equals("") ) { id = null; }
				if(supervisorId.equals("") ) { supervisorId = null; }
				if(teamRoleId.equals("") ) { teamRoleId = null; }
				if(teamId.equals("") ) { teamId = null; }
				if(locationId.equals("") ) { locationId = null; }

				TeamMember supervisor = null; if(supervisorId != null) { supervisor = Context.getService(TeamMemberService.class).getTeamMember(Integer.parseInt(supervisorId)); }
				Team team = null; if(teamId != null) { team = Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId)); }
				TeamHierarchy teamRole = null; if(teamRoleId != null) { teamRole = Context.getService(TeamHierarchyService.class).getTeamRoleById(Integer.parseInt(teamRoleId)); }
				Location location = null; if(locationId != null) { location = Context.getLocationService().getLocation(Integer.parseInt(locationId)); }
				
				//System.out.println("\nTo Function: " + id + ", " + supervisorId + ", " + teamRoleId + ", " + teamId + ", " + locationId + "\n");

				List<TeamMember> teamMembers = (List<TeamMember>) Context.getService(TeamMemberService.class).searchTeamMember(id, supervisor, teamRole, team, location, null, null);

				//System.out.println("\nAFTER: " + id + ", " + supervisorId + ", " + teamRoleId + ", " + teamId + ", " + locationId + "\n");
				System.out.println("teamMembers: " + teamMembers);

				return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
			}
			else {
				System.out.println("get: " + context.getParameter("get"));
				return null;
			}
		}
		else { return null; }
	}

	@PropertyGetter("personId")
	public String getPersonId(TeamMember teamMember) {
		if (teamMember == null){ return ""; }
		return teamMember.getPerson().getId().toString();
	}


	@PropertyGetter("teamHierarchy")
	public String getTeamRoleName(TeamMember teamMember) {
		if (teamMember == null){ return ""; }
		return teamMember.getTeamHierarchy().getName().toString();
	}
	
	@PropertyGetter("reportTo")
	public String getReportTo(TeamMember teamMember) {
		if (teamMember == null){ return ""; }
		return teamMember.getTeamHierarchy().getReportTo().getName();
	}

	@PropertyGetter("subTeam")
	public String getSubTeam(TeamMember teamMember) {
		List<TeamMember> tm = Context.getService(TeamMemberService.class).getTeamMemberByPersonId(teamMember.getPerson().getId());
		if(tm == null) { return ""; }
		else { String teamNames = "";
			for (int j = 0; j < tm.size(); j++) { 
				if(j==tm.size()-1) { teamNames += tm.get(j).getTeam().getTeamName() + ""; }
				else { teamNames += tm.get(j).getTeam().getTeamName() + ", "; }
			} return teamNames;
		}
	}
	
	@PropertyGetter("subTeamHierarchy")
	public String getSubTeamRole(TeamMember teamMember) {
		List<TeamMember> tm = Context.getService(TeamMemberService.class).getTeamMemberByPersonId(teamMember.getPerson().getId());
		if(tm == null) { return ""; }
		else { String teamNames = "";
			for (int j = 0; j < tm.size(); j++) { 
				if(j==tm.size()-1) { teamNames += tm.get(j).getTeamHierarchy().getName() + ""; }
				else { teamNames += tm.get(j).getTeamHierarchy().getName() + ", "; }
			} return teamNames;
		}
	}
}