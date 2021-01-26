package org.quasar.route.noCrowding.scenarios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import org.quasar.route.dbConnection.PointOfInterest;
import org.quasar.route.graphhopper.GraphhopperNoCrowding;
import org.quasar.route.graphhopper.GraphhopperServer;
import org.quasar.route.request.ConvertRouteRequest;
import org.quasar.route.request.RouteRequest;
import org.quasar.route.response.Route;
import org.quasar.route.response.RouteResponse;

import com.graphhopper.PathWrapper;
import com.graphhopper.util.shapes.GHPoint;

public class scenario1NoCrowding {

//	@Test
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		String scenarioRequest1 = "{origin: {latitude: 38.7144118, longitude: -9.1408772}, destination: {latitude: 38.7115605, longitude: -9.1367243}, departureDate: 1602772200000, visitationTime: 300, budget: 50, effortLevel: 3, selectedPoints: [], selectedCategories: [2, 7], checkWeather: false}";

		ConvertRouteRequest scenario1Converter = new ConvertRouteRequest(scenarioRequest1);
		RouteRequest routeRequest1 = scenario1Converter.getRouteRequest();
		
		GraphhopperNoCrowding hoper = new GraphhopperNoCrowding(routeRequest1);

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
