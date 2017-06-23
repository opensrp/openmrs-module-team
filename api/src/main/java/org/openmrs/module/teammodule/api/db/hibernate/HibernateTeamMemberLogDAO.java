package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.TeamMemberLog;
import org.openmrs.module.teammodule.api.db.TeamMemberLogDAO;

public class HibernateTeamMemberLogDAO implements TeamMemberLogDAO{


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

	public void saveTeamMemberLog(TeamMemberLog teamMemberLog) {
		sessionFactory.getCurrentSession().saveOrUpdate(teamMemberLog);
	}

	public void updateTeamMemberLog(TeamMemberLog teamMemberLog) {
		sessionFactory.getCurrentSession().update(teamMemberLog);
	}

	public TeamMemberLog getTeamMemberLog(Integer id) {
		return	(TeamMemberLog)sessionFactory.getCurrentSession().createQuery("from TeamMemberLog teamMemberLog where teamMemberLog.logId = :id").setInteger("id", id).uniqueResult();
	}

	public TeamMemberLog getTeamMemberLog(String uuid) {
		return	(TeamMemberLog)sessionFactory.getCurrentSession().createQuery("from TeamMemberLog teamMemberLog where teamMemberLog.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<TeamMemberLog> getAllLogs(Integer offset, Integer pageSize) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TeamMember.class);
		if (pageSize != null) {
			criteria.setFirstResult(offset);
		}
		if (pageSize != null) {
			criteria.setMaxResults(pageSize);
		}
		return criteria.list();
	}

	public void purgeTeamMemberLog(TeamMemberLog teamMemberLog) {
		sessionFactory.getCurrentSession().delete(teamMemberLog);
	}

	@SuppressWarnings("unchecked")
	public List<TeamMemberLog> searchTeamMemberLogByTeamMember(Integer teamMember, Integer offset, Integer pageSize) {
		Query createQuery =sessionFactory.getCurrentSession().createQuery("from TeamMemberLog teamMemberLog where teamMemberLog.teamMember = :teamMember").setInteger("teamMember", teamMember);
		if(offset != null) {
			createQuery.setFirstResult(offset);
		}
		if(pageSize != null) {
			createQuery.setMaxResults(pageSize);	
		}
		return createQuery.list();
	}
}
