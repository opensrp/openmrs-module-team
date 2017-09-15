package org.openmrs.module.teammodule.api.db.hibernate;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.TeamMemberPatientRelation;
import org.openmrs.module.teammodule.api.db.TeamMemberPatientRelationDAO;

public class HibernateTeamMemberPatientRelationDAO implements TeamMemberPatientRelationDAO{


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

	@Override
	public void saveTeamMemberPatientRelation(TeamMemberPatientRelation team) {
		getCurrentSession().save(team);
	}
	
	@Override
	public TeamMemberPatientRelation getTeamMemberPatientRelation(int tpr) {
		return (TeamMemberPatientRelation) getCurrentSession().createQuery("from TeamMemberPatientRelation teamMemberPatient where teamMemberPatient.teamMemberPatientId = :id").setInteger("id", tpr).uniqueResult();
	}

	@Override
	public void updateTeamMemberPatientRelation(TeamMemberPatientRelation tmpr) {
		getCurrentSession().update(tmpr);
	}

	@Override
	public void purgeTeamMemberPatientRelation(TeamMemberPatientRelation tpr) {
		getCurrentSession().delete(tpr);
	}
	
	@Override
	public TeamMemberPatientRelation getTeamMemberPatientRelationByUUID(String uuid) {
		return (TeamMemberPatientRelation) getCurrentSession().createQuery("from TeamMemberPatientRelation teamMemberPatient where teamMemberPatient.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}
	
	@Override
	public TeamMemberPatientRelation getTeamMemberPatientRelations(TeamMemberPatientRelation tpr) {
		return (TeamMemberPatientRelation) getCurrentSession().createQuery("from TeamMemberPatientRelation teamMemberPatient where teamMemberPatient.teamMemberPatientId = :id").setInteger("id", tpr.getId()).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TeamMemberPatientRelation> getTeamMemberPatientRelations( Integer teamMember, Integer patient,
			String status,Integer location, Integer offset, Integer pageSize) {
		Criteria criteria = getCurrentSession().createCriteria(TeamMemberPatientRelation.class);

		if (teamMember != null) {
			criteria.add(Restrictions.eq("teamMember.teamMemberId", teamMember));
		}
		else if(patient!= null)
		{
			criteria.add(Restrictions.eq("patient.patientId", patient));
		}
		else if(location!= null)
		{
			criteria.add(Restrictions.eq("location.locationId", location));
		}
		else if(status!= null)
		{
			criteria.add(Restrictions.eq("status", status));
		}
		
		if(offset != null) {
			criteria.setFirstResult(offset);
		}
		if(pageSize != null) {
			criteria.setMaxResults(pageSize);	
		}
		return (List<TeamMemberPatientRelation>) criteria.list();
	}
	
	private Session getCurrentSession() {
		try {
			return sessionFactory.getCurrentSession();
		}
		catch (NoSuchMethodError ex) {
			try {
				Method method = sessionFactory.getClass().getMethod("getCurrentSession",null);
				return (Session)method.invoke(sessionFactory, null);
			}
			catch (Exception e) {
				throw new RuntimeException("Failed to get the current hibernate session from HibernateTeamMemberPatientDAO", e);
			}
		}
	}
}
