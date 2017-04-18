/**
 * 
 */
package org.openmrs.module.teammodule.web.resource;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamMemberService;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
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

//@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/memberLocation", supportedClass = TeamMemberWrapper.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*", "1.12.*" })
public class LocationRequestResource extends DelegatingCrudResource<List<TeamMember>> {

	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		
		DelegatingResourceDescription description = null;

		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
			if (rep instanceof DefaultRepresentation) {
				description.addProperty("teamMember");
				description.addProperty("user");
				description.addSelfLink();
			} else if (rep instanceof FullRepresentation) {
				description.addProperty("teamMember");
				description.addProperty("user");
				description.addSelfLink();
			}
		}
		
		return description;
		
		
	}

	
	public List<TeamMember> getByUniqueId(String uuid) {
		// TODO Auto-generated method stub
//		return null;
		TeamMember tmw = Context.getService(TeamMemberService.class).getTeamMemberByUuid(uuid);
		List<TeamMember> l = new ArrayList<>();
		l.add(tmw);
		return l;
		
//		return (List<TeamMemberWrapper>) new TeamMemberWrapper(Context.getService(TeamMemberService.class).getTeamMemberByUuid(uuid));
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
	protected void delete(List<TeamMember> arg0, String arg1, RequestContext arg2) throws ResponseException {
		// TODO Auto-generated method stub
	}

	@Override
	public void purge(List<TeamMember> arg0, RequestContext arg1) throws ResponseException {
		// TODO Auto-generated method stub
	}
	
	
	@SuppressWarnings("unused")
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
		
		List<TeamMember> result = Context.getService(TeamMemberService.class).getTeamMemberByLocationId(locationId);
		
		List<TeamMember> teamWrapper = new ArrayList<TeamMember>();
		
		/*for(int i = 0; i < result.size(); i++){
			TeamMember tmw = result.get(i);
			//get(0) to get the first person only and not the list
			try{
				User user = Context.getUserService().getUsersByPerson(result.get(i).getPerson(), true).get(0);
				if(!user.equals(null)){
					tmw.setUser(user);
				}
			} catch (Exception e){
				tmw.setUser(null);
			}

			teamWrapper.add(tmw);
		}*/
		
		return new NeedsPaging<TeamMember>(teamWrapper,context).toSimpleObject(this);
	}
	

}