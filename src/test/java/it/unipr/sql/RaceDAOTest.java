package test.java.it.unipr.sql;

import main.java.it.unipr.model.*;

import java.sql.Date;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class RaceDAOTest extends UtilTest {

	private static int id = 0;
	private static String name = "Gara 1";
	private static String place = "Roma";
	private static Date date = Date.valueOf("2022-03-08");
	private static int boatsNumber = 3;
	private static float registrationFee = 10;
	private static Date endDateRegistration = Date.valueOf("2022-03-07");
	
	@BeforeAll
	public static void insertRaceTest() {
		UtilTest.getClub().getRaceDAO().insertRace(name, place, date, boatsNumber, registrationFee, endDateRegistration);
		
		Race newRace = UtilTest.getClub().getRaceDAO().getRaceByDate(date);
		assertAll(
			() -> assertTrue(newRace.getName().equals(name), newRace.getName() + " should equal " + name),
			() -> assertTrue(newRace.getPlace().equals(place), newRace.getPlace() + " should equal " + place),
			() -> assertTrue(newRace.getDate().equals(date), newRace.getDate() + " should equal " + date),
			() -> assertTrue(newRace.getBoatsNumber() == boatsNumber, newRace.getBoatsNumber() + " should equal " + boatsNumber),
			() -> assertTrue(newRace.getRegistrationFee() == registrationFee, newRace.getRegistrationFee() + " should equal " + registrationFee),
			() -> assertTrue(newRace.getEndDateRegistration().equals(endDateRegistration), newRace.getEndDateRegistration() + " should equal " + endDateRegistration)
		);
		
		id = newRace.getId();
	}
	
	@Test
	public void getRaceByDateTest() {
		Race race = UtilTest.getClub().getRaceDAO().getRaceByDate(date);		
		assertNotNull(race);
	}
	
	@Test
	public void updateNameTest() {
		name = "Gara 100";		
		UtilTest.getClub().getRaceDAO().updateRace(id, name, null, null, 0, null, null);
		
		Race newRace = UtilTest.getClub().getRaceDAO().getRaceById(id);
		assertAll(
			() -> assertTrue(newRace.getName().equals(name), newRace.getName() + " should equal " + name),
			() -> assertTrue(newRace.getPlace().equals(place), newRace.getPlace() + " should equal " + place),
			() -> assertTrue(newRace.getDate().equals(date), newRace.getDate() + " should equal " + date),
			() -> assertTrue(newRace.getBoatsNumber() == boatsNumber, newRace.getBoatsNumber() + " should equal " + boatsNumber),
			() -> assertTrue(newRace.getRegistrationFee() == registrationFee, newRace.getRegistrationFee() + " should equal " + registrationFee),
			() -> assertTrue(newRace.getEndDateRegistration().equals(endDateRegistration), newRace.getEndDateRegistration() + " should equal " + endDateRegistration)
		);
	}
	
	@Test
	public void updatePlaceTest() {
		place = "Genova";
		UtilTest.getClub().getRaceDAO().updateRace(id, null, place, null, 0, null, null);
		
		Race newRace = UtilTest.getClub().getRaceDAO().getRaceById(id);
		assertAll(
			() -> assertTrue(newRace.getName().equals(name), newRace.getName() + " should equal " + name),
			() -> assertTrue(newRace.getPlace().equals(place), newRace.getPlace() + " should equal " + place),
			() -> assertTrue(newRace.getDate().equals(date), newRace.getDate() + " should equal " + date),
			() -> assertTrue(newRace.getBoatsNumber() == boatsNumber, newRace.getBoatsNumber() + " should equal " + boatsNumber),
			() -> assertTrue(newRace.getRegistrationFee() == registrationFee, newRace.getRegistrationFee() + " should equal " + registrationFee),
			() -> assertTrue(newRace.getEndDateRegistration().equals(endDateRegistration), newRace.getEndDateRegistration() + " should equal " + endDateRegistration)
		);
	}
	
	@Test
	public void updateDateTest() {
		date = Date.valueOf("2022-03-10");
		
		UtilTest.getClub().getRaceDAO().updateRace(id, null, null, date, 0, null, null);
		
		Race newRace = UtilTest.getClub().getRaceDAO().getRaceById(id);
		assertAll(
			() -> assertTrue(newRace.getName().equals(name), newRace.getName() + " should equal " + name),
			() -> assertTrue(newRace.getPlace().equals(place), newRace.getPlace() + " should equal " + place),
			() -> assertTrue(newRace.getDate().equals(date), newRace.getDate() + " should equal " + date),
			() -> assertTrue(newRace.getBoatsNumber() == boatsNumber, newRace.getBoatsNumber() + " should equal " + boatsNumber),
			() -> assertTrue(newRace.getRegistrationFee() == registrationFee, newRace.getRegistrationFee() + " should equal " + registrationFee),
			() -> assertTrue(newRace.getEndDateRegistration().equals(endDateRegistration), newRace.getEndDateRegistration() + " should equal " + endDateRegistration)
		);
	}
	
	@Test
	public void updateBoatsNumberTest() {
		boatsNumber = 5;
		UtilTest.getClub().getRaceDAO().updateRace(id, null, null, null, boatsNumber, null, null);
		
		Race newRace = UtilTest.getClub().getRaceDAO().getRaceById(id);
		assertAll(
			() -> assertTrue(newRace.getName().equals(name), newRace.getName() + " should equal " + name),
			() -> assertTrue(newRace.getPlace().equals(place), newRace.getPlace() + " should equal " + place),
			() -> assertTrue(newRace.getDate().equals(date), newRace.getDate() + " should equal " + date),
			() -> assertTrue(newRace.getBoatsNumber() == boatsNumber, newRace.getBoatsNumber() + " should equal " + boatsNumber),
			() -> assertTrue(newRace.getRegistrationFee() == registrationFee, newRace.getRegistrationFee() + " should equal " + registrationFee),
			() -> assertTrue(newRace.getEndDateRegistration().equals(endDateRegistration), newRace.getEndDateRegistration() + " should equal " + endDateRegistration)
		);
	}
	
	@Test
	public void updateRegistrationFeeTest() {
		registrationFee = 22;	
		UtilTest.getClub().getRaceDAO().updateRace(id, null, null, null, 0, registrationFee, null);
		
		Race newRace = UtilTest.getClub().getRaceDAO().getRaceById(id);
		assertAll(
			() -> assertTrue(newRace.getName().equals(name), newRace.getName() + " should equal " + name),
			() -> assertTrue(newRace.getPlace().equals(place), newRace.getPlace() + " should equal " + place),
			() -> assertTrue(newRace.getDate().equals(date), newRace.getDate() + " should equal " + date),
			() -> assertTrue(newRace.getBoatsNumber() == boatsNumber, newRace.getBoatsNumber() + " should equal " + boatsNumber),
			() -> assertTrue(newRace.getRegistrationFee() == registrationFee, newRace.getRegistrationFee() + " should equal " + registrationFee),
			() -> assertTrue(newRace.getEndDateRegistration().equals(endDateRegistration), newRace.getEndDateRegistration() + " should equal " + endDateRegistration)
		);
	}
	
	@Test
	public void updateEndDateRegistrationTest() {
		endDateRegistration = Date.valueOf("2022-03-08");
		UtilTest.getClub().getRaceDAO().updateRace(id, null, null, null, 0, null, endDateRegistration);
		
		Race newRace = UtilTest.getClub().getRaceDAO().getRaceById(id);
		assertAll(
			() -> assertTrue(newRace.getName().equals(name), newRace.getName() + " should equal " + name),
			() -> assertTrue(newRace.getPlace().equals(place), newRace.getPlace() + " should equal " + place),
			() -> assertTrue(newRace.getDate().equals(date), newRace.getDate() + " should equal " + date),
			() -> assertTrue(newRace.getBoatsNumber() == boatsNumber, newRace.getBoatsNumber() + " should equal " + boatsNumber),
			() -> assertTrue(newRace.getRegistrationFee() == registrationFee, newRace.getRegistrationFee() + " should equal " + registrationFee),
			() -> assertTrue(newRace.getEndDateRegistration().equals(endDateRegistration), newRace.getEndDateRegistration() + " should equal " + endDateRegistration)
		);
	}
	
	@Test
	public void updateRaceTest() {
		name = "Nettuno";
		place = "Rimini";
		date = Date.valueOf("2022-03-20");
		boatsNumber = 7;
		registrationFee = 15;
		endDateRegistration = Date.valueOf("2022-03-15");
		
		UtilTest.getClub().getRaceDAO().updateRace(id, name, place, date, boatsNumber, registrationFee, endDateRegistration);
		
		Race newRace = UtilTest.getClub().getRaceDAO().getRaceById(id);
		assertAll(
			() -> assertTrue(newRace.getName().equals(name), newRace.getName() + " should equal " + name),
			() -> assertTrue(newRace.getPlace().equals(place), newRace.getPlace() + " should equal " + place),
			() -> assertTrue(newRace.getDate().equals(date), newRace.getDate() + " should equal " + date),
			() -> assertTrue(newRace.getBoatsNumber() == boatsNumber, newRace.getBoatsNumber() + " should equal " + boatsNumber),
			() -> assertTrue(newRace.getRegistrationFee() == registrationFee, newRace.getRegistrationFee() + " should equal " + registrationFee),
			() -> assertTrue(newRace.getEndDateRegistration().equals(endDateRegistration), newRace.getEndDateRegistration() + " should equal " + endDateRegistration)
		);
	} 
	
	@AfterAll
	public static void removeRaceTest() {
		UtilTest.getClub().getRaceDAO().removeRace(id);
		
		Race race = UtilTest.getClub().getRaceDAO().getRaceById(id);
		assertNull(race);
	}

}
