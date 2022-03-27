package main.java.it.unipr.message;

import java.io.Serializable;

/**
 * The class {@code Request} provides a simplified model of a request message.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class Request implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private RequestType requestType;
	private Serializable primaryObject;
	private Serializable secondaryObject;
	private boolean backgroundRequest;
	
	/**
	 * Class constructor.
	**/
	public Request() {
		this.setRequestType(null);
		this.setPrimaryObject(null);
		this.setSecondaryObject(null);
		this.setBackgroundRequest(false);
	}
	
	/**
	 * Class constructor.
	 * 
	 * @param messageType the type of message requested.
	**/
	public Request(final RequestType messageType) {
		this.setRequestType(messageType);
		this.setBackgroundRequest(false);
	}
	
	/**
	 * Class constructor.
	 * 
	 * @param messageType
	 * @param object
	**/
	public Request(final RequestType messageType, final Serializable primaryObject, final Serializable secondaryObject) {
		this.setRequestType(messageType);
		this.setPrimaryObject(primaryObject);
		this.setSecondaryObject(secondaryObject);
		this.setBackgroundRequest(false);
	}
	
	/**
	 * 
	 * @return
	**/
	public RequestType getRequestType() {
		return this.requestType;
	}
	
	/**
	 * 
	 * @param requestType
	**/
	public void setRequestType(final RequestType requestType) {
		this.requestType = requestType;
	}
	
	/**
	 * 
	 * @return
	**/
	public Serializable getPrimaryObject() {
		return this.primaryObject;
	}
	
	/**
	 * 
	 * @param primaryObject
	**/
	public void setPrimaryObject(final Serializable primaryObject) {
		this.primaryObject = primaryObject;
	}
	
	/**
	 * 
	 * @return
	**/
	public Serializable getSecondaryObject() {
		return this.secondaryObject;
	}
	
	/**
	 * 
	 * @param primaryObject
	**/
	public void setSecondaryObject(final Serializable secondaryObject) {
		this.secondaryObject = secondaryObject;
	}
	
	/**
	 * 
	 * @return
	**/
	public boolean isBackgroundRequest() {
		return this.backgroundRequest;
	}
	
	/**
	 * 
	 * @param backgroundRequest
	**/
	public void setBackgroundRequest(final boolean backgroundRequest) {
		this.backgroundRequest = backgroundRequest;
	}
	
	/**
	 * 
	 * @return
	**/
	public static long getSerialversionUID() {
		return serialVersionUID;
	}
}
