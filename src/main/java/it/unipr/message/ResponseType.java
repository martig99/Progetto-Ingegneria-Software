package main.java.it.unipr.message;

/**
 * The enum {@code ResponseType} defines the type of the response message.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public enum ResponseType {
	
	/**
	 * The response that the operation was a success.
	**/
	OK("Operation successfully performed.", true),
	
	/**
	 * The response that the operation resulted in an error.
	**/
	ERROR("An error occurred.", false),
	
	/**
	 * The negative response indicates that the e-mail entered does not respect the correct format.
	**/
	ERROR_INVALID_EMAIL("The format of the email is not valid.", false),
	
	/**
	 * The negative response indicates that the fiscal code entered does not respect the correct format.
	**/
	ERROR_INVALID_FISCAL_CODE("The format of the fiscal code is not valid.", false),
	
	/**
	 * The negative response indicates that the credentials entered are incorrect.
	**/
	ERROR_INCORRECT_CREDENTIALS("The credentials are incorrect.", false),
	
	/**
	 * The negative response indicates that an account already exists in the database with the email entered.
	**/
	ERROR_EXIST_EMAIL("An account with this email already exists.", false),
	
	/**
	 * The negative response indicates that no user exists with the email entered.
	**/
	ERROR_USER_EMAIL("There is no user with this email.", false),
	
	/**
	 * The negative response indicates that an account already exists in the database with the fiscal code entered.
	**/
	ERROR_EXIST_FISCAL_CODE("An account with this fiscal code already exists.", false),
	
	/**
	 * The positive response indicates that a new user has been added.
	**/
	SUCCESS_ADD_USER("The new user has been successfully created.", true),
	
	/**
	 * The positive response indicates that a user has been updated.
	**/
	SUCCESS_UPDATE_USER("The user has been successfully updated.", true),
	
	/**
	 * The positive response indicates that a employee has been removed.
	**/
	SUCCESS_REMOVE_EMPLOYEE("The employee has been removed successfully.", true),
	
	/**
	 * The positive response indicates that a new boat has been added.
	**/
	SUCCESS_ADD_BOAT("The boat has been added correctly.", true),
	
	/**
	 * The positive response indicates that a boat has been updated.
	**/
	SUCCESS_UPDATE_BOAT("The boat has been updated correctly.", true),
	
	/**
	 * The positive response indicates that a boat has been removed.
	**/
	SUCCESS_REMOVE_BOAT("The boat has been removed correctly.", true),
	
	/**
	 * The negative response indicates that a boat already exists in the database with the name entered.
	**/
	ERROR_BOAT_NAME("A boat with this name already exists.", false),	
	
	/**
	 * The negative response indicates that the date of the race must be greater than the current date.
	**/
	ERROR_RACE_DATE("The date of the race must be greater than the current date.", false),
	
	/**
	 * The negative response indicates that a race already exists in the database on the date entered.
	**/
	ERROR_EXIST_RACE_DATE("A race on this date already exists.", false),
	
	/**
	 * The negative response indicates that the last date to register for the race must be greater than the current date and lower than the date of the race.
	**/
	ERROR_END_REGISTRATION_DATE("The last date to register for the race must be greater than the current date and less than the date of the race.", false),
	
	/**
	 * The negative response indicates that the maximum number of participating boats has been reached.
	**/
	ERROR_MAX_NUMBER_BOATS("The maximum number of participating boats has been reached.", false),
	
	/**
	 * The positive response indicates that a new race has been added.
	**/
	SUCCESS_ADD_RACE("The new race has been added correctly.", true),
	
	/**
	 * The positive response indicates that a race has been updated.
	**/
	SUCCESS_UPDATE_RACE("The race has been updated correctly.", true),
	
	/**
	 * The positive response indicates that a race has been removed.
	**/
	SUCCESS_REMOVE_RACE("The race has been removed correctly.", true),
	
	/**
	 * The negative response indicates that there are not enough boats to create a race.
	**/
	ERROR_NO_BOATS("There are not enough boats to create a race.", false),
	
	/**
	 * The negative response indicates that the number of participating boats is incorrect.
	**/
	ERROR_NUMBER_BOATS("The number of participating boats is not correct.", false),
	
	/**
	 * The negative response indicates that the number of boats participating in the race cannot be updated because a boat is already registered.
	**/
	ERROR_ONE_RACE_REGISTRATION("The number of boats participating in the race cannot be updated because a boat is already registered.", false),
	
	/**
	 * The negative response indicates that is not possible to take any action because the registrations for the race are closed.
	**/
	ERROR_CLOSE_REGISTRATION("It is not possible to take any action because the registrations for this race are closed.", false),
	
	/**
	 * The positive response indicates that the registration to the race has been removed.
	**/
	SUCCESS_REMOVE_REGISTRATION("The registration has been removed correctly.", true),
	
	/**
	 * The negative response indicates that there are not boats has registered for the race.
	**/
	ERROR_NO_REGISTRATIONS("No boats has registered for this race.", false),
	
	/**
	 * The positive response indicates that the payment of the fee has been made.
	**/
	SUCCESS_PAY_FEE("The fee has been paid correctly.", true),
	
	/**
	 * The positive response indicates that the payment notification has been sent.
	**/
	SUCCESS_NOTIFY_PAYMENT("Notification to pay the fee has been sent successfully.", true),
	
	/**
	 * The negative response indicates that the payment notification has already been sent previously.
	**/
	ERROR_NOTIFY_PAYMENT("The payment notification has already been sent previously.", false),
	
	/**
	 * The negative response indicates that the member has not paid the membership fee to the club.
	**/
	ERROR_UNPAY_MEMBERSHIP_FEE("Unpaid membership fee.", false),
	
	/**
	 * The negative response indicates that the storage fee for a boat has not been paid.
	**/
	ERROR_UNPAY_STORAGE_FEE("Unpaid storage fee for this boat.", false),
	
	/**
	 * The negative answer indicates that the member has already registered a boat in a race.
	**/
	ERROR_USER_BOATS_PER_RACE("Only one boat per club member can be registered for a race.", false),
	
	/**
	 * The positive response indicates that a new registration to a race has been added.
	**/
	SUCCESS_ADD_RACE_REGISTRATION("The boat has been registered at the race correctly.", true),
	
	/**
	 * The positive response indicates that a registration to a race has been updated.
	**/
	SUCCESS_UPDATE_RACE_REGISTRATION("The registration of the boat at the race has been updated correctly.", true),
	
	/**
	 * The positive response that a fee has been updated.
	**/
	SUCCESS_UPDATE_FEE("The fee has been updated correctly.", true),
	
	/**
	 * The negative response indicates that the payment service is incorrect.
	**/
	ERROR_PAYMENT_SERVICE("The payment service is incorrect.", false),
	
	/**
	 * The negative response indicates an error with the boat.
	**/
	ERROR_BOAT("The boat is incorrect.", false),
	
	/**
	 * The negative response indicates an error with the member.
	**/
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
