package main.java.it.unipr.sql;

import main.java.it.unipr.model.*;

import java.sql.*;
import java.util.*;

/**
 * The class {@code BoatDAO} defines a model for the management of the query of the entity Boats of the database.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class BoatDAO {
	
	/**
	 * Gets the list of all the boats.
	 * If the member passed as parameter is different from <code>null</code> returns the list of the boats owned by the user.
	 * 
	 * @param owner the owner of the boats. 
	 * @return the list.
	**/
	public ArrayList<Boat> getAllBoats(final User owner) {
		ArrayList<Boat> list = new ArrayList<Boat>();
		try {
			String query = "SELECT * FROM Boats JOIN Members ON Members.IdMember = Boats.Owner JOIN Users ON Users.IdUser = Members.IdMember WHERE (? IS NULL OR Boats.Owner = ?) AND Boats.StatusCode <> ? ORDER BY Boats.IdBoat";
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			
			if (owner != null) {
				pstmt.setInt(1, owner.getId());
				pstmt.setInt(2, owner.getId());
			} else {
				pstmt.setNull(1, Types.INTEGER);
				pstmt.setNull(2, Types.INTEGER);
			}
			
			pstmt.setInt(3, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				list.add(DBUtil.setBoatFromResultSet(rset));
			}		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}

	/**
	 * Gets a list with the name of all the boats owned by a member user.
	 * 
	 * @param owner the member user who owns the boats.
	 * @return the list.
	**/
	public ArrayList<String> getAllNameBoatsByOwner(final User owner) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			String query = "SELECT Boats.Name FROM Boats JOIN Users ON Users.IdUser = Boats.Owner WHERE Boats.Owner = ? AND Boats.StatusCode <> ? ORDER BY Boats.IdBoat";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
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
		Boat boat = null;
		try {	
			String query = "SELECT * FROM Boats JOIN Members ON Members.IdMember = Boats.Owner JOIN Users ON Users.IdUser = Members.IdMember WHERE Boats.Name = ? AND Boats.Owner = ? AND Boats.StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setString(1, name);
			pstmt.setInt(2, owner.getId());
			pstmt.setInt(3, StatusCode.ELIMINATED.getValue());
						
			ResultSet rset = pstmt.executeQuery();
			if (rset.next())
				boat = DBUtil.setBoatFromResultSet(rset);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return boat;
	}
	
	/**
	 * Gets a boat given the unique identifier.
	 * 
	 * @param id the unique identifier of the boat.
	 * @return the reference of the boat or <code>null</code>.
	**/
	public Boat getBoatById(final int id) {
		Boat boat = null;
		try {			
			String query = "SELECT * FROM Boats JOIN Members ON Members.IdMember = Boats.Owner JOIN Users ON Users.IdUser = Members.IdMember WHERE Boats.IdBoat = ? AND Boats.StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
						
			ResultSet rset = pstmt.executeQuery();
			if (rset.next())
				boat = DBUtil.setBoatFromResultSet(rset);
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return boat;
	}
	
	/**
	 * Inserts a new boat in the database.
	 * 
	 * @param name the name of the boat.
	 * @param length the length of the boat.
	 * @param user the owner of the boat.
	**/
	public void insertBoat(final String name, final int length, final User user) {
		try {			
			String query = "INSERT INTO Boats (Name, Length, Owner, StatusCode) VALUES (?,?,?,?)";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setString(1, name);
			pstmt.setInt(2, length);
			pstmt.setInt(3, user.getId());
			pstmt.setInt(4, StatusCode.ACTIVE.getValue());
						
			pstmt.executeUpdate();	
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Updates the information of a boat.
	 * 
	 * @param id the unique identifier of the boat.
	 * @param name the new name of the boat.
	 * @param length the new length of the boat.
	**/
	public void updateBoat(final int id, final String name, final Integer length) {	
		try {		
			String query = "UPDATE Boats SET Name = IfNull(?, Name), Length = IfNull(?, Length) WHERE IdBoat = ?"; 			
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setString(1, name);
			
			if (length != null) {
				pstmt.setInt(2, length);
			} else {
				pstmt.setNull(2, Types.INTEGER);
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
		try {
			String query = "UPDATE Boats SET StatusCode = ? WHERE IdBoat = ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, StatusCode.ELIMINATED.getValue());
			pstmt.setInt(2, id);
						
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Gets the maximum number of boats.
	 * Considers a boat for each user and then calculates the maximum number. 
	 * 
	 * @return the maximum number of boats or 0.
	**/
	public int getMaxBoatsNumber() {
		try {
			String query = "SELECT COUNT(DISTINCT Users.Email) FROM Boats JOIN Users ON Boats.Owner = Users.IdUser WHERE Boats.StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
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
}
