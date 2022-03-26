package main.java.it.unipr.sql;

import java.sql.*;
import main.java.it.unipr.model.*;

public final class DBUtil {
	
	private static final String DBURL = "jdbc:mysql://localhost:3306/sailingclub?";
	private static final String ARGS = "serverTimezone=UTC";
	private static final String LOGIN = "adminclub";
	private static final String PASSWORD = "Melograno00$";
	
	private static Connection conn;
	
	/**
	 * 
	 * @throws SQLException
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
	 * 
	 * @param rset
	 * @param pstmt
	 * @throws SQLException
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
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	**/
	public static PreparedStatement prepareQuery(final String query) throws SQLException {
		dbConnect();
		
		PreparedStatement pstmt = conn.prepareStatement(query);
		return pstmt;
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	**/
	public static User setUserFromResultSet(final ResultSet rset) throws SQLException {
		return new User(rset.getInt("IdUser"), rset.getString("FirstName"), rset.getString("LastName"), rset.getString("Email"), rset.getString("Password"));
	}
	
	/**
	 * 
	 * @param rset
	 * @return
	 * @throws SQLException
	**/
	public static Member setMemberFromResultSet(final ResultSet rset) throws SQLException {
		return new Member(rset.getInt("Users.IdUser"), rset.getString("Users.FirstName"), rset.getString("Users.LastName"), rset.getString("Users.Email"), rset.getString("Users.Password"), rset.getString("Members.FiscalCode"), rset.getString("Members.Address"));
	}
	
	/**
	 * 
	 * @param rset
	 * @return
	 * @throws SQLException
	**/
	public static Employee setEmployeeFromResultSet(final ResultSet rset) throws SQLException {
		return new Employee(rset.getInt("IdUser"), rset.getString("FirstName"), rset.getString("LastName"), rset.getString("Email"), rset.getString("Password"), rset.getBoolean("Administrator"));
	}
	
	/**
	 * 
	 * @param rset
	 * @param member
	 * @return
	 * @throws SQLException
	**/
	public static Boat setBoatFromResultSet(final ResultSet rset) throws SQLException {
		Member member = DBUtil.setMemberFromResultSet(rset);
		return new Boat(rset.getInt("Boats.IdBoat"), rset.getString("Boats.Name"), rset.getInt("Boats.Length"), member, StatusCode.getStatusCode(rset.getInt("Boats.StatusCode")));
	}
	
	/**
	 * 
	 * @param rset
	 * @return
	 * @throws SQLException
	**/
	public static Fee setFeeFromResultSet(final ResultSet rset) throws SQLException {
		return new Fee(rset.getInt("IdFee"), FeeType.valueOf(rset.getString("Type").toUpperCase()), rset.getFloat("Amount"), rset.getInt("ValidityPeriod"), StatusCode.getStatusCode(rset.getInt("StatusCode")));
	}
	
	/**
	 * 
	 * @param rset
	 * @return
	 * @throws SQLException
	**/
	public static Race setRaceFromResultSet(final ResultSet rset) throws SQLException {
		return new Race(rset.getInt("Races.IdRace"), rset.getString("Races.Name"), rset.getString("Races.Place"), rset.getDate("Races.Date"), rset.getInt("Races.BoatsNumber"), rset.getFloat("Races.RegistrationFee"), rset.getDate("Races.EndDateRegistration"), StatusCode.getStatusCode(rset.getInt("Races.StatusCode")));				
	}
	
	/**
	 * 
	 * @param rset
	 * @param race
	 * @param boat
	 * @return
	 * @throws SQLException
	**/
	public static RaceRegistration setRaceRegistrationFromResultSet(final ResultSet rset, final Race race, final Boat boat) throws SQLException {
		return new RaceRegistration(rset.getInt("RaceRegistrations.IdRegistration"), rset.getDate("RaceRegistrations.Date"), race, boat, StatusCode.getStatusCode(rset.getInt("RaceRegistrations.StatusCode")));
	}
	
	/**
	 * 
	 * @param rset
	 * @return
	 * @throws SQLException
	**/
	public static PaymentService setPaymentServiceFromResultSet(final ResultSet rset) throws SQLException {
		return new PaymentService(rset.getInt("PaymentServices.IdPaymentService"), rset.getString("PaymentServices.Description"));
	}
	
	/**
	 * 
	 * @param rset
	 * @param member
	 * @param boat
	 * @param raceRegistration
	 * @param fee
	 * @param paymentService
	 * @return
	 * @throws SQLException
	**/
	public static Payment setPaymentFromResultSet(final ResultSet rset, final Member member, final Boat boat, final RaceRegistration raceRegistration, final Fee fee, final PaymentService paymentService) throws SQLException {
		return new Payment(rset.getInt("Payments.IdPayment"), rset.getDate("Payments.Date"), member, boat, raceRegistration, fee, rset.getDate("Payments.ValidityStartDate"), rset.getDate("Payments.ValidityEndDate"), rset.getFloat("Payments.Total"), paymentService);
	}
	
	/**
	 * 
	 * @param rset
	 * @return
	 * @throws SQLException
	**/
	public static Notification setNotificationFromResultSet(final ResultSet rset) throws SQLException {
		Member member = DBUtil.setMemberFromResultSet(rset);
		Boat boat = DBUtil.setBoatFromResultSet(rset);
		Fee fee = DBUtil.setFeeFromResultSet(rset);
		return new Notification(rset.getInt("Notifications.IdNotification"), member, boat, fee, rset.getBoolean("Notifications.ReadStatus"));
	}
}
