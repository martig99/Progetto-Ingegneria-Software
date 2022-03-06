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

			String query = "SELECT * FROM Boats boats JOIN Members members ON members.IdMember = boats.Owner JOIN Users users ON users.IdUser = members.IdMember WHERE (? IS NULL OR boats.Owner = ?) AND boats.StatusCode <> ? ORDER BY IdBoat";
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
				list.add(new Boat(rset.getInt("IdBoat"), rset.getString("Name"), rset.getInt("Length"), (float)(rset.getInt("Length") * storageFee.getAmount()), (new Member(rset.getInt("IdUser"), rset.getString("FirstName"), rset.getString("LastName"), rset.getString("Email"), rset.getString("Password"), rset.getString("FiscalCode"), rset.getString("Address")))));
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
			
			String query = "SELECT boats.Name FROM Boats boats JOIN Users users ON users.IdUser = boats.Owner WHERE boats.Owner = ? AND boats.StatusCode <> ? ORDER BY IdBoat";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, owner.getId());
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				list.add(rset.getString("Name"));
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
			
			String query = "SELECT * FROM Boats boats JOIN Members members ON members.IdMember = boats.Owner JOIN Users users ON users.IdUser = members.IdMember WHERE boats.Name = ? AND boats.Owner = ? AND boats.StatusCode <> ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setInt(2, owner.getId());
			pstmt.setInt(3, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return new Boat(rset.getInt("IdBoat"), rset.getString("Name"), rset.getInt("Length"), (float)(rset.getInt("Length") * storageFee.getAmount()), (new Member(rset.getInt("IdUser"), rset.getString("FirstName"), rset.getString("LastName"), rset.getString("Email"), rset.getString("Password"), rset.getString("FiscalCode"), rset.getString("Address"))));
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
			
			String query = "SELECT * FROM Boats boats JOIN Members members ON members.IdMember = boats.Owner JOIN Users users ON users.IdUser = members.IdMember WHERE boats.IdBoat = ? AND boats.StatusCode <> ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return new Boat(rset.getInt("IdBoat"), rset.getString("Name"), rset.getInt("Length"), (float)(rset.getInt("Length") * storageFee.getAmount()), (new Member(rset.getInt("IdUser"), rset.getString("FirstName"), rset.getString("LastName"), rset.getString("Email"), rset.getString("Password"), rset.getString("FiscalCode"), rset.getString("Address"))));
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
				query = "SELECT MAX(ValidityEndDate) FROM Payments payments JOIN Members members ON members.IdMember = payments.Member JOIN Fees fees ON payments.Fee = fees.IdFee WHERE payments.Member = ? AND payments.Fee = ?";
				parameter = member.getId();
			} else if (feeType == FeeType.STORAGE && boat != null) {
				query = "SELECT MAX(ValidityEndDate) FROM Payments payments JOIN Boats boats ON boats.IdBoat = payments.Boat JOIN Fees fees ON payments.Fee = fees.IdFee WHERE payments.Boat = ? AND payments.Fee = ?";
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
				query = "SELECT * FROM Payments payments JOIN Members members ON members.IdMember = payments.Member WHERE payments.Member = ? AND payments.Fee = ? AND payments.ValidityStartDate <= ? AND payments.ValidityEndDate >= ?";
				parameter = member.getId();
			} else if (feeType == FeeType.STORAGE && boat != null) {
				query =  "SELECT * FROM Payments payments JOIN Members members ON members.IdMember = payments.Member JOIN Boats boats ON boats.IdBoat = payments.Boat WHERE payments.Boat = ? AND payments.Fee = ? AND payments.ValidityStartDate <= ? AND payments.ValidityEndDate >= ?";
				parameter = boat.getId();
			}
			
			PreparedStatement pstmt = conn.prepareStatement(query);	
			pstmt.setInt(1, parameter);
			pstmt.setInt(2, fee.getId());
			
			Date currentDate = new Date();
			pstmt.setObject(3, currentDate);
			pstmt.setObject(4, currentDate);
			
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
				query = "SELECT * FROM Payments payments JOIN Members members ON members.IdMember = payments.Member JOIN Users users ON users.IdUser = members.IdMember JOIN PaymentServices paymentServices ON paymentServices.IdPaymentService = payments.PaymentService WHERE (? IS NULL OR payments.Member = ?) AND payments.Fee = ? ORDER BY payments.IdPayment";
			} else if (feeType == FeeType.STORAGE) {
				query = "SELECT * FROM Payments payments JOIN Members members ON members.IdMember = payments.Member JOIN Users users ON users.IdUser = members.IdMember JOIN Boats boats ON boats.IdBoat = payments.Boat JOIN PaymentServices paymentServices ON paymentServices.IdPaymentService = payments.PaymentService WHERE (? IS NULL OR payments.Member = ?) AND payments.Fee = ? ORDER BY payments.IdPayment";
			} else if (feeType == FeeType.RACE_REGISTRATION) {
				
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
				Member member = new Member(rset.getInt("IdUser"), rset.getString("FirstName"), rset.getString("LastName"), rset.getString("Email"), rset.getString("Password"), rset.getString("FiscalCode"), rset.getString("Address"));
				PaymentService paymentService = new PaymentService(rset.getInt("IdPaymentService"), rset.getString("Description"));
				
				if (feeType == FeeType.MEMBERSHIP) {
					list.add(new Payment(rset.getInt("IdPayment"), rset.getDate("Date"), member, null, rset.getDate("ValidityStartDate"), rset.getDate("ValidityEndDate"), rset.getFloat("Total"), paymentService));
				} else if (feeType == FeeType.STORAGE) {
					Boat boat = new Boat(rset.getInt("IdBoat"), rset.getString("Name"), rset.getInt("Length"), (float)(rset.getInt("Length") * fee.getAmount()), member);
					list.add(new Payment(rset.getInt("IdPayment"), rset.getDate("Date"), member, boat, rset.getDate("ValidityStartDate"), rset.getDate("ValidityEndDate"), rset.getFloat("Total"), paymentService));
				} else if (feeType == FeeType.RACE_REGISTRATION) {
					
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 
	 * @param user
	 * @param boat
	 * @param feeType
	 * @param paymentService
	**/
	public void payFee(final User member, final Boat boat, final FeeType feeType, final PaymentService paymentService) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			Fee fee = this.getFee(feeType);

			String query = "INSERT INTO Payments (Date, Member, Boat, RaceRegistration, Fee, ValidityStartDate, ValidityEndDate, Total, PaymentService) VALUES (?,?,?,?,?,?,?,?,?)";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setObject(1, new Date());
			pstmt.setInt(2, member.getId());
			
			if (boat != null) {
				pstmt.setInt(3, boat.getId());
			} else {
				pstmt.setNull(3, Types.INTEGER);
			}
			
			pstmt.setNull(4, Types.INTEGER); //RACE REGISTRATION
			pstmt.setInt(5, fee.getId());
			
			Date newDate = new Date();
			
			if (feeType == FeeType.MEMBERSHIP || feeType == FeeType.STORAGE) {
				Date dateLastPayment = this.getLastPaymentFee(member, boat, feeType);
				if (dateLastPayment != null) {
					if (dateLastPayment.after(newDate)) {
						newDate = this.getEndDate(dateLastPayment, 1);	
					}
				}
				
				pstmt.setObject(6, newDate); //CONTROLLARE DATA
			
				Date validityEndDate = this.getEndDate(newDate, fee.getValidityPeriod());
				pstmt.setObject(7, validityEndDate);
			} else if (feeType == FeeType.RACE_REGISTRATION) {
				pstmt.setObject(6, new Date());
				pstmt.setObject(7, new Date());
				
				//MANCA TOTALE FEE RACE REGISTRATION
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
				list.add(new Race(rset.getInt("IdRace"), rset.getString("Name"), rset.getString("Place"), rset.getDate("DateRace"), rset.getInt("BoatsNumber"), rset.getFloat("RegistrationFee")));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * Gets a race of a club member given the data.
	 * 
	 * @param dataRace the data of the race.
	 * @return the reference of the race or <code>null</code>.
	**/
	public Race getRaceByDate(final Date dataRace) {
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()) {
			
			String query = "SELECT * FROM Races WHERE DateRace = ? AND StatusCode <> ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setObject(1,dataRace);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return new Race(rset.getInt("IdRace"), rset.getString("Name"), rset.getString("Place"), rset.getDate("DaraRace"), rset.getInt("BoatsNumber"), rset.getFloat("RegistrationFee"));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
}
