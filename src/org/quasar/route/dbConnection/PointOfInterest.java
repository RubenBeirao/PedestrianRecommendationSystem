package org.quasar.route.dbConnection;

import com.graphhopper.util.shapes.GHPoint;

/**
 * Represents a Point Of Interest
 * @author Rita Peixoto
 * @author Rúben Beirão
 *
 */
public class PointOfInterest {

	private int pointID;
	private String name;
	private double latitude;
	private double longitude;
	private int categoryID;
	private int visitTime;
	private int crowding;
	private double price;
	private int sustainability;
//	private LinkedList<Schedule> schedule = new LinkedList<>();
	private int openHour;
	private int closeHour;

	/**
	 * Creates a point of interest with the specified parameters
	 * @param pointID The unique identifier of the point of interest in the database
	 * @param name The name of the point of interest
	 * @param latitude The latitude geographic coordinate of the point of interest
	 * @param longitude The longitude geographic coordinate of the point of interest
	 * @param sustainability The sustainability level of the point of interest
	 * @param openHour The point of interest opens at this hour
	 * @param closeHour The point of interest closes at this hour
	 * @param category The category to which the point of interest is associated to
	 * @param price The estimated price of ticket to visit the point of interest
	 * @param crowding The crowding level of the point of interest
	 * @param visit_time The estimated time that takes to visit the point of interest in minutes
	 */
	public PointOfInterest(int pointID, String name, double latitude, double longitude, int sustainability,
			int openHour, int closeHour, int category, double price, int crowding, int visit_time) {
		this.pointID = pointID;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.sustainability = sustainability;
		this.openHour = openHour;
		this.closeHour = closeHour;
		this.categoryID = category;
		this.price = price;
		this.crowding = crowding;
		this.visitTime = visit_time;

	}

	/**
	 * Gets the geographic coordinates (longitude and latitude) of the point of interest
	 * @return A GHPoint representing the geographic coordinates (longitude and latitude) of the point of interest
	 */
	public GHPoint getGHPoint() {
		GHPoint ghPoint = new GHPoint(getLatitude(), getLongitude());
		return ghPoint;
	}

	/**
	 * Gets the unique identifier of the point of interest in the database
	 * @return An int representing the unique identifier of the point of interest in the database
	 */
	public int getPointID() {
		return pointID;
	}

	/**
	 * Sets the unique identifier of the point of interest in the database
	 * @param pointID An int representing the unique identifier of the point of interest in the database
	 */
	public void setPointID(int pointID) {
		this.pointID = pointID;
	}

	/**
	 * Gets the name of the point of interest
	 * @return A string representing the name of the point of interest
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the point of interest
	 * @param name A string representing the name of the point of interest
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the category to which the point of interest is associated to
	 * @return An int representing the category to which the point of interest is associated to
	 */
	public int getCategoryID() {
		return categoryID;
	}

	/**
	 * Sets the category to which the point of interest is associated to
	 * @param categoryID An int representing the category to which the point of interest is associated to
	 */
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

//	public LinkedList<Schedule> getSchedule() {
//		return schedule;
//	}
//
//	public void setSchedule(LinkedList<Schedule> schedule) {
//		this.schedule = schedule;
//	}

	/**
	 * Sets the estimated time that takes to visit the point of interest in minutes
	 * @param visitTime An int representing the estimated time that takes to visit the point of interest in minutes
	 */
	public void setVisitTime(int visitTime) {
		this.visitTime = visitTime;
	}

	/**
	 * Gets the latitude geographic coordinate of the point of interest
	 * @return A double representing the latitude geographic coordinate of the point of interest
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Gets the longitude geographic coordinate of the point of interest
	 * @return A double representing the longitude geographic coordinate of the point of interest
	 */
	public double getLongitude() {
		return longitude;
	}


	/**
	 * Sets the latitude geographic coordinate of the point of interest
	 * @param latitude A double representing the latitude geographic coordinate of the point of interest
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets the longitude geographic coordinate of the point of interest
	 * @param longitude A double representing the longitude geographic coordinate of the point of interest
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Sets the category to which the point of interest is associated to
	 * @param category An int representing the category to which the point of interest is associated to
	 */
	public void setCategory(int category) {
		this.categoryID = category;
	}

	/**
	 * Sets the sustainability level of the point of interest
	 * @param sustainability 
	 */
	public void setSustainability(int sustainability) {
		this.sustainability = sustainability;
	}
	
	/**
	 * Gets the opening hour of the point of interest
	 * @return An int representing the opening hour of the point of interest
	 */
	public int getOpenHour() {
		return openHour;
	}

	/**
	 * Sets the opening hour of the point of interest
	 * @param openHour An int representing the opening hour of the point of interest
	 */
	public void setOpenHour(int openHour) {
		this.openHour = openHour;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getCloseHour() {
		return closeHour;
	}

	/**
	 * Sets the closing hour of the point of interest
	 * @param closeHour An int representing the closing hour of the point of interest
	 */
	public void setCloseHour(int closeHour) {
		this.closeHour = closeHour;
	}

	/**
	 * 
	 * @return
	 */
	public int getCrowding() {
		return crowding;
	}

	/**
	 * 
	 * @param crowding
	 */
	public void setCrowding(int crowding) {
		this.crowding = crowding;
	}

	/**
	 * Gets the sustainability level of the point of interest
	 * @return An int representing the sustainability level of the point of interest
	 */
	public int getSustainability() {
		return sustainability;
	}

	/**
	 * Gets the estimated time that takes to visit the point of interest in minutes
	 * @return An int representing the estimated time that takes to visit the point of interest in minutes
	 */
	public int getVisitTime() {
		return visitTime;
	}

	/**
	 * Gets the the estimated price of ticket to visit the point of interest
	 * @return A double representing the estimated price of ticket to visit the point of interest
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Sets the the estimated price of ticket to visit the point of interest
	 * @param price A double representing the estimated price of ticket to visit the point of interest
	 */
	public void setPrice(double price) {
		this.price = price;
	}
}
