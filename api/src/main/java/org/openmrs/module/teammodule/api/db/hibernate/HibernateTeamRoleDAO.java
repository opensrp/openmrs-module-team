package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.TeamRole;
import org.openmrs.module.teammodule.api.db.TeamRoleDAO;

public class HibernateTeamRoleDAO implements TeamRoleDAO{


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

	public void saveTeamRole(TeamRole TeamRole) {
		sessionFactory.getCurrentSession().save(TeamRole);
	}

	public void updateTeamRole(TeamRole TeamRole) {
		sessionFactory.getCurrentSession().update(TeamRole);
	}

	public TeamRole getTeamRoleById(Integer id) {
		return	(TeamRole)sessionFactory.getCurrentSession().createQuery("from TeamRole teamRole where teamRole.teamRoleId = :id").setInteger("id", id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TeamRole> getAllTeamRole() {
		List<TeamRole> createQuery = (List<TeamRole>)sessionFactory.getCurrentSession().createQuery("from TeamRole teamRole").list();
		return	createQuery;
		}

	public void purgeTeamRole(TeamRole TeamRole) {
		sessionFactory.getCurrentSession().delete(TeamRole);
	}

	@SuppressWarnings("unchecked")
	public List<TeamRole> searchTeamRoleByRole(String role) {
		return (List<TeamRole>)sessionFactory.getCurrentSession().createQuery("from TeamRole teamRole where teamRole.name = :role").setString("role", role).list();
	}
	
	public TeamRole getTeamRoleByUuid(String uuid) {
		return (TeamRole)sessionFactory.getCurrentSession().createQuery("from TeamRole teamRole where teamRole.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TeamRole> getSubTeamRoles(TeamMember teamMember) {
		return sessionFactory.getCurrentSession().createQuery("from TeamRole teamRole where teamRole.reportTo = :id").setInteger("id", teamMember.getTeamRole().getId()).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TeamRole> searchTeamRoleReportBy(int id) {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("select count(*),teamRole.name,teamRole.teamRoleId,teamRole.ownsTeam from TeamRole teamRole where teamRole.reportTo= :id group by teamRole.name").setInteger("id", id).list();
	}

}
