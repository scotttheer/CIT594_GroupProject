package edu.upenn.cit594.data;

import java.util.HashMap;

public class Population {
	private HashMap<Integer, Integer> population = new HashMap<Integer, Integer>();
	
	/*
	public Population(HashMap<Integer, Integer> population) {
		this.population = population;
	}
	*/

	public HashMap<Integer, Integer> getPopulation() {
		return population;
	}

	public void setPopulation(HashMap<Integer, Integer> population) {
		this.population = population;
	}
	
	
	
}