package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.teammodule.TeamLocation;
import org.openmrs.module.teammodule.api.db.TeamLocationDAO;

@SuppressWarnings("unchecked")
public class HibernateTeamLocationDAO implements TeamLocationDAO{


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

	public void saveTeamLocation(TeamLocation TeamLocation) {
		sessionFactory.getCurrentSession().saveOrUpdate(TeamLocation);
		
	}

	public TeamLocation getTeamLocation(int id) {
		return	(TeamLocation)sessionFactory.getCurrentSession().createQuery("from TeamLocation tl where tl.teamLocationId = :id").setInteger("id", id).uniqueResult();
	}

	public void purgeTeamLocation(TeamLocation teamLocation) {
		sessionFactory.getCurrentSession().delete(teamLocation);
	}

	public List<TeamLocation> searchLocationByLocation(String location) {
		return (List<TeamLocation>)sessionFactory.getCurrentSession().createQuery("from TeamLocation tl join tl.location l where l.locationId = :location").setString("location", location).list();
	}
	
	public List<TeamLocation> getAllLocation() {
		return (List<TeamLocation>)sessionFactory.getCurrentSession().createQuery("from TeamLocation tl").list();
	}

	public TeamLocation getTeamLocationByTeamId(Integer id) {
		return	(TeamLocation)sessionFactory.getCurrentSession().createQuery("from TeamLocation tl join tl.team t where t.teamId = :id").setInteger("id", id).uniqueResult();
	}
	
	public TeamLocation getTeamLocation(String uuid) {
		return	(TeamLocation)sessionFactory.getCurrentSession().createQuery("from TeamLocation tl where tl.uuid = :uuid").setString("uuid", uuid).uniqueResult();
	}
}
