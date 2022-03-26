package main.java.it.unipr.model;

import java.io.Serializable;

/**
  * The class {@code PaymentService} provides an implementation of a model of a payment service.
  * 
  * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
  * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class PaymentService implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int id;
	private String description;
	
	/**
	 * Class constructor.
	**/
	public PaymentService() {
		this.setId(0);
		this.setDescription("");
	}	
	
	/**
	 * Class constructor.
	 * 
	 * @param id the unique identifier of the payment service.
	 * @param description the description of the payment service.
	**/
	public PaymentService(final int id, final String description) {
		this.setId(id);
		this.setDescription(description);
	}	
	
	/**
	 * Gets the unique identifier of the payment service.
	 * 
	 * @return the unique identifier.
	**/
	public int getId() {
		return this.id;
	}
	
	/**
	 * Sets the unique identifier of the payment service.
	 *  
	 * @param id the new unique identifier.
	**/
	public void setId(final int id) {
		this.id = id;
	}
	
	/**
	 * Gets the description of the payment service.
	 * 
	 * @return the description.
	**/
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Sets the description of the payment service.
	 *  
	 * @param description the new description.
	**/
	public void setDescription(final String description) {
		this.description = description;
	}
	
	/**
	 * Gets a string that describes a payment service.
	 * 
	 * @return the string.
	**/
	@Override
	public String toString() {
		return "Id: " + this.id + " - Description: " + this.description;
	}
}
