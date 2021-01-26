package org.quasar.route.noCrowding.scenarios;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.quasar.route.dbConnection.PointOfInterest;
import org.quasar.route.graphhopper.GraphhopperNoCrowding;
import org.quasar.route.graphhopper.GraphhopperServer;
import org.quasar.route.request.ConvertRouteRequest;
import org.quasar.route.request.RouteRequest;
import org.quasar.route.response.Route;
import org.quasar.route.response.RouteResponse;

import com.graphhopper.PathWrapper;
import com.graphhopper.util.shapes.GHPoint;

public class scenario4NoCrowding {

	@Test
	public void test() {
		String scenarioRequest4 = "{origin: {latitude: 38.7093008, longitude: -9.141986}, destination: {latitude: 38.7115605, longitude: -9.1367243}, departureDate: 1602802800000, visitationTime: 180, budget: 0, effortLevel: 2, selectedPoints: [], selectedCategories: [3, 8], checkWeather: false}";

		ConvertRouteRequest scenario4Converter = new ConvertRouteRequest(scenarioRequest4);
		RouteRequest routeRequest4 = scenario4Converter.getRouteRequest();
		
		GraphhopperNoCrowding hoper = new GraphhopperNoCrowding(routeRequest4);

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
