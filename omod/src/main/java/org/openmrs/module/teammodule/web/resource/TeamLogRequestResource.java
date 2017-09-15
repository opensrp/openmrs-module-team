package org.openmrs.module.teammodule.web.resource;

import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamLog;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.TeamMemberLog;
import org.openmrs.module.teammodule.api.TeamLogService;
import org.openmrs.module.teammodule.api.TeamMemberLogService;
import org.openmrs.module.teammodule.api.TeamMemberService;
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
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

/**
 * @author Muhammad Safwan and Shakeeb raza
 * 
 */
@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teamlog", supportedClass = TeamLog.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*", "1.12.*", "2.0.*", "2.1.*" })
public class TeamLogRequestResource extends DataDelegatingCrudResource<TeamLog> {

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;
		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
			if (rep instanceof DefaultRepresentation) {
				description.addProperty("display");
				description.addProperty("team");
				description.addProperty("action");
				description.addProperty("dataNew");
				description.addProperty("dataOld");
				description.addProperty("uuid");
				description.addProperty("log");
			} else if (rep instanceof FullRepresentation) {
				description.addProperty("display");
				description.addProperty("team");
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
		description.addProperty("team");
		description.addProperty("action");
		description.addProperty("dataNew");
		description.addProperty("dataOld");
		description.addProperty("log");
		return description;
	}
	
	@Override
	public DelegatingResourceDescription getUpdatableProperties()  {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("team");
		description.addProperty("action");
		description.addProperty("dataNew");
		description.addProperty("dataOfld");
		description.addProperty("log");
		return description;
	}
	
	@Override
	public TeamLog newDelegate() {
		return new TeamLog();
	}

	@Override
	public TeamLog save(TeamLog teamLog) {
		try {
			if (teamLog.getId() != null && teamLog.getId() > 0) {
				Context.getService(TeamLogService.class).updateTeamLog(teamLog);
				return teamLog;
			} else {
				Context.getService(TeamLogService.class).saveTeamLog(teamLog);
				return teamLog;
			}
	}
		catch(Exception e) { e.printStackTrace(); throw new RuntimeException(e); }
	}

	@Override
	protected void delete(TeamLog teamLog, String reason, RequestContext context) throws ResponseException {
		Context.getService(TeamLogService.class).purgeTeamLog(teamLog);
	}

	@Override
	public void purge(TeamLog teamLog, RequestContext arg1) throws ResponseException {
		Context.getService(TeamLogService.class).purgeTeamLog(teamLog);
	}
	
	@Override
	public SimpleObject search(RequestContext context) {
		Integer offset=null,size=null;
		if (context.getParameter("q") != null) {
			Team team = Context.getService(TeamService.class).getTeamByUuid(context.getParameter("q"));
			if(context.getParameter("offset")!=null && context.getParameter("size")!=null)
			{
				offset=Integer.parseInt(context.getParameter("offset"));
				size=Integer.parseInt(context.getParameter("size"));
			}
			List<TeamLog> teamLogs = Context.getService(TeamLogService.class).searchTeamLogByTeam(team.getId(), offset, size);
			return new NeedsPaging<TeamLog>(teamLogs, context).toSimpleObject(this);
		} 
	
		return null;
	}
	
	@PropertyGetter("display")
	public String getDisplayString(TeamLog teamLog) {
		if (teamLog == null) {
			return "";
		} else if (teamLog.getTeam() == null) {
			return teamLog.getAction();
		} else {
			return teamLog.getTeam().getTeamName() + " : "+teamLog.getAction();
		}
	}
	
	@Override
	public TeamLog getByUniqueId(String uuid) {
		return Context.getService(TeamLogService.class).getTeamLogByUUID(uuid);
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<TeamLog> teamLogs;
		if(context.getParameter("offset")!=null && context.getParameter("size")!=null)
		{
			int offset=Integer.parseInt(context.getParameter("offset"));
			int size=Integer.parseInt(context.getParameter("size"));
			teamLogs = Context.getService(TeamLogService.class).getAllLogs(offset, size);
			return new NeedsPaging<TeamLog>(teamLogs, context);	
		}
		
		teamLogs = Context.getService(TeamLogService.class).getAllLogs(null, null);
		return new NeedsPaging<TeamLog>(teamLogs, context);		
	}
}
