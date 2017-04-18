package org.openmrs.module.teammodule.web.resource;

import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.TeamMemberLog;
import org.openmrs.module.teammodule.api.TeamMemberLogService;
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
 * @author Zohaib Masood
 * 
 */
@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teammemberlog", supportedClass = TeamMemberLog.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*",
		"1.12.*" })
public class TeamMemberLogRS extends DataDelegatingCrudResource<TeamMemberLog> {

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;
		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
				description.addProperty("logId");
				description.addProperty("teamMember");
				description.addProperty("action");
				description.addProperty("dataNew");
				description.addProperty("dataOld");
				description.addProperty("log");
				description.addProperty("dateCreated");
		}

		return description;
	}

	@Override
	public TeamMemberLog newDelegate() {
		return new TeamMemberLog();
	}

	@Override
	public TeamMemberLog save(TeamMemberLog teamLog) {
		return null;
	}

	@Override
	protected void delete(TeamMemberLog teamMemberLog, String reason, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub
	}

	@Override
	public void purge(TeamMemberLog arg0, RequestContext arg1) throws ResponseException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public SimpleObject search(RequestContext context) {
		List<TeamMemberLog> listTeamMemberLog = Context.getService(TeamMemberLogService.class).searchTeamMemberLogByTeamMember(Integer.parseInt(context.getParameter("q")));
		return new NeedsPaging<TeamMemberLog>(listTeamMemberLog, context).toSimpleObject(this);
	}
	
	@PropertyGetter("display")
	public List<TeamMemberLog> getDisplayString(int team) {
		return Context.getService(TeamMemberLogService.class).searchTeamMemberLogByTeamMember(team);
	}

	@Override
	public TeamMemberLog getByUniqueId(String uniqueId) {
		return Context.getService(TeamMemberLogService.class).getTeamMemberLog(uniqueId);
	}
	
	public TeamMemberLog getById(Integer id) {
		return Context.getService(TeamMemberLogService.class).getTeamMemberLog(id);
	}
}

