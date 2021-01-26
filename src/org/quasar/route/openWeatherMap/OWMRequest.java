package org.quasar.route.openWeatherMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Represents a request made to the OpenWeatherMap API
 * @author Rúben Beirão
 *
 */
public class OWMRequest {

	private String API_KEY = "b94eeed1ca13138b5da9054f83ddcc8a";
	private String LATITUDE = "";
	private String LONGITUDE = "";
	private String LANGUAGE = "pt_en";
	private String UNIT = "metric";
	private String urlString = "";
	private ArrayList<WeatherData> weatherData;

	/**
	 * Creates an OpenWeatherMap API request for the specific location determined by the LATITUDE and LONGITUDE
	 * @param LATITUDE A string representing the latitude of the location that we want weather data
	 * @param LONGITUDE A string representing the longitude of the location that we want weather data
	 */
	public OWMRequest(String LATITUDE, String LONGITUDE) {
		this.LATITUDE = LATITUDE;
		this.LONGITUDE = LONGITUDE;
		urlString = "https://api.openweathermap.org/data/2.5/forecast?lat=" + LATITUDE + "&lon=" + LONGITUDE + "&appid="
				+ this.API_KEY + "&lang=" + this.LANGUAGE + "&units=" + this.UNIT;

		String json = "...";

		try {
			StringBuilder result = new StringBuilder();
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			System.out.println(result);

			json = result.toString();
		} catch (IOException e) {
			System.out.println(e.getMessage());

		}

		weatherData = getWeatherData(json);
	}

	/**
	 * Gets a set of WeatherData objects that contains the temperature, probability of precipitation and dates obtained from the OpenWeatherMap API request
	 * @return An ArrayList of WeatherData objects that contains the temperature, probability of precipitation and dates obtained from the OpenWeatherMap API request
	 */
	public ArrayList<WeatherData> getWeatherData() {
		return weatherData;
	}

	private ArrayList<WeatherData> getWeatherData(String json) {
		System.out.println("This is JSON" + json);
		JSONObject obj = new JSONObject(json);
		JSONArray list = obj.getJSONArray("list");
		Double temperature = null;
		Double precipitation = null;
		String date = null;
		ArrayList<WeatherData> weatherData = new ArrayList<WeatherData>();
		if (list != null) {
			for (int i = 0; i < list.length(); i++) {
				JSONObject listObject = list.getJSONObject(i);
				JSONObject main = listObject.getJSONObject("main");
				System.out.println(main);
				System.out.println(main.getDouble("temp"));
				temperature = main.getDouble("temp");

				precipitation = listObject.getDouble("pop");
				System.out.println(precipitation);

				date = list.getJSONObject(i).getString("dt_txt");
				System.out.println(date);

				weatherData.add(new WeatherData(temperature, precipitation, date));
			}
		}
		return weatherData;
	}

	// Convert Date to Calendar
	private static Calendar dateToCalendar(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;

	}

	/**
	 * Creates an array of probability of precipitation values that are within the time of visit of the user
	 * @param now A Calendar object with the date initialized equal to the moment when the request of weather data to the OpenWeatherMap API is done
	 * @param availableTime A double representing the user available time for the route in hours
	 * @return An ArrayList of Doubles with all the values of probability of precipitation that are within the interval time of visit of the user
	 */
	public ArrayList<Double> getUsefulPrecipitation(Calendar now, double availableTime) {
		// Test - Convert Date to Calendar
		ArrayList<Double> usefulWeather = new ArrayList<Double>();
		for (int i = 0; i < weatherData.size(); i++) {
			Calendar calendar = OWMRequest
					.dateToCalendar(weatherData.get(i).convertStringToDate(weatherData.get(i).getDate()));
			System.out.println("My calendar" + calendar.getTime());

			int hours = (int) Duration.between(now.toInstant(), calendar.toInstant()).toHours();

			if (hours <= availableTime) {
				// usefulWeather.put("Temperature", weatherData.get(i).getTemperature());
				usefulWeather.add(weatherData.get(i).getPrecipitation());
				System.out.println("I want this prevision: " + weatherData.get(i).toString());

			}
		}
		return usefulWeather;
	}
}
