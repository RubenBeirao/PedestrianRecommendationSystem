package arcosin.genetic_algo.tsp.adaptation;

import java.util.ArrayList;
import java.util.Arrays;

public class Population {

	private ArrayList<Route> routes;

	public Population(int size, boolean init) {
		// tours = new ArrayList<Tour>(size);
		routes = new ArrayList<Route>(Arrays.asList(new Route[size]));

		if (init) {
			for (int i = 0; i < size; i++) {
				routes.set(i, new Route(true));
			}
		}
	}

	public void setRoute(int index, Route route) {
		routes.set(index, route);
	}

	public Route getRoute(int index) {
		return routes.get(index);
	}

	public int size() {
		return routes.size();
	}

	public Route getFittest() {
		Route best = routes.get(0);

		for (int i = 1; i < routes.size(); i++) {
			Route temp = routes.get(i);
			if (temp.getFitness() > best.getFitness())
				best = temp;
		}

		return best;
	}

	@Override
	public String toString() {
		String str = "--population--\n";

		for (Route route : routes) {
			str = str + "   " + route.toString() + "\n";
		}

		return str;
	}
}
