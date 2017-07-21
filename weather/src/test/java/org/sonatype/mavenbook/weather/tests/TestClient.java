package org.sonatype.mavenbook.weather.tests;

import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.mavenbook.weather.YahooParserImpl;

import java.io.InputStream;
import org.dom4j.DocumentException;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(JUnit4.class)
public class TestClient {
	
	private final Logger logger = LoggerFactory.getLogger(TestClient.class);
	private final String NYWEATHER = "/ny-weather.xml";
	private final String WOEIDNY = "2459115";
	private final int FIRST = 0;
	private final int SECOND = 1;
	private final int THIRD = 2;
	private final String CITY = "New York";
	private final String REGION = " NY";
	private final String COUNTRY = "United States";
	private final String TEMPERATURE = "18";
	private final String CONDITION = "Partly Cloudy";
	private final String CHILL = "64";
	private final String HUMIDITY = "49";
	private final String SUNRISE = "6:26 am";
	private final String FIRSTDAY = "Mon";
	private final String FIRSTLOW = "8";
	private final String FIRSTHIGH = "22";
	private final String FIRSTCONDITION = "Partly Cloudy";
	private final String SECONDDAY = "Tue";
	private final String SECONDLOW = "13";
	private final String SECONDHIGH = "24";
	private final String SECONDCONDITION = "Partly Cloudy";
	private final String THIRDDAY = "Wed";
	private final String THIRDLOW = "13";
	private final String THIRDHIGH = "21";
	private final String THIRDCONDITION = "Scattered Showers";
	
	private void testYahooParser() throws DocumentException {
		
		logger.info("Unit testing parse method at YahooParser");
		InputStream nyData = getClass().getResourceAsStream(NYWEATHER);
		org.sonatype.mavenbook.model.Weather weather = new YahooParserImpl().parse(WOEIDNY, nyData);
		
		 assertThat(weather.getLocations().getCity(), is(CITY));
		 assertThat(weather.getLocations().getRegion(), is(REGION));
		 assertThat(weather.getLocations().getCountry(), is(COUNTRY));
		 assertThat(weather.getConditions().getTemp(), is(TEMPERATURE));
		 assertThat(weather.getConditions().getText(), is(CONDITION));
		 assertThat(weather.getWind().getChill(), is(CHILL));
		 assertThat(weather.getAtmosphere().getHumidity(), is(HUMIDITY));
		 assertThat(weather.getAstronomy().getSunrise(), is(SUNRISE));
		 assertThat((weather.getForecastList()).get(FIRST).getCurrentday(), is(FIRSTDAY));
		 assertThat((weather.getForecastList()).get(FIRST).getLow(), is(FIRSTLOW));
		 assertThat((weather.getForecastList()).get(FIRST).getHigh(), is(FIRSTHIGH));
		 assertThat((weather.getForecastList()).get(FIRST).getConditions(), is(FIRSTCONDITION));
		 assertThat((weather.getForecastList()).get(SECOND).getCurrentday(), is(SECONDDAY));
		 assertThat((weather.getForecastList()).get(SECOND).getLow(), is(SECONDLOW));
		 assertThat((weather.getForecastList()).get(SECOND).getHigh(), is(SECONDHIGH));
		 assertThat((weather.getForecastList()).get(SECOND).getConditions(), is(SECONDCONDITION));
		 assertThat((weather.getForecastList()).get(THIRD).getCurrentday(), is(THIRDDAY));
		 assertThat((weather.getForecastList()).get(THIRD).getLow(), is(THIRDLOW));
		 assertThat((weather.getForecastList()).get(THIRD).getHigh(), is(THIRDHIGH));
		 assertThat((weather.getForecastList()).get(THIRD).getConditions(), is(THIRDCONDITION));
	}
	
	@Test
	public void executeTestYahooParser() throws DocumentException {
		
		TestClient testClient = new TestClient();
		testClient.testYahooParser();
	}

}
