package org.request;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.quasar.route.request.RouteRequest;
import org.quasar.route.request.TimeInterval;

import com.graphhopper.util.shapes.GHPoint;

public class RouteRequestTest {
	
	String IMEI = "865885043764230"; // user single identification
	GHPoint origin = new GHPoint(38.714466, -9.140692); // user location or local departure for the tour
	GHPoint destination = new GHPoint(38.710516, -9.136196); // user ending local
	TimeInterval availableTime = new TimeInterval(14, 19); // user available time for the route (start and end time for the route)
	int effortLevel = 1; // the maximum level of physical effort that the user is able to do
	int budget = 100; // the budget that the user have to visit POIs
	LinkedList<Integer> selectedPoints = new LinkedList<Integer>(); // user selected POIs to visit (the APP send just the IDs)
	List<Integer> selectedCategories; // user preferred categories of POIs
	boolean checkWeather = false; // users states if they want suggestions or not
	ArrayList<Double> usefulWeatherData; // the weather data (probability of precipitation) for the next 10 hours
	Calendar calendar; // register the moment of the request
	
	RouteRequest routeRequest = new RouteRequest(IMEI, origin, destination, availableTime, effortLevel,
			budget, selectedPoints, selectedCategories, checkWeather);

	@Test
	public void testGetIMEI() {
		String IMEI = "865885043764230";
		assertEquals(IMEI, routeRequest.getIMEI());
	}

	@Test
	public void testSetIMEI() {
		routeRequest.setIMEI("409441609320896");
		assertEquals("409441609320896", routeRequest.getIMEI());
	}
	
	@Test
	public void testGetOrigin() {
		GHPoint origin = new GHPoint(38.714466, -9.140692);
		assertEquals(origin, routeRequest.getOrigin());
	}

	@Test
	public void testSetOrigin() {
		routeRequest.setOrigin(new GHPoint(38.7139092, -9.1334762));
		assertEquals(new GHPoint(38.7139092, -9.1334762), routeRequest.getOrigin());
	}
	
	@Test
	public void testGetDestination() {
		GHPoint destination = new GHPoint(38.710516, -9.136196);
		assertEquals(destination, routeRequest.getDestination());
	}

	@Test
	public void testSetDestination() {
		routeRequest.setDestination(new GHPoint(38.714041, -9.138340));
		assertEquals(new GHPoint(38.714041, -9.138340), routeRequest.getDestination());
	}
	
	@Test
	public void testGetAvailableTime() {
		TimeInterval availableTime = new TimeInterval(14, 19);
		assertEquals(availableTime, routeRequest.getAvailableTime());
	}

	@Test
	public void testSetAvailableTime() {
		routeRequest.setAvailableTime(new TimeInterval(15, 20));
		assertEquals(new TimeInterval(15, 20), routeRequest.getAvailableTime());
	}
	
	@Test
	public void testGetEffortLevel() {
		int effortLevel = 1;
		assertEquals(effortLevel, routeRequest.getEffortLevel());
	}

	@Test
	public void testSetEffortLevel() {
		routeRequest.setEffortLevel(2);
		assertEquals(2, routeRequest.getEffortLevel());
	}
	
	@Test
	public void testGetBudget() {
		int budget = 100;
		assertEquals(budget, routeRequest.getBudget());
	}

	@Test
	public void testSetBudget() {
		routeRequest.setBudget(50);
		assertEquals(50, routeRequest.getBudget());
	}
	
	@Test
	public void testGetSelectedPoints() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetSelectedPoints() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSelectedCategories() {
		LinkedList<Integer> selectedPOIs = new LinkedList<>();
		assertEquals(0, selectedPOIs.size());
		selectedPOIs.add(1); // Castelo S. Jorge
		selectedPOIs.add(30); // Museu Arqueológico do Carmo
		assertEquals(2, selectedPOIs.size());
		
		assertEquals(selectedPOIs, selectedPoints);
	}

	@Test
	public void testGetCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsCheckWeather() {
		assertFalse(checkWeather);
	}

	@Test
	public void testSetCheckWeather() {
		routeRequest.setCheckWeather(true);
		assertTrue(checkWeather);
	}
	
	@Test
	public void testGetUsefulWeatherData() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testRouteRequest() {
		fail("Not yet implemented");
	}

}
