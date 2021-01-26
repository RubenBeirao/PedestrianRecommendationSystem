package org.quasar.route.scenarios;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.quasar.route.dbConnection.PointOfInterest;
import org.quasar.route.graphhopper.GraphhopperServer;
import org.quasar.route.request.ConvertRouteRequest;
import org.quasar.route.request.RouteRequest;
import org.quasar.route.request.TimeInterval;
import org.quasar.route.response.Route;
import org.quasar.route.response.RouteResponse;

import com.graphhopper.PathWrapper;
import com.graphhopper.util.shapes.GHPoint;
/**
 * In his last day in Lisbon, Dinis decides to visit the city by night, because he wants a vision of Lisbon and its lights. 
 * startTime = 22:00
 * endTime = 01:00 
 * He wants to departure right from the restaurant he just had dinner 
 * Origin (Restaurant) = (38.709612, -9.142108)
 * and does not want to spend more money.
 * Budget=0€
 *  The idea is to go for a walk passing through some viewpoints and squares,
 *  SelectedCategories = (3,8)
 *   during the next three hours, until he arrives the hostel.
 *   Destination (Hostel) = (38.710516, -9.136196)
 * @author Rúben Beirão
 *
 */
public class Scenario4Test {

	@Test
	public void test() {
		String scenarioRequest4 = "{origin: {latitude: 38.7093008, longitude: -9.141986}, destination: {latitude: 38.7115605, longitude: -9.1367243}, departureDate: 1602802800000, visitationTime: 180, budget: 0, effortLevel: 2, selectedPoints: [], selectedCategories: [3, 8], checkWeather: false}";

		ConvertRouteRequest scenario4Converter = new ConvertRouteRequest(scenarioRequest4);
		RouteRequest routeRequest4 = scenario4Converter.getRouteRequest();
		
		GraphhopperServer hoper = new GraphhopperServer(routeRequest4);

		LinkedList<PointOfInterest> selectedPOIs = hoper.getSelectedPOIs(routeRequest4.getSelectedPoints());
		LinkedList<LinkedList<PointOfInterest>> scenarios = hoper.getAllScenarios(selectedPOIs);
		scenarios = hoper.calculateTimeBetweenPOIsInScenarios(routeRequest4.getCalendar(), scenarios);
		LinkedList<LinkedList<GHPoint>> myGpoints = hoper.selectGHPoint(scenarios);
		LinkedList<LinkedList<PathWrapper>> dividedRequest = hoper.dividedRequest(myGpoints);
		List<PathWrapper> compareAlternativeRoutes = hoper.compareAlternativeRoutes(dividedRequest);
		
		System.out.println("COMPAREALTERNATIVEROUTES SIZE IS: " + compareAlternativeRoutes.size());
		
		List<PathWrapper> chooseBestDistance = hoper.chooseBestDistance(compareAlternativeRoutes);

		hoper.pathInfo(chooseBestDistance);
		
		Route route = hoper.createRouteForResponse();
		
		RouteResponse routeResponse = hoper.dataForRouteResponse(route);
		
		routeResponse.convertToJson(routeResponse);
		
		hoper.getRouteLevelOfCrowding();

	}

}
