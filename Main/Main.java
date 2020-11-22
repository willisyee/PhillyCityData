package edu.upenn.cit594.Main;
import java.io.File;

import edu.upenn.cit594.datamanagement.CsvParkingReader;
import edu.upenn.cit594.datamanagement.CsvPropertyReader;
import edu.upenn.cit594.datamanagement.JsonParkingReader;
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.datamanagement.TextPopulationReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Method6Processor;
import edu.upenn.cit594.processor.ParkingProcessor;
import edu.upenn.cit594.processor.ResidentialProcessor;
import edu.upenn.cit594.processor.TotalPopulationProcessor;
import edu.upenn.cit594.ui.CommandLineUserInterface;

public class Main {
	
	public static String logFile;
	
	public static void main(String[] args) {
		
		if(args.length != 5) {
			System.out.println("error: 5 arguments are not present");
			System.exit(0);
		}
		
		if(!args[0].toLowerCase().contains("csv") && !args[0].toLowerCase().contains("json")) {
			System.out.println("error: format specified is neither csv nor json");
			System.exit(0);
		}
		
		if(!new File((String) args[1]).canRead() || !new File((String) args[2]).canRead() || !new File((String) args[3]).canRead()) {
			System.out.println("File not found or cannot access file");
			System.exit(0);
		}
		
		String parkingFileFormat = args[0];
		String parkingFileName = args[1];
		String propertyFileName = args[2];
		String populationFileName = args[3];
		String logFileName = args[4];
		
		logFile = logFileName;	
		Logger logger = Logger.getInstance();
		logger.logStart(parkingFileFormat, parkingFileName, propertyFileName, populationFileName, logFileName);
		
		Reader parkingR;
		if (parkingFileFormat.toLowerCase().equals("json")) {
			parkingR = new JsonParkingReader(parkingFileName);
		}
		else {
			parkingR = new CsvParkingReader(parkingFileName);
		}
		
		CsvPropertyReader propertyR = new CsvPropertyReader(propertyFileName);
		TextPopulationReader textP = new TextPopulationReader(populationFileName);
		
		ResidentialProcessor resP = new ResidentialProcessor(propertyR, textP);
		TotalPopulationProcessor tPopP = new TotalPopulationProcessor(textP);
		ParkingProcessor parkingP = new ParkingProcessor(parkingR, textP);
				
		Method6Processor m6p = new Method6Processor(textP, propertyR, parkingR);
		CommandLineUserInterface test = new CommandLineUserInterface(resP, tPopP, m6p, parkingP);
		test.start();
	}
}
