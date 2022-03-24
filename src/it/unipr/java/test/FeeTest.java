package it.unipr.java.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.unipr.java.model.Fee;
import it.unipr.java.model.FeeType;
import it.unipr.java.model.SailingClub;
import it.unipr.java.model.StatusCode;

public class FeeTest {
	
	final static int idFee = 1;
	final static FeeType type = FeeType.MEMBERSHIP; 
	final static float amount = 100;
	final static int validityPeriod = 365;
	
	@Test
	void initialTestRace()
	{
		String toString = "Id: " + idFee + " - Type: " + type.toString() + " - Amount: " + amount + " - Validity Period: " + validityPeriod + " - Status Code: " + StatusCode.ACTIVE.toString();
	
		Fee f = new Fee();
		f.setId(idFee);
		f.setType(type);
		f.setAmount(amount);
		f.setValidityPeriod(validityPeriod);
		f.setStatusCode(StatusCode.ACTIVE);
		
		assertEquals(idFee, f.getId(),
				() -> f.getId() + " should equal " + idFee);
		assertEquals(type, f.getType(),
				() -> f.getType() + " should equal " + type);
		assertEquals(amount, f.getAmount(),
				() -> f.getAmount() + " should equal " + amount);
		assertEquals(validityPeriod, f.getValidityPeriod(),
				() -> f.getValidityPeriod() + " should equal " + validityPeriod);
		assertEquals(StatusCode.ACTIVE.toString(), f.getStatusCode().toString(),
				() -> f.getStatusCode().toString() + " should equal " + StatusCode.ACTIVE.toString());
		assertEquals(toString, f.toString(),
				() -> f.toString() + " should equal " + toString);
		
		Fee f2 = new Fee(idFee,type,amount,validityPeriod,StatusCode.ACTIVE);
		
		assertEquals(idFee, f2.getId(),
				() -> f2.getId() + " should equal " + idFee);
		assertEquals(type, f2.getType(),
				() -> f2.getType() + " should equal " + type);
		assertEquals(amount, f2.getAmount(),
				() -> f2.getAmount() + " should equal " + amount);
		assertEquals(validityPeriod, f2.getValidityPeriod(),
				() -> f2.getValidityPeriod() + " should equal " + validityPeriod);
		assertEquals(StatusCode.ACTIVE.toString(), f2.getStatusCode().toString(),
				() -> f2.getStatusCode().toString() + " should equal " + StatusCode.ACTIVE.toString());
		assertEquals(toString, f2.toString(),
				() -> f2.toString() + " should equal " + toString);
		
	}
	
	/*
	@Test
	void insertFeeTest()
	{
		SailingClub s = new SailingClub();
		String typeF = "Test";
		float amountF = 1;
		int validityPeriodF = 1;
		s.insertFee(typeF, amountF, validityPeriodF);
		int idF = 7; //ultima riga della tabella delle Fee
		//Fee f = s.getFee(typeF); //da errore perché questo tipo che noi creiamo non c'è negli feetype
		Fee f = s.getFeeById(idF);
	}
	
	@Test
	void removeFeeTest()
	{
		SailingClub s = new SailingClub();
		int idF = 7; //ultima riga della tabella delle Fee
		s.removeFee(idF);
		Fee f = s.getFeeById(idF);
		assertEquals(null,f,
			() -> f + " should equal " + null);
	}
	
*/
}
