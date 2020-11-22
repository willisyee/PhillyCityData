package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.upenn.cit594.data.ZipCodePopulation;
import edu.upenn.cit594.logging.Logger;

public class TextPopulationReader implements Reader {

	protected String filename;
	private Logger logger = Logger.getInstance();
	/**
	 * Constructor	
	 * @param filename is the path of the file to be read
	 */
	public TextPopulationReader(String filename) {
		this.filename = filename;
	}
	/**
	 * Method to read and parse a text file of population data by zipcode
	 * @return a List of zipCodePopulation objects
	 */
	public List<ZipCodePopulation> getData() { 
		List<ZipCodePopulation> zipCodePopulationRecords = new ArrayList<ZipCodePopulation>(); 
		if(filename == null) {
			return zipCodePopulationRecords;
		}
		Scanner read = null;
		File inputFile = new File(filename);
		try {
			read = new Scanner(inputFile);
			while (read.hasNextLine()) {
				String line = read.nextLine();
				String [] splitLine = line.split("\\s", -1);
				
				boolean missingFieldFlag = isMissingData(splitLine); //Checking is there are null or empty datafields
				if(!missingFieldFlag) {
					String zip = splitLine[0];
					if(!isInteger(splitLine[1])) {//checking if 'pop' is an integer
						continue;
					}
					int pop = Integer.parseInt(splitLine[1]);
					if(zip.matches("^[0-9]{5}")) {//checking if zip is the correct format 
						ZipCodePopulation zipCodePopulation = new ZipCodePopulation(zip, pop);
						zipCodePopulationRecords.add(zipCodePopulation);
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
		return zipCodePopulationRecords;
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
	 * Helper function to determine if a datafield is the correct format to be transformed into the integer data type
	 * @param field is the string to evaluate
	 * @return true if it is the correct format and false if incorrect format
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
