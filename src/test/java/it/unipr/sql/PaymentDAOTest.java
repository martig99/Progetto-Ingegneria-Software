package test.java.it.unipr.sql;

import main.java.it.unipr.model.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The class {@code PaymentDAOTest} defines the test for class {@code PaymentDAO}. 
 * This class extends the class {@code UtilTest}.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class PaymentDAOTest extends UtilTest {
	
	private static String fiscalCode = "RSSLRI95A41A944A";
	
	/**
	 * Performs the test for the method that pays a fee. 
	 * In this case, the association fee is paid for a particular user. 
	**/
	@BeforeAll
	public static void payMembershipFee() {
		Member member = UtilTest.getClub().getUserDAO().getMemberByFiscalCode(fiscalCode);
		PaymentService paymentService = UtilTest.getClub().getPaymentDAO().getPaymentServiceByDescription("Transfer Receipt");
		Fee fee = UtilTest.getClub().getFeeDAO().getFeeByType(FeeType.MEMBERSHIP);
		
		UtilTest.getClub().getPaymentDAO().payFee(member, null, null, fee, paymentService, false, false);
	}
	
	/**
	 * Performs the test for the method that checks if the payment of the association fee is still valid.
	**/
	@Test
	public void checkPaymentMembershipFeeTest() {
		Member member = UtilTest.getClub().getUserDAO().getMemberByFiscalCode(fiscalCode);
		
		boolean result = UtilTest.getClub().getPaymentDAO().checkPaymentMembershipFee(member);
		assertTrue(result == true, result + " should equal " + true);
	}
}
