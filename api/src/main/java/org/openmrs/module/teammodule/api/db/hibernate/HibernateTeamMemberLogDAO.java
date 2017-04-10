package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
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
		sessionFactory.getCurrentSession().saveOrUpdate(teamMemberLog);
	}

	public TeamMemberLog getTeamMemberLog(int id) {
		return	(TeamMemberLog)sessionFactory.getCurrentSession().createQuery("from TeamMemberLog teamMemberLog where teamMemberLog.logId = :id").setInteger("id", id).uniqueResult();
	}

	public TeamMemberLog getTeamMemberLog(String uuid) {
		return	(TeamMemberLog)sessionFactory.getCurrentSession().createQuery("from TeamMemberLog teamMemberLog where teamMemberLog.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<TeamMemberLog> getAllLogs() {
		List<TeamMemberLog> createQuery = (List<TeamMemberLog>)sessionFactory.getCurrentSession().createQuery("from TeamMemberLog teamMemberLog").list();
		return	createQuery;
	}

	public void purgeTeamMemberLog(TeamMemberLog teamMemberLog) {
		sessionFactory.getCurrentSession().delete(teamMemberLog);
	}

	@SuppressWarnings("unchecked")
	public List<TeamMemberLog> searchTeamMemberLogByTeamMember(int teamMember) {
		return (List<TeamMemberLog>)sessionFactory.getCurrentSession().createQuery("from TeamMemberLog teamMemberLog where teamMemberLog.teamMember = :teamMember").setInteger("teamMember", teamMember).list();
	}
}
