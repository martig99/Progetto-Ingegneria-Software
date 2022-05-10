package main.java.it.unipr.sql;

import main.java.it.unipr.model.*;

import java.sql.*;
import java.util.*;

/**
 * The class {@code NotificationDAO} defines a model for the management of the query of the entity Notifications of the database.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class NotificationDAO {
	
	/**
	 * Gets the list of all notifications of a member user.
	 * 
	 * @param user the user.
	 * @return the list.
	**/
	public ArrayList<Notification> getAllNotifications(final User user) {
		ArrayList<Notification> list = new ArrayList<Notification>();
		try {
			String query = "SELECT * FROM Notifications JOIN Members ON Members.IdMember = Notifications.Member JOIN Users ON Users.IdUser = Members.IdMember LEFT JOIN Boats ON Boats.IdBoat = Notifications.Boat JOIN Fees ON Fees.IdFee = Notifications.Fee WHERE Notifications.Member = ? AND Notifications.StatusCode = ? ORDER BY Notifications.IdNotification";

			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, user.getId());			
			pstmt.setInt(2, StatusCode.ACTIVE.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				list.add(DBUtil.setNotificationFromResultSet(rset));
			}
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * Inserts a new notification in the database.
	 * 
	 * @param user the user that is to be notified.
	 * @param boat the boat to which the storage fee refers or <code>null</code> if the fee to be notified is of membership. 
	 * @param fee the fee that the member should pay.
	**/
	public void insertNotification(final User user, final Boat boat, final Fee fee) {				
		try {			
			String query = "INSERT INTO Notifications (Member, Boat, Fee, StatusCode) VALUES (?,?,?,?)";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, user.getId());
			
			if (boat != null) {
				pstmt.setInt(2, boat.getId());
			} else {
				pstmt.setNull(2, Types.INTEGER);
			}
			
			pstmt.setInt(3, fee.getId());
			pstmt.setInt(4, StatusCode.ACTIVE.getValue());
						
			pstmt.executeUpdate();	
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Gets a notification given some values.
	 * 
	 * @param user the user who received the notification. 
	 * @param boat the boat to which the storage fee refers or <code>null</code> if the fee to be notified is of membership. 
	 * @param fee the fee that the member should pay.
	 * @param statusCode the status code of the notification.
	 * @return the reference of the notification or <code>null</code>.
	**/
	public Notification getNotification(final User user, final Boat boat, final Fee fee, final StatusCode statusCode) {
		try {			
			String query = "SELECT * FROM Notifications JOIN Members ON Members.IdMember = Notifications.Member JOIN Users ON Users.IdUser = Members.IdMember LEFT JOIN Boats ON Boats.IdBoat = Notifications.Boat JOIN Fees ON Fees.IdFee = Notifications.Fee WHERE Notifications.Member = ? AND (? IS NULL OR Notifications.Boat = ?) AND Notifications.Fee = ? AND Notifications.StatusCode = ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, user.getId());
			
			if (boat != null) {
				pstmt.setInt(2, boat.getId());
				pstmt.setInt(3, boat.getId());
			} else {
				pstmt.setNull(2, Types.INTEGER);
				pstmt.setNull(3, Types.INTEGER);
			}
			
			pstmt.setInt(4, fee.getId());
			pstmt.setInt(5, statusCode.getValue());
					
			ResultSet rset = pstmt.executeQuery();
			if (rset.next())
				return DBUtil.setNotificationFromResultSet(rset);
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Gets whether the notification of payment of the membership fee exists or not.
	 * 
	 * @param user the member who received the notification. 
	 * @return <code>true</code> in case of notice of payment of the membership fee.
	**/
	public boolean existNotificationMembershipFee(final User user) {
		try {			
			String query = "SELECT * FROM Notifications JOIN Members ON Members.IdMember = Notifications.Member JOIN Fees ON Fees.IdFee = Notifications.Fee WHERE Notifications.Member = ? AND Fees.Type = ? AND Notifications.StatusCode = ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, user.getId());
			pstmt.setString(2, FeeType.MEMBERSHIP.toString());
			pstmt.setInt(3, StatusCode.ACTIVE.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next())
				return true;
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Gets whether the notification of payment of the storage fee exists or not.
	 * 
	 * @param user the member who received the notification. 
	 * @param boat the boat to which the storage fee refers.
	 * @return <code>true</code> in case of notice of payment of the storage fee.
	**/
	public boolean existNotificationStorageFee(final User user, final Boat boat) {
		try {			
			String query = "SELECT * FROM Notifications JOIN Members ON Members.IdMember = Notifications.Member JOIN Boats ON Boats.IdBoat = Notifications.Boat JOIN Fees ON Fees.IdFee = Notifications.Fee WHERE Notifications.Member = ? AND Notifications.Boat = ? AND Fees.Type = ? AND Notifications.StatusCode = ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, user.getId());
			pstmt.setInt(2, boat.getId());
			pstmt.setString(3, FeeType.STORAGE.toString());
			pstmt.setInt(4, StatusCode.ACTIVE.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next())
				return true;
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Updates the status of a notification.
	 * 
	 * @param user the member who received the notification. 
	 * @param boat the boat to which the storage fee refers or <code>null</code> if the fee to be notified is of membership. 
	 * @param fee the fee that the member should pay.
	**/
	public void updateStatusCodeNotification(final User user, final Boat boat, final Fee fee) {		
		try {		
			String query = "UPDATE Notifications SET StatusCode = ? WHERE Member = ? AND (? IS NULL OR Boat = ?) AND Fee = ?"; 			
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, StatusCode.ELIMINATED.getValue());
			pstmt.setInt(2, user.getId());
			
			if (boat != null) {
				pstmt.setInt(3, boat.getId());
				pstmt.setInt(4, boat.getId());
			} else {
				pstmt.setNull(3, Types.INTEGER);
				pstmt.setNull(4, Types.INTEGER);
			}
			
			pstmt.setInt(5, fee.getId());
						
			pstmt.executeUpdate();	
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
}
