package test.java.it.unipr.sql;

import main.java.it.unipr.model.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentDAOTest extends UtilTest {
	
	private static String fiscalCode = "RSSLRI95A41A944A";
		
	@BeforeAll
	public static void payFee() {
		Member member = UtilTest.getClub().getUserDAO().getMemberByFiscalCode(fiscalCode);
		PaymentService paymentService = UtilTest.getClub().getPaymentDAO().getPaymentServiceByDescription("Transfer Receipt");
		Fee fee = UtilTest.getClub().getFeeDAO().getFeeByType(FeeType.MEMBERSHIP);
		
		UtilTest.getClub().getPaymentDAO().payFee(member, null, null, fee, paymentService, false, false);
	}
	
	@Test
	public void checkPaymentMembershipFeeTest() {
		Member member = UtilTest.getClub().getUserDAO().getMemberByFiscalCode(fiscalCode);
		
		boolean result = UtilTest.getClub().getPaymentDAO().checkPaymentMembershipFee(member);
		assertTrue(result == true, result + " should equal " + true);
	}
}
