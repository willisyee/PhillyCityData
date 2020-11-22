package edu.upenn.cit594.ui;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import edu.upenn.cit594.Main.Main;
import edu.upenn.cit594.datamanagement.CsvParkingReader;
import edu.upenn.cit594.datamanagement.CsvPropertyReader;
import edu.upenn.cit594.datamanagement.JsonParkingReader;
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.datamanagement.TextPopulationReader;
import edu.upenn.cit594.processor.*;
import edu.upenn.cit594.logging.Logger;

public class CommandLineUserInterface {
	
	ResidentialProcessor resP;
	TotalPopulationProcessor tPopP;
	ParkingProcessor parkingP;
	Method6Processor m6p;
	Logger logger = Logger.getInstance();
	private Map<Integer, Integer> map1 = new HashMap<>();
	private Map<Integer, Map<String, Double>> map2 = new HashMap<>();
	private Map<String, Integer> map3 = new HashMap<>();
	private Map<String, Integer> map4 = new HashMap<>();
	private Map<String, Integer> map5 = new HashMap<>();
	private Map<Integer, Integer> map6 = new HashMap<>();
	
	public CommandLineUserInterface(ResidentialProcessor rp, TotalPopulationProcessor tpp, Method6Processor m6p, ParkingProcessor parkingP) {
		this.resP = rp;
		this.tPopP = tpp;
		this.m6p = m6p;
		this.parkingP = parkingP;
	}

	public void start() {
		
		System.out.println("Welcome to the CIT 594 Group Project based on datasets from OpenDataPhilly.");
		System.out.println("We've calculated some statistics that we think you'll find interesting\n");
		printInputOptions();
		Scanner s = new Scanner(System.in);
		String input = "";
		boolean exitFlag = false;
		while(!exitFlag) {
			input = s.next();
			if(!isInputValid(input)) {
				System.out.println("\nThe input is invalid. Only values 0-6 are acceptable. Exiting now...");
				System.exit(0);
			}
			int choice= Integer.parseInt(input);
			logger.logUserChoice(Integer.toString(choice));
			if(choice == 0) {
				exitFlag = true;
				System.out.println("Exiting...bye bye");
				System.exit(0);
			}		
			else if(choice == 1) {
				if(map1.containsKey(choice)) {
					System.out.println(map1.get(choice));
				}
				else {
					int answer = tPopP.getTotalPopulationCount();
					map1.put(choice, answer);
					System.out.println(answer);
				}
			}
			else if(choice == 2) {
				if(map2.containsKey(choice)) {
					for (Map.Entry<String, Double> entry : map2.get(choice).entrySet()) {
						System.out.println(entry.getKey() + " " + parkingFormatter(entry.getValue()));
					}
				}
				else {
					TreeMap<String, Double> totalFinesPerCapita = parkingP.getTotalFinesPerCapita();
					map2.put(choice, totalFinesPerCapita);
					for (Map.Entry<String, Double> entry : totalFinesPerCapita.entrySet()) {
						System.out.println(entry.getKey() + " " + parkingFormatter(entry.getValue()));
					}
				}
			}
			else if(choice == 3) {
				System.out.println("Please enter a zip code for its avg Residential Mkt Value");
				input = s.next();
				logger.logZipInput(input);
				if(map3.containsKey(input)) {
					System.out.println(map3.get(input));
				}
				else {
					int answer = resP.getAvgResiMktValue(input);
					map3.put(input, answer);
					System.out.println(answer);
				}
			}
			else if(choice == 4) {
				System.out.println("Please enter a zip code for its avg Residential total livable area");
				input = s.next();
				logger.logZipInput(input);
				if(map4.containsKey(input)) {
					System.out.println(map4.get(input));
				}
				else {
					int answer = resP.getAvgResiTotalLivArea(input);
					map4.put(input, answer);
					System.out.println(answer);
				}
			}
			else if(choice == 5) {
				System.out.println("Please enter a zip code for its total Residential Mkt value per Capita");
				input = s.next();
				logger.logZipInput(input);
				if(map5.containsKey(input)) {
					System.out.println(map5.get(input));
				}
				else {
					int answer = resP.getTotalResiMktValPerCapita(input);
					map5.put(input, answer);
					System.out.println(answer);
				}
				
			}
			else if(choice == 6) {
				if(map6.containsKey(choice)) {
					System.out.println(map6.get(choice));
				}
				else {
					int answer = m6p.mktValPerCapitaPerWorstViolatingZip();
					map6.put(choice, answer);
					System.out.println(answer);
				}
				
			}
			System.out.println("\nInterested in learning more?" +
								"\nYES or NO?");
			String learnMore = s.next();
			
			if(!learnMore.toLowerCase().matches("yes|no")){
				System.out.println("\nThe input is invalid. Only value YES or NO is accepted. Exiting now...");
				System.exit(0);
			}
			if(learnMore.toLowerCase().matches("yes")){
				System.out.println();
				printInputOptions();
				continue;
			}
			exitFlag = true;
		}
		s.close();
		System.out.println("Exiting...bye bye");
		System.exit(0);
	}
	/**
	 * Prints the options to console
	 */
	public void printInputOptions() {
		System.out.println	("Enter a number between 0-6 to get cool stats per the following instructions:" + 
							"\n" + 
							"\nEnter 1: Total population for all the zip codes" + 
							"\nEnter 2: Total parking fines per capita by zip code" +
							"\nEnter 3: Average market value of homes by zip code of your choice" +
							"\nEnter 4: Average total livable area by zip code of your choice" +
							"\nEnter 5: Total residential market value per capita by zip code of your choice" +
							"\nEnter 6: Market value per capita for the zip code with the most parking violations" +
							"\nEnter 0: Exit the program");
	}
	/**
	 * Evaluates whether the user input is valid
	 * @param input
	 * @return
	 */
	public boolean isInputValid(String input) {
		if(input.matches("^[0-6]{1}")) {
			return true;
		}
		return false;
	}
	/**
	 * Formats the output for q2
	 * @param totalFines
	 * @return
	 */
	public String parkingFormatter(Double totalFines) {
		DecimalFormat formatter = new DecimalFormat("#0.0000");
		formatter.setRoundingMode(RoundingMode.FLOOR);
		return new String(formatter.format(totalFines));//By converting back to a Double, we lose some of the formatting (trailing zeros don't come through)
	}
	
	public static void main(String[] args) {
		
		String logFileName = "logger.txt";
		Main.logFile = logFileName;
		Logger logger = Logger.getInstance();
		String parkingFileFormat = "json";
		
		String parkingFilename = "parking.json";
		Reader parkingR;
		if (parkingFileFormat.equals("json")) {
			parkingR = new JsonParkingReader(parkingFilename);
		}
		else {
			parkingR = new CsvParkingReader(parkingFilename);
		}
		
		CsvPropertyReader propertyR = new CsvPropertyReader("properties.csv");
		TextPopulationReader textP = new TextPopulationReader("population.txt");
		
		ResidentialProcessor resP = new ResidentialProcessor(propertyR, textP);
		TotalPopulationProcessor tPopP = new TotalPopulationProcessor(textP);
		ParkingProcessor parkingP = new ParkingProcessor(parkingR, textP);
				
		Method6Processor m6p = new Method6Processor(textP, propertyR, parkingR);
		CommandLineUserInterface test = new CommandLineUserInterface(resP, tPopP, m6p, parkingP);
		test.start();
	}
}
