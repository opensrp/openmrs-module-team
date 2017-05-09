package org.openmrs.module.teammodule.web.resource;

import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamHierarchy;
import org.openmrs.module.teammodule.api.TeamHierarchyService;
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
 * @author Shakeeb raza
 * 
 */
@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teamhierarchy", supportedClass = TeamHierarchy.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*",
		"1.12.*" })
public class TeamHierarchyRequestResource extends DataDelegatingCrudResource<TeamHierarchy> {

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;
		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
		if (rep instanceof DefaultRepresentation) {
			description.addProperty("display");
			description.addProperty("uuid");
			description.addProperty("ownsTeam");
			description.addProperty("reportTo");
			} else if (rep instanceof FullRepresentation) {
			description.addProperty("display");
			description.addProperty("name");
			description.addProperty("uuid");
			description.addProperty("ownsTeam");
			description.addProperty("reportTo");
			description.addProperty("auditInfo");
			}
		}

		return description;
	}

	@Override
	public TeamHierarchy newDelegate() {
		return new TeamHierarchy();
	}

	@Override
	public TeamHierarchy save(TeamHierarchy teamHierarchy) {
		Context.getService(TeamHierarchyService.class).saveTeamHierarchy(teamHierarchy);
		return teamHierarchy;
	}

	@Override
	protected void delete(TeamHierarchy teamHierarchy, String reason, RequestContext context) throws ResponseException {

		Context.getService(TeamHierarchyService.class).purgeTeamRole(teamHierarchy);
	}
	
	@Override
	public void purge(TeamHierarchy teamHierarchy, RequestContext arg1) throws ResponseException {
		Context.getService(TeamHierarchyService.class).purgeTeamRole(teamHierarchy);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SimpleObject search(RequestContext context) {
		if(context.getParameter("q")!=null)
		{
		List<TeamHierarchy> listTeam = Context.getService(TeamHierarchyService.class).searchTeamRoleByRole(context.getParameter("q"));
		return new NeedsPaging<TeamHierarchy>(listTeam, context).toSimpleObject(this);
		}
		else if(context.getParameter("team")!=null)
		{
			List<TeamHierarchy> listTeamHierarchy = (List<TeamHierarchy>) Context.getService(TeamHierarchyService.class).getTeamRoleById(Integer.parseInt(context.getParameter("id")));
			return new NeedsPaging<TeamHierarchy>(listTeamHierarchy, context).toSimpleObject(this);
		}
		return null;
		}
	
	
	@PropertyGetter("display")
	public String getDisplayString(TeamHierarchy teamHierarchy) {
		return teamHierarchy.getName();
	}

	@Override
	public TeamHierarchy getByUniqueId(String uniqueId) {
		return Context.getService(TeamHierarchyService.class).getTeamRoleByUuid(uniqueId);
	}
	
	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		
		List<TeamHierarchy> teamsHierarchies = Context.getService(TeamHierarchyService.class).getAllTeamHierarchy();
		return new NeedsPaging<TeamHierarchy>(teamsHierarchies,context).toSimpleObject(this);	
	}
}
