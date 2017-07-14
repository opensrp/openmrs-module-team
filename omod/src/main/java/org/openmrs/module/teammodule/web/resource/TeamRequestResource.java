/**
 * 
 */
package org.openmrs.module.teammodule.web.resource;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.Location;
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
@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/team", supportedClass = Team.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*", "1.12.*", "2.0.*", "2.1.*" })
public class TeamRequestResource extends DataDelegatingCrudResource<Team> {
	
	@Override
	public Team newDelegate() {
		return new Team();
	}

	@Override
	public Team save(Team delegate) {
		try {
			if(delegate.getId() != null && delegate.getId() > 0) { Context.getService(TeamService.class).updateTeam(delegate); return delegate; }
			else { Context.getService(TeamService.class).saveTeam(delegate); return delegate; }
		}
		catch(Exception e) { e.printStackTrace(); throw new RuntimeException(e); }
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
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
				description.addProperty("voided");
				description.addProperty("voidReason");
				description.addProperty("members");
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
				description.addProperty("voided");
				description.addProperty("voidReason");
				description.addProperty("location", Representation.FULL);
				description.addProperty("members");
				description.addProperty("auditInfo");
				description.addSelfLink();
			}
		} return description;
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
	public DelegatingResourceDescription getUpdatableProperties()  {
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
	protected void delete(Team team, String reason, RequestContext context) throws ResponseException {
		Context.getService(TeamService.class).purgeTeam(team);
	}

	@Override
	public Team getByUniqueId(String uuid) {
		return Context.getService(TeamService.class).getTeam(uuid);
	}

	@Override
	public void purge(Team team, RequestContext arg1) throws ResponseException {
		Context.getService(TeamService.class).purgeTeam(team);
	}
	
	@ResponseBody
	@Override
	public SimpleObject search(RequestContext context) {
		if(context.getParameter("q") != null) {
			List<Team> listTeam = Context.getService(TeamService.class).searchTeam(context.getParameter("q"));
			return new NeedsPaging<Team>(listTeam, context).toSimpleObject(this);
		}
		else if(context.getParameter("location")!=null) {
			Location location = Context.getLocationService().getLocation(Integer.parseInt(context.getParameter("location")));
			List<Team> listTeam = Context.getService(TeamService.class).getTeamByLocation(location.getId(), 0,20);
			return new NeedsPaging<Team>(listTeam, context).toSimpleObject(this);
		}
		else if(context.getParameter("supervisor")!=null) {
			TeamMember supervisor = Context.getService(TeamMemberService.class).getTeamMember(Integer.parseInt(context.getParameter("supervisor")));
			Team team =  Context.getService(TeamService.class).getTeamBySupervisor(supervisor.getId());
			List<Team> listTeam = new ArrayList<>();
			listTeam.add(team);
			return new NeedsPaging<Team>(listTeam, context).toSimpleObject(this);
		}
		else if(context.getParameter("supervisorId")!=null && context.getParameter("teamName")!=null && context.getParameter("locationId")!=null && context.getParameter("identifier")!=null && context.getParameter("voided")!=null && context.getParameter("voidReason")!=null) {
			Team team = (Context.getService(TeamService.class).searchTeam(context.getParameter("teamName"))).get(0);
			team.setTeamIdentifier(context.getParameter("identifier"));
			if(context.getParameter("voided").equals("true")) { team.setVoided(true); team.setVoidReason(context.getParameter("voidReason")); }
			if(context.getParameter("supervisorId") != "0") { team.setSupervisor(Context.getService(TeamMemberService.class).getTeamMemberByUuid(context.getParameter("supervisorId"))); }
			team.setTeamIdentifier(context.getParameter("identifier"));
			team.setLocation(Context.getLocationService().getLocationByUuid(context.getParameter("locationId")));
			Context.getService(TeamService.class).updateTeam(team);
			List<Team> listTeam = new ArrayList<>();
			listTeam.add(team);
			return new NeedsPaging<Team>(listTeam, context).toSimpleObject(this);
		}
		else if(context.getParameter("supervisorId")!=null && context.getParameter("teamName")!=null && context.getParameter("locationId")!=null && context.getParameter("identifier")!=null) {
			Team team = (Context.getService(TeamService.class).searchTeam(context.getParameter("teamName"))).get(0);
			if(context.getParameter("supervisorId") != "0") { team.setSupervisor(Context.getService(TeamMemberService.class).getTeamMemberByUuid(context.getParameter("supervisorId"))); }
			team.setTeamIdentifier(context.getParameter("identifier"));
			team.setLocation(Context.getLocationService().getLocationByUuid(context.getParameter("locationId")));
			Context.getService(TeamService.class).saveTeam(team);
			List<Team> listTeam = new ArrayList<>();
			listTeam.add(team);
			return new NeedsPaging<Team>(listTeam, context).toSimpleObject(this);
		}
		else if(context.getParameter("teamName")!=null && context.getParameter("locationId")!=null && context.getParameter("identifier")!=null && context.getParameter("voided")!=null && context.getParameter("voidReason")!=null) {
			Team team = (Context.getService(TeamService.class).searchTeam(context.getParameter("teamName"))).get(0);
			team.setTeamIdentifier(context.getParameter("identifier"));
			if(context.getParameter("voided").equals("true")) { team.setVoided(true); team.setVoidReason(context.getParameter("voidReason")); }
			team.setTeamIdentifier(context.getParameter("identifier"));
			team.setLocation(Context.getLocationService().getLocationByUuid(context.getParameter("locationId")));
			Context.getService(TeamService.class).updateTeam(team);
			List<Team> listTeam = new ArrayList<>();
			listTeam.add(team);
			return new NeedsPaging<Team>(listTeam, context).toSimpleObject(this);
		}
		else if(context.getParameter("teamName")!=null && context.getParameter("locationId")!=null && context.getParameter("identifier")!=null) {
			Team team = (Context.getService(TeamService.class).searchTeam(context.getParameter("teamName"))).get(0);
			team.setTeamIdentifier(context.getParameter("identifier"));
			team.setLocation(Context.getLocationService().getLocationByUuid(context.getParameter("locationId")));
			Context.getService(TeamService.class).updateTeam(team);
			List<Team> listTeam = new ArrayList<>();
			listTeam.add(team);
			return new NeedsPaging<Team>(listTeam, context).toSimpleObject(this);
		}
		else if(context.getParameter("teamName")!=null && context.getParameter("locationId")!=null) {
			Team team = Context.getService(TeamService.class).getTeam(context.getParameter("teamName"), Integer.parseInt(context.getParameter("locationId")));
			List<Team> listTeam = new ArrayList<>();
			listTeam.add(team);
			return new NeedsPaging<Team>(listTeam, context).toSimpleObject(this);
		}
		else {
			return null;
		}
	}
	
	@PropertyGetter("display")
	public String getDisplayString(Team team) {
		if (team == null) { return ""; } return team.getTeamName();
	}
	
	@PropertyGetter("members")
	public int getNumberOfMember(Team team) {
		if (team == null) { return 0; } return Context.getService(TeamMemberService.class).countTeam(team.getTeamId());
	}
	
	@PropertyGetter("supervisor")
	public String getSuperVisor(Team team) {
		if (team == null || team.getSupervisor() == null) { return ""; } return team.getSupervisor().getPerson().getPersonName().toString();
	}
	
	@PropertyGetter("supervisorUuid")
	public String getSuperVisorUuid(Team team) {
		if (team == null || team.getSupervisor() == null) { return ""; } return team.getSupervisor().getUuid();
	}

	@PropertyGetter("supervisorTeam")
	public String getSuperVisorTeam(Team team) {
		if (team == null || team.getSupervisor() == null || team.getSupervisor().getTeam() == null) { return ""; } return team.getSupervisor().getTeam().getTeamName();
	}

	@PropertyGetter("supervisorTeamUuid")
	public String getSuperVisorTeamUuid(Team team) {
		if (team == null || team.getSupervisor() == null || team.getSupervisor().getTeam() == null) { return ""; } return team.getSupervisor().getTeam().getUuid();
	}

	@PropertyGetter("supervisorIdentifier")
	public String getSuperVisorIdentifier(Team team) {
		if (team == null || team.getSupervisor() == null) { return ""; } return team.getSupervisor().getIdentifier();
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		System.out.println("doGetAll");
		List<Team> teams = Context.getService(TeamService.class).getAllTeams(false, 0, 25);
		return new NeedsPaging<Team>(teams,context);	
	}
	
	@Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		System.out.println("getAll");
		List<Team> teams = Context.getService(TeamService.class).getAllTeams(false, null, null);
		return new NeedsPaging<Team>(teams, context).toSimpleObject(this);
	}
}
