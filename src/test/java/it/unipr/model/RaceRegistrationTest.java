package test.java.it.unipr.model;

import main.java.it.unipr.model.*;

import java.sql.Date;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The class {@code RaceRegistrationTest} defines the test for class {@code RaceRegistration}. 
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class RaceRegistrationTest {
	
	private final static int id = 1;
	private final static Date date = Date.valueOf("2022-03-06");
	private final static StatusCode statusCode = StatusCode.ACTIVE;
	
	private static Boat boat;
	private static Race race;
	private static String toString;
		
	/**
	 * Configures some helpful elements for the following tests.
	**/
	@BeforeAll
	public static void setUp() {
		Member member = new Member(1, "Ilaria", "Rossi", "ilaria.rossi@gmail.com", "aaaaaaaaa", "RSSLRI95A41A944A", "Via della Pace 11, Bologna", StatusCode.ACTIVE);
		boat = new Boat(1, "Yacht", 1, member, StatusCode.ACTIVE);
		race = new Race(1, "Gara 1", "Roma", Date.valueOf("2022-03-08"), 3, 10, Date.valueOf("2022-03-07"), StatusCode.ACTIVE);
		
		toString = "Id: " + id + " - Date: " + date + " - Race: [" + race.getDate() + ", " + race.getName() + "] - Boat: " + boat.getId() + " - Status Code: " + StatusCode.ACTIVE.toString();
	}
	
	/**
	 * Performs the test for the setter methods.
	**/
	@Test
	public void setterTest() {
		RaceRegistration registration = new RaceRegistration();
		
		registration.setId(id);
		registration.setDate(date);
		registration.setRace(race);
		registration.setBoat(boat);
		registration.setStatusCode(statusCode);
		
		assertAll(
			() -> assertTrue(registration.getId() == id, registration.getId() + " should equal " + id),
			() -> assertTrue(registration.getDate() == date, registration.getDate() + " should equal " + date),
			() -> assertTrue(registration.getRace() == race, registration.getRace().toString() + " should equal " + race.toString()),
			() -> assertTrue(registration.getBoat() == boat, registration.getBoat().toString() + " should equal " + boat.toString()),
			() -> assertTrue(registration.getStatusCode() == statusCode, registration.getStatusCode() + " should equal " + statusCode),
			() -> assertTrue(registration.toString().equals(toString), registration.toString() + " should equal " + toString)
		);
	}
	
	/**
	 * Performs the test for the constructor method.
	**/
	@Test
	public void constructorTest() {
		RaceRegistration registration = new RaceRegistration(id, date, race, boat, statusCode);
		
		assertAll(
			() -> assertTrue(registration.getId() == id, registration.getId() + " should equal " + id),
			() -> assertTrue(registration.getDate() == date, registration.getDate() + " should equal " + date),
			() -> assertTrue(registration.getRace() == race, registration.getRace().toString() + " should equal " + race.toString()),
			() -> assertTrue(registration.getBoat() == boat, registration.getBoat().toString() + " should equal " + boat.toString()),
			() -> assertTrue(registration.getStatusCode() == statusCode, registration.getStatusCode() + " should equal " + statusCode),
			() -> assertTrue(registration.toString().equals(toString), registration.toString() + " should equal " + toString)
		);
		
	}
}
