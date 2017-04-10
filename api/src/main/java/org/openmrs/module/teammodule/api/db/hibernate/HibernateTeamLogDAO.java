package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	public List<TeamLog> getAllLogs() {
		List<TeamLog> createQuery = (List<TeamLog>)sessionFactory.getCurrentSession().createQuery("from TeamLog teamLog").list();
		return	createQuery;
	}

	public void purgeTeamLog(TeamLog TeamLog) {
		sessionFactory.getCurrentSession().delete(TeamLog);
	}

	@SuppressWarnings("unchecked")
	public List<TeamLog> searchTeamLogByTeam(int team) {
		return (List<TeamLog>)sessionFactory.getCurrentSession().createQuery("from TeamLog teamLog where teamLog.team = :team").setInteger("team", team).list();
	}

	public TeamLog getTeamLog(String uuid) {
		return (TeamLog)sessionFactory.getCurrentSession().createQuery("from TeamLog teamLog where teamLog.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}

}
