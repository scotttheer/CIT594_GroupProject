package edu.upenn.cit594.ui;

import java.util.LinkedList;
import java.util.Scanner;

import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.processor.Analyzer;
import edu.upenn.cit594.processor.CalculateByResidentialMarketValue;
import edu.upenn.cit594.processor.CalculateByResidentialTotalLivableArea;
import edu.upenn.cit594.processor.CalculateMethod;
import edu.upenn.cit594.logging.Logger;

public class ViolationsUserInterface {

	protected Scanner user;
	protected Analyzer analysis;
	protected LinkedList<Population> totalPopulation;
	public LinkedList<Property> allProperties;
	public LinkedList<ParkingViolation> allViolations;
	protected Logger log;

	//public ViolationsUserInterface(Analyzer analyzer, ArrayList<Population> allPop, ArrayList<Property> allProps, ArrayList<ParkingViolation> allViols, Logger logger) {
	public ViolationsUserInterface(Analyzer analyzer, LinkedList<Population> allPop, LinkedList<Property> allProps, LinkedList<ParkingViolation> allViols, Logger logger) {
		user = new Scanner(System.in);
		analysis = analyzer;
		totalPopulation = allPop;
		allProperties = allProps;
		allViolations = allViols;
		log = logger;
	}

	public int getUserAction(boolean first) {
		boolean invalidNumFlag = false;
		int userAction;
		if (first) {
			System.out.print("Action options:\n 0 to exit,\n" +
					" 1 to show total ZIP Code population,\n" + 
					" 2 to show each ZIP Code's total parking fines per capita,\n" +
					" 3 to show specified ZIP Code's average residence market value,\n" +
					" 4 to show specified ZIP Code's average total liveable area for residences,\n" +
					" 5 to show specified ZIP Code's total residential market value per capita,\n" +
					" 6 to show the zip code with the highest total market value.\n" + 
					"Enter desired action:\n");
		}else {
			System.out.print("Please select a new action!: ");
		}
		do {
			if(invalidNumFlag) {
				System.out.println("Please select a valid option number!: ");
			}
			while(!user.hasNextInt()) {
				System.out.println("Not a number! Please select valid action: ");
				invalidNumFlag = false;
				user.next();
			}
			invalidNumFlag = true;
			userAction = user.nextInt();
		} while (userAction < 0 | userAction > 6);

		return(userAction);
	}

	public int getZipCode() {
		String userZip = "";
		int zipCode = 0;

		boolean validInput = false;

		while(!validInput) {
			System.out.println("Please Enter a Valid ZIP: ");
			userZip = user.nextLine();
			try {
				zipCode = Integer.parseInt(userZip);
				for(Population p : totalPopulation) {
					if(p.getZip() == zipCode) {
						validInput = true;
					}
				}
			} catch (Exception e) {
				System.out.println("there is something wrong with zipcode input!");
			}
		}
		return zipCode;
	}




	public void callUserFunction(int userAction) {
		CalculateMethod calcMethod;
		Scanner in;
		String userZIP;
		int zip;
		switch(userAction) {
		case 0:
			System.exit(0);
			break;
		case 1:
			int totalPop = analysis.questionOne(totalPopulation);
			System.out.println(totalPop);
			break;
		case 2:
			analysis.questionTwo(allViolations, totalPopulation);
			break;
		case 3:
			calcMethod = new CalculateByResidentialMarketValue();
			zip = getZipCode();
			userZIP = Integer.toString(zip);
			log.logUserZip(userZIP);
			int avgMarketVal = analysis.questionThreeOrFour(zip, allProperties, calcMethod);
			System.out.println(avgMarketVal);
			break;
		case 4:
			calcMethod = new CalculateByResidentialTotalLivableArea();
			zip = getZipCode();
			userZIP = Integer.toString(zip);
			log.logUserZip(userZIP);
			int avgMarketVal2 = analysis.questionThreeOrFour(zip, allProperties, calcMethod);
			System.out.println(avgMarketVal2);
			break;
		case 5:
			zip = getZipCode();
			userZIP = Integer.toString(zip);
			log.logUserZip(userZIP);
			int mvPerCapita = analysis.questionFive(zip, allProperties, totalPopulation);
			System.out.println(mvPerCapita);
			break;
		case 6:
			int highestAvgMV = analysis.questionSix(allProperties);
			System.out.println(highestAvgMV);
			break;
		}
	}

}


