package org.sonatype.mavenbook.persist;

import java.util.List;

import org.hibernate.Hibernate;
import org.sonatype.mavenbook.model.Locations;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LocationsDAOImpl implements LocationsDAO {
	
	private HibernateTemplate  hibernateTemplate;
	private final String LOCATIONS = "Locations.byWoeid";
	private final String WOEID = "woeid";
	private final int ZERO = 0;

    public LocationsDAOImpl(HibernateTemplate  hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

	@SuppressWarnings("unchecked")
	@Override
	public Locations findByWoeid(String woeid) {
		List<Locations> locationList = (List<Locations>) hibernateTemplate.findByNamedQueryAndNamedParam(LOCATIONS, WOEID, woeid);
		
		if (locationList != null && locationList.size() != ZERO) {
			// Initialize the lazily loaded weatherList before transaction is committed and session is closed.
			Hibernate.initialize(locationList.get(ZERO).getWeatherList());
			return locationList.get(ZERO);
		}
		
		return null;
	}

	@Override
	public List<Locations> list() {
		// Create criteria for filter the data
		List<Locations> locationList = hibernateTemplate.loadAll(Locations.class);
		
		for ( Locations locations: locationList ) {
			Hibernate.initialize(locations.getWeatherList());
		}
		
		return locationList;	
	}
	
}
