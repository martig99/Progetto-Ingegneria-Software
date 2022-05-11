package main.java.it.unipr.sql;

import main.java.it.unipr.model.*;

import java.sql.*;
import java.util.ArrayList;

/**
 * The class {@code RaceRegistrationDAO} defines a model for the management of the query of the entity RaceRegistrations of the database.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class RaceRegistrationDAO {
	
	/**
	 * Gets the number of boats participating in a race.
	 * 
	 * @param idRace the unique identifier of the race.
	 * @return the number of boats participating.
	**/
	public int getNumberBoatsInRace(final int idRace) {
		try {
			String query = "SELECT COUNT(*) FROM RaceRegistrations WHERE Race = ? AND StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, idRace);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());

			ResultSet rset = pstmt.executeQuery();
			if (rset.next())
				return rset.getInt(1);
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	 * Checks whether a member’s boat participates in a given race.
	 * 
	 * @param idMember the unique identifier of the member.
	 * @param idRace the unique identifier of the race.
	 * @return <code>true</code> if in the race there is a boat owned by the user.
	**/
	public boolean checkBoatInRaceByMember(final int idMember, final int idRace) {
		try {		
			String query = "SELECT * FROM RaceRegistrations JOIN Boats ON Boats.IdBoat = RaceRegistrations.Boat JOIN Members ON Members.IdMember = Boats.Owner JOIN Users ON Users.IdUser = Members.IdMember WHERE RaceRegistrations.Race = ? AND Boats.Owner = ? AND RaceRegistrations.StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, idRace);
			pstmt.setInt(2, idMember);
			pstmt.setInt(3, StatusCode.ELIMINATED.getValue());

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
	 * Gets the race registration given the race and the boat registered.
	 * 
	 * @param idRace the unique identifier of the race.
	 * @param idBoat the unique identifier of the boat.
	 * @return the reference of the race registration or <code>null</code>.
	**/
	public RaceRegistration getRaceRegistration(final int idRace, final int idBoat) {
		try {
			String query = "SELECT * FROM RaceRegistrations JOIN Races ON Races.IdRace = RaceRegistrations.Race JOIN Boats ON Boats.IdBoat = RaceRegistrations.Boat JOIN Members ON Members.IdMember = Boats.Owner JOIN Users ON Users.IdUser = Members.IdMember WHERE RaceRegistrations.Race = ? AND RaceRegistrations.Boat = ? AND RaceRegistrations.StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, idRace);
			pstmt.setInt(2, idBoat);
			pstmt.setInt(3, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next())
				return DBUtil.setRaceRegistrationFromResultSet(rset);
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Gets the registration to the race given the unique identifier.
	 * 
	 * @param id the unique identifier of the registration for the race.
	 * @return the reference of the race registration or <code>null</code>.
	**/
	public RaceRegistration getRaceRegistrationById(final int id) {
		try {			
			String query = "SELECT * FROM RaceRegistrations JOIN Races ON Races.IdRace = RaceRegistrations.Race JOIN Boats ON Boats.IdBoat = RaceRegistrations.Boat JOIN Members ON Members.IdMember = Boats.Owner JOIN Users ON Users.IdUser = Members.IdMember WHERE RaceRegistrations.IdRegistration = ? AND RaceRegistrations.StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return DBUtil.setRaceRegistrationFromResultSet(rset);
			}		
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Registers a boat at a race. Inserts a new race registration in the database.
	 * 
	 * @param idRace the unique identifier of the race.
	 * @param idBoat the unique identifier of the boat registered to the race.
	**/
	public void registerBoatAtRace(final int idRace, final int idBoat) {
		try {
			String query = "INSERT INTO RaceRegistrations (Date, Race, Boat, StatusCode) VALUES (?,?,?,?)";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
			pstmt.setDate(1, sqlDate);
			pstmt.setInt(2, idRace);
			pstmt.setInt(3, idBoat);
			pstmt.setInt(4, StatusCode.ACTIVE.getValue());
			
			pstmt.executeUpdate();			
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Gets the list of all the registration given a race.
	 * 
	 * @param idRace the unique identifier of the race.
	 * @return the list.
	**/
	public ArrayList<RaceRegistration> getAllRegistrationsByRace(final int idRace) {
		ArrayList<RaceRegistration> list = new ArrayList<RaceRegistration>();
		try {	
			String query = "SELECT * FROM RaceRegistrations JOIN Races ON Races.IdRace = RaceRegistrations.Race JOIN Boats ON Boats.IdBoat = RaceRegistrations.Boat JOIN Members ON Members.IdMember = Boats.Owner JOIN Users ON Users.IdUser = Members.IdMember WHERE RaceRegistrations.Race = ? AND RaceRegistrations.StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, idRace);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
						
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				list.add(DBUtil.setRaceRegistrationFromResultSet(rset));
			}			
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * Check if the registrations exist for a race or not.
	 * 
	 * @param idRace the unique identifier of the race.
	 * @return <code>true</code> if there is at least one registration for the race.
	**/
	public boolean existRegistrationsForRace(final int idRace) {
		try {	
			String query = "SELECT * FROM RaceRegistrations JOIN Races ON Races.IdRace = RaceRegistrations.Race JOIN Boats ON Boats.IdBoat = RaceRegistrations.Boat JOIN Members ON Members.IdMember = Boats.Owner JOIN Users ON Users.IdUser = Members.IdMember WHERE RaceRegistrations.Race = ? AND RaceRegistrations.StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, idRace);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
	
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
	 * Gets the list of all registrations to the races given a boat
	 * 
	 * @param idBoat the unique identifier of the boat.
	 * @return the list.
	**/
	public ArrayList<RaceRegistration> getAllRegistrationByBoat(final int idBoat) {
		ArrayList<RaceRegistration> list = new ArrayList<RaceRegistration>();
		try {		
			String query = "SELECT * FROM RaceRegistrations JOIN Races ON Races.IdRace = RaceRegistrations.Race JOIN Boats ON Boats.IdBoat = RaceRegistrations.Boat JOIN Races ON Races.IdRace = RaceRegistrations.Race WHERE RaceRegistrations.Boat = ? AND RaceRegistrations.StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, idBoat);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
						
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				list.add(DBUtil.setRaceRegistrationFromResultSet(rset));
			}			
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * Removes a race registration from the database. 
	 * 
	 * @param idRegistration the unique identifier of the registration.
	**/
	public void removeRaceRegistration(final int idRegistration) {
		try {
			String query = "UPDATE RaceRegistrations SET StatusCode = ? WHERE IdRegistration = ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, StatusCode.ELIMINATED.getValue());
			pstmt.setInt(2, idRegistration);
			
			pstmt.executeUpdate();
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Updates the information of a race registration.
	 * 
	 * @param idRegistration the unique identifier of the registration. 
	 * @param idBoat the new boat.
	**/
	public void updateRaceRegistration(final int idRegistration, final int idBoat) {
		try {
			String query = "UPDATE RaceRegistrations SET Boat = ? WHERE IdRegistration = ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, idBoat);
			pstmt.setInt(2, idRegistration);
			
			pstmt.executeUpdate();
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
}
