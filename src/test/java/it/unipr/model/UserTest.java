package test.java.it.unipr.model;

import main.java.it.unipr.model.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The class {@code UserTest} defines the test for classes {@code User}, {@code Member} and {@code Employee}.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class UserTest {
	
	private final static int id = 1; 
	private final static String firstName = "Ilaria";
	private final static String lastName = "Rossi";
	private final static String email = "ilaria.rossi@gmail.com";
	private final static String password = "aaaaaaaaa";
	private final static StatusCode statusCode = StatusCode.ACTIVE;
	
	private final static String fiscalCode = "RSSLRI95A41A944A";
	private final static String address = "Via della Pace 11, Bologna";
	
	private final static boolean admin = false;
	
	private static String toString;
	
	/**
	 * Configures some helpful elements for the following tests.
	**/
	@BeforeAll
	public static void setUp() {
		toString = "Id: " + id + " - First Name: " + firstName + " - Last Name: " + lastName + " - Email: " + email + " - Password: " + password;
	}
	
	/**
	 * Performs the test for the setter methods of class {@code User}.
	**/
	@Test
	public void setterTest() {
		User user = new User();
		
		user.setId(id);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		user.setStatusCode(statusCode);
		
		assertAll(
			() -> assertTrue(user.getId() == id, user.getId() + " should equal " + id),
			() -> assertTrue(user.getFirstName().equals(firstName), user.getFirstName() + " should equal " + firstName),
			() -> assertTrue(user.getLastName().equals(lastName), user.getLastName() + " should equal " + lastName),
			() -> assertTrue(user.getEmail().equals(email), user.getEmail() + " should equal " + email),
			() -> assertTrue(user.getPassword().equals(password), user.getPassword() + " should equal " + password),
			() -> assertTrue(user.getStatusCode() == statusCode, user.getStatusCode() + " should equal " + statusCode),
			() -> assertTrue(user.toString().equals(toString), user.toString() + " should equal " + toString)
		);
	}	
	
	/**
	 * Performs the test for the constructor method of class {@code User}.
	**/
	@Test
	public void constructorTest() {
		User user = new User(id, firstName, lastName, email, password, statusCode);
		
		assertAll(
			() -> assertTrue(user.getId() == id, user.getId() + " should equal " + id),
			() -> assertTrue(user.getFirstName().equals(firstName), user.getFirstName() + " should equal " + firstName),
			() -> assertTrue(user.getLastName().equals(lastName), user.getLastName() + " should equal " + lastName),
			() -> assertTrue(user.getEmail().equals(email), user.getEmail() + " should equal " + email),
			() -> assertTrue(user.getPassword().equals(password), user.getPassword() + " should equal " + password),
			() -> assertTrue(user.getStatusCode() == statusCode, user.getStatusCode() + " should equal " + statusCode),
			() -> assertTrue(user.toString().equals(toString), user.toString() + " should equal " + toString)
		);
	}
	
	/**
	 * Performs the test for the setter methods of class {@code Member}.
	**/
	@Test
	public void setterMemberTest() {
		Member member = new Member();
		
		member.setId(id);
		member.setFirstName(firstName);
		member.setLastName(lastName);
		member.setEmail(email);
		member.setPassword(password);
		member.setFiscalCode(fiscalCode);
		member.setAddress(address);
		member.setStatusCode(statusCode);
		
		assertAll(
			() -> assertTrue(member.getId() == id, member.getId() + " should equal " + id),
			() -> assertTrue(member.getFirstName().equals(firstName), member.getFirstName() + " should equal " + firstName),
			() -> assertTrue(member.getLastName().equals(lastName), member.getLastName() + " should equal " + lastName),
			() -> assertTrue(member.getEmail().equals(email), member.getEmail() + " should equal " + email),
			() -> assertTrue(member.getPassword().equals(password), member.getPassword() + " should equal " + password),
			() -> assertTrue(member.getFiscalCode().equals(fiscalCode), member.getFiscalCode() + " should equal " + fiscalCode),
			() -> assertTrue(member.getAddress().equals(address), member.getAddress() + " should equal " + address),
			() -> assertTrue(member.getStatusCode() == statusCode, member.getStatusCode() + " should equal " + statusCode)
		);
	}
	
	/**
	 * Performs the test for the constructor method of class {@code Member}.
	**/
	@Test
	public void constructorMemberTest() {
		Member member = new Member(id, firstName, lastName, email, password, fiscalCode, address, statusCode);
		
		assertAll(
				() -> assertTrue(member.getId() == id, member.getId() + " should equal " + id),
				() -> assertTrue(member.getFirstName().equals(firstName), member.getFirstName() + " should equal " + firstName),
				() -> assertTrue(member.getLastName().equals(lastName), member.getLastName() + " should equal " + lastName),
				() -> assertTrue(member.getEmail().equals(email), member.getEmail() + " should equal " + email),
				() -> assertTrue(member.getPassword().equals(password), member.getPassword() + " should equal " + password),
				() -> assertTrue(member.getFiscalCode().equals(fiscalCode), member.getFiscalCode() + " should equal " + fiscalCode),
				() -> assertTrue(member.getAddress().equals(address), member.getAddress() + " should equal " + address),
				() -> assertTrue(member.getStatusCode() == statusCode, member.getStatusCode() + " should equal " + statusCode)
			);
	}
	
	/**
	 * Performs the test for the setter methods of class {@code Employee}.
	**/
	@Test
	public void setterEmployeeTest() {
		Employee employee = new Employee();
		
		employee.setId(id);
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setEmail(email);
		employee.setPassword(password);
		employee.setAdministrator(admin);
		employee.setStatusCode(statusCode);
		
		assertAll(
			() -> assertTrue(employee.getId() == id, employee.getId() + " should equal " + id),
			() -> assertTrue(employee.getFirstName().equals(firstName), employee.getFirstName() + " should equal " + firstName),
			() -> assertTrue(employee.getLastName().equals(lastName), employee.getLastName() + " should equal " + lastName),
			() -> assertTrue(employee.getEmail().equals(email), employee.getEmail() + " should equal " + email),
			() -> assertTrue(employee.getPassword().equals(password), employee.getPassword() + " should equal " + password),
			() -> assertTrue(employee.isAdministrator() == admin, employee.isAdministrator() + " should equal " + admin),
			() -> assertTrue(employee.getStatusCode() == statusCode, employee.getStatusCode() + " should equal " + statusCode)
		);
	}
	
	/**
	 * Performs the test for the constructor method of class {@code Employee}.
	**/
	@Test
	public void constructorEmployeeTest() {
		Employee employee = new Employee(id, firstName, lastName, email, password, admin, statusCode);
		
		assertAll(
				() -> assertTrue(employee.getId() == id, employee.getId() + " should equal " + id),
				() -> assertTrue(employee.getFirstName().equals(firstName), employee.getFirstName() + " should equal " + firstName),
				() -> assertTrue(employee.getLastName().equals(lastName), employee.getLastName() + " should equal " + lastName),
				() -> assertTrue(employee.getEmail().equals(email), employee.getEmail() + " should equal " + email),
				() -> assertTrue(employee.getPassword().equals(password), employee.getPassword() + " should equal " + password),
				() -> assertTrue(employee.isAdministrator() == admin, employee.isAdministrator() + " should equal " + admin),
				() -> assertTrue(employee.getStatusCode() == statusCode, employee.getStatusCode() + " should equal " + statusCode)
			);
	}
}
