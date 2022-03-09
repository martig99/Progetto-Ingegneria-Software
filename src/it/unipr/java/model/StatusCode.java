package it.unipr.java.model;

/**
 * The enum {@code Status} defines the status 
 * 
 * {@link #ACTIVE}
 * {@link #ELIMINATED}
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/

public enum StatusCode {
	/**
	 * The active state.
	**/
	ACTIVE(0),
	/**
	 * The eliminated state.
	**/
	ELIMINATED(9);
	
	private final int value;

    StatusCode (final int newValue) {
        this.value = newValue;
    }

    public int getValue() { 
    	return value; 
    }
    
    public static StatusCode getStatusCode(final int value) {
        for (StatusCode s: StatusCode.values()) {
            if (s.value == value) 
            	return s;
        }
        
        throw new IllegalArgumentException("Status code not found.");
     }
}
