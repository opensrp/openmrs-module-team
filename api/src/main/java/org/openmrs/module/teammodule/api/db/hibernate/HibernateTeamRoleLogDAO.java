package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
		return (TeamRoleLog)sessionFactory.getCurrentSession().createQuery("from TeamRoleLog trl where trl.logId = :id").setInteger("id", id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TeamRoleLog> getAllLogs() {
		List<TeamRoleLog> createQuery = (List<TeamRoleLog>)sessionFactory.getCurrentSession().createQuery("from TeamRoleLog").list();
		return	createQuery;
	}

	public void purgeTeamRoleLog(TeamRoleLog TeamRoleLog) {
		sessionFactory.getCurrentSession().delete(TeamRoleLog);
	}

	@SuppressWarnings("unchecked")
	public List<TeamRoleLog> searchTeamRoleLogByTeamRole(int teamRole) {
		return (List<TeamRoleLog>)sessionFactory.getCurrentSession().createQuery("from TeamRoleLog trl join trl.teamRole tr where tr.teamRoleId = :teamRole").setInteger("teamRole", teamRole).list();
	}

	public TeamRoleLog getTeamRoleLog(String uuid) {
		return (TeamRoleLog)sessionFactory.getCurrentSession().createQuery("from TeamRoleLog trl where trl.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}

}
