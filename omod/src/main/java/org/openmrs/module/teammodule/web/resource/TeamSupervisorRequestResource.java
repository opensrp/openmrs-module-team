/**
 * 
 */
package org.openmrs.module.teammodule.web.resource;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.TeamSupervisor;
import org.openmrs.module.teammodule.api.TeamSupervisorService;
import org.openmrs.module.teammodule.rest.v1_0.resource.TeamModuleResourceController;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

/**
 * @author Muhammad Safwan
 *
 */

@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teamSupervisor", supportedClass = TeamSupervisor.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*",
"1.12.*" })
public class TeamSupervisorRequestResource extends DelegatingCrudResource<TeamSupervisor> {

	@Override
	public TeamSupervisor newDelegate() {
		return new TeamSupervisor();
	}

	@Override
	public TeamSupervisor save(TeamSupervisor delegate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;

		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
			if (rep instanceof DefaultRepresentation) {
				description.addProperty("teamSupervisorId");
				description.addProperty("team");
				description.addProperty("teamMember");
				description.addProperty("uuid");	
			} else if (rep instanceof FullRepresentation) {
				description.addProperty("teamSupervisorId");
				description.addProperty("team");
				description.addProperty("teamMember");
				description.addProperty("uuid");
			}
		}
		
		return description;
	}

	@Override
	public TeamSupervisor getByUniqueId(String uuid) {
		return Context.getService(TeamSupervisorService.class).getTeamSupervisor(uuid);
	}

	@Override
	protected void delete(TeamSupervisor delegate, String reason, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void purge(TeamSupervisor delegate, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub
		
	}
		
	/*@Override
	public SimpleObject search(RequestContext context) {
		List<TeamSupervisor> SupervisorList = Context.getService(TeamSupervisorService.class).searchTeam(context.getParameter("q"));
		return new NeedsPaging<TeamSupervisor>(SupervisorList, context).toSimpleObject(this);
	}*/
	

}
