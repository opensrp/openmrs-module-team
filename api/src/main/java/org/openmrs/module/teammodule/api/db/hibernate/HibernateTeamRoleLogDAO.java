package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.openmrs.module.teammodule.TeamHierarchyLog;
import org.openmrs.module.teammodule.api.db.TeamHierarchyLogDAO;

public class HibernateTeamHierarchyLogDAO implements TeamHierarchyLogDAO{


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

	public void saveTeamHierarchyLog(TeamHierarchyLog TeamHierarchyLog) {
		sessionFactory.getCurrentSession().saveOrUpdate(TeamHierarchyLog);
		
	}

	public TeamHierarchyLog getTeamHierarchyLog(int id) {
		return (TeamHierarchyLog)sessionFactory.getCurrentSession().createQuery("from TeamHierarchyLog teamHierarchyLog where teamHierarchyLog.logId = :id").setInteger("id", id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TeamHierarchyLog> getAllLogs(Integer offset, Integer pageSize) {
		Query createQuery = sessionFactory.getCurrentSession().createQuery("from TeamHierarchyLog teamHierarchyLog");
		if(offset != null) {
			createQuery.setFirstResult(offset);
		}
		if(pageSize != null) {
			createQuery.setMaxResults(pageSize);	
		}
		return	createQuery.list();
	}

	public void purgeTeamHierarchyLog(TeamHierarchyLog TeamRoleLog) {
		sessionFactory.getCurrentSession().delete(TeamRoleLog);
	}

	@SuppressWarnings("unchecked")
	public List<TeamHierarchyLog> searchTeamHierarchyLog(String teamRole, Integer offset, Integer pageSize) {
		Query createQuery =sessionFactory.getCurrentSession().createQuery("from TeamHierarchyLog teamHierarchyLog where teamHierarchyLog.teamRole = :teamRole").setInteger("teamRole", Integer.parseInt(teamRole));
		if(offset != null) {
			createQuery.setFirstResult(offset);
		}
		if(pageSize != null) {
			createQuery.setMaxResults(pageSize);	
		}
		return createQuery.list();
	}

	public TeamHierarchyLog getTeamHierarchyLog(String uuid) {
		Query createQuery=sessionFactory.getCurrentSession().createQuery("from TeamHierarchyLog teamHierarchyLog where teamHierarchyLog.uuid = :uuid").setString("uuid", uuid);
		return (TeamHierarchyLog) createQuery.uniqueResult();
	}

}
