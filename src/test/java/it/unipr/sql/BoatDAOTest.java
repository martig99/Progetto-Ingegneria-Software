package test.java.it.unipr.sql;

import main.java.it.unipr.model.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 * The class {@code BoatDAOTest} defines the test for class {@code BoatDAO}. 
 * This class extends the class {@code UtilTest}.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class BoatDAOTest extends UtilTest {
	
	private final static String fiscalCode = "RSSLRI95A41A944A";
	
	private static int id = 0;
	private static String nameBoat = "Battello";
	private static int lengthBoat = 2;

	/**
	 * Performs the test for the method of inserting a new boat.
	**/
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
	
	/**
	 * Performs the test for the method that gets a boat given the name and the owner.
	 * The test is successful if a boat is found.
	**/
	@Test
	public static void getBoatByNameTest() {	
		Member member = UtilTest.getClub().getUserDAO().getMemberByFiscalCode(fiscalCode);
		
		Boat boat = UtilTest.getClub().getBoatDAO().getBoatByName(nameBoat, member);
		assertNotNull(boat);
	}
	
	/**
	 * Performs the test for the method of updating the name of the boat.
	**/
	@Test
	public void updateNameBoatTest() {		
		nameBoat = "Nave Crocera";
		
		UtilTest.getClub().getBoatDAO().updateBoat(id, nameBoat, null);
		
		Boat newBoat = UtilTest.getClub().getBoatDAO().getBoatById(id);
		assertAll(
			() -> assertTrue(newBoat.getName().equals(nameBoat), newBoat.getName() + " should equal " + nameBoat),
			() -> assertTrue(newBoat.getLength() == lengthBoat, newBoat.getLength() + " should equal " + lengthBoat)
		);
	}	
	
	/**
	 * Performs the test for the method of updating the length of the boat.
	**/
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
	
	/**
	 * Performs the test for the method of updating the boat. 
	**/
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
	
	/**
	 * Performs the test for the method of removing the boat.
	**/
	@AfterAll
	public static void removeBoatTest() {	
		UtilTest.getClub().getBoatDAO().removeBoat(id);
		
		Boat boat = UtilTest.getClub().getBoatDAO().getBoatById(id);
		assertNull(boat);
	}
}
