package org.sonatype.mavenbook.persist;

import java.util.List;

import org.sonatype.mavenbook.model.Locations;
import org.sonatype.mavenbook.model.Weather;

public interface WeatherDAO {
	
	public void save(Weather weather);
	
	public Weather get(Integer id);
	
	public List<Weather> recentForLocations(final Locations locations);
}
