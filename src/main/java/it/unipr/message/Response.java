package main.java.it.unipr.message;

import java.io.Serializable;

/**
 * The class {@code Response} provides a simplified model of a response message.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class Response implements Serializable {
  
	private static final long serialVersionUID = 1L;
	
	private ResponseType responseType;
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
	 * @param responseType
	 * @param object
	**/
	public Response(final ResponseType responseType, final Serializable object) {
		this.setResponseType(responseType);
		this.setObject(object);
	}

	/**
	 * Gets the response type.
	 * 
	 * @return the response type.
	**/
	public ResponseType getResponseType() {
		return this.responseType;
	}
	
	/**
	 * Sets the response type.
	 * 
	 * @param responseType the new response type.
	**/
	public void setResponseType(final ResponseType responseType) {
		this.responseType = responseType;
	}	
	
	/**
	 * 
	 * @return
	**/
	public Serializable getObject() {
		return this.object;
	}
	
	/**
	 * 
	 * @param object
	**/
	public void setObject(final Serializable object) {
		this.object = object;
	}

	/**
	 * 
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
