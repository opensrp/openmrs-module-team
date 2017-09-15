/**
 * 
 */
package org.openmrs.module.teammodule.web.resource;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.PersonName;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamMember;
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
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Muhammad Safwan and Shakeeb raza
 * 
 */
@Resource(name = RestConstants.VERSION_1
		+ TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/team", supportedClass = Team.class, supportedOpenmrsVersions = {
		"1.8.*", "1.9.*, 1.10.*, 1.11.*", "1.12.*", "2.0.*", "2.1.*" })
public class TeamRequestResource extends DataDelegatingCrudResource<Team> {

	@Override
	public Team newDelegate() {
		return new Team();
	}

	@Override
	public Team save(Team delegate) {
		try {
			if (delegate.getId() != null && delegate.getId() > 0) {
				Context.getService(TeamService.class).updateTeam(delegate);
				return delegate;
			} else {
				Context.getService(TeamService.class).saveTeam(delegate);
				return delegate;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(
			Representation rep) {
		DelegatingResourceDescription description = null;

		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
			if (rep instanceof DefaultRepresentation) {
				description.addProperty("display");
				description.addProperty("uuid");
				description.addProperty("teamName");
				description.addProperty("teamIdentifier");
				description.addProperty("supervisor");
				description.addProperty("supervisorUuid");
				description.addProperty("supervisorTeam");
				description.addProperty("supervisorTeamUuid");
				description.addProperty("supervisorIdentifier");
				description.addProperty("members");
				description.addProperty("voided");
				description.addProperty("voidReason");
				description.addProperty("location", Representation.DEFAULT);
			} else if (rep instanceof FullRepresentation) {
				description.addProperty("display");
				description.addProperty("uuid");
				description.addProperty("teamName");
				description.addProperty("teamIdentifier");
				description.addProperty("supervisor");
				description.addProperty("supervisorUuid");
				description.addProperty("supervisorTeam");
				description.addProperty("supervisorTeamUuid");
				description.addProperty("supervisorIdentifier");
				description.addProperty("members");
				description.addProperty("voided");
				description.addProperty("voidReason");
				description.addProperty("location", Representation.FULL);
				description.addProperty("auditInfo");
				description.addSelfLink();
			}
		}
		return description;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("teamName");
		description.addProperty("teamIdentifier");
		description.addProperty("supervisor");
		description.addProperty("voided");
		description.addProperty("voidReason");
		description.addProperty("location");
		return description;
	}

	@Override
	public DelegatingResourceDescription getUpdatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("teamName");
		description.addProperty("teamIdentifier");
		description.addProperty("supervisor");
		description.addProperty("voided");
		description.addProperty("voidReason");
		description.addProperty("location");
		return description;
	}

	@Override
	protected void delete(Team team, String reason, RequestContext context)
			throws ResponseException {
		Context.getService(TeamService.class).purgeTeam(team);
	}

	@Override
	public Team getByUniqueId(String uuid) {
		return Context.getService(TeamService.class).getTeamByUuid(uuid);
	}

	@Override
	public void purge(Team team, RequestContext arg1) throws ResponseException {
		Context.getService(TeamService.class).purgeTeam(team);
	}

	@ResponseBody
	@Override
	public SimpleObject search(RequestContext context) {
		Integer offset=null, size=null;
		if (context.getParameter("q") != null) {
			List<Team> listTeam = Context.getService(TeamService.class).searchTeam(context.getParameter("q"));
			return new NeedsPaging<Team>(listTeam, context).toSimpleObject(this);
		} 
		else if (context.getParameter("locationUuid") != null) {
			Location location = Context.getLocationService().getLocationByUuid(context.getParameter("locationUuid"));
			if(context.getParameter("offset") != null && context.getParameter("size") != null)
			{
				offset = Integer.parseInt(context.getParameter("offset"));
				size = Integer.parseInt(context.getParameter("size"));
			}
			List<Team> listTeam = Context.getService(TeamService.class).getTeamByLocation(location.getId(), offset, size);
			return new NeedsPaging<Team>(listTeam, context).toSimpleObject(this);
		} 
		else if (context.getParameter("supervisorUuid") != null) {
			TeamMember supervisor = Context.getService(TeamMemberService.class).getTeamMemberByUuid(context.getParameter("supervisorUuid"));
			Team team = Context.getService(TeamService.class).getTeamBySupervisor(supervisor.getId());
			List<Team> listTeam = new ArrayList<>();
			listTeam.add(team);
			return new NeedsPaging<Team>(listTeam, context).toSimpleObject(this);
		} 
		else if (context.getParameter("teamUuid") != null) {
			Team team = Context.getService(TeamService.class).getTeamByUUID(context.getParameter("teamUuid"));
			List<Team> listTeam = new ArrayList<>();
			if(context.getParameter("voided")!=null && context.getParameter("voidReason") != null)
			{
				if (context.getParameter("voided").equals("true")) {
					team.setVoided(true);
					team.setVoidReason(context.getParameter("voidReason"));
				}
			}
			if(context.getParameter("identifier")!=null)
			{
				team.setTeamIdentifier(context.getParameter("identifier"));
			}
			if(context.getParameter("locationUuid")!=null)
			{
				team.setLocation(Context.getLocationService().getLocationByUuid(context.getParameter("locationUuid")));
			}
			listTeam.add(team);
			return new NeedsPaging<Team>(listTeam, context).toSimpleObject(this);
		}
		return null;
	}

	@PropertyGetter("display")
	public String getDisplayString(Team team) {
		if (team == null) {
			return "";
		}
		return team.getTeamIdentifier()+" : "+team.getTeamName();
	}
	
	

	@PropertyGetter("members")
	public int getNumberOfMember(Team team) {
		if (team == null) {
			return 0;
		}
		return Context.getService(TeamMemberService.class).countTeamMemberByTeam(team.getTeamId());
	}
	
	@PropertyGetter("supervisor")
	public PersonName getSuperVisor(Team team) {
		if (team == null || team.getSupervisor() == null) {
			return null;
		}
		return team.getSupervisor().getPerson().getPersonName();
	}

	@PropertyGetter("supervisorUuid")
	public String getSuperVisorUuid(Team team) {
		if (team == null || team.getSupervisor() == null) {
			return "";
		}
		return team.getSupervisor().getUuid();
	}

	@PropertyGetter("supervisorTeam")
	public String getSuperVisorTeam(Team team) {
		if (team == null || team.getSupervisor() == null
				|| team.getSupervisor().getTeam() == null) {
			return "";
		}
		return team.getSupervisor().getTeam().getTeamName();
	}

	@PropertyGetter("supervisorTeamUuid")
	public String getSuperVisorTeamUuid(Team team) {
		if (team == null || team.getSupervisor() == null
				|| team.getSupervisor().getTeam() == null) {
			return "";
		}
		return team.getSupervisor().getTeam().getUuid();
	}

	@PropertyGetter("supervisorIdentifier")
	public String getSuperVisorIdentifier(Team team) {
		if (team == null || team.getSupervisor() == null) {
			return "";
		}
		return team.getSupervisor().getIdentifier();
	}

	@Override
	protected PageableResult doGetAll(RequestContext context)
			throws ResponseException {
		List<Team> teams=null;
		if(context.getParameter("offset")!=null && context.getParameter("size")!=null)
		{
			int offset=Integer.parseInt(context.getParameter("offset"));
			int size= Integer.parseInt(context.getParameter("size"));
			teams = Context.getService(TeamService.class).getAllTeams(false, offset, size);
			return new NeedsPaging<Team>(teams, context);
		}
		teams = Context.getService(TeamService.class).getAllTeams(false, null, null);
		return new NeedsPaging<Team>(teams, context);
		
	}
}
