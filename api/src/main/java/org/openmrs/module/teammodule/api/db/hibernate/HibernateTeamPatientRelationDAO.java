package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.teammodule.TeamMemberPatientRelation;
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
		sessionFactory.getCurrentSession().delete(team);
	}

	public void delete(int memberPatientId) {
		sessionFactory.getCurrentSession().createQuery("delete from TeamMemberPatientRelation where teamMemberPatientId="+memberPatientId);
	}
	
	public TeamMemberPatientRelation getTeamPatientRelation(int tpr) {
		return (TeamMemberPatientRelation) sessionFactory.getCurrentSession().createQuery("from TeamMemberPatientRelation where teamMemberPatientId = :id").setInteger("id", tpr).uniqueResult();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TeamMemberPatientRelation> getTeamPatientRelations(Integer tpr) {
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

	public void update(TeamMemberPatientRelation tmpr) {
		sessionFactory.getCurrentSession().update(tmpr);
	}

	public void purgeTeamPatientRelation(TeamMemberPatientRelation tpr) {
		sessionFactory.getCurrentSession().delete(tpr);
	}

	@SuppressWarnings("unchecked")
	public List<TeamMemberPatientRelation> searchTeamPatientRelation(String name) {
		List<TeamMemberPatientRelation> list = sessionFactory.getCurrentSession().createQuery("from TeamMemberPatientRelation").list();
		List<TeamMemberPatientRelation> list1 = new ArrayList<TeamMemberPatientRelation>();
		for(int i=0;i<list.size();i++)
		{
			if(name==list.get(i).getMember().getPerson().getGivenName())
			{
				list1.add(list.get(i));
			}
		}
		return list1;
	}

	@SuppressWarnings("unchecked")
	public List<TeamMemberPatientRelation> getTeamPatientRelations() {
		return sessionFactory.getCurrentSession().createQuery("from TeamMemberPatientRelation").list();
	}

	/*@Override
	public TeamMemberPatientRelation getTeamPatientRelation(TeamMemberPatientRelation tpr) {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public TeamMemberPatientRelation getTeamPatientRelation(String uuid) {
		return (TeamMemberPatientRelation) sessionFactory.getCurrentSession().createQuery("from TeamMemberPatientRelation where uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}

	/*@Override
	public List<TeamMemberPatientRelation> getTeamPatientRelations(TeamMemberPatientRelation tpr) {
		// TODO Auto-generated method stub
		return null;
	}*/

}
