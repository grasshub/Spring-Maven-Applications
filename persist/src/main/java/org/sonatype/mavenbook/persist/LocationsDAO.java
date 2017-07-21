package org.sonatype.mavenbook.persist;

import java.util.List;

import org.sonatype.mavenbook.model.Locations;

public interface LocationsDAO {
	
	public Locations findByWoeid(final String woeid);
	
	public List<Locations> list();

}
