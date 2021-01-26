package org.quasar.route.graphhopper;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.quasar.route.dbConnection.DBConnection;
import org.quasar.route.dbConnection.MongoConnection;
import org.quasar.route.dbConnection.PointOfInterest;
import org.quasar.route.request.RouteRequest;
import org.quasar.route.request.TimeInterval;
import org.quasar.route.response.Route;
import org.quasar.route.response.RouteResponse;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.PathWrapper;
import com.graphhopper.reader.dem.CGIARProvider;
import com.graphhopper.reader.osm.GraphHopperOSM;
import com.graphhopper.routing.util.AllEdgesIterator;
import com.graphhopper.routing.util.EdgeFilter;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.routing.util.FootFlagEncoder;
import com.graphhopper.routing.weighting.Weighting;
import com.graphhopper.storage.Graph;
import com.graphhopper.storage.GraphHopperStorage;
import com.graphhopper.storage.index.LocationIndex;
import com.graphhopper.storage.index.QueryResult;
import com.graphhopper.util.EdgeIterator.Edge;
import com.graphhopper.util.EdgeIteratorState;
import com.graphhopper.util.Instruction;
import com.graphhopper.util.InstructionList;
import com.graphhopper.util.PointList;
import com.graphhopper.util.Translation;
import com.graphhopper.util.TranslationMap;
import com.graphhopper.util.shapes.GHPoint;
import com.graphhopper.util.shapes.GHPoint3D;

/**
 * 
 * @author Rita Peixoto
 * @author Rúben Beirão
 *
 */
public class GraphhopperNoCrowding {

	private RouteRequest request;
	private GraphHopper hopper;
	private static final String OSM_FILE = "C:/Users/Rúben Beirão/Desktop/RoutingTest/ROUTE/resources/CentralLisbon.osm.pbf";
	private static final String GRAPHHOPPER_DIR = "src/main/resources/graphhopperv2";
	private static final String VEHICLE = "foot";
	private static final String ELEVATION_PROVIDER = "C:/Users/Rúben Beirão/Desktop/RoutingTest/geo-graphs/target/classes";

	private LinkedList<PointOfInterest> databasePOIs = new LinkedList<PointOfInterest>();

	private LinkedList<PointOfInterest> routePOIs = new LinkedList<PointOfInterest>();
	private ArrayList<GHPoint3D> routeLine = new ArrayList<GHPoint3D>();
	private double routeDistance = 0;
	private int routeTime = 0;
	private Timestamp routeStartTime = new Timestamp(0);
	private Timestamp routeEndTime = new Timestamp(0);
	private List<PathWrapper> bestPathwrapper = new LinkedList<>();
	private double routeCalories = 0;
	private LinkedList<PointOfInterest> routeOrderedPois = new LinkedList<>();
	private ArrayList<Timestamp> timestampOfVisits = new ArrayList<>();

	private int responseStatusCode;
	private String responseString;

	private final double PRECIPITATION_LIMIT = 0.5;
	private final int MAX_POIS_TO_VISIT = 5;

	private int numberOfSuggestedPOIs;
	private int routeLevelOfCrowding = 0;

	/**
	 * 
	 * @param request
	 */
	public GraphhopperNoCrowding(RouteRequest request) {
		// configurations
		org.apache.log4j.BasicConfigurator.configure();

		/**
		 * Configures the underlying storage and response to be used on a well equipped
		 * server. Result also optimized for usage in the web module i.e. try reduce
		 * network IO.
		 */
		hopper = new GraphHopperOSM().forServer();

		/**
		 * This file can be any file type supported by the DataReader. E.g. for the
		 * OSMReader it is the OSM xml (.osm), a compressed xml (.osm.zip or .osm.gz) or
		 * a protobuf file (.pbf)
		 */
		hopper.setDataReaderFile(OSM_FILE);
		/**
		 * Sets the graphhopper folder.
		 */
		hopper.setGraphHopperLocation(GRAPHHOPPER_DIR);

		/**
		 * Specify which vehicles can be read by this GraphHopper instance. An encoding
		 * manager defines how data from every vehicle is written (and read) into edges
		 * of the graph.
		 */
		hopper.setEncodingManager(EncodingManager.create(VEHICLE));

		/**
		 * Enable storing and fetching elevation data. Default is false
		 */
		hopper.setElevation(true);

		/**
		 * Sets the elevation provider
		 */
		hopper.setElevationProvider(new CGIARProvider(ELEVATION_PROVIDER));

		/**
		 * Enables or disables contraction hierarchies (CH). This speed-up mode is
		 * enabled by default.
		 */
		hopper.setCHEnabled(false);

		/**
		 * Removes the on-disc routing files. Call only after calling close or before
		 * importOrLoad or load
		 */
		// hopper.clean();

		/**
		 * Imports provided data from disc and creates graph. Depending on the settings
		 * the resulting graph will be stored to disc so on a second call this method
		 * will only load the graph from disc which is usually a lot faster.
		 */
		hopper.importOrLoad();

		System.out.println("Number of Nodes is: " + hopper.getGraphHopperStorage().getNodes());
		System.out.println("Number of Edges is: " + hopper.getGraphHopperStorage().getEdges());

		AllEdgesIterator allEdges = hopper.getGraphHopperStorage().getAllEdges();
		while (allEdges.next()) {
			int adjNode = allEdges.getAdjNode();
			int baseNode = allEdges.getBaseNode();
			int edgeId = allEdges.getEdge();

		}

		this.request = request;
		copyDataFromDatabase();
	}

