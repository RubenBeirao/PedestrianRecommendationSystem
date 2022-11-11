package arcosin.genetic_algo.tsp;

public class POI
{
	private int x;
	private int y;
	private int cityID;
	
	private static int cityCount = 0;

	
	
	public POI()
	{
		this.x = (int) (Math.random() * 200);
		this.y = (int) (Math.random() * 200);
		cityID = cityCount;
		cityCount++;
	}
	
	
	
	public POI(int x, int y)
	{
		this.x = x;
		this.y = y;
		cityID = cityCount;
		cityCount++;
	}

	
	
	public int getX()
	{
		return x;
	}
	
	
	
	public int getY()
	{
		return y;
	}
	
	
	
	public int getID()
	{
		return this.cityID;
	}
	
	
	
	public int distanceTo(POI target)
	{
		int xDist = Math.abs(target.getX() - this.x);
		int yDist = Math.abs(target.getY() - this.y);
		int dist = (int) Math.sqrt(xDist * xDist + yDist * yDist);
		return dist;
	}
	
	
	
	@Override
	public String toString()
	{
		return "[" + cityID + "(" + x + "," + y + ")]";
	}
}
