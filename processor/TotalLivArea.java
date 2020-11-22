package edu.upenn.cit594.processor;

import java.util.List;

import edu.upenn.cit594.data.PropertyValues;

public class TotalLivArea implements ResidentFieldTotal{
	
	//implement memoization
	public Double total(String zipcode, List<PropertyValues> data) {
		Double totalfieldArea = 0.0;
		for(PropertyValues p : data) {
			if(p.zipCode.equals(zipcode)) {
				//System.out.println(p.zipCode + "this is the zip");
				totalfieldArea += p.totalLivableArea;
			}
		}
		return totalfieldArea;
	}
}
