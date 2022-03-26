package main.java.it.unipr.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.java.it.unipr.model.Boat;
import main.java.it.unipr.model.Member;
import main.java.it.unipr.model.Race;
import main.java.it.unipr.model.RaceRegistration;
import main.java.it.unipr.model.StatusCode;
import main.java.it.unipr.model.User;

public class RaceRegistrationDAO {

	/**
	 * 
	 * @param race
	 * @return
	**/
	public int getNumberBoatsInRace(final Race race) {
		try {
			String query = "SELECT COUNT(*) FROM RaceRegistrations WHERE Race = ? AND StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, race.getId());
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
	 * 
	 * @param member
	 * @return
	**/
	public Boat getBoatInRaceByMember(final Member member, final Race race) {
		try {		
			String query = "SELECT * FROM RaceRegistrations JOIN Boats ON Boats.IdBoat = RaceRegistrations.Boat JOIN Members ON Members.IdMember = Boats.Owner JOIN Users ON Users.IdUser = Members.IdMember WHERE RaceRegistrations.Race = ? AND Boats.Owner = ? AND RaceRegistrations.StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, race.getId());
			pstmt.setInt(2, member.getId());
			pstmt.setInt(3, StatusCode.ELIMINATED.getValue());

			ResultSet rset = pstmt.executeQuery();
			if (rset.next())
				return DBUtil.setBoatFromResultSet(rset);
				
			DBUtil.dbDisconnect(rset, pstmt);
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
		try {
			String query = "SELECT * FROM RaceRegistrations WHERE Race = ? AND Boat = ? AND StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, race.getId());
			pstmt.setInt(2, boat.getId());
			pstmt.setInt(3, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next())
				return DBUtil.setRaceRegistrationFromResultSet(rset, race, boat);
			
			DBUtil.dbDisconnect(rset, pstmt);
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
		try {			
			String query = "SELECT * FROM RaceRegistrations JOIN Races ON Races.IdRace = RaceRegistrations.Race JOIN Boats ON Boats.IdBoat = RaceRegistrations.Boat JOIN Members ON Members.IdMember = Boats.Owner JOIN Users ON Users.IdUser = Members.IdMember WHERE RaceRegistrations.IdRegistration = ? AND RaceRegistrations.StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				Race race = DBUtil.setRaceFromResultSet(rset);
				Boat boat = DBUtil.setBoatFromResultSet(rset);
				
				return DBUtil.setRaceRegistrationFromResultSet(rset, race, boat);
			}		
			
			DBUtil.dbDisconnect(rset, pstmt);
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
	**/
	public void registerBoatAtRace(final Race race, final User member, final Boat boat) {
		try {
			String query = "INSERT INTO RaceRegistrations (Date, Race, Boat, StatusCode) VALUES (?,?,?,?)";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
			pstmt.setDate(1, sqlDate);
			pstmt.setInt(2, race.getId());
			pstmt.setInt(3, boat.getId());
			pstmt.setInt(4, StatusCode.ACTIVE.getValue());
			
			pstmt.executeUpdate();			
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param race
	 * @return
	**/
	public ArrayList<RaceRegistration> getAllRegistrationsByRace(final Race race) {
		ArrayList<RaceRegistration> list = new ArrayList<RaceRegistration>();
		try {	
			String query = "SELECT * FROM RaceRegistrations JOIN Boats ON Boats.IdBoat = RaceRegistrations.Boat JOIN Members ON Members.IdMember = Boats.Owner JOIN Users ON Users.IdUser = Members.IdMember WHERE RaceRegistrations.Race = ? AND RaceRegistrations.StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, race.getId());
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
						
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				Boat boat = new Boat(rset.getInt("Boats.IdBoat"), rset.getString("Boats.Name"), rset.getInt("Boats.Length"), (new Member(rset.getInt("Users.IdUser"), rset.getString("Users.FirstName"), rset.getString("Users.LastName"), rset.getString("Users.Email"), rset.getString("Users.Password"), rset.getString("Members.FiscalCode"), rset.getString("Members.Address"))), StatusCode.getStatusCode(rset.getInt("Boats.StatusCode")));
				list.add(DBUtil.setRaceRegistrationFromResultSet(rset, race, boat));
			}			
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 
	 * @param race
	 * @return
	**/
	public boolean existRegistrationsForRace(final Race race) {
		try {	
			String query = "SELECT * FROM RaceRegistrations JOIN Boats ON Boats.IdBoat = RaceRegistrations.Boat JOIN Members ON Members.IdMember = Boats.Owner JOIN Users ON Users.IdUser = Members.IdMember WHERE RaceRegistrations.Race = ? AND RaceRegistrations.StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, race.getId());
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
	 * 
	 * @param boat
	 * @return
	**/
	public ArrayList<RaceRegistration> getAllRegistrationByBoat(final Boat boat) {
		ArrayList<RaceRegistration> list = new ArrayList<RaceRegistration>();
		try {		
			String query = "SELECT * FROM RaceRegistrations JOIN Boats ON Boats.IdBoat = RaceRegistrations.Boat JOIN Races ON Races.IdRace = RaceRegistrations.Race WHERE RaceRegistrations.Boat = ? AND RaceRegistrations.StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, boat.getId());
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
						
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				Race race = DBUtil.setRaceFromResultSet(rset);
				list.add(DBUtil.setRaceRegistrationFromResultSet(rset, race, boat));
			}			
			
			DBUtil.dbDisconnect(rset, pstmt);
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
	public void removeRaceRegistration(final int id) {
		try {
			String query = "UPDATE RaceRegistrations SET StatusCode = ? WHERE IdRegistration = ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, StatusCode.ELIMINATED.getValue());
			pstmt.setInt(2, id);
			
			pstmt.executeUpdate();
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param id
	 * @param boat
	**/
	public void updateRaceRegistration(final int id, final Boat boat) {
		try {
			String query = "UPDATE RaceRegistrations SET Boat = ? WHERE IdRegistration = ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, boat.getId());
			pstmt.setInt(2, id);
			
			pstmt.executeUpdate();
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
}
