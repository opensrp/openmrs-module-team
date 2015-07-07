/**
 * 
 */
package org.openmrs.module.temmodule.web.resource;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamLead;
import org.openmrs.module.teammodule.api.TeamLeadService;
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

@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/lead", supportedClass = TeamLead.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*",
"1.12.*" })
public class TeamLeadRequestResource extends DelegatingCrudResource<TeamLead> {

	@Override
	public TeamLead newDelegate() {
		return new TeamLead();
	}

	@Override
	public TeamLead save(TeamLead delegate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;

		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
			if (rep instanceof DefaultRepresentation) {
				description.addProperty("teamLeadId");
				description.addProperty("team");
				description.addProperty("teamMember");
				//description.addProperty("team");
				description.addProperty("uuid");	
				//System.out.println("Default");
			} else if (rep instanceof FullRepresentation) {
				description.addProperty("teamLeadId");
				description.addProperty("team");
				description.addProperty("teamMember");
				//description.addProperty("team");
				description.addProperty("uuid");
			}
		}
		
		return description;
	}

	@Override
	public TeamLead getByUniqueId(String uuid) {
		return Context.getService(TeamLeadService.class).getTeamLead(uuid);
	}

	@Override
	protected void delete(TeamLead delegate, String reason, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void purge(TeamLead delegate, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub
		
	}

}
