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
import org.openmrs.module.teammodule.TeamRole;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamRoleService;
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
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.webservices.rest.web.v1_0.controller.ComplexDataDelegatingCrudResource;

/**
 * @author Muhammad Safwan
 * 
 */

@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teammember", supportedClass = TeamMember.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*", "1.12.*" })
public class TeamMemberRequestResource extends ComplexDataDelegatingCrudResource<TeamMember> {

	@Override
	public TeamMember newDelegate() {
		return new TeamMember();
	}

	@Override
	public TeamMember save(TeamMember delegate) {
		try {
			if(delegate.getId() != null && delegate.getId() > 0) { Context.getService(TeamMemberService.class).updateTeamMember(delegate); return delegate; }
			else { Context.getService(TeamMemberService.class).saveTeamMember(delegate); return delegate; }
		}
		catch(Exception e) { e.printStackTrace(); throw new RuntimeException(e); }
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;

		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
			if (rep instanceof DefaultRepresentation) {
				description.addProperty("display");
				description.addProperty("identifier");
				description.addProperty("uuid");
				description.addProperty("voided");
				description.addProperty("voidReason");
				description.addProperty("isDataProvider");
				description.addProperty("subTeams");
				description.addProperty("subTeamsCount");
				description.addProperty("subTeamRoles");
				description.addProperty("subTeamRolesCount");
				description.addProperty("person", Representation.DEFAULT);
				description.addProperty("team", Representation.DEFAULT);
				description.addProperty("teamRole", Representation.DEFAULT);
				description.addProperty("locations", Representation.DEFAULT);
				description.addProperty("patients", Representation.DEFAULT);
			} else if (rep instanceof FullRepresentation) {
				description.addProperty("display");
				description.addProperty("identifier");
				description.addProperty("uuid");
				description.addProperty("voided");
				description.addProperty("voidReason");
				description.addProperty("isDataProvider");
				description.addProperty("subTeams");
				description.addProperty("subTeamsCount");
				description.addProperty("subTeamRoles");
				description.addProperty("subTeamRolesCount");
				description.addProperty("person", Representation.FULL);
				description.addProperty("team", Representation.FULL);
				description.addProperty("teamRole", Representation.FULL);
				description.addProperty("locations", Representation.FULL);
				description.addProperty("patients", Representation.FULL);
				description.addProperty("auditInfo");
				description.addSelfLink();
			}
		}
		return description;
	}


	@Override
	public SimpleObject subresourceSave(TeamMember delegate, String subResource, SimpleObject post, RequestContext context) {
		if(subResource.equals("team") && post.get("team") != null) {//post.get("team") == new team id
			System.out.println("subResource: " + subResource);
			System.out.println("team: " + post.get("team").toString());
			delegate.setTeam(Context.getService(TeamService.class).getTeam(Integer.parseInt(post.get("team").toString())));
			Context.getService(TeamMemberService.class).saveTeamMember(delegate);
			List<TeamMember> teamMembers = new ArrayList<>();
			teamMembers.add(delegate);
			System.out.println("teamMembers: " + teamMembers);
			return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
		}
		else if(subResource.equals("role") && post.get("role") != null) {//post.get("team") == new team id
			System.out.println("subResource: " + subResource);
			System.out.println("role: " + post.get("role").toString());
			delegate.setTeamRole(Context.getService(TeamRoleService.class).getTeamRoleById(Integer.parseInt(post.get("role").toString())));
			Context.getService(TeamMemberService.class).saveTeamMember(delegate);
			List<TeamMember> teamMembers = new ArrayList<>();
			teamMembers.add(delegate);
			System.out.println("teamMembers: " + teamMembers);
			return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
		}
		else { return null; }
	}
	
	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("identifier");
		description.addProperty("joinDate");
		description.addProperty("leaveDate");
		description.addProperty("isDataProvider");
		description.addProperty("patients");
		description.addRequiredProperty("team");
		description.addRequiredProperty("person");
		description.addRequiredProperty("locations");
		description.addRequiredProperty("teamRole");
		return description;
	}
	
	@Override
	public DelegatingResourceDescription getUpdatableProperties()  {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("identifier");
		description.addProperty("person");	
		description.addProperty("locations");
		description.addProperty("team");
		description.addProperty("patients");
		description.addProperty("subTeams");
		description.addProperty("voided");
		description.addProperty("voidReason");
		description.addProperty("isDataProvider");
		description.addProperty("subTeamRoles");
		//description.addProperty("teamRole");
		return description;
	}

	@Override
	public TeamMember getByUniqueId(String uuid) {
		TeamMember tm = Context.getService(TeamMemberService.class).getTeamMemberByUuid(uuid);
		if(tm != null){
			return tm;
		}
		
		Person person = Context.getPersonService().getPersonByUuid(uuid);
		if (person != null) {
			List<TeamMember> tml = Context.getService(TeamMemberService.class).getTeamMemberByPersonId(person.getPersonId());
			return tml.size() > 0 ? tml.get(0) : null;
		}
		return null;
	}

	@Override
	protected void delete(TeamMember delegate, String reason, RequestContext context) throws ResponseException {
		Context.getService(TeamMemberService.class).purgeTeamMember(delegate);
	}

	@Override
	public void purge(TeamMember delegate, RequestContext context) throws ResponseException {
		Context.getService(TeamMemberService.class).purgeTeamMember(delegate);
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

				System.out.println("\nFrom UI: " + id + ", " + supervisorId + ", " + teamRoleId + ", " + teamId + ", " + locationId + "\n");

				if(id.equals("") ) { id = null; }
				if(supervisorId.equals("") ) { supervisorId = null; }
				if(teamRoleId.equals("") ) { teamRoleId = null; }
				if(teamId.equals("") ) { teamId = null; }
				if(locationId.equals("") ) { locationId = null; }

				TeamMember supervisor = null; if(supervisorId != null) { supervisor = Context.getService(TeamMemberService.class).getTeamMember(Integer.parseInt(supervisorId)); }
				Team team = null; if(teamId != null) { team = Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId)); }
				TeamRole teamRole = null; if(teamRoleId != null) { teamRole = Context.getService(TeamRoleService.class).getTeamRoleById(Integer.parseInt(teamRoleId)); }
				Location location = null; if(locationId != null) { location = Context.getLocationService().getLocation(Integer.parseInt(locationId)); }
				
				System.out.println("\nTo Function: " + id + ", " + supervisorId + ", " + teamRoleId + ", " + teamId + ", " + locationId + "\n");
				List<TeamMember> teamMembers = (List<TeamMember>) Context.getService(TeamMemberService.class).searchTeamMember(id, supervisor, teamRole, team, location, null, null);

				//System.out.println("\nAFTER: " + id + ", " + supervisorId + ", " + teamRoleId + ", " + teamId + ", " + locationId + "\n");
				System.out.println("teamMembers: " + teamMembers);

				return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
			}
			else { System.out.println("get: " + context.getParameter("get")); return null; }
		} else { return null; }
	}

	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<TeamMember> teamMembers = Context.getService(TeamMemberService.class).getAllTeamMember(null, false, 0, 100);
		return new NeedsPaging<TeamMember>(teamMembers, context);	
	}
	
	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		List<TeamMember> teamMembers = Context.getService(TeamMemberService.class).getAllTeamMember(null, false, null, null);
		return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);	
	}
	
	@PropertyGetter("display")
	public String getDisplayString(TeamMember teamMember) {
		if (teamMember == null){ return ""; } return teamMember.getPerson().getPersonName().toString();
	}
	
	@PropertyGetter("subTeams")
	public List<Team> getSubTeam(TeamMember teamMember) {
		List<Team> teams = Context.getService(TeamService.class).getSubTeams(teamMember); //if(teams == null) { return ""; } else { String str = ""; for (int i = 0; i < teams.size(); i++) { if(i==teams.size()-1) { str += teams.get(i).getTeamName(); } else { str += teams.get(i).getTeamName() + ", "; } } return str; }
		if(teams == null) return null; else { return teams; } 
	}
	
	@PropertyGetter("subTeamRoles")
	public String getSubTeamRole(TeamMember teamMember) { 
		List<TeamRole> teamRoles = Context.getService(TeamRoleService.class).getSubTeamRoles(teamMember); if(teamRoles == null) { return ""; } else { String str = ""; for (int i = 0; i < teamRoles.size(); i++) { if(i==teamRoles.size()-1) { str += teamRoles.get(i).getName(); } else { str += teamRoles.get(i).getName() + ", "; } } return str; } 
	}
	
	@PropertyGetter("subTeamsCount")
	public String getSubTeamCount(TeamMember teamMember) {
		List<Team> teams = Context.getService(TeamService.class).getSubTeams(teamMember); if(teams == null) { return "0"; } else { return Integer.toString(teams.size()); }
	}
	
	@PropertyGetter("subTeamRolesCount")
	public String getSubTeamRoleCount(TeamMember teamMember) {
		List<TeamRole> teamRoles = Context.getService(TeamRoleService.class).getSubTeamRoles(teamMember); if(teamRoles == null) { return "0"; } else { return Integer.toString(teamRoles.size()); }
	}
}