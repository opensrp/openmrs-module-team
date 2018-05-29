/**
 * 
 */
package org.openmrs.module.teammodule.web.resource;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.PersonAttribute;
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
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

/**
 * @author Muhammad Safwan & Zohaib Masood
 * 
 */

@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teammember", supportedClass = TeamMember.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*", "1.12.*", "2.0.*", "2.1.*" })
public class TeamMemberRequestResource extends DataDelegatingCrudResource<TeamMember> {

	@Override
	public TeamMember newDelegate() {
		return new TeamMember();
	}

	@Override
	public TeamMember save(TeamMember teamMember) {
		try {
			if (teamMember.getId() != null && teamMember.getId() > 0) {
				Context.getService(TeamMemberService.class).updateTeamMember(teamMember);
				return teamMember;
			} else {
				Context.getService(TeamMemberService.class).saveTeamMember(teamMember);
				return teamMember;
			}
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
				description.addProperty("subTeamRoles");
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
				description.addProperty("subTeamRoles");
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
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("identifier");
		description.addProperty("joinDate");
		description.addProperty("leaveDate");
		description.addProperty("isDataProvider");
		description.addProperty("patients");
		description.addProperty("team");
		description.addProperty("teamRole");
		description.addRequiredProperty("person");
		description.addRequiredProperty("locations");
		return description;
	}
	
	@Override
	public DelegatingResourceDescription getUpdatableProperties()  {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("identifier");
		description.addProperty("person");	
		description.addProperty("locations");
		description.addProperty("team");
		description.addProperty("teamRole");
		description.addProperty("patients");
		description.addProperty("subTeams");
		description.addProperty("voided");
		description.addProperty("voidReason");
		description.addProperty("isDataProvider");
		description.addProperty("subTeamRoles");
		return description;
	}

	@Override
	public TeamMember getByUniqueId(String uuid) {
		return Context.getService(TeamMemberService.class).getTeamMemberByUuid(uuid);
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
			List<TeamMember> teamMembers = Context.getService(TeamMemberService.class).searchTeamMember( null, null, null, null, null, null, null, context.getParameter("q"),null, null, null);
			return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
		}
		Integer s=null, tr=null, t=null, l=null ;
		String identifier = "", supervisorUuid = "", teamRoleUuid = "", teamUuid = "", locationUuid = "";
		
		if (context.getParameter("nameOrIdentifier") != null) {
			identifier =context.getParameter("nameOrIdentifier");
		}
		
		TeamMember supervisor = null;
		if (context.getParameter("supervisor") != null) {
			supervisorUuid =context.getParameter("supervisor");
			supervisor = Context.getService(TeamMemberService.class).getTeamMemberByUuid(supervisorUuid);
			s= supervisor.getId();
		}
		
		Team team = null;
		if (context.getParameter("team") != null) {
			teamUuid =context.getParameter("team");
			team = Context.getService(TeamService.class).getTeamByUUID(teamUuid);
			t=team.getId();
		}
		TeamRole teamRole = null;
		if (context.getParameter("role") != null) {
			teamRoleUuid =context.getParameter("role");
			teamRole = Context.getService(TeamRoleService.class).getTeamRoleByUuid((teamRoleUuid));
			tr=teamRole.getId();
		}
		Location location = null;
		if (context.getParameter("location") != null) {
			locationUuid =context.getParameter("location");
			location = Context.getLocationService().getLocationByUuid(locationUuid);
			l=location.getId();
		}
		
		List<TeamMember> teamMembers = (List<TeamMember>) Context.getService(TeamMemberService.class)
				.searchTeamMember(identifier, s, tr, t, l, null, null, null, null, null, null);
		return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
	}

	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<TeamMember> teamMembers=null;
		Integer offset=null;
		Integer size= null;
		Boolean voided=null;
		
		if(context.getParameter("offset")!=null && context.getParameter("size")!=null)
		{
			offset=Integer.parseInt(context.getParameter("offset"));
			size= Integer.parseInt(context.getParameter("size"));
		}
		if(context.getParameter("voided")!=null)
		{
			voided=Boolean.parseBoolean(context.getParameter("voided"));
		}
		teamMembers = Context.getService(TeamMemberService.class).getAllTeamMember(null, voided, offset, size);
		return new NeedsPaging<TeamMember>(teamMembers, context);	
	}
	
	
	@PropertyGetter("display")
	public String getDisplayString(TeamMember teamMember) {
		if (teamMember == null || teamMember.getPerson() == null || teamMember.getPerson().getPersonName() == null) {
			return "";
		}
		return teamMember.getPerson().getPersonName().toString();
	}
	
	
	@PropertyGetter("subTeams")
	public List<Team> getSubTeam(TeamMember teamMember) {
		List<Team> teams = Context.getService(TeamService.class).getSubTeams(teamMember.getId());
		if (teams == null) {
			return null;
		}
		return teams;
	}
	
	@PropertyGetter("subTeamRoles")
	public List<TeamRole> getSubTeamRole(TeamMember teamMember) { 
		List<TeamRole> teamRoles = Context.getService(TeamRoleService.class)
				.getSubTeamRoles(teamMember.getId());
		if (teamRoles == null) {
			return null;
		}
		return teamRoles;
	}
}