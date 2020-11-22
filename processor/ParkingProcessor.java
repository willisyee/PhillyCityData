package edu.upenn.cit594.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.ZipCodePopulation;
import edu.upenn.cit594.datamanagement.*;


public class ParkingProcessor {
	String parkingFileFormat;
	String parkingFilename;
	String populationFilename;
	protected Reader populationReader;
	protected Reader parkingReader;
	
	/**
	 * Constructor
	 * @param parkingReader
	 * @param populationReader
	 */
	public ParkingProcessor(Reader parkingReader, Reader populationReader) {
		this.parkingReader = parkingReader;
		this.populationReader = populationReader;
	}
	/**
	 * Calculates the aggregate fines per zipcode
	 * Excludes parking violations that were not in the state of "PA"
	 * @return
	 */
	public HashMap<String, Long> totalFinesPerZip(){
		HashMap<String, Long> totalFinesPerZip = new HashMap<String, Long>();
		List<ParkingViolation> finesRecord = parkingReader.getData();
		
		for(ParkingViolation violation: finesRecord) {
			String zipCode = violation.getZipCode();
			long fine = violation.getFine();
			if(!violation.getState().equals("PA")) {//exclude violations w/out "PA" license plate
				continue;
			}
			if(totalFinesPerZip.containsKey(zipCode)) {
				totalFinesPerZip.put(zipCode, totalFinesPerZip.get(zipCode) + fine);
			}
			else {
				totalFinesPerZip.put(zipCode, fine);
			}
		}
		return totalFinesPerZip;
	}
	/**
	 * Calculates the parking fines per capital per zipcode. 
	 * Excludes zipcodes with zero population or zero aggreates parking violation fines
	 * In ascending order by zipcode
	 * @return
	 */
	public TreeMap<String, Double> getTotalFinesPerCapita(){
		HashMap<String, Double> finesPerCapitaPerZip = new HashMap<String, Double>();
		HashMap<String, Long> totalFinesPerZip = totalFinesPerZip();
		List<ZipCodePopulation> popByZip = populationReader.getData();//should I first check to see if there's multiple entries? 
		
		for(ZipCodePopulation zcp: popByZip) {
			String zip = zcp.getZipCode();
			int pop = zcp.getPopulation();
			if(pop>0 && totalFinesPerZip.containsKey(zip)) {//Checking for zero population and a known zipcode
				double perCapita = (double)totalFinesPerZip.get(zip)/(double)pop;
				if(perCapita>0) {//Removing zipcodes with zero total fines
					finesPerCapitaPerZip.put(zip, perCapita);
				}
			}
		}
		TreeMap<String, Double> sortedFinesPerCapitaPerZip = new TreeMap<>(finesPerCapitaPerZip);//Ordering in ascending by key (zipcode)
		return sortedFinesPerCapitaPerZip;
	}
}
