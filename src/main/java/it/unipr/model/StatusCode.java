package main.java.it.unipr.model;

/**
 * The enum {@code StatusCode} defines the status of an instance.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
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

	/**
	 * Class constructor.
	 * 
	 * @param value the value.
	**/
    StatusCode (final int value) {
        this.value = value;
    }

    /**
     * Gets the value.
     * 
     * @return the value.
    **/
    public int getValue() { 
    	return value; 
    }
    
    /**
     * Gets the status corresponding to a value.
     * 
     * @param value the value.
     * @return the status.
    **/
    public static StatusCode getStatusCode(final int value) {
        for (StatusCode s: StatusCode.values()) {
            if (s.value == value) 
            	return s;
        }
        
        throw new IllegalArgumentException("Status code not found.");
    }
}
