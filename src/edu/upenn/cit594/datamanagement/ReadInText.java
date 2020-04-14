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
	public AllParkingViolations read(File f) {
		ArrayList<ParkingViolation> allParkingViolations = new ArrayList<>();

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
					if(!time.equals(null) && fine != -1 && !description.equals(null) && !identifierForVehicle.equals(null)
							&& !state.equals(null) && !identiferForThisViolation.equals(null) && ZIPCode != -1) {
						ParkingViolation p = new ParkingViolation(time, fine, description, identifierForVehicle, state, identiferForThisViolation, ZIPCode);
						allParkingViolations.add(p);
					}
				}
			}
			AllParkingViolations allParkingViolationsInfo = new AllParkingViolations();
			allParkingViolationsInfo.setAllParkingViolations(allParkingViolations);
			return allParkingViolationsInfo;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}




	/*public static void main(String[] args) {
		ReadInText a = new ReadInText();
		AllParkingViolations b = a.read("parking.csv");
		int c = b.getAllParkingViolations().size();
		System.out.println(c);
	}*/

}