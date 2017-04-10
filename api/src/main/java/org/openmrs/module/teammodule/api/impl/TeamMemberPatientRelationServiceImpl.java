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

	public void save(TeamMemberPatientRelation tmpr) {
		 dao.save(tmpr);
	}
	
	public void delete(TeamMemberPatientRelation tmpr) {
		 dao.delete(tmpr);
	}
	
	public void delete(int memberPatientId) {
		 dao.delete(memberPatientId);
	}
	
	public TeamMemberPatientRelation getTeamPatientRelation(Integer id) {
		return dao.getTeamPatientRelation(id);
	}

	public TeamMemberPatientRelation getTeamPatientRelation(String uuid) {
		return dao.getTeamPatientRelation(uuid);
	}

	public List<TeamMemberPatientRelation> searchTeamPatientRelation(String name) {
		return dao.searchTeamPatientRelation(name);
	}

	public void update(TeamMemberPatientRelation tmpr) {
		dao.update(tmpr);
	}

	public List<TeamMemberPatientRelation> getTeamPatientRelations(Integer id) {
		return dao.getTeamPatientRelations(id);
	}
	
	public TeamMemberPatientRelation getTeamPatientRelations(TeamMemberPatientRelation id) {
		return dao.getTeamPatientRelations(id);
	}
	
	public List<TeamMemberPatientRelation> getTeamPatientRelations() {
		return dao.getTeamPatientRelations();
	}
	
	public List<TeamMemberPatientRelation> getTeamPatientRelationByTeamMember(TeamMember tm) {
		return dao.getTeamPatientRelationByTeamMember(tm);
	}
	
}