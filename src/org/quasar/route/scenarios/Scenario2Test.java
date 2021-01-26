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
 * Dinis decides to do a gastronomic tour in the area while visiting some spots. 
 * He specifically chooses 8 places to visit and eat: Nicolau Café, Nicola Café, Casa Portuguesa do Pastel de Bacalhau, 
 * Café da Garagem, Martinho da Arcada, Aura Lisboa, Tasquinha ‘A Vaidosa’, As Bifanas do Afonso, 
 * A Brasileira e o Solar do Duque. 
 * He is asking the maximum number of POIs possible and do not choose any preferred category because there is no space for suggestions. 
 * He has a budget of 70€ to spend.
 * @author Rúben Beirão
 *
 */

public class Scenario2Test {

	@Test
	public void test() {
		String scenarioRequest2 = "{origin: {latitude: 38.7087856, longitude: -9.1309565}, destination: {latitude: 38.7115605, longitude: -9.1367243}, departureDate: 1602763200000, visitationTime: 300, budget: 70, effortLevel: 2, selectedPoints: [22, 33, 34, 36, 38], selectedCategories: [], checkWeather: false}";

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
