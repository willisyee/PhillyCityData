package edu.upenn.cit594.processor;

import java.util.HashMap;
import java.util.List;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.PropertyValues;
import edu.upenn.cit594.data.ZipCodePopulation;
import edu.upenn.cit594.datamanagement.CsvParkingReader;
import edu.upenn.cit594.datamanagement.CsvPropertyReader;
import edu.upenn.cit594.datamanagement.JsonParkingReader;
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.datamanagement.TextPopulationReader;

/**
 * what is the mkt value per capita for the zipcode with the most parking violations
 * 	
 */

public class Method6Processor {
	Reader populationR;
	String parkingfileName;
	Reader propertyR;
	Reader parkingVioR;
	
	
	
	public Method6Processor(Reader populationR, Reader propertyR, Reader parkingR) {
		this.populationR = populationR;
		this.propertyR = propertyR;
		this.parkingVioR = parkingR;
	}
	/**
	 * produces a hashmap of zip codes and the number of times they appear in the parking data
	 */
	// memoization
	public HashMap<String, Integer> countParkingViolations(List<ParkingViolation> data) {
		HashMap<String, Integer> zipCount = new HashMap<>();
		for(ParkingViolation p : data) {
			Integer count = zipCount.get(p.getZipCode());
			if(count == null) {
				zipCount.put(p.getZipCode(), 1);
			}
			else {
				zipCount.put(p.getZipCode(), zipCount.get(p.getZipCode())+1);
			}
		}
		return zipCount;
	}
	
	/**
	 *  finds the zipCode with the greatest number of violations
	 */
	
	public String findMaxZipCodeVio() {
		String maxZipCode = "";
		Integer maxCount = null;
		HashMap<String, Integer> zipCodes = countParkingViolations(parkingVioR.getData());
		//System.out.println(zipCodes.size() + "size");
		for(String zp : zipCodes.keySet()) {
			if(maxCount == null) {
				maxCount = zipCodes.get(zp);
				maxZipCode = zp;
			}
			else if(zipCodes.get(zp) > maxCount) {
				maxCount = zipCodes.get(zp);
				maxZipCode = zp;
			}
		}
		return maxZipCode;
	}
	
	/**
	 * takes the zip code from the last method and finds the market value per capita for that zip code
	 * 
	 * 
	 */
	
	public int mktValPerCapitaPerWorstViolatingZip() {
		String worstZipCode = findMaxZipCodeVio();
		//System.out.println(worstZipCode + " worstZip");
		int zipCodePopulation = 0;
		List<ZipCodePopulation> populationList = populationR.getData();
		for(ZipCodePopulation z : populationList) {
			if(z.getZipCode().equals(worstZipCode)) {
				zipCodePopulation = z.getPopulation();
			}
		}
		//System.out.println(zipCodePopulation + " poptest");
		if(zipCodePopulation == 0) {
			return 0;
		}
		double totalMktValue = 0.0;
		List<PropertyValues> propertyList = propertyR.getData();
		for(PropertyValues p : propertyList) {
			if(p.zipCode.equals(worstZipCode)) {
				totalMktValue += p.marketValue;
			}
		}
		//System.out.println(totalMktValue + "   totalMktValue");
		double average = totalMktValue/zipCodePopulation;
		String stringAnswer = String.valueOf(average);
		String[] stringArray = stringAnswer.split("\\.");
		return Integer.parseInt(stringArray[0]);
	}
	
	public static void main(String[] args) {
		//Method6Processor m = new Method6Processor(new TextPopulationReader("population.txt"), new CsvPropertyReader("properties.csv"), "parking.json");
		//System.out.println(m.mktValPerCapitaPerWorstViolatingZip());
	}
}
