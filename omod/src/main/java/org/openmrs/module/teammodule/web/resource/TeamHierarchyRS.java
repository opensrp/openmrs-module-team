package org.openmrs.module.teammodule.web.resource;

import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.TeamHierarchy;
import org.openmrs.module.teammodule.api.TeamHierarchyService;
import org.openmrs.module.teammodule.rest.v1_0.resource.TeamModuleResourceController;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

/**
 * @author Shakeeb raza
 * 
 */
@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teamHierarchy", supportedClass = TeamHierarchy.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*",
		"1.12.*" })
public class TeamHierarchyRS extends DataDelegatingCrudResource<TeamHierarchy> {

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;
		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
			description.addProperty("display");
			description.addProperty("teamRoleId");
			description.addProperty("ownsTeam");
			description.addProperty("reportTo");
			description.addProperty("dateCreated");
		}

		return description;
	}

	@Override
	public TeamHierarchy newDelegate() {
		return new TeamHierarchy();
	}

	@Override
	public TeamHierarchy save(TeamHierarchy teamRole) {
		return null;
	}

	@Override
	protected void delete(TeamHierarchy teamRole, String reason, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub
	}

	@Override
	public void purge(TeamHierarchy arg0, RequestContext arg1) throws ResponseException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public SimpleObject search(RequestContext context) {
		List<TeamHierarchy> listTeam = Context.getService(TeamHierarchyService.class).searchTeamRoleByRole(context.getParameter("q"));
		return new NeedsPaging<TeamHierarchy>(listTeam, context).toSimpleObject(this);
	}
	
	@PropertyGetter("display")
	public List<TeamHierarchy> getDisplayString(String teamRole) {
		if (teamRole == null){		
			return null;
		}
		
		return Context.getService(TeamHierarchyService.class).searchTeamRoleByRole(teamRole);
	}

	@Override
	public TeamHierarchy getByUniqueId(String uniqueId) {
		return Context.getService(TeamHierarchyService.class).getTeamRoleByUuid(uniqueId);
	}
}

