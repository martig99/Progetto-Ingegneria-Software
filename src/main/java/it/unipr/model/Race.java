package main.java.it.unipr.model;

import java.io.Serializable;
import java.util.Date;

/**
 * The class {@code Race} provides an implementation of a model of a race.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class Race implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * The unique identifier of the race.
	**/
	private int id;
	
	/**
	 * The name of the race.
	**/
	private String name;
	
	/**
	 * The place of the race.
	**/
	private String place;
	
	/**
	 * The date of the race.
	**/
	private Date date;
	
	/**
	 * The number of boats participating in the race.
	**/
	private int boatsNumber;
	
	/**
	 * The registration fee for the race.
	**/
	private float registrationFee;
	
	/**
	 * The end date of registration of a boat at the race.
	**/
	private Date endDateRegistration;
	
	/**
	 * The status code of the race.
	**/
	private StatusCode statusCode;
		
	/**
	 * Class constructor.
	**/
	public Race() {
		this.setId(0);
		this.setName("");
		this.setPlace("");
		this.setDate(null);
		this.setBoatsNumber(0);
		this.setRegistrationFee(0);
		this.setEndDateRegistration(null);
		this.setStatusCode(null);
	}	
	
	/**
	 * Class constructor.
	 * 
	 * @param id the unique identifier of the race.
	 * @param name the name of the race.
	 * @param place the place of the race.
	 * @param date the date of the race.
	 * @param boatsNumber the number of boats participating in the race.
	 * @param registrationFee the registration fee for the race.
	 * @param endDateRegistration the end date of registration of a boat at the race.
	 * @param statusCode the status code of the race.
	**/
	public Race(final int id, final String name, final String place, final Date date, final int boatsNumber, final float registrationFee, final Date endDateRegistration, final StatusCode statusCode) {
		this.setId(id);
		this.setName(name);
		this.setPlace(place);
		this.setDate(date);
		this.setBoatsNumber(boatsNumber);
		this.setRegistrationFee(registrationFee);
		this.setEndDateRegistration(endDateRegistration);
		this.setStatusCode(statusCode);
	}	
	
	/**
	 * Gets the unique identifier of the race.
	 * 
	 * @return the unique identifier.
	**/
	public int getId() {
		return this.id;
	}
	
	/**
	 * Sets the unique identifier of the race.
	 *  
	 * @param id the new unique identifier.
	**/
	public void setId(final int id) {
		this.id = id;
	}
	
	/**
	 * Gets the name of the race.
	 * 
	 * @return the name.
	**/
	public String getName() {
		return this.name;
	}
	
	/**
	 * Sets the name of the race.
	 *  
	 * @param name the new name.
	**/
	public void setName(final String name) {
		this.name = name;
	}
	
	/**
	 * Gets the place of the race.
	 * 
	 * @return the place.
	**/
	public String getPlace() {
		return this.place; 
	}
	
	/**
	 * Sets the place of the race.
	 *  
	 * @param place the new place.
	**/
	public void setPlace(final String place) {
		this.place = place;
	}
	
	/**
	 * Gets the date of the race.
	 * 
	 * @return the date.
	**/
	public Date getDate() {
		return this.date;
	}
	
	/**
	 * Sets the date of the race.
	 * 
	 * @param date the new date.
	**/
	public void setDate(final Date date) {
		this.date = date;
	}
	
	/**
	 * Gets the number of boats participating in the race.
	 * 
	 * @return the number of boats.
	**/
	public int getBoatsNumber() {
		return this.boatsNumber;
	}
	
	/**
	 * Sets the number of boats participating in the race.
	 * 
	 * @param boatsNumber the new number of boats.
	**/
	public void setBoatsNumber(final int boatsNumber) {
		this.boatsNumber = boatsNumber;
	}
	
	/**
	 * Gets the registration fee for the race.
	 * 
	 * @return the registration fee.
	**/
	public float getRegistrationFee() {
		return this.registrationFee;
	}
	
	/**
	 * Sets the registration fee for the race.
	 * 
	 * @param registrationFee the new registration fee.
	**/
	public void setRegistrationFee(final float registrationFee) {
		this.registrationFee = registrationFee;
	}
	
	/**
	 * Gets the end date of registration of a boat at the race.
	 * 
	 * @return the end date of registration.
	**/
	public Date getEndDateRegistration() {
		return this.endDateRegistration;
	}
	
	/**
	 * Sets the end date of registration of a boat at the race.
	 * 
	 * @param endDateRegistration the new end date of registration.
	**/
	public void setEndDateRegistration(final Date endDateRegistration) {
		this.endDateRegistration = endDateRegistration;
	}
	
	/**
	 * Gets the status code of the race.
	 * 
	 * @return the status code.
	**/
	public StatusCode getStatusCode() {
		return this.statusCode;
	}
	
	/**
	 * Sets the status code of the race.
	 * 
	 * @param statusCode the new status code.
	**/
	public void setStatusCode(final StatusCode statusCode) {
		this.statusCode = statusCode;
	}
	
	/**
	 * Gets a string that describes a race.
	 * 
	 * @return the string.
	**/
	@Override
	public String toString() {
		return "Id: " + this.id + " - Name: " + this.name + " - Place: " + this.place + " - Date: " + this.date + " - Boats Number: " + this.boatsNumber + " - Registration Fee: " + this.registrationFee + " - End Date Registration: " + this.endDateRegistration + " - Status Code: " + this.statusCode;
	}
}
