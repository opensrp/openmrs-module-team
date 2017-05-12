package org.openmrs.module.teammodule.web.resource;

import java.util.List;

import org.apache.poi.hssf.record.formula.functions.If;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.TeamRole;
import org.openmrs.module.teammodule.TeamRoleLog;
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
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

/**
 * @author Shakeeb raza
 * 
 */
@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teamrolelog", supportedClass = TeamRoleLog.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*",
		"1.12.*" })
public class TeamRoleLogRequestResource extends DataDelegatingCrudResource<TeamRoleLog> {

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
		if(context.getParameter("q") != null)
		{
		List<TeamRoleLog> listTeam = Context.getService(TeamRoleLogService.class).searchTeamRoleLog(Integer.parseInt(context.getParameter("q")),null,null);
		return new NeedsPaging<TeamRoleLog>(listTeam, context).toSimpleObject(this);
		}
		else if( context.getParameter("teamRole")!=null)
		{
			TeamRole teamRole = Context.getService(TeamRoleService.class).getTeamRoleByUuid(context.getParameter("teamRole"));
			List<TeamRoleLog> teamRoleLogs = Context.getService(TeamRoleLogService.class).searchTeamRoleLog(teamRole.getId(),null,null);
			return new NeedsPaging<TeamRoleLog>(teamRoleLogs, context).toSimpleObject(this);
		}
		return null;
	}
	

	@Override
	public TeamRoleLog getByUniqueId(String uniqueId) {
		return Context.getService(TeamRoleLogService.class).getTeamRoleLog(uniqueId);
	}
}
