package it.unipr.java.model;

import java.sql.Date;

/**
 * The class {@code Race} provides an implementation of a model of a race.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class Race {

	private int id;
	private String name;
	private String place;
	private Date dateRace;
	private int boatsNumber;
	private float registrationFee;
	
	/**
	 * Class constructor.
	**/
	public Race() {
		this.setId(0);
		this.setName("");
		this.setPlace("");
		this.setDateRace(null);
		this.setBoatsNumber(0);
		this.setRegistrationFee(0);
	}	
	
	/**
	 * Class constructor.
	 * 
	 * @param id the unique identifier of the race.
	 * @param name the name of the race.
	 * @param place the place of the race.
	 * @param dateRace the data of the race.
	 * @param boatsNumber the number of boats participating in the race.
	 * @param registrationFree the race registration fee

	**/
	public Race(final int id, final String name, final String place, final Date dateRace, final int boatsNumber, final float registrationFee) {
		this.setId(id);
		this.setName(name);
		this.setPlace(place);
		this.setDateRace(dateRace);
		this.setBoatsNumber(boatsNumber);
		this.setRegistrationFee(registrationFee);
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
	 * @param place the new race.
	**/
	public void setPlace(final String place) {
		this.place = place;
	}
	
	/**
	 * Gets the date of the race.
	 * 
	 * @return the date.
	**/
	public Date getDateRace() {
		return this.dateRace;
	}
	
	/**
	 * Sets the date of the race.
	 * 
	 * @param dateRace date of the new race.
	**/
	public void setDateRace(final Date dateRace) {
		this.dateRace = dateRace;
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
	 * @param boatsNumber the number of boats.
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
	 * @param registrationFee the registration fee.
	**/
	public void setRegistrationFee(final float registrationFee) {
		this.registrationFee = registrationFee;
	}
	
	
	/**
	 * Gets a string that describes a boat.
	 * 
	 * @return the string.
	**/
	@Override
	public String toString() {
		return "Id: " + this.id + " - Name: " + this.name + " - Place: " + this.place + " - Date: " + this.dateRace + " - Boats Number: " + this.boatsNumber + " - Registration Fee: " + this.registrationFee;
	}
}
