package org.openmrs.module.teammodule.api.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamMemberPatientRelation;
import org.openmrs.module.teammodule.api.TeamMemberPatientRelationService;
import org.openmrs.module.teammodule.api.TeamService;
import org.openmrs.module.teammodule.api.db.TeamDAO;
import org.openmrs.module.teammodule.api.db.TeamMemberPatientRelationDAO;
import org.openmrs.module.teammodule.api.db.hibernate.HibernateTeamPatientRelationDAO;

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
		// TODO Auto-generated method stub
		return dao.getTeamPatientRelation(id);
	}

	public List<TeamMemberPatientRelation> getTeamPatientRelations(
			Integer id) {
		// TODO Auto-generated method stub
		return dao.getTeamPatientRelations(id);
	}

	public TeamMemberPatientRelation getTeamPatientRelation(
			TeamMemberPatientRelation tmpr) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TeamMemberPatientRelation> getTeamPatientRelations(
			TeamMemberPatientRelation tmpr) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(TeamMemberPatientRelation tmpr) {
		// TODO Auto-generated method stub
		
	}


	
}