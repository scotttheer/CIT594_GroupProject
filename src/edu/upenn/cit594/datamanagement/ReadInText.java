package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileReader;
//import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import edu.upenn.cit594.data.ParkingViolation;

public class ReadInText implements ReadMethod{

	@Override
	public LinkedList<ParkingViolation> read(File f) {
		LinkedList<ParkingViolation> allParkingViolations = new LinkedList<ParkingViolation>();
		
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
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return allParkingViolations;
	}
	
	public static void main(String[] args) {
		ReadInText rdr = new ReadInText();
		File violationsFile = new File("parking.csv");
		LinkedList<ParkingViolation> violations = new LinkedList<ParkingViolation>();
		violations = rdr.read(violationsFile);
		for(ParkingViolation v: violations) {
			System.out.println(v.getZIPCode()+": "+ v.getFine());
		}
	}
}