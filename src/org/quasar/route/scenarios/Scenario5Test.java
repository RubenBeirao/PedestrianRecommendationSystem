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

public class Scenario5Test {

	@Test
	public void test() {
		String scenarioRequest5 = "{origin: {latitude: 38.710793100000004, longitude: -9.140844399999999}, destination: {latitude: 38.7079863, longitude: -9.141485}, departureDate: 1602786600000, visitationTime: 90, budget: 10, effortLevel: 3, selectedPoints: [7, 25], selectedCategories: [], checkWeather: false}";

		ConvertRouteRequest scenario5Converter = new ConvertRouteRequest(scenarioRequest5);
		RouteRequest routeRequest5 = scenario5Converter.getRouteRequest();
		
		GraphhopperServer hoper = new GraphhopperServer(routeRequest5);

		LinkedList<PointOfInterest> selectedPOIs = hoper.getSelectedPOIs(routeRequest5.getSelectedPoints());
		LinkedList<LinkedList<PointOfInterest>> scenarios = hoper.getAllScenarios(selectedPOIs);
		scenarios = hoper.calculateTimeBetweenPOIsInScenarios(routeRequest5.getCalendar(), scenarios);
		LinkedList<LinkedList<GHPoint>> myGpoints = hoper.selectGHPoint(scenarios);
		LinkedList<LinkedList<PathWrapper>> dividedRequest = hoper.dividedRequest(myGpoints);
		List<PathWrapper> compareAlternativeRoutes = hoper.compareAlternativeRoutes(dividedRequest);
		
		System.out.println("COMPAREALTERNATIVEROUTES SIZE IS: " + compareAlternativeRoutes.size());
		
		List<PathWrapper> chooseBestDistance = hoper.chooseBestDistance(compareAlternativeRoutes);

		hoper.pathInfo(chooseBestDistance);
		
		Route route = hoper.createRouteForResponse();
		
		RouteResponse routeResponse = hoper.dataForRouteResponse(route);
		
		routeResponse.convertToJson(routeResponse);

	}

}
