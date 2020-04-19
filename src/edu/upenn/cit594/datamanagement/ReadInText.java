package edu.upenn.cit594.datamanagement;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

import edu.upenn.cit594.data.ParkingViolation;

public class ReadInText implements ReadMethod{

	@Override
	public LinkedList<ParkingViolation> read(File f) {
		LinkedList<ParkingViolation> allParkingViolations = new LinkedList<>();

		try {
			Scanner in = new Scanner(new FileReader(f));
			while(in.hasNextLine()) {
				String[] data = in.nextLine().split(",");
				if(data.length == 7) {
					String time = data[0].length() == 0 ? null : data[0];
					double fine = data[1].length() == 0 ? -1 : Double.parseDouble(data[1]);
					String description = data[2].length() == 0 ? null : data[2];
					String identifierForVehicle = data[3].length() == 0 ? null : data[3];
					String state = data[4].length() == 0 ? null : data[4];
					String identiferForThisViolation = data[5].length() == 0 ? null : data[5];
					int ZIPCode = data[6].length() == 0 ? -1 : Integer.parseInt(data[6]);
					
					//ignore it when the data is not properly formatted
					if(time != null && fine != -1 && description != null && identifierForVehicle != null
							&& state != null && identiferForThisViolation != null && ZIPCode != -1) {
						ParkingViolation p = new ParkingViolation(time, fine, description, identifierForVehicle, state, identiferForThisViolation, ZIPCode);
						allParkingViolations.add(p);
					}
				}
			}
			return allParkingViolations;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return allParkingViolations;
	}
}