package main.java.it.unipr.model;

/**
 * The class {@code Member} provides an implementation of a model of a club member.
 * This class extends the class {@code User}.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class Member extends User {
	
	private static final long serialVersionUID = 1L;
	
	private String fiscalCode;
	private String address;
	
	/**
	 * Class constructor.
	**/
	public Member() {
		super();
		this.setFiscalCode("");
		this.setAddress("");
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
	public Member(final int id, final String firstName, final String lastName, final String email, final String password, final StatusCode statusCode) {
		super(id, firstName, lastName, email, password, statusCode);
		this.setFiscalCode("");
		this.setAddress("");
	}
	
	/**
	 * Class constructor.
	 * 
	 * @param id the unique identifier of the user.
	 * @param firstName the first name of the user.
	 * @param lastName the last name of the user.
	 * @param email the email address of the user.
	 * @param password the password of the user.
	 * @param fiscalCode the fiscal code of the member.
	 * @param address the address of the member.
	 * @param statusCode the status code of the user.
	**/
	public Member (final int id, final String firstName, final String lastName, final String email, final String password, final String fiscalCode, final String address, final StatusCode statusCode) {
		super(id, firstName, lastName, email, password, statusCode);
		this.setFiscalCode(fiscalCode);
		this.setAddress(address);
	}
	
	/**
	 * Gets the fiscal code of the member.
	 *
	 * @return the fiscal code.
	**/
	public String getFiscalCode() {
		return this.fiscalCode;
	}

	/**
	 * Sets the fiscal code of the member.
	 * 
	 * @param fiscalCode the new fiscal code.
	**/
	public void setFiscalCode(final String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}
	
	/**
	 * Gets the address of the member.
	 *
	 * @return the address.
	**/
	public String getAddress() {
		return this.address;
	}

	/**
	 * Sets the address of the member.
	 * 
	 * @param fiscalCode the new address.
	**/
	public void setAddress(final String address) {
		this.address = address;
	}
	

	/**
	 * Gets a string that describes a club member.
	 * 
	 * @return the string.
	**/
	@Override
	public String toString() {
		return super.toString() + " - Fiscal Code: " + this.fiscalCode + " - Address: " + this.address;
	}
}
