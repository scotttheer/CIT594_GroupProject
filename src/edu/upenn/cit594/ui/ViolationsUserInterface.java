package edu.upenn.cit594.ui;

import java.util.LinkedList;

import java.util.Scanner;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.processor.Analyzer;
import edu.upenn.cit594.processor.CalculateByResidentialMarketValue;
import edu.upenn.cit594.processor.CalculateByResidentialTotalLivableArea;
import edu.upenn.cit594.processor.CalculateMethod;

public class ViolationsUserInterface {
	
	protected Scanner user;
	protected Analyzer analysis;
	protected LinkedList<Property> allProps;
	protected LinkedList<ParkingViolation> allParkViolations;
	protected Population pop;
	
	public ViolationsUserInterface(Analyzer analyzer, LinkedList<Property> allProperties, LinkedList<ParkingViolation> allParking, Population population) {
		user = new Scanner(System.in);
		analysis = analyzer;
		allProps = allProperties;
		allParkViolations = allParking;
		pop = population;
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
		
		user.close();
		return(userAction);
	}
	
	public void callUserFunction(int userAction) {
		CalculateMethod calcMethod;
		switch(userAction) {
			case 0:
				System.exit(0);
				break;
			case 1:
				int totalPop = analysis.questionOne(pop);
				System.out.println(totalPop);
			    break;
			case 2:
				analysis.questionTwo(allParkViolations, pop);
			    break;
			case 3:
				calcMethod = new CalculateByResidentialMarketValue();
				int avgMarketVal = analysis.questionThreeOrFour(allProps, calcMethod);
				System.out.println(avgMarketVal);
				break;
			case 4:
				calcMethod = new CalculateByResidentialTotalLivableArea();
				int totalLiveableArea = analysis.questionThreeOrFour(allProps, calcMethod);
				System.out.println(totalLiveableArea);
				break;
			case 5:
				int mvPerCapita = analysis.questionFive(allProps, pop);
				System.out.println(mvPerCapita);
				break;
			case 6:
				int highestAvgMV = analysis.questionSix(allProps);
				System.out.println(highestAvgMV);
				break;
		}
	}

}

 
