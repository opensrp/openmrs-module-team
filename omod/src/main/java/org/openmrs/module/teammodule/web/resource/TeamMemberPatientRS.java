package org.openmrs.module.teammodule.web.resource;

import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.TeamMemberPatientRelation;
import org.openmrs.module.teammodule.api.TeamMemberPatientRelationService;
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
@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teamMemberPatient", supportedClass = TeamMemberPatientRelation.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*",
		"1.12.*" })
public class TeamMemberPatientRS extends DataDelegatingCrudResource<TeamMemberPatientRelation> {

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;
		if (Context.isAuthenticated()) {
			
			description = new DelegatingResourceDescription();
				//description.addProperty("display");
				description.addProperty("teamMemberPatientId");
				//description.addProperty("teamMember");
				description.addProperty("patient");
				description.addProperty("dateCreated");
		}

		return description;
	}

	@Override
	public TeamMemberPatientRelation newDelegate() {
		return new TeamMemberPatientRelation();
	}

	@Override
	public TeamMemberPatientRelation save(TeamMemberPatientRelation teamPatient) {
		return null;
	}

	@Override
	protected void delete(TeamMemberPatientRelation teamPatient, String reason, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void purge(TeamMemberPatientRelation arg0, RequestContext arg1) throws ResponseException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public SimpleObject search(RequestContext context) {
		List<TeamMemberPatientRelation> listTeam = Context.getService(TeamMemberPatientRelationService.class).searchTeamPatientRelation(context.getParameter("q"));
		return new NeedsPaging<TeamMemberPatientRelation>(listTeam, context).toSimpleObject(this);
	}
	
	@PropertyGetter("display")
	public List<TeamMemberPatientRelation> getDisplayString(String teamPatient) {
		if (teamPatient == null){		
			return null;
		}
		
		return Context.getService(TeamMemberPatientRelationService.class).searchTeamPatientRelation(teamPatient);
	}
	

	@Override
	public TeamMemberPatientRelation getByUniqueId(String uniqueId) {
		TeamMemberPatientRelation tml = Context.getService(TeamMemberPatientRelationService.class).getTeamPatientRelation(uniqueId);
		return tml;
	}
}

