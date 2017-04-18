package org.openmrs.module.teammodule.web.resource;

import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.TeamLog;
import org.openmrs.module.teammodule.api.TeamLogService;
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
 * @author Muhammad Safwan and Shakeeb raza
 * 
 */
@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teamlog", supportedClass = TeamLog.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*",
		"1.12.*" })
public class TeamLogRS extends DataDelegatingCrudResource<TeamLog> {

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;
		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
				description.addProperty("logId");
				description.addProperty("team");
				description.addProperty("action");
				description.addProperty("dataNew");
				description.addProperty("log");
				description.addProperty("dateCreated");
		}

		return description;
	}

	@Override
	public TeamLog newDelegate() {
		return new TeamLog();
	}

	@Override
	public TeamLog save(TeamLog teamLog) {
		return null;
	}

	@Override
	protected void delete(TeamLog teamLog, String reason, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub
	}

	public TeamLog getByUniqueId(int id) {
		return Context.getService(TeamLogService.class).getTeamLog(id);
	}

	@Override
	public void purge(TeamLog arg0, RequestContext arg1) throws ResponseException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public SimpleObject search(RequestContext context) {
		List<TeamLog> listTeamLog = Context.getService(TeamLogService.class).searchTeamLogByTeam(Integer.parseInt(context.getParameter("q")));
		return new NeedsPaging<TeamLog>(listTeamLog, context).toSimpleObject(this);
	}
	
	@PropertyGetter("display")
	public List<TeamLog> getDisplayString(int team) {
		return Context.getService(TeamLogService.class).searchTeamLogByTeam(team);
	}

	@Override
	public TeamLog getByUniqueId(String uniqueId) {
		// TODO Auto-generated method stub
		return Context.getService(TeamLogService.class).getTeamLog(uniqueId);
	}
}
