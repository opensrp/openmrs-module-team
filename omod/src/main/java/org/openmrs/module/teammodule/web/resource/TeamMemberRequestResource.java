/**
 * 
 */
package org.openmrs.module.teammodule.web.resource;

import java.util.List;

import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamMemberService;
import org.openmrs.module.teammodule.rest.v1_0.resource.TeamModuleResourceController;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

/**
 * @author Muhammad Safwan
 * 
 */

@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/member", supportedClass = TeamMember.class, supportedOpenmrsVersions = { "1.8.*",
		"1.9.*, 1.10.*, 1.11.*", "1.12.*" })
public class TeamMemberRequestResource extends DelegatingCrudResource<TeamMember> {

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
				description.addProperty("user");				
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
			List<TeamMember> tm = Context.getService(TeamMemberService.class).getMemberByPersonId(id);
			for (TeamMember teamMember : tm) {
				if(teamMember.isVoided() == false){
					return teamMember;
				}
			}
		}
		return Context.getService(TeamMemberService.class).getTeamMember(uuid);

	}

	@Override
	protected void delete(TeamMember delegate, String reason, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void purge(TeamMember delegate, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub

	}

	@Override
	public SimpleObject search(RequestContext context) {
		if(context.getParameter("team") != null){
			List<TeamMember> memberList = Context.getService(TeamMemberService.class).searchMemberByTeam(context.getParameter("team"));
			return new NeedsPaging<TeamMember>(memberList, context).toSimpleObject(this);
		}
		
		List<TeamMember> memberList = Context.getService(TeamMemberService.class).searchMember(context.getParameter("q"));
		return new NeedsPaging<TeamMember>(memberList, context).toSimpleObject(this);
	}
	
	@PropertyGetter("user")
	public User getUser(TeamMember member){
		List<User> user = Context.getUserService().getUsersByPerson(member.getPerson(), false);
		return user.size()>0?user.get(0):null;
	}
}
