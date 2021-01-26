package org.quasar.route.dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.graphhopper.PathWrapper;

/**
 * DBConnection is a class that allows to access the local database and extract
 * data about the Points of Interest
 * 
 * @author Rita Peixoto
 * @author Rúben Beirão
 *
 */

public class DBConnection {

	final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	final String DB_URL = "jdbc:mysql://localhost:3306/crowding";

	// database credentials
	final String USER = "root";
	final String PASS = "";

	private Connection conn = null;
	private Statement stmt = null;

	private LinkedList<PointOfInterest> result = new LinkedList<PointOfInterest>();

	// predefined set of categories that are suitable for the rain
	private ArrayList<Integer> rainyCategories = new ArrayList<Integer>(Arrays.asList(1, 2, 4, 5, 6));

	/**
	 * This method starts the connection with the database via Java Database
	 * Connectivity (JDBC). The interface for accessing relational databases from
	 * Java is JDBC. JDBC provides an interface which allows you to perform SQL
	 * operations independently of the instance of the used database. To use JDBC,
	 * you require the database specific implementation of the JDBC driver.
	 */
	public void start() {

		try {
			/**
			 * To connect to MySQL from Java, you have to use the JDBC driver from MySQL
			 */
			Class.forName("com.mysql.jdbc.Driver");

			System.out.println("connecting to the database");
			/**
			 * Attempts to establish a connection to the given database URL. The
			 * DriverManager attempts to select an appropriate driver from the set of
			 * registered JDBC drivers.
			 */
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			System.out.println("creating a statement..");
			/**
			 * Creates a Statement object for sendingSQL statements to the database. SQL
			 * statements without parameters are normally executed using Statement objects.
			 */
			stmt = conn.createStatement();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method closes the connection to the database.
	 */
	public void close() {

		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		System.out.println("database closed");
	}

	/**
	 * This method gets the all the Points of Interest stored in the database
	 * 
	 * @return A list containing the Points of Interest objects created with the
	 *         data obtained from the database
	 */
	public LinkedList<PointOfInterest> getPOI() {
		result = new LinkedList<PointOfInterest>();

		String sql = "SELECT point_id,point_name,longitude,latitude,sustainability,opens_hours,closes_hours,category_id,price,crowding,visit_time"
				+ " from point_of_interest;";
		try {

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("point_id");
				String name = rs.getString("point_name");
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				int sustainability = rs.getInt("sustainability");
				int openHour = rs.getInt("opens_hours");
				int closeHour = rs.getInt("closes_hours");
				int category = rs.getInt("category_id");
				double price = rs.getDouble("price");
				int crowding = rs.getInt("crowding");
				int visit_time = rs.getInt("visit_time");

				// displaying values:
				// System.out.println("id " + id);

				PointOfInterest poi = new PointOfInterest(id, name, latitude, longitude, sustainability, openHour,
						closeHour, category, price, crowding, visit_time);

				result.add(poi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * This method returns the list of Points of Interest corresponding to the IDs
	 * that are in the selectePoints list
	 * 
	 * @param selectedPoints
	 *            a list of integer containing the point_ids of each POI selected by
	 *            the user
	 * @return a list of POIs representing the POIs selected by the user
	 */
	public LinkedList<PointOfInterest> getSelectedPoints(LinkedList<Integer> selectedPoints) {
		start();
		LinkedList<PointOfInterest> allPOIs = getPOI();
		LinkedList<PointOfInterest> selected = new LinkedList<>();
		for (PointOfInterest poi : allPOIs) {
			if (selectedPoints.contains(poi.getPointID()))
				selected.add(poi);
		}
		System.out.println("Selected POIs size is: " + selected.size());
		// close(); maybe its better to close on the getSelectedPOIs method because,
		// eventually
		// there is the need to add more POIs to the list with the
		// suggestPointOfInterest method
		return selected;
	}

	/**
	 * This method analyze the selected POIs categories to understand if they are
	 * suitable for rain conditions
	 * 
	 * @param selectedPOIs
	 *            the list of POIs selected by the user
	 * @return a list of POIs that was filtered from the parameter according to
	 *         their suitability to be visited when it is raining
	 */
	public LinkedList<PointOfInterest> analyzePOIs(LinkedList<PointOfInterest> selectedPOIs) {

		// first compare the selectedPOIs categories to the suitable rainy categories,
		// so that we eliminate the POIs that cannot be suggested
		System.out.println("The selected POIs size is: " + selectedPOIs.size());

		// Create a list to store the POIs from the original list that are suitable to
		// visit during rain
		LinkedList<PointOfInterest> filteredSelectedPOIs = new LinkedList<PointOfInterest>();
		// Iterate the array that contains the categories adequated for rain
		for (Iterator<Integer> it = getRainyCategories().iterator(); it.hasNext();) {
			Integer inte = it.next();
			// iterate the list of POIs given as parameter
			for (Iterator<PointOfInterest> i = selectedPOIs.iterator(); i.hasNext();) {
				PointOfInterest poi2 = i.next();
				// if the poi has a category that belongs to the list of categories adequated
				// for rain it is added to the output list
				if (poi2.getCategoryID() == inte) {
					// i.remove();
					filteredSelectedPOIs.add(poi2);
				}
			}
		}
		System.out.println("After removing the selectedPOIs my size is: " + filteredSelectedPOIs.size());
		return selectedPOIs;
	}

	/**
	 * This method analyzes the categories chosen by the user to understand if they
	 * are suitable for raining conditions, when check weather is true.
	 * The categories that are not suitable for rain are deleted from the list.
	 * @param selectedCategories A list of integers that represent the categories ids the user chose as preferred
	 * @return a list of integer representing the list of category ids suitable with rain conditions
	 */
	public List<Integer> analyzeCategories(List<Integer> selectedCategories) {
		// first compare the selected categories to the suitable rainy categories,
		// so that we eliminate the categories that cannot be suggested
		List<Integer> filteredCategories = selectedCategories;
		// removes from the first list all of its elements that are not contained in the
		// specified collection.
		filteredCategories.retainAll(getRainyCategories());

		// if it does not result in an empty array, than it should be verified if there
		// is at least 5 POIs
		// in those categories to suggest to the user
		int numOfPOIs = 0; // a counter for the number of POIs
		if (filteredCategories.size() > 0) {
			for (Iterator<Integer> it = filteredCategories.iterator(); it.hasNext();) {
				Integer inte = it.next();
				for (Iterator<PointOfInterest> i = getResult().iterator(); i.hasNext();) {
					PointOfInterest poi = i.next();
					if (poi.getCategoryID() == inte) {
						numOfPOIs++;
						System.out.println("num is " + numOfPOIs);
					}
				}
			}
			// if the number of POIs remaining to suggest in the filtered categories is less
			// than 5 then
			// we should add a category that is not the same that is there and that is
			// suitable for rain
			if (numOfPOIs < 5) {
				List<Integer> notRepeatedCategories = getRainyCategories();
				// Removes from the first list all of its elements that are contained in the
				// specified collection
				notRepeatedCategories.removeAll(filteredCategories);
				// generate a stream of pseudorandom numbers
				Random rand = new Random();
				int randomElement = notRepeatedCategories.get(rand.nextInt(notRepeatedCategories.size()));
				filteredCategories.add(randomElement);
				System.out.println("The random category number " + randomElement + "was added and the now there are "
						+ filteredCategories.size() + " categories");
			}
		}

		// if it gets out of categories then all the rain categories are selected
		if (filteredCategories.size() == 0) {
			filteredCategories = getRainyCategories();
		}

		System.out.println("Filtered categories size is: " + filteredCategories.size());
		return filteredCategories;
	}

	/**
	 * Returns the predefined set of categories that are suitable for the rain
	 * 
	 * @return a list of integer containing the category ids of the categories suitable with raining conditions
	 */
	private ArrayList<Integer> getRainyCategories() {
		ArrayList<Integer> rainyCategories = new ArrayList<>(); // create a new array to store the rain categories
		rainyCategories.addAll(Arrays.asList(1, 2, 4, 5, 6)); // store the predefined rain categories

		return rainyCategories; // return the rain categories
	}

	/**
	 * This method was developed to suggest POIs to users in the case that they
	 * didn't completely filled the selected POIs list and still have available time
	 * and money
	 * 
	 * @param selectedPOIs The list of POIs chosen by the user to be included in the route
	 * @param selectedCategories The list of preferred categories chosen by the user
	 * @param numberOfPointsToSuggest The number of points that are still possible to add to the list of POIs chosen by the user
	 * @param remainingBudget The budget that the user still have to spend on visiting POIs
	 * @param timeLeftForVisit The time that the user still have left for visit other POIs
	 * @return A list of POIs representing the final list of POIs that are going to be suggested to the user
	 */
	public LinkedList<PointOfInterest> suggestPointOfInterest(LinkedList<PointOfInterest> selectedPOIs,
			List<Integer> selectedCategories, int numberOfPointsToSuggest, double remainingBudget,
			int timeLeftForVisit) {
		start();
		// get all the POIs stored in the database
		LinkedList<PointOfInterest> allPOIs = getPOI();
		// new list to store only the POIs that are able to be suggested
		LinkedList<PointOfInterest> filteredPOIs = new LinkedList<PointOfInterest>();
		// first compare the selectedPOIs to all the POIs that we get from the database,
		// so that we eliminate
		// the ones that were already chosen and ensure that we dont suggest repeated
		// POIs
		System.out.println("The selected POIs size is: " + selectedPOIs.size());

		for (Iterator<PointOfInterest> it = allPOIs.iterator(); it.hasNext();) {
			PointOfInterest poi = it.next();
			for (Iterator<PointOfInterest> i = selectedPOIs.iterator(); i.hasNext();) {
				PointOfInterest poi2 = i.next();
				if (poi2.getPointID() == poi.getPointID()) {
					it.remove();
				}
			}
		}

		// allPOIs.removeAll(selectedPOIs);
		System.out.println("After removing the selectedPOIs my size is: " + allPOIs.size());

		// second, for each remaining POI in the list choose only the ones that are in
		// the the same category as the
		// selected categories by the user
		for (PointOfInterest poi : allPOIs) {
			for (int i = 0; i < selectedCategories.size(); i++) {
				if (poi.getCategoryID() == selectedCategories.get(i)) {
					filteredPOIs.add(poi);
					System.out.println(poi.getPointID() + " was added to filtered POIs");
				}
			}
		}
		// order the filtered list by the greatest values of sustainability
		filteredPOIs.sort(Comparator.comparing(PointOfInterest::getSustainability));
		LinkedList<PointOfInterest> finalSuggestion = new LinkedList<PointOfInterest>();
		// suggest only the POIs remaining to complete the list that was already chosen
		// by the user
		for (int p = filteredPOIs.size() - 1; p > filteredPOIs.size() - 1 - numberOfPointsToSuggest; p--) {
			if (remainingBudget - filteredPOIs.get(p).getPrice() >= 0
					&& timeLeftForVisit - filteredPOIs.get(p).getVisitTime() >= 0) {
				finalSuggestion.add(filteredPOIs.get(p));
				System.out.println("The poi " + filteredPOIs.get(p).getPointID() + "was added to the final suggestion");
				remainingBudget -= filteredPOIs.get(p).getPrice();
				timeLeftForVisit -= filteredPOIs.get(p).getVisitTime();
			}

		}

		for (PointOfInterest poi : selectedPOIs) {
			finalSuggestion.add(poi);
		}

		close();

		return finalSuggestion;
	}

	/**
	 * This method returns a list with the POIs obtained from the connection with
	 * the database
	 * 
	 * @return a list of PointOfInterest cointaining all the POIs in the database
	 */
	public LinkedList<PointOfInterest> getResult() {
		return result;
	}

	public static void main(String[] args) {
		DBConnection db = new DBConnection();
		List<Integer> selectedCategories = new ArrayList<>();
		selectedCategories.add(5);
		db.start();
		db.getPOI();
		db.analyzeCategories(selectedCategories);
		db.close();

		int from = 0;
		int to = 6;
		List<PointOfInterest> best = new LinkedList<PointOfInterest>();
		List<PointOfInterest> temp = db.getResult();
		best = temp.subList(from, to);

		System.out.println("BEST SIZE IS: " + best.size());
	}
}