	/**
	 * Gets a copy of the data from the database and stores it locally
	 * 
	 * @return
	 */
	public LinkedList<PointOfInterest> copyDataFromDatabase() {
		// create an instance of the database
		DBConnection db = new DBConnection();
		// start the connection with the database
		db.start();
		// save all the POIs in the database into the list
		databasePOIs = db.getPOI();
		// close the connection with the database
		db.close();
		return databasePOIs;
	}

	/**
	 * Converts the IDs of the POIs chosen by the users in POIs objects and gets a
	 * list of those POIs During the conversion different requirements are checked.
	 * 
	 * User available time vs Route time
	 * 
	 * If weather check is true: Analyze if the raining probability is greater than
	 * or equal to 50%; Analyze if the selected POIs are suitable for the rain;
	 * Analyze if the selected categories are suitable for the rain;
	 * 
	 * Selected POIs size vs Maximum number of pois to suggest User budget vs
	 * Selected POIs cost of visitation
	 * 
	 * @param selectedPoints
	 *            by using the list of POIs IDs
	 * @return
	 */
	public LinkedList<PointOfInterest> getSelectedPOIs(LinkedList<Integer> selectedPoints) {
		DBConnection db = new DBConnection();

		LinkedList<PointOfInterest> allPOIs = getDatabasePOIs();
		LinkedList<PointOfInterest> selectedPOIs = new LinkedList<>();
		for (PointOfInterest poi : allPOIs) {
			if (selectedPoints.contains(poi.getPointID()))
				selectedPOIs.add(poi);
		}
		System.out.println("Selected POIs size is: " + selectedPOIs.size());

		// NOTA!! SE O NUMERO DE POIS FOR IGUAL AO MAX_POIS_TO_VISIT ENTÃO NEM É PRECISO
		// FAZER AS VERIFICAÇÕES ABAIXO, É SÓ RETORNAR A LISTA DE PONTOS DE INTERESSE

		if (selectedPOIs.size() < MAX_POIS_TO_VISIT) {

			// copy the list of preferred categories chosen by the user
			List<Integer> selectedCategories = request.getSelectedCategories();

			// Verify if the available time of the user is higher than the estimation of
			// visit time for the POIs s/he chose
			if (timeVisitEstimation(selectedPOIs) < request.getVisitationTime()) {
				// Save the time that s/he has left for visiting extra places
				int timeLeftForVisit = (int) (request.getVisitationTime() - timeVisitEstimation(selectedPOIs));

				// Verify if user wants to check weather
				if (request.isCheckWeather() == true) {
					// get precipitation data
					ArrayList<Double> precipitation = request.getUsefulWeatherData();

					int i = 0;
					boolean cut = false;
					boolean rain = false;

					if (precipitation.size() > 0) {
						while (i < precipitation.size() && cut == false) {
							// if the raining probability is greater than or equal to 50%
							if (precipitation.get(i) >= PRECIPITATION_LIMIT) {
								cut = true;
								rain = true;
							}
							i++;
						}
					}
					if (rain == true) {
						// analyze if the selected POIs are suitable for the rain
						selectedPOIs = db.analyzePOIs(selectedPOIs);
						// analyze if the selected categories are suitable for the rain
						db.analyzeCategories(selectedCategories);
					}
				}

				// NOTA!! SE AS CATEGORIAS VIEREM VAZIAS E NÃO TIVER CHOVIDO, NÃO VALE A PENA IR
				// PARA A RECOMENDAÇÃO PORQUE SÓ
				// DEVEM SER INCLUÍDOS OS POIS ESCOLHIDOS PELO USER
				if (selectedCategories.size() != 0 && request.getBudget() >= evaluateBudget(selectedPOIs)) {
					int numberOfPointsToSuggest = MAX_POIS_TO_VISIT - selectedPOIs.size();
					double remainingBudget = request.getBudget() - evaluateBudget(selectedPOIs);
					selectedPOIs = db.suggestPointOfInterest(selectedPOIs, selectedCategories, numberOfPointsToSuggest,
							remainingBudget, timeLeftForVisit);
					System.out.println("The selectedPOIs size in GraphhopperServer is: " + selectedPOIs.size());
				}
			}
		}
		this.routePOIs = selectedPOIs;
		return selectedPOIs;
	}

	private double timeVisitEstimation(LinkedList<PointOfInterest> selectedPOIs) {
		final int INTERVAL = 10;
		double visitEstimationTime = 0;
		for (PointOfInterest p : selectedPOIs) {
			visitEstimationTime += p.getVisitTime() + INTERVAL;
		}
		return visitEstimationTime;
	}

