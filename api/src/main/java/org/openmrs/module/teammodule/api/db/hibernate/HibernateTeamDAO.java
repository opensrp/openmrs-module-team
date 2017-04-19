/**
 * 
 */
package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
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

	public void saveTeam(Team team) {

		// sessionFactory.openSession();
		sessionFactory.getCurrentSession().saveOrUpdate(team);
		// sessionFactory.close();
	}

	public Team getTeam(int teamId) {
		return (Team) sessionFactory.getCurrentSession().createQuery("from Team team where team.teamId = :id").setInteger("id", teamId).uniqueResult();

	}

	public Team getTeam(String uuid) {
		return (Team) sessionFactory.getCurrentSession().createQuery("from Team team where team.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Team> getAllTeams(boolean retired, int pageIndex) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Team.class);
		if (!retired) {
			criteria.add(Restrictions.eq("voided", false));
		}
		criteria.setFirstResult(pageIndex);
		criteria.setMaxResults(20);
		return criteria.list();
	}


	public void purgeTeam(Team team) {
		sessionFactory.getCurrentSession().delete(team);
	}

	@SuppressWarnings("unchecked")
	public List<Team> searchTeam(String teamName) {
		return sessionFactory.getCurrentSession().createQuery("from Team team where team.teamName like :teamName or team.teamIdentifier like :teamName").setString("teamName", "%" + teamName + "%").list();
	}
	
	public Team getTeamBySupervisor(int teamSupervisor) {
		
		Query createQuery= sessionFactory.getCurrentSession().createQuery("from Team team where team.supervisor = :id").setInteger("id", teamSupervisor);
		return (Team) createQuery.uniqueResult();
	}

	@Override
	public void updateTeam(Team team) {
		sessionFactory.getCurrentSession().update(team);
		
	}

	@Override
	public List<Team> getTeambyLocation(int locationId,int pageIndex) {
		// TODO Auto-generated method stub
		Query createQuery= sessionFactory.getCurrentSession().createQuery("from Team team where team.location_id=: id").setInteger("id", locationId);
		createQuery.setFirstResult(pageIndex);
		createQuery.setMaxResults(20);
		return createQuery.list();
	}

}
