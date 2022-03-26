package test.java.it.unipr.model;

import main.java.it.unipr.model.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class FeeTest {
	
	private final static int id = 1;
	private final static FeeType type = FeeType.MEMBERSHIP; 
	private final static float amount = 100;
	private final static int validityPeriod = 365;
	private final static StatusCode statusCode = StatusCode.ACTIVE;
	
	private static String toString;
	
	@BeforeAll
	public static void setUp() {
		toString = "Id: " + id + " - Type: " + type.toString() + " - Amount: " + amount + " - Validity Period: " + validityPeriod + " - Status Code: " + statusCode.toString();
	}
	
	@Test
	public void setterTest() {
		Fee fee = new Fee();
		
		fee.setId(id);
		fee.setType(type);
		fee.setAmount(amount);
		fee.setValidityPeriod(validityPeriod);
		fee.setStatusCode(statusCode);
		
		assertAll(
			() -> assertTrue(fee.getId() == id, fee.getId() + " should equal " + id),
			() -> assertTrue(fee.getType() == type, fee.getType() + " should equal " + type),
			() -> assertTrue(fee.getAmount() == amount, fee.getAmount() + " should equal " + amount),
			() -> assertTrue(fee.getValidityPeriod() == validityPeriod, fee.getValidityPeriod() + " should equal " + validityPeriod),
			() -> assertTrue(fee.getStatusCode() == statusCode, fee.getStatusCode() + " should equal " + statusCode),
			() -> assertTrue(fee.toString().equals(toString), fee.toString() + " should equal " + toString)
		);
	}
	
	@Test
	public void constructorTest() {
		Fee fee = new Fee(id, type, amount, validityPeriod, statusCode);
		
		assertAll(
			() -> assertTrue(fee.getId() == id, fee.getId() + " should equal " + id),
			() -> assertTrue(fee.getType() == type, fee.getType() + " should equal " + type),
			() -> assertTrue(fee.getAmount() == amount, fee.getAmount() + " should equal " + amount),
			() -> assertTrue(fee.getValidityPeriod() == validityPeriod, fee.getValidityPeriod() + " should equal " + validityPeriod),
			() -> assertTrue(fee.getStatusCode() == statusCode, fee.getStatusCode() + " should equal " + statusCode),
			() -> assertTrue(fee.toString().equals(toString), fee.toString() + " should equal " + toString)
		);
	}
}
