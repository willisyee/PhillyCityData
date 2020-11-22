package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.logging.Logger;

public class JsonParkingReader implements Reader{
	
	protected String filename;
	private Logger logger = Logger.getInstance();
	
	/**
	 * Constructor
	 * @param filename is the path of the file to be read
	 */
	public JsonParkingReader(String filename) {
		this.filename = filename;
	}
	/**
	 * Method to read and parse a json file with parking violation data 
	 * @return a List of parkingViolation objects
	 */
	public List<ParkingViolation> getData() {
		List<ParkingViolation> parkingViolationRecords = new ArrayList<ParkingViolation>();
		File inputFile = new File(filename);
		FileReader reader = null;
		try {
			reader = new FileReader(filename);
		    JSONParser jsonParser = new JSONParser();
		    JSONArray jsonFileArray = (JSONArray) jsonParser.parse(reader);
		    Iterator<JSONObject> iteratorArray = jsonFileArray.iterator();
			while(iteratorArray.hasNext()) {
				JSONObject jsonParkingViolation = iteratorArray.next();
				if(isMissingData(jsonParkingViolation)) {//checking to see if there are any empty or null values
					continue;
				}
				String state = (String) jsonParkingViolation.get("state");
				String plateID = (String) jsonParkingViolation.get("plate_id");
				String date = (String) jsonParkingViolation.get("date");
				String zipCode = (String) jsonParkingViolation.get("zip_code");
				String violation = (String) jsonParkingViolation.get("violation");
				//START - This block of code isn't working to catch Illegal states (wrong data type for the fine and ticket_number attributes
				long fine = 0;
				long ticketNumber = 0;
				try {
					fine = (long) jsonParkingViolation.get("fine");
					ticketNumber = (long) jsonParkingViolation.get("ticket_number");
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				//END
				if(zipCode.matches("^[0-9]{5}") && state.matches("^[A-Z]{2}")) {
					ParkingViolation parkingViolation = new ParkingViolation(Integer.toString((int)ticketNumber), plateID, date, zipCode, violation, (int)fine, state);
					parkingViolationRecords.add(parkingViolation);
				}
			}
		} 
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
		logger.logFileRead(filename);
		return parkingViolationRecords; 
	}
	
	private boolean isMissingData(JSONObject jsonObject) {
		for (Object value : jsonObject.keySet()) {
			if(jsonObject.get(value).toString() == null || jsonObject.get(value).toString().isEmpty()) {
				return true;
			}
		}
		return false;
	}
}