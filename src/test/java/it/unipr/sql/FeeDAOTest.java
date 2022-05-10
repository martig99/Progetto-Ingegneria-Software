package test.java.it.unipr.sql;

import main.java.it.unipr.model.*;

import org.junit.jupiter.api.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The class {@code FeeDAOTest} defines the test for class {@code FeeDAO}. 
 * This class extends the class {@code UtilTest}.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class FeeDAOTest extends UtilTest {
	
	private final static FeeType type = FeeType.MEMBERSHIP;
	private final static float amount = 15;
	private final static int validityPeriod = 365;
	
	private static int id = 0;
	
	/**
	 * Performs the test for the method that gets a fee given the type.
	**/
	@BeforeAll
	public static void getFeeByType() {
		Fee fee = UtilTest.getClub().getFeeDAO().getFeeByType(type);
		assertNotNull(fee);
		
		id = fee.getId();
	}
	
	/**
	 * Performs the test for the method that gets a fee given the id.
	**/
	@Test
	public static void getFeeById() {
		Fee fee = UtilTest.getClub().getFeeDAO().getFeeById(id);
		assertNotNull(fee);
	}
	
	/**
	 * Performs the test for the method of updating the fee.
	**/
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
