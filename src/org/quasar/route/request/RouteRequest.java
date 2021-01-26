package org.quasar.route.request;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.quasar.route.openWeatherMap.OWMRequest;

import com.graphhopper.util.shapes.GHPoint;

/**
 * Represents a RouteRequest. This means a route recommendation request made by
 * an user from the app.
 * 
 * @author Rúben Beirão
 *
 */
public class RouteRequest {

	// private String IMEI; // user single identification
	private GHPoint origin; // user location or local departure for the route
	private GHPoint destination; // user ending local
	private TimeInterval availableTime; // user available time for the route
	private int effortLevel; // the maximum level of physical effort that the user is able to do
	private int budget; // the budget that the user have to visit POIs
	private LinkedList<Integer> selectedPoints; // user selected POIs to visit (the APP send just the IDs)
	private List<Integer> selectedCategories; // user preferred categories of POIs
	private boolean checkWeather; // represents the user's will in which the weather conditions are checked or not
	ArrayList<Double> usefulWeatherData; // the weather data (probability of precipitation) for the next 10 hours
	private Calendar calendar; // register the moment of the request
	private Timestamp departureDate;
	private int visitationTime;

	/**
	 * Creates a RouteRequest with the specified origin, destination,
	 * availableTime, effortLevel, budget, selectedPoints, selectedCategories and
	 * checkWeather
	 * 
	 * @param origin A GHPoint representing the coordinates (latitude,longitude) of the user location or local departure for the route
	 * @param destination A GHPoint representing the coordinates (latitude,longitude) of the user ending local
	 * @param availableTime A TimeInterval representing the user available time for the route
	 * @param effortLevel An int representing the maximum level of physical effort that the user is able to do
	 * @param budget An int representing the budget that the user have to spend on visiting POIs
	 * @param selectedPoints A LinkedList of integers representing the user selected POIs to visit (the APP send just the IDs)
	 * @param selectedCategories A List of integers representing the user preferred categories of POIs
	 * @param checkWeather A boolean representing the user's will in which the weather conditions are checked or not
	 */
	public RouteRequest(GHPoint origin, GHPoint destination, Timestamp departureDate, int visitationTime, int effortLevel,
			int budget, LinkedList<Integer> selectedPoints, List<Integer> selectedCategories, boolean checkWeather) {

		this.origin = origin;
		this.destination = destination;
		this.departureDate = departureDate;
		this.visitationTime = visitationTime;
		this.effortLevel = effortLevel;
		this.budget = budget;
		this.selectedPoints = selectedPoints;
		this.selectedCategories = selectedCategories;
		this.checkWeather = checkWeather;
		this.calendar = Calendar.getInstance();
		this.calendar.setTime(departureDate);
//		this.calendar.setTime(availableTime.getStartTime());

		// this.calendar = GregorianCalendar.getInstance(Locale.UK);
		// this.calendar.add(Calendar.HOUR_OF_DAY, (int) availableTime.getStartTime());

		if (checkWeather == true) {
			// at the same time there is a route request there is a weather forecast request
			// from this moment the weather data (temperature and precipitation) until
			//  the stored on an HashMap
			OWMRequest req = new OWMRequest(String.valueOf(origin.lat), String.valueOf(origin.lon));
			double userAvailableTime = userAvailableTimeInHours(departureDate, visitationTime);
			usefulWeatherData = req.getUsefulPrecipitation(calendar, userAvailableTime);
		}

	}

