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
	public TeamMember getTeamMemberByUuid(String uuid) {
		return (TeamMember) sessionFactory.getCurrentSession().createQuery("from TeamMember teamMember where teamMember.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}

	@Override
	public List<TeamMember> getTeamMemberByDate(Date joinDateFrom, Date joinDateTo) {
		return sessionFactory.getCurrentSession().createQuery("from TeamMember teamMember where teamMember.joinDate between :from and :to").setDate("from", joinDateFrom).setDate("to", joinDateTo).list();
	}
	
	
	@Override
	public List<TeamMember> getTeamMemberByPersonId(Integer personId) {
		return sessionFactory.getCurrentSession().createQuery("from TeamMember teamMember where teamMember.person.personId = :id").setInteger("id", personId).list();
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
	
	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	public List<TeamMember> getTeamMemberByPatientId(Integer id) {
		List<TeamMember> teamMembers = new ArrayList();
		
		List<Object> result = sessionFactory.getCurrentSession().createQuery("select teamMember.teamMemberId  from TeamMember teamMember join teamMember.patient patient where patient.patientId = :id").setInteger("id", id).list();
		
		String query = "from TeamMember teamMember where  ";
		for (int i = 0; i < result.size(); i++) {
			query += "teamMember.teamMemberId =" + result.get(i);
			System.out.println(result.get(i));
			if(i<(result.size()-1)) {
				 query+= " OR ";
			}
		}
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
	public List<TeamMember> getTeamMemberByTeamWithPage(Team team, String teamName, Integer teamLeadId, Boolean retired, Integer pageSize) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TeamMember.class).setFirstResult(pageSize);
		
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
	public List<TeamMember> getAllTeamMember(Integer id, boolean isRetired) {
	
		if(id != null) {// id is not null
			if (!isRetired) { // get by team member id & retired
				Criteria criteria_member = sessionFactory.getCurrentSession().createCriteria(TeamMember.class).add(Restrictions.eq("retired", false));
				criteria_member.add(Restrictions.eq("teamMemberId", id));
				if(criteria_member.list() == null) { // get by team id & retired
					Criteria criteria_team = sessionFactory.getCurrentSession().createCriteria(TeamMember.class).add(Restrictions.eq("retired", false));
					criteria_team.add(Restrictions.eq("team", id));
					if(criteria_team.list() == null) { // get by role id & retired
						Criteria criteria_role = sessionFactory.getCurrentSession().createCriteria(TeamMember.class).add(Restrictions.eq("retired", false));
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
			if (!isRetired) {
				criteria.add(Restrictions.eq("retired", false));
			}
			return criteria.list();
		} 
	}

	@Override
	public void save(TeamMember teamMember) {
		sessionFactory.getCurrentSession().saveOrUpdate(teamMember);
	}

	@Override
	public void saveLocation(Location location) {
		System.out.println("saveLocation: " + location);
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
	public List<TeamMember> searchTeamMember(String id, String supervisor, String teamRole, String team, String location) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TeamMember.class);
		
		if (id != null) {
			criteria.add(Restrictions.eq("teamMemberIdentifier", id));
		}
		
		if (supervisor != null) {
			criteria.add(Restrictions.eq("supervisorId", supervisor));
		}

		if (teamRole != null) {
			criteria.add(Restrictions.eq("teamRole", teamRole));
		}

		if (team != null) {
			criteria.add(Restrictions.eq("team", team));
		}

		if (location != null) {
			criteria.add(Restrictions.eq("location", location));
		}
		
		return criteria.list();
	}
	
	@Override
	public List<TeamMember> searchTeamMemberWithPage(String id, String supervisor, String teamRole, String team, String location, Integer pageSize) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TeamMember.class).setFirstResult(pageSize);
		
		if (id != null) {
			criteria.add(Restrictions.eq("teamMemberIdentifier", id));
		}
		
		if (supervisor != null) {
			criteria.add(Restrictions.eq("supervisorId", supervisor));
		}

		if (teamRole != null) {
			criteria.add(Restrictions.eq("teamRole", teamRole));
		}

		if (team != null) {
			criteria.add(Restrictions.eq("team", team));
		}

		if (location != null) {
			criteria.add(Restrictions.eq("location", location));
		}
		
		return criteria.list();
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
