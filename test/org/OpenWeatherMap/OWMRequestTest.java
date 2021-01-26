package org.OpenWeatherMap;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;
import org.quasar.route.openWeatherMap.OWMRequest;

public class OWMRequestTest {
	
	@Test
	public void testOWMRequest() {
		OWMRequest owm = new OWMRequest("38.714466", "-9.140692");
		
	}

	@Test
	public void testGetWeatherData() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsefulPrecipitation() {
		fail("Not yet implemented");
	}

}
