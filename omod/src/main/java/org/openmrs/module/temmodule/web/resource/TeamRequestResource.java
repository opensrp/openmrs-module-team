/**
 * 
 */
package org.openmrs.module.temmodule.web.resource;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.api.TeamService;
import org.openmrs.module.teammodule.rest.v1_0.resource.TeamModuleResourceController;
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
 * @author Muhammad Safwan
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
				//description.addProperty("teamId");
				description.addProperty("display");
				description.addProperty("teamIdentifier");
				description.addProperty("teamName");
				description.addProperty("uuid");
				description.addProperty("dateCreated");
				description.addProperty("location");
				description.addProperty("memberCount");
				description.addSelfLink();
				description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
				//System.out.println("Default");
			} else if (rep instanceof FullRepresentation) {
				description.addProperty("display");
				description.addProperty("teamId");
				description.addProperty("teamIdentifier");
				description.addProperty("teamName");
				description.addProperty("uuid");
				description.addProperty("dateCreated");
				description.addProperty("location");
				description.addProperty("memberCount");
				description.addSelfLink();
				description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_REF);
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
		/*
		 * Context.getService(TeamService.class).saveTeam(team); return team;
		 */
		return null;
	}

	@Override
	protected void delete(Team team, String reason, RequestContext context) throws ResponseException {
		/*
		 * Context.getService(TeamService.class).getTeam(team.getTeamId());
		 * Context.getService(TeamService.class).purgeTeam(team);
		 * team.setVoidReason(reason);
		 */

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
	protected NeedsPaging<Team> doSearch(RequestContext context) {
		System.out.println("Got inside");
		System.out.println(context.getParameter("teamName"));
		List<Team> listTeam = Context.getService(TeamService.class).searchTeam(context.getParameter("teamName"));
		System.out.println(listTeam);
		//listTeam =  Context.getService(TeamService.class).searchTeam(context.getParameter("teamName"));
		return new NeedsPaging<Team>(listTeam, context);
		//return new NeedsPaging<Team>(Context.getService(TeamService.class).searchTeam(context.getParameter("teamName")), context);
	}
	
	@PropertyGetter("display")
	public String getDisplayString(Team team) {
		if (team == null){		
			return "";
		}
			
		
		return team.getTeamName();
	}

}
