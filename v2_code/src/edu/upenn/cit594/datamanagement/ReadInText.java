package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import edu.upenn.cit594.data.AllParkingViolations;
import edu.upenn.cit594.data.ParkingViolation;

public class ReadInText implements ReadMethod{

	@Override
	public ArrayList<ParkingViolation> read(File f) {
		ArrayList<ParkingViolation> allParkingViolations = new ArrayList<ParkingViolation>();

		try {
			Scanner in = new Scanner(new FileReader(f));
			while(in.hasNextLine()) {
				String[] data = in.nextLine().split(",");
				if(data.length == 7) {
					String time = data[0];
					double fine = Double.parseDouble(data[1]);
					String description = data[2];
					int identifierForVehicle = Integer.parseInt(data[3]);
					String state = data[4];
					int identiferForThisViolation = Integer.parseInt(data[5]);
					int ZIPCode = Integer.parseInt(data[6]);
					
					ParkingViolation p = new ParkingViolation(time, fine, description, identifierForVehicle, state, identiferForThisViolation, ZIPCode);
					allParkingViolations.add(p);
					//ignore it when the data is not properly formatted
					/*if(!time.equals(null) && fine != -1 && !description.equals(null) && !(identifierForVehicle == null)
							&& !state.equals(null) && !identiferForThisViolation.equals(null) && ZIPCode != -1) {
						ParkingViolation p = new ParkingViolation(time, fine, description, identifierForVehicle, state, identiferForThisViolation, ZIPCode);
						allParkingViolations.add(p);
					}*/
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return allParkingViolations;
	}
	
	public static void main(String[] args) {
		ReadInText rdr = new ReadInText();
		File violationsFile = new File("parking.csv");
		ArrayList<ParkingViolation> violations = new ArrayList<ParkingViolation>();
		violations = rdr.read(violationsFile);
		for(ParkingViolation v: violations) {
			System.out.println(v.getZIPCode()+": "+ v.getFine());
		}
	}
}