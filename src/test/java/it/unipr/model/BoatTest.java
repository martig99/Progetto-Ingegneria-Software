package test.java.it.unipr.model;

import main.java.it.unipr.model.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
  * The class {@code BoatTest} defines the test for class {@code Boat}. 
  * 
  * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
  * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
class BoatTest {
	
	private final static int id = 1;
	private final static String name = "Yacht";
	private final static int length = 1;
	private final static StatusCode statusCode = StatusCode.ACTIVE;
	
	private static Member member;
	private static String toString;

	/**
	 * Configures some helpful elements for the following tests.
	**/
	@BeforeAll
	public static void setUp() {
		member = new Member(1, "Ilaria", "Rossi", "ilaria.rossi@gmail.com", "aaaaaaaaa", "RSSLRI95A41A944A", "Via della Pace 11, Bologna", StatusCode.ACTIVE);
		toString = "Id: " + id + " - Name: " + name + " - Length: " + length + " - Owner: " + member.getEmail() + " - Status Code: " + statusCode.toString();
	}
	
	/**
	 * Performs the test for the setter methods.
	**/
	@Test 
	public void setterTest() {
		Boat boat = new Boat();
		
		boat.setId(id);
		boat.setName(name);
		boat.setLength(length);
		boat.setOwner(member);
		boat.setStatusCode(StatusCode.ACTIVE);
		
		assertAll(
			() -> assertTrue(boat.getId() == id, boat.getId() + " should equal " + id),
			() -> assertTrue(boat.getName() == name, boat.getName() + " should equal " + name),
			() -> assertTrue(boat.getLength() == length, boat.getLength() + " should equal " + length),
			() -> assertTrue(boat.getOwner().getId() == member.getId(),  boat.getOwner().getId() + " should equal " + member.getId()),
			() -> assertTrue(boat.getStatusCode() == statusCode, boat.getStatusCode() + " should equal " + statusCode),
			() -> assertTrue(boat.toString().equals(toString), boat.toString() + " should equal " + toString)
		);
	}
	
	/**
	 * Performs the test for the constructor method.
	**/
	@Test
	public void constructorTest() {
		Boat boat = new Boat(id, name, length, member, statusCode);
		
		assertAll(
			() -> assertTrue(boat.getId() == id, boat.getId() + " should equal " + id),
			() -> assertTrue(boat.getName() == name, boat.getName() + " should equal " + name),
			() -> assertTrue(boat.getLength() == length, boat.getLength() + " should equal " + length),
			() -> assertTrue(boat.getOwner().getId() == member.getId(),  boat.getOwner().getId() + " should equal " + member.getId()),
			() -> assertTrue(boat.getStatusCode() == statusCode, boat.getStatusCode() + " should equal " + statusCode),
			() -> assertTrue(boat.toString().equals(toString), boat.toString() + " should equal " + toString)
		);
	}
}