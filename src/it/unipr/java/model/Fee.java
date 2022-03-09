package it.unipr.java.model;

/**
 * The class {@code Fee} provides an implementation of a model of a fee.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class Fee {
	
	private int id;
	private FeeType type;
	private float amount;
	private int validityPeriod;
	private StatusCode statusCode;
	
	/**
	 * Class constructor.
	**/
	public Fee() {
		this.setId(0);
		this.setType(null);
		this.setAmount(0);
		this.setValidityPeriod(0);
	}	
	
	/**
	 * Class constructor.
	 * 
	 * @param id the id of the fee.
	 * @param type the type of the fee.
	 * @param amount the amount of the fee.
	 * @param validityPeriod the validity period of the fee in days.
	 * @param statusCode the status code of the fee.
	**/
	public Fee(final int id, final FeeType type, final float amount, final int validityPeriod, final StatusCode statusCode) {
		this.setId(id);
		this.setType(type);
		this.setAmount(amount);
		this.setValidityPeriod(validityPeriod);
		this.setStatusCode(statusCode);
	}	
	
	/**
	 * Gets the id of the fee.
	 * 
	 * @return the id.
	**/
	public int getId() {
		return this.id;
	}
	
	/**
	 * Sets the id of the fee.
	 * 
	 * @param id the new id.
	**/
	public void setId(final int id) {
		this.id = id;
	}
	
	/**
	 * Gets the type of the fee.
	 * 
	 * @return the type.
	**/
	public FeeType getType() {
		return type;
	}
	
	/**
	 * Sets the type of the fee.
	 * 
	 * @param type the new type.
	**/
	public void setType(final FeeType type) {
		this.type = type;
	}
	
	/**
	 * Gets the amount of the fee.
	 * 
	 * @return the amount.
	**/
	public float getAmount() {
		return this.amount;
	}
	
	/**
	 * Sets the amount of the fee.
	 * 
	 * @param amount the new amount.
	**/
	public void setAmount(final float amount) {
		this.amount = amount;
	}
	
	/**
	 * Gets the validity period of the fee in days.
	 * 
	 * @return the validity period.
	**/
	public int getValidityPeriod() {
		return this.validityPeriod;
	}
	
	/**
	 * Sets the validity period of the fee in days.
	 * 
	 * @param validityPeriod the new validity period.
	**/
	public void setValidityPeriod(final int validityPeriod) {
		this.validityPeriod = validityPeriod;
	}
	
	/**
	 * Gets the status code of the fee.
	 * 
	 * @return the status code.
	**/
	public StatusCode getStatusCode() {
		return this.statusCode;
	}
	
	/**
	 * Sets the status code of the fee.
	 * 
	 * @param statusCode the new status code.
	**/
	public void setStatusCode(final StatusCode statusCode) {
		this.statusCode = statusCode;
	}
	
	/**
	 * Gets a string that describes a fee.
	 * 
	 * @return the string.
	**/
	@Override
	public String toString() {
		return "Id: " + this.id + " - Type: " + this.type.toString() + " - Amount: " + this.amount + " - Validity Period: " + this.validityPeriod + " - Status Code: " + this.statusCode.toString();
	}
}
