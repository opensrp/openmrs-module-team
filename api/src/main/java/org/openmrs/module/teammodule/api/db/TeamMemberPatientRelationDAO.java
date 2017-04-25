package org.openmrs.module.teammodule.api.db;

import java.util.List;

import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.TeamMemberPatientRelation;

public interface TeamMemberPatientRelationDAO {

	public void saveTeamMemberPatientRelation(TeamMemberPatientRelation tpr);
	
	public TeamMemberPatientRelation getTeamMemberPatientRelation(int tpr);

	public void updateTeamMemberPatientRelation(TeamMemberPatientRelation tpr);
	
	public void purgeTeamMemberPatientRelation(TeamMemberPatientRelation tpr);
	
	public TeamMemberPatientRelation getTeamMemberPatientRelation(String uuid);

	public TeamMemberPatientRelation getTeamMemberPatientRelations(TeamMemberPatientRelation tmpr);

	public List<TeamMemberPatientRelation> getTeamMemberPatientRelations(TeamMember tmpr);
}
