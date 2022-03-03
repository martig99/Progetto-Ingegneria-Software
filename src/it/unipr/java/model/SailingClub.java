package it.unipr.java.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
	 * @param type the user type to select.
	 * @return the reference of the user or <code>null</code>.
	**/
	public User login(final String email, final String password, final UserType type) {		
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			
			String query = "";
			if (type == UserType.MEMBER) {
				query = "SELECT * FROM Users JOIN Members ON IdUser = IdMember WHERE Email = ? AND Password = ? AND StatusCode <> ?";
			} else if (type == UserType.EMPLOYEE) {
				query = "SELECT * FROM Users JOIN Employees ON IdUser = IdEmployee WHERE Email = ? AND Password = ? AND StatusCode <> ?";
			}
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			pstmt.setInt(3, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				if (type == UserType.MEMBER) {
					return new Member(rset.getInt("IdUser"), rset.getString("FirstName"), rset.getString("LastName"), rset.getString("Email"), rset.getString("Password"), rset.getString("FiscalCode"), rset.getString("Address"));
				} else if (type == UserType.EMPLOYEE) {
					return new Employee(rset.getInt("IdUser"), rset.getString("FirstName"), rset.getString("LastName"), rset.getString("Email"), rset.getString("Password"), rset.getBoolean("Administrator"));
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
	 * @param type the type of user to insert.
	**/
	public void createUser(final String fiscalCode, final String firstName, final String lastName, final String email, final String password, final String address, final boolean admin, final UserType type) {	
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
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			String query = "SELECT * FROM Users WHERE Email = ? AND StatusCode <> ?";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return new User(rset.getInt("IdUser"), rset.getString("FirstName"), rset.getString("LastName"), rset.getString("Email"), rset.getString("Password"));
			}
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
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			String query = "SELECT * FROM Users JOIN Members ON IdMember = IdUser WHERE FiscalCode = ? AND StatusCode <> ?";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, fiscalCode);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return new Member(rset.getInt("IdUser"), rset.getString("FirstName"), rset.getString("LastName"), rset.getString("Email"), rset.getString("Password"), rset.getString("FiscalCode"), rset.getString("Address"));
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
	public void insertUser(final String firstName, final String lastName, final String email, final String password) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			String query = "INSERT INTO Users (FirstName, LastName, Email, Password, StatusCode) VALUES (?,?,?,?,?)";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, firstName);	
			pstmt.setString(2, lastName);
			pstmt.setString(3, email);
			pstmt.setString(4, password);
			pstmt.setInt(5, StatusCode.ACTIVE.getValue());
			
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
			
			Member member = (Member) this.getMemberById(id);
			if (member != null) {
				return member;
			}
			
			Employee employee = (Employee) this.getEmployee(id);
			if (employee != null) {
				return employee;
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
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
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			
			String query = "SELECT * FROM Users JOIN Members ON IdUser = IdMember WHERE IdMember = ? AND StatusCode <> ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return new Member(rset.getInt("IdUser"), rset.getString("FirstName"), rset.getString("LastName"), rset.getString("Email"), rset.getString("Password"), rset.getString("FiscalCode"), rset.getString("Address"));
			}
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
	public User getEmployee(final int id) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			
			String query = "SELECT * FROM Users JOIN Employees ON IdUser = IdEmployee WHERE IdEmployee = ? AND StatusCode <> ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return new Employee(rset.getInt("IdUser"), rset.getString("FirstName"), rset.getString("LastName"), rset.getString("Email"), rset.getString("Password"), rset.getBoolean("Administrator"));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Gets the list of all the users.
	 * 
	 * @param type the type of users to select.
	 * @return the list.
	**/
	public ArrayList<User> getAllUser(final UserType type) {
		ArrayList<User> list = new ArrayList<User>();
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			
			String query = "";
			if (type == UserType.MEMBER) {
				query = "SELECT * FROM Users JOIN Members ON IdMember = IdUser WHERE StatusCode <> ? ORDER BY IdUser";			
			} else if (type == UserType.EMPLOYEE) {
				query = "SELECT * FROM Users JOIN Employees ON IdEmployee = IdUser WHERE StatusCode <> ? ORDER BY IdUser";			
			}
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				if (type == UserType.MEMBER) {
					list.add(new Member(rset.getInt("IdUser"), rset.getString("FirstName"), rset.getString("LastName"), rset.getString("Email"), rset.getString("Password"), rset.getString("FiscalCode"), rset.getString("Address")));
				} else if (type == UserType.EMPLOYEE) {
					list.add(new Employee(rset.getInt("IdUser"), rset.getString("FirstName"), rset.getString("LastName"), rset.getString("Email"), rset.getString("Password"), rset.getBoolean("Administrator")));
				}
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
			
			String query = "SELECT * FROM Users JOIN Members ON IdMember = IdUser WHERE StatusCode <> ? ORDER BY Email";
			PreparedStatement pstmt = conn.prepareStatement(query);	
			pstmt.setInt(1, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
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
			String query = "SELECT * FROM Fees WHERE Type = ? AND StatusCode <> ?";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, type.toString());
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			
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
	**/
	public void insertMember(final int idUser, final String fiscalCode, final String address) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			String query = "INSERT INTO Members (IdMember, FiscalCode, Address) VALUES (?,?,?)";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, idUser);
			pstmt.setString(2, fiscalCode);
			pstmt.setString(3, address);
			
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
			String query = "INSERT INTO Employees (IdEmployee, Administrator) VALUES (?,?)";
			
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
			Fee storageFee = this.getFee(FeeType.STORAGE);
			
			PreparedStatement pstmt;
			if (owner != null) {
				String query = "SELECT * FROM Boats WHERE Owner = ? AND StatusCode <> ? ORDER BY IdBoat";
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, owner.getId());
				pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			} else {
				String query = "SELECT * FROM Boats WHERE StatusCode <> ? ORDER BY IdBoat";
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, StatusCode.ELIMINATED.getValue());
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
			Fee storageFee = this.getFee(FeeType.STORAGE);
			
			String query = "SELECT * FROM Boats WHERE Name = ? AND Owner = ? AND StatusCode <> ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setInt(2, owner.getId());
			pstmt.setInt(3, StatusCode.ELIMINATED.getValue());
			
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
	 * Gets a boat given the unique identifier.
	 * 
	 * @param name the unique identifier of the boat.
	 * @return the reference of the boat or <code>null</code>.
	**/
	public Boat getBoatById(final int id) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			Fee storageFee = this.getFee(FeeType.STORAGE);
			
			String query = "SELECT * FROM Boats WHERE IdBoat = ? AND StatusCode <> ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			
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
			String query = "INSERT INTO Boats (Name, Length, Owner, StatusCode) VALUES (?,?,?,?)";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setInt(2, length);
			pstmt.setInt(3, owner.getId());
			pstmt.setInt(4, StatusCode.ACTIVE.getValue());
			
			pstmt.executeUpdate();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Updates the boat information.
	 * If the information has value <code>null</code> are not replaced.
	 * 
	 * @param id the identification of the boat.
	 * @param name the new name of the boat.
	 * @param length the new length of the boat.
	**/
	public void updateBoat(final int id, final String name, final Integer length) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {		
			
			String query = "UPDATE Boats SET Name = IfNull(?, Name), Length = IfNull(?, Length) WHERE IdBoat = ?"; 			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			
			if (length == null) {
	            pstmt.setNull(2, Types.INTEGER);
	        } else {
	        	pstmt.setInt(2, length);
	        }
			
			pstmt.setInt(3, id);
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
			String query = "UPDATE Boats SET StatusCode = ? WHERE IdBoat = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, StatusCode.ELIMINATED.getValue());
			pstmt.setInt(2, id);
			
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Gets the list of all the payment services.
	 * 
	 * @return the list.
	**/
	public ArrayList<PaymentService> getAllPaymentServices() {
		ArrayList<PaymentService> list = new ArrayList<PaymentService>();
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			
			String query = "SELECT * FROM PaymentServices ORDER BY IdService";
			PreparedStatement pstmt = conn.prepareStatement(query);			
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				list.add(new PaymentService(rset.getInt("IdService"), rset.getString("Description")));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * Gets the last payment of the association fee made by the club member.
	 * 
	 * @param idMember the club member's unique identifier.
	 * @return the date of the last payment or <code>null</code>.
	**/
	public Date getLastPaymentMembershipFee(final int idMember) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			
			int idFee = this.getFee(FeeType.MEMBERSHIP).getId();
			
			String query = "SELECT MAX(Date) FROM Payments JOIN Members ON Member = IdMember JOIN Fees ON Fee = IdFee WHERE Member = ? AND Fee = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);			
			pstmt.setInt(1, idMember);
			pstmt.setInt(2, idFee);
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return rset.getDate(1);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Checks whether the payment of the membership fee of the club member is still valid.
	 * 
	 * @param idMember the club member's unique identifier.
	 * @return <code>true</code> if the last payment is valid.
	**/
	public boolean checkPaymentMembershipFee(final int idMember) {
		int validityPeriod = this.getFee(FeeType.MEMBERSHIP).getValidityPeriod();
		Date minDate = this.getLastPaymentMembershipFee(idMember);
		
		if (minDate != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(minDate);
			c.add(Calendar.DATE, validityPeriod);
			Date maxDate = c.getTime();
			
			Date currentDate = new Date();
			if (currentDate.after(minDate) && currentDate.before(maxDate)) {
				return true;
			} 
		}
		
		return false;
	}
}
