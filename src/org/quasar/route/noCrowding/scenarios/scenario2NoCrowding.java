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

public class scenario2NoCrowding {

	@Test
	public void test() {
		String scenarioRequest2 = "{origin: {latitude: 38.7087856, longitude: -9.1309565}, destination: {latitude: 38.7115605, longitude: -9.1367243}, departureDate: 1602763200000, visitationTime: 300, budget: 70, effortLevel: 2, selectedPoints: [22, 33, 34, 36, 38], selectedCategories: [], checkWeather: false}";

		ConvertRouteRequest scenario2Converter = new ConvertRouteRequest(scenarioRequest2);
		RouteRequest routeRequest2 = scenario2Converter.getRouteRequest();
		
		GraphhopperNoCrowding hoper = new GraphhopperNoCrowding(routeRequest2);

		LinkedList<PointOfInterest> selectedPOIs = hoper.getSelectedPOIs(routeRequest2.getSelectedPoints());
		LinkedList<LinkedList<PointOfInterest>> scenarios = hoper.getAllScenarios(selectedPOIs);
		scenarios = hoper.calculateTimeBetweenPOIsInScenarios(routeRequest2.getCalendar(), scenarios);
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