	/**
	 * Gets all the different sets of POIs selected by the user combined in
	 * different order to have all the possible different combinations of routes
	 * that pass through all POIs
	 * 
	 * @param selectedPOIs
	 * @return
	 */
	public LinkedList<LinkedList<PointOfInterest>> getAllScenarios(LinkedList<PointOfInterest> selectedPOIs) {
		numberOfSuggestedPOIs = selectedPOIs.size();
		LinkedList<LinkedList<PointOfInterest>> scenarios = poiCombination.choose(selectedPOIs, selectedPOIs.size());
		return scenarios;
	}

	/**
	 * Analyzes if the POIs are open at the moment the user arrives
	 * 
	 * @param calendar
	 * @param scenarios
	 * @return
	 */
	public LinkedList<LinkedList<PointOfInterest>> calculateTimeBetweenPOIsInScenarios(Calendar calendar,
			LinkedList<LinkedList<PointOfInterest>> scenarios) {
		LinkedList<LinkedList<PointOfInterest>> possibleScenarios = new LinkedList<LinkedList<PointOfInterest>>();

		System.out.println("Scenarios size is : " + scenarios.size());

		int contador = 0;

		// for each scenario in the list lets check if its possible to travel and arrive
		// at the POI when its open
		for (LinkedList<PointOfInterest> poiList : scenarios) {
			System.out.println("CONTADOR: " + contador);
			contador++;
			// array for saving the visit times of the POIs in this scenario especifically
			// in the order they are disposed
			ArrayList<Integer> tempVisitTimes = new ArrayList<Integer>();
			// array for saving the open and closing hours of the POIs in this scenario
			// specifically in the order they are disposed
			ArrayList<Integer> tempOpenHours = new ArrayList<Integer>();
			ArrayList<Integer> tempCloseHours = new ArrayList<Integer>();

			Calendar calendar2 = (Calendar) calendar.clone();
			System.out.println("Calendar 2" + calendar2.getTime());

			// for each point of interest of this scenario
			for (int z = 0; z < poiList.size(); z++) {
				// save the visit time
				tempVisitTimes.add((int) poiList.get(z).getVisitTime());
				// save the open hour
				tempOpenHours.add(poiList.get(z).getOpenHour());
				// save the close hour
				tempCloseHours.add(poiList.get(z).getCloseHour());
			}
			for (int c = 0; c < poiList.size(); c++) {
				System.out.println(tempVisitTimes.get(c));
				System.out.println(tempOpenHours.get(c));
				System.out.println(tempCloseHours.get(c));
			}
			int count = 0;
			int i = 0;
			int j = i + 1;
			while (j < poiList.size()) {
				System.out.println("J value is: " + j);
				GHRequest req = new GHRequest(poiList.get(i).getGHPoint(), poiList.get(j).getGHPoint())
						.setWeighting("short_fastest").setVehicle("foot").setLocale(Locale.ENGLISH)
						.setAlgorithm("alternative_route");
				GHResponse rsp = hopper.route(req);
				if (rsp.hasErrors()) {
					// handle them!
					for (Throwable tw : rsp.getErrors())
						System.out.println(tw.toString());
					return null;
				}
				System.out.println("A hora de abertura é " + tempOpenHours.get(i) + " e a hora de fecho é "
						+ tempCloseHours.get(i));
				System.out.println("São " + calendar2.get(Calendar.HOUR_OF_DAY) + " horas");
				if (calendar2.get(Calendar.HOUR_OF_DAY) >= tempOpenHours.get(i)
						&& calendar2.get(Calendar.HOUR_OF_DAY) < tempCloseHours.get(i)) {
					System.out.println("entrei!");
					calendar2.add(Calendar.MINUTE, tempVisitTimes.get(i));
					calendar2.add(Calendar.MINUTE, (int) TimeUnit.MILLISECONDS.toMinutes(rsp.getBest().getTime()));
					count++;
					System.out.println("count is now : " + count);
				}
				if (count == poiList.size() - 1) {
					possibleScenarios.add(poiList);
					count = 0;
				}
				i++;
				j++;
			}

		}
		if (possibleScenarios.size() == 0) {
			responseStatusCode = 204;
			responseString = "There is any possible recommendation regarding the chosen POIs, their schedule and the user available time";
		} else if (possibleScenarios.size() > 0) {
			responseStatusCode = 200;
			responseString = "The request got a successful recommendation";
		}

		return possibleScenarios;
	}

