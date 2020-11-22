package edu.upenn.cit594.processor;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import edu.upenn.cit594.data.*;
import edu.upenn.cit594.datamanagement.*;

public class ResidentialProcessor {

	public Reader propertyValueCSV;
	public Reader populationReader;

	
	public ResidentialProcessor(Reader propertyValueCSV, Reader populationReader) {
		this.propertyValueCSV = propertyValueCSV;
		this.populationReader = populationReader;
	}
	
	/**
	 * 
	 * the following three methods pertain to questions 3,4
	 */
	public Integer getAvgResiMktValue(String zipCode) {
		return getAvgResiBlank(zipCode, new TotalMarketValue());
	}
	
	public Integer getAvgResiTotalLivArea(String zipCode) {
		return getAvgResiBlank(zipCode, new TotalLivArea());
	} 
	
	public Integer getAvgResiBlank(String zipCode, ResidentFieldTotal fieldData){
		int totalResidences = 0;
		List<PropertyValues> propertyList = propertyValueCSV.getData();
		for(PropertyValues p : propertyList) {
			if(p.zipCode.equals(zipCode)) {
				totalResidences++;
			}
		}
		Double totalFieldValue = fieldData.total(zipCode, propertyList);
	//	System.out.println(totalFieldValue + " totalfieldvalue");
	//
	//	System.out.println(totalResidences + " totalResidences");
		
		if(totalResidences == 0) {
			return 0;
		}
		
		double average = totalFieldValue/totalResidences;
		
		/*
		NumberFormat n = NumberFormat.getNumberInstance();
		n.setRoundingMode(RoundingMode.FLOOR);
		int truncatedAverage = Integer.parseInt(n.format(average));
		*/
		
		String stringAnswer = String.valueOf(average);
		String[] stringArray = stringAnswer.split("\\.");
		return Integer.parseInt(stringArray[0]);
	}
	
	/**
	 * method to get an answer for question 5
	 */
	public Integer getTotalResiMktValPerCapita(String zipCode) {
		Double totalMarketValue = new TotalMarketValue().total(zipCode, propertyValueCSV.getData());
		//System.out.println(totalMarketValue + "     totalMktValue");
		int zipCodePopulation = 0;
		List<ZipCodePopulation> populationList = populationReader.getData();
		for(ZipCodePopulation z : populationList) {
			if(z.getZipCode().equals(zipCode)) {
				zipCodePopulation = z.getPopulation();
			}
		}
		if(zipCodePopulation == 0) {
			return 0;
		}
		double answer = totalMarketValue/zipCodePopulation;
		String stringAnswer = String.valueOf(answer);
		String[] stringArray = stringAnswer.split("\\.");
		return Integer.parseInt(stringArray[0]);
	}
	
	public static void main(String[] args) {
	CsvPropertyReader c = new CsvPropertyReader("properties.csv");
	TextPopulationReader t = new TextPopulationReader("population.txt");
	
	ResidentialProcessor a = new ResidentialProcessor(c, t);
	//System.out.println(a.getAvgResiTotalLivArea("19103") + " avgresitotalarea");
	System.out.println(a.getAvgResiMktValue("19103") + " avgmktvalue");
	//System.out.println(a.getTotalResiMktValPerCapita("19103") + " 5 answer");

	}
}
