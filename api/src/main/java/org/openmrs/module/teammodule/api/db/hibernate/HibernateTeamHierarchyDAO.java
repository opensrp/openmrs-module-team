package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.teammodule.TeamHierarchy;
import org.openmrs.module.teammodule.api.db.TeamHierarchyDAO;

public class HibernateTeamHierarchyDAO implements TeamHierarchyDAO{


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

	public void saveTeamRole(TeamHierarchy TeamRole) {
		sessionFactory.getCurrentSession().saveOrUpdate(TeamRole);
		
	}

	public TeamHierarchy getTeamRoleById(int id) {
		return	(TeamHierarchy)sessionFactory.getCurrentSession().createQuery("from TeamHierarchy teamHierarchy where teamHierarchy.teamRoleId = :id").setInteger("id", id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TeamHierarchy> getAllTeams() {
		List<TeamHierarchy> createQuery = (List<TeamHierarchy>)sessionFactory.getCurrentSession().createQuery("from TeamHierarchy teamHierarchy").list();
		return	createQuery;
		}

	public void purgeTeamRole(TeamHierarchy TeamRole) {
		sessionFactory.getCurrentSession().delete(TeamRole);
	}

	@SuppressWarnings("unchecked")
	public List<TeamHierarchy> searchTeamRoleByRole(String role) {
		return (List<TeamHierarchy>)sessionFactory.getCurrentSession().createQuery("from TeamHierarchy teamHierarchy where teamHierarchy.teamRoleId = :role").setString("role", role).list();
	}
	
	public TeamHierarchy getTeamRoleByUuid(String uuid) {
		return (TeamHierarchy)sessionFactory.getCurrentSession().createQuery("from TeamHierarchy teamHierarchy where th.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}

}
