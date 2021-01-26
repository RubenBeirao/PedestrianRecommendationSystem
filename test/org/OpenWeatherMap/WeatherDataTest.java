package org.OpenWeatherMap;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.quasar.route.openWeatherMap.WeatherData;

import com.google.protobuf.TextFormat.ParseException;

public class WeatherDataTest {

	@Test
	public void testToString() {
		WeatherData weatherData = new WeatherData(17.5, 0.3, "1997-10-30 20:00:00");
		String weatherDataString = "WeatherData [temperature=" + 17.5 + ", precipitation=" + 0.3 + ", date="
				+ "1997-10-30 20:00:00" + "]";
		assertEquals(weatherDataString, weatherData.toString());
	}

	@Test
	public void testGetTemperature() {
		WeatherData weatherData = new WeatherData(17.5, 0.3, "1997-10-30 20:00:00");
		assertEquals(17.5, weatherData.getTemperature(), 0);
	}

	@Test
	public void testGetPrecipitation() {
		WeatherData weatherData = new WeatherData(17.5, 0.3, "1997-10-30 20:00:00");
		assertEquals(0.3, weatherData.getPrecipitation(), 0);
	}

	@Test
	public void testGetDate() {
		WeatherData weatherData = new WeatherData(17.5, 0.3, "1997-10-30 20:00:00");
		String date = "1997-10-30 20:00:00";
		assertEquals(date, weatherData.getDate());
	}
	
	@Test
	public void testExceptionConvertStringToDate() {
		WeatherData weatherData = new WeatherData(17.5, 0.3, "10-30 20:00:00");
		
		ParseException e = new ParseException("Unparseable date: \"-10-30 20:00:00\"");
		Exception exception = assertThrows(ParseException.class, () ->  weatherData.convertStringToDate(weatherData.getDate()));
		
	}

	@Test
	public void testConvertStringToDate() {
		WeatherData weatherData = new WeatherData(17.5, 0.3, "1997-10-30 20:00:00");
		Date date = null;

		assertNull(date);

		date = weatherData.convertStringToDate(weatherData.getDate());

		assertNotNull(date);

	}

}
