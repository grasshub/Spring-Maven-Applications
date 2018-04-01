package org.sonatype.mavenbook.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NamedQueries(
	{	@NamedQuery(
			name = "Weather.byLocations",
			query = "from Weather w where w.locations = :locations")
	}
)
public class Weather {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="locations_woeid")
	private Locations locations;
	
	@OneToOne(mappedBy="weather", cascade=CascadeType.ALL)
	private Conditions conditions;
	
	@OneToOne(mappedBy="weather", cascade=CascadeType.ALL)
	private Wind wind;
	
	@OneToOne(mappedBy="weather", cascade=CascadeType.ALL)
	private Atmosphere atmosphere;
	
	@OneToOne(mappedBy="weather", cascade=CascadeType.ALL)
	private Astronomy astronomy;
	
	@OneToMany(mappedBy="weather", cascade=CascadeType.ALL)
	private List<Forecast> forecastList = new ArrayList<>();
	
	private Date currentdate;
	
	public Weather() {}
	
	public List<Forecast> getForecastList() {
		return forecastList;
	}

	public void setForecastList(List<Forecast> forecastList) {
		this.forecastList = forecastList;
	}
	
	public Date getCurrentdate() {
		return currentdate;
	}

	public void setCurrentdate(Date currentdate) {
		this.currentdate = currentdate;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Locations getLocations() {
		return locations;
	}

	public void setLocations(Locations locations) {
		this.locations = locations;
	}

	public Conditions getConditions() {
		return conditions;
	}

	public void setConditions(Conditions conditions) {
		this.conditions = conditions;
	}

	public Wind getWind() {
		return wind;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

	public Atmosphere getAtmosphere() {
		return atmosphere;
	}

	public void setAtmosphere(Atmosphere atmosphere) {
		this.atmosphere = atmosphere;
	}

	public Astronomy getAstronomy() {
		return astronomy;
	}

	public void setAstronomy(Astronomy astronomy) {
		this.astronomy = astronomy;
	}
	
}
