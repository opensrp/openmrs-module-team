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
	public List<TeamRoleLog> getAllLogs(Integer offset, Integer pageSize) {
		Query createQuery = sessionFactory.getCurrentSession().createQuery("from TeamRoleLog teamRoleLog");
		if(offset != null) {
			createQuery.setFirstResult(offset);
		}
		if(pageSize != null) {
			createQuery.setMaxResults(pageSize);	
		}
		return	createQuery.list();
	}

	public void purgeTeamRoleLog(TeamRoleLog TeamRoleLog) {
		sessionFactory.getCurrentSession().delete(TeamRoleLog);
	}

	public void updateTeamRoleLog(TeamRoleLog TeamRoleLog) {
		sessionFactory.getCurrentSession().update(TeamRoleLog);
	}

	@SuppressWarnings("unchecked")
	public List<TeamRoleLog> searchTeamRoleLog(String teamRole, Integer offset, Integer pageSize) {
		Query createQuery =sessionFactory.getCurrentSession().createQuery("from TeamRoleLog teamRoleLog where teamRoleLog.teamRole = :teamRole").setInteger("teamRole", Integer.parseInt(teamRole));
		if(offset != null) {
			createQuery.setFirstResult(offset);
		}
		if(pageSize != null) {
			createQuery.setMaxResults(pageSize);	
		}
		return createQuery.list();
	}

	public TeamRoleLog getTeamRoleLog(String uuid) {
		Query createQuery=sessionFactory.getCurrentSession().createQuery("from TeamRoleLog teamRoleLog where teamRoleLog.uuid = :uuid").setString("uuid", uuid);
		return (TeamRoleLog) createQuery.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TeamRoleLog> searchTeamRoleLog(Integer teamRole, Integer offset, Integer pageSize) {
		Query createQuery =sessionFactory.getCurrentSession().createQuery("from TeamRoleLog teamRoleLog where teamRoleLog.teamRole = :teamRole").setInteger("teamRole", teamRole);
		if(offset != null) {
			createQuery.setFirstResult(offset);
		}
		if(pageSize != null) {
			createQuery.setMaxResults(pageSize);	
		}
		return createQuery.list();
	}
}
