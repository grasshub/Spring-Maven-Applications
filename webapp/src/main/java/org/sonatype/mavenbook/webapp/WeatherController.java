package org.sonatype.mavenbook.webapp;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.sonatype.mavenbook.model.Weather;
import org.sonatype.mavenbook.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WeatherController {
	
	private final String WOEID = "woeid";
	private final String WEATHER = "weather";
	@Autowired
	private WeatherService weatherService;
	private final String REMINDER = "reminder";
	
	// Access through URL: http://localhost:8080/webapp/weather.x?woeid=628879
	@RequestMapping(value="/weather.x", method=RequestMethod.GET)
	ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws DocumentException, IOException {
		
		String woeid = request.getParameter(WOEID);
		// Yahoo Retrieve service will return http 400 error if woeid equals null
		if (woeid == null)
			return new ModelAndView(REMINDER);

		Weather weather = weatherService.retrieveForecast(woeid);	
		// In case the weather information at specific location doesn't exist, display the reminder page.
		if (weather == null)
			return new ModelAndView(REMINDER);
		
		weatherService.save(weather);
		
		return new ModelAndView(WEATHER, WEATHER, weather);
	}

}
