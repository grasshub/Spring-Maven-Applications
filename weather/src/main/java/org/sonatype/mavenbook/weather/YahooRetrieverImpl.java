package org.sonatype.mavenbook.weather;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.net.URLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class YahooRetrieverImpl implements YahooRetriever {
	
	private static final Logger logger = LoggerFactory.getLogger(YahooRetrieverImpl.class);
	// The Yahoo weather service has been changed, use the following URL to retrieve weather data
	// The first parameter for service is w or woeid
	// The second parameter for service is u for unit, it could be either c(Celsius) or f(Fahrenheit)
	// The response for RSS feed could be in RSS or xml format
	// The query string: select * from weather.forecast where woeid=628879 and u='c'&format=xml
	private final String PREFIXURL = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid=";
	private final String SUFFIXURL = "%20and%20u=%27c%27&format=xml";
	
	public InputStream retrieve(String woeid) throws IOException {
		
		logger.info("Retrieving Weather Data");
		String url = PREFIXURL + woeid + SUFFIXURL;
		URLConnection connection = new URL(url).openConnection();
		return connection.getInputStream();	
	}
}