	public Timestamp getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Timestamp departureDate) {
		this.departureDate = departureDate;
	}

	public int getVisitationTime() {
		return visitationTime;
	}

	public void setVisitationTime(int visitationTime) {
		this.visitationTime = visitationTime;
	}

	private long userAvailableTimeInHours(Timestamp departureDate, int visitationTime) {
		long startTime = departureDate.getTime();
		long endTime = departureDate.getTime()+TimeUnit.MINUTES.toMillis(visitationTime);

		long diff = endTime - startTime;

		long diffHours = diff / (60 * 60 * 1000);

		return diffHours;
	}
	
	public long timeDifferenceInMinutes(Timestamp departureDate, int visitationTime) {
		long startTime = departureDate.getTime();
		long endTime = departureDate.getTime()+TimeUnit.MINUTES.toMillis(visitationTime);

		long diff = endTime - startTime;

		long diffMinutes = diff / (60 * 1000);

		return diffMinutes;

	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Double> getUsefulWeatherData() {
		return usefulWeatherData;
	}

	/**
	 * Gets a list of the selected categories present in the RouteRequest
	 * 
	 * @return A List of integers representing the user preferred categories of POIs
	 */
	public List<Integer> getSelectedCategories() {
		return selectedCategories;
	}

	/**
	 * Gets the IMEI present in the RouteRequest
	 * 
	 * @return A String correspondent to the IMEI of the mobile of the user who sent
	 *         the request
	 */
	// public String getIMEI() {
	// return IMEI;
	// }

	/**
	 * Sets the IMEI present in the RouteRequest
	 * 
	 * @param iMEI
	 *            A String containing the IMEI of the mobile of the user who sent
	 *            the request
	 */
	// public void setIMEI(String iMEI) {
	// IMEI = iMEI;
	// }

	/**
	 * Gets the origin local of the RouteRequest
	 * 
	 * @return A GHPoint representing the coordinates (latitude,longitude) of the
	 *         user location or local departure for the route
	 */
	public GHPoint getOrigin() {
		return origin;
	}

	/**
	 * Sets the origin local of the RouteRequest
	 * 
	 * @param origin
	 *            A GHPoint containing the coordinates (latitude,longitude) of the
	 *            user location or local departure for the route
	 */
	public void setOrigin(GHPoint origin) {
		this.origin = origin;
	}

	/**
	 * Gets the destination local of the RouteRequest
	 * 
	 * @return A GHPoint representing the coordinates (latitude,longitude) of the
	 *         user ending local
	 */
	public GHPoint getDestination() {
		return destination;
	}

	/**
	 * Sets the destination local of the RouteRequest
	 * 
	 * @param destination
	 *            A GHPoint containing the coordinates (latitude,longitude) of the
	 *            user ending local
	 */
	public void setDestination(GHPoint destination) {
		this.destination = destination;
	}

	/**
	 * Gets the time interval within which the route must occur
	 * 
	 * @return A TimeInterval representing the user available time for the route
	 */
//	public TimeInterval getAvailableTime() {
//		return availableTime;
//	}

	/**
	 * Sets the time interval within which the route must occur
	 * 
	 * @param availableTime
	 *            A TimeInterval containing the user available time for the route
	 */
//	public void setAvailableTime(TimeInterval availableTime) {
//		this.availableTime = availableTime;
//	}

	/**
	 * Gets the effort level of the RouteRequest
	 * 
	 * @return An int representing the maximum level of physical effort that the
	 *         user is able to do
	 */
	public int getEffortLevel() {
		return effortLevel;
	}

	/**
	 * Sets the effort level of the RouteRequest
	 * 
	 * @param effortLevel
	 *            An int containing the maximum level of physical effort that the
	 *            user is able to do
	 */
	public void setEffortLevel(int effortLevel) {
		this.effortLevel = effortLevel;
	}

	/**
	 * Gets the budget of the RouteRequest
	 * 
	 * @return An int representing the budget that the user have to spend on
	 *         visiting POIs
	 */
	public int getBudget() {
		return budget;
	}

	/**
	 * Sets the budget of the RouteRequest
	 * 
	 * @param budget
	 *            An int containing the budget that the user have to spend on
	 *            visiting POIs
	 */
	public void setBudget(int budget) {
		this.budget = budget;
	}

	/**
	 * Gets the list of selected POIs present in the RouteRequest
	 * 
	 * @return A LinkedList of integers representing the user selected POIs to visit
	 *         (the APP send just the IDs)
	 */
	public LinkedList<Integer> getSelectedPoints() {
		return selectedPoints;
	}

	/**
	 * Sets the list of selected POIs present in the RouteRequest
	 * 
	 * @param selectedPoints
	 *            A LinkedList of integers representing the user selected POIs to
	 *            visit (the APP send just the IDs)
	 */
	public void setSelectedPoints(LinkedList<Integer> selectedPoints) {
		this.selectedPoints = selectedPoints;
	}

	/**
	 * Gets a calendar object with the date initialized equal to the start time of
	 * the AvailableTime
	 * 
	 * @return A Calendar object with the date initialized equal to the start time
	 *         of the AvailableTime
	 */
	public Calendar getCalendar() {
		return calendar;
	}

	/**
	 * Gets the boolean checkWeather present in the RouteRequest
	 * 
	 * @return A boolean representing the user's will in which the weather
	 *         conditions are checked or not
	 */
	public boolean isCheckWeather() {
		return checkWeather;
	}

	/**
	 * Sets the boolean checkWeather present in the RouteRequest
	 * 
	 * @param checkWeather
	 *            A boolean containing the user's will in which the weather
	 *            conditions are checked or not
	 */
	public void setCheckWeather(boolean checkWeather) {
		this.checkWeather = checkWeather;
	}

	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance(Locale.UK);
		System.out.println(calendar.getTime());
		Calendar calTemp = (Calendar) calendar.clone();
		System.out.println(calTemp.getTime());
		calTemp.add(Calendar.HOUR_OF_DAY, 11);
		System.out.println(calTemp.getTime());
		
	}
}
