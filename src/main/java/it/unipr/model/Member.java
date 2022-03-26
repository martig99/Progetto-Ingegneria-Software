package main.java.it.unipr.model;

/**
 * The class {@code Member} provides an implementation of a model of a club member and extends the class {@code User}.
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
	 * @param id the member's id.
	 * @param firstName the member's first name.
	 * @param lastName the member's last name.
	 * @param email the member's email.
	 * @param password the member's password.
	**/
	public Member (final int id, final String firstName, final String lastName, final String email, final String password) {
		super(id, firstName, lastName, email, password);
		this.setFiscalCode("");
		this.setAddress("");
	}
	
	/**
	 * Class constructor.
	 * 
	 * @param id the member's id.
	 * @param firstName the member's first name.
	 * @param lastName the member's last name.
	 * @param email the member's email.
	 * @param password the member's password.
	 * @param fiscalCode the member's fiscal code.
	 * @param address the member's address.
	**/
	public Member (final int id, final String firstName, final String lastName, final String email, final String password, final String fiscalCode, final String address) {
		super(id, firstName, lastName, email, password);
		this.setFiscalCode(fiscalCode);
		this.setAddress(address);
	}
	
	/**
	 * Gets the member's fiscal code.
	 *
	 * @return the fiscal code.
	**/
	public String getFiscalCode() {
		return this.fiscalCode;
	}

	/**
	 * Sets the member's fiscal code.
	 * 
	 * @param fiscalCode the new fiscal code.
	**/
	public void setFiscalCode(final String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}
	
	/**
	 * Gets the member's address.
	 *
	 * @return the address.
	**/
	public String getAddress() {
		return this.address;
	}

	/**
	 * Sets the member's address.
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
