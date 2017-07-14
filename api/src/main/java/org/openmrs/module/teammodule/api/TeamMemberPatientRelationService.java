package org.openmrs.module.teammodule.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.TeamMemberPatientRelation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TeamMemberPatientRelationService extends OpenmrsService {

	public void saveTeamMemberPatientRelation(TeamMemberPatientRelation teamMemberPatientRelation);

	public void purgeTeamMemberPatientRelation(TeamMemberPatientRelation teamMemberPatientRelation);

	public TeamMemberPatientRelation getTeamMemberPatientRelation(Integer id);
	
	public TeamMemberPatientRelation getTeamMemberPatientRelation(String uuid);

	public void updateTeamMemberPatientRelation(TeamMemberPatientRelation teamMemberPatientRelation);

	public List<TeamMemberPatientRelation> getTeamMemberPatientRelations(TeamMember teamMember);
}
