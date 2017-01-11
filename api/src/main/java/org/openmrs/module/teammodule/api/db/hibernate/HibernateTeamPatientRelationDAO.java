package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.TeamMemberPatientRelation;
import org.openmrs.module.teammodule.api.db.TeamMemberDAO;
import org.openmrs.module.teammodule.api.db.TeamMemberPatientRelationDAO;

public class HibernateTeamPatientRelationDAO implements TeamMemberPatientRelationDAO{


	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * Hibernate session factory
	 */
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void save(TeamMemberPatientRelation team) {
		sessionFactory.getCurrentSession().save(team);
	}
	
	public void delete(TeamMemberPatientRelation team) {

		// sessionFactory.openSession();
		sessionFactory.getCurrentSession().delete(team);
		// sessionFactory.close();
	}

	public void delete(int memberPatientId) {

		// sessionFactory.openSession();
		sessionFactory.getCurrentSession().createQuery("delete from TeamMemberPatientRelation where member_patient_id="+memberPatientId);
		// sessionFactory.close();
	}
	
	public TeamMemberPatientRelation getTeamPatientRelation(int tpr) {
		return (TeamMemberPatientRelation) sessionFactory.getCurrentSession().createQuery("from TeamMemberPatientRelation where member_patient_id = :id").setInteger("id", tpr).uniqueResult();
		
	}
	
	
	public List<TeamMemberPatientRelation> getTeamPatientRelations(
			Integer tpr) {
		List<TeamMemberPatientRelation> list = sessionFactory.getCurrentSession().createQuery("from TeamMemberPatientRelation").list();
		List<TeamMemberPatientRelation> list1 = new ArrayList<TeamMemberPatientRelation>();
		for(int i=0;i<list.size();i++)
		{
			if(tpr==list.get(i).getMember().getId())
			{
				list1.add(list.get(i));
			}
		}
		return list1;
	}

	public TeamMemberPatientRelation getTeamPatientRelation(
			TeamMemberPatientRelation tpr) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TeamMemberPatientRelation> getTeamPatientRelations(
			TeamMemberPatientRelation tpr) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(TeamMemberPatientRelation tpr) {
		// TODO Auto-generated method stub
		
	}

	public void purgeTeamPatientRelation(TeamMemberPatientRelation tpr) {
		// TODO Auto-generated method stub
		
	}

	public TeamMemberPatientRelation getTeamPatientRelation(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TeamMemberPatientRelation> searchTeamPatientRelation(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TeamMemberPatientRelationDAO> getTeamPatientRelations() {
		// TODO Auto-generated method stub
		return null;
	}

}
