package edu.upenn.cit594.data;

public class ZipCodePopulation {
	
	protected String zipCode;
	protected int population;
	
	/**
	 * Constructor
	 * @param zipCode is a string representation of a zip code
	 * @param population is an integer representation of the population for a given zip code
	 */
	public ZipCodePopulation(String zipCode, int population) {
		this.zipCode= zipCode;
		this.population = population; 
	}

	public String getZipCode() {
		return zipCode;
	}

	public int getPopulation() {
		return population;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public void setPopulation(int population) {
		this.population = population;
	}
}
