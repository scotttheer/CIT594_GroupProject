package edu.upenn.cit594.processor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.data.Property;

public class Analyzer {
	
	private int answerForQuestionOne;
	private TreeMap<Integer, Double> answerForQuestionTwo;
	//the key is the ZIP code, the value is the result
	private HashMap<Integer, Integer> answerForQuestionThree;
	//the key is the ZIP code, the value is the result
	private HashMap<Integer, Integer> answerForQuestionFour;
	//the key is the ZIP code, the value is the result
	private HashMap<Integer, Integer> answerForQuestionFive;
	private int answerForQuestionSix;
	
	public Analyzer() {
		answerForQuestionOne = -1;
		answerForQuestionTwo = new TreeMap<Integer, Double>();
		answerForQuestionThree = new HashMap<Integer, Integer>();
		answerForQuestionFour = new HashMap<Integer, Integer>();
		answerForQuestionFive = new HashMap<Integer, Integer>();
		answerForQuestionSix = -1;
	}
	
	public int questionOne(LinkedList<Population> allPopulation) {
		if(answerForQuestionOne == -1) {
			totalPopulationForAllZIPCodes(allPopulation);
		} 
		return answerForQuestionOne;
	}

	public void totalPopulationForAllZIPCodes(LinkedList<Population> allPopulation) {
		int totalPopulation = 0;
		for(Population p : allPopulation) {
			totalPopulation = totalPopulation + p.getPopulation();
		}
		answerForQuestionOne = totalPopulation; //for memoization
	}
	
	public void questionTwo(LinkedList<ParkingViolation> allViolations, LinkedList<Population> allPopulation) {

		if(answerForQuestionTwo.keySet().isEmpty()) {
			totalFinesPerCapita(allViolations, allPopulation);
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

	public void totalFinesPerCapita(LinkedList<ParkingViolation> allViolations, LinkedList<Population> allPopulation) {

		//key is ZIP code, value is total fine from that zip code 
		HashMap<Integer, Double> totalFines = new HashMap<Integer, Double>();

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
					answerForQuestionTwo.put(ZIPCode, finesPerCapita);
				}
			}
		}

	}
	
	public int questionThreeOrFour(int zip, LinkedList<Property> allProperties, CalculateMethod calculateMethod) {

		if(calculateMethod instanceof CalculateByResidentialMarketValue) {
			if(!answerForQuestionThree.containsKey(zip)) {
				int result = averageResidentialMarketValueOrTotalLivableArea(zip, allProperties, calculateMethod);
				answerForQuestionThree.put(zip, result);
				return result;
			} else {
				return answerForQuestionThree.get(zip);
			} 
		}
		else {
			if(!answerForQuestionFour.containsKey(zip)) {
				int result = averageResidentialMarketValueOrTotalLivableArea(zip, allProperties, calculateMethod);
				answerForQuestionFour.put(zip, result);
				return result;
			} else {
				return answerForQuestionFour.get(zip);
			} 
		}
	}

	public int averageResidentialMarketValueOrTotalLivableArea(int zip, LinkedList<Property> allProperties, CalculateMethod calculateMethod) {

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
	
	public int questionFive(int zip, LinkedList<Property> allProperties, LinkedList<Population> allPopulation) {

		if(!answerForQuestionFive.containsKey(zip)) {
			int result = totalResidentialMarketValuePerCapita(zip, allProperties, allPopulation);
			answerForQuestionFive.put(zip, result);
			return result;
		} else {
			return answerForQuestionFive.get(zip);
		}
	}

	public int totalResidentialMarketValuePerCapita(int zip, LinkedList<Property> allProperties, LinkedList<Population> allPopulation) {

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
	public int questionSix(LinkedList<Property> allProperties) {
		if(answerForQuestionSix == -1) {
			highestAverageMarketValue(allProperties);
		}
		return answerForQuestionSix;
	}

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
