package org.openmrs.module.teammodule.web.resource;

import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.TeamLog;
import org.openmrs.module.teammodule.TeamRole;
import org.openmrs.module.teammodule.TeamRoleLog;
import org.openmrs.module.teammodule.api.TeamLogService;
import org.openmrs.module.teammodule.api.TeamRoleLogService;
import org.openmrs.module.teammodule.api.TeamRoleService;
import org.openmrs.module.teammodule.rest.v1_0.resource.TeamModuleResourceController;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

/**
 * @author Shakeeb raza
 * 
 */
@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teamrolelog", supportedClass = TeamRoleLog.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*", "1.12.*", "2.0.*", "2.1.*" })
public class TeamRoleLogRequestResource extends DataDelegatingCrudResource<TeamRoleLog> {

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;
		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
			if (rep instanceof DefaultRepresentation) {
				description.addProperty("display");
				description.addProperty("teamRole");
				description.addProperty("action");
				description.addProperty("dataNew");
				description.addProperty("dataOld");
				description.addProperty("uuid");
				description.addProperty("log");
			} else if (rep instanceof FullRepresentation) {
				description.addProperty("display");
				description.addProperty("teamRole");
				description.addProperty("action");
				description.addProperty("dataNew");
				description.addProperty("dataOld");
				description.addProperty("uuid");
				description.addProperty("log");
				description.addProperty("auditInfo");
				description.addSelfLink();
			}
		}
		return description;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("teamRole");
		description.addProperty("action");
		description.addProperty("dataNew");
		description.addProperty("dataOld");
		description.addProperty("log");
		return description;
	}
	
	@Override
	public DelegatingResourceDescription getUpdatableProperties()  {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("teamRole");
		description.addProperty("action");
		description.addProperty("dataNew");
		description.addProperty("dataOld");
		description.addProperty("log");
		return description;
	}

	@Override
	public TeamRoleLog newDelegate() {
		return new TeamRoleLog();
	}

	@Override
	public TeamRoleLog save(TeamRoleLog teamRoleLog) {
		try {
			if (teamRoleLog.getId() != null && teamRoleLog.getId() > 0) {
				Context.getService(TeamRoleLogService.class).updateTeamRoleLog(teamRoleLog);
				return teamRoleLog;
			} else {
				Context.getService(TeamRoleLogService.class).saveTeamRoleLog(teamRoleLog);
				return teamRoleLog;
			}
		}
		catch(Exception e) { e.printStackTrace(); throw new RuntimeException(e); }
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
		if(context.getParameter("q")!=null) {
			TeamRole teamRole = Context.getService(TeamRoleService.class).getTeamRoleByUuid(context.getParameter("q"));
			List<TeamRoleLog> teamRoleLogs = Context.getService(TeamRoleLogService.class).searchTeamRoleLog(teamRole.getId(),null,null);
			return new NeedsPaging<TeamRoleLog>(teamRoleLogs, context).toSimpleObject(this);
		} 
		return null; 
	}

	@PropertyGetter("display")
	public String getDisplayString(TeamRoleLog teamRoleLog) {
		if (teamRoleLog == null) { return ""; }
		else if(teamRoleLog.getTeamRole() == null) { return ""; }
		else { return teamRoleLog.getTeamRole().getName().toString(); } 
	}
	
	@Override
	public TeamRoleLog getByUniqueId(String uuid) {
		TeamRoleLog teamRoleLog = Context.getService(TeamRoleLogService.class).getTeamRoleLogbyUuid(uuid);
		if(teamRoleLog != null) { return teamRoleLog; }
		else { return null; }
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<TeamRoleLog> teamRoleLogs;
		
		if(context.getParameter("offset")!=null && context.getParameter("size")!=null)
		{
			int offset=Integer.parseInt(context.getParameter("offset"));
			int size=Integer.parseInt(context.getParameter("size"));
			teamRoleLogs = Context.getService(TeamRoleLogService.class).getAllLogs(offset, size);
			return new NeedsPaging<TeamRoleLog>(teamRoleLogs, context);	
		}
		teamRoleLogs = Context.getService(TeamRoleLogService.class).getAllLogs(null, null);
		return new NeedsPaging<TeamRoleLog>(teamRoleLogs, context);
	}
	
}
