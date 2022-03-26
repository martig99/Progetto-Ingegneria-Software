package test.java.it.unipr.model;

import main.java.it.unipr.model.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class NotificationTest {
	
	private final static int id = 1;
	private final static boolean readStatus = false;
	
	private static Member member;
	private static Boat boat;
	private static Fee fee;
	
	private static String toString;
	
	@BeforeAll
	public static void setUp() {
		member = new Member(1, "Ilaria", "Rossi", "ilaria.rossi@gmail.com", "aaaaaaaaa", "RSSLRI95A41A944A", "Via della Pace 11, Bologna");
		boat = new Boat(1, "Yacht", 2, member, StatusCode.ACTIVE);
		fee = new Fee(1, FeeType.STORAGE, 20, 365, StatusCode.ACTIVE);
		
		toString = "Id: " + id + " - Member: " + member.getEmail() +" - Boat: " + boat.getId() + " - Fee: " + fee.getType() + " - Read status: " + readStatus;
	}
	
	@Test 
	public void setterTest() {
		Notification notification = new Notification();
		
		notification.setId(id);
		notification.setMember(member);
		notification.setBoat(boat);
		notification.setFee(fee);
		notification.setReadStatus(readStatus);
		
		assertAll(
			() -> assertTrue(notification.getId() == id, notification.getId() + " should equal " + id),
			() -> assertTrue(notification.getMember().getId() == member.getId(), notification.getMember().getId() + " should equal " + member.getId()),
			() -> assertTrue(notification.getBoat().getId() == boat.getId(), notification.getBoat().getId() + " should equal " + boat.getId()),
			() -> assertTrue(notification.getFee().getId() == fee.getId(), notification.getFee().getId() + " should equal " + fee.getId()),
			() -> assertTrue(notification.toString().equals(toString), notification.toString() + " should equal " + toString)
		);
	}
	
	@Test
	public void constructorTest() {
		Notification notification = new Notification(id, member, boat, fee, readStatus);
		
		assertAll(
				() -> assertTrue(notification.getId() == id, notification.getId() + " should equal " + id),
				() -> assertTrue(notification.getMember().getId() == member.getId(), notification.getMember().getId() + " should equal " + member.getId()),
				() -> assertTrue(notification.getBoat().getId() == boat.getId(), notification.getBoat().getId() + " should equal " + boat.getId()),
				() -> assertTrue(notification.getFee().getId() == fee.getId(), notification.getFee().getId() + " should equal " + fee.getId()),
				() -> assertTrue(notification.toString().equals(toString), notification.toString() + " should equal " + toString)
			);
	}
}
