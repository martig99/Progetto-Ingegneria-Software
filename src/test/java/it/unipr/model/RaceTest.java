package test.java.it.unipr.model;

import main.java.it.unipr.model.*;

import java.sql.Date;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The class {@code RaceTest} defines the test for class {@code Race}. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class RaceTest {
	
	private final static int id = 1;
	private final static String name = "Gara 1";
	private final static String place = "Roma";
	private final static Date date = Date.valueOf("2022-03-08");
	private final static int boatsNumber = 3;
	private final static float registrationFee = 10;
	private final static Date endDateRegistration = Date.valueOf("2022-03-07");
	private final static StatusCode statusCode = StatusCode.ACTIVE;
	
	private static String toString;
	
	/**
	 * Configures some helpful elements for the following tests.
	**/
	@BeforeAll
	public static void setUp() {
		toString = "Id: " + id + " - Name: " + name + " - Place: " + place + " - Date: " + date + " - Boats Number: " + boatsNumber + " - Registration Fee: " + registrationFee + " - End Date Registration: " + endDateRegistration + " - Status Code: " + statusCode.toString();
	}
	
	/**
	 * Performs the test for the setter methods.
	**/
	@Test
	public void setterTest() {
		Race race = new Race();
		
		race.setId(id);
		race.setName(name);
		race.setPlace(place);
		race.setDate(date);
		race.setBoatsNumber(boatsNumber);
		race.setRegistrationFee(registrationFee);
		race.setEndDateRegistration(endDateRegistration);
		race.setStatusCode(statusCode);
		
		assertAll(
			() -> assertTrue(race.getId() == id, race.getId() + " should equal " + id),
			() -> assertTrue(race.getName().equals(name), race.getName() + " should equal " + name),
			() -> assertTrue(race.getPlace().equals(place), race.getPlace() + " should equal " + place),
			() -> assertTrue(race.getDate() == date, race.getDate() + " should equal " + date),
			() -> assertTrue(race.getBoatsNumber() == boatsNumber, race.getBoatsNumber() + " should equal " + boatsNumber),
			() -> assertTrue(race.getRegistrationFee() == registrationFee, race.getRegistrationFee() + " should equal " + registrationFee),
			() -> assertTrue(race.getEndDateRegistration() == endDateRegistration, race.getEndDateRegistration() + " should equal " + endDateRegistration),
			() -> assertTrue(race.getStatusCode() == statusCode, race.getStatusCode() + " should equal " + statusCode),
			() -> assertTrue(race.toString().equals(toString), race.toString() + " should equal " + toString)
		);		
	}
	
	/**
	 * Performs the test for the constructor method.
	**/
	@Test
	public void constructorTest() {
		Race race = new Race(id, name, place, date, boatsNumber, registrationFee, endDateRegistration, statusCode);
		
		assertAll(
			() -> assertTrue(race.getId() == id, race.getId() + " should equal " + id),
			() -> assertTrue(race.getName().equals(name), race.getName() + " should equal " + name),
			() -> assertTrue(race.getPlace().equals(place), race.getPlace() + " should equal " + place),
			() -> assertTrue(race.getDate() == date, race.getDate() + " should equal " + date),
			() -> assertTrue(race.getBoatsNumber() == boatsNumber, race.getBoatsNumber() + " should equal " + boatsNumber),
			() -> assertTrue(race.getRegistrationFee() == registrationFee, race.getRegistrationFee() + " should equal " + registrationFee),
			() -> assertTrue(race.getEndDateRegistration() == endDateRegistration, race.getEndDateRegistration() + " should equal " + endDateRegistration),
			() -> assertTrue(race.getStatusCode() == statusCode, race.getStatusCode() + " should equal " + statusCode),
			() -> assertTrue(race.toString().equals(toString), race.toString() + " should equal " + toString)
		);	
	}
}
