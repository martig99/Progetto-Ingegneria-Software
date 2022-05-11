package main.java.it.unipr.model;

import java.io.Serializable;

/**
 * The class {@code User} provides an implementation of a model of an user.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The unique identifier of the user.
	**/
	private int id;
	
	/**
	 * The first name of the user. 
	**/
	private String firstName;
	
	/**
	 * The last name of the user. 
	**/
	private String lastName;
	
	/**
	 * The email address of the user. 
	**/
	private String email;
	
	/**
	 * The password of the user. 
	**/
	private String password;
	
	/**
	 * The status code of the user.
	**/
	private StatusCode statusCode;
	
	/**
	 * Class constructor.
	**/
	public User() {
		this.setId(0);
		this.setFirstName("");
		this.setLastName("");
		this.setEmail("");
		this.setPassword("");
		this.setStatusCode(null);
	}
	
	/**
	 * Class constructor.
	 * 
	 * @param id the unique identifier of the user.
	 * @param firstName the first name of the user.
	 * @param lastName the last name of the user.
	 * @param email the email address of the user.
	 * @param password the password of the user.
	 * @param statusCode the status code of the user.
	**/
	public User(final int id, final String firstName, final String lastName, final String email, final String password, final StatusCode statusCode) {
		this.setId(id);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
		this.setPassword(password);
		this.setStatusCode(statusCode);
	}
	
	/**
	 * Class constructor.
	 * 
	 * @param email the email address of the user.
	 * @param password the password of the user.
	**/
	public User(final String email, final String password) {
		this.setEmail(email);
		this.setPassword(password);
	}
	
	/**
	 * Gets the unique identifier of the user.
	 * 
	 * @return the unique identifier.
	**/
	public int getId() {
		return this.id;
	}

	/**
	 * Sets the unique identifier of the user.
	 *
	 * @param id the new unique identifier.
	**/
	public void setId(final int id) {
		this.id = id;
	}
	
	/**
	 * Gets the first name of the user.
	 *
	 * @return the first name.
	**/
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Sets the first name of the user.
	 * 
	 * @param firstName the new first name.
	**/
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}
	 
	/**
	 * Gets the last name of the user.
	 *
	 * @return the last name.
	**/
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Sets the last name of the user.
	 * 
	 * @param lastName the new last name.
	**/
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}
	 
	/**
	 * Gets the email address of the user.
	 * 
	 * @return the email address.
	**/
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Sets the email address of the user.
	 * 
	 * @param email the new email address.
	**/
	public void setEmail(final String email) {
		this.email = email;
	}
	
	/**
	 * Gets the password of the user.
	 * 
	 * @return the password.
	**/
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Sets the password of the user.
	 * 
	 * @param password the new password.
	**/
	public void setPassword(final String password) {
		this.password = password;
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
	 * Gets a string that describes an user.
	 * 
	 * @return the string.
	**/
	@Override
	public String toString() {
		return "Id: " + this.id + " - First Name: " + this.firstName + " - Last Name: " + this.lastName + " - Email: " + this.email + " - Password: " + this.password;
	}
}
