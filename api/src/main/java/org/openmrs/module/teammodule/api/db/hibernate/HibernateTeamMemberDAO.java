/**
 * 
 */
package org.openmrs.module.teammodule.api.db.hibernate;

import java.lang.reflect.Method;
import java.util.ArrayList;
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
import org.openmrs.Person;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamRole;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.db.TeamMemberDAO;

/**
 * @author Zohaib Masood and Shakeeb Raza
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
	public List<TeamMember> getTeamMemberByPersonId(int personId) {
		return (List<TeamMember>) getCurrentSession().createQuery("from TeamMember teamMember where teamMember.person.personId = :id").setInteger("id", personId).list();
	}

	@Override
	public List<TeamMember> getTeamMemberByPersonIdentifier(String personIdentifier) {
		return (List<TeamMember>) getCurrentSession().createQuery("from TeamMember teamMember where teamMember.person.identifier = :id").setString("id", personIdentifier).list();
	}
	@Override
	public List<TeamMember> getAllTeamMember(Integer id, Boolean voided, Integer offset, Integer pageSize) {
			Criteria criteria = getCurrentSession().createCriteria(TeamMember.class).add(Restrictions.eq("voided", false));
			criteria.add(Restrictions.eq("teamMemberId", id));
			
			if(voided!=null)
			criteria.add(Restrictions.eq("voided", voided));
			
			if (offset != null) {
				criteria.setFirstResult(offset);
			}
			
			if (pageSize != null) {
				criteria.setMaxResults(pageSize);
			}
			return (List<TeamMember>) criteria.list();
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
	public List<TeamMember> searchTeamMember(String nameOrIdentifier, Integer supervisor, Integer teamRole,
			Integer team, Integer location, Date joinDateFrom, Date joinDateTo, String name,
			Boolean isdataprovider,
			Integer offset, Integer pageSize) {
		Criteria criteria = getCurrentSession().createCriteria(TeamMember.class).add(Restrictions.eq("voided", false));
		
		List<TeamMember> data=new ArrayList<TeamMember>();
		if (location != null) {
			criteria.createAlias("locations", "l").add(Restrictions.eq("l.locationId", location));
		}
		
		if (teamRole != null) {
			criteria.add(Restrictions.eq("teamRole.teamRoleId", teamRole));
		}
		
		if (nameOrIdentifier != null) {
			criteria.createAlias("person", "p").createAlias("p.names", "pn")
				.add(Restrictions.or(Restrictions.like("pn.givenName", "%"+nameOrIdentifier+"%"),Restrictions.or(Restrictions.like("pn.middleName", "%"+nameOrIdentifier+"%"),Restrictions.or(Restrictions.like("pn.familyName", "%"+nameOrIdentifier+"%"),Restrictions.like("identifier", "%"+nameOrIdentifier+"%")))));
		}
		
		if (team != null) {
			criteria.add(Restrictions.eq("team.teamId", team));
		}
		
		if (supervisor != null) {
			criteria.createAlias("team", "t").add(Restrictions.eq("t.supervisor.teamMemberId", supervisor));
		}
		
		if (offset != null) {
			criteria.setFirstResult(offset);
		}
		
		if (pageSize != null) {
			criteria.setMaxResults(pageSize);
		}
		
		if (isdataprovider != null) {
			criteria.add(Restrictions.eq("isdataprovider", isdataprovider));
			System.out.println("location");
		}
		
		data.addAll((List<TeamMember>) criteria.list());
		
		String query = "";

		if (name != null) {
		if((joinDateFrom != null && joinDateTo != null))
		{
			query += "from TeamMember teamMember join teamMember.person person join person.names personName where personName.givenName like " + name + " or personName.familyName like " + name + " or teamMember.identifier like " + name + " and teamMember.joinDate between " + joinDateFrom.toString() + " and " + joinDateTo.toString();
		}
		else {
			query += " from TeamMember teamMember where teamMember.joinDate between " + joinDateFrom.toString() + " and " + joinDateTo.toString();
		}
		data.addAll((List<TeamMember>) getCurrentSession().createQuery(query).list());
		}
		return data;
	}
		
	@Override
	public int countTeamMemberByTeam(Integer teamId) {
		
		return (int) getCurrentSession().createQuery("select count(*) from TeamMember teammember where team= "+ teamId).list().get(0);
	}

	@Override
	public int countTeamMemberByTeamRole(Integer teamRoleId) {
		return  (int) getCurrentSession().createQuery("select count(*) from TeamMember teammember where teamRole= "+ teamRoleId).list().get(0);
			
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
