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
import org.openmrs.module.teammodule.TeamLead;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.db.TeamLeadDAO;

/**
 * @author Muhammad Safwan
 * 
 */
public class HibernateTeamLeadDAO implements TeamLeadDAO {

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

	public void save(TeamLead teamLead) {
		sessionFactory.getCurrentSession().save(teamLead);
	}

	@SuppressWarnings("unchecked")
	public List<TeamMember> getTeamMembers(Integer teamLeadId) {

		return (List<TeamMember>) sessionFactory.getCurrentSession().createQuery("from Team t where t.team_lead_id = :teamLeadId").setInteger("teamLeadId", teamLeadId).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TeamLead> getTeamLeads(Team team) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TeamLead.class);

		if (team != null) {
			criteria.add(Restrictions.eq("team", team));
		}

		return criteria.list();
	}

	public TeamLead getTeamLead(Team team) {
		return (TeamLead) sessionFactory.getCurrentSession().createQuery("from TeamLead tl where tl.team = :teamId and tl.voided = :num").setInteger("teamId", team.getTeamId()).setInteger("num", 0).uniqueResult();
		/*Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TeamLead.class);
		
		criteria.add(Restrictions.eq("voided", false));
		return (TeamLead) criteria.uniqueResult();*/
	}

	public void update(TeamLead teamLead) {
		sessionFactory.getCurrentSession().update(teamLead);
	}
	
	public void purgeTeamLead(TeamLead teamLead){
		sessionFactory.getCurrentSession().delete(teamLead);
	}
	
	public TeamLead getTeamLead(String uuid){
		return (TeamLead) sessionFactory.getCurrentSession().createQuery("from TeamLead t where t.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}
}