	/**
	 * Transform the scenarios of POIs into GHPoints because the Graphhopper
	 * requests only accept coordinates (GHPoints) Takes the opportunity to add the
	 * origin of the route and the destination to the beggining and end of each
	 * scenario so that they are always the place to start the route and to finish
	 * it
	 * 
	 * @param poiScenariosList
	 *            A list of lists representing all the different possibilities of
	 *            traveling from the origin to the destination and passing through
	 *            all the chosen/suggested POIs
	 * @return A list of lists representing all the different possibilities of
	 *         traveling from the origin to the destination and passing through all
	 *         the chosen/suggested POIs represented in coordinates (GHPoint)
	 */
	public LinkedList<LinkedList<GHPoint>> selectGHPoint(LinkedList<LinkedList<PointOfInterest>> poiScenariosList) {
		// create a list to store the lists of GHPoints
		LinkedList<LinkedList<GHPoint>> listOfScenarios = new LinkedList<LinkedList<GHPoint>>();
		GHPoint origin = request.getOrigin(); // user location or local departure for the tour
		GHPoint destination = request.getDestination(); // user ending local
		for (LinkedList<PointOfInterest> poiList : poiScenariosList) {
			// create a list to store the GHPoints correspondents to the POIs
			LinkedList<GHPoint> ghScenarioPoints = new LinkedList<GHPoint>();
			for (PointOfInterest p : poiList) {
				ghScenarioPoints.add(p.getGHPoint());
			}
			// add the origin point to the beggining of every list because it is always the
			// first place to "visit"
			ghScenarioPoints.addFirst(origin);
			// add the destination point to the end of every list because it is always the
			// last place to "visit"
			ghScenarioPoints.addLast(destination);
			listOfScenarios.add(ghScenarioPoints);
		}

		return listOfScenarios;
	}

	/**
	 * This method returns a list of lists of PathWrappers, which is a class that
	 * holds the data like points, distance, instructions, etc.. of a Path
	 * 
	 * @param listOfScenarios
	 * @return
	 */
	public LinkedList<LinkedList<PathWrapper>> dividedRequest(LinkedList<LinkedList<GHPoint>> listOfScenarios) {
		LinkedList<LinkedList<PathWrapper>> path = new LinkedList<LinkedList<PathWrapper>>();

		for (LinkedList<GHPoint> ghPointList : listOfScenarios) {
			int i = 0;
			int j = i + 1;
			while (j < ghPointList.size()) {
				LinkedList<PathWrapper> temp = new LinkedList<PathWrapper>();
				GHPoint begin = ghPointList.get(i);
				GHPoint end = ghPointList.get(j);
				// Set routing request from specified startPlace (fromLat, fromLon) to endPlace
				// (toLat, toLon)
				// setWeighting: By default it supports fastest and shortest. Or specify empty
				// to use default.
				// setVehicle: Specifiy car, bike or foot. Or specify empty to use default.
				GHRequest req = new GHRequest(begin, end).setWeighting("short_fastest").setVehicle("foot")
						.setLocale(Locale.US);
				// the maximum number of returned path. Valid >= 2 because if just 1 the user
				// should not use alternative route calculation due to the overhead
				req.getHints().put("alternative_route.max_paths", "3");
				// defines the minimum distance (again better: ‘weight’) that a branch from
				// the source SPT must share with the destination Shortest Path Trees.
				req.getHints().put("alternative_route.min_plateau_factor", "0.1");
				req.getHints().put("elevation", true);
				// set an algorithm to calculate the path
				req.setAlgorithm("alternative_route");

				// GHResponse is the wrapper containing path and error output of GraphHopper.
				GHResponse rsp = hopper.route(req);
				if (rsp.hasErrors()) {
					// handle them!
					for (Throwable tw : rsp.getErrors())
						System.out.println(tw.toString());
					return null;
				}
				// use the best path, see the GHResponse class for more possibilities.
				// add all the wrappers found between the two points to the temporary list
				temp.addAll(rsp.getAll());
				// add the temporary list to the final list of lists of pathwrappers
				path.add(temp);
				i++;
				j++;
			}
		}
		System.out.println("Número de paths: " + path.size());
		return path;
	}

	/**
	 * Compares all the alternative routes that exist going from the origin to the
	 * destination and order them from the shortest/quickest... to the less
	 * short/quick
	 * 
	 * @param pathAlternatives
	 * @return
	 */
	
	public List<PathWrapper> compareAlternativeRoutes(LinkedList<LinkedList<PathWrapper>> pathAlternatives) {
		List<PathWrapper> sortedPaths = new LinkedList<PathWrapper>();
		for (LinkedList<PathWrapper> diffPaths : pathAlternatives) {
			// sort the alternative routes regarding the distance covered
			diffPaths.sort(Comparator.comparing(PathWrapper::getDistance));

			// get only the first path which corresponds to the shortest distance
			sortedPaths.add(diffPaths.get(0));

		}
		System.out.println("SORTED PATHS: " + sortedPaths.toString());
		System.out.println(sortedPaths.size());
		return sortedPaths;
	}

