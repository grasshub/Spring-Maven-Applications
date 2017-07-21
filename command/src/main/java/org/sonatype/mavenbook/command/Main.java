package org.sonatype.mavenbook.command;

import java.io.IOException;
import java.util.List;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.mavenbook.model.Locations;
import org.sonatype.mavenbook.model.Weather;
import org.sonatype.mavenbook.weather.WeatherService;
import org.sonatype.mavenbook.weather.configuration.WeatherJavaConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	
	// Yahoo weather RSS feed accepts WOE (Where On Earth) ID instead of zipcode
	// Use the WOE ID searching site: http://woeid.factormystic.net/ to find WOEID based on zipcode
	// For instance the default zipcode 60202 maps to WOEID: 12784107 for IL/USA
	private String woeid;
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final int TWO = 2;
	private static final String WOEID = "12784107";
	private static final String WEATHER = "weather";
	private static final String HISTORY = "history";
	private static final String INVALID_OPERATION = "This program only supports weather|history operation!";
	private final String REMINDER = "There is no weather information available for the location of woeid you entered!";
	private final String INFO = "There are no historical records for this specific location with woeid you entered!";
	private static WeatherService weatherService;
	
	// Add comment for main method
	public Main(String woeid) {
		this.woeid = woeid;
	}

	private void getWeather() throws DocumentException, IOException {
		Weather weather = weatherService.retrieveForecast(woeid);

		// In case the weather information at specific location doesn't exist, display the reminder page.
		if (weather == null)
			System.out.println(REMINDER);
		else {
			weatherService.save(weather);
			System.out.println(new WeatherFormatter().formatWeather(weather));
		}
	}
	
	private void getHistory() throws DocumentException, IOException {
		Locations locations = weatherService.findByWoeid(woeid);
		List<Weather> weatherList = weatherService.recentForLocations(locations);
		
		// In case history information doesn't exist for this location, display the information page
		if (locations == null || weatherList == null)
			System.out.println(INFO);
		else
			System.out.println(new WeatherFormatter().formatHistory(locations, weatherList));
	}

	public static void main(String[] args) throws DocumentException, IOException {
		
		// Pass in the woeid as first program argument
		// (if not supplied, defaults to 12784107)
		String woeid = WOEID;
		
		if(args != null && args.length != ZERO)
			woeid = args[ZERO];
		
		// Pass in the operation (weather or history) as second program argument
		// (if not supplied, defaults to weather operation)
		String operation = WEATHER;
		if (args != null && args.length == TWO)
			operation = args[ONE];
		
		ApplicationContext context = new AnnotationConfigApplicationContext(WeatherJavaConfig.class);
		weatherService = (WeatherService) context.getBean(WeatherService.class);
		
		Main main = new Main(woeid);
		
		logger.info("Start the command program with woeid as: {}, and operation as: {}", woeid, operation);
		
		if(operation.equals(WEATHER))
			main.getWeather();
		else if (operation.equals(HISTORY))
			main.getHistory();
		else
			System.out.println(INVALID_OPERATION);
		
		((ConfigurableApplicationContext)context).close();
			
	}

}
