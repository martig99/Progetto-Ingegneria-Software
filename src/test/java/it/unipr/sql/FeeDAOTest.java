package test.java.it.unipr.sql;

import main.java.it.unipr.model.*;

import org.junit.jupiter.api.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FeeDAOTest extends UtilTest {
	
	private final static FeeType type = FeeType.MEMBERSHIP;
	private final static float amount = 15;
	private final static int validityPeriod = 365;
	
	private static int id = 0;
	
	@BeforeAll
	public static void getFee() {
		Fee fee = UtilTest.getClub().getFeeDAO().getFeeByType(type);
		assertNotNull(fee);
		
		id = fee.getId();
	}
	
	@Test
	public void updateFeeTest() {
		UtilTest.getClub().getFeeDAO().updateFee(id, type, amount, validityPeriod);
		
		Fee fee = UtilTest.getClub().getFeeDAO().getFeeByType(type);
		assertAll(
			() -> assertTrue(fee.getType() == type, fee.getType() + " should equal " + type),
			() -> assertTrue(fee.getAmount() == amount, fee.getAmount() + " should equal " + amount),
			() -> assertTrue(fee.getValidityPeriod() == validityPeriod, fee.getValidityPeriod() + " should equal " + validityPeriod)
		);
	}
}
