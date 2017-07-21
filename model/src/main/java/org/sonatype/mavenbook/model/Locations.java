package org.sonatype.mavenbook.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries(
	{
		@NamedQuery(
			name = "Locations.byWoeid",
			query = "from Locations l where l.woeid = :woeid")		
	}
)
// Location is SQL Reserved Words
public class Locations {
	
	@Id
	private String woeid;
	
	@OneToMany(mappedBy="locations", cascade=CascadeType.ALL)
	private List<Weather> weatherList = new ArrayList<>();

	private String city;
	private String region;
	private String country;

	public Locations() {}

	public String getWoeid() {
		return woeid;
	}

	public void setWoeid(String woeid) {
		this.woeid = woeid;
	}
	
	public List<Weather> getWeatherList() {
		return weatherList;
	}

	public void setWeatherList(List<Weather> weatherList) {
		this.weatherList = weatherList;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
