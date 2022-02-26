package it.unipr.java.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SailingClub {
	
	private static final String DBURL = "jdbc:mysql://localhost:3306/sailingclub?";
	private static final String ARGS = "serverTimezone=UTC";
	private static final String LOGIN = "adminclub";
	private static final String PASSWORD = "Melograno00$";
	
	/**
	 * Class constructor.
	**/
	public SailingClub() {}
	
	/**
	 * Performs club member's login.
	 * 
	 * @param email the login email address.
	 * @param password the login password.
	 * @return the reference of the club member found in the database or <code>null</code>.
	**/
	public Member loginMember (final String email, final String password) {
		String query = "SELECT * FROM Users JOIN Members ON IdUser = IdMember WHERE Email = ? AND Password = ?";
		
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()){
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return new Member(rset.getInt("IdUser"), rset.getString("FiscalCode"), rset.getString("FirstName"), rset.getString("LastName"), rset.getString("Email"), rset.getString("Password"), rset.getFloat("MembershipFee"));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Performs club employee's login.
	 * 
	 * @param email the login email address.
	 * @param password the login password.
	 * @return the reference of the club employee found in the database or <code>null</code>.
	**/
	public Employee loginEmployee (final String email, final String password) {
		String query = "SELECT * FROM Users JOIN Employees ON IdUser = IdEmployee WHERE Email = ? AND Password = ?";
		
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()){
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return new Employee(rset.getInt("IdUser"), rset.getString("FiscalCode"), rset.getString("FirstName"), rset.getString("LastName"), rset.getString("Email"), rset.getString("Password"), rset.getBoolean("Administrator"));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Performs user's login.
	 * 
	 * @return the reference of the user logged in or <code>null</code>.
	**/
	public User login (final String email, final String password) {
		Member member = this.loginMember(email, password);
		Employee employee = this.loginEmployee(email, password);
		
		if (member != null) {
			System.out.println(member.toString());
			return member;
		} else if (employee != null) {
			System.out.println(employee.toString());
			return employee;
		} else {
			System.out.println("The credentials entered are incorrect.");
			return null;
		}
	}
}
