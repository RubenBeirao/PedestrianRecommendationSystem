package org.quasar.route.response;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;

import org.quasar.route.graphhopper.TimedPointOfInterest;

import com.graphhopper.util.shapes.GHPoint;
import com.graphhopper.util.shapes.GHPoint3D;

/**
 * This class represents a Route
 * @author Rúben Beirão
 *
 */

public class Route {

	private LinkedList<TimedPointOfInterest> pois; //a list of all the pois to visit during the route
	private ArrayList<GHPoint3D> line; //a list with all the coordinates of the route
	private int time; //
	private double distance; // the total distance of the suggested route
	private int sustainability; // average sustainability level of the route
	private int price; // total price of the route
	private final GHPoint origin; // route local of departure
	private final GHPoint destination; // route ending local
	private final Timestamp startTime; // the date and hour (yyyy-mm-dd hh:mm:ss) when the route starts
	private final Timestamp endTime; // the date and hour (yyyy-mm-dd hh:mm:ss) when the route ends
	private final int calories; // total calories burned during the route

	/**
	 * Creates a route with the specified parameters
	 * @param pois The set of POIs and timestamps that a user will visit during the route
	 * @param line The coordinates (longitude, latitude, elevation) which the route will pass through
	 * @param time A double representing the route total time
	 * @param distance The route total distance
	 * @param sustainability the route average sustainability level
	 * @param price The route total price
	 * @param origin The coordinate (latitude, longitude) that represents the local of departure of the route
	 * @param destination The coordinate (latitude, longitude) that represents the local of arrival of the route
	 * @param startTime A data representation of the moment when the route trip starts
	 * @param endTime A data representation of the moment when the route trip starts
	 * @param calories An estimation of the total calories burned by a user during the route trip
	 */
	public Route(LinkedList<TimedPointOfInterest> pois, ArrayList<GHPoint3D> line, int time, double distance,
			int sustainability, int price, GHPoint origin, GHPoint destination, Timestamp startTime, Timestamp endTime, int calories) {
		
		this.pois = pois;
		this.line = line;
		this.time = time;
		this.distance = distance;
		this.sustainability = sustainability;
		this.price = price;
		this.origin = origin;
		this.destination = destination;
		this.startTime = startTime;
		this.endTime = endTime;
		this.calories = calories;
	
	}

	/**
	 * Gets a list containing the POIs associated with a timestamp which is the moment the user should be visiting it
	 * @return A linked list containing the set of POIs and timestamps that a user will visit during the route
	 */
	public LinkedList<TimedPointOfInterest> getPois() {
		return pois;
	}

	/**
	 * Gets a list containing the POIs associated with a timestamp which is the moment the user should be visiting it
	 * @param pois A linked list containing the set of POIs and timestamps that a user will visit during the route
	 */
	public void setPois(LinkedList<TimedPointOfInterest> pois) {
		this.pois = pois;
	}

	/**
	 * Gets a list of coordinates (longitude, latitude, elevation) which the route will pass through
	 * @return An array list representing the coordinates (longitude, latitude, elevation) which the route will pass through
	 */
	public ArrayList<GHPoint3D> getLine() {
		return line;
	}

	/**
	 * Sets a list of coordinates (longitude, latitude, elevation) which the route will pass through
	 * @param line An array list representing the coordinates (longitude, latitude, elevation) which the route will pass through
	 */
	public void setLine(ArrayList<GHPoint3D> line) {
		this.line = line;
	}

	/**
	 * Gets the route total time
	 * @return A double representing the route total time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Sets the route total time
	 * @param time A double representing the route total time
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * Gets the route total distance
	 * @return A double representing the route total distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Sets the route total distance
	 * @param distance A double representing the route total distance
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * Gets the route average sustainability level
	 * @return an integer representing the route average sustainability level
	 */
	public int getSustainability() {
		return sustainability;
	}

	/**
	 * Sets the route average sustainability level
	 * @param sustainability An integer representing the route average sustainability level
	 */
	public void setSustainability(int sustainability) {
		this.sustainability = sustainability;
	}

	/**
	 * Gets the route total price
	 * @return An int representing the route total price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Sets the route total price
	 * @param price An int representing the route total price
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * Gets the coordinate (latitude, longitude) that represents the local of departure of the route
	 * @return A GHPoint representing the coordinate (latitude, longitude) that is the local of departure of the route
	 */
	public GHPoint getOrigin() {
		return origin;
	}

	/**
	 * Gets the coordinate (latitude, longitude) that represents the local of arrival of the route
	 * @return A GHPoint representing the coordinate (latitude, longitude) that is the local of arrival of the route
	 */
	public GHPoint getDestination() {
		return destination;
	}

	/**
	 * Gets a data representation of the moment when the route trip starts
	 * @return A timestamp representing a data representation of the moment when the route trip starts, in a long format
	 */
	public Timestamp getStartTime() {
		return startTime;
	}

	/**
	 * Gets a data representation of the moment when the route trip ends
	 * @return A timestamp representing a data representation of the moment when the route trip ends, in a long format
	 */
	public Timestamp getEndTime() {
		return endTime;
	}

	/**
	 * Gets an estimation of the total calories burned by a user during the route trip
	 * @return An int representing an estimation of the total calories burned by a user during the route trip
	 */
	public int getCalories() {
		return calories;
	}

}
