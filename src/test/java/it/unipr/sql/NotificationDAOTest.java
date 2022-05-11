package test.java.it.unipr.sql;

import main.java.it.unipr.model.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The class {@code NotificationDAOTest} defines the test for class {@code NotificationDAO}. 
 * This class extends the class {@code UtilTest}.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class NotificationDAOTest extends UtilTest {
	
	private final static String email = "ilaria.rossi@gmail.com";
	private final static UserType userType = UserType.MEMBER;
	private final static FeeType feeType = FeeType.MEMBERSHIP;

	/**
	 * Performs the test for the method of inserting a new notification.
	**/
	@BeforeAll
	public static void insertNotificationTest() {
		User user = UtilTest.getClub().getUserDAO().getUserByEmail(email, userType);
		Fee fee = UtilTest.getClub().getFeeDAO().getFeeByType(feeType);
		
		UtilTest.getClub().getNotificationDAO().insertNotification(user, null, fee);
		
		Notification newNotification = UtilTest.getClub().getNotificationDAO().getNotification(user, null, fee, StatusCode.ACTIVE);
		assertAll(
			() -> assertTrue(newNotification.getMember().getId() == user.getId(), newNotification.getMember().getId() + " should equal " + user.getId()),
			() -> assertTrue(newNotification.getFee().getId() == fee.getId(), newNotification.getFee().getId() + " should equal " + fee.getId()),
			() -> assertTrue(newNotification.getStatusCode() == StatusCode.ACTIVE, newNotification.getStatusCode() + " should equal " + StatusCode.ACTIVE)
		);
	}
	
	/**
	 * Performs the test for the method that checks if a certain user has a payment notification for the membership fee.
	**/
	@Test
	public void existNotificationMembershipFeeTest() {
		User user = UtilTest.getClub().getUserDAO().getUserByEmail(email, userType);
		
		boolean result = UtilTest.getClub().getNotificationDAO().existNotificationMembershipFee(user);
		assertTrue(result == true, result + " should equal " + true);
	}
	
	/**
	 * Performs the test for the method of updating the status code of the notification.
	**/
	@AfterAll
	public static void updateStatusCodeNotificationTest() {
		User user = UtilTest.getClub().getUserDAO().getUserByEmail(email, userType);
		Fee fee = UtilTest.getClub().getFeeDAO().getFeeByType(feeType);
		
		UtilTest.getClub().getNotificationDAO().updateStatusCodeNotification(user, null, fee);
		
		Notification newNotification = UtilTest.getClub().getNotificationDAO().getNotification(user, null, fee, StatusCode.ELIMINATED);
		assertAll(
			() -> assertTrue(newNotification.getMember().getId() == user.getId(), newNotification.getMember().getId() + " should equal " + user.getId()),
			() -> assertTrue(newNotification.getFee().getId() == fee.getId(), newNotification.getFee().getId() + " should equal " + fee.getId()),
			() -> assertTrue(newNotification.getStatusCode() == StatusCode.ELIMINATED, newNotification.getStatusCode() + " should equal " + StatusCode.ELIMINATED)
		);
	}
}
