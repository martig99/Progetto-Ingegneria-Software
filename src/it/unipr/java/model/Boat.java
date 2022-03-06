package it.unipr.java.model;

/**
  * The class {@code Boat} provides an implementation of a model of a boat.
  * 
  * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
  * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class Boat {

	private int id;
	private String name;
	private int length;
	private float storageFee;
	private Member owner;
	
	/**
	 * Class constructor.
	**/
	public Boat() {
		this.setId(0);
		this.setName("");
		this.setLength(0);
		this.setStorageFee(0);
		this.setOwner(null);
	}	
	
	/**
	 * Class constructor.
	 * 
	 * @param id the unique identifier of the boat.
	 * @param name the name of the boat.
	 * @param length the length of the boat.
	 * @param storageFee the storage fee of the boat.
	 * @param owner the owner of the boat.
	**/
	public Boat(final int id, final String name, final int length, final float storageFee, final Member owner) {
		this.setId(id);
		this.setName(name);
		this.setLength(length);
		this.setStorageFee(storageFee);
		this.setOwner(owner);
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
	 * Gets the name of the boat.
	 * 
	 * @return the name.
	**/
	public String getName() {
		return this.name;
	}
	
	/**
	 * Sets the name of the boat.
	 *  
	 * @param name the new name.
	**/
	public void setName(final String name) {
		this.name = name;
	}
	
	/**
	 * Gets the length of the boat.
	 * 
	 * @return the length.
	**/
	public int getLength() {
		return this.length; 
	}
	
	/**
	 * Sets the length of the boat.
	 *  
	 * @param length the new length.
	**/
	public void setLength(final int length) {
		this.length = length;
	}
	
	/**
	 * Gets the storage fee of the boat.
	 * 
	 * @return the storage fee.
	**/
	public float getStoragFee() {
		return this.storageFee;
	}
	
	/**
	 * Sets the storage fee of the boat.
	 * 
	 * @param storageFee the new storage fee.
	**/
	public void setStorageFee(final float storageFee) {
		this.storageFee = storageFee;
	}
	
	/**
	 * Gets the owner of the boat.
	 * 
	 * @return the owner.
	**/
	public Member getOwner() {
		return this.owner;
	}
	
	/**
	 * Sets the owner of the boat.
	 * 
	 * @param owner the new owner.
	**/
	public void setOwner(final Member owner) {
		this.owner = owner;
	}
	
	/**
	 * Gets a string that describes a boat.
	 * 
	 * @return the string.
	**/
	@Override
	public String toString() {
		return "Id: " + this.id + " - Name: " + this.name + " - Length: " + this.length + " - Storage Fee: " + this.storageFee + " - Owner [" + this.owner.toString() + "]";
	}
}
