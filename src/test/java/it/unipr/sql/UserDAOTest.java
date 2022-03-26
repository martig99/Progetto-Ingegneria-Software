package test.java.it.unipr.sql;

import main.java.it.unipr.model.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;

public class UserDAOTest extends UtilTest {
	
	private static int id = 0; 
	private static String firstName = "Luca";
	private static String lastName = "Bianchi";
	private static String email = "luca.bianchi@gmail.com";
	private static String password = "0000";
	private static String fiscalCode = "BNCLCA73S51F205T";
	private static String address = "Via della Repubblica 1, Milano";
	private static UserType userType = UserType.MEMBER;
	
	@BeforeAll
	public static void createUserTest() {
		User user = UtilTest.getClub().getUserDAO().getUserByEmail(email);
		assertNull(user);
		
		Member member = UtilTest.getClub().getUserDAO().getMemberByFiscalCode(fiscalCode);
		assertNull(member);
		
		UtilTest.getClub().getUserDAO().createUser(firstName, lastName, email, password, fiscalCode, address, false, userType);
		
		Member newMember = UtilTest.getClub().getUserDAO().getMemberByFiscalCode(fiscalCode);
		assertAll(
			() -> assertTrue(newMember.getFirstName().equals(firstName), newMember.getFirstName() + " should equal " + firstName),
			() -> assertTrue(newMember.getLastName().equals(lastName), newMember.getLastName() + " should equal " + lastName),
			() -> assertTrue(newMember.getEmail().equals(email), newMember.getEmail() + " should equal " + email),
			() -> assertTrue(newMember.getPassword().equals(password), newMember.getPassword() + " should equal " + password),
			() -> assertTrue(newMember.getFiscalCode().equals(fiscalCode), newMember.getFiscalCode() + " should equal " + fiscalCode),
			() -> assertTrue(newMember.getAddress().equals(address), newMember.getAddress() + " should equal " + address)
		);

		id = newMember.getId();
	}
	
	@Test
	public void loginTest() {
		User user = UtilTest.getClub().getUserDAO().login(email, password, userType);
		assertNotNull(user);
	}
	
	@Test
	public void getUserByEmailTest() {
		User user = UtilTest.getClub().getUserDAO().getUserByEmail(email);
		assertNotNull(user);
	}
	
	@Test
	public void getMemberByFiscalCodeTest() {
		Member member = UtilTest.getClub().getUserDAO().getMemberByFiscalCode(fiscalCode);
		assertNotNull(member);
	}
	
	@Test
	public void updateFirstNameTest() {
		firstName = "Luigi";
		
		UtilTest.getClub().getUserDAO().updateUser(id, firstName, null, null, null);
		
		User newUser = UtilTest.getClub().getUserDAO().getUserById(id);
		assertAll(
			() -> assertTrue(newUser.getFirstName().equals(firstName), newUser.getFirstName() + " should equal " + firstName),
			() -> assertTrue(newUser.getLastName().equals(lastName), newUser.getLastName() + " should equal " + lastName),
			() -> assertTrue(newUser.getEmail().equals(email), newUser.getEmail() + " should equal " + email),
			() -> assertTrue(newUser.getPassword().equals(password), newUser.getPassword() + " should equal " + password)
		);
	}
	
	@Test
	public void updateLastNameTest() {
		lastName = "Pisano";
		
		UtilTest.getClub().getUserDAO().updateUser(id, null, lastName, null, null);
		
		User newUser = UtilTest.getClub().getUserDAO().getUserById(id);
		assertAll(
			() -> assertTrue(newUser.getFirstName().equals(firstName), newUser.getFirstName() + " should equal " + firstName),
			() -> assertTrue(newUser.getLastName().equals(lastName), newUser.getLastName() + " should equal " + lastName),
			() -> assertTrue(newUser.getEmail().equals(email), newUser.getEmail() + " should equal " + email),
			() -> assertTrue(newUser.getPassword().equals(password), newUser.getPassword() + " should equal " + password)
		);
	}
	
