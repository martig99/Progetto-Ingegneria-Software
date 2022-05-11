package test.java.it.unipr.model;

import main.java.it.unipr.model.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * The class {@code NotificationTest} defines the test for class {@code Notification}. 
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class NotificationTest {
	
	private final static int id = 1;
	private final static StatusCode statusCode = StatusCode.ACTIVE;
	
	private static Member user;
	private static Boat boat;
	private static Fee fee;
	
	private static String toString;
	
	/**
	 * Configures some helpful elements for the following tests.
	**/
	@BeforeAll
	public static void setUp() {
		user = new Member(1, "Ilaria", "Rossi", "ilaria.rossi@gmail.com", "aaaaaaaaa", "RSSLRI95A41A944A", "Via della Pace 11, Bologna",StatusCode.ACTIVE);
		boat = new Boat(1, "Yacht", 2, user, StatusCode.ACTIVE);
		fee = new Fee(1, FeeType.STORAGE, 20, 365, StatusCode.ACTIVE);
		
		toString = "Id: " + id + " - Member: " + user.getEmail() +" - Boat: " + boat.getId() + " - Fee: " + fee.getType() + " - Status Code: " + statusCode;
	}
	
	/**
	 * Performs the test for the setter methods.
	**/
	@Test 
	public void setterTest() {
		Notification notification = new Notification();
		
		notification.setId(id);
		notification.setMember(user);
		notification.setBoat(boat);
		notification.setFee(fee);
		notification.setStatusCode(statusCode);
		
		assertAll(
			() -> assertTrue(notification.getId() == id, notification.getId() + " should equal " + id),
			() -> assertTrue(notification.getMember().getId() == user.getId(), notification.getMember().getId() + " should equal " + user.getId()),
			() -> assertTrue(notification.getBoat().getId() == boat.getId(), notification.getBoat().getId() + " should equal " + boat.getId()),
			() -> assertTrue(notification.getFee().getId() == fee.getId(), notification.getFee().getId() + " should equal " + fee.getId()),
			() -> assertTrue(notification.toString().equals(toString), notification.toString() + " should equal " + toString)
		);
	}
	
	/**
	 * Performs the test for the constructor method.
	**/
	@Test
	public void constructorTest() {
		Notification notification = new Notification(id, user, boat, fee, statusCode);
		
		assertAll(
				() -> assertTrue(notification.getId() == id, notification.getId() + " should equal " + id),
				() -> assertTrue(notification.getMember().getId() == user.getId(), notification.getMember().getId() + " should equal " + user.getId()),
				() -> assertTrue(notification.getBoat().getId() == boat.getId(), notification.getBoat().getId() + " should equal " + boat.getId()),
				() -> assertTrue(notification.getFee().getId() == fee.getId(), notification.getFee().getId() + " should equal " + fee.getId()),
				() -> assertTrue(notification.toString().equals(toString), notification.toString() + " should equal " + toString)
			);
	}
}
