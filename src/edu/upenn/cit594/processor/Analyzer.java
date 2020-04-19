package edu.upenn.cit594.processor;

import java.util.HashMap;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.logging.Logger;

public class Analyzer {

	private int answerForQuestionOne;

	private HashMap<Integer, Double> answerForQuestionTwo;

	//the key is the ZIP code, the value is the result
	private HashMap<Integer, Integer> answerForQuestionThree;

	//the key is the ZIP code, the value is the result
	private HashMap<Integer, Integer> answerForQuestionFour;

	//the key is the ZIP code, the value is the result
	private HashMap<Integer, Integer> answerForQuestionFive;

	private int answerForQuestionSix;

	protected Logger log;

	public Analyzer(Logger logger) {
		log = logger;
		answerForQuestionOne = -1;
		answerForQuestionTwo = new HashMap<Integer, Double>();
		answerForQuestionThree = new HashMap<Integer, Integer>();
		answerForQuestionFour = new HashMap<Integer, Integer>();
		answerForQuestionFive = new HashMap<Integer, Integer>();
		answerForQuestionSix = -1;
	}

	public int questionOne(Population population) {
		if(answerForQuestionOne == -1) {
			totalPopulationForAllZIPCodes(population);
		} 
		return answerForQuestionOne;
	}

	public void totalPopulationForAllZIPCodes(Population population) {
		int totalPopulation = 0;
		HashMap<Integer, Integer> populationData = population.getPopulation();
		for(Integer ZIPCode : populationData.keySet()) {
			totalPopulation = totalPopulation + populationData.get(ZIPCode);
		}
		answerForQuestionOne = totalPopulation; //for memoization
	}

	public void questionTwo(LinkedList<ParkingViolation> allParkingViolations, 
			Population population) {

		if(answerForQuestionTwo.keySet().isEmpty()) {
			
			totalFinesPerCapita(allParkingViolations, 
					population);
		}
		
		Iterator<Integer> iter = answerForQuestionTwo.keySet().iterator();

		while(iter.hasNext()) {
			
			int ZIPCode = iter.next();
			double value = answerForQuestionTwo.get(ZIPCode);

			// display any ZIP Code for which the total aggregate fines is 0 
			//or for which the population is 0.
			if(value != 0.0) {

				System.out.print(ZIPCode + " ");
				//truncate to four digits
				System.out.printf("%.4f", Math.round(value* 10000 - 0.5)/ 10000.0);
				System.out.println();

			}			
		}
	}


	public void totalFinesPerCapita(LinkedList<ParkingViolation> allParkingViolations, 
			Population population) {

		//key is ZIP code, value is total fine from that zip code 
		HashMap<Integer, Double> totalFines = new HashMap<Integer, Double>();

		for(ParkingViolation v : allParkingViolations) {
			
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

		HashMap<Integer, Integer> populationData = population.getPopulation();

		for(Integer ZIPCode : totalFines.keySet()) {
			
			if(populationData.keySet().contains(ZIPCode)) {
				
				double fines = totalFines.get(ZIPCode);
				double capita = populationData.get(ZIPCode);

				//ignore it when popluation for a ZIPCode is 0
				if(capita != 0.0) {
					
					double finesPerCapita = fines / capita;
					answerForQuestionTwo.put(ZIPCode, finesPerCapita);
				}
			}
		}
	}

	public int questionThreeOrFour(LinkedList<Property> allProperties, 
			CalculateMethod calculateMethod) {

		//use strategy design patter
		System.out.println("Enter a ZIP code");
		Scanner in = new Scanner(System.in);

		String userZIP = in.nextLine();

		log.logUserZip(userZIP);

		try {
			int ZIPCode = Integer.parseInt(userZIP);
			if(calculateMethod instanceof CalculateByResidentialMarketValue) {
				if(!answerForQuestionThree.containsKey(userZIP)) {
					int result = averageResidentialMarketValueOrTotalLivableArea(allProperties, 
							calculateMethod, ZIPCode);
					answerForQuestionThree.put(ZIPCode, result);
					return result;
				} else {
					return answerForQuestionThree.get(ZIPCode);
				} 
			}
			else {
				if(!answerForQuestionFour.containsKey(userZIP)) {
					int result = averageResidentialMarketValueOrTotalLivableArea(allProperties, 
							calculateMethod, ZIPCode);
					answerForQuestionFour.put(ZIPCode, result);
					return result;
				} else {
					return answerForQuestionFour.get(ZIPCode);
				} 
			}
			//return 0 when the input cannot be parsed to integer, not a valid input
		} catch (NumberFormatException e) {
			return 0;
		}
	}



	public int averageResidentialMarketValueOrTotalLivableArea(LinkedList<Property>  allProperties, 
			CalculateMethod calculateMethod, int ZIPCode) {

		double total = 0;
		int numOfResidences = 0;

		for(Property p : allProperties) {
			if(p.getZIPCode() == ZIPCode) {
				numOfResidences++;
				total = total + calculateMethod.calculate(p);
			}
		}

		//this is not a zip code that is listed in the input files
		if(numOfResidences == 0) {
			return 0;
		} else {

			double result = total / numOfResidences;

			//truncate this double to a integer
			return (int) Math.round(result - 0.5);
		}
	}

	public int questionFive(LinkedList<Property> allProperties, 
			Population population) {

		System.out.println("Enter a ZIP code");

		Scanner in = new Scanner(System.in);

		String userZIP = in.nextLine();
		log.logUserZip(userZIP);

		try {
			int ZIPCode = Integer.parseInt(userZIP);
			if(!answerForQuestionFive.containsKey(ZIPCode)) {
				int result = totalResidentialMarketValuePerCapita(allProperties, 
						population, ZIPCode);
				answerForQuestionFive.put(ZIPCode, result);
				return result;
			} else {
				return answerForQuestionFive.get(ZIPCode);
			}
			//return 0 when the input cannot be parsed to a integer
		} catch (NumberFormatException e) {
			return 0;
		}
	}


	public int totalResidentialMarketValuePerCapita(LinkedList<Property> allProperties, 
			Population population, int ZIPCode) {

		double totalResidentalMarketValue = 0;

		//return 0 when population is 0, or is not listed
		if(!population.getPopulation().containsKey(ZIPCode)|| population.getPopulation().get(ZIPCode) == 0) {
			return 0;
		}

		int capita = population.getPopulation().get(ZIPCode);

		for(Property p : allProperties) {
			if(p.getZIPCode() == ZIPCode) {
				double value = p.getMarketValue();
				totalResidentalMarketValue = totalResidentalMarketValue + value;
			}
		}
		double result = totalResidentalMarketValue / capita;
		return (int) Math.round(result - 0.5);
	}


	public int questionSix(LinkedList<Property> allProperties) {
		if(answerForQuestionSix == -1) {
			highestAverageMarketValue(allProperties);
		}
		return answerForQuestionSix;
	}



	/*
	 * return the zip code wit the highest total market value
	 */

	public void highestAverageMarketValue(LinkedList<Property> allProperties) {

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
		answerForQuestionSix = ZIPCodeWithHighestMarketValue;
	}

}
