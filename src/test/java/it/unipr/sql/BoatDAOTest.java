package test.java.it.unipr.sql;

import main.java.it.unipr.model.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class BoatDAOTest extends UtilTest {
	
	private final static String fiscalCode = "RSSLRI95A41A944A";
	
	private static int id = 0;
	private static String nameBoat = "Battello";
	private static int lengthBoat = 2;

	@BeforeAll
	public static void insertBoatTest() {
		Member member = UtilTest.getClub().getUserDAO().getMemberByFiscalCode(fiscalCode);
		
		UtilTest.getClub().getBoatDAO().insertBoat(nameBoat, lengthBoat, member);
		
		Boat newBoat = UtilTest.getClub().getBoatDAO().getBoatByName(nameBoat, member);
		assertAll(
			() -> assertTrue(newBoat.getName().equals(nameBoat), newBoat.getName() + " should equal " + nameBoat),
			() -> assertTrue(newBoat.getLength() == lengthBoat, newBoat.getLength() + " should equal " + lengthBoat),
			() -> assertTrue(newBoat.getOwner().getId() == member.getId(), newBoat.getOwner().getId() + " should equal " + member.getId())
		);
		
		id = newBoat.getId();
	}
	
	@Test
	public static void getNoBoatByNameTest() {	
		Member member = UtilTest.getClub().getUserDAO().getMemberByFiscalCode(fiscalCode);
		
		Boat boat = UtilTest.getClub().getBoatDAO().getBoatByName(nameBoat, member);
		assertNotNull(boat);
	}
	
	@Test
	public void updateNameBoatTest() {		
		nameBoat = "Classica";
		
		UtilTest.getClub().getBoatDAO().updateBoat(id, nameBoat, null);
		
		Boat newBoat = UtilTest.getClub().getBoatDAO().getBoatById(id);
		assertAll(
			() -> assertTrue(newBoat.getName().equals(nameBoat), newBoat.getName() + " should equal " + nameBoat),
			() -> assertTrue(newBoat.getLength() == lengthBoat, newBoat.getLength() + " should equal " + lengthBoat)
		);
	}	
	
	@Test
	public void updateLengthBoatTest() {
		lengthBoat = 5;
				
		UtilTest.getClub().getBoatDAO().updateBoat(id, null, lengthBoat);
		
		Boat newBoat = UtilTest.getClub().getBoatDAO().getBoatById(id);
		assertAll(
			() -> assertTrue(newBoat.getName().equals(nameBoat), newBoat.getName() + " should equal " + nameBoat),
			() -> assertTrue(newBoat.getLength() == lengthBoat, newBoat.getLength() + " should equal " + lengthBoat)
		);
	}	
	
	@Test
	public void updateBoatTest() {	
		nameBoat = "Yacht 100";
		lengthBoat = 7;
				
		UtilTest.getClub().getBoatDAO().updateBoat(id, nameBoat, lengthBoat);
		
		Boat newBoat = UtilTest.getClub().getBoatDAO().getBoatById(id);
		assertAll(
			() -> assertTrue(newBoat.getName().equals(nameBoat), newBoat.getName() + " should equal " + nameBoat),
			() -> assertTrue(newBoat.getLength() == lengthBoat, newBoat.getLength() + " should equal " + lengthBoat)
		);
	}	
	
	@AfterAll
	public static void removeBoatTest() {	
		UtilTest.getClub().getBoatDAO().removeBoat(id);
		
		Boat boat = UtilTest.getClub().getBoatDAO().getBoatById(id);
		assertNull(boat);
	}
}
