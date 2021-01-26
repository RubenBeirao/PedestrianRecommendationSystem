package org.quasar.route.scenarios;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.quasar.route.dbConnection.PointOfInterest;
import org.quasar.route.graphhopper.GraphhopperServer;
import org.quasar.route.request.ConvertRouteRequest;
import org.quasar.route.request.RouteRequest;
import org.quasar.route.response.Route;
import org.quasar.route.response.RouteResponse;

import com.graphhopper.PathWrapper;
import com.graphhopper.util.shapes.GHPoint;

/**
 * Dinis decides that he wants to spend his afternoon (14:30 – 19:30) visiting
 * the city. To do so, he has approximately 5 hours (TM) and 50 euros available
 * (B). Through the mobile application, Dinis plans a trip to depart immediately
 * from his current location (Train Station (38.714466, -9.140692)) and conclude
 * at his hostel (Hostel (38.710516, -9.136196)). Additionally, he specifies
 * which categories of POIs he is interested in: churches and monuments (2, 7).
 * Optionally, Dinis can change the effort level of his trip and the system will
 * adjust the physical effort of the route accordingly.
 * 
 * @author Rúben Beirão
 *
 */
public class Scenario1Test {

//	@Test
	public static void main(String[] args) {
		String scenarioRequest1 = "{origin: {latitude: 38.7144118, longitude: -9.1408772}, destination: {latitude: 38.7115605, longitude: -9.1367243}, departureDate: 1602772200000, visitationTime: 300, budget: 50, effortLevel: 3, selectedPoints: [], selectedCategories: [2, 7], checkWeather: false}";

		ConvertRouteRequest scenario1Converter = new ConvertRouteRequest(scenarioRequest1);
		RouteRequest routeRequest1 = scenario1Converter.getRouteRequest();
		
		GraphhopperServer hoper = new GraphhopperServer(routeRequest1);

		LinkedList<PointOfInterest> selectedPOIs = hoper.getSelectedPOIs(routeRequest1.getSelectedPoints());
		LinkedList<LinkedList<PointOfInterest>> scenarios = hoper.getAllScenarios(selectedPOIs);
		scenarios = hoper.calculateTimeBetweenPOIsInScenarios(routeRequest1.getCalendar(), scenarios);
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
