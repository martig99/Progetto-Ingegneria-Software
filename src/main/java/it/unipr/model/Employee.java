package main.java.it.unipr.model;

/**
 * The class {@code Employee} provides an implementation of a model of a club employee.
 * This class extends the class {@code User}.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class Employee extends User {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * An attribute that is <code>true</code> if the employee is administrator.
	**/
	private boolean administrator;
	
	/**
	 * Class constructor.
	**/
	public Employee() {
		super();
		this.setAdministrator(false);
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
	public Employee(final int id, final String firstName, final String lastName, final String email, final String password, final StatusCode statusCode) {
		super(id, firstName, lastName, email, password, statusCode);
		this.setAdministrator(false);
	}
	
	/**
	 * Class constructor.
	 * 
	 * @param id the unique identifier of the user.
	 * @param firstName the first name of the user.
	 * @param lastName the last name of the user.
	 * @param email the email address of the user.
	 * @param password the password of the user.
	 * @param administrator <code>true</code> if the employee is administrator.
	 * @param statusCode the status code of the user.
	**/
	public Employee (final int id, final String firstName, final String lastName, final String email, final String password, final boolean administrator, final StatusCode statusCode) {
		super(id, firstName, lastName, email, password, statusCode);
		this.setAdministrator(administrator);
	}

	/**
	 * Gets whether the employee is administrator or not.
	 * 
	 * @return <code>true</code> if the employee is administrator.
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
