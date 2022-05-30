package main.java.it.unipr.sql;

import main.java.it.unipr.model.*;

import java.sql.*;
import java.util.*;

/**
 * The class {@code NotificationDAO} defines a model for the management of the query of the entity Notifications of the database.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
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
	 * @param idUser the unique code of the user that is to be notified.
	 * @param idBoat the unique code of the boat to which the storage fee refers or <code>null</code> if the fee to be notified is of membership. 
	 * @param idFee the unique code of the fee that the member should pay.
	**/
	public void insertNotification(final int idUser, final Integer idBoat, final int idFee) {				
		try {			
			String query = "INSERT INTO Notifications (Member, Boat, Fee, StatusCode) VALUES (?,?,?,?)";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, idUser);
			
			if (idBoat != null) {
				pstmt.setInt(2, idBoat);
			} else {
				pstmt.setNull(2, Types.INTEGER);
			}
			
			pstmt.setInt(3, idFee);
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
		Notification notification = null;
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
				notification = DBUtil.setNotificationFromResultSet(rset);
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return notification;
	}
	
	/**
	 * Gets whether the notification of payment of the membership fee exists or not.
	 * 
	 * @param user the member who received the notification. 
	 * @return <code>true</code> in case of notice of payment of the membership fee.
	**/
	public boolean existNotificationMembershipFee(final User user) {
		boolean result = false;
		try {			
			String query = "SELECT * FROM Notifications JOIN Members ON Members.IdMember = Notifications.Member JOIN Fees ON Fees.IdFee = Notifications.Fee WHERE Notifications.Member = ? AND Fees.Type = ? AND Notifications.StatusCode = ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, user.getId());
			pstmt.setString(2, FeeType.MEMBERSHIP.toString());
			pstmt.setInt(3, StatusCode.ACTIVE.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next())
				result = true;
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Gets whether the notification of payment of the storage fee exists or not.
	 * 
	 * @param user the member who received the notification. 
	 * @param boat the boat to which the storage fee refers.
	 * @return <code>true</code> in case of notice of payment of the storage fee.
	**/
	public boolean existNotificationStorageFee(final User user, final Boat boat) {
		boolean result = false;
		try {			
			String query = "SELECT * FROM Notifications JOIN Members ON Members.IdMember = Notifications.Member JOIN Boats ON Boats.IdBoat = Notifications.Boat JOIN Fees ON Fees.IdFee = Notifications.Fee WHERE Notifications.Member = ? AND Notifications.Boat = ? AND Fees.Type = ? AND Notifications.StatusCode = ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, user.getId());
			pstmt.setInt(2, boat.getId());
			pstmt.setString(3, FeeType.STORAGE.toString());
			pstmt.setInt(4, StatusCode.ACTIVE.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next())
				result = true;
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Updates the status of a notification.
	 * 
	 * @param idUser the unique code of the member who received the notification. 
	 * @param idBoat the unique code of the boat to which the storage fee refers or <code>null</code> if the fee to be notified is of membership. 
	**/
	public void updateStatusCodeNotification(final int idUser, final Integer idBoat) {		
		if (idBoat != null)
			this.updateStatusCodeNotificationStorageFee(idUser, idBoat);
		else 
			this.updateStatusCodeNotificationMembershipFee(idUser);
			
	}
	
	private void updateStatusCodeNotificationMembershipFee(final int idUser) {
		try {
			String query = "UPDATE Notifications SET StatusCode = ? WHERE Member = ? AND Boat IS NULL AND StatusCode = ?"; 
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, StatusCode.ELIMINATED.getValue());
			pstmt.setInt(2, idUser);		
			pstmt.setInt(3, StatusCode.ACTIVE.getValue());
						
			pstmt.executeUpdate();	
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	private void updateStatusCodeNotificationStorageFee(final int idUser, final int idBoat) {
		try {
			String query = "UPDATE Notifications SET StatusCode = ? WHERE Member = ? AND Boat = ? AND StatusCode = ?"; 
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, StatusCode.ELIMINATED.getValue());
			pstmt.setInt(2, idUser);
			pstmt.setInt(3, idBoat);
			pstmt.setInt(4, StatusCode.ACTIVE.getValue());
						
			pstmt.executeUpdate();	
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Updates all notifications given the unique code of the old fee.
	 * 
	 * @param idOldFee the old fee.
	 * @param idNewFee the new fee.
	**/
	public void updateNotificationsByFee(final int idOldFee, final int idNewFee) {
		ArrayList<Notification> list = this.getAllNotificationsByFee(idOldFee);
		for (Notification notification: list) {
			
			Integer idBoat = null;
			if (notification.getBoat() != null) {
				if (notification.getBoat().getId() != 0)
					idBoat = notification.getBoat().getId();
			}
			
			this.updateStatusCodeNotification(notification.getMember().getId(), idBoat);
			this.insertNotification(notification.getMember().getId(), idBoat, idNewFee);
		}
	}
	
	/**
	 * Gets the list of all notifications for the given fee.
	 * 
	 * @param idFee the unique code of the fee.
	 * @return the list.
	**/
	public ArrayList<Notification> getAllNotificationsByFee(final int idFee) {
		ArrayList<Notification> list = new ArrayList<Notification>();
		try {
			String query = "SELECT * FROM Notifications JOIN Members ON Members.IdMember = Notifications.Member JOIN Users ON Users.IdUser = Members.IdMember LEFT JOIN Boats ON Boats.IdBoat = Notifications.Boat JOIN Fees ON Fees.IdFee = Notifications.Fee WHERE Notifications.Fee = ? AND Notifications.StatusCode = ? ORDER BY Notifications.IdNotification";

			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, idFee);			
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
	
}
