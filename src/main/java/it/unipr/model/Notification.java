package main.java.it.unipr.model;

import java.io.Serializable;

/**
  * The class {@code Notification} provides an implementation of a model of a notification.
  * 
  * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
  * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class Notification implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * The unique identifier of the notification.
	**/
	private int id;
	
	/**
	 * The member who received the notification.
	**/
	private Member member;
	
	/**
	 * The boat to which the notification of payment of the storage fee refers.
	**/
	private Boat boat;
	
	/**
	 * The fee to which the notification relates.
	**/
	private Fee fee;
	
	/**
	 * The status code of the notification.
	**/
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
	 * @param member the new member.
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
		String emailMember = this.member != null ? this.member.getEmail() : "";
		String idBoat = this.boat != null ? String.valueOf(this.boat.getId()) : "";
		String feeType = this.fee != null ? this.fee.getType().toString() : "";
		
		return "Id: " + this.id + " - Member: " + emailMember + " - Boat: " + idBoat + " - Fee: " + feeType + " - Status Code: " + this.statusCode.toString();
	}
}
