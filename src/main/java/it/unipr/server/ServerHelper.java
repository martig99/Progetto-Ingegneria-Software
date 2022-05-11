package main.java.it.unipr.server;

import main.java.it.unipr.message.*;
import main.java.it.unipr.model.*;
import main.java.it.unipr.sql.*;

import java.io.Serializable;
import java.util.*;
import java.util.Date;
import java.util.regex.*;

/**
 * The class {@code ServerHelper} manages the request sent by the client to the server.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class ServerHelper {
	
	private Request request;
	private List<Serializable> object;
	
	private Response response;
	
	private SailingClub club;
	
	/**
	 * Class constructor.
	**/
	public ServerHelper() {
		this.setRequest(new Request());
		this.setObject(new ArrayList<Serializable>());
		this.setResponse(new Response());
		this.setClub(new SailingClub());
	}
	
	/**
	 * Class constructor.
	 * 
	 * @param request the request received by the client.
	**/
	public ServerHelper(final Request request) {
		this.setRequest(request);
		this.setObject(request.getObject());
		this.setResponse(new Response());
		this.setClub(new SailingClub());
	}
	
	/**
	 * Gets the request received by the client.
	 * 
	 * @return the request.
	**/
	public Request getRequest() {
		return this.request;
	}
	
	/**
	 * Sets the request received by the client.
	 * 
	 * @param request the new request.
	**/
	public void setRequest(final Request request) {
		this.request = request;
	}
	
	/**
	 * Gets the object contained in the request.
	 *  
	 * @return the object.
	**/
	public List<Serializable> getObject() {
		return this.object;
	}
	
	
	/**
	 * Sets the object contained in the request.
	 * 
	 * @param object the new object.
	**/
	public void setObject(final List<Serializable> object) {
		this.object = object;
	}
	
	
	/**
	 * Gets the response for the client.
	 * 
	 * @return the response.
	**/
	public Response getResponse() {
		return this.response;
	}
	
	/**
	 * Sets the response for the client.
	 * 
	 * @param response the new response.
	**/
	public void setResponse(final Response response) {
		this.response = response;
	}
	
	/**
	 * Gets the object that initializes DAO classes.
	 * 
	 * @return the object.
	**/
	public SailingClub getClub() {
		return this.club;
	}
	
	/**
	 * Sets the object that initializes DAO classes.
	 * 
	 * @param club the new object.
	**/
	public void setClub(final SailingClub club) {
		this.club = club;
	}
	
	/**
	 * Gets the calendar object given a date and setting the time to zero.
	 * 
	 * @param date the date to be considered.
	 * @return the calendar object.
	**/
	public Calendar getZeroTimeCalendar(final Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar;
	}
	
	/**
	 * Checks if the email matches the correct format.
	 * 
	 * @param email the email address. 
	 * @return <code>true</code> if the email is correct.
	**/
	public boolean emailValidation(final String email) {
		String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
		        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		
		if (!Pattern.compile(regexPattern).matcher(email).matches()) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks if the fiscal code matches the correct format.
	 * 
	 * @param fiscalCode the fiscal code.
	 * @return <code>true</code> if the fiscal code is correct.
	**/
	public boolean fiscalCodeValidation(final String fiscalCode) {
		String regexPattern = "^([A-Z]{6}[0-9LMNPQRSTUV]{2}[ABCDEHLMPRST]{1}[0-9LMNPQRSTUV]{2}[A-Z]{1}[0-9LMNPQRSTUV]{3}[A-Z]{1})$|([0-9]{11})$";
		
		if (!Pattern.compile(regexPattern).matcher(fiscalCode).matches()) {
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * Performs the user's login given all the information in the request object.
	**/
	public void login() {
		User user = (User) this.object.get(0);

		if (!this.emailValidation(user.getEmail())) {			
			this.response.setResponseType(ResponseType.ERROR_INVALID_EMAIL);
			return;
		}
		
		UserType type = (UserType) this.object.get(1);
		
		User loggedUser = this.club.getUserDAO().login(user.getEmail(), user.getPassword(), type);
		if (loggedUser != null) {
			this.response.setObject(loggedUser);
		} else {
			this.response.setResponseType(ResponseType.ERROR_INCORRECT_CREDENTIALS);
		}
	}
	
	/**
	 * Creates a new user given all the information in the request object.
	**/
	public void insertUser() {
		UserType type = (UserType) this.object.get(1);
		
		String fiscalCode = null, address = null;
		boolean admin = false;
		
		User user;
		if (type == UserType.MEMBER) {
			Member member = (Member) this.object.get(0);
			
			fiscalCode = member.getFiscalCode();
			address = member.getAddress();
			
			if (fiscalCode != null && this.club.getUserDAO().getMemberByFiscalCode(fiscalCode) != null) {
				this.response.setResponseType(ResponseType.ERROR_EXIST_FISCAL_CODE);
				return;
			}
			
			if (!this.fiscalCodeValidation(fiscalCode)) {			
				this.response.setResponseType(ResponseType.ERROR_INVALID_FISCAL_CODE);
				return;
			}
			
			user = (User) member;
		} else {
			Employee employee = (Employee) this.object.get(0);
			admin = employee.isAdministrator();
			
			user = (User) employee;
		}
		
		if (!this.emailValidation(user.getEmail())) {			
			this.response.setResponseType(ResponseType.ERROR_INVALID_EMAIL);
			return;
		}
		
		if (this.club.getUserDAO().getUserByEmail(user.getEmail()) != null) {
			this.response.setResponseType(ResponseType.ERROR_EXIST_EMAIL);
			return;
		}
		
		this.club.getUserDAO().createUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), fiscalCode, address, admin, type);
		this.response.setResponseType(ResponseType.SUCCESS_ADD_USER);
	}
	
	/**
	 * Updates a user given all the information in the request object.
	**/
	public void updateUser() {		
		User user = (User) this.object.get(0);
		UserType type = (UserType) this.object.get(1);
		
		User oldUser = this.club.getUserDAO().getUserById(user.getId());
		
		if (!this.emailValidation(user.getEmail())) {			
			this.response.setResponseType(ResponseType.ERROR_INVALID_EMAIL);
			return;
		}
		
		if (!oldUser.getEmail().equalsIgnoreCase(user.getEmail()) && this.club.getUserDAO().getUserByEmail(user.getEmail(), type) != null) {
			this.response.setResponseType(ResponseType.ERROR_EXIST_EMAIL);
			return;
		}
		
		this.club.getUserDAO().updateUser(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());	
		this.response.setResponseType(ResponseType.OK);
	}
	
	/**
	 * Updates a member given all the information in the request object.
	**/
	public void updateMember() {
		Member member = (Member) this.object.get(0);
		Member oldMember = (Member) this.club.getUserDAO().getMemberById(member.getId());
		
		if (member.getFiscalCode() != null && !oldMember.getFiscalCode().equalsIgnoreCase(member.getFiscalCode()) && this.club.getUserDAO().getMemberByFiscalCode(member.getFiscalCode()) != null) {
			this.response.setResponseType(ResponseType.ERROR_EXIST_FISCAL_CODE);
			return;
		}
		
		this.club.getUserDAO().updateMember(member.getId(), member.getFiscalCode(), member.getAddress());
		this.response.setResponseType(ResponseType.SUCCESS_UPDATE_USER);
	}
	
	/**
	 * Updates an employee given all the information in the request object.
	**/
	public void updateEmployee() {
		Employee employee = (Employee) this.object.get(0);
		this.club.getUserDAO().updateEmployee(employee.getId(), employee.isAdministrator());
		this.response.setResponseType(ResponseType.SUCCESS_UPDATE_USER);
	}
	
	/**
	 * Removes an employee given the unique identifier in the request object.
	**/
	public void removeEmployee() {
		int id = (int) this.object.get(0);
		this.club.getUserDAO().removeUser(id);
		this.response.setResponseType(ResponseType.SUCCESS_REMOVE_EMPLOYEE);
	}
	
	/**
	 * Gets a user given the email in the request object.
	**/
	public void getUserByEmail() {
		String email = (String) this.object.get(0);
		UserType type = (UserType) this.object.get(1);
		User user = this.club.getUserDAO().getUserByEmail(email, type);
		this.response.setObject(user);
	}
	
	/**
	 * Gets the list of all users given the type of user in the request object.
	**/
	public void getAllUsers() {
		UserType userType = (UserType) this.object.get(0);
		ArrayList<User> list = this.club.getUserDAO().getAllUsers(userType);
		this.response.setObject(list);
	}
	
	/**
	 * Gets the list of all member emails.
	**/
	public void getAllMemberEmails() {
		ArrayList<String> list = this.club.getUserDAO().getAllMemberEmails();
		this.response.setObject(list);
	}

	/**
	 * Gets the list of all the boats owner by a user given the reference of a member in the request object.
	**/
	public void getAllBoats() {
		User user = (User) this.object.get(0);
		ArrayList<Boat> list = this.club.getBoatDAO().getAllBoats(user);
		this.response.setObject(list);
	}
	
	/**
	 * Inserts a new boat given all the information in the request object.
	**/
	public void insertBoat() {
		Boat boat = (Boat) this.object.get(0);
		String emailMember = (String) this.object.get(1);
		
		Member member = (Member) this.club.getUserDAO().getUserByEmail(emailMember, UserType.MEMBER);
		if (member == null) {
			this.response.setResponseType(ResponseType.ERROR_MEMBER);
			return;
		}
				
		Boat existingBoat = this.club.getBoatDAO().getBoatByName(boat.getName(), member);
		if (existingBoat != null) {
			this.response.setResponseType(ResponseType.ERROR_BOAT_NAME);
			return;
		}
		
		boolean membershipFeePaid = this.club.getPaymentDAO().checkPaymentMembershipFee(member);
		if(!membershipFeePaid) {
			this.response.setResponseType(ResponseType.ERROR_UNPAY_MEMBERSHIP_FEE);
			return;
		}
		
		this.club.getBoatDAO().insertBoat(boat.getName(), boat.getLength(), member);
		this.response.setResponseType(ResponseType.SUCCESS_ADD_BOAT);
	}
	
	/**
	 * Updates a boat given all the information in the request object.
	**/
	public void updateBoat() {
		Boat boat = (Boat) this.object.get(0);
		
		Boat oldBoat = this.club.getBoatDAO().getBoatById(boat.getId());
		if (this.club.getBoatDAO().getBoatByName(boat.getName(), oldBoat.getOwner()) != null) {
			this.response.setResponseType(ResponseType.ERROR_BOAT_NAME);
			return;
		}
		
		if(!this.club.getPaymentDAO().checkPaymentMembershipFee(oldBoat.getOwner())) {
			this.response.setResponseType(ResponseType.ERROR_UNPAY_MEMBERSHIP_FEE);
			return;
		}
		
		this.club.getBoatDAO().updateBoat(boat.getId(), boat.getName(), boat.getLength());
		this.response.setResponseType(ResponseType.SUCCESS_UPDATE_BOAT);
	}
	
	/**
	 * Removes a boat given the unique identifier in the request object.
	**/
	public void removeBoat() {
		Date today = this.getZeroTimeCalendar(new Date()).getTime();
		
		int idBoat = (int) this.object.get(0);
		ArrayList<RaceRegistration> list = this.club.getRaceRegistrationDAO().getAllRegistrationByBoat(idBoat);
		
		for (RaceRegistration registration: list) {
    		if (registration.getRace().getDate().after(today)) {
        		this.club.getRaceRegistrationDAO().removeRaceRegistration(registration.getId());
    			this.club.getPaymentDAO().refundRegistrationFee(registration.getId());
        	}
		}
		
		this.club.getBoatDAO().removeBoat(idBoat);
		this.response.setResponseType(ResponseType.SUCCESS_REMOVE_BOAT);
	}
	
	/**
	 * Gets the list of all the names of the boats owned by a user given the reference of a member in the request object.
	**/
	public void getAllNameBoatsByOwner() {
		User owner = (User) this.object.get(0);
		ArrayList<String> list = this.club.getBoatDAO().getAllNameBoatsByOwner(owner);
		this.response.setObject(list);
	}
	
	/**
	 * Gets the payment list given the reference of the user and the type of fee in the request object.
	**/
	public void getAllPayments() {
		User user = (User) this.object.get(0);
		FeeType type = (FeeType) this.object.get(1);		
		ArrayList<Payment> list = this.club.getPaymentDAO().getAllPayments(user, type);
		this.response.setObject(list);
	}
	
	/**
	 * Gets the list of all descriptions of payment services.
	**/
	public void getAllPaymentServicesDescription() {
		ArrayList<String> list = this.club.getPaymentDAO().getAllPaymentServicesDescription();
		this.response.setObject(list);
	}
	
	/**
	 * Gets the payment service given the description in the request object.
	**/
	public void getPaymentServiceByDescription() {
		String description = (String) this.object.get(0);
		PaymentService paymentService = this.club.getPaymentDAO().getPaymentServiceByDescription(description);
		this.response.setObject(paymentService);
	}
	
	/**
	 * Performs the notification of a payment given all the information in the request object.
	**/
	public void notifyPayment() {
		String emailMember = (String) this.object.get(0);
		FeeType feeType = (FeeType) this.object.get(1);
		String nameBoat = (String) this.object.get(2);
		
		Member member = (Member) this.club.getUserDAO().getUserByEmail(emailMember, UserType.MEMBER);
		if (member == null) {
			this.response.setResponseType(ResponseType.ERROR_MEMBER);
			return;
		}
		
		Boat boat = null;
		if (feeType == FeeType.STORAGE) {
			boat = this.club.getBoatDAO().getBoatByName(nameBoat, member);
			
			if (boat == null) {
				this.response.setResponseType(ResponseType.ERROR_BOAT);
				return;
			}
		}
		
		if ((feeType == FeeType.MEMBERSHIP && this.club.getNotificationDAO().existNotificationMembershipFee(member)) ||
				(feeType == FeeType.STORAGE && this.club.getNotificationDAO().existNotificationStorageFee(member, boat))) {
			this.response.setResponseType(ResponseType.ERROR_NOTIFY_PAYMENT);
			return;
		}
		
		Fee fee = this.club.getFeeDAO().getFeeByType(feeType);
		
		this.club.getNotificationDAO().insertNotification(member, boat, fee);
		this.response.setResponseType(ResponseType.SUCCESS_NOTIFY_PAYMENT);
	}
	
	/**
	 * Gets the list of all notifications given the reference of the user in the request object.
	**/
	public void getAllNotifications() {
		User user = (User) this.object.get(0);
		ArrayList<Notification> list = this.club.getNotificationDAO().getAllNotifications(user);
		this.response.setObject(list);
	}
	
	/**
	 * Gets the fee given the type of fee in the request object.
	**/
	public void getFeeByType() {
		FeeType type = (FeeType) this.object.get(0);
		Fee fee = this.club.getFeeDAO().getFeeByType(type);
		this.response.setObject(fee);
	}
	
	/**
	 * Performs the payment of the fee given all the information in the request object.
	**/
	public void payFee() {
		Date today = this.getZeroTimeCalendar(new Date()).getTime();
		
		String emailMember = (String) this.object.get(0);
		String nameBoat = (String) this.object.get(1);
		String descriptionPaymentService = (String) this.object.get(2);
		FeeType feeType = (FeeType) this.object.get(3);
		
		Member member = (Member) this.club.getUserDAO().getUserByEmail(emailMember, UserType.MEMBER);
		if (member == null) {
			this.response.setResponseType(ResponseType.ERROR_MEMBER);
			return;
		}
				
		Boat boat = null;
		if (feeType == FeeType.STORAGE) {
			boat = this.club.getBoatDAO().getBoatByName(nameBoat, member);
			
			if (boat == null) {
				this.response.setResponseType(ResponseType.ERROR_BOAT);
				return;
			}
		}
		
		Fee fee = this.club.getFeeDAO().getFeeByType(feeType);
		
		PaymentService paymentService = this.club.getPaymentDAO().getPaymentServiceByDescription(descriptionPaymentService);
		if (paymentService == null) {
			this.response.setResponseType(ResponseType.ERROR_PAYMENT_SERVICE);
			return;
		}
		
		Date lastPayment = this.club.getPaymentDAO().getLastPaymentFee(member, boat, feeType);
		boolean atLeastOneBoat = (this.club.getBoatDAO().getAllBoats(member).size() == 0) ? false : true;
		
		if (lastPayment == null || today.before(lastPayment) || today.equals(lastPayment) || !atLeastOneBoat) {
			this.club.getPaymentDAO().payFee(member, boat, null, fee, paymentService, atLeastOneBoat, false);
		} else {
			if (atLeastOneBoat) {
				long diffTime = today.getTime() - lastPayment.getTime();
				long diffDays = diffTime/(1000 * 60 * 60 * 24);
				int period = (int) Math.ceil((float) diffDays/this.club.getFeeDAO().getFeeByType(fee.getType()).getValidityPeriod());
				
				for (int i = 0; i < period; i++) {
					this.club.getPaymentDAO().payFee(member, boat, null, fee, paymentService, atLeastOneBoat, false);
				}
			}
		}
		
		this.club.getNotificationDAO().updateStatusCodeNotification(member, boat, fee);
		this.response.setResponseType(ResponseType.SUCCESS_PAY_FEE);
	}

	/**
	 * Gets the list of all races.
	**/
	public void getAllRaces() {
		ArrayList<Race> list = this.club.getRaceDAO().getAllRaces();
		this.response.setObject(list);
	}
	
    private boolean checkOpenRegistration(final Date date) {
    	Date today = this.getZeroTimeCalendar(new Date()).getTime();
    	
		if (date != null && date.before(today)) {
			this.response.setResponseType(ResponseType.ERROR_CLOSE_REGISTRATION);
    		return false; 
		}
		
		return true;
    }
    
	private boolean checkDateRace(final Date dateRace) {
		Date today = this.getZeroTimeCalendar(new Date()).getTime();
		
		if (dateRace != null) {
			if (dateRace.before(today) || dateRace.equals(today)) {
				this.response.setResponseType(ResponseType.ERROR_RACE_DATE);
				return false;
			}
			
			if (this.club.getRaceDAO().getRaceByDate(dateRace) != null) {
				this.response.setResponseType(ResponseType.ERROR_EXIST_RACE_DATE);
				return false;
			}
		}
		
		return true;
	}
	
	private boolean checkEndDateRegistration(final int idRace, final Date dateRace, final Date endDateRegistration) {
		Date today = this.getZeroTimeCalendar(new Date()).getTime();
		
		Boolean errorDate = false;
		if ((endDateRegistration != null && endDateRegistration.before(today)) || ((idRace == 0 || (dateRace != null && endDateRegistration != null)) && !dateRace.after(endDateRegistration))) {
			errorDate = true;
		}
		
		if (idRace != 0) {
			Race race = this.club.getRaceDAO().getRaceById(idRace);
			if (race != null) {
				if ((dateRace != null && endDateRegistration == null && !dateRace.after(race.getEndDateRegistration())) || (dateRace == null && endDateRegistration != null && !race.getDate().after(endDateRegistration))) {
					errorDate = true;
				}
			}
		}
		
		if (errorDate) {
			this.response.setResponseType(ResponseType.ERROR_END_REGISTRATION_DATE);
			return false;
		}
		
		return true;
	}
	
	private boolean checkBoatsNumber(final int boatsNumber) {
		int totBoats = this.club.getBoatDAO().getMaxBoatsNumber();
		if (totBoats <= 1) {
			this.response.setResponseType(ResponseType.ERROR_NO_BOATS);
			return false;
		}
		
		if (boatsNumber != 0 && (boatsNumber <= 1 || boatsNumber > totBoats)) {	
			this.response.setResponseType(ResponseType.ERROR_NUMBER_BOATS);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Inserts a new race given all the information in the request object.
	**/
	public void insertRace() {
		Race race = (Race) this.object.get(0);

		if (!this.checkBoatsNumber(race.getBoatsNumber()))
			return;
		
		if (!this.checkDateRace(race.getDate()))
			return;
		
		if (!this.checkEndDateRegistration(race.getId(), race.getDate(), race.getEndDateRegistration()))
			return;
		
		this.club.getRaceDAO().insertRace(race.getName(), race.getPlace(), race.getDate(), race.getBoatsNumber(), race.getRegistrationFee(), race.getEndDateRegistration());
		this.response.setResponseType(ResponseType.SUCCESS_ADD_RACE);
	}
	
	/**
	 * Updates a race given all the information in the request object.
	**/
	public void updateRace() {
		Race race = (Race) this.object.get(0);
		Race oldRace = this.club.getRaceDAO().getRaceById(race.getId());
		
		if (!this.checkOpenRegistration(oldRace.getEndDateRegistration()))
			return;
		
		if (!this.checkBoatsNumber(race.getBoatsNumber()))
			return;
		
		boolean registrations = this.club.getRaceRegistrationDAO().existRegistrationsForRace(oldRace.getId());
		if (race.getBoatsNumber() != oldRace.getBoatsNumber() && registrations) {
			this.response.setResponseType(ResponseType.ERROR_ONE_RACE_REGISTRATION);
			return;
		}
		
		if (!this.checkDateRace(race.getDate()))
			return;
		
		if (!this.checkEndDateRegistration(race.getId(), race.getDate(), race.getEndDateRegistration()))
			return;
		
		this.club.getRaceDAO().updateRace(race.getId(), race.getName(), race.getPlace(), race.getDate(), race.getBoatsNumber(), race.getRegistrationFee(), race.getEndDateRegistration());
		this.response.setResponseType(ResponseType.SUCCESS_UPDATE_RACE);
	}
	
	/**
	 * Removes a race given the unique identifier in the request object.
	**/
	public void removeRace() {
		int idRace = (int) this.object.get(0);
		
		Race race = this.club.getRaceDAO().getRaceById(idRace);
		if (!this.checkOpenRegistration(race.getEndDateRegistration()))
			return;

		ArrayList<RaceRegistration> list = this.club.getRaceRegistrationDAO().getAllRegistrationsByRace(idRace);
		for (RaceRegistration registration: list) {
			this.club.getPaymentDAO().refundRegistrationFee(registration.getId());
			this.club.getRaceRegistrationDAO().removeRaceRegistration(registration.getId());
		}
				
		this.club.getRaceDAO().removeRace(idRace);
		this.response.setResponseType(ResponseType.SUCCESS_REMOVE_RACE);
	}
	
	/**
	 * Gets the list of all registrations to the race given the unique identifier of the race in the request object.
	**/
	public void getAllRegistrationByRace() {
		int idRace = (int) this.object.get(0);
		ArrayList<RaceRegistration> list = this.club.getRaceRegistrationDAO().getAllRegistrationsByRace(idRace);
		this.response.setObject(list);
	}

	/**
	 * Checks if there are registrations to the race given the unique identifier of the race saved in the request object.
	**/
	public void existRegistrationsForRace() {
		int idRace = (int) this.object.get(0);
		boolean result = this.club.getRaceRegistrationDAO().existRegistrationsForRace(idRace);
		
		if (result) {
			this.response.setResponseType(ResponseType.OK);
		} else {
			this.response.setResponseType(ResponseType.ERROR_NO_REGISTRATIONS);
		}
	}
	
	private boolean checkPaymentsByRaceRegistration(final Member member, final Boat boat, final Race race) {
		if(!this.club.getPaymentDAO().checkPaymentMembershipFee(member)) {
			this.response.setResponseType(ResponseType.ERROR_UNPAY_MEMBERSHIP_FEE);
    		return false;
		}
		
		Date lastPayment = this.club.getPaymentDAO().getLastPaymentFee(member, boat, FeeType.STORAGE);
		if (!this.club.getPaymentDAO().checkPaymentStorageFee(boat) || lastPayment.before(race.getDate()) || lastPayment.equals(race.getDate())) {
			this.response.setResponseType(ResponseType.ERROR_UNPAY_STORAGE_FEE);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Inserts a new registration to the race given all the information in the request object.
	**/
	public void insertRaceRegistration() {
		String emailMember = (String) this.object.get(0);
		String nameBoat = (String) this.object.get(1);
		String descriptionPaymentService = (String) this.object.get(2);
		Race race = (Race) this.object.get(3);
		
		Member member = (Member) this.club.getUserDAO().getUserByEmail(emailMember, UserType.MEMBER);
		if (member == null) {
			this.response.setResponseType(ResponseType.ERROR_MEMBER);
			return;
		}
		
		Boat boat = this.club.getBoatDAO().getBoatByName(nameBoat, member);
		if (boat == null) {
			this.response.setResponseType(ResponseType.ERROR_BOAT);
			return;
		}
		
		boolean openRegistration = this.checkOpenRegistration(race.getEndDateRegistration());
		if (!openRegistration)
			return;
		
		int participants = this.club.getRaceRegistrationDAO().getNumberBoatsInRace(race.getId()) + 1;
		if (participants > race.getBoatsNumber()) {
			this.response.setResponseType(ResponseType.ERROR_MAX_NUMBER_BOATS);
			return;
		}

		boolean boatInRace = this.club.getRaceRegistrationDAO().checkBoatInRaceByMember(member.getId(), race.getId());
		if (boatInRace) {
			this.response.setResponseType(ResponseType.ERROR_USER_BOATS_PER_RACE);
			return;
		}
		
		boolean regularPayments = this.checkPaymentsByRaceRegistration(member, boat, race);
		if (!regularPayments)
			return;	
		
		PaymentService paymentService = this.club.getPaymentDAO().getPaymentServiceByDescription(descriptionPaymentService);
		if (paymentService == null) {
			this.response.setResponseType(ResponseType.ERROR_PAYMENT_SERVICE);
			return;
		}
	
		this.club.getRaceRegistrationDAO().registerBoatAtRace(race.getId(), boat.getId());
		RaceRegistration newRegistration = this.club.getRaceRegistrationDAO().getRaceRegistration(race.getId(), boat.getId());
		if (newRegistration != null) {
			Fee fee = this.club.getFeeDAO().getFeeByType(FeeType.RACE_REGISTRATION);	
			this.club.getPaymentDAO().payFee(member, boat, newRegistration, fee, paymentService, false, false);
		}
		
		this.response.setResponseType(ResponseType.SUCCESS_ADD_RACE_REGISTRATION);
	}
	
	/**
	 * Updates a registration to the race given all the information in the request object.
	**/
	public void updateRaceRegistration() {
		int id = (int) this.object.get(0);
		String emailMember = (String) this.object.get(1);
		String nameBoat = (String) this.object.get(2);
		Race race = (Race) this.object.get(3);
		
		Member member = (Member) this.club.getUserDAO().getUserByEmail(emailMember, UserType.MEMBER);
		if (member == null) {
			this.response.setResponseType(ResponseType.ERROR_MEMBER);
			return;
		}
		
		Boat boat = this.club.getBoatDAO().getBoatByName(nameBoat, member);
		if (boat == null) {
			this.response.setResponseType(ResponseType.ERROR_BOAT);
			return;
		}
		
		if (!this.checkOpenRegistration(race.getEndDateRegistration()))
			return;
		
		if (!this.checkPaymentsByRaceRegistration(member, boat, race))
			return;
		
		this.club.getRaceRegistrationDAO().updateRaceRegistration(id, boat.getId());
		this.response.setResponseType(ResponseType.SUCCESS_UPDATE_RACE_REGISTRATION);
	}
	
	/**
	 * Removes a registration to the race given the unique identifier in the request object.
	**/
	public void removeRaceRegistration() {
		int idRegistration = (int) this.object.get(0);
		
		RaceRegistration registration = this.club.getRaceRegistrationDAO().getRaceRegistrationById(idRegistration);
		Race race = registration.getRace();		
		if (!this.checkOpenRegistration(race.getEndDateRegistration()))
			return;
		
		this.club.getRaceRegistrationDAO().removeRaceRegistration(idRegistration);
		this.club.getPaymentDAO().refundRegistrationFee(idRegistration);
		this.response.setResponseType(ResponseType.SUCCESS_REMOVE_REGISTRATION);
	}
	
	/**
	 * Gets the list of all fees.
	**/
	public void getAllFees() {
		ArrayList<Fee> list = this.club.getFeeDAO().getAllFees();
		this.response.setObject(list);
	}
	
	/**
	 * Updates a fee given all the information in the request object. 
	**/
	public void updateFee() {
		Fee fee = (Fee) this.object.get(0);
		this.club.getFeeDAO().updateFee(fee.getId(), fee.getType(), fee.getAmount(), fee.getValidityPeriod());
		this.response.setResponseType(ResponseType.SUCCESS_UPDATE_FEE);
	}
	
	/**
	 * Processes the type of request and invokes the corresponding method.
	 * 
	 * @return the response object.
	**/
	public Response processRequest() {		
		switch(this.request.getRequestType()) {
			case LOGIN_USER:				
				this.login();
				break;
			case INSERT_USER:
				this.insertUser();
				break;
			case UPDATE_USER:
				this.updateUser();
				break;
			case UPDATE_MEMBER:
				this.updateMember();
				break;
			case UPDATE_EMPLOYEE:
				this.updateEmployee();
				break;
			case REMOVE_USER:
				this.removeEmployee();
				break;
			case GET_USER_BY_EMAIL:
				this.getUserByEmail();
				break;
			case GET_ALL_USERS:
				this.getAllUsers();
				break;
			case GET_ALL_MEMBERS_EMAIL:
				this.getAllMemberEmails();
				break;
			case GET_ALL_BOATS:
				this.getAllBoats();
				break;
			case INSERT_BOAT:
				this.insertBoat();
				break;
			case UPDATE_BOAT:
				this.updateBoat();
				break;
			case REMOVE_BOAT:
				this.removeBoat();
				break;
			case GET_ALL_NAME_BOATS_BY_OWNER:
				this.getAllNameBoatsByOwner();
				break;
			case GET_ALL_PAYMENTS:
				this.getAllPayments();
				break;
			case GET_ALL_PAYMENT_SERVICES_DESCRIPTION:
				this.getAllPaymentServicesDescription();
				break;
			case GET_PAYMENT_SERVICE_BY_DESCRIPTION:
				this.getPaymentServiceByDescription();
				break;
			case NOTIFY_PAYMENT:
				this.notifyPayment();
				break;
			case GET_ALL_NOTIFICATIONS:
				this.getAllNotifications();
				break;
			case GET_FEE_BY_TYPE:
				this.getFeeByType();
				break;
			case PAY_FEE:
				this.payFee();
				break;
			case GET_ALL_RACES:
				this.getAllRaces();
				break;
			case INSERT_RACE:
				this.insertRace();
				break;				
			case UPDATE_RACE:
				this.updateRace();
				break;
			case REMOVE_RACE:
				this.removeRace();
				break;
			case GET_ALL_REGISTRATIONS_BY_RACE:
				this.getAllRegistrationByRace();
				break;
			case EXIST_REGISTRATIONS_FOR_RACE:
				this.existRegistrationsForRace();
				break;
			case INSERT_RACE_REGISTRATION:
				this.insertRaceRegistration();
				break;
			case REMOVE_RACE_REGISTRATION:
				this.removeRaceRegistration();
				break;
			case UPDATE_RACE_REGISTRATION:
				this.updateRaceRegistration();
				break;
			case GET_ALL_FEES:
				this.getAllFees();
				break;
			case UPDATE_FEE:
				this.updateFee();
				break;
			default:
				break;
		}
		
		return this.response;
	}
}
