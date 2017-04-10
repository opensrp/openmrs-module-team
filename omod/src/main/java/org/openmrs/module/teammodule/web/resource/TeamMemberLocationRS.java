package org.openmrs.module.teammodule.web.resource;

import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.TeamMemberLocation;
import org.openmrs.module.teammodule.api.TeamMemberLocationService;
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
 * @author Zohaib Masood
 * 
 */
@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teamMemberLocation", supportedClass = TeamMemberLocation.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*",
		"1.12.*" })
public class TeamMemberLocationRS extends DataDelegatingCrudResource<TeamMemberLocation> {

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;
		if (Context.isAuthenticated()) {
			
			description = new DelegatingResourceDescription();
				//description.addProperty("display");
				description.addProperty("teamMemberLocationId");
				description.addProperty("teamMember");
				description.addProperty("location");
				description.addProperty("dateCreated");
		}

		return description;
	}

	@Override
	public TeamMemberLocation newDelegate() {
		return new TeamMemberLocation();
	}

	@Override
	public TeamMemberLocation save(TeamMemberLocation teamLocation) {
		return null;
	}

	@Override
	protected void delete(TeamMemberLocation teamLocation, String reason, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void purge(TeamMemberLocation arg0, RequestContext arg1) throws ResponseException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public SimpleObject search(RequestContext context) {
		List<TeamMemberLocation> listTeam = Context.getService(TeamMemberLocationService.class).searchLocationByLocation(context.getParameter("q"));
		return new NeedsPaging<TeamMemberLocation>(listTeam, context).toSimpleObject(this);
	}
	
	@PropertyGetter("display")
	public List<TeamMemberLocation> getDisplayString(String teamLocation) {
		if (teamLocation == null){		
			return null;
		}
		
		return Context.getService(TeamMemberLocationService.class).searchLocationByLocation(teamLocation);
	}

	@Override
	public TeamMemberLocation getByUniqueId(String uniqueId) {
		TeamMemberLocation tml = Context.getService(TeamMemberLocationService.class).getTeamMemberLocation(uniqueId);
		return tml;
	}
}

