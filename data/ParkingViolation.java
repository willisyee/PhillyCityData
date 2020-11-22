package edu.upenn.cit594.data;


public class ParkingViolation {
	protected String ticketNumber;
	protected String plateID;
	protected String date;
	protected String zipCode;
	protected String violation;
	protected int fine;
	protected String state;
	
	/**
	 * Constructor
	 * @param ticketNumber
	 * @param plateID
	 * @param date
	 * @param zipCode
	 * @param violation
	 * @param fine
	 * @param state
	 */
	public ParkingViolation(String ticketNumber, String plateID, String date, String zipCode, String violation, int fine, String state){
		this.ticketNumber = ticketNumber;
		this.plateID = plateID;
		this.date = date;
		this.zipCode = zipCode;
		this.violation = violation;
		this.fine = fine;
		this.state = state;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public String getPlateID() {
		return plateID;
	}

	public String getDate() {
		return date;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getViolation() {
		return violation;
	}

	public long getFine() {
		return fine;
	}

	public String getState() {
		return state;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public void setPlateID(String plateID) {
		this.plateID = plateID;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public void setViolation(String violation) {
		this.violation = violation;
	}

	public void setFine(int fine) {
		this.fine = fine;
	}

	public void setState(String state) {
		this.state = state;
	}
}
