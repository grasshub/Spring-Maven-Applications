package org.sonatype.mavenbook.command;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.mavenbook.model.Locations;
import org.sonatype.mavenbook.model.Weather;

public class WeatherFormatter {
	
	private static final Logger logger = LoggerFactory.getLogger(WeatherFormatter.class);
	private final String WEATHER_TEMPLATE = "/weather.vm";
	private final String WEATHER_TEMPLATE_NAME = "weather";
	private final String HISTORY_TEMPLATE = "/history.vm";
	private final String HISTORY_TEMPLATE_NAME = "history";
	private final String LOCATIONS = "locations";
	private final String WEATHER_LIST = "weatherList";
	
	public String formatWeather (Weather weather) {
		
		logger.info("Formatting Weather Data");
		
		// Get the Velocity weather template from resource file
		Reader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(WEATHER_TEMPLATE)));
		
		// Prepare the context data
		VelocityContext context = new VelocityContext();
		context.put(WEATHER_TEMPLATE_NAME, weather);
		StringWriter writer = new StringWriter();
		// Merge the template and data
		Velocity.evaluate(context, writer, WEATHER_TEMPLATE_NAME, reader);
		
		return writer.toString();
	}
	
	public String formatHistory(Locations locations, List<Weather> weatherList) {

		logger.info("Formatting History Data");

		// Get the Velocity weather template from resource file
		Reader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(HISTORY_TEMPLATE)));

		// Prepare the context data
		VelocityContext context = new VelocityContext();
		context.put(LOCATIONS, locations);
		context.put(WEATHER_LIST, weatherList);
		StringWriter writer = new StringWriter();
		// Merge the template and data
		Velocity.evaluate(context, writer, HISTORY_TEMPLATE_NAME, reader);

		return writer.toString();
	}

}
