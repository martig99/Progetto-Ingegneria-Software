package main.java.it.unipr.message;

/**
 * The enum {@code ResponseType} defines the type of the response message.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public enum ResponseType {
	
	OK("Operation successfully performed.", true),
	ERROR("An error occurred.", false),
	ERROR_INVALID_EMAIL("The format of the email is not valid.", false),
	ERROR_INVALID_FISCAL_CODE("The format of the fiscal code is not valid.", false),
	ERROR_INCORRECT_CREDENTIALS("The credentials are incorrect.", false),
	ERROR_EXIST_EMAIL("An account with this email already exists.", false),
	ERROR_USER_EMAIL("There is no user with this email.", false),
	ERROR_EXIST_FISCAL_CODE("An account with this fiscal code already exists.", false),
	SUCCESS_ADD_USER("The new user has been successfully created.", true),
	SUCCESS_UPDATE_USER("The user has been successfully updated.", true),
	SUCCESS_REMOVE_EMPLOYEE("The employee has been removed successfully.", true),
	SUCCESS_REMOVE_BOAT("The boat has been removed correctly.", true),
	SUCCESS_ADD_BOAT("The boat has been added correctly.", true),
	SUCCESS_UPDATE_BOAT("The boat has been updated correctly.", true),
	ERROR_BOAT_NAME("A boat with this name already exists.", false),	
	SUCCESS_REMOVE_RACE("The race has been removed correctly.", true),
	ERROR_RACE_DATE("The date of the race must be greater than the current date.", false),
	ERROR_EXIST_RACE_DATE("A race on this date already exists.", false),
	ERROR_END_REGISTRATION_DATE("The last date to register for the race must be greater than today and less than the date of the race.", false),
	ERROR_MAX_NUMBER_BOATS("The maximum number of participating boats has been reached.", false),
	SUCCESS_ADD_RACE("The new race has been added correctly.", true),
	SUCCESS_UPDATE_RACE("The race has been updated correctly.", true),
	ERROR_NO_BOATS("There aren’t enough boats to create a race.", false),
	ERROR_NUMBER_BOATS("The number of participating boats is not correct.", false),
	ERROR_ONE_RACE_REGISTRATION("The number of boats participating in the race cannot be altered because a boat is already registered.", false),
	ERROR_CLOSE_REGISTRATION("It is not possible to take any action because the registrations for this race are closed.", false),
	SUCCESS_REMOVE_REGISTRATION("The registration has been removed correctly.", true),
	ERROR_NO_REGISTRATIONS("No boats has registered for this race.", false),
	SUCCESS_PAY_FEE("The fee has been paid correctly.", true),
	SUCCESS_NOTIFY_PAYMENT("Notification to pay the fee has been sent successfully.", true),
	ERROR_NOTIFY_PAYMENT("The payment notification has already been sent previously.", false),
	ERROR_UNPAY_MEMBERSHIP_FEE("Unpaid membership fee.", false),
	ERROR_UNPAY_STORAGE_FEE("Unpaid storage fee for this boat.", false),
	ERROR_USER_BOATS_PER_RACE("Only one boat per club member can be registered for a race.", false),
	SUCCESS_ADD_RACE_REGISTRATION("The boat has been registered at the race correctly.", true),
	SUCCESS_UPDATE_RACE_REGISTRATION("The registration of the boat at the race has been updated correctly.", true),
	SUCCESS_UPDATE_FEE("The fee has been updated correctly.", true),
	ERROR_PAYMENT_SERVICE("The payment service is incorrect.", false),
	ERROR_BOAT("The boat is incorrect.", false),
	ERROR_MEMBER("The member is incorrect.", false);
	
	private final String value;
	private final boolean successful;

	/**
	 * Class constructor.
	 * 
	 * @param value the value of the response type.
	 * @param success
	**/
    ResponseType (final String value, final boolean successful) {
        this.value = value;
        this.successful = successful;
    }

    /**
     * Gets the value of the response type.
     * 
     * @return the value.
    **/
    public String getValue() { 
    	return this.value; 
    }
    
    /**
     * Gets whether a type of response is successful or not.
     * 
     * @return <code>true</code> if the type of response is successful.
    **/
    public boolean isSuccessful() {
    	return this.successful;
    }
}
