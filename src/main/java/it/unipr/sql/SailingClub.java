package main.java.it.unipr.sql;

/**
 * The class {@code SailingClub} describes a sailing club and its properties.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
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
	 * 
	 * @return
	**/
	public UserDAO getUserDAO() {
		return this.userDAO;
	}
	
	/**
	 * 
	 * @param userDAO
	**/
	public void setUserDAO(final UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	/**
	 * 
	 * @return
	**/
	public BoatDAO getBoatDAO() {
		return this.boatDAO;
	}
	
	/**
	 * 
	 * @param boatDAO
	**/
	public void setBoatDAO(final BoatDAO boatDAO) {
		this.boatDAO = boatDAO;
	}
	
	/**
	 * 
	 * @return
	**/
	public FeeDAO getFeeDAO() {
		return this.feeDAO;
	}
	
	/**
	 * 
	 * @param feeDAO
	**/
	public void setFeeDAO(final FeeDAO feeDAO) {
		this.feeDAO = feeDAO;
	}
	
	/**
	 * 
	 * @return
	**/
	public PaymentDAO getPaymentDAO() {
		return this.paymentDAO;
	}
	
	/**
	 * 
	 * @param paymentDAO
	**/
	public void setPaymentDAO(final PaymentDAO paymentDAO) {
		this.paymentDAO = paymentDAO;
	}
	
	/**
	 * 
	 * @return
	**/
	public RaceDAO getRaceDAO() {
		return this.raceDAO;
	}
	
	/**
	 * 
	 * @param raceDAO
	**/
	public void setRaceDAO(final RaceDAO raceDAO) {
		this.raceDAO = raceDAO;
	}
	
	/**
	 * 
	 * @return
	**/
	public RaceRegistrationDAO getRaceRegistrationDAO() {
		return this.raceRegistrationDAO;
	}
	
	/**
	 * 
	 * @param raceRegistrationDAO
	**/
	public void setRaceRegistrationDAO(final RaceRegistrationDAO raceRegistrationDAO) {
		this.raceRegistrationDAO = raceRegistrationDAO;
	}
	
	/**
	 * 
	 * @return
	**/
	public NotificationDAO getNotificationDAO() {
		return this.notificationDAO;
	}
	
	/**
	 * 
	 * @param notificationDAO
	**/
	public void setNotificationDAO(final NotificationDAO notificationDAO) {
		this.notificationDAO = notificationDAO;
	}
}
