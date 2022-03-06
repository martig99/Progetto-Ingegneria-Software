package it.unipr.java.model;

import java.util.Date;

/**
  * The class {@code Payment} provides an implementation of a model of a payment.
  * 
  * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
  * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class Payment {

	private int id;
	private Date date;
	private Member member;
	private Boat boat;
	//MANCA RACE REGISTRATION
	private Date validityStartDate;
	private Date validityEndDate;
	private double total;
	private PaymentService paymentService;
	
	/**
	 * Class constructor.
	**/
	public Payment() {
		this.setId(0);
		this.setDate(null);
		this.setMember(null);
		this.setBoat(null);
		//MANCA RACE REGISTRATION
		this.setValidityStartDate(null);
		this.setValidityEndDate(null);
		this.setTotal(0);
		this.setPaymentService(null);
	}	
	
	/**
	 * Class constructor.
	**/
	public Payment(final int id, final Date date, final Member member, final Boat boat, final Date validityStartDate, final Date validityEndDate, final double total, final PaymentService paymentService) {
		this.setId(id);
		this.setDate(date);
		this.setMember(member);
		this.setBoat(boat);
		this.setValidityStartDate(validityStartDate);
		this.setValidityEndDate(validityEndDate);
		this.setTotal(total);
		this.setPaymentService(paymentService);
	}	
	
	/**
	 * Gets the unique identifier of the payment.
	 * 
	 * @return the unique identifier.
	**/
	public int getId() {
		return this.id;
	}
	
	/**
	 * Sets the unique identifier of the payment.
	 *  
	 * @param id the new unique identifier.
	**/
	public void setId(final int id) {
		this.id = id;
	}
	
	/**
	 * Gets the date of the payment.
	 * 
	 * @return the date.
	**/
	public Date getDate() {
		return this.date;
	}
	
	/**
	 * Sets the date of the payment.
	 *  
	 * @param date the new date.
	**/
	public void setDate(final Date date) {
		this.date = date;
	}
	
	/**
	 * Gets the member who made the payment.
	 * 
	 * @return the member.
	**/
	public Member getMember() {
		return this.member;
	}
	
	/**
	 * Sets the member who made the payment.
	 *  
	 * @param member the new member.
	**/
	public void setMember(final Member member) {
		this.member = member;
	}
	
	/**
	 * Gets the boat for which the storage fee has been paid.
	 * 
	 * @return the boat.
	**/
	public Boat getBoat() {
		return this.boat;
	}
	
	/**
	 * Sets the boat to pay the storage fee.
	 *  
	 * @param boat the new boat.
	**/
	public void setBoat(final Boat boat) {
		this.boat = boat;
	}
	
	/**
	 * Gets the validity start date of the payment.
	 * 
	 * @return the validity start date.
	**/
	public Date getValidityStartDate() {
		return this.validityStartDate;
	}
	
	/**
	 * Sets the validity start date of the payment.
	 *  
	 * @param date the new validity start date.
	**/
	public void setValidityStartDate(final Date validityStartDate) {
		this.validityStartDate = validityStartDate;
	}
	
	/**
	 * Gets the validity end date of the payment.
	 * 
	 * @return the validity end date.
	**/
	public Date getValidityEndDate() {
		return this.validityEndDate;
	}
	
	/**
	 * Sets the validity end date of the payment.
	 *  
	 * @param date the new validity end date.
	**/
	public void setValidityEndDate(final Date validityEndDate) {
		this.validityEndDate = validityEndDate;
	}
	
	/**
	 * Gets the total of the payment.
	 * 
	 * @return the total.
	**/
	public double getTotal() {
		return this.total;
	}
	
	/**
	 * Sets the total of the payment.
	 *  
	 * @param total the new total.
	**/
	public void setTotal(final double total) {
		this.total = total;
	}
	
	/**
	 * Gets the payment service.
	 * 
	 * @return the payment service.
	**/
	public PaymentService getPaymentService() {
		return this.paymentService;
	}
	
	/**
	 * Sets the payment service.
	 *  
	 * @param paymentService the new payment service.
	**/
	public void setPaymentService(final PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	/**
	 * Gets a string that describes a payment.
	 * 
	 * @return the string.
	**/
	@Override
	public String toString() {
		return "Id: " + this.id + " - Date: " + this.date + " - Member: " + this.member.getEmail() + " - Validity Start Date: " + this.validityStartDate + " - Validity End Date: " + this.validityEndDate + " - Total: " + this.total + " - Payment Service: " + this.paymentService.getDescription();
	}
}
