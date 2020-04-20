package edu.upenn.cit594.data;

import java.util.ArrayList;

public class AllParkingViolations {
	private ArrayList<ParkingViolation> allParkingViolations;

	public AllParkingViolations() {
		allParkingViolations = new ArrayList<ParkingViolation>();
	}

	public ArrayList<ParkingViolation> getAllParkingViolations() {
		return allParkingViolations;
	}

	public void setAllParkingViolations(ArrayList<ParkingViolation> allParkingViolations) {
		this.allParkingViolations = allParkingViolations;
	}


}
