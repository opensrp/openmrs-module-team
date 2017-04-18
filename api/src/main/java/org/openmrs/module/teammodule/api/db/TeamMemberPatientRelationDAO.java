package org.openmrs.module.teammodule.api.db;

import java.util.List;

import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.TeamMemberPatientRelation;

public interface TeamMemberPatientRelationDAO {

	public void save(TeamMemberPatientRelation tpr);

	public void delete(TeamMemberPatientRelation tpr);
	
	public TeamMemberPatientRelation getTeamPatientRelation(int tpr);

	public void update(TeamMemberPatientRelation tpr);
	
	public void purgeTeamPatientRelation(TeamMemberPatientRelation tpr);
	
	public TeamMemberPatientRelation getTeamPatientRelation(String uuid);

	public TeamMemberPatientRelation getTeamPatientRelations(TeamMemberPatientRelation tmpr);

	public List<TeamMemberPatientRelation> getTeamPatientRelations(TeamMember tmpr);
}
