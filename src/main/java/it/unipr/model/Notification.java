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
	private boolean readStatus;
	
	/**
	 * Class constructor.
	**/
	public Notification() {
		this.setId(0);
		this.setMember(null);
		this.setBoat(null);
		this.setFee(null);
		this.setReadStatus(false);
	}	
	
	/**
	 * Class constructor.
	 * 
	 * @param id the unique identifier of the notification.
	**/
	public Notification(final int id, final Member member, final Boat boat, final Fee fee, final boolean readStatus) {
		this.setId(id);
		this.setMember(member);
		this.setBoat(boat);
		this.setFee(fee);
		this.setReadStatus(readStatus);
	}
	
	/**
	 * Gets the unique identifier of the boat.
	 * 
	 * @return the unique identifier.
	**/
	public int getId() {
		return this.id;
	}
	
	/**
	 * Sets the unique identifier of the boat.
	 *  
	 * @param id the new unique identifier.
	**/
	public void setId(final int id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return
	**/
	public Member getMember() {
		return this.member;
	}
	
	/**
	 * 
	 * @param user
	**/
	public void setMember(final Member member) {
		this.member = member;
	}
	
	/**
	 * 
	 * @return
	**/
	public Boat getBoat() {
		return this.boat;
	}
	
	/**
	 * 
	 * @param boat
	**/
	public void setBoat(final Boat boat) {
		this.boat = boat;
	}
	
	/**
	 * 
	 * @return
	**/
	public Fee getFee() {
		return this.fee;
	}
	
	/**
	 * 
	 * @param fee
	**/
	public void setFee(final Fee fee) {
		this.fee = fee;
	}
	
	/**
	 * 
	 * @return
	**/
	public boolean isReadStatus() {
		return this.readStatus;
	}
	
	/**
	 * 
	 * @param readStatus
	**/
	public void setReadStatus(final boolean readStatus) {
		this.readStatus = readStatus;
	}
	
	/**
	 * Gets a string that describes a boat.
	 * 
	 * @return the string.
	**/
	@Override
	public String toString() {
		String boatString = this.boat != null ? " - Boat: " + this.boat.getId() : "";

		return "Id: " + this.id + " - Member: " + this.member.getEmail() + boatString + " - Fee: " + this.fee.getType() + " - Read status: " + this.readStatus;
	}
}
