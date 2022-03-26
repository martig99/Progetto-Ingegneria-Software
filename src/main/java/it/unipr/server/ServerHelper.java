package main.java.it.unipr.server;

import main.java.it.unipr.message.*;
import main.java.it.unipr.model.*;
import main.java.it.unipr.sql.*;

import java.util.*;
import java.util.Date;
import java.util.regex.*;

public class ServerHelper {
	
	private Request request;
	private Response response;
	private SailingClub club;
	private Date today;
	
	/**
	 * 
	**/
	public ServerHelper() {
		this.setRequest(new Request());
		this.setResponse(new Response());
		this.setClub(new SailingClub());
		this.setToday();
	}
	
	/**
	 * 
	 * @param request
	**/
	public ServerHelper(final Request request) {
		this.setRequest(request);
		this.setResponse(new Response());
		this.setClub(new SailingClub());
		this.setToday();
	}
	
	/**
	 * 
	 * @return
	**/
	public Request getRequest() {
		return this.request;
	}
	
	/**
	 * 
	 * @param request
	**/
	public void setRequest(final Request request) {
		this.request = request;
	}
	
	/**
	 * 
	 * @return
	**/
	public Response getResponse() {
		return this.response;
	}
	
	/**
	 * 
	 * @param response
	**/
	public void setResponse(final Response response) {
		this.response = response;
	}
	
	/**
	 * 
	 * @return
	**/
	public SailingClub getClub() {
		return this.club;
	}
	
	/**
	 * 
	 * @param club
	**/
	public void setClub(final SailingClub club) {
		this.club = club;
	}
	
	public Date getToday() {
		return this.today;
	}
	
	public void setToday() {
		this.today = this.getZeroTimeCalendar(new Date()).getTime();
	}
	
	/**
	 * 
	 * @param date
	 * @return
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
	 * @param email the email to check. 
	 * @return <code>true</code> if email is correct.
	**/
	public synchronized ResponseType emailValidation(final String email) {
		String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
		        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		
		if (!Pattern.compile(regexPattern).matcher(email).matches()) {
			return ResponseType.ERROR_INVALID_EMAIL;
		}
		
		return ResponseType.OK;
	}
	
	/**
	 * 
	 * @param email
	 * @return
	**/
	public boolean validEmail(final String email) {
		ResponseType emailResponse = this.emailValidation(email);
		if (emailResponse == ResponseType.OK) {
			return true;
		}
		
		this.response.setResponseType(emailResponse);
		return false;
	}
	
	/**
	 * 
	**/
	public void login() {
		User user = (User) this.request.getPrimaryObject();

		if (!this.validEmail(user.getEmail())) {
			return;
		}
		
		UserType type = (UserType) this.request.getSecondaryObject();
		
		User loggedUser = this.club.getUserDAO().login(user.getEmail(), user.getPassword(), type);
		if (loggedUser != null) {
			this.response.setObject(loggedUser);
		} else {
			this.response.setResponseType(ResponseType.ERROR_INCORRECT_CREDENTIALS);
		}
	}
	