	/**
	 * Picks the shortest route from all the scenarios that were already filtered
	 * 
	 * @param sortedPaths
	 * @return
	 */
	public List<PathWrapper> chooseBestDistance(List<PathWrapper> sortedPaths) {
		
		MongoConnection mongo = new MongoConnection();

		List<PathWrapper> temp = new LinkedList<PathWrapper>();
		List<PathWrapper> best = new LinkedList<PathWrapper>();
		int from = 0;
		int to = numberOfSuggestedPOIs + 1;

		double bestDistance = 0;
		
		while (to < sortedPaths.size()) {
			double distance = 0;
			
			int crowding = 0;
						
			temp = sortedPaths.subList(from, to);

			int effortLevel = analyzeEffort(temp);
			
			for (PathWrapper path : temp) {
				distance += path.getDistance();
				crowding += mongo.getMaxCrowdingPath(path);
			}
			
				if (best.size() == 0 && effortLevel <= request.getEffortLevel()) {
					best = temp;
					bestDistance = distance;
					from += numberOfSuggestedPOIs + 1;
					to += numberOfSuggestedPOIs + 1;
					routeLevelOfCrowding = crowding;
				}
				else if (distance < bestDistance && effortLevel <= request.getEffortLevel()) {
					best = temp;
					bestDistance = distance;
					from += numberOfSuggestedPOIs + 1;
					to += numberOfSuggestedPOIs + 1;
					routeLevelOfCrowding = crowding;
				} else {
					from += numberOfSuggestedPOIs + 1;
					to += numberOfSuggestedPOIs + 1;
				}
		}

		bestPathwrapper = best;
		System.out.println("BEST PATHWRAPPER SIZE IS: " + bestPathwrapper.size());
		return best;
	}

	private int calculateCalories(List<PathWrapper> temp) {
		double calories = 0;

		for (int i = 0; i < temp.size(); i++) {

			double distance = temp.get(i).getDistance();
			double time = temp.get(i).getTime() / 1000;
			double height1 = temp.get(i).getPoints().getEle(0);

			double tempHeight = 0;
			for (int j = 0; j < temp.get(i).getPoints().size(); j++) {
				tempHeight += temp.get(i).getPoints().getEle(j);
			}
			double height2 = (tempHeight / temp.get(i).getPoints().size());

			Effort e = new Effort(distance, time, height1, height2);

			calories += e.calorieEstimation();

		}

		return (int) calories;
	}

	private int analyzeEffort(List<PathWrapper> temp) {

		double effort = 0;
		double maxEffort = 0;

		// int j = 1;
		for (int i = 0; i < temp.size() - 1; i++) {
			System.out.println("THIS IS TEMP GET " + i + " String " + temp.get(i).toString());
			System.out.println("TEMP GET DISTANCE " + i + " DISTANCE " + temp.get(i).getDistance());
			System.out.println("TEMP GET TIME " + i + " TIME " + temp.get(i).getTime());
			System.out.println("POINTS: " + temp.get(i).getPoints());
			System.out.println("ASCEND IS " + temp.get(i).getAscend());
			System.out.println("DESCEND IS " + temp.get(i).getDescend());
			double distance = temp.get(i).getDistance();
			double time = temp.get(i).getTime() / 1000;
			double height1 = temp.get(i).getPoints().getEle(0);

			double tempHeight = 0;
			for (int j = 0; j < temp.get(i).getPoints().size(); j++) {
				tempHeight += temp.get(i).getPoints().getEle(j);
			}
			double height2 = (tempHeight / temp.get(i).getPoints().size());

			System.out.println("HEIGHT 1 IS: " + height1 + " HEIGHT2 IS: " + height2);
			// System.out.println("TEMP GET ELEVATION " + i + " ELEVATION " +
			// temp.get(i).getPoints().getEle(index));

			Effort e = new Effort(distance, time, height1, height2);
			// j++;
			effort += e.getMET();

			if (e.getMET() >= maxEffort) {
				maxEffort = e.getMET();
			}
		}

		System.out.println("MAX EFFORT: " + maxEffort);
		int effortLevel = 0;

		if (maxEffort < 3) {
			effortLevel = 1;
		}
		if (maxEffort >= 3 && maxEffort <= 6) {
			effortLevel = 2;
		}
		if (maxEffort > 6)
			effortLevel = 3;

		System.out.println("Effort is " + effort / temp.size());
		return effortLevel;
	}

	private LinkedList<PointOfInterest> getOrderOfPOIs() {
		ArrayList<GHPoint> temp = new ArrayList<>();
		for (PathWrapper path : bestPathwrapper) {
			PointList pointList = path.getPoints();
			double lati = pointList.getLat(0);
			double loni = pointList.getLon(0);
			GHPoint origin = new GHPoint(lati, loni);

			// double latf = pointList.getLat(pointList.size() - 1);
			// double lonf = pointList.getLon(pointList.size() - 1);
			// GHPoint destination = new GHPoint(latf, lonf);

			temp.add(origin);
			// temp.add(destination);
		}
		System.out.println("O tamanho do TEMP é " + temp.size());

		if (temp.size() > 0) {
			temp.remove(0);
		}

		LinkedList<PointOfInterest> randomPois = routePOIs;
		LinkedList<PointOfInterest> orderedPois = new LinkedList<>();
		PointOfInterest bestPoi = null;

		int i = 0;
		while (i < temp.size()) {

			double distance = 0;
			double minorDistance = 0;

			for (PointOfInterest poi : randomPois) {

				distance = distFrom(temp.get(i).lat, temp.get(i).lon, poi.getLatitude(), poi.getLongitude());
				if (minorDistance == 0) {
					bestPoi = poi;
					minorDistance = distance;

				} else if (distance < minorDistance) {
					bestPoi = poi;
					minorDistance = distance;

				}

			}
			orderedPois.add(bestPoi);
			randomPois.remove(bestPoi);
			i++;
		}
		System.out.println("OrderedPOIs size is: " + orderedPois.size());
		for (PointOfInterest poi : orderedPois) {
			System.out.println("Contenho o POI " + poi.getName());
			this.routeOrderedPois.add(poi);
		}
		return orderedPois;
	}

