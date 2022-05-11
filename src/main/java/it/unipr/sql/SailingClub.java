package main.java.it.unipr.sql;

/**
 * The class {@code SailingClub} defines a model for the implementation of DAO classes.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class SailingClub {
	
	private UserDAO userDAO;
	private BoatDAO boatDAO;
	private FeeDAO feeDAO;
	private PaymentDAO paymentDAO;
	private RaceDAO raceDAO;
	private RaceRegistrationDAO raceRegistrationDAO;
	private NotificationDAO notificationDAO;
	
	/**
	 * Class constructor.
	**/
	public SailingClub() {
		this.setUserDAO(new UserDAO());
		this.setBoatDAO(new BoatDAO());
		this.setFeeDAO(new FeeDAO());
		this.setPaymentDAO(new PaymentDAO());
		this.setRaceDAO(new RaceDAO());
		this.setRaceRegistrationDAO(new RaceRegistrationDAO());
		this.setNotificationDAO(new NotificationDAO());
	}
	
	/**
	 * Gets the data access object used to manage users in the database.
	 * 
	 * @return the reference of the object.
	**/
	public UserDAO getUserDAO() {
		return this.userDAO;
	}
	
	/**
	 * Sets the data access object used to manage users in the database.
	 * 
	 * @param userDAO the new object.
	**/
	public void setUserDAO(final UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	/**
	 * Gets the data access object used to manage boats in the database.
	 * 
	 * @return the reference of the object.
	**/
	public BoatDAO getBoatDAO() {
		return this.boatDAO;
	}
	
	/**
	 * Sets the data access object used to manage boats in the database.
	 * 
	 * @param boatDAO the new object.
	**/
	public void setBoatDAO(final BoatDAO boatDAO) {
		this.boatDAO = boatDAO;
	}
	
	/**
	 * Gets the data access object used to manage fees in the database.
	 * 
	 * @return the reference of the object.
	**/
	public FeeDAO getFeeDAO() {
		return this.feeDAO;
	}
	
	/**
	 * Sets the data access object used to manage fees in the database.
	 * 
	 * @param feeDAO the new object.
	**/
	public void setFeeDAO(final FeeDAO feeDAO) {
		this.feeDAO = feeDAO;
	}
	
	/**
	 * Gets the data access object used to manage payments in the database.
	 * 
	 * @return the reference of the object.
	**/
	public PaymentDAO getPaymentDAO() {
		return this.paymentDAO;
	}
	
	/**
	 * Sets the data access object used to manage payments in the database.
	 * 
	 * @param paymentDAO the new object.
	**/
	public void setPaymentDAO(final PaymentDAO paymentDAO) {
		this.paymentDAO = paymentDAO;
	}
	
	/**
	 * Gets the data access object used to manage races in the database.
	 * 
	 * @return the reference of the object.
	**/
	public RaceDAO getRaceDAO() {
		return this.raceDAO;
	}
	
	/**
	 * Sets the data access object used to manage races in the database.
	 * 
	 * @param raceDAO the new object.
	**/
	public void setRaceDAO(final RaceDAO raceDAO) {
		this.raceDAO = raceDAO;
	}
	
	/**
	 * Gets the data access object used to manage race registrations in the database.
	 * 
	 * @return the reference of the object.
	**/
	public RaceRegistrationDAO getRaceRegistrationDAO() {
		return this.raceRegistrationDAO;
	}
	
	/**
	 * Sets the data access object used to manage race registrations in the database.
	 * 
	 * @param raceRegistrationDAO the new object.
	**/
	public void setRaceRegistrationDAO(final RaceRegistrationDAO raceRegistrationDAO) {
		this.raceRegistrationDAO = raceRegistrationDAO;
	}
	
	/**
	 * Gets the data access object used to manage notifications in the database.
	 * 
	 * @return the reference of the object.
	**/
	public NotificationDAO getNotificationDAO() {
		return this.notificationDAO;
	}
	
	/**
	 * Sets the data access object used to manage notifications in the database.
	 * 
	 * @param notificationDAO the new object.
	**/
	public void setNotificationDAO(final NotificationDAO notificationDAO) {
		this.notificationDAO = notificationDAO;
	}
}
