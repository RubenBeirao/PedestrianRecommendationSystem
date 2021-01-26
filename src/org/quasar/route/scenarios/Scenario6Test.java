package org.quasar.route.scenarios;

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

public class Scenario6Test {
	
	@Test
	public void test() {

	String scenarioRequest2 = "{origin: {latitude: 38.712116, longitude: -9.136001}, destination: {latitude: 38.71535134325515, longitude: -9.1247034072876}, departureDate: 1602763200000, visitationTime: 300, budget: 70, effortLevel: 2, selectedPoints: [40,41], selectedCategories: [], checkWeather: false}";

	ConvertRouteRequest scenario2Converter = new ConvertRouteRequest(scenarioRequest2);
	RouteRequest routeRequest2 = scenario2Converter.getRouteRequest();
	
	GraphhopperServer hoper = new GraphhopperServer(routeRequest2);

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
