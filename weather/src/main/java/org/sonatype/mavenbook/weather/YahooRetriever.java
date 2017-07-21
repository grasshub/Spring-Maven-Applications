package org.sonatype.mavenbook.weather;

import java.io.IOException;
import java.io.InputStream;

public interface YahooRetriever {
	public InputStream retrieve(String woeid) throws IOException;
}
