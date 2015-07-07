/**
 * 
 */
package org.openmrs.module.temmodule.web.resource;

import java.util.List;

import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamMemberService;
import org.openmrs.module.teammodule.rest.v1_0.resource.TeamModuleResourceController;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
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
				// System.out.println("Default");
			} else if (rep instanceof FullRepresentation) {
				description.addProperty("teamMemberId");
				description.addProperty("identifier");
				description.addProperty("isTeamLead");
				description.addProperty("person");
				description.addProperty("uuid");
				description.addProperty("location");
				description.addProperty("team");
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
			System.out.println("ID");
			List<TeamMember> tm = Context.getService(TeamMemberService.class).getMemberByPersonId(id);
			id = tm.get(0).getTeamMemberId();
			System.out.println(id);
			TeamMember member = Context.getService(TeamMemberService.class).getMember(id);
			return member;
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

}
