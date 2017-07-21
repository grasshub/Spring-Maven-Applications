package org.sonatype.mavenbook.webapp;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.sonatype.mavenbook.model.Locations;
import org.sonatype.mavenbook.model.Weather;
import org.sonatype.mavenbook.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HistoryController {
	
	private final String WOEID = "woeid";
	private final String LOCATIONS = "locations";
	private final String WEATHERLIST = "weatherList";
	private final String HISTORY = "history";
	private final String INFO = "info";
	@Autowired
	private WeatherService weatherService;
	
	// Access through URL: http://localhost:8080/webapp/history.x?woeid=628879
	@RequestMapping(value="/history.x", method=RequestMethod.GET)
	ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws DocumentException, IOException {
		
		String woeid = request.getParameter(WOEID);
		Locations locations = weatherService.findByWoeid(woeid);
		List<Weather> weatherList = weatherService.recentForLocations(locations);
		
		// In case of first time to visit history page of specific location with woeid, display the information page.
		if (locations == null || weatherList == null)
			return new ModelAndView(INFO);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put(LOCATIONS, locations);
		model.put(WEATHERLIST, weatherList);
		
		return new ModelAndView(HISTORY, model);
	}
}
