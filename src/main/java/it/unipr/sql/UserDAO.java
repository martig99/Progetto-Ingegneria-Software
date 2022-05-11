package main.java.it.unipr.sql;

import main.java.it.unipr.model.*;

import java.sql.*;
import java.util.*;

/**
 * The class {@code UserDAO} defines a model for the management of the query of the entities Users, Members and Employees of the database.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class UserDAO {
	
	/**
	 * Performs user's login.
	 * 
	 * @param email the login email address.
	 * @param password the login password.
	 * @param type the user type.
	 * @return the reference of the user or <code>null</code>.
	**/
	public User login(final String email, final String password, final UserType type) {		
		try {
			String query = "";
			if (type == UserType.MEMBER)
				query = "SELECT * FROM Users JOIN Members ON IdUser = IdMember WHERE Email = ? AND Password = ? AND StatusCode <> ?";
			else if (type == UserType.EMPLOYEE)
				query = "SELECT * FROM Users JOIN Employees ON IdUser = IdEmployee WHERE Email = ? AND Password = ? AND StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			pstmt.setInt(3, StatusCode.ELIMINATED.getValue());
						
			ResultSet rset = pstmt.executeQuery();			
			if (rset.next()) {
				if (!password.contentEquals(rset.getString("Password"))) {
					return null;
				} else {
					if (type == UserType.MEMBER) {
						return DBUtil.setMemberFromResultSet(rset);
					} else if (type == UserType.EMPLOYEE) {
						return DBUtil.setEmployeeFromResultSet(rset);
					}
				}
			}
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Creates a new user.
	 *
	 * @param firstName the first name of the new user.
	 * @param lastName the last name of the new user.
	 * @param email the email address of the new user.
	 * @param password the password of the new user.
	 * @param fiscalCode the fiscal code of the new member.
	 * @param address the address of the new member.
	 * @param admin <code>true</code> if the new employee is an administrator.
	 * @param type the type of user to be inserted.
	**/
	public void createUser(final String firstName, final String lastName, final String email, final String password, final String fiscalCode, final String address, final boolean admin, final UserType type) {			
		this.insertUser(firstName, lastName, email, password);
		User newUser = this.getUserByEmail(email);
		
		if (type.equals(UserType.MEMBER)) {
			this.insertMember(newUser.getId(), fiscalCode, address);
		} else {
			this.insertEmployee(newUser.getId(), admin);
		}
	}
	
	/**
	 * Gets the user in the database given the email.
	 * 
	 * @param email the email address.
	 * @return the reference of the user or <code>null</code>.
	**/
	public User getUserByEmail(final String email) {
		try {
			String query = "SELECT * FROM Users WHERE Email = ? AND StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setString(1, email);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
						
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return DBUtil.setUserFromResultSet(rset);
			}
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Gets the user in the database given the email and the type of user.
	 * 
	 * @param email the email address.
	 * @param type the type of user.
	 * @return the reference of the user or <code>null</code>.
	**/
	public User getUserByEmail(final String email, final UserType type) {
		try {
			String query = "";
			if (type == UserType.MEMBER) 
				query = "SELECT * FROM Users JOIN Members ON IdUser = IdMember WHERE Email = ? AND StatusCode <> ?";
			else
				query = "SELECT * FROM Users JOIN Employees ON IdUser = IdEmployee WHERE Email = ? AND StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setString(1, email);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
						
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				if (type == UserType.MEMBER)
					return DBUtil.setMemberFromResultSet(rset);
				else
					return DBUtil.setEmployeeFromResultSet(rset);
			}
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Gets the member in the database given the fiscal code.
	 * 
	 * @param fiscalCode the fiscal code.
	 * @return the reference of the member or <code>null</code>.
	**/
	public Member getMemberByFiscalCode(final String fiscalCode) {
		try {
			String query = "SELECT * FROM Users JOIN Members ON IdMember = IdUser WHERE FiscalCode = ? AND StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setString(1, fiscalCode);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
						
			ResultSet rset = pstmt.executeQuery();
			if (rset.next())
				return DBUtil.setMemberFromResultSet(rset);
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Inserts a new user in the database.
	 * 
	 * @param firstName the first name of the new user.
	 * @param lastName the last name of the new user.
	 * @param email the email address of the new user.
	 * @param password the password of the new user.
	**/
	public void insertUser(final String firstName, final String lastName, final String email, final String password) {
		try {
			String query = "INSERT INTO Users (FirstName, LastName, Email, Password, StatusCode) VALUES (?,?,?,?,?)";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setString(1, firstName);	
			pstmt.setString(2, lastName);
			pstmt.setString(3, email);
			pstmt.setString(4, password);
			pstmt.setInt(5, StatusCode.ACTIVE.getValue());
						
			pstmt.executeUpdate();
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Gets the user in the database given the unique identifier.
	 * 
	 * @param id the unique identifier.
	 * @return the reference of the user or <code>null</code>.
	**/
	public User getUserById(final int id) {
		Member member = (Member) this.getMemberById(id);
		if (member != null) {
			return member;
		}
		
		Employee employee = (Employee) this.getEmployeeById(id);
		if (employee != null) {
			return employee;
		}
		
		return null;
	}
	
	/**
	 * Gets the club member in the database given the unique identifier.
	 * 
	 * @param id the unique identifier.
	 * @return the reference of the member or <code>null</code>.
	**/
	public Member getMemberById(final int id) {
		try {
			String query = "SELECT * FROM Users JOIN Members ON IdUser = IdMember WHERE IdMember = ? AND StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
						
			ResultSet rset = pstmt.executeQuery();
			if (rset.next())
				return DBUtil.setMemberFromResultSet(rset);
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Gets the club employee in the database given the unique identifier.
	 * 
	 * @param id the unique identifier.
	 * @return the reference of the employee or <code>null</code>.
	**/
	public User getEmployeeById(final int id) {
		try {
			String query = "SELECT * FROM Users JOIN Employees ON IdUser = IdEmployee WHERE IdEmployee = ? AND StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
						
			ResultSet rset = pstmt.executeQuery();
			if (rset.next())
				return DBUtil.setEmployeeFromResultSet(rset);
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Gets the list of all the users.
	 * 
	 * @param type the type of users to be selected.
	 * @return the list.
	**/
	public ArrayList<User> getAllUsers(final UserType type) {
		ArrayList<User> list = new ArrayList<User>();
		try {
			String query = "";
			if (type == UserType.MEMBER) {
				query = "SELECT * FROM Users JOIN Members ON IdMember = IdUser WHERE StatusCode <> ? ORDER BY IdUser";			
			} else if (type == UserType.EMPLOYEE) {
				query = "SELECT * FROM Users JOIN Employees ON IdEmployee = IdUser WHERE StatusCode <> ? ORDER BY IdUser";			
			}
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, StatusCode.ELIMINATED.getValue());
						
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				if (type == UserType.MEMBER) {
					list.add(DBUtil.setMemberFromResultSet(rset));
				} else if (type == UserType.EMPLOYEE) {
					list.add(DBUtil.setEmployeeFromResultSet(rset));
				}
			}
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * Gets the email list of all club members.
	 * 
	 * @return the list.
	**/
	public ArrayList<String> getAllMemberEmails() {
		ArrayList<String> list = new ArrayList<String>();
		try {
			String query = "SELECT * FROM Users JOIN Members ON IdMember = IdUser WHERE StatusCode <> ? ORDER BY Email";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, StatusCode.ELIMINATED.getValue());
						
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				list.add(rset.getString("Email"));
			}
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * Inserts a new club member in the database.
	 * 
	 * @param id the unique identifier of the user.
	 * @param fiscalCode the fiscal code of the new member.
	 * @param address the address of the new member.
	**/
	public void insertMember(final int id, final String fiscalCode, final String address) {
		try {
			String query = "INSERT INTO Members (IdMember, FiscalCode, Address) VALUES (?,?,?)";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, id);
			pstmt.setString(2, fiscalCode);
			pstmt.setString(3, address);
						
			pstmt.executeUpdate();
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Inserts a new club employee into the database.
	 * 
	 * @param id the unique identifier of the user.
	 * @param admin <code>true</code> if the employee is administrator.
	**/
	public void insertEmployee(final int id, final boolean admin) {
		try {
			String query = "INSERT INTO Employees (IdEmployee, Administrator) VALUES (?,?)";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, id);
			pstmt.setBoolean(2, admin);
			
			pstmt.executeUpdate();
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Removes an user from the database.
	 * 
	 * @param id the unique identifier of the user.
	**/
	public void removeUser(final int id) {
		try {
			String query = "UPDATE Users SET StatusCode = ? WHERE IdUser = ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, StatusCode.ELIMINATED.getValue());
			pstmt.setInt(2, id);
						
			pstmt.executeUpdate();
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Updates the information of the user.
	 * 
	 * @param id the unique identifier of the user.
	 * @param firstName the new first name of the user.
	 * @param lastName the new last name of the user.
	 * @param email the new email address of the user.
	 * @param password the new password of the user.
	**/
	public void updateUser(final int id, final String firstName, final String lastName, final String email, final String password) {
		try {		
			String query = "UPDATE Users SET FirstName = IfNull(?, FirstName), LastName = IfNull(?, LastName), Email = IfNull(?, Email), Password = ifNull (?,Password) WHERE IdUser = ?"; 			
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, email);
			pstmt.setString(4, password);			
			pstmt.setInt(5, id);
						
			pstmt.executeUpdate();
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	/**
	 * Updates the information of the member.
	 * 
	 * @param id the unique identifier of the user.
	 * @param fiscalCode the new fiscal code of the member.
	 * @param address the new address of the member.
	**/
	public void updateMember(final int id, final String fiscalCode, final String address) {		
		try {		
			String query = "UPDATE Members SET FiscalCode = IfNull(?, FiscalCode), Address = IfNull(?, Address) WHERE IdMember = ?"; 			
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setString(1, fiscalCode);
			pstmt.setString(2, address);			
			pstmt.setInt(3, id);
						
			pstmt.executeUpdate();	
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	/**
	 * Updates the information of the employee.
	 * 
	 * @param id the unique identifier of the user.
	 * @param admin <code>true</code> if the employee is administrator.
	**/
	public void updateEmployee(final int id, final boolean admin) {
		try {		
			String query = "UPDATE Employees SET Administrator = ? WHERE IdEmployee = ?"; 			
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setBoolean(1, admin);			
			pstmt.setInt(2, id);
						
			pstmt.executeUpdate();	
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
}
