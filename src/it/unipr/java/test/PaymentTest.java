package it.unipr.java.test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import it.unipr.java.model.Boat;
import it.unipr.java.model.Fee;
import it.unipr.java.model.FeeType;
import it.unipr.java.model.Member;
import it.unipr.java.model.Payment;
import it.unipr.java.model.PaymentService;
import it.unipr.java.model.Race;
import it.unipr.java.model.RaceRegistration;
import it.unipr.java.model.SailingClub;
import it.unipr.java.model.StatusCode;

public class PaymentTest {
	
	final static int idPayment = 1;
	final static Date datePayment = Date.valueOf("2022-03-08");
	final static int idMember = 1;
	final static int idRaceRegistration = 1;
	final static int idBoat = 1;
	final static FeeType ftype = FeeType.RACE_REGISTRATION;
	final static Date validityStartDatePayment = Date.valueOf("2022-03-08");
	final static Date validityEndDatePayment = Date.valueOf("2022-03-08");
	final static String paymentServiceDescription = "Transfer Receipt";
	
	@Test
	void initialTestRace()
	{
		SailingClub s = new SailingClub();
		Member m = s.getMemberById(idMember);
		RaceRegistration rr = s.getRaceRegistrationById(idRaceRegistration);
		//Boat b = rr.getBoat();
		Boat b = s.getBoatById(idBoat);
		float total = rr.getRace().getRegistrationFee();
		PaymentService ps =  s.getPaymentServiceByDescription(paymentServiceDescription);
		Fee f = s.getFee(ftype);
		
		String toString = "Id: " + idPayment + " - Date: " + datePayment + " - Member: " + m.getEmail() + " - Boat: [" + b.toString() + "] - Race Registration: [" + rr.toString() + "] - Validity Start Date: " + validityStartDatePayment + " - Validity End Date: " + validityEndDatePayment + " - Total: " + total + " - Payment Service: " + ps.getDescription();

		Payment p = new Payment();
		p.setId(idPayment);
		p.setDate(datePayment);
		p.setMember(m);
		p.setBoat(b);
		p.setRaceRegistration(rr);
		p.setFee(f);
		p.setValidityStartDate(validityStartDatePayment);
		p.setValidityEndDate(validityEndDatePayment);
		p.setTotal(total);
		p.setPaymentService(ps);
		
		assertEquals(idPayment, p.getId(),
				() -> p.getId() + " should equal " + idPayment);
		assertEquals(datePayment, p.getDate(),
				() -> p.getDate() + " should equal " + datePayment);
		assertEquals(m.toString(), p.getMember().toString(),
				() -> p.getMember().toString() + " should equal " + m.toString());
		assertEquals(b.toString(), p.getBoat().toString(),
				() -> p.getBoat().toString() + " should equal " + b.toString());
		assertEquals(rr.toString(), p.getRaceRegistration().toString(),
				() -> p.getRaceRegistration().toString() + " should equal " + rr.toString());
		assertEquals(f.toString(), p.getFee().toString(),
				() -> p.getFee().toString() + " should equal " + f.toString());
		assertEquals(validityStartDatePayment, p.getValidityStartDate(),
				() -> p.getValidityStartDate() + " should equal " + validityStartDatePayment);
		assertEquals(validityEndDatePayment, p.getValidityEndDate(),
				() -> p.getValidityEndDate() + " should equal " + validityEndDatePayment);
		assertEquals(total, p.getTotal(),
				() -> p.getTotal() + " should equal " + total);
		assertEquals(ps.toString(), p.getPaymentService().toString(),
				() -> p.getPaymentService().toString() + " should equal " + ps.toString());
		
		Payment p2 = new Payment(idPayment,datePayment,m,b,rr,f,validityStartDatePayment,validityEndDatePayment,total,ps);
		
		assertEquals(idPayment, p2.getId(),
				() -> p2.getId() + " should equal " + idPayment);
		assertEquals(datePayment, p2.getDate(),
				() -> p2.getDate() + " should equal " + datePayment);
		assertEquals(m.toString(), p2.getMember().toString(),
				() -> p2.getMember().toString() + " should equal " + m.toString());
		assertEquals(b.toString(), p2.getBoat().toString(),
				() -> p2.getBoat().toString() + " should equal " + b.toString());
		assertEquals(rr.toString(), p2.getRaceRegistration().toString(),
				() -> p2.getRaceRegistration().toString() + " should equal " + rr.toString());
		assertEquals(f.toString(), p2.getFee().toString(),
				() -> p2.getFee().toString() + " should equal " + f.toString());
		assertEquals(validityStartDatePayment, p2.getValidityStartDate(),
				() -> p2.getValidityStartDate() + " should equal " + validityStartDatePayment);
		assertEquals(validityEndDatePayment, p2.getValidityEndDate(),
				() -> p2.getValidityEndDate() + " should equal " + validityEndDatePayment);
		assertEquals(total, p2.getTotal(),
				() -> p2.getTotal() + " should equal " + total);
		assertEquals(ps.toString(), p2.getPaymentService().toString(),
				() -> p2.getPaymentService().toString() + " should equal " + ps.toString());
	}
	
	@Test
	void checkPaymentFeeTest()
	{
		SailingClub s = new SailingClub();
		Member m = s.getMemberById(idMember);
		Boat b = s.getBoatById(idBoat);
		assertEquals(true, s.checkPaymentFee(m,b,FeeType.STORAGE), //se l'id dello storage è uno vecchio non funziona, tipo l'id è 4 non va solo con l'ultimo funziona
				() -> s.checkPaymentFee(m,b,FeeType.STORAGE) + " should equal " + true);
		
		assertEquals(true, s.checkPaymentFee(m,null,FeeType.MEMBERSHIP),
				() -> s.checkPaymentFee(m,null,FeeType.MEMBERSHIP) + " should equal " + true);
	}
	

}
