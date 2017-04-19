package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
		sessionFactory.getCurrentSession().saveOrUpdate(TeamLog);
		
	}

	public TeamLog getTeamLog(int id) {
		return (TeamLog)sessionFactory.getCurrentSession().createQuery("from TeamLog teamLog where teamLog.logId = :id").setInteger("id", id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TeamLog> getAllLogs(int pageIndex) {		
		Query createQuery = sessionFactory.getCurrentSession().createQuery("from TeamLog teamLog");
		createQuery.setFirstResult(pageIndex);
		createQuery.setMaxResults(20);
		return createQuery.list();
	}

	public void purgeTeamLog(TeamLog TeamLog) {
		sessionFactory.getCurrentSession().delete(TeamLog);
	}

	@SuppressWarnings("unchecked")
	public List<TeamLog> searchTeamLogByTeam(int team,int pageIndex) {
		Query createQuery=sessionFactory.getCurrentSession().createQuery("from TeamLog teamLog where teamLog.team = :team").setInteger("team", team);
		createQuery.setFirstResult(pageIndex);
		createQuery.setMaxResults(20);
		return createQuery.list();
	}

	public TeamLog getTeamLog(String uuid) {
		return (TeamLog)sessionFactory.getCurrentSession().createQuery("from TeamLog teamLog where teamLog.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}

}
