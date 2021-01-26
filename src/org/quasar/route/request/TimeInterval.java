package org.quasar.route.request;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Represents an interval in time
 * @author Rúben Beirão
 *
 */
public class TimeInterval {

	private Timestamp startTime;
	private Timestamp endTime;

	/**
	 * Creates an interval in time with a specific start and end time
	 * @param startTime The TimeInterval start time
	 * @param endTime The TimeInterval end time
	 */
	public TimeInterval(Timestamp startTime, Timestamp endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Calculates the time difference of the interval in hours
	 * @return An int representing the number of hours of the interval
	 */
	public long timeDifferenceInHours() {
		long milliseconds1 = startTime.getTime();
		long milliseconds2 = endTime.getTime();

		long diff = milliseconds2 - milliseconds1;

		long diffHours = diff / (60 * 60 * 1000);

		return diffHours;

	}

	/**
	 * Calculates the time difference of the interval in minutes
	 * @return An int representing the number of minutes of the interval
	 */
	public long timeDifferenceInMinutes() {
		long milliseconds1 = startTime.getTime();
		long milliseconds2 = endTime.getTime();

		long diff = milliseconds2 - milliseconds1;

		long diffMinutes = diff / (60 * 1000);

		return diffMinutes;

	}

	/**
	 * Calculates the time difference between two timestamps
	 * @param currentTime The moment in time (timestamp) that is more recent
	 * @param oldTime The moment in time (timestamp) that is older
	 * @return A long representing the difference between the currentTime and the oldTime
	 */
	public long compareTwoTimeStamps(Timestamp currentTime, Timestamp oldTime) {
		long milliseconds1 = oldTime.getTime();
		long milliseconds2 = currentTime.getTime();

		long diff = milliseconds2 - milliseconds1;
//		long diffSeconds = diff / 1000;
//		long diffMinutes = diff / (60 * 1000);
		long diffHours = diff / (60 * 60 * 1000);
//		long diffDays = diff / (24 * 60 * 60 * 1000);

		return diffHours;
	}

	/**
	 * Gets the TimeInterval start time
	 * @return A Timestamp representing the TimeInterval start time
	 */
	public Timestamp getStartTime() {
		return startTime;
	}

	/**
	 * Sets the TimeInterval start time
	 * @param startTime A Timestamp containing the TimeInterval start time
	 */
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	/**
	 * Gets the TimeInterval end time
	 * @return A Timestamp representing the TimeInterval end time
	 */
	public Timestamp getEndTime() {
		return endTime;
	}

	/**
	 * Sets the TimeInterval end time
	 * @param endTime A Timestamp containing the TimeInterval start time
	 */
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public static void main(String[] args) {

		Timestamp startTime = Timestamp.valueOf("2020-10-03 14:30:10.0");
		Timestamp endTime = Timestamp.valueOf("2020-10-03 19:10:10.0");
		
		System.out.println(Timestamp.valueOf("2020-10-03 14:30:10.0"));
		System.out.println(Timestamp.valueOf("2020-10-03 19:10:10.0"));
		
		Timestamp time = new Timestamp(1601924400);
		
		System.out.println(time);
		
		TimeInterval timeInterval = new TimeInterval(startTime, endTime);

		System.out.println(timeInterval.compareTwoTimeStamps(endTime, startTime));

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		Calendar cal = Calendar.getInstance();
		cal.setTime(timestamp);
		System.out.println(cal.getTime());
		// cal.clear(Calendar.YEAR);
		// cal.clear(Calendar.DAY_OF_YEAR);

		Date date = new Date();
		date.toInstant();

		System.out.println(timestamp.after(date));
	}
}
