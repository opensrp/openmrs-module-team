package org.openmrs.module.teammodule.api.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.TeamMemberPatientRelation;
import org.openmrs.module.teammodule.api.TeamMemberPatientRelationService;
import org.openmrs.module.teammodule.api.db.TeamMemberPatientRelationDAO;

public class TeamMemberPatientRelationServiceImpl extends BaseOpenmrsService implements TeamMemberPatientRelationService {
	
	@SuppressWarnings("unused")
	private final Log log = LogFactory.getLog(this.getClass());
	
	private TeamMemberPatientRelationDAO dao;	
	
	public TeamMemberPatientRelationDAO getDao() {
		return dao;
	}

	public void setDao(TeamMemberPatientRelationDAO dao) {
		this.dao = dao;
	}

	public void saveTeamMemberPatientRelation(TeamMemberPatientRelation tmpr) {
		 dao.saveTeamMemberPatientRelation(tmpr);
	}
	
	public void purgeTeamMemberPatientRelation(TeamMemberPatientRelation tmpr) {
		 dao.purgeTeamMemberPatientRelation(tmpr);
	}
	
	public TeamMemberPatientRelation getTeamMemberPatientRelation(Integer id) {
		return dao.getTeamMemberPatientRelation(id);
	}

	public TeamMemberPatientRelation getTeamMemberPatientRelation(String uuid) {
		return dao.getTeamMemberPatientRelation(uuid);
	}

	public void updateTeamMemberPatientRelation(TeamMemberPatientRelation tmpr) {
		dao.updateTeamMemberPatientRelation(tmpr);
	}
	
	public TeamMemberPatientRelation getTeamMemberPatientRelations(TeamMemberPatientRelation id) {
		return dao.getTeamMemberPatientRelations(id);
	}
	
	public List<TeamMemberPatientRelation> getTeamMemberPatientRelations(TeamMember tm) {
		return dao.getTeamMemberPatientRelations(tm);
	}
	
}