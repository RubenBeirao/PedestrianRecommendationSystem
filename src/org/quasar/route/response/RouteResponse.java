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
import com.mongodb.util.JSON;

/**
 * RouteResponse is the class that creates the structure which will be the
 * answer that is sent to the mobile application
 * 
 * @author Rúben Beirão
 *
 */
public class RouteResponse {

//	private String IMEI;
	private Route route;
	private int statusCode;
	private String codeJustification;

	/**
	 * Creates a Route Response with specified IMEI, route, status code and code
	 * justification
	 * 
	 * @param IMEI
	 *            The IMEI correspondent to the mobile of the user who sent the
	 *            request
	 * @param route
	 *            All the information needed to present the route to the user on the
	 *            app side
	 * @param statusCode
	 *            The code that identifies a successful or unsuccessful request
	 * @param codeJustification
	 *            The explanation of the success or failure of the route response
	 *            status code
	 */
	public RouteResponse(Route route, int statusCode, String codeJustification) {
		// this.IMEI = IMEI;
		this.route = route;
		this.statusCode = statusCode;
		this.codeJustification = codeJustification;
	}

	/**
	 * Gets the RouteResponse IMEI
	 * 
	 * @return A string representing the IMEI of the RouteResponse
	 */
//	public String getIMEI() {
//		return IMEI;
//	}

	/**
	 * Sets the RouteResponse IMEI
	 * 
	 * @param iMEI
	 *            A string containing the IMEI of the mobile of the user who sent
	 *            the request
	 */
//	public void setIMEI(String iMEI) {
//		IMEI = iMEI;
//	}

	/**
	 * Gets the RouteResponse status code
	 * 
	 * @return An int representing the status code of the RouteResponse
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * Sets the RouteResponse status code
	 * 
	 * @param statusCode
	 *            An int containing the code that identifies a successful or
	 *            unsuccessful request
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * Gets the RouteResponse status code justification
	 * 
	 * @return A string representing the status code justification of the
	 *         RouteResponse
	 */
	public String getCodeJustification() {
		return codeJustification;
	}

	/**
	 * Sets the RouteResponse status code justification
	 * 
	 * @param codeJustification
	 *            A string containing the explanation of the success or failure of
	 *            the route response status code
	 */
	public void setCodeJustification(String codeJustification) {
		this.codeJustification = codeJustification;
	}

	/**
	 * Sets the RouteResponse route representation
	 * 
	 * @param route
	 *            A Route object containing all the information needed to present
	 *            the route to the user on the app side
	 */
	public void setRoute(Route route) {
		this.route = route;
	}

	/**
	 * Gets the RouteResponse route representation
	 * 
	 * @return A Route object representing the route of the RouteResponse
	 */
	public Route getRoute() {
		return route;
	}

