package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamHierarchy;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamHierarchyService;
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

	public TeamHierarchy getTeamRole(int id) {
		return	(TeamHierarchy)sessionFactory.getCurrentSession().createQuery("from TeamRole th where th.team_hierarchy_id = :id").setInteger("id", id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TeamHierarchy> getAllTeams() {
		List<TeamHierarchy> createQuery = (List<TeamHierarchy>)sessionFactory.getCurrentSession().createQuery("from TeamRole").list();
		return	createQuery;
		}

	public void purgeTeamRole(TeamHierarchy TeamRole) {
		sessionFactory.getCurrentSession().delete(TeamRole);
	}

	public List<TeamHierarchy> searchTeamRoleByRole(String role) {
		return (List<TeamHierarchy>)sessionFactory.getCurrentSession().createQuery("from TeamRole th where th.team_role = :role").setString("role", role);
	}

}
