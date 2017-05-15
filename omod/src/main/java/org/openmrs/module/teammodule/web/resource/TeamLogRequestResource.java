package org.openmrs.module.teammodule.web.resource;

import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamLog;
import org.openmrs.module.teammodule.api.TeamLogService;
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
@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teamlog", supportedClass = TeamLog.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*",
		"1.12.*" })
public class TeamLogRequestResource extends DataDelegatingCrudResource<TeamLog> {

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;
		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
			if (rep instanceof DefaultRepresentation) {
				description.addProperty("uuid");
				description.addProperty("team");
				description.addProperty("action");
				description.addProperty("log");
			} else if (rep instanceof FullRepresentation) {
				description.addProperty("logId");
				description.addProperty("uuid");
				description.addProperty("team");
				description.addProperty("action");
				description.addProperty("dataNew");
				description.addProperty("dataOld");
				description.addProperty("log");
				description.addProperty("auditInfo");
				description.addSelfLink();
			}
		}
		return description;
	}

	@Override
	public TeamLog newDelegate() {
		return new TeamLog();
	}

	@Override
	public TeamLog save(TeamLog teamLog) {
		Context.getService(TeamLogService.class).saveTeamLog(teamLog);
		return teamLog;
	}

	@Override
	protected void delete(TeamLog teamLog, String reason, RequestContext context) throws ResponseException {
		Context.getService(TeamLogService.class).purgeTeamLog(teamLog);
	}

	public TeamLog getByUniqueId(int id) {
		return Context.getService(TeamLogService.class).getTeamLog(id);
	}

	@Override
	public void purge(TeamLog teamLog, RequestContext arg1) throws ResponseException {
		Context.getService(TeamLogService.class).purgeTeamLog(teamLog);
	}
	
	@Override
	public SimpleObject search(RequestContext context) {
		List<Team> team = Context.getService(TeamService.class).searchTeam(context.getParameter("q"));
		List<TeamLog> listTeamLog = Context.getService(TeamLogService.class).searchTeamLogByTeam(team.get(0).getTeamId(),null,null);
		return new NeedsPaging<TeamLog>(listTeamLog, context).toSimpleObject(this);
	}
	
	@PropertyGetter("display")
	public List<TeamLog> getDisplayString(int team) {
		return Context.getService(TeamLogService.class).searchTeamLogByTeam(team,null,null);
	}

	@Override
	public TeamLog getByUniqueId(String uniqueId) {
		return Context.getService(TeamLogService.class).getTeamLog(uniqueId);
	}
}
