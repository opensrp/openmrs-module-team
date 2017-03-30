package org.openmrs.module.teammodule.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamHierarchy;
import org.openmrs.module.teammodule.TeamLocation;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamHierarchyService;
import org.openmrs.module.teammodule.api.db.TeamHierarchyDAO;
import org.openmrs.module.teammodule.api.db.TeamLocationDAO;

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
		return	(TeamLocation)sessionFactory.getCurrentSession().createQuery("from TeamLocation tl where tl.team_location_id = :id").setInteger("id", id).uniqueResult();
	}

	public void purgeTeamLocation(TeamLocation teamLocation) {
		sessionFactory.getCurrentSession().delete(teamLocation);
	}

	public List<TeamLocation> searchLocationByLocation(String location) {
		return (List<TeamLocation>)sessionFactory.getCurrentSession().createQuery("from TeamLocation tl where tl.team_Location = :location").setString("location", location);
	}

}
