package org.openmrs.module.teammodule.web.resource;

import java.util.List;

import org.openmrs.PersonName;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.TeamMemberLog;
import org.openmrs.module.teammodule.TeamRoleLog;
import org.openmrs.module.teammodule.api.TeamMemberLogService;
import org.openmrs.module.teammodule.api.TeamMemberService;
import org.openmrs.module.teammodule.api.TeamRoleLogService;
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
 * @author Zohaib Masood
 * 
 */
@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teammemberlog", supportedClass = TeamMemberLog.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*", "1.12.*", "2.0.*", "2.1.*" })
public class TeamMemberLogRequestResource extends DataDelegatingCrudResource<TeamMemberLog> {

	@Override
	public TeamMemberLog newDelegate() {
		return new TeamMemberLog();
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;
		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
			if (rep instanceof DefaultRepresentation) {
				description.addProperty("display");
				description.addProperty("teamMember");
				description.addProperty("action");
				description.addProperty("dataNew");
				description.addProperty("dataOld");
				description.addProperty("uuid");
				description.addProperty("log");
			} else if (rep instanceof FullRepresentation) {
				description.addProperty("display");
				description.addProperty("teamMember");
				description.addProperty("action");
				description.addProperty("dataNew");
				description.addProperty("dataOld");
				description.addProperty("log");
				description.addProperty("uuid");
				description.addProperty("auditInfo");
				description.addSelfLink();
			}
		}
		return description;
	}
	
	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("teamMember");
		description.addProperty("action");
		description.addProperty("dataNew");
		description.addProperty("dataOld");
		description.addProperty("log");
		return description;
	}
	
	@Override
	public DelegatingResourceDescription getUpdatableProperties()  {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("teamMember");
		description.addProperty("action");
		description.addProperty("dataNew");
		description.addProperty("dataOld");
		description.addProperty("log");
		return description;
	}
	
	@Override
	public TeamMemberLog save(TeamMemberLog delegate) {
		try {
			if(delegate.getId() != null && delegate.getId() > 0) { Context.getService(TeamMemberLogService.class).updateTeamMemberLog(delegate); return delegate; }
			else { Context.getService(TeamMemberLogService.class).saveTeamMemberLog(delegate); return delegate; }
		}
		catch(Exception e) { e.printStackTrace(); throw new RuntimeException(e); }
	}

	@Override
	protected void delete(TeamMemberLog delegate, String reason, RequestContext context) throws ResponseException {
		Context.getService(TeamMemberLogService.class).purgeTeamMemberLog(delegate);
	}

	@Override
	public void purge(TeamMemberLog delegate, RequestContext arg1) throws ResponseException {
		Context.getService(TeamMemberLogService.class).purgeTeamMemberLog(delegate);
	}
	
	@Override
	public SimpleObject search(RequestContext context) {
		if(context.getParameter("q") != null) {
			TeamMember teamMember = Context.getService(TeamMemberService.class).getTeamMemberByUuid(context.getParameter("q"));
			List<TeamMemberLog> teamMemberLogs = Context.getService(TeamMemberLogService.class).searchTeamMemberLogByTeamMember(teamMember.getId(),null,null);
			return new NeedsPaging<TeamMemberLog>(teamMemberLogs, context).toSimpleObject(this);
		} else { return null; }
	}
	
	@PropertyGetter("display")
	public PersonName getDisplayString(TeamMemberLog teamMemberLog) {
		 if (teamMemberLog.getTeamMember().getPerson() == null) {
			return teamMemberLog.getTeamMember().getPerson().getPersonName();
		}
		 return null;
	}
	
	@Override
	public TeamMemberLog getByUniqueId(String uuid) {
		TeamMemberLog teamMemberLog = Context.getService(TeamMemberLogService.class).getTeamMemberLogByUUID(uuid);
		if(teamMemberLog != null) { return teamMemberLog; }
		else { return null; }
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<TeamMemberLog> teamMemberLogs;
		Integer offset=null, size=null;
	
		if(context.getParameter("offset")!=null && context.getParameter("size")!=null)
		{
			offset=Integer.parseInt(context.getParameter("offset"));
			size=Integer.parseInt(context.getParameter("size"));
		}
		teamMemberLogs = Context.getService(TeamMemberLogService.class).getAllLogs(offset, size);
		return new NeedsPaging<TeamMemberLog>(teamMemberLogs, context);	
	}
	
}

