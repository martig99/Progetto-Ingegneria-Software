package it.unipr.java.test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import it.unipr.java.model.*;


public class UserTest {
	
	final int id = 1; 
	final String firstName = "Ilaria";
	final String lastName = "Rossi";
	final String email = "ilaria.rossi@gmail.com";
	final String password = "aaaaaaaaa";
	final String fiscalCode = "RSSLRI95A41A944A";
	final String address = "Via della Pace 11, Bologna";
	
	@Test
	void initialTest()
	{
		String toString = "Id: " + id + " - First Name: " + firstName + " - Last Name: " + lastName + " - Email: " + email + " - Password: " + password;
		
		User u = new User();
		u.setId(id);
		u.setFirstName(firstName);
		u.setLastName(lastName);
		u.setEmail(email);
		u.setPassword(password);

		assertEquals(id, u.getId(),
				() -> u.getId() + " should equal " + id);
		assertEquals(firstName, u.getFirstName(),
				() -> u.getFirstName() + " should equal " + firstName);
		assertEquals(lastName, u.getLastName(),
				() -> u.getLastName() + " should equal " + lastName);
		assertEquals(email, u.getEmail(),
				() -> u.getEmail() + " should equal " + email);
		assertEquals(password, u.getPassword(),
				() -> u.getPassword() + " should equal " + password);
		assertEquals(toString, u.toString(),
				() -> u.toString() + " should equal " + toString);
		
		User u2 = new User(id,firstName,lastName,email,password);
		assertEquals(id, u2.getId(),
				() -> u2.getId() + " should equal " + id);
		assertEquals(firstName, u2.getFirstName(),
				() -> u2.getFirstName() + " should equal " + firstName);
		assertEquals(lastName, u2.getLastName(),
				() -> u2.getLastName() + " should equal " + lastName);
		assertEquals(email, u2.getEmail(),
				() -> u2.getEmail() + " should equal " + email);
		assertEquals(password, u2.getPassword(),
				() -> u2.getPassword() + " should equal " + password);
		assertEquals(toString, u2.toString(),
				() -> u2.toString() + " should equal " + toString);
	}
	
	@Test
	void loginTest()
	{
		SailingClub s = new SailingClub();
		User u = s.login(email, password, UserType.MEMBER);
		assertEquals(id, u.getId(),
				() -> u.getId() + " should equal " + id);
		
		int idEmployee = 2; //va bene così o devo creare tutti i campi come sono quelli globali e controllare solo quei due tipo creare il campo email e il campo password e poi controllare l'id?
		User result = s.getUserById(idEmployee);
		User u2 = s.login(result.getEmail(), result.getPassword(), UserType.EMPLOYEE);
		assertEquals(idEmployee, u2.getId(),
				() -> u2.getId() + " should equal " + idEmployee);
	}
	
	@Test
	void getUserByEmailTest()
	{
		SailingClub s = new SailingClub();
		User u = s.getUserByEmail(email);
		assertEquals(id, u.getId(),
				() -> u.getId() + " should equal " + id);	
	}
	
	@Test
	void getMemberByFiscalCodeTest()
	{
		SailingClub s = new SailingClub();
		Member m = s.getMemberByFiscalCode(fiscalCode);
		assertEquals(id, m.getId(),
				() -> m.getId() + " should equal " + id);	
	}
	
