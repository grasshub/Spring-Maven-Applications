package org.sonatype.mavenbook.weather;

import java.io.IOException;
import java.util.List;

import org.dom4j.DocumentException;
import org.sonatype.mavenbook.model.Locations;
import org.sonatype.mavenbook.model.Weather;

public interface WeatherService {
	
	public Weather retrieveForecast(String woeid) throws DocumentException, IOException;
	
	public void save(Weather weather);
	
	public Locations findByWoeid(String woeid);
	
	public List<Weather> recentForLocations(Locations locations);

}
