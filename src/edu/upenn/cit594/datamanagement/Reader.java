package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.data.Property;

public class Reader {
	
	public Reader() {}

	public LinkedList<Population> readPopulation(File f) {

		LinkedList<Population> totalPopulation = new LinkedList<Population>();
		
		try {
			Scanner in = new Scanner(new FileReader(f));
			int ZIPCode;
			int num;
			while(in.hasNextLine()) {
				String[] data = in.nextLine().split("\\s");
				
				try {
					ZIPCode = Integer.parseInt(data[0]);
					num = Integer.parseInt(data[1]);
				}catch(Exception e) {
					continue;
				}
				
				totalPopulation.add(new Population(ZIPCode, num));
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found exception");
		}
		
		return totalPopulation;
	}
	
	public LinkedList<Property> readProperties(File f) {
		LinkedList<Property> allProperties = new LinkedList<Property>();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(f));
			int indexForMarketValue = 0;
			int indexForLivableArea = 0;
			int indexForZIPCode = 0;
			
			//read in data header
			String[] firstRow = in.readLine().split(",");

			//determine + store columns for relevant data fields
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
			
			double marketValue;
			double totalLivableArea;
			int ZIPCode;
			
			String line = in.readLine();
			while(line != null) {

				//split line of csv, accounting for commas included in quotes within data fields
				String[] data = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
				
				//try to parse strings read from input files to Doubles or Ints
				try {
					marketValue = Double.parseDouble(data[indexForMarketValue]);
					totalLivableArea = Double.parseDouble(data[indexForLivableArea]);
					ZIPCode = Integer.parseInt(data[indexForZIPCode].substring(0, 5));
				//if entry read is invalid, catch error and continue (ignore row of data)
				}catch(Exception e) {
					line = in.readLine();
					continue;
				}
				
				//create new instance of Property and add to list
				Property p = new Property(marketValue, totalLivableArea, ZIPCode);
				allProperties.add(p);
				
				line = in.readLine();
			}
			in.close();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		return allProperties;
	}

	public LinkedList<ParkingViolation> readParkingViolations(String format, File f) {
		LinkedList<ParkingViolation> allViolations = new LinkedList<ParkingViolation>();
		
		//determine format of input file to call corresponding read method
		if(format.equals("csv")) {
			ReadMethod readInText = new ReadInText();
			allViolations = readInText.read(f);
		}
		if(format.equals("json")) {
			ReadMethod readInJson = new ReadInJson();
			allViolations = readInJson.read(f);
		}
		return allViolations;
	}
	
	public static void main(String[] args) {
		Reader r = new Reader();
		File f = new File("properties.csv");
		LinkedList<Property> props;
		props = r.readProperties(f);
		for(Property v: props) {
			System.out.println(v.getZIPCode() + ": " + v.getMarketValue());
		}
	}

}
