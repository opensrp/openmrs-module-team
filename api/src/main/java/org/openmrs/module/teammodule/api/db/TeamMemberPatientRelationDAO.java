package org.openmrs.module.teammodule.api.db;

import java.util.List;

import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.TeamMemberPatientRelation;

public interface TeamMemberPatientRelationDAO {

	public void saveTeamMemberPatientRelation(TeamMemberPatientRelation tpr);
	
	public TeamMemberPatientRelation getTeamMemberPatientRelation(int tpr);

	public void updateTeamMemberPatientRelation(TeamMemberPatientRelation tpr);
	
	public void purgeTeamMemberPatientRelation(TeamMemberPatientRelation tpr);
	
	public TeamMemberPatientRelation getTeamMemberPatientRelationByUUID(String uuid);

	public TeamMemberPatientRelation getTeamMemberPatientRelations(TeamMemberPatientRelation tmpr);

	public List<TeamMemberPatientRelation> getTeamMemberPatientRelations(Integer teamMember, Integer patient,
			String status,Integer location, Integer offset, Integer pageSize);
}
