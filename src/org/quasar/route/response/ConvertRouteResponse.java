package org.quasar.route.response;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.quasar.route.dbConnection.PointOfInterest;
import org.quasar.route.graphhopper.TimedPointOfInterest;

import com.graphhopper.util.shapes.GHPoint;
import com.graphhopper.util.shapes.GHPoint3D;

/**
 * This class converts a RouteResponse into JSON
 * @author Rúben Beirão
 *
 */

public class ConvertRouteResponse {
	
	/**
	 * Creates a JSON representation of the specified route response
	 * @param routeResponse A response to the recommendation request made by a user
	 */
	public ConvertRouteResponse(RouteResponse routeResponse) {
		convertToJson(routeResponse);
	}
	
	/**
	 * Converts the specified Route Response into JSON
	 * @param routeResponse A response to the recommendation request made by a user
	 * @return A string which is a JSON representation of the RouteResponse parameter
	 */
	public String convertToJson(RouteResponse routeResponse) {
		//===================================MAIN OBJECT===================================================
		JSONObject jsonObject = new JSONObject();
		
		//===================================CODE===================================================
		jsonObject.put("code", routeResponse.getStatusCode());
		
		//===================================CODE JUSTIFICATION===============================================
		jsonObject.put("codeJustification", routeResponse.getCodeJustification());
		
		//===================================IMEI===================================================
//		jsonObject.put("IMEI", routeResponse.getIMEI());
		
		//================================ROUTE OBJECT==================================================
		JSONObject routeObject = new JSONObject();
		
		//===================================TIMED POIS===================================================
		JSONArray timedpois = new JSONArray();
		JSONObject timepoi = new JSONObject();
		for(int i = 0; i<routeResponse.getRoute().getPois().size(); i++) {
			timepoi.put("pointID", routeResponse.getRoute().getPois().get(i).getPoi().getPointID());
			timepoi.put("name", routeResponse.getRoute().getPois().get(i).getPoi().getName());
			timepoi.put("latitude", routeResponse.getRoute().getPois().get(i).getPoi().getLatitude());
			timepoi.put("longitude", routeResponse.getRoute().getPois().get(i).getPoi().getLongitude());
			timepoi.put("categoryID", routeResponse.getRoute().getPois().get(i).getPoi().getCategoryID());
			timepoi.put("visitTime", routeResponse.getRoute().getPois().get(i).getPoi().getVisitTime());
			timepoi.put("price", routeResponse.getRoute().getPois().get(i).getPoi().getPrice());
			timepoi.put("sustainability", routeResponse.getRoute().getPois().get(i).getPoi().getSustainability());
			timepoi.put("openHour", routeResponse.getRoute().getPois().get(i).getPoi().getOpenHour());
			timepoi.put("closeHour", routeResponse.getRoute().getPois().get(i).getPoi().getCloseHour());
			
			timepoi.put("visitTimestamp", routeResponse.getRoute().getPois().get(i).getVisitTimestamp().getTime());
			//Arrival time para não fazer confusão com visitTime
			
			timedpois.put(timepoi);
		}
		
		routeObject.put("poi", timedpois);
		
		//===================================LINE===================================================
		JSONArray line = new JSONArray();
		
		for(int i = 0; i<routeResponse.getRoute().getLine().size(); i++) {
			JSONObject coordinate = new JSONObject();
			coordinate.put("latitude", routeResponse.getRoute().getLine().get(i).getLat());
			coordinate.put("longitude", routeResponse.getRoute().getLine().get(i).getLon());
//			coordinate.put("elevation", routeResponse.getRoute().getLine().get(i).getEle());
			line.put(coordinate);
		}
		
		routeObject.put("line", line);
		
		//===================================TIME===================================================
		double time = routeResponse.getRoute().getTime();
		routeObject.put("time", time);
		
		//===================================DISTANCE===================================================
		double distance = routeResponse.getRoute().getDistance();
		routeObject.put("distance", distance);
		
		//===================================SUSTAINABILITY===================================================
		int sustainability = routeResponse.getRoute().getSustainability();
		routeObject.put("sustainability", sustainability);
		
		//===================================PRICE===================================================
		int price = routeResponse.getRoute().getPrice();
		routeObject.put("price", price);
		
		//===================================ORIGIN===================================================
		JSONObject origin = new JSONObject();
		double originLatitude = routeResponse.getRoute().getOrigin().getLat();
		double originLongitude = routeResponse.getRoute().getOrigin().getLon();
		origin.put("latitude", originLatitude);
		origin.put("longitude", originLongitude);
		routeObject.put("origin", origin);
		
		//===================================DESTINATION===================================================
		JSONObject destination = new JSONObject();
		double destinationLatitude = routeResponse.getRoute().getDestination().getLat();
		double destinationLongitude = routeResponse.getRoute().getDestination().getLon();
		destination.put("latitude", destinationLatitude);
		destination.put("longitude", destinationLongitude);
		routeObject.put("destination", destination);
		
		//===================================START TIME===================================================
		routeObject.put("startTime", routeResponse.getRoute().getStartTime().getTime());
		
		//===================================END TIME===================================================
		routeObject.put("endTime", routeResponse.getRoute().getEndTime().getTime());
		
		//===================================CALORIES===================================================
		routeObject.put("calories", routeResponse.getRoute().getCalories());
		
		//=================================ADD ROUTE OBJECT TO MAIN OBJECT=====================================
		jsonObject.put("route", routeObject);
		
		String jsonObjectString = jsonObject.toString();
		
		System.out.println(jsonObjectString);
//		String myJson = jsonObject.toString();
//		System.out.println(myJson);
		return jsonObjectString;
	}

