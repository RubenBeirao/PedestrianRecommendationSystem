package org.dbConnection;

import static org.junit.Assert.*;

import org.junit.Test;
import org.quasar.route.dbConnection.PointOfInterest;

import com.graphhopper.util.shapes.GHPoint;

public class PointOfInterestTest {

	PointOfInterest poi = new PointOfInterest(1, "Castelo de São Jorge", 38.7139092, -9.1334762, 45, 9, 18,
			7, 10, 4, 70);

	@Test
	public void IDisCorrect() {
		assertEquals(1, poi.getPointID());
	}
	
	@Test
	public void changeID() {
		poi.setPointID(2);
		assertEquals(2, poi.getPointID());
	}
	
	@Test
	public void NameisCorrect() {
		assertEquals("Castelo de São Jorge", poi.getName());
	}
	
	@Test
	public void changeName() {
		poi.setName("Praça do Comércio");
		assertEquals("Praça do Comércio", poi.getName());
	}
	
	@Test
	public void getGHPoint() {
		GHPoint point = new GHPoint(38.7139092, -9.1334762);
		assertEquals(point, poi.getGHPoint());
	}
	
	@Test
	public void sustainabilityIsCorrect() {
		assertEquals(45, poi.getSustainability());
	}
	
	@Test
	public void changeSustainability() {
		poi.setSustainability(50);;
		assertEquals(50, poi.getSustainability());
	}
	
	@Test
	public void openHourIsCorrect() {
		assertEquals(9, poi.getOpenHour());
	}
	
	@Test
	public void changeOpenHour() {
		poi.setOpenHour(10);
		assertEquals(10, poi.getOpenHour());
	}
	
	@Test
	public void closeHourIsCorrect() {
		assertEquals(18, poi.getCloseHour());
	}
	
	@Test
	public void changeCloseHour() {
		poi.setCloseHour(19);
		assertEquals(19, poi.getCloseHour());
	}
	
	@Test
	public void categoryIsCorrect() {
		assertEquals(7, poi.getCategoryID());
	}
	
	@Test
	public void changeCategory() {
		poi.setCategory(8);
		assertEquals(8, poi.getCategoryID());
	}
	
	@Test
	public void priceIsCorrect() {
		assertEquals(10, poi.getPrice(),0);
	}
	
	@Test
	public void changePrice() {
		poi.setPrice(9);
		assertEquals(9, poi.getPrice(),0);
	}
	
	@Test
	public void crowdingIsCorrect() {
		assertEquals(4, poi.getCrowding());
	}
	
	@Test
	public void changeCrowding() {
		poi.setCrowding(9);
		assertEquals(9, poi.getCrowding());
	}
	
	@Test
	public void visitTimeIsCorrect() {
		assertEquals(70, poi.getVisitTime());
	}
	
	@Test
	public void changeVisitTime() {
		poi.setVisitTime(9);
		assertEquals(9, poi.getVisitTime());
	}
}
