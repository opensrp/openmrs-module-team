package org.openmrs.module.teammodule.api.db.hibernate;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openmrs.module.teammodule.TeamLog;
import org.openmrs.module.teammodule.api.db.TeamLogDAO;

public class HibernateTeamLogDAO implements TeamLogDAO{


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

	public void saveTeamLog(TeamLog TeamLog) {
		getCurrentSession().saveOrUpdate(TeamLog);
		
	}

	public TeamLog getTeamLog(Integer id) {
		return (TeamLog) getCurrentSession().createQuery("from TeamLog teamLog where teamLog.logId = :id").setInteger("id", id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TeamLog> getAllLogs(Integer offset, Integer pageSize) {		
		Query createQuery = getCurrentSession().createQuery("from TeamLog teamLog");
		if( offset != null) {
			createQuery.setFirstResult(offset);
		}
		if(pageSize != null) {
			createQuery.setMaxResults(pageSize);
		}
		return (List<TeamLog>) createQuery.list();
	}

	public void purgeTeamLog(TeamLog TeamLog) {
		getCurrentSession().delete(TeamLog);
	}

	public void updateTeamLog(TeamLog TeamLog) {
		getCurrentSession().update(TeamLog);
	}

	@SuppressWarnings("unchecked")
	public List<TeamLog> searchTeamLogByTeam(Integer team, Integer offset, Integer pageSize) {
		Query createQuery=getCurrentSession().createQuery("from TeamLog teamLog where teamLog.team = :team").setInteger("team", team);
		if( offset != null) {
			createQuery.setFirstResult(offset);
		}
		if(pageSize != null) {
			createQuery.setMaxResults(pageSize);
		}
		return (List<TeamLog>) createQuery.list();
	}

	public TeamLog getTeamLog(String uuid) {
		return (TeamLog) getCurrentSession().createQuery("from TeamLog teamLog where teamLog.uuid = :uuid").setString("uuid", uuid).uniqueResult();
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
				throw new RuntimeException("Failed to get the current hibernate session from HibernateTeamLogDAO", e);
			}
		}
	}
}
