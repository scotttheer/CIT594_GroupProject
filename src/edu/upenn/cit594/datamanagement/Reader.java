package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.util.Scanner;

import edu.upenn.cit594.data.AllParkingViolations;
import edu.upenn.cit594.data.AllProperties;
import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.data.Property;

public class Reader {

	private AllProperties allProperties;
	private AllParkingViolations allParkingViolations;
	private Population population;

	public Reader(AllProperties allProp, AllParkingViolations allParkViol, Population pop) {
		/*allProperties = new AllProperties();
		allParkingViolations = new AllParkingViolations();
		population = new Population();*/
		allProperties = allProp;
		allParkingViolations = allParkViol;
		population = pop;
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


			//need to figure out the comma issue within quotations
			String[] firstRow = in.nextLine().split(",");

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
				String[] data = in.nextLine().split(",");

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

					allProperties.getAllProperties().add(p);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(NumberFormatException e) {
			e.printStackTrace();
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


	public AllProperties getAllProperties() {
		return allProperties;
	}


	public void setAllProperties(AllProperties allProperties) {
		this.allProperties = allProperties;
	}


	public AllParkingViolations getAllParkingViolations() {
		return allParkingViolations;
	}


	public void setAllParkingViolations(AllParkingViolations allParkingViolations) {
		this.allParkingViolations = allParkingViolations;
	}


	public Population getPopulation() {
		return population;
	}


	public void setPopulation(Population population) {
		this.population = population;
	}



}