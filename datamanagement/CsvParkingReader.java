package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.logging.Logger;

public class CsvParkingReader implements Reader {

	protected String filename;
	private Logger logger = Logger.getInstance();
	
	/**
	 * Constructor	
	 * @param filename is the path of the file to be read
	 */
	public CsvParkingReader(String filename) {
		this.filename = filename;
	}
	/**
	 * Method to read and parse a csv file with parking violation data 
	 * @return a List of parkingViolation objects
	 */
	public List<ParkingViolation> getData() {
		List<ParkingViolation> parkingViolationRecords = new ArrayList<ParkingViolation>(); 
		if(filename == null) {
			return parkingViolationRecords;
		}
		Scanner read = null;
		File inputFile = new File(filename);
		try {
			read = new Scanner(inputFile);
			while (read.hasNextLine()) {
				String line = read.nextLine();
				String [] splitLine = line.split("\\s*,", -1);
				
				boolean missingFieldFlag = isMissingData(splitLine); //Checking is there are null or empty datafields
				if(!missingFieldFlag) {
					String date = splitLine[0];
					if(!isInteger(splitLine[1])) {//checking if 'fine' is an integer
						continue;
					}
					int fine = Integer.parseInt(splitLine[1]);
					String violation = splitLine[2];
					String plateID = splitLine[3];
					String state = splitLine[4];
					String ticketNumber = splitLine[5];
					String zipCode = splitLine[6];
					if(zipCode.matches("^[0-9]{5}") && state.matches("^[A-Z]{2}")) {//checking if 'zipCode' and 'state' are valid formats
						ParkingViolation parkingViolation = new ParkingViolation(ticketNumber, plateID, date, zipCode, violation, (int)fine, state);
						parkingViolationRecords.add(parkingViolation);
					}
				}
			}
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
		finally {
			read.close();
		}
		logger.logFileRead(filename);
		return parkingViolationRecords;
	}
	/**
	 * Helper method to determine if there are null or empty values in the array of data
	 * @param splitLine is a String array (presumed that each element in the array is a datafield)
	 * @return true if data is missing, false if no data missing
	 */
	public boolean isMissingData(String [] splitLine) {
		for(String dataField : splitLine) {
			if(dataField == null || dataField.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Helper method to determine if a string is an integer format
	 * @param field is the data field to test
	 * @return true if isInteger false if !isInteger
	 */
	public boolean isInteger(String field) {
		try { 
            Integer.parseInt(field); 
            return true; 
        }  
        catch (NumberFormatException e)  { 
            return false; 
        } 
	}
}
