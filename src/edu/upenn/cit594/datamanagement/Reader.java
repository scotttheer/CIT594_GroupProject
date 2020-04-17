package edu.upenn.cit594.datamanagement;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.data.Property;

public class Reader {

	private LinkedList<Property> allProperties;
	private LinkedList<ParkingViolation> allParkingViolations;
	private Population population;

	public Reader() {

		allProperties = new LinkedList<Property>();
		allParkingViolations = new LinkedList<ParkingViolation>();
		population = new Population();

	}

	public void readPopulation(File f) {

		try {
			Scanner in = new Scanner(new FileReader(f));
			while(in.hasNextLine()) {
				String[] data = in.nextLine().split("\\s");

				//if the field is missing, use -1;
				int ZIPCode = data[0].length() == 0 ? -1 : Integer.parseInt(data[0]);

				//if the field is missing, use -1;
				int num = data[1].length() == 0 ? -1 :Integer.parseInt(data[1]);

				//ignore any row of data in the input file that is not properly formated
				if(ZIPCode != -1 && num != -1) {
					population.getPopulation().put(ZIPCode, num);

				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found exception");
		} catch (NumberFormatException e) {
			System.out.println("there is something wrong with population input file");
		}
	}

	public void readProperties(File f) {

		try {
			Scanner in = new Scanner(new FileReader(f));
			int indexForMarketValue = 0;
			int indexForLivableArea = 0;
			int indexForZIPCode = 0;


			//https://stackoverflow.com/questions/18893390/splitting-on-comma-outside-quotes
			String[] firstRow = in.nextLine().split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");

			for(int i = 0; i <= firstRow.length - 1; i++) {
				if(firstRow[i].equals("market_value")) {
					indexForMarketValue = i;
				}
				if(firstRow[i].equals("total_livable_area")) {
					indexForLivableArea = i;
				}
				if(firstRow[i].equals("zip_code")) {
					indexForZIPCode = i;
				}
			}

			while(in.hasNextLine()) {

				//need to figure out the comma issue within quotations!!!
				String[] data = in.nextLine().split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");

				//if the field is missing, use -1;

				System.out.println(data[indexForMarketValue]);

				double marketValue = data[indexForMarketValue].length() == 0 ? -1.0 : Double.parseDouble(data[indexForMarketValue]);
				//if the field is missing, use -1;
				double totalLivableArea = data[indexForLivableArea].length() == 0 ? -1 : Double.parseDouble(data[indexForLivableArea]);

				//use only the first five digit of ZIP code
				int ZIPCode = data[indexForZIPCode].length() < 5 ? -1 : Integer.parseInt(data[indexForZIPCode].substring(0, 5));


				//ignore any row of date in the input file that is not properly formatted
				if(marketValue != -1 && totalLivableArea != -1 && ZIPCode != -1) {

					Property p = new Property(marketValue, totalLivableArea, ZIPCode);

					allProperties.add(p);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found");
		} catch(NumberFormatException e) {
			System.out.println("Something wrong with the format of the input file");
		}
	}


	public void readParkingViolations(String format, File f) {
		if(format.equals("csv")) {
			ReadMethod readInText = new ReadInText();
			allParkingViolations = readInText.read(f);
		}
		if(format.equals("json")) {
			ReadMethod readInJson = new ReadInJson();
			allParkingViolations = readInJson.read(f);
		}
	}

	public LinkedList<Property> getAllProperties() {
		return allProperties;
	}

	public void setAllProperties(LinkedList<Property> allProperties) {
		this.allProperties = allProperties;
	}

	public LinkedList<ParkingViolation> getAllParkingViolations() {
		return allParkingViolations;
	}

	public void setAllParkingViolations(LinkedList<ParkingViolation> allParkingViolations) {
		this.allParkingViolations = allParkingViolations;
	}

	public Population getPopulation() {
		return population;
	}

	public void setPopulation(Population population) {
		this.population = population;
	}




}