/**
 * 
 */
package org.openmrs.module.teammodule.web.resource;

import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
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
 * @author Muhammad Safwan and Shakeeb raza
 * 
 */
@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/team", supportedClass = Team.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*",
		"1.12.*" })
public class TeamRequestResource extends DataDelegatingCrudResource<Team> {

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;

		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
			if (rep instanceof DefaultRepresentation) {
				description.addProperty("display");
				description.addProperty("teamIdentifier");
				description.addProperty("teamName");
				description.addProperty("uuid");
				description.addProperty("dateCreated");
			} else if (rep instanceof FullRepresentation) {
				description.addProperty("display");
				description.addProperty("teamId");
				description.addProperty("teamIdentifier");
				description.addProperty("teamName");
				description.addProperty("uuid");
				description.addProperty("dateCreated");
			}
		}
		return description;
	}

	@Override
	public Team newDelegate() {
		return new Team();
	}

	@Override
	public Team save(Team team) {
		return null;
	}

	@Override
	protected void delete(Team team, String reason, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub
	}

	@Override
	public Team getByUniqueId(String uuid) {
		return Context.getService(TeamService.class).getTeam(uuid);
	}

	@Override
	public void purge(Team arg0, RequestContext arg1) throws ResponseException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public SimpleObject search(RequestContext context) {
		List<Team> listTeam = Context.getService(TeamService.class).searchTeam(context.getParameter("q"));
		return new NeedsPaging<Team>(listTeam, context).toSimpleObject(this);
	}
	
	@PropertyGetter("display")
	public String getDisplayString(Team team) {
		if (team == null){		
			return "";
		}
		return team.getTeamName();
	}
}
