package main.java.it.unipr.sql;

import main.java.it.unipr.model.*;

import java.sql.*;
import java.util.ArrayList;

public class FeeDAO {
	
	/**
	 * Gets the fee identification code given the fee type. 
	 * 
	 * @param type the fee type.
	 * @return the reference of the fee or <code>null</code>.
	**/
	public Fee getFeeByType(final FeeType type) {
		try {
			String query = "SELECT * FROM Fees WHERE Type = ? AND StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setString(1, type.toString());
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next())
				return DBUtil.setFeeFromResultSet(rset);
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Gets the list of all the fees.
	 * 
	 * @return the list.
	**/
	public ArrayList<Fee> getAllFees() {
		ArrayList<Fee> list = new ArrayList<Fee>();
		try {
			String query = "SELECT * FROM Fees WHERE StatusCode <> ? AND Type <> ? ORDER BY IdFee";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, StatusCode.ELIMINATED.getValue());
			pstmt.setString(2, FeeType.RACE_REGISTRATION.toString());
						
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				list.add(DBUtil.setFeeFromResultSet(rset));
			}
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * Gets a fee given the unique identifier.
	 * 
	 * @param id the unique identifier of the fee.
	 * @return the reference of the fee or <code>null</code>.
	**/
	public Fee getFeeById(final int id) {
		try {
			String query = "SELECT * FROM Fees WHERE IdFee = ? AND StatusCode <> ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, StatusCode.ELIMINATED.getValue());
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next())
				return DBUtil.setFeeFromResultSet(rset);
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Updates a fee.
	**/
	public void updateFee(final int id, final FeeType feeType, final float amount, final int period) {
		this.removeFee(id);
		this.insertFee(feeType, amount, period);
	}
	
	/**
	 * Removes a fee from the database.
	 * 
	 * @param id the unique identifier of the fee.
	**/
	public void removeFee(final int id) {
		try {
			String query = "UPDATE Fees SET StatusCode = ? WHERE IdFee = ?";
			
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
	 * Inserts a new fee in the database.
	 * 
	 * @param type the type of the new fee.
	 * @param amount the amount of the new fee.
	 * @param validityPeriod the validity period of the new fee.
	**/
	public void insertFee(final FeeType type, final float amount, final int validityPeriod) {
		try {
			String query = "INSERT INTO Fees (Type, Amount, ValidityPeriod, StatusCode) VALUES (?,?,?,?)";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setString(1, type.toString());
			pstmt.setDouble(2, amount);
			pstmt.setInt(3, validityPeriod);
			pstmt.setInt(4, StatusCode.ACTIVE.getValue());
			
			pstmt.executeUpdate();
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

}
