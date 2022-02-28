package it.unipr.java.model;

/**
 * The class {@code Member} provides an implementation of a model of a club member and extends the class {@code User}.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class Member extends User{
	
	private float membershipFee;
	
	/**
	 * Class constructor.
	**/
	public Member() {
		super();
		this.membershipFee = 0;
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
	 * @param membershipFee the user's membership fee.
	**/
	public Member (final int id, final String fiscalCode, final String firstName, final String lastName, final String email, final String password, final float membershipFee) {
		super(id, fiscalCode, firstName, lastName, email, password);
		this.setMembershipFee(membershipFee);
	}
	
	/**
	 * Gets the user's membership fee.
	 * 
	 * @return the membership fee.
	**/
	public float getMembershipFee() {
		return this.membershipFee;
	}
	
	/**
	 * Sets the user's membership fee.
	 * 
	 * @param membershipFee the new user's membership fee.
	**/
	public void setMembershipFee(final float membershipFee) {
		this.membershipFee = membershipFee;
	}

	/**
	 * Gets a string that describes a club member.
	 * 
	 * @return the string.
	**/
	@Override
	public String toString() {
		return super.toString() + " - Membership Fee: " + this.membershipFee;
	}
}
