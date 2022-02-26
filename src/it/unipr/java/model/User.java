package it.unipr.java.model;

/**
 * The class {@code User} provides an implementation of a model of an user.
**/
public class User {
	
	private int id;
	private String fiscalCode;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
	/**
	 * Class constructor.
	**/
	public User() {
		this.id = 0;
		this.fiscalCode = "";
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.password = "";
	}
	
	/**
	 * Class constructor.
	 * 
	 * @param id the user's id.
	 * @param fiscalCode the user's fiscal code.
	 * @param firstName the user's first name.
	 * @param lastName the user's last name.
	 * @param email the user's email.
	 * @param password the user's password.
	**/
	public User (final int id, final String fiscalCode, final String firstName, final String lastName, final String email, final String password) {
		this.setId(id);
		this.setFiscalCode(fiscalCode);
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
	 * @param id the identifier.
	**/
	public void setId(final int id) {
		this.id = id;
	}
	
	/**
	 * Gets the user's fiscal code.
	 *
	 * @return the fiscal code.
	**/
	public String getFiscalCode() {
		return this.fiscalCode;
	}

	/**
	 * Sets the user's fiscal code.
	 * 
	 * @param fiscalCode the fiscal code.
	 * @return {@code true} if the operation is successful.
	**/
	public boolean setFiscalCode(final String fiscalCode) {
		if (!fiscalCode.isEmpty()) {
			this.fiscalCode = fiscalCode;
			return true;
		}
		
		return false;
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
	 * @param firstName the first name.
	 * @return {@code true} if the operation is successful.
	**/
	public boolean setFirstName(final String firstName) {
		if (!firstName.isEmpty()) {
			this.firstName = firstName;
			return true;
		}
		
		return false;
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
	 * @param lastName the last name.
	 * @return {@code true} if the operation is successful.
	**/
	public boolean setLastName(final String lastName) {
		if (!lastName.isEmpty()) {
			this.lastName = lastName;
			return true;
		}
		
		return false;
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
	 * @return {@code true} if the operation is successful.
	**/
	public boolean setEmail(final String email) {
		if (!email.isEmpty()) {
			this.email = email;
			return true;
		}
		
		return false;
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
	 * @return {@code true} if the operation is successful.
	**/
	public boolean setPassword(final String password) {
		if (!password.isEmpty()) {
			this.password = password;
			return true;
		} 
		
		return false;
	}

	/**
	 * Gets a string that describes an user.
	 * 
	 * @return the string.
	**/
	@Override
	public String toString() {
		return "Id: " + this.id + " - Fiscal Code: " + this.fiscalCode + " - First Name: " + this.firstName + " - Last Name: " + this.lastName + " - Email: " + this.email + " - Password: " + this.password;
	}
}
