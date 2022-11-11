package arcosin.genetic_algo.tsp.adaptation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class TSP {

	public static ArrayList<POI> tourManager = new ArrayList<POI>();

	private static final int GENERATIONS = 1000;
	private static final int POP_SIZE = 300;

	private static BufferedWriter logger;

	public static void main(String[] args) {
		initLog();

		POI[] map = TestData.map3;

		logln("Map:");

		for (POI poi : map) {
			tourManager.add(poi);
			log(poi.toString() + "  ");
		}

		Population pop = new Population(POP_SIZE, true);
		System.out.println("----------------------------------------------------------------");
		System.out.println("Initial distance: " + pop.getFittest().getDistance());
		logln();
		logln("Initial distance: " + pop.getFittest().getDistance());
		// System.out.println(pop.toString());
		System.out.println("Evolving...");

		for (int i = 0; i < GENERATIONS; i++) {
			pop = GeneticAlgo.evolve(pop);
		}

		System.out.println("Finished.");
		logln("Generations: " + GENERATIONS);
		logln("Population size: " + POP_SIZE);
		logln("Mutation rate: " + GeneticAlgo.MUTATION_RATE);
		logln("Tournament size: " + GeneticAlgo.TOURNAMENT_SIZE);
		logln("Elitism: " + GeneticAlgo.ELITISM);

		logln("Final distance: " + pop.getFittest().getDistance());
		System.out.println("Final distance: " + pop.getFittest().getDistance());

		System.out.println("Solution:");
		logln("Solution:");

		System.out.println(pop.getFittest().toString());
		logln(pop.getFittest().toString());

		System.out.println("----------------------------------------------------------------");

		closeLog();
	}

	private static void initLog() {
		try {
			File logDir = new File("logs");

			if (!logDir.exists()) {
				if (!logDir.mkdir()) {
					System.out.println("Log open failed.");
				}
			}

			String time = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(Calendar.getInstance().getTime());
			File logFile = new File("logs/TSP_Log_" + time + ".txt");
			logFile.createNewFile();
			logger = new BufferedWriter(new PrintWriter(logFile));
		} catch (IOException ioe) {
			System.out.println("Log open failed.");
			ioe.printStackTrace();
		}
	}

	private static void closeLog() {
		try {
			logger.flush();
			logger.close();
		} catch (IOException ioe) {
			System.out.println("Log close failed.");
			ioe.printStackTrace();
		}
	}

	private static void logln(String line) {
		try {
			logger.write(line + System.lineSeparator());
			logger.flush();
		} catch (IOException ioe) {
			System.out.println("Log write failed.");
			ioe.printStackTrace();
		}
	}

	private static void logln() {
		logln("");
	}

	private static void log(String line) {
		try {
			logger.write(line);
		} catch (IOException ioe) {
			System.out.println("Log write failed.");
			ioe.printStackTrace();
		}
	}
}
