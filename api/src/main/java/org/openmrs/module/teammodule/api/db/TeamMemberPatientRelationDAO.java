package org.openmrs.module.teammodule.api.db;

import java.util.List;

import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamLead;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.TeamMemberPatientRelation;

public interface TeamMemberPatientRelationDAO {

	public void save(TeamMemberPatientRelation tpr);

	public List<TeamMemberPatientRelation> getTeamPatientRelation(int tpr);

	public List<TeamMemberPatientRelation> getTeamPatientRelations(Integer Id);

	public TeamMemberPatientRelation getTeamPatientRelation(TeamMemberPatientRelation tpr);

	public List<TeamMemberPatientRelation> getTeamPatientRelations(TeamMemberPatientRelation tpr);

	public void update(TeamMemberPatientRelation tpr);
	
	public void purgeTeamPatientRelation(TeamMemberPatientRelation tpr);
	
	public TeamMemberPatientRelation getTeamPatientRelation(String name);
	
	public List<TeamMemberPatientRelation> searchTeamPatientRelation(String name);

	public List<TeamMemberPatientRelationDAO> getTeamPatientRelations();
}
