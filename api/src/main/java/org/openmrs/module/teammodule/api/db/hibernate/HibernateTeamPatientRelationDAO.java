package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.teammodule.TeamMember;
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
	
	public TeamMemberPatientRelation getTeamPatientRelation(int tpr) {
		return (TeamMemberPatientRelation) sessionFactory.getCurrentSession().createQuery("from TeamMemberPatientRelation teamMemberPatient where teamMemberPatient.teamMemberPatientId = :id").setInteger("id", tpr).uniqueResult();
	}

	public void update(TeamMemberPatientRelation tmpr) {
		sessionFactory.getCurrentSession().update(tmpr);
	}

	public void purgeTeamPatientRelation(TeamMemberPatientRelation tpr) {
		sessionFactory.getCurrentSession().delete(tpr);
	}
	
	@Override
	public TeamMemberPatientRelation getTeamPatientRelation(String uuid) {
		return (TeamMemberPatientRelation) sessionFactory.getCurrentSession().createQuery("from TeamMemberPatientRelation teamMemberPatient where teamMemberPatient.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}
	
	@Override
	public TeamMemberPatientRelation getTeamPatientRelations(TeamMemberPatientRelation tpr) {
		return (TeamMemberPatientRelation) sessionFactory.getCurrentSession().createQuery("from TeamMemberPatientRelation teamMemberPatient where teamMemberPatient.teamMemberPatientId = :id").setInteger("id", tpr.getId()).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TeamMemberPatientRelation> getTeamPatientRelations(TeamMember tm) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TeamMemberPatientRelation.class);

		if (tm != null) {
			criteria.add(Restrictions.eq("member", tm));
		}
		return criteria.list();
	}
}