	/**
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
		double earthRadius = 6371000; // meters
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = (earthRadius * c);

		return dist;
	}

	/**
	 * 
	 * @param paths
	 */
	public void pathInfo(List<PathWrapper> paths) {
		// create a calendar using the initial time asked by the user
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(request.getDepartureDate());
		LinkedList<PointOfInterest> orderedPois = getOrderOfPOIs();
		// this.routeOrderedPois = orderedPois;
		int contadorPOIs = 0;
		PointOfInterest poi = null;
		for (PathWrapper path : paths) {
			if (contadorPOIs < orderedPois.size()) {
				poi = orderedPois.get(contadorPOIs);
			}
			// System.out.println(path.toString());
			// points, distance in meters and time in millis of the full path
			PointList pointList = path.getPoints();
			double distance = path.getDistance();
			long timeInMs = path.getTime();

			System.out.println("TENHO " + paths.size() + " CAMINHOS ALTERNATIVOS");

			double lati = pointList.getLat(0);
			System.out.println("This is lati " + lati);
			double loni = pointList.getLon(0);
			System.out.println("This is loni " + loni);
			double latf = pointList.getLat(pointList.size() - 1);
			System.out.println("This is latf " + latf);
			double lonf = pointList.getLon(pointList.size() - 1);
			System.out.println("This is lonf " + lonf);

			if (path != null) {
				contadorPOIs++;
				QueryResult start = hopper.getLocationIndex().findClosest(lati, loni, EdgeFilter.ALL_EDGES);
				QueryResult end = hopper.getLocationIndex().findClosest(latf, lonf, EdgeFilter.ALL_EDGES);

				EdgeIteratorState startEdge = start.getClosestEdge();
				EdgeIteratorState endEdge = end.getClosestEdge();

				GHPoint startPoint = start.getQueryPoint();
				GHPoint endPoint = end.getQueryPoint();

				GHPoint3D startPoint3D = start.getSnappedPoint();
				GHPoint3D endPoint3D = end.getSnappedPoint();

				System.out.println("--------------------------------------------");
				System.out.println("SHOWING BEST PATH:");
				System.out.println("From: " + startEdge.getName() + " (edge)\t" + startPoint + " (point)\t"
						+ startPoint3D + " (point3D)");
				System.out.println(
						"To: " + endEdge.getName() + " (edge)\t" + endPoint + " (point)\t" + endPoint3D + " (point3D)");

				printPath(path, startEdge, endEdge, calendar, poi);
			}
		}
	}

	/**
	 * 
	 * @param path
	 * @param startEdge
	 * @param endEdge
	 * @param calendar
	 * @param poi
	 */
	private void printPath(PathWrapper path, EdgeIteratorState startEdge, EdgeIteratorState endEdge, Calendar calendar,
			PointOfInterest poi) {

		// ConvertSecondToHHMMSSString(instruction.getTime() / 1000);
		System.out.println("--------------------------------------------");
		System.out.println("INSTRUCTIONS:");
		InstructionList il = path.getInstructions();

		Translation transl = new TranslationMap.TranslationHashMap(Locale.ENGLISH);

		// iterate over every turn instruction
		System.out.println("Start at: " + startEdge.getName());
		System.out.println("Right now it's: " + calendar.getTime());
		for (Instruction instruction : il) {
			// System.out.print(
			// ">> " + getInstructionMessage(instruction) + "|" +
			// instruction.getTurnDescription(transl) + " ");

			calendar.add(Calendar.SECOND, (int) (instruction.getTime() / 1000));
			routeEndTime = new Timestamp(calendar.getTimeInMillis());
//			System.out.print(instruction.getName() + " for " + calendar.toString());
			// System.out.print(instruction.getName() + " for " + instruction.getTime() /
			// 1000 + "s ");
			System.out.printf("(%.1fm)", instruction.getDistance());
			System.out.print("\tLength: " + instruction.getLength() + "\n");
		}
		System.out.println("Stop at: " + endEdge.getName());
		System.out.println("User stops at this point at: " + calendar.getTime() + " and visits the POI");

		timestampOfVisits.add(new Timestamp(calendar.getTimeInMillis()));

		calendar.add(Calendar.MINUTE, poi.getVisitTime());

		System.out.println("--------------------------------------------");
		System.out.println("PATH INFO:");
		System.out.printf("Points: %d\tTotal distance: %.1f m\tTotal time: %.1f s\n", path.getPoints().size(),
				path.getDistance(), path.getTime() / 1000.0);
		routeDistance += path.getDistance();
		this.routeTime += path.getTime();

		System.out.println("--------------------------------------------");
		System.out.println("POINTS:");
		for (GHPoint3D p3d : path.getPoints()) {
			System.out
					.println("Lat: " + p3d.getLat() + "\tLon: " + p3d.getLon() + "\tElevation: " + p3d.getElevation());
			this.routeLine.add(p3d);
			// addTimeToCalendar(calendar,p3d);
		}

		// // or get the json
		// List<Map<String, Object>> iList = il.createJson();
		//
		// // or get the result as gpx entries:
		// List<GPXEntry> list = il.createGPXList();
	}

