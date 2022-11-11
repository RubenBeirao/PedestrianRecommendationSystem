package arcosin.genetic_algo.tsp.adaptation;

import java.util.HashSet;
import java.util.Set;

public class GeneticAlgo {

	//How often a child tour will mutate.
		public static final double MUTATION_RATE = 0.015;
		
		//Size of random tournaments.
		public static final int TOURNAMENT_SIZE = 7;
		
		//Save the best of the original population during evolution.
		public static final boolean ELITISM = true;

		
		
		
		public static Population evolve(Population pop)
		{
			Population evolvedPopulation = new Population(pop.size(), false);
			int offset = 0;
			
			if(ELITISM)
			{
				evolvedPopulation.setRoute(0, pop.getFittest());
				offset++;
			}
			
			for(int i = offset; i < evolvedPopulation.size(); i++)
			{
				Route parent1 = tournamentSelection(pop);
				Route parent2 = tournamentSelection(pop);
				Route child = crossover(parent1, parent2);
				mutate(child);
				evolvedPopulation.setRoute(i, child);
			}
			
			return evolvedPopulation;
		}
		
		
		
		public static Route crossover(Route parent1, Route parent2)
		{
			Route child = new Route(false);
			int start = (int) (Math.random() * parent1.size());
			int end = (int) (Math.random() * parent1.size());
			Set<Integer> added = new HashSet<Integer>();
			int q = 0;
			
			//Switch start and end if end is before start.
			if(start > end)
			{
				int temp = start;
				start = end;
				end = temp;
			}
			
			//Add block from parent 1.
			for(int i = start; i < end; i++)
			{
				POI temp = parent1.getPOI(i);
				added.add(temp.getID());
				child.setPOI(i, temp);
			}
			
			//Add prefix from parent 2.
			for(int i = 0; i < start && q < parent2.size(); i++, q++)
			{
				POI temp = parent2.getPOI(q);
				
				if(!added.contains((Integer) temp.getID()))
				{
					child.setPOI(i, temp);
				}
				else
				{
					i--;
				}
			}
			
			//Add suffix from parent 2.
			for(int i = end; i < child.size() && q < parent2.size(); i++, q++)
			{
				POI temp = parent2.getPOI(q);
				
				if(!added.contains((Integer) temp.getID()))
				{
					child.setPOI(i, temp);
				}
				else
				{
					i--;
				}
			}
			
			return child;
		}
		
		
		
		private static void mutate(Route tour)
		{
			for(int i = 0; i < tour.size(); i++)
			{
				if(Math.random() < MUTATION_RATE)
				{
					int switcher = (int) (tour.size() * Math.random());
					
					POI first = tour.getPOI(i);
					POI second = tour.getPOI(switcher);
					
					tour.setPOI(i, second);
					tour.setPOI(switcher, first);
				}
			}
		}
		
		
		
		private static Route tournamentSelection(Population pop)
		{
			Population tournament = new Population(TOURNAMENT_SIZE, false);
			
			for(int i = 0; i < TOURNAMENT_SIZE; i++)
			{
				int randomId = (int) (Math.random() * pop.size());
				tournament.setRoute(i, pop.getRoute(randomId));
			}
			
			return tournament.getFittest();
		}
}
