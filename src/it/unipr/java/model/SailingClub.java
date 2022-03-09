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
			String query = "SELECT * FROM Users WHERE Email = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			
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
			String query = "SELECT * FROM Users JOIN Members ON IdMember = IdUser WHERE FiscalCode = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, fiscalCode);
			
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
				return new Fee(rset.getInt("IdFee"), FeeType.valueOf(rset.getString("Type").toUpperCase()), rset.getFloat("Amount"), rset.getInt("ValidityPeriod"), StatusCode.getStatusCode(rset.getInt("StatusCode")));
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
	 * Removes an user from the database.
	 * 
	 * @param id the unique identifier of the user.
	**/
	public void removeUser(final int id) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			String query = "UPDATE users SET StatusCode = ? WHERE IdUser = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, StatusCode.ELIMINATED.getValue());
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	**/
	public void updateUser(final int id, final String firstName, final String lastName, final String email, final String password) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {		
			
			String query = "UPDATE users SET FirstName = IfNull(?, FirstName), LastName = IfNull(?, LastName), Email = IfNull(?, Email), Password = ifNull (?,Password) WHERE IdUser = ?"; 			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, email);
			pstmt.setString(4, password);
			
			pstmt.setInt(5, id);
			pstmt.executeUpdate();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	

	/**
	 * 
	 * @param id
	 * @param fiscalCode
	 * @param address
	**/
	public void updateMember(final int id, final String fiscalCode, final String address) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {		
			
			String query = "UPDATE members SET FiscalCode = IfNull(?, FiscalCode), Address = IfNull(?, Address) WHERE IdMember = ?"; 			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, fiscalCode);
			pstmt.setString(2, address);
			
			pstmt.setInt(3, id);
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

			String query = "SELECT * FROM Boats JOIN Members ON Members.IdMember = Boats.Owner JOIN Users ON Users.IdUser = Members.IdMember WHERE (? IS NULL OR Boats.Owner = ?) AND Boats.StatusCode <> ? ORDER BY Boats.IdBoat";
			PreparedStatement pstmt = conn.prepareStatement(query);
			
			if(owner != null) {
				pstmt.setInt(1, owner.getId());
				pstmt.setInt(2, owner.getId());
			} else {
				pstmt.setNull(1, Types.INTEGER);
				pstmt.setNull(2, Types.INTEGER);
			}
			
			pstmt.setInt(3, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				list.add(new Boat(rset.getInt("Boats.IdBoat"), rset.getString("Boats.Name"), rset.getInt("Boats.Length"), (float)(rset.getInt("Boats.Length") * storageFee.getAmount()), (new Member(rset.getInt("Users.IdUser"), rset.getString("Users.FirstName"), rset.getString("Users.LastName"), rset.getString("Users.Email"), rset.getString("Users.Password"), rset.getString("Members.FiscalCode"), rset.getString("Members.Address"))), StatusCode.getStatusCode(rset.getInt("Boats.StatusCode"))));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 
	**/
	public ArrayList<String> getAllNameBoatsByOwner(final User owner) {
		ArrayList<String> list = new ArrayList<String>();
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			
			String query = "SELECT Boats.Name FROM Boats JOIN Users ON Users.IdUser = Boats.Owner WHERE Boats.Owner = ? AND Boats.StatusCode <> ? ORDER BY Boats.IdBoat";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, owner.getId());
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				list.add(rset.getString(1));
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
			
			String query = "SELECT * FROM Boats JOIN Members ON Members.IdMember = Boats.Owner JOIN Users ON Users.IdUser = Members.IdMember WHERE Boats.Name = ? AND Boats.Owner = ? AND Boats.StatusCode <> ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setInt(2, owner.getId());
			pstmt.setInt(3, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return new Boat(rset.getInt("Boats.IdBoat"), rset.getString("Boats.Name"), rset.getInt("Boats.Length"), (float)(rset.getInt("Boats.Length") * storageFee.getAmount()), (new Member(rset.getInt("Users.IdUser"), rset.getString("Users.FirstName"), rset.getString("Users.LastName"), rset.getString("Users.Email"), rset.getString("Users.Password"), rset.getString("Members.FiscalCode"), rset.getString("Members.Address"))), StatusCode.getStatusCode(rset.getInt("Boats.StatusCode")));
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
			
			String query = "SELECT * FROM Boats JOIN Members ON Members.IdMember = Boats.Owner JOIN Users ON Users.IdUser = Members.IdMember WHERE Boats.IdBoat = ? AND Boats.StatusCode <> ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return new Boat(rset.getInt("Boats.IdBoat"), rset.getString("Boats.Name"), rset.getInt("Boats.Length"), (float)(rset.getInt("Boats.Length") * storageFee.getAmount()), (new Member(rset.getInt("Users.IdUser"), rset.getString("Users.FirstName"), rset.getString("Users.LastName"), rset.getString("Users.Email"), rset.getString("Users.Password"), rset.getString("Members.FiscalCode"), rset.getString("Members.Address"))), StatusCode.getStatusCode(rset.getInt("Boats.StatusCode")));
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
	 * 
	 * @return
	**/
	public int getMaxBoatsNumber() {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			String query = "SELECT COUNT(DISTINCT Users.Email) FROM Boats JOIN Users ON Boats.Owner = Users.IdUser WHERE Boats.StatusCode <> ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return rset.getInt(1);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	 * Gets the list of all description of the payment services.
	 * 
	 * @return the list.
	**/
	public ArrayList<String> getAllPaymentServicesDescription() {
		ArrayList<String> list = new ArrayList<String>();
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			
			String query = "SELECT * FROM PaymentServices ORDER BY IdPaymentService";
			PreparedStatement pstmt = conn.prepareStatement(query);			
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				list.add(rset.getString("Description"));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * Gets the payment service given the description.
	 * 
	 * @param description the description.
	 * @return the reference of the payment service or <code>null</code>.
	**/
	public PaymentService getPaymentServiceByDescription(final String description) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			
			String query = "SELECT * FROM PaymentServices WHERE Description = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);			
			pstmt.setString(1, description);
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return new PaymentService(rset.getInt("IdPaymentService"), rset.getString("Description"));
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Gets the last payment of the fee made by the club member.
	 * 
	 * @param idMember the club member's unique identifier.
	 * @param fee
	 * @param idBoat
	 * @return the date of the last payment or <code>null</code>.
	**/
	public Date getLastPaymentFee(final User member, final Boat boat, final FeeType feeType) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			Fee fee = this.getFee(feeType);
			
			String query = "";
			int parameter = 0;
			if (feeType == FeeType.MEMBERSHIP) {
				query = "SELECT MAX(ValidityEndDate) FROM Payments JOIN Members ON Members.IdMember = Payments.Member JOIN Fees ON Payments.Fee = Fees.IdFee WHERE Payments.Member = ? AND Payments.Fee = ?";
				parameter = member.getId();
			} else if (feeType == FeeType.STORAGE && boat != null) {
				query = "SELECT MAX(ValidityEndDate) FROM Payments JOIN Boats ON Boats.IdBoat = Payments.Boat JOIN Fees ON Payments.Fee = Fees.IdFee WHERE Payments.Boat = ? AND Payments.Fee = ?";
				parameter = boat.getId();
			}
			
			PreparedStatement pstmt = conn.prepareStatement(query);			
			pstmt.setInt(1, parameter);
			pstmt.setInt(2, fee.getId());
			
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
	 * 
	 * @param member
	 * @return
	**/
	public Date getEndDate(final Date startDate, final int period) {		
		if (startDate != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(startDate);
			c.add(Calendar.DATE, period);
			return c.getTime();
		}
		
		return null;
	}
	
	/**
	 * Checks whether the payment of the fee of the club member is still valid.
	 * 
	 * @param member the club member.
	 * @param boat
	 * @param feeType
	 * @return <code>true</code> if the last payment is valid.
	**/
	public boolean checkPaymentFee(final User member, final Boat boat, final FeeType feeType) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			Fee fee = this.getFee(feeType);
			
			String query = "";
			int parameter = 0;
			if (feeType == FeeType.MEMBERSHIP) {
				query = "SELECT * FROM Payments JOIN Members ON Members.IdMember = Payments.Member WHERE Payments.Member = ? AND Payments.Fee = ? AND Payments.ValidityStartDate <= ? AND Payments.ValidityEndDate >= ?";
				parameter = member.getId();
			} else if (feeType == FeeType.STORAGE && boat != null) {
				query =  "SELECT * FROM Payments JOIN Members ON Members.IdMember = Payments.Member JOIN Boats ON Boats.IdBoat = Payments.Boat WHERE Payments.Boat = ? AND Payments.Fee = ? AND Payments.ValidityStartDate <= ? AND Payments.ValidityEndDate >= ?";
				parameter = boat.getId();
			}
			
			PreparedStatement pstmt = conn.prepareStatement(query);	
			pstmt.setInt(1, parameter);
			pstmt.setInt(2, fee.getId());
			
			Date currentDate = new Date();
			pstmt.setDate(3, new java.sql.Date(currentDate.getTime()));
			pstmt.setDate(4, new java.sql.Date(currentDate.getTime()));
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return true;
			} 
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return false;
	}

	/**
	 * Gets the list of all the payments.
	 * 
	 * @return the list.
	**/
	public ArrayList<Payment> getAllPayments( final User user, final FeeType feeType) {
		ArrayList<Payment> list = new ArrayList<Payment>();
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			Fee fee = this.getFee(feeType);
			
			String query = "";			
			if (feeType == FeeType.MEMBERSHIP) {
				query = "SELECT * FROM Payments JOIN Members ON Members.IdMember = Payments.Member JOIN Users ON Users.IdUser = Members.IdMember JOIN PaymentServices ON PaymentServices.IdPaymentService = Payments.PaymentService WHERE (? IS NULL OR Payments.Member = ?) AND Payments.Fee = ? ORDER BY Payments.IdPayment";
			} else if (feeType == FeeType.STORAGE) {
				query = "SELECT * FROM Payments JOIN Members ON Members.IdMember = Payments.Member JOIN Users ON Users.IdUser = Members.IdMember JOIN Boats ON Boats.IdBoat = Payments.Boat JOIN PaymentServices ON PaymentServices.IdPaymentService = Payments.PaymentService WHERE (? IS NULL OR Payments.Member = ?) AND Payments.Fee = ? ORDER BY Payments.IdPayment";
			} else if (feeType == FeeType.RACE_REGISTRATION) {
				query = "SELECT * FROM Payments JOIN Members ON Members.IdMember = Payments.Member JOIN Users ON Users.IdUser = Members.IdMember JOIN RaceRegistrations ON RaceRegistrations.IdRegistration = Payments.RaceRegistration JOIN Boats ON Boats.IdBoat = RaceRegistrations.Boat JOIN Races ON Races.IdRace = RaceRegistrations.Race JOIN PaymentServices ON PaymentServices.IdPaymentService = Payments.PaymentService WHERE (? IS NULL OR Payments.Member = ?) AND Payments.Fee = ? ORDER BY Payments.IdPayment";
			}
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			if(user != null) {
				pstmt.setInt(1, user.getId());
				pstmt.setInt(2, user.getId());
			} else {
				pstmt.setNull(1, Types.INTEGER);
				pstmt.setNull(2, Types.INTEGER);
			}
			
			pstmt.setInt(3, fee.getId());
			
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				Member member = new Member(rset.getInt("Users.IdUser"), rset.getString("Users.FirstName"), rset.getString("Users.LastName"), rset.getString("Users.Email"), rset.getString("Users.Password"), rset.getString("Members.FiscalCode"), rset.getString("Members.Address"));
				PaymentService paymentService = new PaymentService(rset.getInt("PaymentServices.IdPaymentService"), rset.getString("PaymentServices.Description"));
				
				Boat boat = null;
				Race race = null;
				RaceRegistration raceRegistration = null;

				if (feeType != FeeType.MEMBERSHIP) {
					boat = new Boat(rset.getInt("Boats.IdBoat"), rset.getString("Boats.Name"), rset.getInt("Boats.Length"), (float)(rset.getInt("Boats.Length") * this.getFee(FeeType.STORAGE).getAmount()), member, StatusCode.getStatusCode(rset.getInt("Boats.StatusCode")));
				} 
				
				if (feeType == FeeType.RACE_REGISTRATION) {
					race = new Race(rset.getInt("Races.IdRace"), rset.getString("Races.Name"), rset.getString("Races.Place"), rset.getDate("Races.Date"), rset.getInt("Races.BoatsNumber"), rset.getFloat("Races.RegistrationFee"), rset.getDate("Races.EndDateRegistration"), StatusCode.getStatusCode(rset.getInt("Races.StatusCode")));
					raceRegistration = new RaceRegistration(rset.getInt("RaceRegistrations.IdRegistration"), rset.getDate("RaceRegistrations.Date"), race, boat, StatusCode.getStatusCode(rset.getInt("RaceRegistrations.StatusCode")));
				}
				
				list.add(new Payment(rset.getInt("Payments.IdPayment"), rset.getDate("Payments.Date"), member, boat, raceRegistration, rset.getDate("Payments.ValidityStartDate"), rset.getDate("Payments.ValidityEndDate"), rset.getFloat("Payments.Total"), paymentService));

			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 
	 * @param member
	 * @param boat
	 * @param raceRegistration
	 * @param feeType
	 * @param paymentService
	**/
	public void payFee(final User member, final Boat boat, final RaceRegistration raceRegistration, final FeeType feeType, final PaymentService paymentService) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			Fee fee = this.getFee(feeType);

			String query = "INSERT INTO Payments (Date, Member, Boat, RaceRegistration, Fee, ValidityStartDate, ValidityEndDate, Total, PaymentService) VALUES (?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			
			java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
			pstmt.setDate(1, sqlDate);
			pstmt.setInt(2, member.getId());
			
			if (boat != null) {
				pstmt.setInt(3, boat.getId());
			} else {
				pstmt.setNull(3, Types.INTEGER);
			}
			
			if (raceRegistration != null) {
				pstmt.setInt(4, raceRegistration.getId());
			} else {
				pstmt.setNull(4, Types.INTEGER);
			}
			
			pstmt.setInt(5, fee.getId());
			
			Date validityStartDate = new Date();
			if (feeType == FeeType.MEMBERSHIP || feeType == FeeType.STORAGE) {
				Date dateLastPayment = this.getLastPaymentFee(member, boat, feeType);
				if (dateLastPayment != null) {
					if (dateLastPayment.after(validityStartDate)) {
						validityStartDate = this.getEndDate(dateLastPayment, 1);
						sqlDate = new java.sql.Date(validityStartDate.getTime());	
					}
				}
				
				pstmt.setDate(6, sqlDate);
			
				Date validityEndDate = this.getEndDate(validityStartDate, fee.getValidityPeriod());
				pstmt.setDate(7, new java.sql.Date(validityEndDate.getTime()));
			} else if (feeType == FeeType.RACE_REGISTRATION) {
				pstmt.setDate(6, new java.sql.Date(validityStartDate.getTime()));
				pstmt.setDate(7, new java.sql.Date(validityStartDate.getTime()));
				
				pstmt.setDouble(8, raceRegistration.getRace().getRegistrationFee());
			}
			
			if (feeType == FeeType.MEMBERSHIP) {
				pstmt.setDouble(8, fee.getAmount());
			} else if (feeType == FeeType.STORAGE) {
				pstmt.setDouble(8, (float)(boat.getLength() * fee.getAmount()));
			}
			
			pstmt.setInt(9, paymentService.getId());
			
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Gets the list of all the races.
	 * 
	 * @return the list.
	**/
	public ArrayList<Race> getAllRaces() {
		ArrayList<Race> list = new ArrayList<Race>();
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			
			PreparedStatement pstmt;
			String query = "SELECT * FROM Races WHERE StatusCode <> ? ORDER BY IdRace";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				list.add(new Race(rset.getInt("IdRace"), rset.getString("Name"), rset.getString("Place"), rset.getDate("Date"), rset.getInt("BoatsNumber"), rset.getFloat("RegistrationFee"), rset.getDate("EndDateRegistration"), StatusCode.getStatusCode(rset.getInt("StatusCode"))));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * Gets a race of a club member given the date.
	 * 
	 * @param date the date of the race.
	 * @return the reference of the race or <code>null</code>.
	**/
	public Race getRaceByDate(final Date date) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			
			String query = "SELECT * FROM Races WHERE Date = ? AND StatusCode <> ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setDate(1, new java.sql.Date(date.getTime()));
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return new Race(rset.getInt("IdRace"), rset.getString("Name"), rset.getString("Place"), rset.getDate("Date"), rset.getInt("BoatsNumber"), rset.getFloat("RegistrationFee"), rset.getDate("EndDateRegistration"), StatusCode.getStatusCode(rset.getInt("StatusCode")));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Inserts a boat in the database.
	 * 
	 * @param name the name of the race.
	 * @param place the place of the race.
	 * @param dateRace the date of the race.
	 * @param boatsNumber the number of the participating boats.
	 * @param registrationFee the registration fee of the race.
	**/
	public void insertRace(final String name, final String place, final Date dateRace, final int boatsNumber, final float registrationFee) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {			
			String query = "INSERT INTO Races (Name, Place, DateRace, BoatsNumber, RegistrationFee, StatusCode) VALUES (?,?,?,?,?,?)";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, place);
			pstmt.setObject(3, dateRace);
			pstmt.setInt(4, boatsNumber);
			pstmt.setFloat(5, registrationFee);
			pstmt.setInt(6, StatusCode.ACTIVE.getValue());
			
			pstmt.executeUpdate();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Updates the race information.
	 * If the information has value <code>null</code> are not replaced.
	 * 
	 * @param name the name of the race.
	 * @param place the place of the race.
	 * @param dateRace the date of the race.
	 * @param boatsNumber the number of the participating boats.
	 * @param registrationFee the registration fee of the race.
	**/
	public void updateRace(final int id, final String name, final String place, final Date dateRace, final Integer boatsNumber, final Float registrationFee) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {		
			
			String query = "UPDATE Races SET Name = IfNull(?, Name), Place = IfNull(?, Place), DateRace = IfNull(?, DateRace), BoatsNumber = IfNull(?, BoatsNumber), RegistrationFee = IfNull(?, RegistrationFee) WHERE IdRace = ?"; 			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, place);
			
			if (dateRace == null) {
	            pstmt.setNull(3, Types.DATE);
	        } else {
	        	pstmt.setObject(3, dateRace);
	        }
			

			if (boatsNumber == null) {
	            pstmt.setNull(4, Types.INTEGER);
	        } else {
	        	pstmt.setInt(4, boatsNumber);
	        }
						

			if (registrationFee == null) {
	            pstmt.setNull(5, Types.FLOAT);
	        } else {
	        	pstmt.setFloat(5, registrationFee);
	        }
			
			
			pstmt.setInt(6, id);
			pstmt.executeUpdate();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Removes a race from the database.
	 * 
	 * @param id the unique identifier of the race.
	**/
	public void removeRace(final int id) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			String query = "UPDATE Races SET StatusCode = ? WHERE IdRace = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, StatusCode.ELIMINATED.getValue());
			pstmt.setInt(2, id);
			
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Gets the race given the unique identifier.
	 * 
	 * @param id the unique identifier.
	 * @return the reference of the race or <code>null</code>.
	**/
	public Race getRaceById(final int id) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			String query = "SELECT * FROM Races WHERE IdRace = ? AND StatusCode <> ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return new Race(rset.getInt("IdRace"), rset.getString("Name"), rset.getString("Place"), rset.getDate("Date"), rset.getInt("BoatsNumber"), rset.getFloat("RegistrationFee"), rset.getDate("EndDateRegistration"), StatusCode.getStatusCode(rset.getInt("StatusCode")));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Inserts a boat in the database.
	 * 
	 * @param name the name of the race.
	 * @param place the place of the race.
	 * @param dateRace the date of the race.
	 * @param boatsNumber the number of the participating boats.
	 * @param registrationFee the registration fee of the race.
	**/
	public void insertRace(final String name, final String place, final Date dateRace, final int boatsNumber, final float registrationFee, final Date endDateRegistration) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {			
			String query = "INSERT INTO Races (Name, Place, Date, BoatsNumber, RegistrationFee, EndDateRegistration, StatusCode) VALUES (?,?,?,?,?,?,?)";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, place);
			pstmt.setDate(3, new java.sql.Date(dateRace.getTime()));
			pstmt.setInt(4, boatsNumber);
			pstmt.setFloat(5, registrationFee);
			pstmt.setDate(6, new java.sql.Date(endDateRegistration.getTime()));
			pstmt.setInt(7, StatusCode.ACTIVE.getValue());
			
			pstmt.executeUpdate();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Updates the race information.
	 * If the information has value <code>null</code> are not replaced.
	 * 
	 * @param name the name of the race.
	 * @param place the place of the race.
	 * @param dateRace the date of the race.
	 * @param boatsNumber the number of the participating boats.
	 * @param registrationFee the registration fee of the race.
	**/
	public void updateRace(final int id, final String name, final String place, final Date dateRace, final Integer boatsNumber, final Float registrationFee, final Date endDateRegistration) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {		
			
			String query = "UPDATE Races SET Name = IfNull(?, Name), Place = IfNull(?, Place), Date = IfNull(?, Date), BoatsNumber = IfNull(?, BoatsNumber), RegistrationFee = IfNull(?, RegistrationFee), EndDateRegistration = IfNull(?, EndDateRegistration) WHERE IdRace = ?"; 			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, place);
			
			if (dateRace == null) {
	            pstmt.setNull(3, Types.DATE);
	        } else {
	        	pstmt.setDate(3, new java.sql.Date(dateRace.getTime()));
	        }
			
			if (boatsNumber == null) {
	            pstmt.setNull(4, Types.INTEGER);
	        } else {
	        	pstmt.setInt(4, boatsNumber);
	        }
						
			if (registrationFee == null) {
	            pstmt.setNull(5, Types.FLOAT);
	        } else {
	        	pstmt.setFloat(5, registrationFee);
	        }
			
			if (endDateRegistration == null) {
				pstmt.setNull(6, Types.DATE);
			} else {
				pstmt.setDate(6, new java.sql.Date(endDateRegistration.getTime()));
			}
			
			pstmt.setInt(7, id);
			pstmt.executeUpdate();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public int getNumberParticipantsInRace(final Race race) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			String query = "SELECT COUNT(*) FROM RaceRegistrations WHERE Race = ? AND StatusCode <> ?";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, race.getId());
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return rset.getInt(1);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	 * 
	 * @param member
	 * @return
	**/
	public Boat getBoatInRaceByMember(final User member, final Race race) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			Fee storageFee = this.getFee(FeeType.STORAGE);
			
			String query = "SELECT * FROM RaceRegistrations JOIN Boats ON Boats.IdBoat = RaceRegistrations.Boat JOIN Members ON Members.IdMember = Boats.Owner JOIN Users ON Users.IdUser = Members.IdMember WHERE RaceRegistrations.Race = ? AND Boats.Owner = ? AND RaceRegistrations.StatusCode <> ?";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, race.getId());
			pstmt.setInt(2, member.getId());
			pstmt.setInt(3, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return new Boat(rset.getInt("Boats.IdBoat"), rset.getString("Boats.Name"), rset.getInt("Boats.Length"), (float)(rset.getInt("Boats.Length") * storageFee.getAmount()), (new Member(rset.getInt("Users.IdUser"), rset.getString("Users.FirstName"), rset.getString("Users.LastName"), rset.getString("Users.Email"), rset.getString("Users.Password"), rset.getString("Members.FiscalCode"), rset.getString("Members.Address"))), StatusCode.getStatusCode(rset.getInt("Boats.StatusCode")));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Gets the race registration given the race and the boat registered.
	 * 
	 * @param race the race.
	 * @param boat the boat.
	 * @return the reference of the race registration or <code>null</code>.
	**/
	public RaceRegistration getRaceRegistration(final Race race, final Boat boat) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			String query = "SELECT * FROM RaceRegistrations WHERE Race = ? AND Boat = ? AND StatusCode <> ?";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, race.getId());
			pstmt.setInt(2, boat.getId());
			pstmt.setInt(3, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return new RaceRegistration(rset.getInt("IdRegistration"), rset.getDate("Date"), race, boat, StatusCode.getStatusCode(rset.getInt("StatusCode")));
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	**/
	public RaceRegistration getRaceRegistrationById(final int id) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			Fee storageFee = this.getFee(FeeType.STORAGE);
			
			String query = "SELECT * FROM RaceRegistrations JOIN Races ON Races.IdRace = RaceRegistrations.Race JOIN Boats ON Boats.IdBoat = RaceRegistrations.Boat JOIN Members ON Members.IdMember = Boats.Owner JOIN Users ON Users.IdUser = Members.IdMember WHERE RaceRegistrations.IdRegistration = ? AND RaceRegistrations.StatusCode <> ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				Race race = new Race(rset.getInt("Races.IdRace"), rset.getString("Races.Name"), rset.getString("Races.Place"), rset.getDate("Races.Date"), rset.getInt("Races.BoatsNumber"), rset.getFloat("Races.RegistrationFee"), rset.getDate("Races.EndDateRegistration"), StatusCode.getStatusCode(rset.getInt("Races.StatusCode")));
				Boat boat = new Boat(rset.getInt("Boats.IdBoat"), rset.getString("Boats.Name"), rset.getInt("Boats.Length"), (float)(rset.getInt("Boats.Length") * storageFee.getAmount()), (new Member(rset.getInt("Users.IdUser"), rset.getString("Users.FirstName"), rset.getString("Users.LastName"), rset.getString("Users.Email"), rset.getString("Users.Password"), rset.getString("Members.FiscalCode"), rset.getString("Members.Address"))), StatusCode.getStatusCode(rset.getInt("Boats.StatusCode")));
				return new RaceRegistration(rset.getInt("RaceRegistrations.IdRegistration"), rset.getDate("RaceRegistrations.Date"), race, boat, StatusCode.getStatusCode(rset.getInt("RaceRegistrations.StatusCode")));
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Registers a boat at a race. It inserts a new record in the race registration table.
	 * Then it calls the method to pay for registration to the race.
	 * 
	 * @param race the race.
	 * @param member the club member who pays for registration.
	 * @param boat the boat registered to the race.
	 * @param paymentService the payment service used to pay the registration fee for the race.
	**/
	public void registerBoatAtRace(final Race race, final User member, final Boat boat, final PaymentService paymentService) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			String query = "INSERT INTO RaceRegistrations (Date, Race, Boat, StatusCode) VALUES (?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			
			java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
			pstmt.setDate(1, sqlDate);
			pstmt.setInt(2, race.getId());
			pstmt.setInt(3, boat.getId());
			pstmt.setInt(4, StatusCode.ACTIVE.getValue());
			
			pstmt.executeUpdate();
			
			RaceRegistration registration = this.getRaceRegistration(race, boat);
			if (registration != null) {
				this.payFee(member, null, registration, FeeType.RACE_REGISTRATION, paymentService);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public ArrayList<RaceRegistration> getAllRegistrationsByRace(final Race race) {
		ArrayList<RaceRegistration> list = new ArrayList<RaceRegistration>();
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			Fee storageFee = this.getFee(FeeType.STORAGE);
			
			String query = "SELECT * FROM RaceRegistrations JOIN Boats ON Boats.IdBoat = RaceRegistrations.Boat JOIN Members ON Members.IdMember = Boats.Owner JOIN Users ON Users.IdUser = Members.IdMember WHERE RaceRegistrations.Race = ? AND RaceRegistrations.StatusCode <> ?";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, race.getId());
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				Boat boat = new Boat(rset.getInt("Boats.IdBoat"), rset.getString("Boats.Name"), rset.getInt("Boats.Length"), (float)(rset.getInt("Boats.Length") * storageFee.getAmount()), (new Member(rset.getInt("Users.IdUser"), rset.getString("Users.FirstName"), rset.getString("Users.LastName"), rset.getString("Users.Email"), rset.getString("Users.Password"), rset.getString("Members.FiscalCode"), rset.getString("Members.Address"))), StatusCode.getStatusCode(rset.getInt("Boats.StatusCode")));
				list.add(new RaceRegistration(rset.getInt("RaceRegistrations.IdRegistration"), rset.getDate("RaceRegistrations.Date"), race, boat, StatusCode.getStatusCode(rset.getInt("RaceRegistrations.StatusCode"))));
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * Removes a registration from the database.
	 * 
	 * @param id the unique identifier of the registration.
	**/
	public void removeRegistration(final int id) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			String query = "UPDATE RaceRegistrations SET StatusCode = ? WHERE IdRegistration = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, StatusCode.ELIMINATED.getValue());
			pstmt.setInt(2, id);
			
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * 
	**/
	public void updateBoatRegistration(final int id, final Boat boat) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			String query = "UPDATE RaceRegistrations SET Boat = ? WHERE IdRegistration = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boat.getId());
			pstmt.setInt(2, id);
			
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
}
