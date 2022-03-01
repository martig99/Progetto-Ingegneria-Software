package it.unipr.java.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * The class {@code SailingClub} describes a sailing club and its properties.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class SailingClub {
	
	private static final String DBURL = "jdbc:mysql://localhost:3306/sailingclub?";
	private static final String ARGS = "serverTimezone=UTC";
	private static final String LOGIN = "adminclub";
	private static final String PASSWORD = "Melograno00$";
	
	/**
	 * Class constructor.
	**/
	public SailingClub() {}
	
	/**
	 * Performs user's login.
	 * 
	 * @param email the login email address.
	 * @param password the login password.
	 * @param role the user's role.
	 * @return the reference of the user or <code>null</code>.
	**/
	public User login(final String email, final String password, final UserRole role) {		
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			
			String query = "";
			if (role == UserRole.MEMBER) {
				query = "SELECT * FROM Users JOIN Members ON IdUser = IdMember JOIN Fees ON MembershipFee = IdFee WHERE Email = ? AND Password = ?";
			} else if (role == UserRole.EMPLOYEE) {
				query = "SELECT * FROM Users JOIN Employees ON IdUser = IdEmployee WHERE Email = ? AND Password = ?";
			}
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				if (role == UserRole.MEMBER) {
					return new Member(rset.getInt("IdUser"), rset.getString("FiscalCode"), rset.getString("FirstName"), rset.getString("LastName"), rset.getString("Email"), rset.getString("Password"), rset.getFloat("Amount"));
				} else if (role == UserRole.EMPLOYEE) {
					return new Employee(rset.getInt("IdUser"), rset.getString("FiscalCode"), rset.getString("FirstName"), rset.getString("LastName"), rset.getString("Email"), rset.getString("Password"), rset.getBoolean("Administrator"));
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Creates a new user in the application.
	 * 
	 * @param fiscalCode the fiscal code of the new user.
	 * @param firstName the first name of the new user.
	 * @param lastName the last name of the new user.
	 * @param email the email address of the new user.
	 * @param password the password of the new user.
	 * @param role the user's role.
	**/
	public void createUser(final String fiscalCode, final String firstName, final String lastName, final String email, final String password, final boolean admin, final UserRole role) {	
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			
			Fee membershipFee = getFee(FeeType.MEMBERSHIP);
			insertUser(fiscalCode, firstName, lastName, email, password);
			User newUser = getUser(fiscalCode, email);
			
			if (role.equals(UserRole.MEMBER)) {
				insertMember(newUser.getId(), membershipFee.getId());
			} else {
				insertEmployee(newUser.getId(), admin);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Gets the user in the database given the fiscal code or email.
	 * 
	 * @param fiscalCode the fiscal code.
	 * @param email the email address.
	 * @return the reference of the user or <code>null</code>.
	**/
	public User getUser(final String fiscalCode, final String email) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			String query = "SELECT * FROM Users WHERE FiscalCode = ? OR Email = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, fiscalCode);
			pstmt.setString(2, email);
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return new User(rset.getInt("IdUser"), rset.getString("FiscalCode"), rset.getString("FirstName"), rset.getString("LastName"), rset.getString("Email"), rset.getString("Password"));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Inserts a new user in the database.
	 * 
	 * @param fiscalCode the fiscal code of the new user.
	 * @param firstName the first name of the new user.
	 * @param lastName the last name of the new user.
	 * @param email the email address of the new user.
	 * @param password the password of the new user.
	**/
	public void insertUser(final String fiscalCode, final String firstName, final String lastName, final String email, final String password) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			String query = "INSERT INTO Users (FiscalCode, FirstName, LastName, Email, Password) VALUES (?,?,?,?,?)";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, fiscalCode);
			pstmt.setString(2, firstName);	
			pstmt.setString(3, lastName);
			pstmt.setString(4, email);
			pstmt.setString(5, password);
			
			pstmt.executeUpdate();
			
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
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			String query = "SELECT * FROM Users WHERE IdUser = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return new User(rset.getInt("IdUser"), rset.getString("FiscalCode"), rset.getString("FirstName"), rset.getString("LastName"), rset.getString("Email"), rset.getString("Password"));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Gets the list of all the users.
	 * 
	 * @return the list.
	**/
	public ArrayList<User> getAllUsers() {
		ArrayList<User> list = new ArrayList<User>();
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			
			String query = "SELECT * FROM Users ORDER BY IdUser";			
			PreparedStatement pstmt = conn.prepareStatement(query);	
			
			ResultSet rset = pstmt.executeQuery(query);
			while (rset.next()) {
				list.add(new User(rset.getInt("IdUser"), rset.getString("FiscalCode"), rset.getString("FirstName"), rset.getString("LastName"), rset.getString("Email"), rset.getString("Password")));
			}
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
	public ArrayList<String> getAllMembersEmail() {
		ArrayList<String> list = new ArrayList<String>();
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			
			String query = "SELECT Email FROM Users JOIN Members ON IdMember = IdUser";			
			PreparedStatement pstmt = conn.prepareStatement(query);	
			
			ResultSet rset = pstmt.executeQuery(query);
			while (rset.next()) {
				list.add(rset.getString("Email"));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * Gets the fee identification code given the fee type. 
	 * 
	 * @param type the fee type.
	 * @return the reference of the fee or <code>null</code>.
	**/
	public Fee getFee(FeeType type) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			String query = "SELECT * FROM Fees WHERE Type = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, type.toString());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return new Fee(rset.getInt("IdFee"), FeeType.valueOf(rset.getString("Type").toUpperCase()), rset.getFloat("Amount"), rset.getInt("ValidityPeriod"));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Inserts a new club member into the database.
	 * 
	 * @param idUser the user's id.
	 * @param idFee the fee identification code. 
	**/
	public void insertMember(final int idUser, final int idFee) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			String query = "INSERT INTO Members (IdMember, MembershipFee) VALUES (?, ?)";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, idUser);
			pstmt.setInt(2, idFee);
			
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Inserts a new club employee into the database.
	 * 
	 * @param idUser the user's id.
	 * @param admin {@code true} if the employee is administrator.
	**/
	public void insertEmployee(final int idUser, final boolean admin) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			String query = "INSERT INTO Employees (IdEmployee, Administrator) VALUES (?, ?)";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, idUser);
			pstmt.setBoolean(2, admin);
			
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Gets the list of all the boats.
	 * If the member passed as parameter is different from <code>null</code> returns the list of the boats owned by the user.
	 * 
	 * @param owner the owner of the boats. 
	 * @return the list.
	**/
	public ArrayList<Boat> getAllBoats(final Member owner) {
		ArrayList<Boat> list = new ArrayList<Boat>();
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			Fee storageFee = getFee(FeeType.STORAGE);
			
			PreparedStatement pstmt;
			if (owner != null) {
				String query = "SELECT * FROM Boats WHERE Owner = ? ORDER BY IdBoat";
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, owner.getId());
			} else {
				String query = "SELECT * FROM Boats ORDER BY IdBoat";
				pstmt = conn.prepareStatement(query);
			}
			
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				list.add(new Boat(rset.getInt("IdBoat"), rset.getString("Name"), rset.getInt("Length"), (rset.getInt("Length") * storageFee.getAmount()), rset.getInt("Owner")));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * Gets a boat of a club member given the name of the boat.
	 * 
	 * @param name the name of the boat.
	 * @param owner the owner of the boat.
	 * @return the reference of the boat or <code>null</code>.
	**/
	public Boat getBoatByName(final String name, final User owner) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			Fee storageFee = getFee(FeeType.STORAGE);
			
			String query = "SELECT * FROM Boats WHERE Name = ? AND Owner = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setInt(2, owner.getId());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return new Boat(rset.getInt("IdBoat"), rset.getString("Name"), rset.getInt("Length"), (rset.getInt("Length") * storageFee.getAmount()), rset.getInt("Owner"));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Inserts a boat in the database.
	 * 
	 * @param name the name of the boat.
	 * @param length the length of the boat.
	 * @param owner the owner of the boat.
	**/
	public void insertBoat(final String name, final int length, final User owner) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			Fee storageFee = getFee(FeeType.STORAGE);
			
			String query = "INSERT INTO Boats (Name, Length, StorageFee, Owner) VALUES (?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setInt(2, length);
			pstmt.setInt(3, storageFee.getId());
			pstmt.setInt(4, owner.getId());
			
			pstmt.executeUpdate();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Removes a boat from the database.
	 * 
	 * @param id the unique identifier of the boat.
	**/
	public void removeBoat(final int id) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			
			String query = "DELETE FROM Boats WHERE IdBoat = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
}
