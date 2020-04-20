package edu.upenn.cit594.data;

public class ParkingViolation {
	private String time;
	private double fine;
	private String description;
	private int identifierForVehicle;
	private String state;
	private int identiferForThisViolation;
	private int ZIPCode;

	public ParkingViolation ( 
			String time,
			double fine,
			String description,
			int identifierForVehicle,
			String state,
			int identiferForThisViolation,
			int ZIPCode) {
		this.time = time;
		this.fine = fine;
		this.description = description;
		this.identifierForVehicle = identifierForVehicle;
		this.state = state;
		this.identiferForThisViolation = identiferForThisViolation;
		this.ZIPCode = ZIPCode;

	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getFine() {
		return fine;
	}

	public void setFine(double fine) {
		this.fine = fine;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIdentifierForVehicle() {
		return identifierForVehicle;
	}

	public void setIdentifierForVehicle(int identifierForVehicle) {
		this.identifierForVehicle = identifierForVehicle;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getIdentiferForThisViolation() {
		return identiferForThisViolation;
	}

	public void setIdentiferForThisViolation(int identiferForThisViolation) {
		this.identiferForThisViolation = identiferForThisViolation;
	}

	public int getZIPCode() {
		return ZIPCode;
	}

	public void setZIPCode(int zIPCode) {
		ZIPCode = zIPCode;
	}

	


}







