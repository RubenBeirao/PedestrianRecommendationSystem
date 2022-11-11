package arcosin.genetic_algo.tsp.adaptation;

import java.util.ArrayList;
import java.util.Collections;


public class Route {

	private ArrayList<POI> route; // route as an ordered list.
	private double fitness = 0; // Rating of the tour. inverse to distance.
	private int distance = 0; // Distance of full tour.

	public Route(boolean init)
	{
		route = new ArrayList<POI>();
		
		if(init)
		{
			for(POI poi : TSP.tourManager)   route.add(poi);
			Collections.shuffle(route);
			calculateStats();
		}
		else
		{
			for(POI poi : TSP.tourManager)   route.add(poi);
		}
	}

	public POI getPOI(int index) {
		return route.get(index);
	}

	public void setPOI(int index, POI poi) {
		route.set(index, poi);
		calculateStats();
	}

	private void calculateStats() {
		int dist = 0; // Sum distance.
		POI start = null; // Starting poi in one round of travel.
		POI end = null; // Ending poi in one round of travel.
		int partialDist; // Distance between pois in one round of travel.

		// Calculate the sum distance of the tour.
		for (int i = 0; i < route.size() - 1; i++) {
			start = route.get(i);
			end = route.get(i + 1);
			partialDist = start.distanceTo(end);
			dist = dist + partialDist;
		}

		if (end != null) {
			partialDist = end.distanceTo(route.get(0));
			dist = dist + partialDist;
		}

		distance = dist;

		// Calculate fitness as 1 / distance.
		fitness = 1.0 / (double) dist;
	}

	public double getFitness() {
		return fitness;
	}

	public int getDistance() {
		return distance;
	}

	public int size() {
		return route.size();
	}

	public boolean containsCity(POI poi) {
		return route.contains(poi);
	}

	@Override
	public String toString() {
		String str = "";

		for (POI poi : route) {
			str = str + poi.toString() + "  ";
		}

		return str;
	}
}
