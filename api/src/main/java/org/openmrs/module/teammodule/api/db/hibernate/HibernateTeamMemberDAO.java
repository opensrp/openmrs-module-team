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
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.openmrs.Location;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.db.TeamMemberDAO;
//import org.hibernate.Query;
//import org.hibernate.Query;
//import org.hibernate.transform.Transformers;
//import org.openmrs.Person;
//import org.openmrs.PersonName;
//import org.openmrs.module.teammodule.api.TeamMemberService;

/**
 * @author Muhammad Safwan
 * 
 */
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

	public void save(TeamMember teamMember) {
		sessionFactory.getCurrentSession().save(teamMember);
	}

	public void saveLocation(Location location) {
		sessionFactory.getCurrentSession().save(location);
	}

	@SuppressWarnings("unchecked")
	public List<TeamMember> getTeamMembers(Team team, String teamName, Integer teamLeadId, Boolean retired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TeamMember.class);

		if (team != null) {
			criteria.add(Restrictions.eq("team", team));
		}

		if (teamName != null) {
			criteria.add(Restrictions.eq("name", teamName));
		}

		if (teamLeadId != null) {
			criteria.add(Restrictions.eq("teamLeadId", teamLeadId));
		}

		if (retired != null) {
			criteria.add(Restrictions.eq("voided", retired));
		}

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TeamMember> getTeamMembers(Integer id) {
		return (List<TeamMember>) sessionFactory.getCurrentSession().createQuery("from TeamMember tm where tm.id = :id").setInteger("id", id).uniqueResult();
	}

	public TeamMember getMember(int id) {
		//System.out.println("getMember");
		return (TeamMember) sessionFactory.getCurrentSession().createQuery("from TeamMember tm where tm.id = :id").setInteger("id", id).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<TeamMember> getMemberByPersonId(int id) {
		System.out.println("Here");
		return sessionFactory.getCurrentSession().createQuery("from TeamMember tm where tm.person.personId = :id").setInteger("id", id).list();
	}

	@SuppressWarnings("unchecked")
	public List<TeamMember> getMember(String name) {
		return sessionFactory.getCurrentSession().createQuery("from TeamMember tm join tm.person p join p.names pn where pn.givenName = :name").setString("name", name).list();
	}

	@SuppressWarnings("unchecked")
	public List<TeamMember> getMembers(Date joinDateFrom, Date joinDateTo) {
		return sessionFactory.getCurrentSession().createQuery("from TeamMember tm where tm.joinDate between :from and :to").setDate("from", joinDateFrom).setDate("to", joinDateTo).list();
	}
	

	@SuppressWarnings("unchecked")
	public List<TeamMember> getLikeMember(String name) {
		return sessionFactory.getCurrentSession().createQuery("from TeamMember tm join tm.person p join p.names pn where pn.givenName like :name").setString("name", "%" + name + "%").list();
	}

	/*
	 * public SQLQuery getCount(Integer teamId){ return
	 * sessionFactory.getCurrentSession().createSQLQuery(
	 * "select count(*) from team_member where voided = 0 and team_id ="
	 * +teamId); }
	 */
	public void purgeMember(TeamMember teamMember) {
		sessionFactory.getCurrentSession().delete(teamMember);
	}

	@SuppressWarnings("unchecked")
	public List<TeamMember> getAllMembers(boolean retired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TeamMember.class);

		if (!retired) {
			criteria.add(Restrictions.eq("retired", false));
		}

		return criteria.list();
	}

	public void update(TeamMember teamMember) {
		sessionFactory.getCurrentSession().update(teamMember);
	}

	@SuppressWarnings("unchecked")
	public List<TeamMember> searchMember(String name) {
		/*
		 * return sessionFactory.getCurrentSession().createQuery(
		 * "from TeamMember tm join tm.person p join p.names pn where pn.givenName like :name"
		 * ).setString("name", "%" + name + "%").list();
		 */
		return sessionFactory.getCurrentSession()
				.createQuery("from TeamMember tm join tm.person p join p.names pn where pn.givenName like :name or pn.familyName like :name or tm.identifier like :name")
				.setString("name", "%" + name + "%").setResultTransformer(new ResultTransformer() {
					@SuppressWarnings("rawtypes")
					final Map<Integer, Date> tmMap = new LinkedHashMap();

					private static final long serialVersionUID = 1L;

					public Object transformTuple(Object[] result, String[] aliases) {
						TeamMember tm = (TeamMember) result[0];
						tmMap.put(tm.getTeamMemberId(), tm.getJoinDate());
						// Put in map so we can attach comment count later
						return tm;
					}

					@SuppressWarnings("rawtypes")
					public List transformList(List list) {
						return list;
					}
				}).list();
	}

	@SuppressWarnings("unchecked")
	public List<TeamMember> searchMemberByTeam(String name, int teamId) {
		/*
		 * return sessionFactory.getCurrentSession().createQuery(
		 * "from TeamMember tm join tm.person p join p.names pn where pn.givenName like :name"
		 * ).setString("name", "%" + name + "%").list();
		 */
		System.out.println(name + teamId);
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"from TeamMember tm join tm.person p join p.names pn join tm.team t where pn.givenName like :name or pn.familyName like :name or tm.identifier like :name and t.teamId = :teamId")
				.setString("name", "%" + name + "%").setInteger("teamId", teamId).setResultTransformer(new ResultTransformer() {
					@SuppressWarnings("rawtypes")
					final Map<Integer, Date> tmMap = new LinkedHashMap();

					private static final long serialVersionUID = 1L;

					public Object transformTuple(Object[] result, String[] aliases) {
						TeamMember tm = (TeamMember) result[0];
						tmMap.put(tm.getTeamMemberId(), tm.getJoinDate());
						// Put in map so we can attach comment count later
						return tm;
					}

					@SuppressWarnings("rawtypes")
					public List transformList(List list) {
						return list;
					}
				}).list();
	}

	public TeamMember getTeamMember(String uuid) {
		return (TeamMember) sessionFactory.getCurrentSession().createQuery("from TeamMember t where  t.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}
	/*
	 * public Query searchMember(String name) { return (Query)
	 * sessionFactory.getCurrentSession().createQuery(
	 * "from TeamMember tm join tm.person p join p.names pn where pn.givenName like :name"
	 * ).setString("name", "%" + name + "%") .setResultTransformer(new
	 * ResultTransformer() { final Map<Integer, Date> tmMap = new
	 * LinkedHashMap();
	 * 
	 * private static final long serialVersionUID = 1L; public Object
	 * transformTuple(Object[] result, String[] aliases) { TeamMember tm =
	 * (TeamMember) result[0]; tmMap.put(tm.getTeamMemberId(),
	 * tm.getJoinDate()); // Put in map so we can attach comment count later
	 * return null; } public List transformList(List list) { return list; }
	 * }).list(); }
	 */

}
