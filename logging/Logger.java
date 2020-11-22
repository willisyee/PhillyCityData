package edu.upenn.cit594.logging;

import java.io.FileWriter;
import java.io.PrintWriter;

import edu.upenn.cit594.Main.Main;


public class Logger {
	private PrintWriter out;
	public static String filename = Main.logFile;
	/**
	 * Constructor
	 * @param filename
	 */
	private Logger(String filename) {
		try {
			out = new PrintWriter(new FileWriter(filename, true));
		}
		catch (Exception e) {}
	}
	
	private static Logger instance = null;
	
	/**
	 * Gets instance of the private logger instance (instantiated within Logger)
	 * @return
	 */
	public static Logger getInstance() {
		if (instance == null) {
			instance = new Logger(filename);
		}
		return instance;
	}
	/**
	 * Method to write a log entry at start of the program
	 * @param parkingFileFormat 
	 * @param parkingFileName
	 * @param propertyFileName
	 * @param populationFileName
	 * @param logFileName
	 */
	public void logStart(String parkingFileFormat, String parkingFileName, String propertyFileName, String populationFileName, String logFileName) { 
		out.println(System.currentTimeMillis() + " " + parkingFileFormat + " " + parkingFileName + " " + propertyFileName + " " + populationFileName + " " + logFileName );
		out.flush();
	}
	/**
	 * Method to write a log entry any time a Reader.getData()
	 * @param fileName
	 */
	public void logFileRead(String fileName) { 
		out.println(System.currentTimeMillis() + " " + fileName);
		out.flush();
	}
	/**
	 * Method to write a log entry when the user provides a statistical package input (0-6)
	 * @param choice
	 */
	public void logUserChoice(String choice) { 
		out.println(System.currentTimeMillis() + " " + choice);
		out.flush();
	}
	/**
	 * Method to write a log entry when the user provides a zip code of choice for stats 3-5
	 * @param zipCode
	 */
	public void logZipInput(String zipCode) { 
		out.println(System.currentTimeMillis() + " " + zipCode);
		out.flush();
	}
}
