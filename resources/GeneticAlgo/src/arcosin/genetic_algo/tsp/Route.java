package arcosin.genetic_algo.tsp;

import java.util.ArrayList;
import java.util.Collections;

public class Route
{	
	private ArrayList<POI> tour;	// Tour as an ordered list.
	private double fitness = 0;		// Rating of the tour. inverse to distance.
	private int distance = 0;		// Distance of full tour.
	
	
	
	public Route(boolean init)
	{
		tour = new ArrayList<POI>();
		
		if(init)
		{
			for(POI city : TSP.tourManager)   tour.add(city);
			Collections.shuffle(tour);
			calculateStats();
		}
		else
		{
			for(POI city : TSP.tourManager)   tour.add(city);
		}
	}
	
	
	
	public POI getCity(int index)
	{
        return tour.get(index);
    }
	
	

	public void setCity(int index, POI city)
	{
		tour.set(index, city);
		calculateStats();
	}
	
	
	
	private void calculateStats()
	{
		int dist = 0;		//Sum distance.
		POI start = null;	//Starting city in one round of travel.
		POI end = null;	//Ending city in one round of travel.
		int partialDist;	//Distance between cities in one round of travel.
		
		//Calculate the sum distance of the tour.
		for(int i = 0; i < tour.size() - 1; i++)
		{
			start = tour.get(i);
			end = tour.get(i + 1);
			partialDist = start.distanceTo(end);
			dist = dist + partialDist;
		}
		
		if(end != null)
		{
			partialDist = end.distanceTo(tour.get(0));
			dist = dist + partialDist;
		}
		
		distance = dist;
		
		//Calculate fitness as 1 / distance.
		fitness = 1.0 / (double) dist;
	}
	
	
	
	public double getFitness()
	{
		return fitness;
	}
	
	
	
	public int getDistance()
	{
		return distance;
	}
	
	
	
	public int size() 
	{
		return tour.size();
	}
    
	
	
	public boolean containsCity(POI city)
	{
		return tour.contains(city);
	}
	
	
	
	@Override
	public String toString()
	{
		String str = "";
		
		for(POI city : tour)
		{
			str = str + city.toString() + "  ";
		}
		
		return str;
	}
}
