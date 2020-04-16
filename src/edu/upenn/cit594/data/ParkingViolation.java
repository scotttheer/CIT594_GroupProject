package edu.upenn.cit594.data;

public class ParkingViolation {
	private String time;
	private double fine;
	private String description;
	private String identifierForVehicle;
	private String state;
	private String identiferForThisViolation;
	private int ZIPCode;

	public ParkingViolation ( 
			String time,
			double fine,
			String description,
			String identifierForVehicle,
			String state,
			String identiferForThisViolation,
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

	public String getIdentifierForVehicle() {
		return identifierForVehicle;
	}

	public void setIdentifierForVehicle(String identifierForVehicle) {
		this.identifierForVehicle = identifierForVehicle;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIdentiferForThisViolation() {
		return identiferForThisViolation;
	}

	public void setIdentiferForThisViolation(String identiferForThisViolation) {
		this.identiferForThisViolation = identiferForThisViolation;
	}

	public int getZIPCode() {
		return ZIPCode;
	}

	public void setZIPCode(int zIPCode) {
		ZIPCode = zIPCode;
	}
}







