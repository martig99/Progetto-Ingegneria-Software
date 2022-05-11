package main.java.it.unipr.message;

import java.io.Serializable;
import java.util.List;

/**
 * The class {@code Request} provides a simplified model of a request message.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class Request implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The type of the request message.
	**/
	private RequestType requestType;
	
	/**
	 * The object included in the request message.
	**/
	private List<Serializable> object;
	
	/**
	 * An attribute that is <code>true</code> if the request is to be executed in the background.
	**/
	private boolean backgroundRequest;
	
	/**
	 * Class constructor.
	**/
	public Request() {
		this.setRequestType(null);
		this.setObject(null);
		this.setBackgroundRequest(false);
	}
	
	/**
	 * Class constructor.
	 * 
	 * @param messageType the type of request message.
	**/
	public Request(final RequestType messageType) {
		this.setRequestType(messageType);
		this.setObject(null);
		this.setBackgroundRequest(false);
	}
	
	/**
	 * Class constructor.
	 * 
	 * @param requestType the type of the request message.
	 * @param object the object included in the request message.
	**/
	public Request(final RequestType requestType, final List<Serializable> object) {
		this.setRequestType(requestType);
		this.setObject(object);
		this.setBackgroundRequest(false);
	}
	
	/**
	 * Gets the type of the request message.
	 * 
	 * @return the type.
	**/
	public RequestType getRequestType() {
		return this.requestType;
	}
	
	/**
	 * Sets the type of the request message.
	 * 
	 * @param requestType the new type.
	**/
	public void setRequestType(final RequestType requestType) {
		this.requestType = requestType;
	}
	
	/**
	 * Gets the object included in the request message.
	 * 
	 * @return the object.
	**/
	public List<Serializable> getObject() {
		return this.object;
	}
	
	/**
	 * Sets the object included in the request message.
	 * 
	 * @param object the new object.
	**/
	public void setObject(final List<Serializable> object) {
		this.object = object;
	}
	
	/**
	 * Gets whether the request should be executed in the background or not.
	 * 
	 * @return <code>true</code> if the request is to be executed in the background.
	**/
	public boolean isBackgroundRequest() {
		return this.backgroundRequest;
	}
	
	/**
	 * Sets whether the request should be executed in the background or not.
	 * 
	 * @param backgroundRequest the new value.
	**/
	public void setBackgroundRequest(final boolean backgroundRequest) {
		this.backgroundRequest = backgroundRequest;
	}
	
	/**
	 * Gets a string that describes a request.
	 * 
	 * @return the string.
	**/
	@Override
	public String toString() {
		String toString = "Request Type: " + this.requestType.toString();
		
		if (this.object != null) {
			for (int i = 0; i < this.object.size(); i++) {
				if (this.object.get(i) != null) {
					toString += " - Object " + i + ": [" + this.object.get(i).toString() + "]";
				}
			}
		}
		
		return toString;
	}
}
