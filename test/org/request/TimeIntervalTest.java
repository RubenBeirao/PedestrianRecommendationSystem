package org.request;

import static org.junit.Assert.*;

import org.junit.Test;
import org.quasar.route.request.TimeInterval;

public class TimeIntervalTest {
	
	TimeInterval timeInterval = new TimeInterval(14, 19);

	@Test
	public void testTimeInterval() {
	}

	@Test
	public void testGetInterval() {
		assertEquals(5, timeInterval.getInterval(), 0);
	}

	@Test
	public void testGetStartTime() {
		assertEquals(14, timeInterval.getStartTime(), 0);
	}

	@Test
	public void testSetStartTime() {
		timeInterval.setStartTime(15);
		assertEquals(15, timeInterval.getStartTime(), 0);
	}

	@Test
	public void testGetEndTime() {
		assertEquals(19, timeInterval.getEndTime(), 0);

	}

	@Test
	public void testSetEndTime() {
		timeInterval.setEndTime(20);
		assertEquals(20, timeInterval.getEndTime(), 0);
	}

}
