package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.teammodule.TeamMemberLocation;
import org.openmrs.module.teammodule.api.db.TeamMemberLocationDAO;

@SuppressWarnings("unchecked")
public class HibernateTeamMemberLocationDAO implements TeamMemberLocationDAO{


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

	public void saveTeamMemberLocation(TeamMemberLocation teamMemberLocation) {
		sessionFactory.getCurrentSession().saveOrUpdate(teamMemberLocation);
		
	}

	public TeamMemberLocation getTeamMemberLocation(int id) {
		return	(TeamMemberLocation)sessionFactory.getCurrentSession().createQuery("from TeamMemberLocation tl where tl.teamMemberLocationId = :id").setInteger("id", id).uniqueResult();
	}

	public TeamMemberLocation getTeamMemberLocation(String uuid) {
		return	(TeamMemberLocation)sessionFactory.getCurrentSession().createQuery("from TeamMemberLocation tl where tl.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}
	
	public void purgeTeamMemberLocation(TeamMemberLocation teamMemberLocation) {
		sessionFactory.getCurrentSession().delete(teamMemberLocation);
	}

	public List<TeamMemberLocation> searchLocationByLocation(String location) {
		List<TeamMemberLocation> asdf = (List<TeamMemberLocation>) sessionFactory.getCurrentSession().createQuery("from TeamMemberLocation tl where tl.location = :location").setString("location", location).list();
		
		//System.out.println(asdf.get(0).getId());
		
		return asdf;//(List<TeamMemberLocation>)sessionFactory.getCurrentSession().createQuery("from TeamMemberLocation tl join tl.location l where l.locationId = :location").setString("location", location).list();
	}
	
	public List<TeamMemberLocation> getAllLocation() {
		return (List<TeamMemberLocation>)sessionFactory.getCurrentSession().createQuery("from TeamMemberLocation tl").list();
	}

	public TeamMemberLocation getTeamMemberLocationByTeamMemberId(Integer id) {
		return	(TeamMemberLocation)sessionFactory.getCurrentSession().createQuery("from TeamMemberLocation tl join tl.teamMember tm where tm.teamMemberId = :id").setInteger("id", id).uniqueResult();
	}
}
