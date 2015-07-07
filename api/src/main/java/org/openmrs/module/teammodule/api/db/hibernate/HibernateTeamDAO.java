/**
 * 
 */
package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamMember;
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
		return (Team) sessionFactory.getCurrentSession().createQuery("from Team t where t.id = :id").setInteger("id", teamId).uniqueResult();

	}

	public Team getTeam(String teamName) {
		return (Team) sessionFactory.getCurrentSession().createQuery("from Team t where t.teamName = :teamName or t.uuid = :teamName").setString("teamName", teamName).uniqueResult();
	}

	/*
	 * public void updateTeam(Team team) { // TODO Auto-generated method stub
	 * 
	 * }
	 */

	@SuppressWarnings("unchecked")
	public List<Team> getAllTeams(boolean retired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Team.class);

		if (!retired) {
			criteria.add(Restrictions.eq("voided", false));
		}

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TeamMember> getAllMembers(boolean retired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TeamMember.class);

		if (!retired) {
			criteria.add(Restrictions.eq("retired", false));
		}
		return criteria.list();
	}

	public void purgeTeam(Team team) {
		sessionFactory.getCurrentSession().delete(team);
	}

	@SuppressWarnings("unchecked")
	public List<Team> searchTeam(String teamName) {
		return sessionFactory.getCurrentSession().createQuery("from Team t where t.teamName like :teamName or t.teamIdentifier like :teamName").setString("teamName", "%" + teamName + "%").list();
	}

}
