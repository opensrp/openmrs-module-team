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
import org.openmrs.Location;
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

	public Team getTeam(Integer teamId) {
		return (Team) sessionFactory.getCurrentSession().createQuery("from Team team where team.teamId = :id").setInteger("id", teamId).uniqueResult();

	}

	public Team getTeam(String uuid) {
		return (Team) sessionFactory.getCurrentSession().createQuery("from Team team where team.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Team> getAllTeams(boolean voided, Integer offset, Integer pageSize) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Team.class);
		if (!voided) {
			return sessionFactory.getCurrentSession().createQuery("from Team team where team.voided = :voided").setBoolean("voided", voided).list();
		}
		if(offset != null) {
			criteria.setFirstResult(offset);
		}
		if(pageSize != null) {
			criteria.setMaxResults(pageSize);
		}
		return criteria.list();
	}


	public void purgeTeam(Team team) {
		sessionFactory.getCurrentSession().delete(team);
	}

	@SuppressWarnings("unchecked")
	public List<Team> searchTeam(String teamName) {
		return sessionFactory.getCurrentSession().createQuery("from Team team where team.teamName like :teamName or team.teamIdentifier like :teamName").setString("teamName", "%" + teamName + "%").list();
	}
	
	public Team getTeamBySupervisor(TeamMember teamSupervisor) {
		
		Query createQuery= sessionFactory.getCurrentSession().createQuery("from Team team where team.supervisor = :id").setInteger("id", teamSupervisor.getId());
		return (Team) createQuery.uniqueResult();
	}

	@Override
	public void updateTeam(Team team) {
		sessionFactory.getCurrentSession().update(team);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Team> getTeambyLocation(Location locationId, Integer offset, Integer pageSize) {
		Query createQuery= sessionFactory.getCurrentSession().createQuery("from Team team where team.location=:id").setInteger("id", locationId.getId());
		if(offset != null) {
			createQuery.setFirstResult(offset);
		}
		if(pageSize != null) {
			createQuery.setMaxResults(pageSize);
		}
		return createQuery.list();
	}

	@Override
	public Team getTeam(String teamName, int locationId) {
		Query createQuery= sessionFactory.getCurrentSession().createQuery("from Team team where team.teamName = :name and team.location= :locationId").setInteger("locationId", locationId).setString("name", teamName);
		return (Team) createQuery.uniqueResult();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Team> getSubTeams(TeamMember teamSupervisor) {
		return sessionFactory.getCurrentSession().createQuery("from Team team where team.supervisor = :id").setInteger("id", teamSupervisor.getId()).list();
	}
}