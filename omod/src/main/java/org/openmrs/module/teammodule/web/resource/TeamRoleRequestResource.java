package org.openmrs.module.teammodule.web.resource;

import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.TeamRole;
import org.openmrs.module.teammodule.api.TeamRoleService;
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
 * @author Shakeeb raza
 * 
 */
@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teamrole", supportedClass = TeamRole.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*",
		"1.12.*" })
public class TeamRoleRequestResource extends DataDelegatingCrudResource<TeamRole> {

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;
		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
		if (rep instanceof DefaultRepresentation) {
			description.addProperty("display");
			description.addProperty("uuid");
			description.addProperty("name");
			description.addProperty("ownsTeam");
			description.addProperty("reportToName");
			description.addProperty("reportByName");
			} else if (rep instanceof FullRepresentation) {
			description.addProperty("display");
			description.addProperty("name");
			description.addProperty("uuid");
			description.addProperty("ownsTeam");
			description.addProperty("reportToName");
			description.addProperty("reportByName");
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
		description.addProperty("ownsTeam");
		description.addProperty("reportToName");
		description.addProperty("reportByName");
		return description;
	}
	
	@Override
	public TeamRole newDelegate() {
		return new TeamRole();
	}

	@Override
	public TeamRole save(TeamRole delegate) {
		try {
			if(delegate.getId() != null && delegate.getId() > 0) { Context.getService(TeamRoleService.class).updateTeamRole(delegate); return delegate; }
			else { Context.getService(TeamRoleService.class).saveTeamRole(delegate); return delegate; }
		}
		catch(Exception e) { e.printStackTrace(); throw new RuntimeException(e); }
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
		if(context.getParameter("q")!=null) {
			List<TeamRole> teamRoles = Context.getService(TeamRoleService.class).searchTeamRoleByRole(context.getParameter("q"));
			System.out.println(teamRoles);
			return new NeedsPaging<TeamRole>(teamRoles, context).toSimpleObject(this);
		}
		else { return null; }
	}
	
	
	@PropertyGetter("display")
	public String getDisplayString(TeamRole teamRole) {
		if(teamRole == null) { return ""; } return teamRole.getName();
	}
	
	@PropertyGetter("reportToName")
	public String getReportToName(TeamRole teamRole) {
		if(teamRole == null || teamRole.getReportTo() == null) { return ""; } return teamRole.getReportTo().getName();
	}
	
	@PropertyGetter("reportByName")
	public List<TeamRole> getReportByName(TeamRole teamRole) {
		List<TeamRole> teamRoles = Context.getService(TeamRoleService.class).searchTeamRoleReportBy(teamRole.getTeamRoleId()); if(teamRoles == null) { return null; } else { return teamRoles; }
	}
	
	@Override
	public TeamRole getByUniqueId(String uniqueId) {
		return Context.getService(TeamRoleService.class).getTeamRoleByUuid(uniqueId);
	}
	
	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		
		List<TeamRole> teamsHierarchies = Context.getService(TeamRoleService.class).getAllTeamRole();
		return new NeedsPaging<TeamRole>(teamsHierarchies,context).toSimpleObject(this);	
	}
}
