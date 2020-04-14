package edu.upenn.cit594;

import java.io.File;
import java.io.IOException;

import edu.upenn.cit594.data.AllParkingViolations;
import edu.upenn.cit594.data.AllProperties;
import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.processor.Analyzer;
import edu.upenn.cit594.ui.ViolationsUserInterface;

public class Main {
	
	public void run() {
		//Exit if argument number is incorrect
		String[] args = new String[] {"json", "parking.json", "properties.csv", "population.txt", "log.txt"};
		if (args.length != 5) {
			System.out.println("Incorrect Number of Arguments!");
			System.exit(0);
		} 
		
		String formatViolationsFile = args[0];
		File violationsFile = new File(args[1]);
		File propertyValuesFile = new File(args[2]);
		File populationFile = new File(args[3]);
		File logFile = new File(args[4]);
		
		//exit if files cannot be opened
		if (!(formatViolationsFile.equalsIgnoreCase("csv") | formatViolationsFile.equalsIgnoreCase("json"))) {
			System.out.println("Specified Format Not Acceptable!");
			System.exit(0);
		} else if(!(violationsFile.isFile() & violationsFile.canRead()) | !(propertyValuesFile.isFile() & propertyValuesFile.canRead()) | !(populationFile.isFile() & populationFile.canRead())) {
			System.out.println("Error in Files Provided!");
			System.exit(0);
		}
		
		//check log file + create if does not exist
		if (!(logFile.isFile() & logFile.canRead())) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Create data objects
		AllProperties allProps = new AllProperties();
		AllParkingViolations allParkViolations = new AllParkingViolations();
		Population pop = new Population();
		
		//Create file reader + read in data files
		Reader rdr = new Reader(allProps, allParkViolations, pop);
		rdr.readPopulation(populationFile);
		rdr.readProperties(propertyValuesFile);
		rdr.readParkingViolations(formatViolationsFile, violationsFile);
		
		//Run UI and specified analysis
		Analyzer analysis = new Analyzer();
		ViolationsUserInterface vui = new ViolationsUserInterface(analysis, allProps, allParkViolations, pop);
		
		int ret = vui.getUserAction();
		vui.callUserFunction(ret);

	}
	
	public static void main (String[] args) {
		Main m = new Main();
		m.run();
		
	}
	
}

