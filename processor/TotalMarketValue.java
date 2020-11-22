package edu.upenn.cit594.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.upenn.cit594.data.PropertyValues;

public class TotalMarketValue implements ResidentFieldTotal{
	
	Map<String, Double> memoizationFor3and5 = new HashMap<>();
	//implement memoization, may count for both 3 , 5
	public Double total(String zipcode, List<PropertyValues> data) {
		Double totalMktValue = 0.0;
		for(PropertyValues p : data) {
			if(p.zipCode.equals(zipcode)) {
				totalMktValue += p.marketValue;
			}
		}
		return totalMktValue;
	}
	
	
}
