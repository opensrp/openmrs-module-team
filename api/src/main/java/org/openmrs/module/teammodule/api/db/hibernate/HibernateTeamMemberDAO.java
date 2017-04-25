/**
 * 
 */
package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.openmrs.Location;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamHierarchy;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.db.TeamMemberDAO;

/**
 * @author Zohaib Masood
 * 
 */
@SuppressWarnings("unchecked")
public class HibernateTeamMemberDAO implements TeamMemberDAO {

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

	@Override
	public TeamMember getTeamMember(Integer id) {
		return (TeamMember) sessionFactory.getCurrentSession().createQuery("from TeamMember teamMember where teamMember.id = :id").setInteger("id", id).uniqueResult();
	}

	@Override
	public TeamMember getTeamMemberByUuid(String uuid) {
		return (TeamMember) sessionFactory.getCurrentSession().createQuery("from TeamMember teamMember where teamMember.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}
	
	@Override
	public List<TeamMember> getTeamMemberByPersonId(Integer personId) {
		return sessionFactory.getCurrentSession().createQuery("from TeamMember teamMember where teamMember.person.personId = :id").setInteger("id", personId).list();
	}

	@Override
	public List<TeamMember> getAllTeamMember(Integer id, boolean voided) {
	
		if(id != null) {// id is not null
			if (!voided) { // get by team member id & voided
				Criteria criteria_member = sessionFactory.getCurrentSession().createCriteria(TeamMember.class).add(Restrictions.eq("voided", false));
				criteria_member.add(Restrictions.eq("teamMemberId", id));
				if(criteria_member.list() == null) { // get by team id & voided
					Criteria criteria_team = sessionFactory.getCurrentSession().createCriteria(TeamMember.class).add(Restrictions.eq("voided", false));
					criteria_team.add(Restrictions.eq("team", id));
					if(criteria_team.list() == null) { // get by role id & voided
						Criteria criteria_role = sessionFactory.getCurrentSession().createCriteria(TeamMember.class).add(Restrictions.eq("voided", false));
						criteria_role.add(Restrictions.eq("personId", id));
						if(criteria_role.list() == null) {
							return null;
						} else { return criteria_role.list(); }
					} else { return criteria_team.list(); }					
				} else { return criteria_member.list(); }
			}
			else { // get by team member id
				Criteria criteria_member = sessionFactory.getCurrentSession().createCriteria(TeamMember.class);
				criteria_member.add(Restrictions.eq("teamMemberId", id));
				if(criteria_member.list() == null) { // get by team id
					Criteria criteria_team = sessionFactory.getCurrentSession().createCriteria(TeamMember.class);
					criteria_team.add(Restrictions.eq("team", id));
					if(criteria_team.list() == null) { // get by person id
						Criteria criteria_person = sessionFactory.getCurrentSession().createCriteria(TeamMember.class);
						criteria_person.add(Restrictions.eq("personId", id));
						if(criteria_person.list() == null) { // get by role id
							Criteria criteria_role = sessionFactory.getCurrentSession().createCriteria(TeamMember.class);
							criteria_role.add(Restrictions.eq("personId", id));
							if(criteria_role.list() == null) {
								return null;
							} else { return criteria_role.list(); }
						} else { return criteria_person.list(); }
					} else { return criteria_team.list(); }					
				} else { return criteria_member.list(); }
			}
		}
		else {// id is null
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TeamMember.class);
			if (!voided) {
				criteria.add(Restrictions.eq("voided", false));
			}
			return criteria.list();
		} 
	}

	@Override
	public void saveTeamMember(TeamMember teamMember) {
		sessionFactory.getCurrentSession().saveOrUpdate(teamMember);
	}

	@Override
	public void purgeTeamMember(TeamMember teamMember) {
		sessionFactory.getCurrentSession().delete(teamMember);
	}

	@Override
	public void updateTeamMember(TeamMember teamMember) {
		sessionFactory.getCurrentSession().update(teamMember);
	}

	@Override
	public List<TeamMember> searchTeamMemberByTeam(Integer teamId) {
		return sessionFactory.getCurrentSession().createQuery("from TeamMember teamMember where teamMember.team = :teamId").setInteger("teamId", teamId).list();
	}
	
	//id - TODO
	@Override
	public List<TeamMember> searchTeamMember(String identifier, Integer supervisor, TeamHierarchy teamRole, Team team, Location location, Integer offset, Integer pageSize) {
		String queryStr = " select tm.* from team_member tm ";

		if (location != null) {
			queryStr += " inner join location l on l.location_id=" + Integer.toString(location.getId()) + " ";
			System.out.println("location");
		}
		
		if (teamRole != null) {
			queryStr += " inner join team_role tr on tr.team_role_id=" + Integer.toString(teamRole.getId()) + " ";
			System.out.println("teamRole");
		}
		
		if (team != null) {
			queryStr += " inner join team t on t.team_id=" + Integer.toString(team.getId()) + " ";
			System.out.println("team-if");
			if (supervisor != null && identifier != null) {
				queryStr += " where t.supervisor_id=" + Integer.toString(supervisor) + " and tm.identifier like '%" + identifier + "%' ";
				System.out.println("supervisor and identifier");
			}
			else if (supervisor != null && identifier == null) {
				queryStr += " where t.supervisor_id=" + Integer.toString(supervisor) + " ";
				System.out.println("supervisor");
			}
			else if (supervisor == null && identifier != null) {
				queryStr += " where tm.identifier like '%" + identifier + "%' ";
				System.out.println("identifier");
			}
		}
		else {//if (team == null) {
			System.out.println("team-else");
			if (supervisor != null && identifier != null) {
				queryStr += " inner join team t where t.supervisor_id=" + Integer.toString(supervisor) + " and tm.identifier like '%" + identifier + "%' ";
				System.out.println("supervisor and identifier");
			}
			else if (supervisor != null && identifier == null) {
				queryStr += " inner join team t where t.supervisor_id=" + Integer.toString(supervisor) + " ";
				System.out.println("supervisor");
			}
			else if (supervisor == null && identifier != null) {
				queryStr += " where tm.identifier like '%" + identifier + "%' ";
				System.out.println("identifier");
			}
		}

		System.out.println("queryStr: " + queryStr);
		Query q = sessionFactory.getCurrentSession().createSQLQuery(queryStr);
		
		if (offset != null) {
			q.setFirstResult(offset);
		}
		
		if (pageSize != null) {
			q.setMaxResults(pageSize);
		}
		
		return q.list();
	}
			
	@SuppressWarnings("serial")
	@Override
	public List<TeamMember> searchTeamMember(Date joinDateFrom, Date joinDateTo, String name) {
		String query = "from TeamMember teamMember ";

		if (name != null && (joinDateFrom != null && joinDateTo != null)) {
			query += "join teamMember.person person join person.names personName where personName.givenName like " + name + " or personName.familyName like " + name + " or teamMember.identifier like " + name + " and teamMember.joinDate between " + joinDateFrom.toString() + " and " + joinDateTo.toString();
		}
		
		else if (name == null && (joinDateFrom != null && joinDateTo != null)) {
			query += " where teamMember.joinDate between " + joinDateFrom.toString() + " and " + joinDateTo.toString();
		}

		//return sessionFactory.getCurrentSession().createQuery(query).list();
		
		return sessionFactory.getCurrentSession().createQuery(query).setResultTransformer(new ResultTransformer() {
			@SuppressWarnings("rawtypes")
			final Map<Integer, Date> tmMap = new LinkedHashMap();
			public Object transformTuple(Object[] result, String[] aliases) { TeamMember tm = (TeamMember) result[0]; tmMap.put(tm.getTeamMemberId(), tm.getJoinDate()); return tm; }
			@SuppressWarnings("rawtypes")
			public List transformList(List list) { return list; }
		}).list();/**/
	}
}
