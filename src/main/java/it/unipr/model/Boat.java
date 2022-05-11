package main.java.it.unipr.model;

import java.io.Serializable;

/**
 * The class {@code Boat} provides an implementation of a model of a boat.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class Boat implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * The unique identifier of the boat. 
	**/
	private int id;
	
	/**
	 * The name of the boat.
	**/
	private String name;
	
	/**
	 * The length of the boat.
	**/
	private int length;
	
	/**
	 * The owner of the boat.
	**/
	private Member owner;
	
	/**
	 * The status code of the boat.
	**/
	private StatusCode statusCode;

	
	/**
	 * Class constructor.
	**/
	public Boat() {
		this.setId(0);
		this.setName("");
		this.setLength(0);
		this.setOwner(null);
		this.setStatusCode(null);
	}	
	
	/**
	 * Class constructor.
	 * 
	 * @param id the unique identifier of the boat.
	 * @param name the name of the boat.
	 * @param length the length of the boat.
	 * @param owner the owner of the boat.
	 * @param statusCode the status code of the boat.
	**/
	public Boat(final int id, final String name, final int length, final Member owner, final StatusCode statusCode) {
		this.setId(id);
		this.setName(name);
		this.setLength(length);
		this.setOwner(owner);
		this.setStatusCode(statusCode);
	}
	
	/**
	 * Gets the unique identifier of the boat.
	 * 
	 * @return the unique identifier.
	**/
	public int getId() {
		return this.id;
	}
	
	/**
	 * Sets the unique identifier of the boat.
	 *  
	 * @param id the new unique identifier.
	**/
	public void setId(final int id) {
		this.id = id;
	}
	
	/**
	 * Gets the name of the boat.
	 * 
	 * @return the name.
	**/
	public String getName() {
		return this.name;
	}
	
	/**
	 * Sets the name of the boat.
	 *  
	 * @param name the new name.
	**/
	public void setName(final String name) {
		this.name = name;
	}
	
	/**
	 * Gets the length of the boat.
	 * 
	 * @return the length.
	**/
	public int getLength() {
		return this.length; 
	}
	
	/**
	 * Sets the length of the boat.
	 *  
	 * @param length the new length.
	**/
	public void setLength(final int length) {
		this.length = length;
	}

	/**
	 * Gets the owner of the boat.
	 * 
	 * @return the owner.
	**/
	public Member getOwner() {
		return this.owner;
	}
	
	/**
	 * Sets the owner of the boat.
	 * 
	 * @param owner the new owner.
	**/
	public void setOwner(final Member owner) {
		this.owner = owner;
	}
	
	/**
	 * Gets the status code of the boat.
	 * 
	 * @return the status code.
	**/
	public StatusCode getStatusCode() {
		return this.statusCode;
	}
	
	/**
	 * Sets the status code of the boat.
	 * 
	 * @param statusCode the new status code.
	**/
	public void setStatusCode(final StatusCode statusCode) {
		this.statusCode = statusCode;
	}
	
	/**
	 * Gets a string that describes a boat.
	 * 
	 * @return the string.
	**/
	@Override
	public String toString() {
		return "Id: " + this.id + " - Name: " + this.name + " - Length: " + this.length + " - Owner: " + this.owner.getEmail() + " - Status Code: " + this.statusCode.toString();
	}
}