	public static void main(String[] args) {
		PointOfInterest poi = new PointOfInterest(1, "Castelo", 38.8736, -9.3463653, 50, 9, 18, 3, 5, 7, 70);
		PointOfInterest poi2 = new PointOfInterest(1, "Praça", 38.3567, -9.456799, 80, 9, 18, 3, 5, 7, 70);
		
		LinkedList<TimedPointOfInterest> timedPOIs = new LinkedList<>();
		TimedPointOfInterest timedpoi1 = new TimedPointOfInterest(poi, Timestamp.valueOf("2020-10-03 14:50:10.0"));
		TimedPointOfInterest timedpoi2 = new TimedPointOfInterest(poi2, Timestamp.valueOf("2020-10-03 17:08:10.0"));
		timedPOIs.add(timedpoi1);
		timedPOIs.add(timedpoi2);

		System.out.println("O tamanho de timedPOIs é: " + timedPOIs.size());

		ArrayList<GHPoint3D> line = new ArrayList<>();
		GHPoint3D point1 = new GHPoint3D(38.9887765, -9.23434546, 31.0);
		GHPoint3D point2 = new GHPoint3D(38.3455475, -9.87676599, 31.0);
		GHPoint3D point3 = new GHPoint3D(38.8756757, -9.45678900, 31.0);
		line.add(point1);
		line.add(point2);
		line.add(point3);

		int time = 8000;
		System.out.println("Time is + " + time);

		double distance = 3000; // distance in meters
		System.out.println("Distance is + " + distance);

		double sustainability = 55.5;
		System.out.println("Average sustainability is + " + sustainability);

		int price = 30;
		System.out.println("Price is + " + price);

		GHPoint origin = new GHPoint (38.714466, -9.140692);
		System.out.println("Origin is + " + origin);

		GHPoint destination = new GHPoint (38.714466, -9.140692);
		System.out.println("Destination is + " + destination);

		Timestamp startTime = Timestamp.valueOf("2020-10-03 14:30:10.0");
		System.out.println("Start time is + " + startTime);

		int calories = 989;
		System.out.println("Calories is + " + calories);

		Timestamp endTime = Timestamp.valueOf("2020-10-03 19:10:10.0");
		System.out.println("End time is + " + endTime);

		String IMEI = "865885043764230";
		
		Route route = new Route(timedPOIs, line, time, distance, (int) sustainability, price, origin,
				destination, startTime, endTime, calories);
		
		int statusCode = 200;
		
		String codeJustification = "Success";
		
		RouteResponse routeResponse = new RouteResponse(route, statusCode, codeJustification);
		
		ConvertRouteResponse converter = new ConvertRouteResponse(routeResponse);
		
		converter.convertToJson(routeResponse);
	}
}
