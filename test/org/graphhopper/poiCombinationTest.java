package org.graphhopper;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;
import org.quasar.route.dbConnection.PointOfInterest;
import org.quasar.route.graphhopper.poiCombination;

public class poiCombinationTest {

	@Test
	public void testChoose() {
		LinkedList<PointOfInterest> original = new LinkedList<>();
		
		assertEquals(0, original.size());
		
		PointOfInterest poi1 = new PointOfInterest(1, "Castelo de São Jorge", 38.7139092, -9.1334762, 30, 9,
				18, 7, 10, 5, 70);
		PointOfInterest poi2 = new PointOfInterest(2, "Capela Nossa Senhora da Saúde", -9.13569194, 38.71586278, 50, 0,
				24, 2, 0, 4, 30);
		PointOfInterest poi3 = new PointOfInterest(3, "Tasquinha A Vaidosa", -9.13441912, 38.71695138, 20, 9,
				22, 5, 7.5, 6, 45);
		PointOfInterest poi4 = new PointOfInterest(4, "Palácio da Rosa", -9.1346331, 38.714776, 10, 0,
				24, 7, 0, 3, 70);
		PointOfInterest poi5 = new PointOfInterest(5, "Pérola do Rossio", -9.13862817, 38.71356062, 10, 10,
				19, 1, 0, 7, 20);
		
		original.add(poi1);
		original.add(poi2);
		original.add(poi3);
		original.add(poi4);
		original.add(poi5);
		
		assertEquals(5, original.size());
		
		LinkedList<LinkedList<PointOfInterest>> allPermutations = new LinkedList<LinkedList<PointOfInterest>>();
		
		assertEquals(0, allPermutations.size());
		
		allPermutations = poiCombination.choose(original, original.size());
		
		assertEquals(120, allPermutations.size());
	}

	@Test
	public void testSwap() {
		PointOfInterest poi1 = new PointOfInterest(1, "Castelo de São Jorge", 38.7139092, -9.1334762, 30, 9,
				18, 7, 10, 5, 70);
		PointOfInterest poi2 = new PointOfInterest(2, "Capela Nossa Senhora da Saúde", -9.13569194, 38.71586278, 50, 0,
				24, 2, 0, 4, 30);
		
		LinkedList<PointOfInterest> list = new LinkedList<>();
		list.add(poi1);
		list.add(poi2);
		
		assertEquals(poi1, list.get(0));
		assertEquals(poi2, list.get(1));
				
		poiCombination.swap(list, 0, 1);
		
		assertEquals(poi1, list.get(1));
		assertEquals(poi2, list.get(0));
	}

}
