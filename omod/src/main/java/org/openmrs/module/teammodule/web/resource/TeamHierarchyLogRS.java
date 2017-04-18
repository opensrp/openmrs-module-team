package org.openmrs.module.teammodule.web.resource;

import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.TeamHierarchy;
import org.openmrs.module.teammodule.TeamRoleLog;
import org.openmrs.module.teammodule.api.TeamLogService;
import org.openmrs.module.teammodule.api.TeamRoleLogService;
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
@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teamhierarchylog", supportedClass = TeamRoleLog.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*",
		"1.12.*" })
public class TeamHierarchyLogRS extends DataDelegatingCrudResource<TeamRoleLog> {

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;
		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
			description.addProperty("logId");
			description.addProperty("teamRoleId");
			description.addProperty("uuid");
			description.addProperty("action");
			description.addProperty("dataNew");
			description.addProperty("log");
			description.addProperty("dateCreated");
		}

		return description;
	}

	@Override
	public TeamRoleLog newDelegate() {
		return new TeamRoleLog();
	}

	@Override
	public TeamRoleLog save(TeamRoleLog teamRoleLog) {
		Context.getService(TeamRoleLogService.class).saveTeamRoleLog(teamRoleLog);
		return null;
	}

	@Override
	protected void delete(TeamRoleLog teamRoleLog, String reason, RequestContext context) throws ResponseException {
		Context.getService(TeamRoleLogService.class).purgeTeamRoleLog(teamRoleLog);
	}

	@Override
	public void purge(TeamRoleLog teamRoleLog, RequestContext arg1) throws ResponseException {
		Context.getService(TeamRoleLogService.class).purgeTeamRoleLog(teamRoleLog);
	}
	
	@Override
	public SimpleObject search(RequestContext context) {
		List<TeamRoleLog> listTeam = Context.getService(TeamRoleLogService.class).searchTeamRoleLogByTeamRole(context.getParameter("q"));
		return new NeedsPaging<TeamRoleLog>(listTeam, context).toSimpleObject(this);
	}
	
	@PropertyGetter("display")
	public List<TeamRoleLog> getDisplayString(String teamRole) {
		if (teamRole == null){		
			return null;
		}
		
		return Context.getService(TeamRoleLogService.class).searchTeamRoleLogByTeamRole(teamRole);
	}

	@Override
	public TeamRoleLog getByUniqueId(String uniqueId) {
		return Context.getService(TeamRoleLogService.class).getTeamRoleLog(uniqueId);
	}
}
