package edu.upenn.cit594.processor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import edu.upenn.cit594.data.AllParkingViolations;
import edu.upenn.cit594.data.AllProperties;
import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.data.Property;

public class Analyzer {


	public int totalPopulationForAllZIPCodes(Population population) {
		int totalPopulation = 0;
		HashMap<Integer, Integer> populationData = population.getPopulation();
		for(Integer ZIPCode : populationData.keySet()) {
			totalPopulation = totalPopulation + populationData.get(ZIPCode);
		}
		return totalPopulation;
	}

	public void totalFinesPerCapita(AllParkingViolations allParkingViolations, 
			Population population) {

		//key is ZIP code, value is total fine from that zip code 
		HashMap<Integer, Double> totalFines = new HashMap<Integer, Double>();

		//key is ZIP code, value is total fine per Capita from that zip code 
		HashMap<Integer, Double> totalFinesperCapita = new HashMap<Integer, Double>();

		for(ParkingViolation v : allParkingViolations.getAllParkingViolations()) {

			//ignore it when ZIP code is unknown
			//ignore it when plate state is not "PA
			//ZIP code = 0 for the case when the field for ZIP code is missing
			if(v.getZIPCode() != 0 && v.getState().equals("PA")) {
				int ZIPCode = v.getZIPCode();
				Double fine = v.getFine();
				if(totalFines.containsKey(ZIPCode)) {
					totalFines.put(ZIPCode, totalFines.get(ZIPCode) + fine);
				} else {
					totalFines.put(ZIPCode, fine);
				}
			}
		}

		HashMap<Integer, Integer> populationData = population.getPopulation();

		for(Integer ZIPCode : totalFines.keySet()) {
			double fines = totalFines.get(ZIPCode);
			double capita = populationData.get(ZIPCode);

			//ignore it when popluation for a ZIPCode is 0
			if(!(capita == 0.0)) {
				double finesPerCapita = fines / capita;
				totalFinesperCapita.put(ZIPCode, finesPerCapita);
			}
		}

		Iterator<Integer> iter = totalFinesperCapita.keySet().iterator();

		while(iter.hasNext()) {

			int ZIPCode = iter.next();
			double value = totalFinesperCapita.get(ZIPCode);

			// display any ZIP Code for which the total aggregate fines is 0 
			//or for which the population is 0.
			if(value != 0) {

				System.out.print(ZIPCode + " ");
				//truncate to four digits
				System.out.printf("%.4f", Math.round(value* 10000 - 0.5)/ 10000.0);
				System.out.println();

			}			
		}
	}

	public int averageResidentialMarketValueOrTotalLivableArea(AllProperties allProperties, 
			CalculateMethod calculateMethod) {

		//use strategy design patter
		System.out.println("Enter a ZIP code");
		Scanner in = new Scanner(System.in);

		try {

			int ZIPCode = Integer.parseInt(in.nextLine());
			double total = 0;
			int numOfResidences = 0;

			for(Property p : allProperties.getAllProperties()) {
				if(p.getZIPCode() == ZIPCode) {
					numOfResidences++;
					total = total + calculateMethod.calculate(p);
				}
			}

			//this is not a zip code that is listed in the input files
			if(numOfResidences == 0) {
				in.close();
				return 0;
			} else {

				double result = total / numOfResidences;

				//truncate this double to a integer
				in.close();
				return (int) Math.round(result - 0.5);
			}
			//return 0 when the input cannot be parsed to integer, not a valid input
		} catch (NumberFormatException e) {
			in.close();
			return 0;
		}
	}


	public int totalResidentialMarketValuePerCapita(AllProperties allProperties, 
			Population population) {

		System.out.println("Enter a ZIP code");

		Scanner in = new Scanner(System.in);

		//how to throw exception when the input cannot be parsed

		try {
			int ZIPCode = Integer.parseInt(in.nextLine());
			double totalResidentalMarketValue = 0;

			//return 0 when population is 0, or is not listed
			if(!population.getPopulation().containsKey(ZIPCode)|| population.getPopulation().get(ZIPCode) == 0) {
				in.close();
				return 0;
			}

			int capita = population.getPopulation().get(ZIPCode);

			for(Property p : allProperties.getAllProperties()) {
				if(p.getZIPCode() == ZIPCode) {
					double value = p.getMarketValue();
					totalResidentalMarketValue = totalResidentalMarketValue + value;
				}
			}
			double result = totalResidentalMarketValue / capita;
			in.close();
			return (int) Math.round(result - 0.5);

		} catch (NumberFormatException e) {
			in.close();
			return 0;
		}
	}

	/*
	 * return the zip code wit the highest total market value
	 */

	public int highestAverageMarketValue(AllProperties allProperties) {

		//the key is zip code, the value is the total Market Value
		HashMap<Integer, Double> marketValueMap = new HashMap<>();

		for(Property p : allProperties.getAllProperties()) {
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
