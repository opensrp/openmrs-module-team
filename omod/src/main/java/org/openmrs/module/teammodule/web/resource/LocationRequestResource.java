/**
 * 
 */
package org.openmrs.module.teammodule.web.resource;

import java.util.List;

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
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

/**
 * @author Muhammad Safwan
 *
 */

@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/memberLocation", supportedClass = TeamMember.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*",
"1.12.*" })
public class LocationRequestResource extends DelegatingCrudResource<List<TeamMember>> {

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
			}
		}
		
		return description;
		
		
	}

	
	public List<TeamMember> getByUniqueId(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TeamMember> newDelegate() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TeamMember> save(List<TeamMember> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void delete(List<TeamMember> arg0, String arg1,
			RequestContext arg2) throws ResponseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void purge(List<TeamMember> arg0, RequestContext arg1)
			throws ResponseException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public SimpleObject search(RequestContext context){
		String param = context.getRequest().getParameter("q");
		int locationId = 0;
		try{
			locationId = Context.getLocationService().getLocationByUuid(param).getLocationId();
		} catch(Exception e) {
			locationId = Context.getLocationService().getLocation(param).getLocationId();
			if(locationId == 0)
			return null;
		}
		
		List<TeamMember> result = Context.getService(TeamMemberService.class).getMemberByLocationId(locationId);
		
		return new NeedsPaging<TeamMember>(result,context).toSimpleObject(this);
	}
	

}
