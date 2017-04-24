/**
 * 
 */
package org.openmrs.module.teammodule.web.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamMemberService;
import org.openmrs.module.teammodule.rest.v1_0.resource.TeamModuleResourceController;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

/**
 * @author Muhammad Safwan
 * 
 */

@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teammember", supportedClass = TeamMember.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*", "1.12.*" })
public class TeamMemberRequestResource extends DataDelegatingCrudResource<TeamMember> {

	@Override
	public TeamMember newDelegate() {
		return new TeamMember();
	}

	@Override
	public TeamMember save(TeamMember delegate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;

		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
			if (rep instanceof DefaultRepresentation) {
				description.addProperty("teamMemberId");
				description.addProperty("identifier");
				description.addProperty("isTeamLead");
				description.addProperty("person");
				description.addProperty("uuid");
				description.addProperty("location");
				description.addProperty("team");
			} else if (rep instanceof FullRepresentation) {
				description.addProperty("teamMemberId");
				description.addProperty("identifier");
				description.addProperty("isTeamLead");
				description.addProperty("person");
				description.addProperty("uuid");
				description.addProperty("location");
				description.addProperty("team");
				description.addProperty("patients", Representation.REF);
			}
		}

		return description;
	}

	@Override
	public TeamMember getByUniqueId(String uuid) {
		//System.out.println("Inside");
		Person person = Context.getPersonService().getPersonByUuid(uuid);
		//System.out.println(person);
		if (person != null) {
			Integer id = person.getPersonId();
			List<TeamMember> tm = Context.getService(TeamMemberService.class).getTeamMemberByPersonId(id);
			id = tm.get(0).getTeamMemberId();
			TeamMember member = Context.getService(TeamMemberService.class).getTeamMember(id);
			return member;
		}
		return Context.getService(TeamMemberService.class).getTeamMemberByUuid(uuid);

	}

	@Override
	protected void delete(TeamMember delegate, String reason, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void purge(TeamMember delegate, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("deprecation")
	@Override
	public SimpleObject search(RequestContext context) {
		
		/*if(context.getParameter("q") != null) {
			System.out.println(context.getParameter("q"));
			List<TeamMember> memberList = Context.getService(TeamMemberService.class).searchTeamMember(context.getParameter("q"));
			return new NeedsPaging<TeamMember>(memberList, context).toSimpleObject(this);
		}
		else {//if(context.getParameter("id") != null) {
			TeamMember memberList = Context.getService(TeamMemberService.class).getTeamMember(Integer.parseInt(context.getParameter("id")));
			List<TeamMember> temp = new ArrayList<>();
			temp.add(memberList);
			return new NeedsPaging<TeamMember>(temp, context).toSimpleObject(this);
		}
		else {
			List<TeamMember> memberList = Context.getService(TeamMemberService.class).getTeamMemberByPersonId(Integer.parseInt(context.getParameter("personId")));
			return new NeedsPaging<TeamMember>(memberList, context).toSimpleObject(this);
		}*/
		
		if(context.getParameter("identifier") != null && context.getParameter("supervisor") != null && context.getParameter("role") != null && context.getParameter("team") != null && context.getParameter("location") != null) { 
			Integer startRecord = null; 
			Integer pageSize = null;

			//Team team = Context.getService(TeamService.class).getTeam();
			//TeamHierarchy teamRole = Context.getService(TeamHierarchyService.class).getTeamRoleById(Integer.parseInt(context.getParameter("role")));
			List<TeamMember> memberList = Context.getService(TeamMemberService.class).searchTeamMember(context.getParameter("identifier"), Integer.parseInt(context.getParameter("supervisor")), Integer.parseInt(context.getParameter("role")), Integer.parseInt(context.getParameter("team")), Integer.parseInt(context.getParameter("location")), startRecord, pageSize);
			return new NeedsPaging<TeamMember>(memberList, context).toSimpleObject(this);
		}
		
		else if(context.getParameter("teamId") != null) { 
			List<TeamMember> memberList = Context.getService(TeamMemberService.class).searchTeamMemberByTeam(Integer.parseInt(context.getParameter("teamId")));
			return new NeedsPaging<TeamMember>(memberList, context).toSimpleObject(this);
		}
		else if(context.getParameter("id") != null && context.getParameter("retire") != null) { 
			boolean isRetired; if(Integer.parseInt(context.getParameter("retire")) == 0) isRetired = false; else isRetired = false;
			List<TeamMember> memberList = Context.getService(TeamMemberService.class).getAllTeamMember(Integer.parseInt(context.getParameter("id")), isRetired);
			return new NeedsPaging<TeamMember>(memberList, context).toSimpleObject(this);
		}
		else if(context.getParameter("joinFrom") != null && context.getParameter("joinTo") != null) { 
			List<TeamMember> memberList = Context.getService(TeamMemberService.class).searchTeamMember(new Date(context.getParameter("joinFrom")), new Date(context.getParameter("joinTo")), null);
			return new NeedsPaging<TeamMember>(memberList, context).toSimpleObject(this);
		}
		
		else if(context.getParameter("q") != null) { 
			List<TeamMember> memberList = Context.getService(TeamMemberService.class).searchTeamMember(null, null, context.getParameter("q"));
			return new NeedsPaging<TeamMember>(memberList, context).toSimpleObject(this);
		}
		else if(context.getParameter("id") != null) { 
			TeamMember temp = Context.getService(TeamMemberService.class).getTeamMember(Integer.parseInt(context.getParameter("id")));
			List<TeamMember> memberList = new ArrayList<>();
			memberList.add(temp);
			return new NeedsPaging<TeamMember>(memberList, context).toSimpleObject(this);
		}
		else if(context.getParameter("uuid") != null) {
			TeamMember temp = Context.getService(TeamMemberService.class).getTeamMemberByUuid(context.getParameter("uuid"));
			List<TeamMember> memberList = new ArrayList<>();
			memberList.add(temp);
			return new NeedsPaging<TeamMember>(memberList, context).toSimpleObject(this);
		}
		else if(context.getParameter("person") != null) { 
			List<TeamMember> memberList = Context.getService(TeamMemberService.class).getTeamMemberByPersonId(Integer.parseInt(context.getParameter("person")));
			return new NeedsPaging<TeamMember>(memberList, context).toSimpleObject(this);
		}
		
		
		
		
		
		else { return null; }
	}
	
}