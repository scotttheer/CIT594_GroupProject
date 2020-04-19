package edu.upenn.cit594;

import java.io.File;

import java.io.IOException;

import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Analyzer;
import edu.upenn.cit594.ui.ViolationsUserInterface;

public class Main {

	public void run() {
		//Exit if argument number is incorrect
		String[] args = new String[] {"json", "parking.json", "properties.csv", "population.txt", "log.txt"};
		boolean first = true;

		if (args.length != 5) {
			System.out.println("Incorrect Number of Arguments!");
			System.exit(0);
		} 

		String formatViolationsFile = args[0];
		File violationsFile = new File(args[1]);
		File propertyValuesFile = new File(args[2]);
		File populationFile = new File(args[3]);
		File logFile = new File(args[4]);

		//Exit if files cannot be opened
		if (!(formatViolationsFile.equalsIgnoreCase("csv") | formatViolationsFile.equalsIgnoreCase("json"))) {
			System.out.println("Specified Format Not Acceptable!");
			System.exit(0);
		} else if(!(violationsFile.isFile() & violationsFile.canRead()) | !(propertyValuesFile.isFile() & propertyValuesFile.canRead()) | !(populationFile.isFile() & populationFile.canRead())) {
			System.out.println("Error in Files Provided!");
			System.exit(0);
		}

		//Check log file + create if does not exist
		if (!(logFile.isFile() & logFile.canRead())) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		//Create logger instance + initialize with log file
		Logger log = Logger.getInstance();
		log.init(logFile, args);

		//Create data objects

		//Create file reader + read in data files (while logging opened files)
		Reader rdr = new Reader();
		log.logOpenedFiles(populationFile);
		rdr.readPopulation(populationFile);
		
		System.out.println("population file was read");
		
		log.logOpenedFiles(violationsFile);
		rdr.readParkingViolations(formatViolationsFile, violationsFile);
		
		System.out.println("violation file was read");
		System.out.println("Parking violations: " + rdr.getAllParkingViolations().size());
		
		log.logOpenedFiles(propertyValuesFile);
		rdr.readProperties(propertyValuesFile);
		
		System.out.println("properties file was read");
		
		//Run UI and specified analysis
		Analyzer analysis = new Analyzer(log);
		ViolationsUserInterface vui = new ViolationsUserInterface(analysis, rdr.getAllProperties(), rdr.getAllParkingViolations(), rdr.getPopulation());

		System.out.println("Properties: " + rdr.getAllProperties().size());
		
		System.out.println(rdr.getPopulation().getPopulation().keySet().size());
		
		
		//Run user selection prompt, log choice, and call corresponding analyzer method
		do {
			int ret = vui.getUserAction(first);
			log.logUserSelection(ret);
			vui.callUserFunction(ret);
			first = false;
		}while(true);

	}

	public static void main (String[] args) {
		Main m = new Main();
		m.run();

	}
}

