package edu.upenn.cit594.processor;

import java.util.List;

import edu.upenn.cit594.data.PropertyValues;

public interface ResidentFieldTotal {
	
	public Double total(String zipcode, List<PropertyValues> data);
}
