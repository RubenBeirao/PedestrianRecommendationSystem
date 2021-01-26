package org.quasar.route.openWeatherMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a structure to store the probability of precipitation and temperature associated to each moment in time
 * @author Rúben Beirão
 *
 */
public class WeatherData {

	private Double temperature;
	private Double precipitation;
	private String date;

	/**
	 * Creates a weather data object with a specific value of temperature, probability of precipitation and a date
	 * @param temperature A Double correspondent to the temperature in Celsius degrees
	 * @param precipitation A Double correspondent to the probability of precipitation
	 * @param date A String correspondent to the date (the moment in time) when the temperature and precipitation will be felt
	 */
	public WeatherData(Double temperature, Double precipitation, String date) {
		this.temperature = temperature;
		this.precipitation = precipitation;
		this.date = date;
	}

	/**
	 * Gets the WeatherData temperature
	 * @return A Double correspondent to the temperature in Celsius degrees
	 */
	public Double getTemperature() {
		return temperature;
	}

	/**
	 * Gets the WeatherData probability of precipitation 
	 * @return A Double correspondent to the probability of precipitation
	 */
	public Double getPrecipitation() {
		return precipitation;
	}

	/**
	 * Gets the WeatherData date
	 * @return A String correspondent to the date (the moment in time) when the temperature and precipitation will be felt
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Converts the string date into a calendar date so that it is possible to compare with the date
	 * when users are requesting for a route
	 * @param dateString A String correspondent to the date (the moment in time) when the temperature and precipitation will be felt
	 * @return A Date object correspondent to the String date that was used as a parameter
	 */
	public Date convertStringToDate(String dateString) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(dateString + "\t" + date);

		return date;
	}
	
	@Override
	public String toString() {
		return "WeatherData [temperature=" + temperature + ", precipitation=" + precipitation + ", date=" + date + "]";
	}

}
