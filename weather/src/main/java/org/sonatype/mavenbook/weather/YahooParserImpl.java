package org.sonatype.mavenbook.weather;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.sonatype.mavenbook.model.Weather;
import org.sonatype.mavenbook.model.Locations;
import org.sonatype.mavenbook.model.Wind;
import org.springframework.beans.factory.annotation.Autowired;
import org.sonatype.mavenbook.model.Forecast;
import org.sonatype.mavenbook.model.Conditions;
import org.sonatype.mavenbook.model.Atmosphere;
import org.sonatype.mavenbook.model.Astronomy;

public class YahooParserImpl implements YahooParser {
	
	private static final Logger logger = LoggerFactory.getLogger(YahooParserImpl.class);
	
	private final String YAHOO = "yweather";
	private final String RSSURL = "http://xml.weather.yahoo.com/ns/rss/1.0";
	private final int MAXIMUM = 3;
	private final int ZERO = 0;
	@Autowired
	private WeatherService weatherService;

	private SAXReader createXmlReader() {
		
		Map<String, String> uris = new HashMap<String, String>();
		uris.put(YAHOO, RSSURL);
		
		DocumentFactory factory = new DocumentFactory();
		factory.setXPathNamespaceURIs(uris);
		
		SAXReader xmlReader = new SAXReader();
		xmlReader.setDocumentFactory(factory);
		
		return xmlReader;
	}
	
	public Weather parse(String woeid, InputStream inputStream) throws DocumentException {
		
		Weather weather = new Weather();
		
		logger.info("Creating XML Reader");
		
		SAXReader xmlReader = createXmlReader();
		Document document = xmlReader.read(inputStream);
		
		logger.info("Parsing XML Response");
		
		// Fail quickly for invalid location woeid, return weather as null to controller to display reminder web page
		String city =  document.valueOf("/query/results/channel/yweather:location/@city");
		String region = document.valueOf("/query/results/channel/yweather:location/@region");
		String country = document.valueOf("/query/results/channel/yweather:location/@country");
		if (city.length() == ZERO && region.length() == ZERO && country.length() == ZERO )
			return null;
		
		// Need to verify if the location already exists at database
		Locations locations = null;
		if (weatherService != null)
			locations = weatherService.findByWoeid(woeid);
		
		if (locations == null)
		{
			// Set the data field from XML element/attribute field associated with XPath
			locations = new Locations();
			locations.setCity(document.valueOf("/query/results/channel/yweather:location/@city"));
			locations.setRegion(document.valueOf("/query/results/channel/yweather:location/@region"));
			locations.setCountry(document.valueOf("/query/results/channel/yweather:location/@country"));
			locations.setWoeid(woeid);
			List<Weather> weatherList = locations.getWeatherList();
			weatherList.add(weather);
			locations.setWeatherList(weatherList);
			weather.setLocations(locations);
		}
		else {
			// Need to get the weatherList from database (locations retrieves from database doesn't contain weatherList data)
			// Those OneToMany relationship data needs to query from Many side which is Weather class with NamedQueries: Weather.byLocations.
			List<Weather> weatherList = weatherService.recentForLocations(locations);
			weatherList.add(weather);
			locations.setWeatherList(weatherList);
			weather.setLocations(locations);
		}
		
		Conditions conditions = new Conditions();
		conditions.setText(document.valueOf("/query/results/channel/item/yweather:condition/@text"));
		conditions.setTemp(document.valueOf("/query/results/channel/item/yweather:condition/@temp"));
		conditions.setCode(document.valueOf("/query/results/channel/item/yweather:condition/@code"));
		conditions.setCurrentdate(document.valueOf("/query/results/channel/item/yweather:condition/@date"));
		conditions.setWeather(weather);
		weather.setConditions(conditions);
		
		Wind wind = new Wind();
		wind.setChill(document.valueOf("/query/results/channel/yweather:wind/@chill"));
		wind.setDirection(document.valueOf("/query/results/channel/yweather:wind/@direction"));
		wind.setSpeed(document.valueOf("/query/results/channel/yweather:wind/@speed"));
		wind.setWeather(weather);
		weather.setWind(wind);
		
		Atmosphere atmosphere = new Atmosphere();
		atmosphere.setHumidity(document.valueOf("/query/results/channel/yweather:atmosphere/@humidity"));
		atmosphere.setVisibility(document.valueOf("/query/results/channel/yweather:atmosphere/@visibility"));
		atmosphere.setPressure(document.valueOf("/query/results/channel/yweather:atmosphere/@pressure"));
		atmosphere.setRising(document.valueOf("/query/results/channel/yweather:atmosphere/@rising"));
		atmosphere.setWeather(weather);
		weather.setAtmosphere(atmosphere);
		
		Astronomy astronomy = new Astronomy();
		astronomy.setSunrise(document.valueOf("/query/results/channel/yweather:astronomy/@sunrise"));
		astronomy.setSunset(document.valueOf("/query/results/channel/yweather:astronomy/@sunset"));
		astronomy.setWeather(weather);
		weather.setAstronomy(astronomy);
		
		int index = 0;
		List<Forecast> forecastList = weather.getForecastList();
		
		while (index < MAXIMUM) {
			index++;
			Forecast forecast = new Forecast();
			forecast.setWeather(weather);
			forecast.setCurrentday(document.valueOf("/query/results/channel/item/yweather:forecast[" + index + "]/@day"));
			forecast.setLow(document.valueOf("/query/results/channel/item/yweather:forecast[" + index + "]/@low"));
			forecast.setHigh(document.valueOf("/query/results/channel/item/yweather:forecast[" + index + "]/@high"));
			forecast.setConditions(document.valueOf("/query/results/channel/item/yweather:forecast[" + index + "]/@text"));
			forecast.setCode(document.valueOf("/query/results/channel/item/yweather:forecast[" + index + "]/@code"));
			forecast.setCurrentdate(document.valueOf("/query/results/channel/item/yweather:forecast[" + index + "]/@date"));
			forecastList.add(forecast);
		}
		
		weather.setForecastList(forecastList);
		
		weather.setCurrentdate(new Date());
		
		return weather;
	}
	
}
