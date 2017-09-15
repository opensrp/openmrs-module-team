package org.openmrs.module.teammodule.web.resource;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamRole;
import org.openmrs.module.teammodule.api.TeamMemberService;
import org.openmrs.module.teammodule.api.TeamRoleService;
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
 * @author Shakeeb raza
 * 
 */
@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teamrole", supportedClass = TeamRole.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*", "1.12.*", "2.0.*", "2.1.*" })
public class TeamRoleRequestResource extends DataDelegatingCrudResource<TeamRole> {

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;
		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
		if (rep instanceof DefaultRepresentation) {
			description.addProperty("display");
			description.addProperty("identifier");
			description.addProperty("uuid");
			description.addProperty("name");
			description.addProperty("voided");
			description.addProperty("voidReason");
			description.addProperty("ownsTeam");
			description.addProperty("reportTo");
			description.addProperty("reportByName");
			description.addProperty("members");
			} else if (rep instanceof FullRepresentation) {
			description.addProperty("display");
			description.addProperty("identifier");
			description.addProperty("name");
			description.addProperty("voided");
			description.addProperty("voidReason");
			description.addProperty("uuid");
			description.addProperty("ownsTeam");
			description.addProperty("reportTo");
			description.addProperty("reportByName");
			description.addProperty("members");
			description.addProperty("auditInfo");
			description.addSelfLink();
			}
		}

		return description;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("name");
		description.addProperty("identifier");
		description.addProperty("ownsTeam");
		description.addProperty("reportTo");
		description.addProperty("reportByName");
		description.addProperty("voided");
		description.addProperty("voidReason");
		return description;
	}
	
	@Override
	public DelegatingResourceDescription getUpdatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("name");
		description.addProperty("identifier");
		description.addProperty("ownsTeam");
		description.addProperty("reportTo");
		description.addProperty("reportByName");
		description.addProperty("voided");
		description.addProperty("voidReason");
		return description;
	}
	
	@Override
	public TeamRole newDelegate() {
		return new TeamRole();
	}
	
	@Override
	public TeamRole save(TeamRole teamRole) {
		try {
			if (teamRole.getId() != null && teamRole.getId() > 0) {
				Context.getService(TeamRoleService.class).updateTeamRole(teamRole);
				return teamRole;
			} else {
				Context.getService(TeamRoleService.class).saveTeamRole(teamRole);
				return teamRole;
			}
		}
		catch(Exception e) { 
			e.printStackTrace();
			throw new RuntimeException(e);
			}
	}

	@Override
	protected void delete(TeamRole teamRole, String reason, RequestContext context) throws ResponseException {
		Context.getService(TeamRoleService.class).purgeTeamRole(teamRole);
	}
	
	@Override
	public void purge(TeamRole teamRole, RequestContext arg1) throws ResponseException {
		Context.getService(TeamRoleService.class).purgeTeamRole(teamRole);
	}
	
	@Override
	public SimpleObject search(RequestContext context) {
		if(context.getParameter("q") != null) {
			List<TeamRole> teamRoles = Context.getService(TeamRoleService.class).searchTeamRole(context.getParameter("q"));
			return new NeedsPaging<TeamRole>(teamRoles, context).toSimpleObject(this);
	}
		 return null;
	}
	
	@PropertyGetter("display")
	public String getDisplayString(TeamRole teamRole) {
		if (teamRole == null) {
			return "";
		}
		return teamRole.getName();
	}
	
	@PropertyGetter("reportByName")
	public List<String> getReportByName(TeamRole teamRole) {
		if (teamRole == null) {
			return null;
		}
		List<TeamRole> teamRoles = Context.getService(TeamRoleService.class).searchTeamRoleBySupervisorTeamRole(teamRole.getTeamRoleId());
		if (teamRoles == null) {
			return null;
		} else {
			List<String> reportByNames = new ArrayList<>();
			for (TeamRole tr : teamRoles) {
				reportByNames.add(tr.getName());
			}
			return reportByNames;
		}
	}
	
	@PropertyGetter("members")
	public int getNumberOfMember(TeamRole teamRole) {
		if (teamRole == null) {
			return 0;
		}
		return Context.getService(TeamMemberService.class).countTeamMemberByTeamRole(teamRole.getTeamRoleId());
	}
	
	@Override
	public TeamRole getByUniqueId(String uniqueId) {
		return Context.getService(TeamRoleService.class).getTeamRoleByUuid(uniqueId);
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		int offset,size;
		Boolean ownsTeam = Boolean.getBoolean(context.getParameter("ownsTeam"));
		List<TeamRole> teamsHierarchies;
		
		if(context.getParameter("offset")!=null && context.getParameter("size")!=null && context.getParameter("ownsTeam")!=null)
		{
			offset=Integer.parseInt(context.getParameter("offset"));
			size= Integer.parseInt(context.getParameter("size"));
			teamsHierarchies = Context.getService(TeamRoleService.class).getAllTeamRole(ownsTeam, false, offset, size);
			return new NeedsPaging<TeamRole>(teamsHierarchies, context);
		}
		
		teamsHierarchies = Context.getService(TeamRoleService.class).getAllTeamRole(ownsTeam,false, null, null);
		return new NeedsPaging<TeamRole>(teamsHierarchies,context);	
	}
	
}