	// private void addTimeToCalendar(Calendar calendar, GHPoint3D p3d) {
	//
	// request.getSelectedPoints();
	// int time = db.getVisitTime(p3d.getLat(), p3d.getLon());
	// }

	// method to convert the route time from seconds to minutes
	private String ConvertSecondToHHMMSSString(long nSecondTime) {
		return LocalTime.MIN.plusSeconds(nSecondTime).toString();
	}

	/**
	 * Method for knowing the total price of visiting the selected POIs
	 * 
	 * @param selectedPoints
	 * @return
	 */
	public double evaluateBudget(List<PointOfInterest> selectedPoints) {
		double price = 0;
		if (selectedPoints.size() != 0) {
			for (PointOfInterest p : selectedPoints) {
				price += p.getPrice();
			}
			if (price >= request.getBudget()) {
				System.out.println("Percurso impossível de ser efetuado");
			}
		}
		System.out.println("Total price: " + price);
		return price;
	}

	/**
	 * Method for knowing the total price of visiting the selected POIs
	 * 
	 * @param selectedPoints
	 * @return
	 */
	public double evaluateSustainability(LinkedList<PointOfInterest> selectedPoints) {
		int averageSustainability = 0;
		if (selectedPoints.size() > 0) {
			for (PointOfInterest p : selectedPoints) {
				averageSustainability += p.getSustainability();
			}
			averageSustainability = averageSustainability / selectedPoints.size();
		} else {
			averageSustainability = 0;
		}
		System.out.println("Average sustainability: " + averageSustainability);
		return averageSustainability;
	}

	/**
	 * 
	 * @param instruction
	 * @return
	 */
	private String getInstructionMessage(Instruction instruction) {
		String message;
		switch (instruction.getSign()) {
		case Instruction.CONTINUE_ON_STREET:
			message = "CONTINUE_ON_STREET";
			break;
		case Instruction.FINISH:
			message = "FINISH";
			break;
		case Instruction.IGNORE:
			message = "IGNORE";
			break;
		case Instruction.KEEP_LEFT:
			message = "KEEP_LEFT";
			break;
		case Instruction.KEEP_RIGHT:
			message = "KEEP_RIGHT";
			break;
		case Instruction.LEAVE_ROUNDABOUT:
			message = "LEAVE_ROUNDABOUT";
			break;
		case Instruction.PT_END_TRIP:
			message = "PT_END_TRIP";
			break;
		case Instruction.PT_START_TRIP:
			message = "PT_START_TRIP";
			break;
		case Instruction.PT_TRANSFER:
			message = "PT_TRANSFER";
			break;
		case Instruction.REACHED_VIA:
			message = "REACHED_VIA";
			break;
		case Instruction.TURN_LEFT:
			message = "TURN_LEFT";
			break;
		case Instruction.TURN_RIGHT:
			message = "TURN_RIGHT";
			break;
		case Instruction.TURN_SHARP_LEFT:
			message = "TURN_SHARP_LEFT";
			break;
		case Instruction.TURN_SHARP_RIGHT:
			message = "TURN_SHARP_RIGHT";
			break;
		case Instruction.TURN_SLIGHT_LEFT:
			message = "TURN_SLIGHT_LEFT";
			break;
		case Instruction.TURN_SLIGHT_RIGHT:
			message = "TURN_SLIGHT_RIGHT";
			break;
		case Instruction.U_TURN_LEFT:
			message = "U_TURN_LEFT";
			break;
		case Instruction.U_TURN_RIGHT:
			message = "U_TURN_RIGHT";
			break;
		case Instruction.U_TURN_UNKNOWN:
			message = "U_TURN_UNKNOWN";
			break;
		case Instruction.UNKNOWN:
			message = "UNKNOWN";
			break;
		case Instruction.USE_ROUNDABOUT:
			message = "USE_ROUNDABOUT";
			break;
		default:
			message = "*** WRONG INSTRUCTION ***";
			break;
		}
		return message;
	}

