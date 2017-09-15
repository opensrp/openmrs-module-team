package org.openmrs.module.teammodule.api.db.hibernate;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void saveTeamRole(TeamRole TeamRole) {
		getCurrentSession().save(TeamRole);
	}

	public void updateTeamRole(TeamRole TeamRole) {
		getCurrentSession().update(TeamRole);
	}

	public TeamRole getTeamRoleById(Integer id) {
		return (TeamRole) getCurrentSession().createQuery("from TeamRole teamRole where teamRole.teamRoleId = :id").setInteger("id", id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TeamRole> getAllTeamRole(Boolean ownsTeam, Boolean voided, Integer offset, Integer pageSize) {
		Criteria criteria = getCurrentSession().createCriteria(TeamRole.class);
		if (ownsTeam!=null) {//TODO != null
			criteria.add(Restrictions.eq("ownsTeam", ownsTeam));
		}
		
		if (voided!=null) {//TODO != null
			criteria.add(Restrictions.eq("voided", voided));
		}
		
		if (offset != null) {
			criteria.setFirstResult(offset);
		}
		
		if (pageSize != null) {
			criteria.setMaxResults(pageSize);
		}
		return (List<TeamRole>) criteria.list();
	}

	public void purgeTeamRole(TeamRole TeamRole) {
		getCurrentSession().delete(TeamRole);
	}

	@SuppressWarnings("unchecked")
	public List<TeamRole> searchTeamRole(String role) {
		return (List<TeamRole>) getCurrentSession().createQuery("from TeamRole teamRole where teamRole.name = :role or teamRole.identifier = :role").setString("role", role).list();
	}
	
	public TeamRole getTeamRoleByUuid(String uuid) {
		return (TeamRole) getCurrentSession().createQuery("from TeamRole teamRole where teamRole.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TeamRole> getSubTeamRoles(Integer teamRoleId) {
		return (List<TeamRole>) getCurrentSession().createQuery("from TeamRole teamRole where teamRole.reportTo = :id").setInteger("id", teamRoleId).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TeamRole> searchTeamRoleReportBy(Integer id) {
		return (List<TeamRole>) getCurrentSession().createQuery("from TeamRole teamRole where teamRole.reportTo= :id").setInteger("id", id).list();
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
				throw new RuntimeException("Failed to get the current hibernate session from HibernateTeamRoleDAO", e);
			}
		}
	}

}
