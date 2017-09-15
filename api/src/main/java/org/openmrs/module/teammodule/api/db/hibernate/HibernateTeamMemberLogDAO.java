package org.openmrs.module.teammodule.api.db.hibernate;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.TeamMemberLog;
import org.openmrs.module.teammodule.api.db.TeamMemberLogDAO;

public class HibernateTeamMemberLogDAO implements TeamMemberLogDAO{


	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * Hibernate session factory
	 */
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Set session factory
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void saveTeamMemberLog(TeamMemberLog teamMemberLog) {
		getCurrentSession().saveOrUpdate(teamMemberLog);
	}

	public void updateTeamMemberLog(TeamMemberLog teamMemberLog) {
		getCurrentSession().update(teamMemberLog);
	}

	public TeamMemberLog getTeamMemberLog(Integer id) {
		return (TeamMemberLog) getCurrentSession().createQuery("from TeamMemberLog teamMemberLog where teamMemberLog.logId = :id").setInteger("id", id).uniqueResult();
	}

	public TeamMemberLog getTeamMemberLogByUUID(String uuid) {
		return (TeamMemberLog) getCurrentSession().createQuery("from TeamMemberLog teamMemberLog where teamMemberLog.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<TeamMemberLog> getAllLogs(Integer offset, Integer pageSize) {
		Criteria criteria = getCurrentSession().createCriteria(TeamMember.class);
		if (pageSize != null) {
			criteria.setFirstResult(offset);
		}
		if (pageSize != null) {
			criteria.setMaxResults(pageSize);
		}
		return (List<TeamMemberLog>) criteria.list();
	}

	public void purgeTeamMemberLog(TeamMemberLog teamMemberLog) {
		getCurrentSession().delete(teamMemberLog);
	}

	@SuppressWarnings("unchecked")
	public List<TeamMemberLog> searchTeamMemberLogByTeamMember(Integer teamMember, Integer offset, Integer pageSize) {
		Query createQuery =getCurrentSession().createQuery("from TeamMemberLog teamMemberLog where teamMemberLog.teamMember = :teamMember").setInteger("teamMember", teamMember);
		if(offset != null) {
			createQuery.setFirstResult(offset);
		}
		if(pageSize != null) {
			createQuery.setMaxResults(pageSize);	
		}
		return (List<TeamMemberLog>) createQuery.list();
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
				throw new RuntimeException("Failed to get the current hibernate session from HibernateTeamMemberLogDAO", e);
			}
		}
	}
}
