package main.java.it.unipr.sql;

import main.java.it.unipr.model.*;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * The class {@code RaceDAO} defines a model for the management of the query of the entity Races of the database.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class RaceDAO {
	
	/**
	 * Gets the list of all the races.
	 * 
	 * @return the list.
	**/ 
	public ArrayList<Race> getAllRaces() {
		ArrayList<Race> list = new ArrayList<Race>();
		try {
			String query = "SELECT * FROM Races WHERE StatusCode <> ? ORDER BY IdRace";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, StatusCode.ELIMINATED.getValue());
						
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				list.add(DBUtil.setRaceFromResultSet(rset));
			}
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * Gets a race given the date.
	 * 
	 * @param date the date.
	 * @return the reference of the race or <code>null</code>.
	**/
	public Race getRaceByDate(final Date date) {
		Race race = null;
		try {
			String query = "SELECT * FROM Races WHERE Date = ? AND StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setDate(1, new java.sql.Date(date.getTime()));
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next())
				race = DBUtil.setRaceFromResultSet(rset);
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return race;
	}
	
	/**
	 * Inserts a race in the database.
	 * 
	 * @param name the name of the race.
	 * @param place the place of the race.
	 * @param dateRace the date of the race.
	 * @param boatsNumber the number of the participating boats.
	 * @param registrationFee the registration fee of the race.
	 * @param endDateRegistration the end date registration of a boat at the race.
	**/
	public void insertRace(final String name, final String place, final Date dateRace, final int boatsNumber, final float registrationFee, final Date endDateRegistration) {
		try {			
			String query = "INSERT INTO Races (Name, Place, Date, BoatsNumber, RegistrationFee, EndDateRegistration, StatusCode) VALUES (?,?,?,?,?,?,?)";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setString(1, name);
			pstmt.setString(2, place);
			pstmt.setDate(3, new java.sql.Date(dateRace.getTime()));
			pstmt.setInt(4, boatsNumber);
			pstmt.setFloat(5, registrationFee);
			pstmt.setDate(6, new java.sql.Date(endDateRegistration.getTime()));
			pstmt.setInt(7, StatusCode.ACTIVE.getValue());

			pstmt.executeUpdate();
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Updates the information of a race.
	 * 
	 * @param id the unique identifier of the race.
	 * @param name the new name of the race.
	 * @param place the new place of the race.
	 * @param dateRace the new date of the race.
	 * @param boatsNumber the new number of the participating boats.
	 * @param registrationFee the new registration fee of the race.
	 * @param endDateRegistration the new end date registration of a boat at the race.
	**/
	public void updateRace(final int id, final String name, final String place, final Date dateRace, final int boatsNumber, final Float registrationFee, final Date endDateRegistration) {
		try {		
			String query = "UPDATE Races SET Name = IfNull(?, Name), Place = IfNull(?, Place), Date = IfNull(?, Date), BoatsNumber = IfNull(?, BoatsNumber), RegistrationFee = IfNull(?, RegistrationFee), EndDateRegistration = IfNull(?, EndDateRegistration) WHERE IdRace = ?"; 			
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setString(1, name);
			pstmt.setString(2, place);
			
			if (dateRace == null) {
	            pstmt.setNull(3, Types.DATE);
	        } else {
	        	pstmt.setDate(3, new java.sql.Date(dateRace.getTime()));
	        }
			
			if (boatsNumber == 0) {
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
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Removes a race from the database.
	 * 
	 * @param idRace the unique identifier of the race.
	**/
	public void removeRace(final int idRace) {
		try {
			String query = "UPDATE Races SET StatusCode = ? WHERE IdRace = ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, StatusCode.ELIMINATED.getValue());
			pstmt.setInt(2, idRace);

			pstmt.executeUpdate();
			DBUtil.dbDisconnect(null, pstmt);
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
		Race race = null;
		try {
			String query = "SELECT * FROM Races WHERE IdRace = ? AND StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());

			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) 
				race = DBUtil.setRaceFromResultSet(rset);
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return race;
	}
}
