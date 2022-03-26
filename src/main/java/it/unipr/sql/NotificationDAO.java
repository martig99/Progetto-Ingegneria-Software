package main.java.it.unipr.sql;

import main.java.it.unipr.model.*;

import java.sql.*;
import java.util.*;

public class NotificationDAO {
	
	/**
	 * 
	 * @param user
	 * @param boat
	 * @return
	**/
	public ArrayList<Notification> getAllNotifications(final User user) {
		ArrayList<Notification> list = new ArrayList<Notification>();
		try {
			String query = "SELECT * FROM Notifications JOIN Members ON Members.IdMember = Notifications.Member JOIN Users ON Users.IdUser = Members.IdMember LEFT JOIN Boats ON Boats.IdBoat = Notifications.Boat JOIN Fees ON Fees.IdFee = Notifications.Fee WHERE Notifications.Member = ? AND Notifications.ReadStatus = ? ORDER BY Notifications.IdNotification";

			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, user.getId());			
			pstmt.setBoolean(2, false);
			
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
	 * Inserts a notification in the database.
	**/
	public void insertNotification(final Member member, final Boat boat, final Fee fee) {				
		try {			
			String query = "INSERT INTO Notifications (Member, Boat, Fee, ReadStatus) VALUES (?,?,?,?)";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, member.getId());
			
			if (boat != null) {
				pstmt.setInt(2, boat.getId());
			} else {
				pstmt.setNull(2, Types.INTEGER);
			}
			
			pstmt.setInt(3, fee.getId());
			pstmt.setBoolean(4, false);
						
			pstmt.executeUpdate();	
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public Notification getNotification(final Member member, final Boat boat, final Fee fee, final boolean readStatus) {
		try {			
			String query = "SELECT * FROM Notifications JOIN Members ON Members.IdMember = Notifications.Member JOIN Users ON Users.IdUser = Members.IdMember LEFT JOIN Boats ON Boats.IdBoat = Notifications.Boat JOIN Fees ON Fees.IdFee = Notifications.Fee WHERE Notifications.Member = ? AND (? IS NULL OR Notifications.Boat = ?) AND Notifications.Fee = ? AND Notifications.ReadStatus = ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, member.getId());
			
			if (boat != null) {
				pstmt.setInt(2, boat.getId());
				pstmt.setInt(3, boat.getId());
			} else {
				pstmt.setNull(2, Types.INTEGER);
				pstmt.setNull(3, Types.INTEGER);
			}
			
			pstmt.setInt(4, fee.getId());
			pstmt.setBoolean(5, readStatus);
					
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
	 * 
	**/
	public boolean existNotificationMembershipFee(final User user) {
		try {			
			String query = "SELECT * FROM Notifications JOIN Members ON Members.IdMember = Notifications.Member JOIN Fees ON Fees.IdFee = Notifications.Fee WHERE Notifications.Member = ? AND Fees.Type = ? AND Notifications.ReadStatus = ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, user.getId());
			pstmt.setString(2, FeeType.MEMBERSHIP.toString());
			pstmt.setBoolean(3, false);
			
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
	 * 
	 * @param user
	 * @param fee
	 * @return
	**/
	public boolean existNotificationStorageFee(final User user, final Boat boat) {
		try {			
			String query = "SELECT * FROM Notifications JOIN Members ON Members.IdMember = Notifications.Member JOIN Boats ON Boats.IdBoat = Notifications.Boat JOIN Fees ON Fees.IdFee = Notifications.Fee WHERE Notifications.Member = ? AND Notifications.Boat = ? AND Fees.Type = ? AND Notifications.ReadStatus = ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, user.getId());
			pstmt.setInt(2, boat.getId());
			pstmt.setString(3, FeeType.STORAGE.toString());
			pstmt.setBoolean(4, false);
			
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
	 * 
	**/
	public void updateReadStatusNotification(final User user, final Boat boat, final Fee fee) {		
		try {		
			String query = "UPDATE Notifications SET ReadStatus = ? WHERE Member = ? AND (? IS NULL OR Boat = ?) AND Fee = ?"; 			
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setBoolean(1, true);
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
