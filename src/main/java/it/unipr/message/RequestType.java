package main.java.it.unipr.message;

/**
 * The enum {@code RequestType} defines the type of the request message.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public enum RequestType {
	
	/**
	 * The login request.
	**/
	LOGIN_USER,
	
	/**
	 * The request to insert a user.
	**/
	INSERT_USER,
	
	/**
	 * The request to update a user.
	**/
	UPDATE_USER,
	
	/**
	 * The request to update a member.
	**/
	UPDATE_MEMBER,
	
	/**
	 * The request to update an employee.
	**/
	UPDATE_EMPLOYEE,
	
	/**
	 * The request to remove a user.
	**/
	REMOVE_EMPLOYEE,
	
	/**
	 * The request to get a user given the email.
	**/
	GET_USER_BY_EMAIL,
	
	/**
	 * The request to get all users.
	**/
	GET_ALL_USERS,
	
	/**
	 * The request to get all email addresses of members.
	**/
	GET_ALL_MEMBERS_EMAIL,
	
	/**
	 * The request to get all boats.
	**/
	GET_ALL_BOATS,
	
	/**
	 * The request to insert a boat.
	**/
	INSERT_BOAT,
	
	/**
	 * The request to update a boat.
	**/
	UPDATE_BOAT,
	
	/**
	 * The request to remove a boat.
	**/
	REMOVE_BOAT,
	
	/**
	 * The request to get all the names of the boats of a member.
	**/
	GET_ALL_NAME_BOATS_BY_OWNER,
	
	/**
	 * The request to get all races.
	**/
	GET_ALL_RACES,
	
	/**
	 * The request to insert a race.
	**/
	INSERT_RACE,
	
	/**
	 * The request to update a race.
	**/
	UPDATE_RACE,
	
	/**
	 * The request to remove a race.
	**/
	REMOVE_RACE,
	
	/**
	 * The request to get all the registrations to a race.
	**/
	GET_ALL_REGISTRATIONS_BY_RACE,
	
	
	/**
	 * The request to check if there are registrations for a race
	**/
	EXIST_REGISTRATIONS_FOR_RACE,
	
	/**
	 * The request to insert a registration to a race.
	**/
	INSERT_RACE_REGISTRATION,
	
	/**
	 * The request to update a registration to a race.
	**/
	UPDATE_RACE_REGISTRATION,
	
	/**
	 * The request to remove a registration to a race.
	**/
	REMOVE_RACE_REGISTRATION,
	
	/**
	 * The request to get all payments.
	**/
	GET_ALL_PAYMENTS,
	
	/**
	 * The request to get all the descriptions of the payment services.
	**/
	GET_ALL_PAYMENT_SERVICES_DESCRIPTION,
	
	/**
	 * The request to get the payment service given the description.
	**/
	GET_PAYMENT_SERVICE_BY_DESCRIPTION,
	
	/**
	 * The request to notify a payment.
	**/
	NOTIFY_PAYMENT,
	
	/**
	 * The request to get all notifications.
	**/
	GET_ALL_NOTIFICATIONS,
	
	/**
	 * The request to get a fee given the type.
	**/
	GET_FEE_BY_TYPE,
	
	/**
	 * The request to pay a fee.
	**/
	PAY_FEE,
	
	/**
	 * The request to get all fees.
	**/
	GET_ALL_FEES,
	
	/**
	 * The request to update a fee.
	**/
	UPDATE_FEE
}
