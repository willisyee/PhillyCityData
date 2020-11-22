package edu.upenn.cit594.processor;
import edu.upenn.cit594.datamanagement.*;

import java.util.List;

import edu.upenn.cit594.data.*;

public class TotalPopulationProcessor {
	Reader populationReader;
	
	public TotalPopulationProcessor(Reader populationReader) {
		this.populationReader = populationReader;
	}
	
	public int getTotalPopulationCount() {
		return calculateTotalPop(populationReader.getData());
	}
	
	//memoization
	public int calculateTotalPop(List<ZipCodePopulation> list) {
		int count = 0;
		for(ZipCodePopulation z : list) {
			count += z.getPopulation(); 
		}
		return count;
	}
	
	public static void main(String[] args) {
		TotalPopulationProcessor t = new TotalPopulationProcessor(new TextPopulationReader("population.txt"));
		System.out.println(t.getTotalPopulationCount());
	}
}
