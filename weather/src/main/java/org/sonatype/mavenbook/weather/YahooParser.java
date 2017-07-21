package org.sonatype.mavenbook.weather;

import java.io.IOException;
import java.io.InputStream;

import org.dom4j.DocumentException;
import org.sonatype.mavenbook.model.Weather;

public interface YahooParser {
	public Weather parse(String woeid, InputStream inputStream) throws DocumentException, IOException;

}
