/**
 * 
 */
package org.openmrs.module.teammodule.api.db.hibernate;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.openmrs.Location;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamRole;
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
		return (TeamMember) getCurrentSession().createQuery("from TeamMember teamMember where teamMember.id = :id").setInteger("id", id).uniqueResult();
	}

	@Override
	public TeamMember getTeamMemberByUuid(String uuid) {
		return (TeamMember) getCurrentSession().createQuery("from TeamMember teamMember where teamMember.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}
	
	@Override
	public List<TeamMember> getTeamMemberByPersonId(Integer personId) {
		return (List<TeamMember>) getCurrentSession().createQuery("from TeamMember teamMember where teamMember.person.personId = :id").setInteger("id", personId).list();
	}

	@Override
	public List<TeamMember> getAllTeamMember(Integer id, boolean voided, Integer offset, Integer pageSize) {
		if(id != null) {// id is not null
			if (!voided) { // get by team member id & voided
				Criteria criteria_member = getCurrentSession().createCriteria(TeamMember.class).add(Restrictions.eq("voided", false));
				criteria_member.add(Restrictions.eq("teamMemberId", id));
				if(criteria_member.list() == null) { // get by team id & voided
					Criteria criteria_team = getCurrentSession().createCriteria(TeamMember.class).add(Restrictions.eq("voided", false));
					criteria_team.add(Restrictions.eq("team", id));
					if(criteria_team.list() == null) { // get by role id & voided
						Criteria criteria_role = getCurrentSession().createCriteria(TeamMember.class).add(Restrictions.eq("voided", false));
						criteria_role.add(Restrictions.eq("personId", id));
						if(criteria_role.list() == null) {
							return null;
						}
						else { 
							return (List<TeamMember>) criteria_role.list(); 
						}
					} 
					else { return (List<TeamMember>) criteria_team.list(); }					
				}
				else { 
					return (List<TeamMember>) criteria_member.list(); 
				}
			}
			else { // get by team member id
				Criteria criteria_member = getCurrentSession().createCriteria(TeamMember.class);
				criteria_member.add(Restrictions.eq("teamMemberId", id));
				if(criteria_member.list() == null) { // get by team id
					Criteria criteria_team = getCurrentSession().createCriteria(TeamMember.class);
					criteria_team.add(Restrictions.eq("team", id));
					if(criteria_team.list() == null) { // get by person id
						Criteria criteria_person = getCurrentSession().createCriteria(TeamMember.class);
						criteria_person.add(Restrictions.eq("personId", id));
						if(criteria_person.list() == null) { // get by role id
							Criteria criteria_role = getCurrentSession().createCriteria(TeamMember.class);
							criteria_role.add(Restrictions.eq("personId", id));
							if(criteria_role.list() == null) {
								return null;
							} 
							else { 
								return (List<TeamMember>) criteria_role.list(); 
							}
						} 
						else {
							return (List<TeamMember>) criteria_person.list(); 
						}
					} 
					else { 
						return (List<TeamMember>) criteria_team.list(); 
					}					
				} 
				else { 
					return (List<TeamMember>) criteria_member.list(); 
				}
			}
		}
		else {// id is null
			Criteria criteria = getCurrentSession().createCriteria(TeamMember.class);
			if (!voided) {
				criteria.add(Restrictions.eq("voided", false));
			}
			
			if (offset != null) {
				criteria.setFirstResult(offset);
			}
			
			if (pageSize != null) {
				criteria.setMaxResults(pageSize);
			}
			return (List<TeamMember>) criteria.list();
		} 
	}

	@Override
	public void saveTeamMember(TeamMember teamMember) {
		getCurrentSession().saveOrUpdate(teamMember);
	}

	@Override
	public void purgeTeamMember(TeamMember teamMember) {
		getCurrentSession().delete(teamMember);
	}

	@Override
	public void updateTeamMember(TeamMember teamMember) {
		getCurrentSession().update(teamMember);
	}

	@Override
	public List<TeamMember> searchTeamMemberByTeam(Integer teamId) {
		return (List<TeamMember>) getCurrentSession().createQuery("from TeamMember teamMember where teamMember.team = :teamId").setInteger("teamId", teamId).list();
	}
	
	@Override
	public List<TeamMember> searchTeamMember(String identifier, TeamMember supervisor, TeamRole teamRole, Team team, Location location, Integer offset, Integer pageSize) {
		Criteria criteria = getCurrentSession().createCriteria(TeamMember.class).add(Restrictions.eq("voided", false));

		if (location != null) {
			criteria.createAlias("locations", "l").add(Restrictions.eq("l.locationId", location.getId()));
		}
		
		if (teamRole != null) {
			criteria.add(Restrictions.eq("teamRole", teamRole));
		}
		
		if (identifier != null) {
			criteria.createAlias("person", "p").createAlias("p.names", "pn").add(Restrictions.or(Restrictions.like("pn.givenName", "%"+identifier+"%"),Restrictions.or(Restrictions.like("pn.middleName", "%"+identifier+"%"),Restrictions.or(Restrictions.like("pn.familyName", "%"+identifier+"%"),Restrictions.like("identifier", "%"+identifier+"%")))));
		}
		
		if (team != null) {
			criteria.add(Restrictions.eq("team", team));
		}
		
		if (supervisor != null) {
			criteria.createAlias("team", "t").add(Restrictions.eq("t.supervisor", supervisor));
		}
		
		if (offset != null) {
			criteria.setFirstResult(offset);
		}
		
		if (pageSize != null) {
			criteria.setMaxResults(pageSize);
		}
		
		return (List<TeamMember>) criteria.list();
	}
			
	@SuppressWarnings("serial")
	@Override
	public List<TeamMember> searchTeamMember(Date joinDateFrom, Date joinDateTo, String name, Integer offset, Integer pageSize) {
		String query = "from TeamMember teamMember ";

		if (name != null && (joinDateFrom != null && joinDateTo != null)) {
			query += "join teamMember.person person join person.names personName where personName.givenName like " + name + " or personName.familyName like " + name + " or teamMember.identifier like " + name + " and teamMember.joinDate between " + joinDateFrom.toString() + " and " + joinDateTo.toString();
		}
		
		else if (name == null && (joinDateFrom != null && joinDateTo != null)) {
			query += " where teamMember.joinDate between " + joinDateFrom.toString() + " and " + joinDateTo.toString();
		}

		return (List<TeamMember>) getCurrentSession().createQuery(query).setResultTransformer(new ResultTransformer() {
			@SuppressWarnings("rawtypes")
			final Map<Integer, Date> tmMap = new LinkedHashMap();
			public Object transformTuple(Object[] result, String[] aliases) { TeamMember tm = (TeamMember) result[0]; tmMap.put(tm.getTeamMemberId(), tm.getJoinDate()); return tm; }
			@SuppressWarnings("rawtypes")
			public List transformList(List list) { return list; }
		}).list();
	}

	@Override
	public int countTeam(Integer teamId) {
		return (int) getCurrentSession().createQuery("from TeamMember teammember where team= :teamId").setInteger("teamId", teamId).list().size();
	}

	@Override
	public int countTeamRole(Integer teamRoleId) {
		return (int) getCurrentSession().createQuery("from TeamMember teammember where teamRole= :teamRoleId").setInteger("teamRoleId", teamRoleId).list().size();
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
				throw new RuntimeException("Failed to get the current hibernate session from HibernateTeamMemberDAO", e);
			}
		}
	}
}