	@Test
	void updateUserTest()
	{
		SailingClub s = new SailingClub();

		String newFirstName = "Luigi";
		s.updateUser(id, newFirstName, null, null, null); //faccio l'assert solo per quello modificato o anche per quelli che non dovrebbe modificare per far vedere che sono rimasti uguali
		User u = s.getUserById(id);
		assertEquals(newFirstName, u.getFirstName(),
				() -> u.getFirstName() + " should equal " + newFirstName);
		assertEquals(lastName, u.getLastName(),
				() -> u.getLastName() + " should equal " + lastName);
		assertEquals(email, u.getEmail(),
				() -> u.getEmail() + " should equal " + email);
		assertEquals(password, u.getPassword(),
				() -> u.getPassword() + " should equal " + password);
		
		
		String newLastName = "Pisano";
		s.updateUser(id, null, newLastName, null, null);
		User u2 = s.getUserById(id);
		assertEquals(newFirstName, u2.getFirstName(),
				() -> u2.getFirstName() + " should equal " + newFirstName);
		assertEquals(newLastName, u2.getLastName(),
				() -> u2.getLastName() + " should equal " + newLastName);
		assertEquals(email, u2.getEmail(),
				() -> u2.getEmail() + " should equal " + email);
		assertEquals(password, u2.getPassword(),
				() -> u2.getPassword() + " should equal " + password);
		
		
		String newEmail = "luigi.pisano@gmail.com";
		s.updateUser(id, null, null, newEmail, null);
		User u3 = s.getUserById(id);
		assertEquals(newFirstName, u3.getFirstName(),
				() -> u3.getFirstName() + " should equal " + newFirstName);
		assertEquals(newLastName, u3.getLastName(),
				() -> u3.getLastName() + " should equal " + newLastName);
		assertEquals(newEmail, u3.getEmail(),
				() -> u3.getEmail() + " should equal " + newEmail);
		assertEquals(password, u3.getPassword(),
				() -> u3.getPassword() + " should equal " + password);
		
		
		String newPassword = "pisa123";
		s.updateUser(id, null, null, null, newPassword);
		User u4 = s.getUserById(id);
		assertEquals(newFirstName, u4.getFirstName(),
				() -> u4.getFirstName() + " should equal " + newFirstName);
		assertEquals(newLastName, u4.getLastName(),
				() -> u4.getLastName() + " should equal " + newLastName);
		assertEquals(newEmail, u4.getEmail(),
				() -> u4.getEmail() + " should equal " + newEmail);
		assertEquals(newPassword, u4.getPassword(),
				() -> u4.getPassword() + " should equal " + newPassword);
		
		
		s.updateUser(id, firstName, lastName, email, password);
		User oldU = s.getUserById(id);
		assertEquals(firstName, oldU.getFirstName(),
				() -> oldU.getFirstName() + " should equal " + firstName);
		assertEquals(lastName, oldU.getLastName(),
				() -> oldU.getLastName() + " should equal " + lastName);
		assertEquals(email, oldU.getEmail(),
				() -> oldU.getEmail() + " should equal " + email);
		assertEquals(password, oldU.getPassword(),
				() -> oldU.getPassword() + " should equal " + password);
	}
	
	@Test
	void updateMemberTest()
	{
		SailingClub s = new SailingClub();

		String newFiscalCode = "CRVCST00S29F463B";
		s.updateMember(id, newFiscalCode, null);
		Member m = s.getMemberById(id);
		assertEquals(newFiscalCode, m.getFiscalCode(),
				() -> m.getFiscalCode() + " should equal " + newFiscalCode);
		assertEquals(address, m.getAddress(),
				() -> m.getAddress() + " should equal " + address);
		
		String newAddress = "Via Alcide de Gasperi, Reggio Emilia";
		s.updateMember(id, null, newAddress);
		Member m2 = s.getMemberById(id);
		assertEquals(newFiscalCode, m2.getFiscalCode(),
				() -> m2.getFiscalCode() + " should equal " + newFiscalCode);
		assertEquals(newAddress, m2.getAddress(),
				() -> m2.getAddress() + " should equal " + newAddress);
		
		s.updateMember(id, fiscalCode, address);
		Member oldM = s.getMemberById(id);
		assertEquals(fiscalCode, oldM.getFiscalCode(),
				() -> oldM.getFiscalCode() + " should equal " + fiscalCode);
		assertEquals(address, oldM.getAddress(),
				() -> oldM.getAddress() + " should equal " + address);
	}
	
	/*
	@Test
	void createUserTest()
	{
		SailingClub s = new SailingClub();
		String fCode = "LTSNET88R22A463B",fName = "nTest", lName = "lTest", em = "prova@test.com", pw = "pwTest", add = "via Test 22 Bologna"; 
		s.createUser(fCode, fName, lName, em, pw, add, false, UserType.MEMBER);
		Member m = s.getMemberByFiscalCode(fCode);
		
		assertEquals(fCode, m.getFiscalCode(),
				() -> m.getFiscalCode() + " should equal " + fCode);
		assertEquals(fName, m.getFirstName(),
				() -> m.getFirstName() + " should equal " + fName);
		assertEquals(lName, m.getLastName(),
				() -> m.getLastName() + " should equal " + lName);
		assertEquals(em, m.getEmail(),
				() -> m.getEmail() + " should equal " + em);
		assertEquals(pw, m.getPassword(),
				() -> m.getPassword() + "should equal " + pw);
		assertEquals(add, m.getAddress(),
				() -> m.getAddress() + "should equal " + add);
	}
	
	
	@Test
	void removeUserTest()
	{
		String em = "prova@test.com"; 
		SailingClub s = new SailingClub();
		User u = s.getUserByEmail(em);
		s.removeUser(u.getId());
		User u2 = s.getUserByEmail(em);
		assertEquals(null,u2,
			() -> u2 + " should equal " + null);
			
	}
	*/
}
