package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
	public List<TeamRole> getAllTeamRole(boolean ownsTeam, boolean voided, Integer offset, Integer pageSize) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TeamRole.class);
		if (!ownsTeam) {
			criteria.add(Restrictions.eq("ownsTeam", false));
		}
		
		if (!voided) {
			criteria.add(Restrictions.eq("voided", false));
		}
		
		if (offset != null) {
			criteria.setFirstResult(offset);
		}
		
		if (pageSize != null) {
			criteria.setMaxResults(pageSize);
		}
		return criteria.list();
	}

	public void purgeTeamRole(TeamRole TeamRole) {
		sessionFactory.getCurrentSession().delete(TeamRole);
	}

	@SuppressWarnings("unchecked")
	public List<TeamRole> searchTeamRoleByRole(String role) {
		return (List<TeamRole>)sessionFactory.getCurrentSession().createQuery("from TeamRole teamRole where teamRole.name like :role").setString("role", role).list();
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
	public List<TeamRole> searchTeamRoleReportBy(Integer id) {
		return sessionFactory.getCurrentSession().createQuery("from TeamRole teamRole where teamRole.reportTo= :id").setInteger("id", id).list();
	}

}