	/**
	 * 
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public String closestNode(double latitude, double longitude) {
		String s = null;
		// LocationIndex provides a way to map real world data "lat,lon" to internal
		// ids/indices of
		// a memory efficient graph - often just implemented as an array.
		LocationIndex li = hopper.getLocationIndex();
		// This method returns the closest QueryResult for the specified location
		// (lat,lon) and only if
		// the filter accepts the edge as valid candidate (e.g. filtering away car-only
		// results for bike search)
		// An object containing the closest node and edge for the specified location.The
		// node id has at least one
		// edge which is accepted from the specified edgeFilter.
		// If nothing is found the method QueryResult.isValid will return false.
		QueryResult ab = li.findClosest(latitude, longitude, EdgeFilter.ALL_EDGES);
		// Returns the closest matching node. -1 if nothing found, this should be
		// avoided via a call of 'isValid'
		int n = ab.getClosestNode();

		s = hopper.getGraphHopperStorage().getNodeAccess().getLat(n) + ":"
				+ hopper.getGraphHopperStorage().getNodeAccess().getLon(n);

		return s;

	}

	/**
	 * 
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public EdgeIteratorState closestEdge(double latitude, double longitude) {
		// LocationIndex provides a way to map real world data "lat,lon" to internal
		// ids/indices of
		// a memory efficient graph - often just implemented as an array.
		LocationIndex li = hopper.getLocationIndex();
		// This method returns the closest QueryResult for the specified location (lat,
		// lon) and only if
		// the filter accepts the edge as valid candidate (e.g. filtering away car-only
		// results for bike search)
		// An object containing the closest node and edge for the specified location.
		// The node id has at least one
		// edge which is accepted from the specified edgeFilter.
		// If nothing is found the method QueryResult.isValid will return false.
		QueryResult qr = li.findClosest(latitude, longitude, EdgeFilter.ALL_EDGES);
		// Returns the closest matching edge. Will be null if nothing found or call
		// isValid before
		EdgeIteratorState edge = qr.getClosestEdge();

		return edge;
	}

	/**
	 * 
	 * @return
	 */
	public GraphHopperStorage getGraphStorage() {
		return hopper.getGraphHopperStorage();
	}

	/**
	 * 
	 * @return
	 */
	public GraphHopper getGraphhopper() {
		return hopper;
	}

	/**
	 * 
	 * @return
	 */
	public LinkedList<PointOfInterest> getDatabasePOIs() {
		return databasePOIs;
	}

	/**
	 * 
	 * @return
	 */
	public Route createRouteForResponse() {
		// LinkedList<PointOfInterest> pois = routePOIs;
		// ArrayList<Timestamp> timestampOfPOIVisits = timestampOfVisits;

		LinkedList<TimedPointOfInterest> timedPOIs = new LinkedList<>();

		for (int i = 0; i < this.routeOrderedPois.size(); i++) {
			TimedPointOfInterest timepoi = new TimedPointOfInterest(this.routeOrderedPois.get(i),
					timestampOfVisits.get(i));
			timedPOIs.add(timepoi);
		}

		System.out.println("O tamanho de timedPOIs é: " + timedPOIs.size());

		ArrayList<GHPoint3D> line = this.routeLine;
		distances(line);

		int time = this.routeTime;
		
		for (int j = 0; j < this.routeOrderedPois.size(); j++) {
			time+=routeOrderedPois.get(j).getVisitTime()*60*1000;
		}
		System.out.println("Time is + " + time);

		double distance = this.routeDistance; // distance in meters
		System.out.println("Distance is + " + distance);

		double sustainability = evaluateSustainability(routeOrderedPois);
		System.out.println("Average sustainability is + " + sustainability);

		int price = (int) evaluateBudget(routeOrderedPois);
		System.out.println("Price is + " + price);

		GHPoint origin = request.getOrigin();
		System.out.println("Origin is + " + origin);

		GHPoint destination = request.getDestination();
		System.out.println("Destination is + " + destination);

		Timestamp startTime = request.getDepartureDate();
		System.out.println("Start time is + " + startTime);

		int calories = (int) calculateCalories(bestPathwrapper);
		System.out.println("Calories is + " + calories);

		Timestamp endTime = routeEndTime;
		System.out.println("End time is + " + endTime);

		Route suggestionRoute = new Route(timedPOIs, line, time, distance, (int) sustainability, price, origin,
				destination, startTime, endTime, calories);

		return suggestionRoute;
	}

	/**
	 * 
	 * @param route
	 * @return
	 */
	public RouteResponse dataForRouteResponse(Route route) {
		System.out.println("RESPONSE STATUS CODE IS: " + responseStatusCode);
		System.out.println("RESPONSE STRING IS: " + responseString);
		
		RouteResponse routeResponse = new RouteResponse(route, responseStatusCode, responseString);
		return routeResponse;
	}
	
	public void distances(ArrayList<GHPoint3D> line) {
		int j = 1;
		double dist = 0;
		for (int i = 0; i < line.size() - 1; i++) {
			dist = distFrom(line.get(i).lat, line.get(i).lon, line.get(j).lat, line.get(j).lon);
			j++;
			System.out.println(dist);
		}
	}

	public int getRouteLevelOfCrowding() {
		System.out.println("This is the level of crowding of the scenario " + routeLevelOfCrowding);
		return routeLevelOfCrowding;
	}

}
