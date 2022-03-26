package test.java.it.unipr.sql;

import main.java.it.unipr.model.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RaceRegistrationDAO extends UtilTest {
	
	private static int idRegistration = 0;
	private static int idBoat = 1;
	private static int idRace = 1;
	
	@BeforeAll
	public static void registerBoatAtRaceTest() {
		Race race = UtilTest.getClub().getRaceDAO().getRaceById(idRace);
		Boat boat = UtilTest.getClub().getBoatDAO().getBoatById(idBoat);
		Member member = boat.getOwner();
		
		UtilTest.getClub().getRaceRegistrationDAO().registerBoatAtRace(race, member, boat);
		
		RaceRegistration newRegistration = UtilTest.getClub().getRaceRegistrationDAO().getRaceRegistration(race, boat);
		assertAll(
			() -> assertTrue(newRegistration.getRace().getId() == idRace, newRegistration.getRace().getId() + " should equal " + race.getId()),
			() -> assertTrue(newRegistration.getBoat().getId() == idBoat, newRegistration.getBoat().getId() + " should equal " + boat.getId())
		);
		
		idRegistration = newRegistration.getId();
	}
	
	@Test
	public void getRaceRegistration() {
		Race race = UtilTest.getClub().getRaceDAO().getRaceById(idRace);
		Boat boat = UtilTest.getClub().getBoatDAO().getBoatById(idBoat);
		
		RaceRegistration registration = UtilTest.getClub().getRaceRegistrationDAO().getRaceRegistration(race, boat);
		assertNotNull(registration);
	}
	
	@Test
	public void updateBoatTest() {
		idBoat = 3;
		
		Boat boat = UtilTest.getClub().getBoatDAO().getBoatById(idBoat);
		Race race = UtilTest.getClub().getRaceDAO().getRaceById(idRace);
		
		UtilTest.getClub().getRaceRegistrationDAO().updateRaceRegistration(idRegistration, boat);
		
		RaceRegistration newRegistration = UtilTest.getClub().getRaceRegistrationDAO().getRaceRegistration(race, boat);
		assertAll(
			() -> assertTrue(newRegistration.getRace().getId() == idRace, newRegistration.getRace().getId() + " should equal " + race.getId()),
			() -> assertTrue(newRegistration.getBoat().getId() == idBoat, newRegistration.getBoat().getId() + " should equal " + boat.getId())
		);
		
	}
	
	@AfterAll
	public static void removeRaceRegistration() {
		UtilTest.getClub().getRaceRegistrationDAO().removeRaceRegistration(idRegistration);
		
		RaceRegistration registration = UtilTest.getClub().getRaceRegistrationDAO().getRaceRegistrationById(idRegistration);
		assertNull(registration);
		
	}
}
