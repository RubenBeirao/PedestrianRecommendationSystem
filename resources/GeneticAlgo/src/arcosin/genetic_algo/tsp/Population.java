package arcosin.genetic_algo.tsp;

import java.util.ArrayList;
import java.util.Arrays;


public class Population
{
	private ArrayList<Route> tours;
	

	
	public Population(int size, boolean init)
	{
		//tours = new ArrayList<Tour>(size);
		tours = new ArrayList<Route>(Arrays.asList(new Route[size]));
		
		if(init)
		{
			for(int i = 0; i < size; i++)
			{
				tours.set(i, new Route(true));
			}
		}
	}
	
	
	
	public void setTour(int index, Route tour)
	{
		tours.set(index, tour);
	}
	
	
	
	public Route getTour(int index)
	{
		return tours.get(index);
	}
	
	
	
	public int size()
	{
		return tours.size();
	}
	
	
	
	public Route getFittest()
	{
		Route best = tours.get(0);
		
		for(int i = 1; i < tours.size(); i++)
		{
			Route temp = tours.get(i);
			if(temp.getFitness() > best.getFitness())   best = temp;
		}
		
		return best;
	}
	
	
	
	@Override
	public String toString()
	{
		String str = "--population--\n";
		
		for(Route tour : tours)
		{
			str = str + "   " + tour.toString() + "\n";
		}
		
		return str;
	}
}
