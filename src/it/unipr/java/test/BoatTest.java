package it.unipr.java.test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import it.unipr.java.model.*;

class BoatTest {
	
	final static int idBoat = 1;
	final static String nameBoat = "yacht";
	final static int lengthBoat = 1;
	
	final static String fiscalCode = "RSSLRI95A41A944A";
	final static String emailMember = "ilaria.rossi@gmail.com";
	
	/*@Test
	void initialTest()
	{
		float storageFee = 4;
		SailingClub s = new SailingClub();
		Member m = s.getMemberByFiscalCode(fiscalCode);
		StatusCode statusCode = StatusCode.ACTIVE;
		String toString = "Id: " + idBoat + " - Name: " + nameBoat + " - Length: " + lengthBoat + " - Storage Fee: " + storageFee + " - Owner: " + m.getEmail() + " - Status Code: " + statusCode.toString();
		
		Boat b = new Boat();
		b.setId(idBoat);
		b.setName(nameBoat);
		b.setLength(lengthBoat);
		b.setStorageFee(storageFee);
		b.setOwner(m);
		b.setStatusCode(statusCode);
		
		assertEquals(idBoat, b.getId(),
				() -> b.getId() + " should equal " + idBoat);
		assertEquals(nameBoat, b.getName(),
				() -> b.getName() + " should equal " + nameBoat);
		assertEquals(lengthBoat, b.getLength(),
				() -> b.getLength() + " should equal " + lengthBoat);
		assertEquals(storageFee, b.getStoragFee(),
				() -> b.getStoragFee() + " should equal " + storageFee);
		assertEquals(m.getId(), b.getOwner().getId(),
				() -> b.getOwner().getId() + " should equal " + m.getId());
		assertEquals(statusCode, b.getStatusCode(),
				() ->b.getStatusCode().toString() + " should equal " + statusCode.toString());
		assertEquals(toString, b.toString(),
				() ->b.toString().toString() + " should equal " + toString);
		
		Boat b2 = new Boat(idBoat,nameBoat,lengthBoat,storageFee,m,statusCode);
		assertEquals(idBoat, b2.getId(),
				() -> b2.getId() + " should equal " + idBoat);
		assertEquals(nameBoat, b2.getName(),
				() -> b2.getName() + " should equal " + nameBoat);
		assertEquals(lengthBoat, b2.getLength(),
				() -> b2.getLength() + " should equal " + lengthBoat);
		assertEquals(storageFee, b2.getStoragFee(),
				() -> b2.getStoragFee() + " should equal " + storageFee);
		assertEquals(m.getId(), b2.getOwner().getId(),
				() -> b2.getOwner().getId() + " should equal " + m.getId());
		assertEquals(statusCode, b2.getStatusCode(),
				() ->b2.getStatusCode().toString() + " should equal " + statusCode.toString());
		assertEquals(toString, b2.toString(),
				() ->b2.toString().toString() + " should equal " + toString);
		
	}*/
	
	@Test
	void updateBoatTest() 
	{
		SailingClub s = new SailingClub();
		Fee fee = s.getFee(FeeType.STORAGE);
		Float amount = fee.getAmount() * lengthBoat;

		String newNameBoat =  "Battello";
		s.updateBoat(idBoat, newNameBoat, null);
		Boat b = s.getBoatById(idBoat);
		assertEquals(newNameBoat, b.getName(),
				() -> b.getName() + " should equal " + newNameBoat);
		assertEquals(lengthBoat, b.getLength(),
				() -> b.getLength() + " should equal " + lengthBoat);
		assertEquals(amount, b.getStoragFee(),
				() -> b.getStoragFee() + " should equal " + amount);
		
		int newLengthBoat =  3;
		s.updateBoat(idBoat, null, newLengthBoat);
		Boat b2 = s.getBoatById(idBoat);
		assertEquals(newNameBoat, b2.getName(),
				() -> b2.getName() + " should equal " + newNameBoat);
		assertEquals(newLengthBoat, b2.getLength(),
				() -> b2.getLength() + " should equal " + newLengthBoat);
		Float newAmount = fee.getAmount() * newLengthBoat;
		assertEquals(newAmount,b2.getStoragFee(),
				()-> b2.getStoragFee() + " should equal " + newAmount);
		
		s.updateBoat(idBoat, nameBoat, lengthBoat);
		Boat oldB = s.getBoatById(idBoat);
		assertEquals(nameBoat, oldB.getName(),
				() -> oldB.getName() + " should equal " + nameBoat);
		assertEquals(lengthBoat, oldB.getLength(),
				() -> oldB.getLength() + " should equal " + lengthBoat);
		assertEquals(amount, oldB.getStoragFee(),
				() -> oldB.getStoragFee() + " should equal " + amount);
		
	}
	
	@ParameterizedTest
	@ValueSource(strings={"Battello"})
	@BeforeEach 
	void getBoatByNameTest(String nameBoat) 
	{
		System.out.println("cocgcpiyg0");
		SailingClub s = new SailingClub();
		Member m = s.getMemberByFiscalCode(fiscalCode);
		Fee fee = s.getFee(FeeType.STORAGE);
		Float amount = fee.getAmount() * lengthBoat;
		Boat result = new Boat(idBoat,nameBoat,lengthBoat,amount,m,StatusCode.ACTIVE);
		Boat b = s.getBoatByName(nameBoat, m);
		assertEquals(null, b,
				() -> b + " should equal " + null); //vuoi che creo la barca e poi confronto con il get o faccio come ho fatto sotto dove controllo direttamente l'id
	}
	
	
	/*
	@Test
	void insertBoatTest()
	{
		String nameB = "nameTest";
		int lengthB = 1;
		SailingClub s = new SailingClub();
		Member m = s.getMemberByFiscalCode(fiscalCode);
		s.insertBoat(nameB, lengthB, m);
		Boat b = s.getBoatByName(nameB, m);
		assertEquals(nameB, b.getName(),
				() -> b.getName() + " should equal " + nameB);
		assertEquals(lengthB, b.getLength(),
				() -> b.getLength() + " should equal " + lengthB);
	}
	
	@Test
	void removeBoatTest()
	{
		SailingClub s = new SailingClub();
		Member m = s.getMemberByFiscalCode(fiscalCode);
		String nameB = "nameTest";
		Boat b = s.getBoatByName(nameB, m);
		s.removeBoat(b.getId());
		Boat b2 = s.getBoatByName(nameB, m);
		assertEquals(null,b2,
			() -> b2 + " should equal " + null);
		
	}*/
}