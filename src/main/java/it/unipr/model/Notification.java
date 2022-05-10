package main.java.it.unipr.model;

import java.io.Serializable;

/**
  * The class {@code Notification} provides an implementation of a model of a notification.
  * 
  * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
  * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class Notification implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private Member member;
	private Boat boat;
	private Fee fee;
	private StatusCode statusCode;
	
	/**
	 * Class constructor.
	**/
	public Notification() {
		this.setId(0);
		this.setMember(null);
		this.setBoat(null);
		this.setFee(null);
		this.setStatusCode(null);
	}	
	
	/**
	 * Class constructor.
	 * 
	 * @param id the unique identifier of the notification.
	 * @param member the member who received the notification.
	 * @param boat the boat to which the notification of payment of the storage fee refers.
	 * @param fee the fee to which the notification relates.
	 * @param statusCode the status code of the notification.
	**/
	public Notification(final int id, final Member member, final Boat boat, final Fee fee, final StatusCode statusCode) {
		this.setId(id);
		this.setMember(member);
		this.setBoat(boat);
		this.setFee(fee);
		this.setStatusCode(statusCode);
	}
	
	/**
	 * Gets the unique identifier of the notification.
	 * 
	 * @return the unique identifier.
	**/
	public int getId() {
		return this.id;
	}
	
	/**
	 * Sets the unique identifier of the notification.
	 *  
	 * @param id the new unique identifier.
	**/
	public void setId(final int id) {
		this.id = id;
	}
	
	/**
	 * Gets the member who received the notification.
	 * 
	 * @return the member.
	**/
	public User getMember() {
		return this.member;
	}
	
	/**
	 * Sets the member who received the notification.
	 * 
	 * @param user the new member.
	**/
	public void setMember(final Member member) {
		this.member = member;
	}
	
	/**
	 * Gets the boat to which the notification of payment of the storage fee refers.
	 * 
	 * @return the boat.
	**/
	public Boat getBoat() {
		return this.boat;
	}
	
	/**
	 * Sets the boat to which the notification of payment of the storage fee refers.
	 * 
	 * @param boat the new boat.
	**/
	public void setBoat(final Boat boat) {
		this.boat = boat;
	}
	
	/**
	 * Gets the fee to which the notification relates.
	 * 
	 * @return the fee.
	**/
	public Fee getFee() {
		return this.fee;
	}
	
	/**
	 * Sets the fee to which the notification relates.
	 * 
	 * @param fee the new fee.
	**/
	public void setFee(final Fee fee) {
		this.fee = fee;
	}
	
	/**
	 * Gets the status code of the notification.
	 * 
	 * @return the status code.
	**/
	public StatusCode getStatusCode() {
		return this.statusCode;
	}
	
	/**
	 * Sets the status code of the notification.
	 * 
	 * @param statusCode the new status code.
	**/
	public void setStatusCode(final StatusCode statusCode) {
		this.statusCode = statusCode;
	}
	
	/**
	 * Gets a string that describes a notification.
	 * 
	 * @return the string.
	**/
	@Override
	public String toString() {
		String boatString = this.boat != null ? " - Boat: " + this.boat.getId() : "";

		return "Id: " + this.id + " - Member: " + this.member.getEmail() + boatString + " - Fee: " + this.fee.getType() + " - Status Code: " + this.statusCode.toString();
	}
}
