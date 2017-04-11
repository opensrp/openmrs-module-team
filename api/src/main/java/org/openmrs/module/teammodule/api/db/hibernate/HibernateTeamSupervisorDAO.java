/**
 * 
 */
package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
//import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
//import org.hibernate.criterion.Restrictions;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamSupervisor;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.db.TeamSupervisorDAO;

/**
 * @author Muhammad Safwan
 * 
 */
public class HibernateTeamSupervisorDAO implements TeamSupervisorDAO {

	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * Hibernate session factory
	 */
	private SessionFactory sessionFactory;

	/**
	 * Set session factory
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void save(TeamSupervisor teamSupervisor) {
		sessionFactory.getCurrentSession().save(teamSupervisor);
	}

	@SuppressWarnings("unchecked")
	public List<TeamMember> getTeamMembers(Integer teamSupervisorId) {

		return (List<TeamMember>) sessionFactory.getCurrentSession().createQuery("from TeamSupervisor teamSupervisor where teamSupervisor.teamSupervisorId = :teamSupervisorId").setInteger("teamSupervisorId", teamSupervisorId).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TeamSupervisor> getTeamSupervisors(Team team) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TeamSupervisor.class);

		if (team != null) {
			criteria.add(Restrictions.eq("team", team));
		}

		return criteria.list();
	}

	public TeamSupervisor getTeamSupervisor(Team team) {
		return (TeamSupervisor) sessionFactory.getCurrentSession().createQuery("from TeamSupervisor teamSupervisor where teamSupervisor.team = :teamId and teamSupervisor.voided = :num").setInteger("teamId", team.getTeamId()).setInteger("num", 0).uniqueResult();
	}
	
	public void update(TeamSupervisor teamSupervisor) {
		sessionFactory.getCurrentSession().update(teamSupervisor);
	}
	
	public void purgeTeamSupervisor(TeamSupervisor teamSupervisor){
		sessionFactory.getCurrentSession().delete(teamSupervisor);
	}
	
	public TeamSupervisor getTeamSupervisor(String uuid){
		return (TeamSupervisor) sessionFactory.getCurrentSession().createQuery("from TeamSupervisor teamSupervisor where teamSupervisor.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}
}
