package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.data.Property;

public class Analyzer {
	
	public Analyzer() {}

	public int totalPopulationForAllZIPCodes(ArrayList<Population> allPopulation) {
		int totalPopulation = 0;
		for(Population p : allPopulation) {
			totalPopulation = totalPopulation + p.getPopulation();
		}
		return totalPopulation;
	}

	public void totalFinesPerCapita(ArrayList<ParkingViolation> allViolations, ArrayList<Population> allPopulation) {

		//key is ZIP code, value is total fine from that zip code 
		HashMap<Integer, Double> totalFines = new HashMap<Integer, Double>();
		TreeMap<Integer, Double> totalFinesPerCapita = new TreeMap<Integer, Double>();

		for(ParkingViolation v : allViolations) {

			//ignore it when ZIP code is unknown
			//ignore it when plate state is not "PA
			//ZIP code = 0 for the case when the field for ZIP code is missing
			if(v.getState().equals("PA")) {
				int ZIPCode = v.getZIPCode();
				Double fine = v.getFine();
				if(totalFines.containsKey(ZIPCode)) {
					totalFines.put(ZIPCode, totalFines.get(ZIPCode) + fine);
				} else {
					totalFines.put(ZIPCode, fine);
				}
			}
		}

		for(Integer ZIPCode : totalFines.keySet()) {
			double fines = totalFines.get(ZIPCode);
			if(fines == 0) {
				continue;
			}
			double finesPerCapita;
			int pop;
			for(Population p: allPopulation) {
				pop = p.getPopulation();
				if(p.getZip() == ZIPCode && pop != 0) {
					finesPerCapita = fines/pop;
					totalFinesPerCapita.put(ZIPCode, finesPerCapita);
				}
			}
		}


		for(int zip: totalFinesPerCapita.keySet()) {
				System.out.print(zip + " ");
				//truncate to four digits
				System.out.printf("%.4f", Math.round(totalFinesPerCapita.get(zip)* 10000 - 0.5)/ 10000.0);
				System.out.println();
		}

	}

	public int averageResidentialMarketValueOrTotalLivableArea(int zip, ArrayList<Property> allProperties, CalculateMethod calculateMethod) {

		double total = 0;
		int numOfResidences = 0;

		for(Property p : allProperties) {
			if(p.getZIPCode() == zip) {
				numOfResidences++;
				total = total + calculateMethod.calculate(p);
			}
		}

		//this is not a zip code that is listed in the input files
		if(numOfResidences == 0) {
			return 0;
		} else {
			double result = total / numOfResidences;
			return (int) Math.round(result - 0.5);
		}

	}

	public int totalResidentialMarketValuePerCapita(int zip, ArrayList<Property> allProperties, ArrayList<Population> allPopulation) {

		double totalResidentalMarketValue = 0;
		double population = 0.0;

		//return 0 when population is 0, or is not listed
		for(Population pop: allPopulation) {
			if(pop.getZip() == zip) {
				population = pop.getPopulation();
			}
		}
		
		if(population == 0) {
			return 0;
		}

		for(Property p : allProperties) {
			if(p.getZIPCode() == zip) {
				double value = p.getMarketValue();
				totalResidentalMarketValue = totalResidentalMarketValue + value;
			}
		}
		double result = totalResidentalMarketValue / population;
		return (int) Math.round(result - 0.5);

	}

	/*
	 * return the zip code wit the highest total market value
	 */

	public int highestAverageMarketValue(ArrayList<Property> allProperties) {

		//the key is zip code, the value is the total Market Value
		HashMap<Integer, Double> marketValueMap = new HashMap<>();

		for(Property p : allProperties) {
			int ZIPCode = p.getZIPCode();
			double value = p.getMarketValue();

			if(marketValueMap.containsKey(ZIPCode)) {
				marketValueMap.put(ZIPCode, marketValueMap.get(ZIPCode) + value);
			} else {
				marketValueMap.put(ZIPCode, value);
			}
		}

		double higestMarketValue = 0;
		int ZIPCodeWithHighestMarketValue = 0;

		for(int ZIPCode : marketValueMap.keySet()) {
			double thisMarketValue = marketValueMap.get(ZIPCode);
			if(thisMarketValue > higestMarketValue) {
				higestMarketValue = thisMarketValue;
				ZIPCodeWithHighestMarketValue = ZIPCode;
			}
		} 

		return ZIPCodeWithHighestMarketValue;
	}

}
