/**
 * 
 */
package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.ArrayList;
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
import org.openmrs.Patient;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.db.TeamMemberDAO;


/**
 * @author Muhammad Safwan
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
	public List<TeamMember> getTeamMemberByIdentifier(String identifier) {
		return (List<TeamMember>) sessionFactory.getCurrentSession().createQuery("from TeamMember teamMember where teamMember.identifier = :identifier").setString("identifier", identifier).uniqueResult();
	}

	@Override
	public TeamMember getTeamMemberByUuid(String uuid) {
		return (TeamMember) sessionFactory.getCurrentSession().createQuery("from TeamMember teamMember where teamMember.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}

	@Override
	public List<TeamMember> getTeamMemberByTeamId(Integer teamId) {
		return (List<TeamMember>) sessionFactory.getCurrentSession().createQuery("from TeamMember teamMember where teamMember.team = :teamId").setInteger("teamId", teamId).uniqueResult();
	}

	@Override
	public List<TeamMember> getTeamMemberByPersonId(Integer personId) {
		return sessionFactory.getCurrentSession().createQuery("from TeamMember teamMember where teamMember.person.personId = :id").setInteger("id", personId).list();
	}

	@Override
	public List<TeamMember> getTeamMemberByDate(Date joinDateFrom, Date joinDateTo) {
		return sessionFactory.getCurrentSession().createQuery("from TeamMember teamMember where teamMember.joinDate between :from and :to").setDate("from", joinDateFrom).setDate("to", joinDateTo).list();
	}

	@Override
	public List<TeamMember> getTeamMemberByTeamLead(boolean isTeamLead) {
		return (List<TeamMember>) sessionFactory.getCurrentSession().createQuery("from TeamMember teamMember where teamMember.isTeamLead = :isTeamLead").setBoolean("isTeamLead", isTeamLead).uniqueResult();
	}

	@Override
	public List<TeamMember> getTeamMemberByRetired(boolean retired) {
		return (List<TeamMember>) sessionFactory.getCurrentSession().createQuery("from TeamMember teamMember where teamMember.voided = :retired").setBoolean("retired", retired).uniqueResult();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<TeamMember> getTeamMemberByLocationId(Integer id) {
		List<TeamMember> teamMembers = new ArrayList();
		
		List<Object> result = sessionFactory.getCurrentSession().createQuery("select teamMember.teamMemberId  from TeamMember teamMember join teamMember.location location where location.locationId = :id").setInteger("id", id).list();
		
		String query = "from TeamMember teamMember where  ";
		for (int i = 0; i < result.size(); i++) {
			query += "teamMember.teamMemberId =" + result.get(i);
			System.out.println(result.get(i));
			if(i<(result.size()-1)) {
				 query+= " OR ";
			}
		}
		
		teamMembers = sessionFactory.getCurrentSession().createQuery(query).list();
		return teamMembers;
	}
	
	@Override
	public List<TeamMember> getTeamMemberByTeam(Team team, String teamName, Integer teamLeadId, Boolean retired) {
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
	
	@Override
	public List<TeamMember> getTeamMemberByTeamWithPage(Team team, String teamName, Integer teamLeadId, Boolean retired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TeamMember.class).setFirstResult(100);
		
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
	
	
	@Override
	public List<TeamMember> getAllTeamMember(boolean isRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TeamMember.class);

		if (!isRetired) {
			criteria.add(Restrictions.eq("retired", false));
		}

		return criteria.list();
	}

	@Override
	public void save(TeamMember teamMember) {
		sessionFactory.getCurrentSession().saveOrUpdate(teamMember);
	}

	@Override
	public void saveLocation(Location location) {
		sessionFactory.getCurrentSession().saveOrUpdate(location);
	}

	@Override
	public void savePatient(Patient patient) {
		sessionFactory.getCurrentSession().saveOrUpdate(patient);
	}

	@Override
	public void purgeMember(TeamMember teamMember) {
		sessionFactory.getCurrentSession().delete(teamMember);
	}

	@Override
	public void update(TeamMember teamMember) {
		sessionFactory.getCurrentSession().update(teamMember);
	}

//	@SuppressWarnings("unchecked")
	@Override
	public List<TeamMember> searchTeamMember(String name) {
		/*
		 * return sessionFactory.getCurrentSession().createQuery(
		 * "from TeamMember tm join tm.person p join p.names pn where pn.givenName like :name"
		 * ).setString("name", "%" + name + "%").list();
		 */
		return sessionFactory.getCurrentSession().createQuery("from TeamMember teamMember join teamMember.person person join person.names personName where personName.givenName like :name or personName.familyName like :name or teamMember.identifier like :name").setString("name", "%" + name + "%").setResultTransformer(new ResultTransformer() {
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
	
	@Override
	public List<TeamMember> searchTeamMemberByTeam(String name, int teamId) {
		/*
		 * return sessionFactory.getCurrentSession().createQuery(
		 * "from TeamMember tm join tm.person p join p.names pn where pn.givenName like :name"
		 * ).setString("name", "%" + name + "%").list();
		 */
		return sessionFactory.getCurrentSession().createQuery("from TeamMember teamMember join teamMember.person person join person.names personName join teamMember.team team where personName.givenName like :name or personName.familyName like :name or teamMember.identifier like :name and team.teamId = :teamId").setString("name", "%" + name + "%").setInteger("teamId", teamId).setResultTransformer(new ResultTransformer() {
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
}