	/**
	 * Converts the specified Route Response into JSON
	 * 
	 * @param routeResponse
	 *            A string which is a JSON representation of the RouteResponse
	 *            parameter
	 */
	public void convertToJson(RouteResponse routeResponse) {
		// ===================================MAIN
		// OBJECT===================================================
		JSONObject jsonObject = new JSONObject();

		// ===================================CODE===================================================
		jsonObject.put("code", routeResponse.getStatusCode());

		// ===================================CODE
		// JUSTIFICATION===============================================
		jsonObject.put("codeJustification", routeResponse.getCodeJustification());

		// ===================================IMEI===================================================
//		jsonObject.put("IMEI", routeResponse.getIMEI());

		if(routeResponse.getStatusCode()!=204) {
		// ================================ROUTE
		// OBJECT==================================================
		JSONObject routeObject = new JSONObject();

		// ===================================TIMED
		// POIS===================================================
		JSONArray timedpois = new JSONArray();
		
		for (int i = 0; i < routeResponse.getRoute().getPois().size(); i++) {
			JSONObject timeAndPoi = new JSONObject();
			timeAndPoi.put("timestamp", routeResponse.getRoute().getPois().get(i).getVisitTimestamp().getTime());
			
			JSONObject timepoi = new JSONObject();
			
			timepoi.put("pointID", routeResponse.getRoute().getPois().get(i).getPoi().getPointID());
			timepoi.put("name", routeResponse.getRoute().getPois().get(i).getPoi().getName());
			
			JSONObject coordinates = new JSONObject();
			coordinates.put("latitude", routeResponse.getRoute().getPois().get(i).getPoi().getLatitude());
			coordinates.put("longitude", routeResponse.getRoute().getPois().get(i).getPoi().getLongitude());
			timepoi.put("coordinates", coordinates);
			
			timepoi.put("categoryID", routeResponse.getRoute().getPois().get(i).getPoi().getCategoryID());
			timepoi.put("visitTime", routeResponse.getRoute().getPois().get(i).getPoi().getVisitTime());
			timepoi.put("price", routeResponse.getRoute().getPois().get(i).getPoi().getPrice());
			timepoi.put("sustainability", routeResponse.getRoute().getPois().get(i).getPoi().getSustainability());
			timepoi.put("openHour", routeResponse.getRoute().getPois().get(i).getPoi().getOpenHour());
			timepoi.put("closeHour", routeResponse.getRoute().getPois().get(i).getPoi().getCloseHour());
			
			timeAndPoi.put("poi", timepoi);

			timedpois.put(timeAndPoi);
		}

		routeObject.put("pois", timedpois);

		// ===================================LINE===================================================
		JSONArray line = new JSONArray();

		for (int i = 0; i < routeResponse.getRoute().getLine().size(); i++) {
			JSONObject coordinate = new JSONObject();
			coordinate.put("latitude", routeResponse.getRoute().getLine().get(i).getLat());
			coordinate.put("longitude", routeResponse.getRoute().getLine().get(i).getLon());
			//coordinate.put("elevation", routeResponse.getRoute().getLine().get(i).getEle());
			line.put(coordinate);
		}

		System.out.println(line.toString());
		routeObject.put("line", line);

		// ===================================TIME===================================================
		int time = route.getTime();
		routeObject.put("durationTime", time);

		// ===================================DISTANCE===================================================
		double distance = route.getDistance();
		routeObject.put("distance", distance);

		// ===================================SUSTAINABILITY===================================================
		int sustainability = route.getSustainability();
		routeObject.put("sustainability", sustainability);

		// ===================================PRICE===================================================
		int price = route.getPrice();
		routeObject.put("price", price);

		// ===================================ORIGIN===================================================
		JSONObject origin = new JSONObject();
		double originLatitude = route.getOrigin().getLat();
		double originLongitude = route.getOrigin().getLon();
		origin.put("latitude", originLatitude);
		origin.put("longitude", originLongitude);
		routeObject.put("origin", origin);

		// ===================================DESTINATION===================================================
		JSONObject destination = new JSONObject();
		double destinationLatitude = route.getDestination().getLat();
		double destinationLongitude = route.getDestination().getLon();
		destination.put("latitude", destinationLatitude);
		destination.put("longitude", destinationLongitude);
		routeObject.put("destination", destination);

		// ===================================START TIME && END TIME ===================================================
		JSONObject startEndTime = new JSONObject();
		startEndTime.put("startTime", route.getStartTime().getTime());
		startEndTime.put("endTime", route.getEndTime().getTime());
		routeObject.put("time", startEndTime);
		
		// ===================================CALORIES===================================================
		routeObject.put("calories", route.getCalories());

		// =================================ADD ROUTE OBJECT TO MAIN
		// OBJECT=====================================
		jsonObject.put("route", routeObject);
		}

		String jsonObjectString = jsonObject.toString();

		System.out.println(jsonObjectString);
		// String myJson = jsonObject.toString();
		// System.out.println(myJson);
	}

	@Override
	public String toString() {
		return "RouteResponse [route=" + route + ", statusCode=" + statusCode
				+ ", codeJustification=" + codeJustification + "]";
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

		GHPoint origin = new GHPoint(38.714466, -9.140692);
		System.out.println("Origin is + " + origin);

		GHPoint destination = new GHPoint(38.714466, -9.140692);
		System.out.println("Destination is + " + destination);

		Timestamp startTime = Timestamp.valueOf("2020-10-03 14:30:10.0");
		System.out.println("Start time is + " + startTime);

		int calories = 989;
		System.out.println("Calories is + " + calories);

		Timestamp endTime = Timestamp.valueOf("2020-10-03 19:10:10.0");
		System.out.println("End time is + " + endTime);

//		String IMEI = "865885043764230";

		Route route = new Route(timedPOIs, line, time, distance, (int) sustainability, price, origin, destination,
				startTime, endTime, calories);

		int statusCode = 200;

		String codeJustification = "Success";

		RouteResponse routeResponse = new RouteResponse(route, statusCode, codeJustification);

		routeResponse.convertToJson(routeResponse);
	}
}