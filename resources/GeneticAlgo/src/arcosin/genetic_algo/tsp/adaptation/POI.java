package arcosin.genetic_algo.tsp.adaptation;

public class POI {

	private double latitude;
	private double longitude;
	private int ID;

	private static int cityCount = 0;

	public POI(double latitude, double longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		ID = cityCount;
		cityCount++;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public int getID() {
		return this.ID;
	}

	public int distanceTo(POI target) {
		
		double xDist = Math.abs(target.getLatitude() - this.latitude);
		double yDist = Math.abs(target.getLongitude() - this.longitude);
		int dist = (int) Math.sqrt(xDist * xDist + yDist * yDist);
		return dist;
	}

	@Override
	public String toString() {
		return "[" + ID + "(" + latitude + "," + longitude + ")]";
	}
}