	/**
	 * 
	**/
	public void insertUser() {
		UserType type = (UserType) this.request.getSecondaryObject();
		
		String fiscalCode = null, address = null;
		boolean admin = false;
		
		User user;
		if (type == UserType.MEMBER) {
			Member member = (Member) this.request.getPrimaryObject();
			if (member.getFiscalCode() != null && this.club.getUserDAO().getMemberByFiscalCode(member.getFiscalCode()) != null) {
				this.response.setResponseType(ResponseType.ERROR_EXIST_FISCAL_CODE);
				return;
			}
			
			fiscalCode = member.getFiscalCode();
			address = member.getAddress();
			
			user = (User) member;
		} else {
			Employee employee = (Employee) this.request.getPrimaryObject();
			admin = employee.isAdministrator();
			
			user = (User) employee;
		}
		
		if (!this.validEmail(user.getEmail()))
			return;
		
		if (this.club.getUserDAO().getUserByEmail(user.getEmail()) != null) {
			this.response.setResponseType(ResponseType.ERROR_EXIST_EMAIL);
			return;
		}
		
		this.club.getUserDAO().createUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), fiscalCode, address, admin, type);
		this.response.setResponseType(ResponseType.SUCCESS_ADD_USER);
	}
	
	/**
	 * 
	**/
	public void updateUser() {
		User user = (User) this.request.getPrimaryObject();
		User oldUser = this.club.getUserDAO().getUserById(user.getId());
		
		if (!this.validEmail(user.getEmail()))
			return;
		
		if (!oldUser.getEmail().equalsIgnoreCase(user.getEmail()) && this.club.getUserDAO().getUserByEmail(user.getEmail()) != null) {
			this.response.setResponseType(ResponseType.ERROR_EXIST_EMAIL);
			return;
		}
		
		this.club.getUserDAO().updateUser(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());	
		this.response.setResponseType(ResponseType.OK);
	}
	
	/**
	 * 
	**/
	public void updateMember() {
		Member member = (Member) this.request.getPrimaryObject();
		Member oldMember = (Member) this.club.getUserDAO().getMemberById(member.getId());
		
		if (member.getFiscalCode() != null && !oldMember.getFiscalCode().equalsIgnoreCase(member.getFiscalCode()) && this.club.getUserDAO().getMemberByFiscalCode(member.getFiscalCode()) != null) {
			this.response.setResponseType(ResponseType.ERROR_EXIST_FISCAL_CODE);
			return;
		}
		
		this.club.getUserDAO().updateMember(member.getId(), member.getFiscalCode(), member.getAddress());
		this.response.setResponseType(ResponseType.SUCCESS_UPDATE_USER);
	}
	
	/**
	 * 
	**/
	public void removeEmployee() {
		int id = (int) this.request.getPrimaryObject();
		this.club.getUserDAO().removeUser(id);
		this.response.setResponseType(ResponseType.SUCCESS_REMOVE_EMPLOYEE);
	}
	
	/**
	 * 
	**/
	public void getUserByEmail() {
		String email = (String) this.request.getPrimaryObject();
		User user = this.club.getUserDAO().getUserByEmail(email);
		if (user != null) {
			this.response.setObject(user);
		} else {
			this.response.setResponseType(ResponseType.ERROR_USER_EMAIL);
		}
	}
	
	/**
	 * 
	**/
	public void getAllUsers() {
		UserType userType = (UserType) this.request.getPrimaryObject();
		ArrayList<User> list = this.club.getUserDAO().getAllUsers(userType);
		this.response.setObject(list);
	}
	
	/**
	 * 
	**/
	public void getAllMembersEmail() {
		ArrayList<String> list = this.club.getUserDAO().getAllMembersEmail();
		this.response.setObject(list);
	}
	
	/**
	 * 
	**/
	public void getBoatByName() {	
		String name = (String) this.request.getPrimaryObject();
		User user = (User) this.request.getSecondaryObject();
		
		Boat boat = this.club.getBoatDAO().getBoatByName(name, user);
		this.response.setObject(boat);
	}
	
	/**
	 * 
	**/
	public void getAllBoats() {
		User user = (User) this.request.getPrimaryObject();
		ArrayList<Boat> list = this.club.getBoatDAO().getAllBoats(user);
		this.response.setObject(list);
	}
	
	/**
	 * 
	**/
	public void insertBoat() {
		Boat boat = (Boat) this.request.getPrimaryObject();
		
		if (this.club.getBoatDAO().getBoatByName(boat.getName(), boat.getOwner()) != null) {
			this.response.setResponseType(ResponseType.ERROR_BOAT_NAME);
			return;
		}
		
		if(!this.club.getPaymentDAO().checkPaymentMembershipFee(boat.getOwner())) {
			this.response.setResponseType(ResponseType.ERROR_UNPAY_MEMBERSHIP_FEE);
			return;
		}
		
		this.club.getBoatDAO().insertBoat(boat.getName(), boat.getLength(), boat.getOwner());
		this.response.setResponseType(ResponseType.SUCCESS_ADD_BOAT);
	}
	
	/**
	 * 
	**/
	public void updateBoat() {
		Boat boat = (Boat) this.request.getPrimaryObject();
		
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
	 * 
	**/
	public void removeBoat() {
		Boat boat = (Boat) this.request.getPrimaryObject();
		
		ArrayList<RaceRegistration> list = this.club.getRaceRegistrationDAO().getAllRegistrationByBoat(boat);
		for (RaceRegistration registration: list) {
    		if (registration.getRace().getDate().after(today)) {
        		this.club.getRaceRegistrationDAO().removeRaceRegistration(registration.getId());
    			this.club.getPaymentDAO().repayRegistrationFee(registration.getId());
        	}
		}
		
		this.club.getBoatDAO().removeBoat(boat.getId());
		this.response.setResponseType(ResponseType.SUCCESS_REMOVE_BOAT);
	}
	
	/**
	 * 
	**/
	public void getAllNameBoatsByOwner() {
		User owner = (User) this.request.getPrimaryObject();
		ArrayList<String> list = this.club.getBoatDAO().getAllNameBoatsByOwner(owner);
		this.response.setObject(list);
	}
	
	/**
	 * 
	**/
	public void getAllPayments() {
		User user = (User) this.request.getPrimaryObject();
		FeeType type = (FeeType) this.request.getSecondaryObject();
		
		ArrayList<Payment> list = this.club.getPaymentDAO().getAllPayments(user, type);
		this.response.setObject(list);
	}
	
	/**
	 * 
	**/
	public void getAllPaymentServicesDescription() {
		ArrayList<String> list = this.club.getPaymentDAO().getAllPaymentServicesDescription();
		this.response.setObject(list);
	}
	
	/**
	 * 
	**/
	public void getPaymentServiceByDescription() {
		String description = (String) this.request.getPrimaryObject();
		PaymentService service = this.club.getPaymentDAO().getPaymentServiceByDescription(description);
		this.response.setObject(service);
	}
	
	/**
	 * 
	**/
	public void notifyPayment() {
		Notification notification = (Notification) this.request.getPrimaryObject();
		
		if ((notification.getFee().getType() == FeeType.MEMBERSHIP && this.club.getNotificationDAO().existNotificationMembershipFee(notification.getMember())) ||
				(notification.getFee().getType() == FeeType.STORAGE && this.club.getNotificationDAO().existNotificationStorageFee(notification.getMember(), notification.getBoat()))) {
			this.response.setResponseType(ResponseType.ERROR_NOTIFY_PAYMENT);
			return;
		}
		
		this.club.getNotificationDAO().insertNotification(notification.getMember(), notification.getBoat(), notification.getFee());
		this.response.setResponseType(ResponseType.SUCCESS_NOTIFY_PAYMENT);
	}
	
	/**
	 * 
	**/
	public void getAllNotifications() {
		User user = (User) this.request.getPrimaryObject();
		ArrayList<Notification> list = this.club.getNotificationDAO().getAllNotifications(user);
		this.response.setObject(list);
	}
	
	/**
	 * 
	**/
	public void getFeeByType() {
		FeeType type = (FeeType) this.request.getPrimaryObject();
		Fee fee = this.club.getFeeDAO().getFeeByType(type);
		this.response.setObject(fee);
	}
	
	/**
	 * 
	**/
	public void payFee() {
		Payment payment = (Payment) this.request.getPrimaryObject();
		
		Date lastPayment = this.club.getPaymentDAO().getLastPaymentFee(payment.getMember(), payment.getBoat(), payment.getFee().getType());

		boolean atLeastOneBoat = true;
		if (this.club.getBoatDAO().getAllBoats(payment.getMember()).size() == 0) {
			atLeastOneBoat = false;
		}
		
		if (lastPayment == null || this.today.before(lastPayment) || this.today.equals(lastPayment) || !atLeastOneBoat) {
			this.club.getPaymentDAO().payFee(payment.getMember(), payment.getBoat(), null, payment.getFee(), payment.getPaymentService(), atLeastOneBoat, false);
		} else {
			if (atLeastOneBoat) {
				long diffTime = this.today.getTime() - lastPayment.getTime();
				long diffDays = diffTime/(1000 * 60 * 60 * 24);
				int period = (int) Math.ceil((float) diffDays/this.club.getFeeDAO().getFeeByType(payment.getFee().getType()).getValidityPeriod());
				
				for (int i = 0; i < period; i++) {
					this.club.getPaymentDAO().payFee(payment.getMember(), payment.getBoat(), null, payment.getFee(), payment.getPaymentService(), atLeastOneBoat, false);
				}
			}
		}
		
		this.club.getNotificationDAO().updateReadStatusNotification(payment.getMember(), payment.getBoat(), payment.getFee());
		this.response.setResponseType(ResponseType.SUCCESS_PAY_FEE);
	}

	/**
	 * 
	**/
	public void getAllRaces() {
		ArrayList<Race> list = this.club.getRaceDAO().getAllRaces();
		this.response.setObject(list);
	}
	
	/**
	 * 
	 * @param boatsNumber
	 * @return
	**/
	public boolean checkBoatsNumber(final int boatsNumber) {
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
     * 
     * @param message
     * @return
    **/
    public boolean checkOpenRegistration() {
    	Date endDate = (Date) this.request.getPrimaryObject();
		if (endDate.before(this.today)) {
			this.response.setResponseType(ResponseType.ERROR_CLOSE_REGISTRATION);
    		return false; 
		}
		
		this.response.setObject(true);
		return true;
    }
    
    /**
	 * 
	 * @param dateRace
	 * @return
	**/
	public boolean checkDateRace(final Date dateRace) {
		if (dateRace != null) {
			if (dateRace.before(this.today) || dateRace.equals(this.today)) {
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
	
	/**
	 * 
	 * @param idRace
	 * @param dateRace
	 * @param endDateRegistration
	 * @return
	**/
	public boolean checkEndDateRegistration(final int idRace, final Date dateRace, final Date endDateRegistration) {
		Boolean errorDate = false;
		if ((endDateRegistration != null && endDateRegistration.before(this.today)) || ((idRace == 0 || (dateRace != null && endDateRegistration != null)) && !dateRace.after(endDateRegistration))) {
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
	
	/**
	 * 
	**/
	public void insertRace() {
		Race race = (Race) this.request.getPrimaryObject();

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
	 * 
	**/
	public void updateRace() {
		Race race = (Race) this.request.getPrimaryObject();
		Race oldRace = this.club.getRaceDAO().getRaceById(race.getId());
		
		if (!this.checkBoatsNumber(race.getBoatsNumber()))
			return;
		
		boolean registrations = this.club.getRaceRegistrationDAO().existRegistrationsForRace(oldRace);
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
	 * 
	**/
	public void removeRace() {
		Race race = (Race) this.request.getPrimaryObject();

		ArrayList<RaceRegistration> list = this.club.getRaceRegistrationDAO().getAllRegistrationsByRace(race);
		for (RaceRegistration registration: list) {
			this.club.getPaymentDAO().repayRegistrationFee(registration.getId());
		}
				
		this.club.getRaceDAO().removeRace(race.getId());
		this.response.setResponseType(ResponseType.SUCCESS_REMOVE_RACE);
	}
	
	/**
	 * 
	**/
	public void getAllRegistrationByRace() {
		Race race = (Race) this.request.getPrimaryObject();
		ArrayList<RaceRegistration> list = this.club.getRaceRegistrationDAO().getAllRegistrationsByRace(race);
		this.response.setObject(list);
	}

	/**
	 * 
	**/
	public void existRegistrationsForRace() {
		Race race = (Race) this.request.getPrimaryObject();
		boolean result = this.club.getRaceRegistrationDAO().existRegistrationsForRace(race);
		
		if (result) {
			this.response.setResponseType(ResponseType.OK);
		} else {
			this.response.setResponseType(ResponseType.ERROR_NO_REGISTRATIONS);
		}
	}
	
	public boolean checkPaymentByRaceRegistration(final RaceRegistration registration) {
		Race race = registration.getRace();	
		Boat boat = registration.getBoat();
		Member user = boat.getOwner();
		
		if(!this.club.getPaymentDAO().checkPaymentMembershipFee(user)) {
			this.response.setResponseType(ResponseType.ERROR_UNPAY_MEMBERSHIP_FEE);
    		return false;
		}
		
		Date lastPayment = this.club.getPaymentDAO().getLastPaymentFee(user, boat, FeeType.STORAGE);
		if (!this.club.getPaymentDAO().checkPaymentStorageFee(boat) || lastPayment.before(race.getDate()) || lastPayment.equals(race.getDate())) {
			this.response.setResponseType(ResponseType.ERROR_UNPAY_STORAGE_FEE);
			return false;
		}
		
		return true;
	}
	
	/**
	 * 
	**/
	public void insertRaceRegistration() {
		RaceRegistration registration = (RaceRegistration) this.request.getPrimaryObject();
		PaymentService paymentService = (PaymentService) this.request.getSecondaryObject();
		
		Race race = registration.getRace();		
		int participants = this.club.getRaceRegistrationDAO().getNumberBoatsInRace(race) + 1;
		if (participants > race.getBoatsNumber()) {
			this.response.setResponseType(ResponseType.ERROR_MAX_NUMBER_BOATS);
			return;
		}
		
		Boat boat = registration.getBoat();
		Member user = boat.getOwner();
		
		if (this.club.getRaceRegistrationDAO().getBoatInRaceByMember(user, race) != null) {
			this.response.setResponseType(ResponseType.ERROR_USER_BOATS_PER_RACE);
			return;
		}
		
		if (!this.checkPaymentByRaceRegistration(registration))
			return;		
	
		Fee fee = this.club.getFeeDAO().getFeeByType(FeeType.RACE_REGISTRATION);
		
		this.club.getRaceRegistrationDAO().registerBoatAtRace(race, user, boat);
		RaceRegistration newRegistration = this.club.getRaceRegistrationDAO().getRaceRegistration(race, boat);
		if (newRegistration != null) {
			this.club.getPaymentDAO().payFee(user, boat, newRegistration, fee, paymentService, false, false);
		}
		
		this.response.setResponseType(ResponseType.SUCCESS_ADD_RACE_REGISTRATION);
	}
	
	/**
	 * 
	**/
	public void updateRaceRegistration() {
		RaceRegistration registration = (RaceRegistration) this.request.getPrimaryObject();
		
		if (!this.checkPaymentByRaceRegistration(registration))
			return;
		
		Boat boat = registration.getBoat();
		this.club.getRaceRegistrationDAO().updateRaceRegistration(registration.getId(), boat);
		this.response.setResponseType(ResponseType.SUCCESS_UPDATE_RACE_REGISTRATION);
	}
	
	/**
	 * 
	**/
	public void removeRaceRegistration() {
		RaceRegistration registration = (RaceRegistration) this.request.getPrimaryObject();
		
		this.club.getRaceRegistrationDAO().removeRaceRegistration(registration.getId());
		this.club.getPaymentDAO().repayRegistrationFee(registration.getId());
		this.response.setResponseType(ResponseType.SUCCESS_REMOVE_REGISTRATION);
	}
	
	/**
	 * 
	**/
	public void getAllFees() {
		ArrayList<Fee> list = this.club.getFeeDAO().getAllFees();
		this.response.setObject(list);
	}
	
	/**
	 * 
	**/
	public void updateFee() {
		Fee fee = (Fee) this.request.getPrimaryObject();
		this.club.getFeeDAO().updateFee(fee.getId(), fee.getType(), fee.getAmount(), fee.getValidityPeriod());
		this.response.setResponseType(ResponseType.SUCCESS_UPDATE_FEE);
	}
	
	/**
	 * 
	 * @param request
	 * @return
	**/
	public Response processRequest() {		
		switch(request.getRequestType()) {
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
				this.getAllMembersEmail();
				break;
			case GET_BOAT_BY_NAME:
				this.getBoatByName();
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
			case CHECK_OPEN_REGISTRATION:
				this.checkOpenRegistration();
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
			case GET_ALL_REGISTRATION_BY_RACE:
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
