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
 * It is raining a lot in the city, but Dinis wants to enjoy the holidays and
 * visit new interesting places. Dinis remembers that the mobile app has an
 * option that he can activate to check the weather conditions and recommend a
 * route accordingly to the situation. He has heard of an interesting exhibition
 * taking place at the Museu Nacional do Desporto, so he explicitly tells the
 * application that he wants to visit this POI. Once the holidays are finishing
 * and Dinis wants to take some souvenirs to his family and friends, he chooses
 * the ‘Local Store’ category. He also wants to visit some other museums. He
 * will leave from his hostel and want that the route to end at the same place
 * 
 * @author Rúben Beirão
 *
 */

public class Scenario3Test {

	@Test
	public void test() {
		String scenarioRequest3 = "{origin: {latitude: 38.7115605, longitude: -9.1367243}, destination: {latitude: 38.7115605, longitude: -9.1367243}, departureDate: 1602756000000, visitationTime: 180, budget: 50, effortLevel: 2, selectedPoints: [32], selectedCategories: [1, 6], checkWeather: true}";

		ConvertRouteRequest scenario3Converter = new ConvertRouteRequest(scenarioRequest3);
		RouteRequest routeRequest3 = scenario3Converter.getRouteRequest();

		GraphhopperServer hoper = new GraphhopperServer(routeRequest3);

		LinkedList<PointOfInterest> selectedPOIs = hoper.getSelectedPOIs(routeRequest3.getSelectedPoints());
		LinkedList<LinkedList<PointOfInterest>> scenarios = hoper.getAllScenarios(selectedPOIs);
		scenarios = hoper.calculateTimeBetweenPOIsInScenarios(routeRequest3.getCalendar(), scenarios);
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