	@Test
	public void updateEmailTest() {
		email = "luigi.pisano@gmail.com";
		
		User user = UtilTest.getClub().getUserDAO().getUserByEmail(email);
		assertNull(user);
		
		UtilTest.getClub().getUserDAO().updateUser(id, null, null, email, null);
		
		User newUser = UtilTest.getClub().getUserDAO().getUserById(id);
		assertAll(
			() -> assertTrue(newUser.getFirstName().equals(firstName), newUser.getFirstName() + " should equal " + firstName),
			() -> assertTrue(newUser.getLastName().equals(lastName), newUser.getLastName() + " should equal " + lastName),
			() -> assertTrue(newUser.getEmail().equals(email), newUser.getEmail() + " should equal " + email),
			() -> assertTrue(newUser.getPassword().equals(password), newUser.getPassword() + " should equal " + password)
		);
	}
	
	@Test
	public void updatePasswordTest() {
		password = "pisa123";
		
		UtilTest.getClub().getUserDAO().updateUser(id, null, null, null, password);
		
		User newUser = UtilTest.getClub().getUserDAO().getUserById(id);
		assertAll(
			() -> assertTrue(newUser.getFirstName().equals(firstName), newUser.getFirstName() + " should equal " + firstName),
			() -> assertTrue(newUser.getLastName().equals(lastName), newUser.getLastName() + " should equal " + lastName),
			() -> assertTrue(newUser.getEmail().equals(email), newUser.getEmail() + " should equal " + email),
			() -> assertTrue(newUser.getPassword().equals(password), newUser.getPassword() + " should equal " + password)
		);
	}
	
	@Test
	public void updateUserTest() {
		firstName = "Marco";
		lastName = "Rossi";
		email = "marco.rossi@gmail.com";
		password = "1234";
		
		User user = UtilTest.getClub().getUserDAO().getUserByEmail(email);
		assertNull(user);
		
		UtilTest.getClub().getUserDAO().updateUser(id, firstName, lastName, email, password);
		
		User newUser = UtilTest.getClub().getUserDAO().getUserById(id);
		assertAll(
			() -> assertTrue(newUser.getFirstName().equals(firstName), newUser.getFirstName() + " should equal " + firstName),
			() -> assertTrue(newUser.getLastName().equals(lastName), newUser.getLastName() + " should equal " + lastName),
			() -> assertTrue(newUser.getEmail().equals(email), newUser.getEmail() + " should equal " + email),
			() -> assertTrue(newUser.getPassword().equals(password), newUser.getPassword() + " should equal " + password)
		);	
	}
	
	@Test
	public void updateFiscalCodeTest() {
		fiscalCode = "CRVCST00S29F463B";
		
		Member member = UtilTest.getClub().getUserDAO().getMemberByFiscalCode(fiscalCode);
		assertNull(member);
		
		UtilTest.getClub().getUserDAO().updateMember(id, fiscalCode, null);
		
		Member newMember = UtilTest.getClub().getUserDAO().getMemberById(id);
		assertAll(
			() -> assertTrue(newMember.getFiscalCode().equals(fiscalCode), newMember.getFiscalCode() + " should equal " + fiscalCode),
			() -> assertTrue(newMember.getAddress().equals(address), newMember.getAddress() + " should equal " + address)
		);	
	}
	
	@Test
	public void updateAddressTest() {
		address = "Via Alcide de Gasperi, Reggio Emilia";
		
		UtilTest.getClub().getUserDAO().updateMember(id, null, address);
		
		Member newMember = UtilTest.getClub().getUserDAO().getMemberById(id);
		assertAll(
			() -> assertTrue(newMember.getFiscalCode().equals(fiscalCode), newMember.getFiscalCode() + " should equal " + fiscalCode),
			() -> assertTrue(newMember.getAddress().equals(address), newMember.getAddress() + " should equal " + address)
		);	
	}

	@Test
	public void updateMemberTest() {
		fiscalCode = "ANRRSS97R12H223L";
		address = "Via Roma 17, Parma";
		
		Member member = UtilTest.getClub().getUserDAO().getMemberByFiscalCode(fiscalCode);
		assertNull(member);
		
		UtilTest.getClub().getUserDAO().updateMember(id, fiscalCode, address);
		
		Member newMember = UtilTest.getClub().getUserDAO().getMemberById(id);
		assertAll(
			() -> assertTrue(newMember.getFiscalCode().equals(fiscalCode), newMember.getFiscalCode() + " should equal " + fiscalCode),
			() -> assertTrue(newMember.getAddress().equals(address), newMember.getAddress() + " should equal " + address)
		);	
	}
	
	@AfterAll
	public static void removeUserTest() {	
		UtilTest.getClub().getUserDAO().removeUser(id);
		
		User user = UtilTest.getClub().getUserDAO().getUserById(id);
		assertNull(user);	
	}
}
