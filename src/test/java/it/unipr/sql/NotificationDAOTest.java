package test.java.it.unipr.sql;

import main.java.it.unipr.model.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class NotificationDAOTest extends UtilTest {
	
	private final static String fiscalCode = "RSSLRI95A41A944A"; 
	private final static FeeType feeType = FeeType.MEMBERSHIP;

	@BeforeAll
	public static void insertNotificationTest() {
		Member member = UtilTest.getClub().getUserDAO().getMemberByFiscalCode(fiscalCode);
		Fee fee = UtilTest.getClub().getFeeDAO().getFeeByType(feeType);
		
		UtilTest.getClub().getNotificationDAO().insertNotification(member, null, fee);
		
		Notification newNotification = UtilTest.getClub().getNotificationDAO().getNotification(member, null, fee, false);
		assertAll(
			() -> assertTrue(newNotification.getMember().getId() == member.getId(), newNotification.getMember().getId() + " should equal " + member.getId()),
			() -> assertTrue(newNotification.getFee().getId() == fee.getId(), newNotification.getFee().getId() + " should equal " + fee.getId()),
			() -> assertTrue(newNotification.isReadStatus() == false, newNotification.isReadStatus() + " should equal " + false)
		);
	}
	
	@Test
	public void existNotificationMembershipFeeTest() {
		Member member = UtilTest.getClub().getUserDAO().getMemberByFiscalCode(fiscalCode);
		
		boolean result = UtilTest.getClub().getNotificationDAO().existNotificationMembershipFee(member);
		assertTrue(result == true, result + " should equal " + true);
	}
	
	@Test
	public void updateReadStatusNotificationTest() {
		Member member = UtilTest.getClub().getUserDAO().getMemberByFiscalCode(fiscalCode);
		Fee fee = UtilTest.getClub().getFeeDAO().getFeeByType(feeType);
		
		UtilTest.getClub().getNotificationDAO().updateReadStatusNotification(member, null, fee);
		
		Notification newNotification = UtilTest.getClub().getNotificationDAO().getNotification(member, null, fee, true);
		assertAll(
			() -> assertTrue(newNotification.getMember().getId() == member.getId(), newNotification.getMember().getId() + " should equal " + member.getId()),
			() -> assertTrue(newNotification.getFee().getId() == fee.getId(), newNotification.getFee().getId() + " should equal " + fee.getId()),
			() -> assertTrue(newNotification.isReadStatus() == true, newNotification.isReadStatus() + " should equal " + true)
		);
	}
}
