package org.quasar.route.graphhopper;

import java.sql.Timestamp;

import org.quasar.route.dbConnection.PointOfInterest;

/**
 * Represents a structure that relates a PointOfInterest and the time that a user should visit it during the route
 * @author Rúben Beirão
 *
 */
public class TimedPointOfInterest {

	private PointOfInterest poi;
	private Timestamp visitTimestamp;

	/**
	 * Creates a TimedPointOfInterest with the specified PointOfInterest and Timestamp
	 * @param poi The TimedPointOfInterest PointOfInterest
	 * @param visitTimestamp The TimedPointOfInterest timestamp to visit the PointOfInterest
	 */
	public TimedPointOfInterest(PointOfInterest poi, Timestamp visitTimestamp) {
		this.poi= poi;
		this.visitTimestamp = visitTimestamp;
	}

	/**
	 * Gets the TimedPointOfInterest PointOfInterest
	 * @return A point of interest representing the TimedPointOfInterest PointOfInterest
	 */
	public PointOfInterest getPoi() {
		return poi;
	}

	/**
	 * Sets the TimedPointOfInterest PointOfInterest
	 * @param poi A PointOfInterest containing the TimedPointOfInterest point of interest 
	 */
	public void setPoi(PointOfInterest poi) {
		this.poi = poi;
	}

	/**
	 * Gets the TimedPointOfInterest Timestamp
	 * @return A timestamp representing the time to visit the PointOfInterest
	 */
	public Timestamp getVisitTimestamp() {
		return visitTimestamp;
	}

	/**
	 * Sets the TimedPointOfInterest Timestamp
	 * @param visitTimestamp A Timestamp containing the TimedPointOfInterest time to visit the PointOfInterest
	 */
	public void setVisitTimestamp(Timestamp visitTimestamp) {
		this.visitTimestamp = visitTimestamp;
	}
}
