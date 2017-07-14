package org.openmrs.module.teammodule.api.db.hibernate;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openmrs.module.teammodule.Team;
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
		getCurrentSession().saveOrUpdate(TeamRoleLog);
		
	}

	public TeamRoleLog getTeamRoleLog(Integer id) {
		return (TeamRoleLog) getCurrentSession().createQuery("from TeamRoleLog teamRoleLog where teamRoleLog.logId = :id").setInteger("id", id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TeamRoleLog> getAllLogs(Integer offset, Integer pageSize) {
		Query createQuery = getCurrentSession().createQuery("from TeamRoleLog teamRoleLog");
		if(offset != null) {
			createQuery.setFirstResult(offset);
		}
		if(pageSize != null) {
			createQuery.setMaxResults(pageSize);	
		}
		return (List<TeamRoleLog>) createQuery.list();
	}

	public void purgeTeamRoleLog(TeamRoleLog TeamRoleLog) {
		getCurrentSession().delete(TeamRoleLog);
	}

	public void updateTeamRoleLog(TeamRoleLog TeamRoleLog) {
		getCurrentSession().update(TeamRoleLog);
	}

	@SuppressWarnings("unchecked")
	public List<TeamRoleLog> searchTeamRoleLog(String teamRole, Integer offset, Integer pageSize) {
		Query createQuery = getCurrentSession().createQuery("from TeamRoleLog teamRoleLog where teamRoleLog.teamRole = :teamRole").setInteger("teamRole", Integer.parseInt(teamRole));
		if(offset != null) {
			createQuery.setFirstResult(offset);
		}
		if(pageSize != null) {
			createQuery.setMaxResults(pageSize);	
		}
		return (List<TeamRoleLog>) createQuery.list();
	}

	public TeamRoleLog getTeamRoleLog(String uuid) {
		Query createQuery= getCurrentSession().createQuery("from TeamRoleLog teamRoleLog where teamRoleLog.uuid = :uuid").setString("uuid", uuid);
		return (TeamRoleLog) createQuery.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TeamRoleLog> searchTeamRoleLog(Integer teamRole, Integer offset, Integer pageSize) {
		Query createQuery = getCurrentSession().createQuery("from TeamRoleLog teamRoleLog where teamRoleLog.teamRole = :teamRole").setInteger("teamRole", teamRole);
		if(offset != null) {
			createQuery.setFirstResult(offset);
		}
		if(pageSize != null) {
			createQuery.setMaxResults(pageSize);	
		}
		return (List<TeamRoleLog>) createQuery.list();
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
				throw new RuntimeException("Failed to get the current hibernate session from HibernateTeamRoleLogDAO", e);
			}
		}
	}
}
