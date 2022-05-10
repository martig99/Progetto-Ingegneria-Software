package test.java.it.unipr.model;

import main.java.it.unipr.model.*;

import java.sql.Date;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The class {@code PaymentTest} defines the test for class {@code Payment}. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class PaymentTest {
	
	private final static int id = 1;
	private final static Date date = Date.valueOf("2022-03-08");
	private final static Date validityStartDate = Date.valueOf("2022-03-08");
	private final static Date validityEndDate = Date.valueOf("2022-03-08");
	private final static float total = 10;
	
	private static Member member;
	private static Boat boat;
	private static Race race;
	private static RaceRegistration registration;
	private static Fee fee;
	private static PaymentService paymentService;
	
	private static String toString;
	
	/**
	 * Configures some helpful elements for the following tests.
	**/
	@BeforeAll
	public static void setUp() {
		member = new Member(1, "Ilaria", "Rossi", "ilaria.rossi@gmail.com", "aaaaaaaaa", "RSSLRI95A41A944A", "Via della Pace 11, Bologna", StatusCode.ACTIVE);
		boat = new Boat(1, "Yacht", 1, member, StatusCode.ACTIVE);
		race = new Race(1, "Gara 1", "Roma", Date.valueOf("2022-03-08"), 3, total, Date.valueOf("2022-03-07"), StatusCode.ACTIVE);
		registration = new RaceRegistration(1, Date.valueOf("2022-03-06"), race, boat, StatusCode.ACTIVE);
		fee = new Fee(3, FeeType.RACE_REGISTRATION, 0, 0, StatusCode.ACTIVE);
		paymentService = new PaymentService(1, "Credit Card");
		
		toString = "Id: " + id + " - Date: " + date + " - Member: " + member.getEmail() + " - Boat: " + boat.getId() + " - Race Registration: " + registration.getId() + " - Validity Start Date: " + validityStartDate + " - Validity End Date: " + validityEndDate + " - Total: " + total + " - Payment Service: " + paymentService.getDescription();
	}
	
	/**
	 * Performs the test for the setter methods.
	**/
	@Test
	public void setterTest() {	
		Payment payment = new Payment();
		
		payment.setId(id);
		payment.setDate(date);
		payment.setMember(member);
		payment.setBoat(boat);
		payment.setRaceRegistration(registration);
		payment.setFee(fee);
		payment.setValidityStartDate(validityStartDate);
		payment.setValidityEndDate(validityEndDate);
		payment.setTotal(total);
		payment.setPaymentService(paymentService);
		
		assertAll(
			() -> assertTrue(payment.getId() == id, payment.getId() + " should equal " + id),
			() -> assertTrue(payment.getDate() == date, payment.getDate() + " should equal " + date),
			() -> assertTrue(payment.getUser() == member, payment.getUser().toString() + " should equal " + member.toString()), 
			() -> assertTrue(payment.getBoat() == boat, payment.getBoat().toString() + " should equal " + boat.toString()),
			() -> assertTrue(payment.getRaceRegistration() == registration, payment.getRaceRegistration().toString() + " should equal " + registration.toString()),
			() -> assertTrue(payment.getFee() == fee, payment.getFee().toString() + " should equal " + fee.toString()),
			() -> assertTrue(payment.getValidityStartDate() == validityStartDate, payment.getValidityStartDate() + " should equal " + validityStartDate),
			() -> assertTrue(payment.getValidityEndDate() == validityEndDate, payment.getValidityEndDate() + " should equal " + validityEndDate),
			() -> assertTrue(payment.getTotal() == total, payment.getTotal() + " should equal " + total),
			() -> assertTrue(payment.getPaymentService() == paymentService, payment.getPaymentService().toString() + " should equal " + paymentService.toString()),
			() -> assertTrue(payment.toString().equals(toString), payment.toString() + " should equal " + toString)
		);		
	}
	
	/**
	 * Performs the test for the constructor method.
	**/
	@Test
	public void constructorTest() {
		Payment payment = new Payment(id, date, member, boat, registration, fee, validityStartDate,validityEndDate, total, paymentService);

		assertAll(
				() -> assertTrue(payment.getId() == id, payment.getId() + " should equal " + id),
				() -> assertTrue(payment.getDate() == date, payment.getDate() + " should equal " + date),
				() -> assertTrue(payment.getUser() == member, payment.getUser().toString() + " should equal " + member.toString()), 
				() -> assertTrue(payment.getBoat() == boat, payment.getBoat().toString() + " should equal " + boat.toString()),
				() -> assertTrue(payment.getRaceRegistration() == registration, payment.getRaceRegistration().toString() + " should equal " + registration.toString()),
				() -> assertTrue(payment.getFee() == fee, payment.getFee().toString() + " should equal " + fee.toString()),
				() -> assertTrue(payment.getValidityStartDate() == validityStartDate, payment.getValidityStartDate() + " should equal " + validityStartDate),
				() -> assertTrue(payment.getValidityEndDate() == validityEndDate, payment.getValidityEndDate() + " should equal " + validityEndDate),
				() -> assertTrue(payment.getTotal() == total, payment.getTotal() + " should equal " + total),
				() -> assertTrue(payment.getPaymentService() == paymentService, payment.getPaymentService().toString() + " should equal " + paymentService.toString()),
				() -> assertTrue(payment.toString().equals(toString), payment.toString() + " should equal " + toString)
			);
	}
}
