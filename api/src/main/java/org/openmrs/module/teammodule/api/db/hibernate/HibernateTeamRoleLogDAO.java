package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.openmrs.module.teammodule.TeamRoleLog;
import org.openmrs.module.teammodule.api.db.TeamRoleLogDAO;

public class HibernateTeamRoleLogDAO implements TeamRoleLogDAO{


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

	public void saveTeamRoleLog(TeamRoleLog TeamRoleLog) {
		sessionFactory.getCurrentSession().saveOrUpdate(TeamRoleLog);
		
	}

	public TeamRoleLog getTeamRoleLog(int id) {
		return (TeamRoleLog)sessionFactory.getCurrentSession().createQuery("from TeamRoleLog teamRoleLog where teamRoleLog.logId = :id").setInteger("id", id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TeamRoleLog> getAllLogs(int pageIndex) {
		Query createQuery = sessionFactory.getCurrentSession().createQuery("from TeamRoleLog teamRoleLog");
		createQuery.setFirstResult(pageIndex);
		createQuery.setMaxResults(20);
		return	createQuery.list();
	}

	public void purgeTeamRoleLog(TeamRoleLog TeamRoleLog) {
		sessionFactory.getCurrentSession().delete(TeamRoleLog);
	}

	@SuppressWarnings("unchecked")
	public List<TeamRoleLog> searchTeamRoleLogByTeamRole(String teamRole, int pageIndex) {
		Query createQuery =sessionFactory.getCurrentSession().createQuery("from TeamRoleLog teamRoleLog where teamRoleLog.teamRole = :teamRole").setInteger("teamRole", Integer.parseInt(teamRole));
		createQuery.setFirstResult(pageIndex);
		createQuery.setMaxResults(20);
		return createQuery.list();
	}

	public TeamRoleLog getTeamRoleLog(String uuid) {
		Query createQuery=sessionFactory.getCurrentSession().createQuery("from TeamRoleLog teamRoleLog where teamRoleLog.uuid = :uuid").setString("uuid", uuid);
		return (TeamRoleLog) createQuery.uniqueResult();
	}

}
