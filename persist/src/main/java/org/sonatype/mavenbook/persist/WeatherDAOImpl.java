package org.sonatype.mavenbook.persist;

import java.util.List;

import org.hibernate.Hibernate;
import org.sonatype.mavenbook.model.Locations;
import org.sonatype.mavenbook.model.Weather;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WeatherDAOImpl implements WeatherDAO {
	
	private HibernateTemplate  hibernateTemplate;
	private final String WEATHER = "Weather.byLocations";
	private final String LOCATIONS = "locations";

    public WeatherDAOImpl(HibernateTemplate  hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

	@Override
	public void save(Weather weather) {
		hibernateTemplate.save(weather);
	}

	@Override
	public Weather get(Integer id) {
		Weather weather = hibernateTemplate.get(Weather.class, id);
		
		// Initialize the lazily loaded forecastList before transaction is committed and session is closed.
		Hibernate.initialize(weather.getForecastList());
		
		return weather;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Weather> recentForLocations(Locations locations) {	
		List<Weather> weatherList = (List<Weather>) hibernateTemplate.findByNamedQueryAndNamedParam(WEATHER, LOCATIONS, locations);
		
		// Initialize the lazily loaded forecastList before transaction is committed and session is closed.
		for (Weather weather: weatherList) {
			Hibernate.initialize(weather.getForecastList());
		}
		
		return weatherList;
	}

}
