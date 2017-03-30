package org.openmrs.module.teammodule.web.resource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamHierarchy;
import org.openmrs.module.teammodule.TeamLocation;
import org.openmrs.module.teammodule.api.TeamHierarchyService;
import org.openmrs.module.teammodule.api.TeamLocationService;
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
@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teamLocation", supportedClass = TeamLocation.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*",
		"1.12.*" })
public class TeamLocationRS extends DataDelegatingCrudResource<TeamLocation> {

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;
		if (Context.isAuthenticated()) {
			
			description = new DelegatingResourceDescription();
				description.addProperty("display");
				description.addProperty("teamLocationId");
				description.addProperty("team");
				description.addProperty("location");
				description.addProperty("dateCreated");
		}

		return description;
	}

	@Override
	public TeamLocation newDelegate() {
		return new TeamLocation();
	}

	@Override
	public TeamLocation save(TeamLocation teamLocation) {
		return null;
	}

	@Override
	protected void delete(TeamLocation teamLocation, String reason, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub
	}

	public TeamLocation getByUniqueId(int id) {
		return Context.getService(TeamLocationService.class).getTeamLocation(id);
	}

	@Override
	public void purge(TeamLocation arg0, RequestContext arg1) throws ResponseException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public SimpleObject search(RequestContext context) {
		List<TeamLocation> listTeam = Context.getService(TeamLocationService.class).searchLocationByLocation(context.getParameter("q"));
		return new NeedsPaging<TeamLocation>(listTeam, context).toSimpleObject(this);
	}
	
	@PropertyGetter("display")
	public List<TeamLocation> getDisplayString(String teamLocation) {
		if (teamLocation == null){		
			return null;
		}
		
		return Context.getService(TeamLocationService.class).searchLocationByLocation(teamLocation);
	}

	@Override
	public TeamLocation getByUniqueId(String uniqueId) {
		// TODO Auto-generated method stub
		return Context.getService(TeamLocationService.class).getTeamLocation(Integer.parseInt(uniqueId));
	}
}

