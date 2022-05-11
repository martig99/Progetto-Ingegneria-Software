package main.java.it.unipr.sql;

import java.sql.*;
import main.java.it.unipr.model.*;

/**
 * The class {@code DBUtil} defines a model for connection and interactions with the SQL database.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class DBUtil {
	
	private static final String DBURL = "jdbc:mysql://localhost:3306/sailingclub?";
	private static final String ARGS = "serverTimezone=UTC";
	private static final String LOGIN = "adminclub";
	private static final String PASSWORD = "Melograno00$";
	
	private static Connection conn;
	
	/**
	 * Creates the connection to the database.
	 * 
	 * @throws SQLException if the connection fails.
	 **/
	public static void dbConnect() throws SQLException {
        try {
            conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
	
	/**
	 * Disconnects from the database.
	 * In particular closes the prepared statement, the result set and the connection.
	 * 
	 * @param rset the result set.
	 * @param pstmt the prepared statement
	 * @throws SQLException if the execution fails.
	**/
	public static void dbDisconnect(final ResultSet rset, final PreparedStatement pstmt) throws SQLException {
		if (rset != null)
			rset.close();
		
		if (pstmt != null)
			pstmt.close();
		
		if (conn != null && !conn.isClosed())
			conn.close();
	}
	
	/**
	 * Gets the object of "PreparedStatement" given a query.
	 * 
	 * @param query the query to execute.
	 * @return the reference of the <code>PreparedStatement</code> object.
	 * @throws SQLException if the execution fails.
	**/
	public static PreparedStatement prepareQuery(final String query) throws SQLException {
		dbConnect();
		
		PreparedStatement pstmt = conn.prepareStatement(query);
		return pstmt;
	}
	
	/**
	 * Sets the user object with the result set data.
	 * 
	 * @param rset the result set.
	 * @return the reference of the new user.
	 * @throws SQLException if the execution fails.
	**/
	public static User setUserFromResultSet(final ResultSet rset) throws SQLException {
		return new User(rset.getInt("Users.IdUser"), rset.getString("Users.FirstName"), rset.getString("Users.LastName"), rset.getString("Users.Email"), rset.getString("Users.Password"), StatusCode.getStatusCode(rset.getInt("Users.StatusCode")));
	}
	
	/**
	 * Sets the member object with the result set data.
	 * 
	 * @param rset the result set.
	 * @return the reference of the new member.
	 * @throws SQLException if the execution fails.
	**/
	public static Member setMemberFromResultSet(final ResultSet rset) throws SQLException {
		return new Member(rset.getInt("Users.IdUser"), rset.getString("Users.FirstName"), rset.getString("Users.LastName"), rset.getString("Users.Email"), rset.getString("Users.Password"), rset.getString("Members.FiscalCode"), rset.getString("Members.Address"), StatusCode.getStatusCode(rset.getInt("Users.StatusCode")));
	}
	
	/**
	 * Sets the employee object with the result set data.
	 * 
	 * @param rset the result set.
	 * @return the reference of the new employee.
	 * @throws SQLException if the execution fails.
	**/
	public static Employee setEmployeeFromResultSet(final ResultSet rset) throws SQLException {
		return new Employee(rset.getInt("Users.IdUser"), rset.getString("Users.FirstName"), rset.getString("Users.LastName"), rset.getString("Users.Email"), rset.getString("Users.Password"), rset.getBoolean("Employees.Administrator"), StatusCode.getStatusCode(rset.getInt("Users.StatusCode")));
	}
	
	/**
	 * Sets the boat object with the result set data.
	 * 
	 * @param rset the result set.
	 * @return the reference of the new boat.
	 * @throws SQLException if the execution fails.
	**/
	public static Boat setBoatFromResultSet(final ResultSet rset) throws SQLException {
		Member member = DBUtil.setMemberFromResultSet(rset);
		
		return new Boat(rset.getInt("Boats.IdBoat"), rset.getString("Boats.Name"), rset.getInt("Boats.Length"), member, StatusCode.getStatusCode(rset.getInt("Boats.StatusCode")));
	}
	
	/**
	 * Sets the fee object with the result set data.
	 * 
	 * @param rset the result set.
	 * @return the reference of the new fee.
	 * @throws SQLException if the execution fails.
	**/
	public static Fee setFeeFromResultSet(final ResultSet rset) throws SQLException {
		return new Fee(rset.getInt("IdFee"), FeeType.valueOf(rset.getString("Type").toUpperCase()), rset.getFloat("Amount"), rset.getInt("ValidityPeriod"), StatusCode.getStatusCode(rset.getInt("StatusCode")));
	}
	
	/**
	 * Sets the race object with the result set data.
	 * 
	 * @param rset the result set.
	 * @return the reference of the new race.
	 * @throws SQLException if the execution fails.
	**/
	public static Race setRaceFromResultSet(final ResultSet rset) throws SQLException {
		return new Race(rset.getInt("Races.IdRace"), rset.getString("Races.Name"), rset.getString("Races.Place"), rset.getDate("Races.Date"), rset.getInt("Races.BoatsNumber"), rset.getFloat("Races.RegistrationFee"), rset.getDate("Races.EndDateRegistration"), StatusCode.getStatusCode(rset.getInt("Races.StatusCode")));				
	}
	
	/**
	 * Sets the race registration object with the result set data.
	 * 
	 * @param rset the result set.
	 * @return the reference of the new race registration.
	 * @throws SQLException if the execution fails.
	**/
	public static RaceRegistration setRaceRegistrationFromResultSet(final ResultSet rset) throws SQLException {
		Boat boat = DBUtil.setBoatFromResultSet(rset);
		Race race = DBUtil.setRaceFromResultSet(rset);
		
		return new RaceRegistration(rset.getInt("RaceRegistrations.IdRegistration"), rset.getDate("RaceRegistrations.Date"), race, boat, StatusCode.getStatusCode(rset.getInt("RaceRegistrations.StatusCode")));
	}
	
	/**
	 * Sets the payment service object with the result set data.
	 * 
	 * @param rset the result set.
	 * @return the reference of the new payment service.
	 * @throws SQLException if the execution fails.
	**/
	public static PaymentService setPaymentServiceFromResultSet(final ResultSet rset) throws SQLException {
		return new PaymentService(rset.getInt("PaymentServices.IdPaymentService"), rset.getString("PaymentServices.Description"));
	}
	
	/**
	 * Sets the payment object with the result set data.
	 * 
	 * @param rset the result set.
	 * @return the reference of the payment.
	 * @throws SQLException if the execution fails.
	**/
	public static Payment setPaymentFromResultSet(final ResultSet rset) throws SQLException {
		Member member = DBUtil.setMemberFromResultSet(rset);
		Boat boat = DBUtil.setBoatFromResultSet(rset);
		RaceRegistration raceRegistration = DBUtil.setRaceRegistrationFromResultSet(rset);
		PaymentService paymentService = DBUtil.setPaymentServiceFromResultSet(rset);
		Fee fee = DBUtil.setFeeFromResultSet(rset);
		
		return new Payment(rset.getInt("Payments.IdPayment"), rset.getDate("Payments.Date"), member, boat, raceRegistration, fee, rset.getDate("Payments.ValidityStartDate"), rset.getDate("Payments.ValidityEndDate"), rset.getFloat("Payments.Total"), paymentService);
	}
	
	/**
	 * Sets the notification object with the result set data.
	 * 
	 * @param rset the result set.
	 * @return the reference of the new notification.
	 * @throws SQLException if the execution fails.
	**/ 
	public static Notification setNotificationFromResultSet(final ResultSet rset) throws SQLException {
		Member member = DBUtil.setMemberFromResultSet(rset);
		Boat boat = DBUtil.setBoatFromResultSet(rset);
		Fee fee = DBUtil.setFeeFromResultSet(rset);
		
		return new Notification(rset.getInt("Notifications.IdNotification"), member, boat, fee, StatusCode.getStatusCode(rset.getInt("Notifications.StatusCode")));
	}
}
