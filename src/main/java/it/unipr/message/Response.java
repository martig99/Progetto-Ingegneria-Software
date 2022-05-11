package main.java.it.unipr.message;

import java.io.Serializable;

/**
 * The class {@code Response} provides a simplified model of a response message.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class Response implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The type of the response message.
	**/
	private ResponseType responseType;
	
	/**
	 * The object included in the response message.
	**/
	private Serializable object;
	
	/**
	 * Class constructor.
	**/
	public Response() {
		this.setObject(null);
	}
	
	/**
	 * Class constructor.
	 * 
	 * @param responseType the type of the response message.
	 * @param object the object included in the response message.
	**/
	public Response(final ResponseType responseType, final Serializable object) {
		this.setResponseType(responseType);
		this.setObject(object);
	}

	/**
	 * Gets the type of the response message.
	 * 
	 * @return the type.
	**/
	public ResponseType getResponseType() {
		return this.responseType;
	}
	
	/**
	 * Sets the type of the response message.
	 * 
	 * @param responseType the new type.
	**/
	public void setResponseType(final ResponseType responseType) {
		this.responseType = responseType;
	}	
	
	/**
	 * Gets the object included in the response message.
	 * 
	 * @return the object.
	**/
	public Serializable getObject() {
		return this.object;
	}
	
	/**
	 * Sets the object included in the response message.
	 * 
	 * @param object the new object.
	**/
	public void setObject(final Serializable object) {
		this.object = object;
	}
	
	/**
	 * Gets a string that describes a response.
	 * 
	 * @return the string.
	**/
	@Override
	public String toString() {
		if (this.responseType != null)
			return this.responseType.getValue();

		if (this.object != null)
			return this.object.toString();
		
		return "";
	}
}
