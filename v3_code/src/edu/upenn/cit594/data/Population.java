package edu.upenn.cit594.data;

public class Population {
	
	private final int zip;
	private final int population;
	
	public Population(int ZIP, int pop){
		zip = ZIP;
		population = pop;
	}

	public int getPopulation() {
		return population;
	}

	public int getZip() {
		return zip;
	}
	
}