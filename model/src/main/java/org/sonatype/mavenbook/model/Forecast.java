package org.sonatype.mavenbook.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Forecast {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="weather_id")
	private Weather weather;
	
	private String currentday;
	private String low;
	private String high;
	private String conditions;
	private String currentdate;

	private String code;
	
	public Forecast() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Weather getWeather() {
		return weather;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCurrentday() {
		return currentday;
	}
	
	public void setCurrentday(String currentday) {
		this.currentday = currentday;
	}
	
	public String getLow() {
		return low;
	}
	
	public void setLow(String low) {
		this.low = low;
	}
	
	public String getHigh() {
		return high;
	}
	
	public void setHigh(String high) {
		this.high = high;
	}
	
	public String getConditions() {
		return conditions;
	}
	
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	
	public String getCurrentdate() {
		return currentdate;
	}

	public void setCurrentdate(String currentdate) {
		this.currentdate = currentdate;
	}
	
}
