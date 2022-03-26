package main.java.it.unipr.model;

import java.io.Serializable;

/**
 * The class {@code User} provides an implementation of a model of an user.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
	/**
	 * Class constructor.
	**/
	public User() {
		this.setId(0);
		this.setFirstName("");
		this.setLastName("");
		this.setEmail("");
		this.setPassword("");
	}
	
	/**
	 * Class constructor.
	 * 
	 * @param id the user's id.
	 * @param firstName the user's first name.
	 * @param lastName the user's last name.
	 * @param email the user's email.
	 * @param password the user's password.
	**/
	public User(final int id, final String firstName, final String lastName, final String email, final String password) {
		this.setId(id);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
		this.setPassword(password);
	}
	
	/**
	 * Gets the user's identifier.
	 * 
	 * @return the identifier.
	**/
	public int getId() {
		return this.id;
	}

	/**
	 * Sets the user's identifier.
	 *
	 * @param id the new identifier.
	**/
	public void setId(final int id) {
		this.id = id;
	}
	
	/**
	 * Gets the user's first name.
	 *
	 * @return the first name.
	**/
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Sets the user's first name.
	 * 
	 * @param firstName the new first name.
	**/
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}
	 
	/**
	 * Gets the user's last name.
	 *
	 * @return the last name.
	**/
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Sets the user's last name.
	 * 
	 * @param lastName the new last name.
	**/
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}
	 
	/**
	 * Gets the user's email address.
	 * 
	 * @return the email address.
	**/
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Sets the user's email address.
	 * 
	 * @param email the new user's email address.
	**/
	public void setEmail(final String email) {
		this.email = email;
	}
	
	/**
	 * Gets the user's password.
	 * 
	 * @return the password.
	**/
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Sets the user's password.
	 * 
	 * @param password the new user's password.
	**/
	public void setPassword(final String password) {
		this.password = password;
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
