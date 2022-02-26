package it.unipr.java.model;

/**
 * The class {@code Employee} provides an implementation of a model of a club employee and extends the class {@code User}.
**/
public class Employee extends User{
	
	private boolean administrator;
	
	/**
	 * Class constructor.
	**/
	public Employee() {
		super();
		this.administrator = false;
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
	 * @param administrator {@code true} if the employee is administrator.
	**/
	public Employee (final int id, final String fiscalCode, final String firstName, final String lastName, final String email, final String password, final boolean administrator) {
		super(id, fiscalCode, firstName, lastName, email, password);
		this.setAdministrator(administrator);
	}
	
	/**
	 * Gets whether the employee is administrator or not.
	 * 
	 * @return {@code true} if the employee is administrator.
	**/
	public boolean isAdministrator() {
		return this.administrator;
	}
	
	/**
	 * Sets whether the employee is administrator or not.
	 * 
	 * @param administrator the new value.
	**/
	public void setAdministrator(final boolean administrator) {
		this.administrator = administrator;
	}

	/**
	 * Gets a string that describes a club employee.
	 * 
	 * @return the string.
	**/
	@Override
	public String toString() {
		return super.toString() + " - Administrator: " + this.administrator;
	}
}
