package main.java.it.unipr.sql;

import main.java.it.unipr.model.*;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * The class {@code PaymentDAO} defines a model for the management of the query of the entity Payments of the database.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class PaymentDAO {
	
	/**
	 * Gets the list of all description of the payment services.
	 * 
	 * @return the list.
	**/
	public ArrayList<String> getAllPaymentServicesDescription() {
		ArrayList<String> list = new ArrayList<String>();
		try {
			String query = "SELECT * FROM PaymentServices ORDER BY IdPaymentService";
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				list.add(rset.getString("Description"));
			}
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * Gets the payment service given the description.
	 * 
	 * @param description the description.
	 * @return the reference of the payment service or <code>null</code>.
	**/
	public PaymentService getPaymentServiceByDescription(final String description) {
		try {
			String query = "SELECT * FROM PaymentServices WHERE Description = ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);	
			pstmt.setString(1, description);
						
			ResultSet rset = pstmt.executeQuery();
			if (rset.next())
				return DBUtil.setPaymentServiceFromResultSet(rset);
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Gets the last payment made given the type of the fee and other parameters.
	 * 
	 * @param member the member for which the last membership fee has been paid
	 * @param boat the boat for which the last storage fee has been paid
	 * @param feeType the type of fee for which the last payment is due.
	 * @return the date of the last payment or <code>null</code>.
	**/
	public Date getLastPaymentFee(final User member, final Boat boat, final FeeType feeType) {
		try {		
			String query = "";
			int parameter = 0;
			if (feeType == FeeType.MEMBERSHIP) {
				query = "SELECT MAX(ValidityEndDate) FROM Payments JOIN Members ON Members.IdMember = Payments.Member JOIN Fees ON Payments.Fee = Fees.IdFee WHERE Payments.Member = ? AND Fees.Type = ?";
				parameter = member.getId();
			} else if (feeType == FeeType.STORAGE && boat != null) {
				query = "SELECT MAX(ValidityEndDate) FROM Payments JOIN Boats ON Boats.IdBoat = Payments.Boat JOIN Fees ON Payments.Fee = Fees.IdFee WHERE Payments.Boat = ? AND Fees.Type = ?";
				parameter = boat.getId();
			}
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);		
			pstmt.setInt(1, parameter);
			pstmt.setString(2, feeType.toString());
						
			ResultSet rset = pstmt.executeQuery();
			if (rset.next())
				return rset.getDate(1);
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}

	private Date getEndDate(final Date startDate, final int period) {		
		if (startDate != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(startDate);
			c.add(Calendar.DATE, period);
			return c.getTime();
		}
		
		return null;
	}
	
	/**
	 * Check if the payment of the storage fee of a boat is still valid.
	 * 
	 * @param boat the boat.
	 * @return <code>true</code> if the last payment is valid.
	**/
	public boolean checkPaymentStorageFee(final Boat boat) {
		try {		
			String query =  "SELECT * FROM Payments JOIN Members ON Members.IdMember = Payments.Member JOIN Boats ON Boats.IdBoat = Payments.Boat JOIN Fees ON Fees.IdFee = Payments.Fee WHERE Payments.Boat = ? AND Fees.Type = ? AND Payments.ValidityStartDate <= ? AND Payments.ValidityEndDate >= ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, boat.getId());
			pstmt.setString(2, FeeType.STORAGE.toString());
			
			Date currentDate = new Date();
			pstmt.setDate(3, new java.sql.Date(currentDate.getTime()));
			pstmt.setDate(4, new java.sql.Date(currentDate.getTime()));
						
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
	 * Checks whether the payment of the fee of the club member is still valid.
	 * 
	 * @param member the club member.
	 * @return <code>true</code> if the last payment is valid.
	**/
	public boolean checkPaymentMembershipFee(final User member) {
		try {			
			String query = "SELECT * FROM Payments JOIN Members ON Members.IdMember = Payments.Member JOIN Fees ON Fees.IdFee = Payments.Fee WHERE Payments.Member = ? AND Fees.Type = ? AND Payments.ValidityStartDate <= ? AND Payments.ValidityEndDate >= ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, member.getId());
			pstmt.setString(2, FeeType.MEMBERSHIP.toString());
			
			Date currentDate = new Date();
			pstmt.setDate(3, new java.sql.Date(currentDate.getTime()));
			pstmt.setDate(4, new java.sql.Date(currentDate.getTime()));
						
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
	 * Gets the list of all the payments.
	 * 
	 * @return the list.
	**/
	public ArrayList<Payment> getAllPayments(final User user, final FeeType feeType) {
		ArrayList<Payment> list = new ArrayList<Payment>();
		try {			
			String query = "SELECT * FROM Payments JOIN Members ON Members.IdMember = Payments.Member JOIN Users ON Users.IdUser = Members.IdMember LEFT JOIN Boats ON Boats.IdBoat = Payments.Boat LEFT JOIN RaceRegistrations ON RaceRegistrations.IdRegistration = Payments.RaceRegistration LEFT JOIN Races ON Races.IdRace = RaceRegistrations.Race JOIN PaymentServices ON PaymentServices.IdPaymentService = Payments.PaymentService JOIN Fees ON Fees.IdFee = Payments.Fee WHERE (? IS NULL OR Payments.Member = ?) AND Fees.Type = ? ORDER BY Payments.IdPayment";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			if(user != null) {
				pstmt.setInt(1, user.getId());
				pstmt.setInt(2, user.getId());
			} else {
				pstmt.setNull(1, Types.INTEGER);
				pstmt.setNull(2, Types.INTEGER);
			}
			
			pstmt.setString(3, feeType.toString());
						
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {				
				list.add(DBUtil.setPaymentFromResultSet(rset));
			}
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * Pays a fee, that is, inserts a payment in the database.
	 * 
	 * @param member the club member who pays the fee.
	 * @param boat the boat of which it is necessary to pay the storage fee or null if the type of fee to be paid is different.
	 * @param raceRegistration the race registration of which it is necessary to pay the race registration fee or null if the type of fee to be paid is different.
	 * @param fee the fee to be paid.
	 * @param paymentService the type of service used to pay.
	 * @param atLeastOneBoat <code>true</code> if the member owns at least one boat.
	 * @param refund <code>true</code> if the payment is a refund.
	**/
	public void payFee(final User member, final Boat boat, final RaceRegistration raceRegistration, final Fee fee, final PaymentService paymentService, final Boolean atLeastOneBoat, final Boolean refund) {
		try {
			String query = "INSERT INTO Payments (Date, Member, Boat, RaceRegistration, Fee, ValidityStartDate, ValidityEndDate, Total, PaymentService) VALUES (?,?,?,?,?,?,?,?,?)";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			
			java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
			pstmt.setDate(1, sqlDate);
			pstmt.setInt(2, member.getId());
			
			if (boat != null) {
				pstmt.setInt(3, boat.getId());
			} else {
				pstmt.setNull(3, Types.INTEGER);
			}
			
			if (raceRegistration != null) {
				pstmt.setInt(4, raceRegistration.getId());
			} else {
				pstmt.setNull(4, Types.INTEGER);
			}
			
			pstmt.setInt(5, fee.getId());
			
			Date validityStartDate = new Date();
			Date validityEndDate = validityStartDate;
			if (fee.getType() == FeeType.MEMBERSHIP || fee.getType() == FeeType.STORAGE) {
				Date dateLastPayment = this.getLastPaymentFee(member, boat, fee.getType());
				if (dateLastPayment != null) {
					if (atLeastOneBoat || validityStartDate.before(dateLastPayment) || validityStartDate.equals(dateLastPayment)) {
						validityStartDate = this.getEndDate(dateLastPayment, 1);
					}
				}
				
				validityEndDate = this.getEndDate(validityStartDate, fee.getValidityPeriod());
			}
			
			pstmt.setDate(6, new java.sql.Date(validityStartDate.getTime()));
			pstmt.setDate(7, new java.sql.Date(validityEndDate.getTime()));
			
			float total = 0;
			if (fee.getType() == FeeType.MEMBERSHIP) {
				total = fee.getAmount();
			} else if (fee.getType() == FeeType.STORAGE) {
				total = boat.getLength() * fee.getAmount();
			} else if (fee.getType() == FeeType.RACE_REGISTRATION) {
				total = raceRegistration.getRace().getRegistrationFee();
			}
			
			if (refund) {
				total *= (-1);
			}
			
			pstmt.setDouble(8, total);
			pstmt.setInt(9, paymentService.getId());
					
			pstmt.executeUpdate();
			DBUtil.dbDisconnect(null, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Gets the payment given a registration to a race.
	 * 
	 * @param id the unique identifier of the registration for the race.
	**/
	public Payment getPaymentByRaceRegistration(final int id) {
		try {
			String query = "SELECT * FROM Payments JOIN Members ON Members.IdMember = Payments.Member JOIN Users ON Users.IdUser = Members.IdMember JOIN RaceRegistrations ON RaceRegistrations.IdRegistration = Payments.RaceRegistration JOIN Boats ON Boats.IdBoat = RaceRegistrations.Boat JOIN Races ON Races.IdRace = RaceRegistrations.Race JOIN PaymentServices ON PaymentServices.IdPaymentService = Payments.PaymentService JOIN Fees ON Fees.IdFee = Payments.Fee WHERE Payments.RaceRegistration = ?";
			
			PreparedStatement pstmt = DBUtil.prepareQuery(query);
			pstmt.setInt(1, id);
						
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return DBUtil.setPaymentFromResultSet(rset);
			}
			
			DBUtil.dbDisconnect(rset, pstmt);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Refunds the payment of the registration for a race.
	 * 
	 * @param id the unique identifier of the registration for the race.
	**/
	public void refundRegistrationFee(final int id) {
		Payment previousPayment = this.getPaymentByRaceRegistration(id); 
		if (previousPayment != null) {
			this.payFee(previousPayment.getUser(), previousPayment.getBoat(), previousPayment.getRaceRegistration(), previousPayment.getFee(), previousPayment.getPaymentService(), false, true);
		}
	}
}
