package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamHierarchy;
import org.openmrs.module.teammodule.TeamLog;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamHierarchyService;
import org.openmrs.module.teammodule.api.db.TeamHierarchyDAO;
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
		return	(TeamLog)sessionFactory.getCurrentSession().createQuery("from TeamLog tl where tl.team_log_id = :id").setInteger("id", id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TeamLog> getAllLogs() {
		List<TeamLog> createQuery = (List<TeamLog>)sessionFactory.getCurrentSession().createQuery("from TeamLog").list();
		return	createQuery;
		}

	public void purgeTeamLog(TeamLog TeamLog) {
		sessionFactory.getCurrentSession().delete(TeamLog);
	}

	public List<TeamLog> searchTeamLogByTeam(int team) {
		return (List<TeamLog>)sessionFactory.getCurrentSession().createQuery("from TeamLog tl where tl.team = :team").setInteger("team", team);
	}



}