package org.sonatype.mavenbook.weather.configuration;

import org.sonatype.mavenbook.persist.configuration.PersistJavaConfig;
import org.sonatype.mavenbook.weather.WeatherService;
import org.sonatype.mavenbook.weather.WeatherServiceImpl;
import org.sonatype.mavenbook.weather.YahooParser;
import org.sonatype.mavenbook.weather.YahooParserImpl;
import org.sonatype.mavenbook.weather.YahooRetriever;
import org.sonatype.mavenbook.weather.YahooRetrieverImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@Import(PersistJavaConfig.class)
public class WeatherJavaConfig {
	
	@Bean
	public YahooRetriever yahooRetriever() {
		return new YahooRetrieverImpl();
	}
	
	@Bean
	public YahooParser yahooParser() {
		return new YahooParserImpl();
	}

	@Bean 
	public WeatherService weatherService() {
		WeatherService weatherService = new WeatherServiceImpl(yahooRetriever(), yahooParser());
		return weatherService;
	}

}
