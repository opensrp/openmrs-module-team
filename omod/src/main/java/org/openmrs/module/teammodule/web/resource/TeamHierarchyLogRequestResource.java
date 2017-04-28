package org.openmrs.module.teammodule.web.resource;

import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.TeamHierarchyLog;
import org.openmrs.module.teammodule.TeamHierarchyLog;
import org.openmrs.module.teammodule.api.TeamHierarchyLogService;
import org.openmrs.module.teammodule.api.TeamHierarchyLogService;
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
@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teamhierarchylog", supportedClass = TeamHierarchyLog.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*",
		"1.12.*" })
public class TeamHierarchyLogRequestResource extends DataDelegatingCrudResource<TeamHierarchyLog> {

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;
		
		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
		if (rep instanceof DefaultRepresentation) {
			description.addProperty("uuid");
			description.addProperty("action");
			description.addProperty("dataNew");
			description.addProperty("log");
			}
		else if (rep instanceof FullRepresentation) {
			description.addProperty("logId");
			description.addProperty("uuid");
			description.addProperty("action");
			description.addProperty("dataNew");
			description.addProperty("log");
			description.addProperty("dateCreated");
			}
		}

		return description;
	}

	@Override
	public TeamHierarchyLog newDelegate() {
		return new TeamHierarchyLog();
	}

	@Override
	public TeamHierarchyLog save(TeamHierarchyLog teamHierarchyLog) {
		Context.getService(TeamHierarchyLogService.class).saveTeamHierarchyLog(teamHierarchyLog);
		return null;
	}

	@Override
	protected void delete(TeamHierarchyLog teamHierarchyLog, String reason, RequestContext context) throws ResponseException {
		Context.getService(TeamHierarchyLogService.class).purgeTeamHierarchyLog(teamHierarchyLog);
	}

	@Override
	public void purge(TeamHierarchyLog teamHierarchyLog, RequestContext arg1) throws ResponseException {
		Context.getService(TeamHierarchyLogService.class).purgeTeamHierarchyLog(teamHierarchyLog);
	}
	
	@Override
	public SimpleObject search(RequestContext context) {
		List<TeamHierarchyLog> listTeam = Context.getService(TeamHierarchyLogService.class).searchTeamHierarchyLog(context.getParameter("q"),null,null);
		return new NeedsPaging<TeamHierarchyLog>(listTeam, context).toSimpleObject(this);
	}
	
	@PropertyGetter("display")
	public List<TeamHierarchyLog> getDisplayString(String teamHierarchy) {
		if (teamHierarchy == null){		
			return null;
		}
		
		return Context.getService(TeamHierarchyLogService.class).searchTeamHierarchyLog(teamHierarchy,null,null);
	}

	@Override
	public TeamHierarchyLog getByUniqueId(String uniqueId) {
		return Context.getService(TeamHierarchyLogService.class).getTeamHierarchyLog(uniqueId);
	}
}
