package test.java.it.unipr.sql;

import main.java.it.unipr.model.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The class {@code RaceRegistrationDAO} defines the test for class {@code RaceRegistrationDAO}. 
 * This class extends the class {@code UtilTest}.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class RaceRegistrationDAOTest extends UtilTest {
	
	private static int idRegistration = 0;
	private static int idBoat = 1;
	private static int idRace = 1;
	
	/**
	 * Performs the test for the method that registers a boat at a race.
	**/
	@BeforeAll
	public static void registerBoatAtRaceTest() {		
		UtilTest.getClub().getRaceRegistrationDAO().registerBoatAtRace(idRace, idBoat);
		
		RaceRegistration newRegistration = UtilTest.getClub().getRaceRegistrationDAO().getRaceRegistration(idRace, idBoat);
		assertAll(
			() -> assertTrue(newRegistration.getRace().getId() == idRace, newRegistration.getRace().getId() + " should equal " + idRace),
			() -> assertTrue(newRegistration.getBoat().getId() == idBoat, newRegistration.getBoat().getId() + " should equal " + idBoat)
		);
		
		idRegistration = newRegistration.getId();
	}
	
	/**
	 * Performs the test for the method that gets a registration given the unique identifier of the race and the id of the boat.
	**/
	@Test
	public void getRaceRegistration() {
		RaceRegistration registration = UtilTest.getClub().getRaceRegistrationDAO().getRaceRegistration(idRace, idBoat);
		assertNotNull(registration);
	}
	
	/**
	 * Performs the test for the method of updating the registration at the race.
	**/
	@Test
	public void updateRaceRegistrationTest() {
		idBoat = 3;
				
		UtilTest.getClub().getRaceRegistrationDAO().updateRaceRegistration(idRegistration, idBoat);
		
		RaceRegistration newRegistration = UtilTest.getClub().getRaceRegistrationDAO().getRaceRegistration(idRace, idBoat);
		assertAll(
			() -> assertTrue(newRegistration.getRace().getId() == idRace, newRegistration.getRace().getId() + " should equal " + idRace),
			() -> assertTrue(newRegistration.getBoat().getId() == idBoat, newRegistration.getBoat().getId() + " should equal " + idBoat)
		);
		
	}
	
	/**
	 * Performs the test for the method of removing the registration at the race.
	**/
	@AfterAll
	public static void removeRaceRegistration() {
		UtilTest.getClub().getRaceRegistrationDAO().removeRaceRegistration(idRegistration);
		
		RaceRegistration registration = UtilTest.getClub().getRaceRegistrationDAO().getRaceRegistrationById(idRegistration);
		assertNull(registration);
		
	}
}
