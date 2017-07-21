package org.sonatype.mavenbook.weather;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.dom4j.DocumentException;
import org.sonatype.mavenbook.model.Locations;
import org.sonatype.mavenbook.model.Weather;
import org.sonatype.mavenbook.persist.LocationsDAO;
import org.sonatype.mavenbook.persist.WeatherDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WeatherServiceImpl implements WeatherService  {
	
	private YahooRetriever yahooRetriever;
	private YahooParser yahooParser;
	@Autowired
	private WeatherDAO weatherDAO;
	@Autowired
	private LocationsDAO locationsDAO;
	
	public WeatherServiceImpl(YahooRetriever yahooRetriever, YahooParser yahooParser) {
		this.yahooRetriever = yahooRetriever;
		this.yahooParser = yahooParser;
	}
	
	public Weather retrieveForecast(String woeid) throws DocumentException, IOException {
		
		// Retrieve XML data from Yahoo
		InputStream dataInput = yahooRetriever.retrieve(woeid);

		// Parse the XML data
		Weather weather = yahooParser.parse(woeid, dataInput);

		return weather;
	}

	public void save(Weather weather) {
		weatherDAO.save(weather);
	}
	
	public Locations findByWoeid(String woeid) {
		Locations locations = locationsDAO.findByWoeid(woeid);
		return locations;
		
	}
	
	public List<Weather> recentForLocations(Locations locations) {
		List<Weather> weatherList = weatherDAO.recentForLocations(locations);
		return weatherList;
	}
	
}
