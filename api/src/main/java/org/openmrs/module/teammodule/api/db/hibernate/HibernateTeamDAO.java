/**
 * 
 */
package org.openmrs.module.teammodule.api.db.hibernate;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.api.db.TeamDAO;

/**
 * @author Muhammad Safwan
 * 
 */
public class HibernateTeamDAO implements TeamDAO {

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

	@Override
	public void saveTeam(Team team) {
		getCurrentSession().saveOrUpdate(team);
	}

	@Override
	public Team getTeam(Integer teamId) {
		return (Team) getCurrentSession().createQuery("from Team team where team.teamId = :id").setInteger("id", teamId).uniqueResult();

	}

	@Override
	public Team getTeam(String uuid) {
		return (Team) getCurrentSession().createQuery("from Team team where team.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Team> getAllTeams(boolean voided, Integer offset, Integer pageSize) {
		Criteria criteria = getCurrentSession().createCriteria(Team.class);
		if (!voided) {
			criteria.add(Restrictions.eq("voided", false));
		}
		if (offset != null) {
			criteria.setFirstResult(offset);
		}
		if (pageSize != null) {
			criteria.setMaxResults(pageSize);
		}
		return (List<Team>) criteria.list();
	}

	@Override
	public void purgeTeam(Team team) {
		getCurrentSession().delete(team);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Team> searchTeam(String teamName) {
		return (List<Team>) getCurrentSession().createQuery("from Team team where team.teamName like :teamName or team.teamIdentifier like :teamName").setString("teamName", "%" + teamName + "%").list();
	}
	
	@Override
	public Team getTeamBySupervisor(Integer teamSupervisorId) {
		return (Team) (getCurrentSession().createQuery("from Team team where team.supervisor = :id").setInteger("id", teamSupervisorId)).uniqueResult();
	}

	@Override
	public void updateTeam(Team team) {
		getCurrentSession().update(team);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Team> getTeamByLocation(Integer locationId, Integer offset, Integer pageSize) {
		Query createQuery= getCurrentSession().createQuery("from Team team where team.location=:id").setInteger("id", locationId);
		if(offset != null) {
			createQuery.setFirstResult(offset);
		}
		if(pageSize != null) {
			createQuery.setMaxResults(pageSize);
		}
		return (List<Team>) createQuery.list();
	}

	@Override
	public Team getTeam(String teamName, Integer locationId) {
		Query createQuery= getCurrentSession().createQuery("from Team team where team.teamName = :name and team.location= :locationId").setInteger("locationId", locationId).setString("name", teamName);
		return (Team) createQuery.uniqueResult();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Team> getSubTeams(Integer teamSupervisorId) {
		return (List<Team>) getCurrentSession().createQuery("from Team team where team.supervisor = :id").setInteger("id", teamSupervisorId).list();
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
				throw new RuntimeException("Failed to get the current hibernate session from HibernateTeamDAO", e);
			}
		}
	}
}