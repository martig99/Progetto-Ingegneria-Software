package main.java.it.unipr.model;

import java.io.Serializable;
import java.util.Date;

/**
 * The class {@code RaceRegistration} provides an implementation of a model of a registration to a race.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class RaceRegistration implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * The unique identifier of the registration for the race. 
	**/
	private int id;
	
	/**
	 * The date of the registration for the race. 
	**/
	private Date date;
	
	/**
	 * The race to which the registration refers. 
	**/
	private Race race;
	
	/**
	 * The boat registered for the race. 
	**/
	private Boat boat;
	
	/**
	 * The status code of the registration for the race. 
	**/
	private StatusCode statusCode;
	
	/**
	 * Class constructor.
	**/
	public RaceRegistration() {
		this.setId(0);
		this.setDate(null);
		this.setRace(null);
		this.setBoat(null);
		this.setStatusCode(null);
	}	
	
	/**
	 * Class constructor.
	 * 
	 * @param id the unique identifier of the registration for the race.
	 * @param date the date of the registration for the race.
	 * @param race the race to which the registration refers.
	 * @param boat the boat registered for the race.
	 * @param statusCode the status code of the registration for the race.
	**/
	public RaceRegistration(final int id, final Date date, final Race race, final Boat boat, final StatusCode statusCode) {
		this.setId(id);
		this.setDate(date);
		this.setRace(race);
		this.setBoat(boat);
		this.setStatusCode(statusCode);
	}	
	
	/**
	 * Gets the unique identifier of the registration for the race.
	 * 
	 * @return the unique identifier.
	**/
	public int getId() {
		return this.id;
	}
	
	/**
	 * Sets the unique identifier of the registration for the race.
	 *  
	 * @param id the new unique identifier.
	**/
	public void setId(final int id) {
		this.id = id;
	}
	
	/**
	 * Gets the date of the registration for the race.
	 * 
	 * @return the date
	**/
	public Date getDate() {
		return this.date;
	}
	
	/**
	 * Sets the date of the registration for the race.
	 * 
	 * @param date the new date.
	**/
	public void setDate(final Date date) {
		this.date = date;
	}
	
	/**
	 * Gets the race to which the registration refers.
	 * 
	 * @return the race.
	**/
	public Race getRace() {
		return this.race;
	}
	
	/**
	 * Sets the race to which the registration refers.
	 * 
	 * @param race the new race.
	**/
	public void setRace(final Race race) {
		this.race = race;
	}
	
	/**
	 * Gets the boat registered for the race.
	 * 
	 * @return the boat.
	**/
	public Boat getBoat() {
		return this.boat;
	}
	
	/**
	 * Sets the boat registered for the race.
	 * 
	 * @param boat the new boat.
	**/
	public void setBoat(final Boat boat) {
		this.boat = boat;
	}
	
	/**
	 * Gets the status code of the registration for the race.
	 * 
	 * @return the status code.
	**/
	public StatusCode getStatusCode() {
		return this.statusCode;
	}
	
	/**
	 * Sets the status code of the registration for the race.
	 * 
	 * @param statusCode the new status code.
	**/
	public void setStatusCode(final StatusCode statusCode) {
		this.statusCode = statusCode;
	}
	
	/**
	 * Gets a string that describes a registration for the race.
	 * 
	 * @return the string.
	**/
	@Override
	public String toString() {
		return "Id: " + this.id + " - Date: " + this.date + " - Race: [" + this.race.getDate() + ", " + this.race.getName() + "] - Boat: " + this.boat.getName() + " - Status Code: " + this.statusCode.toString();
	}
}
