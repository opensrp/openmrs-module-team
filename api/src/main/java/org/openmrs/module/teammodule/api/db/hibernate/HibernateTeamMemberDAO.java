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
import org.openmrs.Patient;
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
	public TeamMember getTeamMemberById(Integer id) {
		return (TeamMember) sessionFactory.getCurrentSession().createQuery("from TeamMember t where t.teamMemberId = :id").setInteger("id", id).uniqueResult();
	}

	@Override
	public TeamMember getTeamMemberByName(String name) {
		return (TeamMember) sessionFactory.getCurrentSession().createQuery("from TeamMember tm join tm.person p join p.names pn where pn.givenName = :name").setString("name", name).uniqueResult();
	}

	@Override
	public List<TeamMember> getTeamMemberByIdentifier(String identifier) {
		return (List<TeamMember>) sessionFactory.getCurrentSession().createQuery("from TeamMember t where t.identifier = :identifier").setString("identifier", identifier).uniqueResult();
	}

	@Override
	public TeamMember getTeamMemberByUuid(String uuid) {
		return (TeamMember) sessionFactory.getCurrentSession().createQuery("from TeamMember t where t.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}

	@Override
	public List<TeamMember> getTeamMemberByTeamId(Integer teamId) {
		return (List<TeamMember>) sessionFactory.getCurrentSession().createQuery("from TeamMember tm join tm.team t where t.teamId = :teamId").setInteger("teamId", teamId).uniqueResult();
	}
	
	@Override
	public List<TeamMember> getTeamMemberByTeamName(String name) {
		return null;
	}

	@Override
	public List<TeamMember> getTeamMemberByPersonId(Integer personId) {
		return (List<TeamMember>) sessionFactory.getCurrentSession().createQuery("from TeamMember tm join tm.person p where p.personId = :personId").setInteger("personId", personId).uniqueResult();
	}

	@Override
	public List<TeamMember> getTeamMembersByDate(Date joinDateFrom, Date joinDateTo) {
		if(joinDateFrom != null && joinDateTo != null) { return (List<TeamMember>) sessionFactory.getCurrentSession().createQuery("from TeamMember t where t.joinDate = :joinDateFrom and t.leave_date = :joinDateTo").setDate("joinDateFrom", joinDateFrom).setDate("joinDateTo", joinDateTo).uniqueResult(); }
		else if(joinDateFrom != null && joinDateTo == null) { return (List<TeamMember>) sessionFactory.getCurrentSession().createQuery("from TeamMember t where t.joinDate = :joinDateFrom").setDate("joinDateFrom", joinDateFrom).uniqueResult(); }
		else if(joinDateFrom == null && joinDateTo != null) { return (List<TeamMember>) sessionFactory.getCurrentSession().createQuery("from TeamMember t where t.leaveDate = :joinDateTo").setDate("joinDateTo", joinDateTo).uniqueResult(); }
		else { return null; }
	}

	@Override
	public List<TeamMember> getTeamMembersByTeamLead(boolean isTeamLead) {
		return (List<TeamMember>) sessionFactory.getCurrentSession().createQuery("from TeamMember t where t.isTeamLead = :isTeamLead").setBoolean("isTeamLead", isTeamLead).uniqueResult();
	}

	@Override
	public List<TeamMember> getTeamMemberByRetired(boolean retired) {
		return (List<TeamMember>) sessionFactory.getCurrentSession().createQuery("from TeamMember t where t.voided = :retired").setBoolean("retired", retired).uniqueResult();
	}

	@Override
	public List<TeamMember> getTeamMemberByTeamRoleId(Integer teamRoleId) {
		// TODO
		return null;
	}
	
	@Override
	public List<TeamMember> getTeamMemberByLocationId(Integer id) {
		// TODO
		return null;
	}

	@Override
	public List<TeamMember> getTeamMemberByPatientId(Integer id) {
		// TODO
		return null;
	}
	
	@Override
	public List<TeamMember> getTeamMemberByTeam(Integer teamId, String teamName, Integer teamLeadId, Boolean retired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TeamMember.class);

		if (teamId != null) {
			criteria.add(Restrictions.eq("teamId", teamId));
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
	public List<TeamMember> getTeamMemberByTeamWithPage(Integer teamId, String teamName, Integer teamLeadId, Boolean retired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TeamMember.class).setFirstResult(100);
		
		if (teamId != null) {
			criteria.add(Restrictions.eq("teamId", teamId));
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
		return (List<TeamMember>) sessionFactory.getCurrentSession().createQuery("from TeamMember").setBoolean("retired", isRetired).uniqueResult();

		/*
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TeamMember.class);
		if (!isRetired) { criteria.add(Restrictions.eq("voided", false)); }
		return criteria.list();
		*/
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

	@Override
	public List<TeamMember> searchTeamMember(String name) {
		/*
		 * return sessionFactory.getCurrentSession().createQuery(
		 * "from TeamMember tm join tm.person p join p.names pn where pn.givenName like :name"
		 * ).setString("name", "%" + name + "%").list();
		 */
		return sessionFactory.getCurrentSession().createQuery("from TeamMember tm join tm.person p join p.names pn where pn.givenName like :name or pn.familyName like :name or tm.identifier like :name").setString("name", "%" + name + "%").setResultTransformer(new ResultTransformer() {
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
		return sessionFactory.getCurrentSession().createQuery("from TeamMember tm join tm.person p join p.names pn join tm.team t where pn.givenName like :name or pn.familyName like :name or tm.identifier like :name and t.teamId = :teamId").setString("name", "%" + name + "%").setInteger("teamId", teamId).setResultTransformer(new ResultTransformer() {
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
