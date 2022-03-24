package it.unipr.java.test;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Locale;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import it.unipr.java.model.*;


public class RaceTest {
	
	final static int idRace = 1;
	final static String nameRace = "Gara 1";
	final static String placeRace = "Roma";
	final static Date dateRace = Date.valueOf("2022-03-08");
	final static int boatsNumberRace = 3;
	final static float registrationFeeRace = 10;
	final static Date endDateRace = Date.valueOf("2022-03-07");
	
	final int idRegistration = 1;
	final int idBoat = 1;
	final static Date dateRegistration = Date.valueOf("2022-03-06");
	
	@Test
	void initialTestRace()
	{
		String toString = "Id: " + idRace + " - Name: " + nameRace + " - Place: " + placeRace + " - Date: " + dateRace + " - Boats Number: " + boatsNumberRace + " - Registration Fee: " + registrationFeeRace + " - End Date Registration: " + endDateRace + " - Status Code: " + StatusCode.ACTIVE.toString();
		
		Race r = new Race();
		r.setId(idRace);
		r.setName(nameRace);
		r.setPlace(placeRace);
		r.setDate(dateRace);
		r.setBoatsNumber(boatsNumberRace);
		r.setRegistrationFee(registrationFeeRace);
		r.setEndDateRegistration(endDateRace);
		r.setStatusCode(StatusCode.ACTIVE);
		
		assertEquals(idRace, r.getId(),
				() -> r.getId() + " should equal " + idRace);
		assertEquals(nameRace, r.getName(),
				() -> r.getName() + " should equal " + nameRace);
		assertEquals(placeRace, r.getPlace(),
				() -> r.getPlace() + " should equal " + placeRace);
		assertEquals(dateRace, r.getDate(),
				() -> r.getDate() + " should equal " + dateRace);
		assertEquals(boatsNumberRace, r.getBoatsNumber(),
				() -> r.getBoatsNumber() + " should equal " + boatsNumberRace);
		assertEquals(registrationFeeRace, r.getRegistrationFee(),
				() -> r.getRegistrationFee() + " should equal " + registrationFeeRace);
		assertEquals(endDateRace, r.getEndDateRegistration(),
				() -> r.getEndDateRegistration() + " should equal " + endDateRace);
		assertEquals(StatusCode.ACTIVE.toString(),r.getStatusCode().toString(),
				() ->  r.getStatusCode().toString() + " should equal " + StatusCode.ACTIVE.toString());
		assertEquals(toString, r.toString(),
				() -> r.toString() + " should equal " + toString);
		
		Race r2 = new Race(idRace,nameRace,placeRace,dateRace,boatsNumberRace,registrationFeeRace,endDateRace,StatusCode.ACTIVE);
		assertEquals(idRace, r2.getId(),
				() -> r2.getId() + " should equal " + idRace);
		assertEquals(nameRace, r2.getName(),
				() -> r2.getName() + " should equal " + nameRace);
		assertEquals(placeRace, r2.getPlace(),
				() -> r2.getPlace() + " should equal " + placeRace);
		assertEquals(dateRace, r2.getDate(),
				() -> r2.getDate() + " should equal " + dateRace);
		assertEquals(boatsNumberRace, r2.getBoatsNumber(),
				() -> r2.getBoatsNumber() + " should equal " + boatsNumberRace);
		assertEquals(registrationFeeRace, r2.getRegistrationFee(),
				() -> r2.getRegistrationFee() + " should equal " + registrationFeeRace);
		assertEquals(endDateRace, r2.getEndDateRegistration(),
				() -> r2.getEndDateRegistration() + " should equal " + endDateRace);
		assertEquals(StatusCode.ACTIVE.toString(),r.getStatusCode().toString(),
				() ->  r.getStatusCode().toString() + " should equal " + StatusCode.ACTIVE.toString()); 
		assertEquals(toString, r2.toString(),
				() -> r2.toString() + " should equal " + toString);
	}
	
	
	
	
	@Test
	void getRaceByDateTest()
	{
		SailingClub s = new SailingClub();
		Race r = s.getRaceByDate(dateRace);
		
		assertEquals(idRace, r.getId(),
				() -> r.getId() + " should equal " + idRace);
	}
	
	@Test
	void updateRaceTest()
	{
		SailingClub s = new SailingClub();

		String newNameRace = "Gara 100";
		s.updateRace(idRace, newNameRace, null, null, null, null, null);
		Race r = s.getRaceById(idRace);
		assertEquals(newNameRace, r.getName(),
				() -> r.getName() + " should equal " + newNameRace);
		assertEquals(placeRace, r.getPlace(),
				() -> r.getPlace() + " should equal " + placeRace);
		assertEquals(dateRace, r.getDate(),
				() -> r.getDate() + " should equal " + dateRace);
		assertEquals(boatsNumberRace, r.getBoatsNumber(),
				() -> r.getBoatsNumber() + " should equal " + boatsNumberRace);
		assertEquals(registrationFeeRace, r.getRegistrationFee(),
				() -> r.getRegistrationFee() + " should equal " + registrationFeeRace);
		assertEquals(endDateRace, r.getEndDateRegistration(),
				() -> r.getEndDateRegistration() + " should equal " + endDateRace);
		
		String newPlaceRace = "Parma";
		s.updateRace(idRace, null, newPlaceRace, null, null, null,null);
		Race r2 = s.getRaceById(idRace);
		assertEquals(newNameRace, r2.getName(),
				() -> r2.getName() + " should equal " + newNameRace);
		assertEquals(newPlaceRace, r2.getPlace(),
				() -> r2.getPlace() + " should equal " + newPlaceRace);
		assertEquals(dateRace, r2.getDate(),
				() -> r2.getDate() + " should equal " + dateRace);
		assertEquals(boatsNumberRace, r2.getBoatsNumber(),
				() -> r2.getBoatsNumber() + " should equal " + boatsNumberRace);
		assertEquals(registrationFeeRace, r2.getRegistrationFee(),
				() -> r2.getRegistrationFee() + " should equal " + registrationFeeRace);
		assertEquals(endDateRace, r2.getEndDateRegistration(),
				() -> r2.getEndDateRegistration() + " should equal " + endDateRace);
		
		
		Date newDateRace = Date.valueOf("2022-03-10");
		s.updateRace(idRace, null, null, newDateRace, null, null, null);
		Race r3 = s.getRaceById(idRace);
		assertEquals(newNameRace, r3.getName(),
				() -> r3.getName() + " should equal " + newNameRace);
		assertEquals(newPlaceRace, r3.getPlace(),
				() -> r3.getPlace() + " should equal " + newPlaceRace);
		assertEquals(newDateRace, r3.getDate(),
				() -> r3.getDate() + " should equal " + newDateRace);
		assertEquals(boatsNumberRace, r3.getBoatsNumber(),
				() -> r3.getBoatsNumber() + " should equal " + boatsNumberRace);
		assertEquals(registrationFeeRace, r3.getRegistrationFee(),
				() -> r3.getRegistrationFee() + " should equal " + registrationFeeRace);
		assertEquals(endDateRace, r3.getEndDateRegistration(),
				() -> r3.getEndDateRegistration() + " should equal " + endDateRace);
		
		int newBoatsNumberRace = 5;
		s.updateRace(idRace, null, null, null, newBoatsNumberRace, null, null);
		Race r4 = s.getRaceById(idRace);
		assertEquals(newNameRace, r4.getName(),
				() -> r4.getName() + " should equal " + newNameRace);
		assertEquals(newPlaceRace, r4.getPlace(),
				() -> r4.getPlace() + " should equal " + newPlaceRace);
		assertEquals(newDateRace, r4.getDate(),
				() -> r4.getDate() + " should equal " + newDateRace);
		assertEquals(newBoatsNumberRace, r4.getBoatsNumber(),
				() -> r4.getBoatsNumber() + " should equal " + newBoatsNumberRace);
		assertEquals(registrationFeeRace, r4.getRegistrationFee(),
				() -> r4.getRegistrationFee() + " should equal " + registrationFeeRace);
		assertEquals(endDateRace, r4.getEndDateRegistration(),
				() -> r4.getEndDateRegistration() + " should equal " + endDateRace);
		
		float newRegistrationFeeRace = 22;
		s.updateRace(idRace, null, null, null, null, newRegistrationFeeRace,null);
		Race r5 = s.getRaceById(idRace);
		assertEquals(newNameRace, r5.getName(),
				() -> r5.getName() + " should equal " + newNameRace);
		assertEquals(newPlaceRace, r5.getPlace(),
				() -> r5.getPlace() + " should equal " + newPlaceRace);
		assertEquals(newDateRace, r5.getDate(),
				() -> r5.getDate() + " should equal " + newDateRace);
		assertEquals(newBoatsNumberRace, r5.getBoatsNumber(),
				() -> r5.getBoatsNumber() + " should equal " + newBoatsNumberRace);
		assertEquals(newRegistrationFeeRace, r5.getRegistrationFee(),
				() -> r5.getRegistrationFee() + " should equal " + newRegistrationFeeRace);
		assertEquals(endDateRace, r5.getEndDateRegistration(),
				() -> r5.getEndDateRegistration() + " should equal " + endDateRace);

		Date newEndDateRace = Date.valueOf("2022-03-08");
		s.updateRace(idRace, null, null, null, null, null ,newEndDateRace);
		Race r6 = s.getRaceById(idRace);
		assertEquals(newNameRace, r6.getName(),
				() -> r6.getName() + " should equal " + newNameRace);
		assertEquals(newPlaceRace, r6.getPlace(),
				() -> r6.getPlace() + " should equal " + newPlaceRace);
		assertEquals(newDateRace, r6.getDate(),
				() -> r6.getDate() + " should equal " + newDateRace);
		assertEquals(newBoatsNumberRace, r6.getBoatsNumber(),
				() -> r6.getBoatsNumber() + " should equal " + newBoatsNumberRace);
		assertEquals(newRegistrationFeeRace, r6.getRegistrationFee(),
				() -> r6.getRegistrationFee() + " should equal " + newRegistrationFeeRace);
		assertEquals(newEndDateRace, r6.getEndDateRegistration(),
				() -> r6.getEndDateRegistration() + " should equal " + newEndDateRace);
		
		
		s.updateRace(idRace, nameRace, placeRace, dateRace, boatsNumberRace, registrationFeeRace, endDateRace);
		Race oldR = s.getRaceById(idRace);
		assertEquals(nameRace, oldR.getName(),
				() -> oldR.getName() + " should equal " + nameRace);
		assertEquals(placeRace, oldR.getPlace(),
				() -> oldR.getPlace() + " should equal " + placeRace);
		assertEquals(dateRace, oldR.getDate(),
				() -> oldR.getDate() + " should equal " + dateRace);
		assertEquals(boatsNumberRace, oldR.getBoatsNumber(),
				() -> oldR.getBoatsNumber() + " should equal " + boatsNumberRace);
		assertEquals(registrationFeeRace, oldR.getRegistrationFee(),
				() -> oldR.getRegistrationFee() + " should equal " + registrationFeeRace);
		assertEquals(endDateRace, oldR.getEndDateRegistration(),
				() -> oldR.getEndDateRegistration() + " should equal " + endDateRace);
	}
	
/*  @Test
	void insertRaceTest()
	{
		SailingClub s = new SailingClub();
		String nameR = "Gara Test", placeR = "Luogo Test";
		Date dateR = Date.valueOf("2022-04-08"), endDateR = Date.valueOf("2022-04-07");
		float registrationFeeR = 1;
		int boatsNumberR = 3;
		s.insertRace(nameR, placeR, dateR, boatsNumberR, registrationFeeR, endDateR);
		Race r = s.getRaceByDate(dateR);
		assertEquals(nameR, r.getName(),
				() -> r.getName() + " should equal " + nameR);
		assertEquals(placeR, r.getPlace(),
				() -> r.getPlace() + " should equal " + placeR);
		assertEquals(dateR, r.getDate(),
				() -> r.getDate() + " should equal " + dateR);
		assertEquals(boatsNumberR, r.getBoatsNumber(),
				() -> r.getBoatsNumber() + " should equal " + boatsNumberR);
		assertEquals(registrationFeeR, r.getRegistrationFee(),
				() -> r.getRegistrationFee() + " should equal " + registrationFeeR);
		assertEquals(endDateR, r.getEndDateRegistration(),
				() -> r.getEndDateRegistration() + " should equal " + endDateR);
	}
	
	@Test
	void removeRaceTest()
	{
		SailingClub s = new SailingClub();
		Date dateR = Date.valueOf("2022-04-08"); //questo della data possiamo evitarlo se sai come si prende l'ultima riga della tabella in modo easy
		Race r = s.getRaceByDate(dateR);
		s.removeRace(r.getId());
		Race r2 = s.getRaceByDate(dateR);
		assertEquals(null,r2,
			() -> r2 + " should equal " + null);
	}*/
	
	@Test
	void initialTestRegistration()
	{
		SailingClub s = new SailingClub();
		Boat b = s.getBoatById(idBoat);
		Race r = s.getRaceById(idRace);
		
		String toString = "Id: " + idRegistration + " - Date: " + dateRegistration + " - Race: [" + r.getDate() + ", " + r.getName() + "] - Boat: " + b.getName() + " - Status Code: " + StatusCode.ACTIVE.toString();
		
		RaceRegistration rr = new RaceRegistration();
		rr.setId(idRegistration);
		rr.setDate(dateRegistration);
		rr.setRace(r);
		rr.setBoat(b);
		rr.setStatusCode(StatusCode.ACTIVE);
		
		
		assertEquals(idRegistration,rr.getId(),
				() -> rr.getId() + " should equal " + idRegistration);
		assertEquals(dateRegistration,rr.getDate(),
				() -> rr.getDate() + " should equal " + dateRegistration);
		assertEquals(r.toString(),rr.getRace().toString(),
				() -> rr.getRace().toString() + " should equal " + r.toString());
		assertEquals(b.toString(),rr.getBoat().toString(),
				() -> rr.getBoat().toString() + " should equal " + b.toString());
		assertEquals(StatusCode.ACTIVE.toString(),rr.getStatusCode().toString(),
				() ->  rr.getStatusCode().toString() + " should equal " + StatusCode.ACTIVE.toString());
		assertEquals(toString,rr.toString(),
				() ->  rr.toString() + " should equal " + toString);
		
		
		
		RaceRegistration rr2 = new RaceRegistration(idRegistration,dateRegistration,r,b,StatusCode.ACTIVE);
		assertEquals(idRegistration,rr2.getId(),
				() -> rr2.getId() + " should equal " + idRegistration);
		assertEquals(dateRegistration,rr2.getDate(),
				() -> rr2.getDate() + " should equal " + dateRegistration);
		assertEquals(r.toString(),rr2.getRace().toString(),
				() -> rr2.getRace().toString() + " should equal " + r.toString());
		assertEquals(b.toString(),rr2.getBoat().toString(),
				() -> rr2.getBoat().toString() + " should equal " + b.toString());
		assertEquals(StatusCode.ACTIVE.toString(),rr2.getStatusCode().toString(),
				() ->  rr2.getStatusCode().toString() + " should equal " + StatusCode.ACTIVE.toString());
		
	}
	
	@Test
	void getRaceRegistration()
	{
		SailingClub s = new SailingClub();
		Boat b = s.getBoatById(idBoat);
		Race r = s.getRaceById(idRace);
		RaceRegistration rr = s.getRaceRegistration(r, b);
		assertEquals(idRegistration,rr.getId(),
				() -> rr.getId() + " should equal " + idRegistration);
	}
	
/*	@Test
	void registerBoatAtRaceTest()
	{
		SailingClub s = new SailingClub();
		Date dateR = Date.valueOf("2022-04-08");
		Race r = s.getRaceByDate(dateR);
		Boat b = s.getBoatById(idBoat);
		Member m = b.getOwner();
		String paymentServiceDescription = "Transfer Receipt";
		PaymentService ps = s.getPaymentServiceByDescription(paymentServiceDescription);
		s.registerBoatAtRace(r, m, b, ps);
		
		RaceRegistration rr = s.getRaceRegistration(r, b);
		assertEquals(dateR,rr.getDate(),
				() -> rr.getDate() + " should equal " + dateR);
		assertEquals(r.toString(),rr.getRace().toString(),
				() -> rr.getRace().toString() + " should equal " + r.toString());
		assertEquals(b.toString(),rr.getBoat().toString(),
				() -> rr.getBoat().toString() + " should equal " + b.toString());
		assertEquals(StatusCode.ACTIVE.toString(),rr.getStatusCode().toString(),
				() ->  rr.getStatusCode().toString() + " should equal " + StatusCode.ACTIVE.toString());
		
	}
	
	@Test
	void removeRaceRegistration()
	{
		SailingClub s = new SailingClub();
		Date dateR = Date.valueOf("2022-04-08");
		Race r = s.getRaceByDate(dateR);
		Boat b = s.getBoatById(idBoat);
		RaceRegistration rr = s.getRaceRegistration(r, b);
		s.removeRaceRegistration(rr.getId());
		RaceRegistration rr2 = s.getRaceRegistration(r, b);
		assertEquals(null,rr2,
				() -> rr2 + " should equal " + null);
		
	}*/
	
	@Test
	void updateBoatRegistration()
	{
		int newIdBoat = 3;
		SailingClub s = new SailingClub();
		Race r = s.getRaceById(idRace);
		
		Boat newB = s.getBoatById(newIdBoat);
		s.updateBoatRegistration(idRegistration, newB);
		RaceRegistration rr = s.getRaceRegistration(r, newB);
		assertEquals(idRegistration,rr.getId(),
				() -> rr.getId() + " should equal " + idRegistration);
		assertEquals(dateRegistration,rr.getDate(),
			() -> rr.getDate() + " should equal " + dateRegistration);
		assertEquals(r.toString(),rr.getRace().toString(),
			() -> rr.getRace().toString() + " should equal " + r.toString());
		assertEquals(newB.toString(),rr.getBoat().toString(),
			() -> rr.getBoat().toString() + " should equal " + newB.toString());
		assertEquals(StatusCode.ACTIVE.toString(),rr.getStatusCode().toString(),
			() ->  rr.getStatusCode().toString() + " should equal " + StatusCode.ACTIVE.toString());
		
		Boat oldB = s.getBoatById(idBoat);
		s.updateBoatRegistration(idRegistration, oldB);
		RaceRegistration rr2 = s.getRaceRegistration(r, oldB);
		assertEquals(idRegistration,rr.getId(),
				() -> rr2.getId() + " should equal " + idRegistration);
		assertEquals(dateRegistration,rr2.getDate(),
			() -> rr2.getDate() + " should equal " + dateRegistration);
		assertEquals(r.toString(),rr2.getRace().toString(),
			() -> rr2.getRace().toString() + " should equal " + r.toString());
		assertEquals(oldB.toString(),rr2.getBoat().toString(),
			() -> rr2.getBoat().toString() + " should equal " + oldB.toString());
		assertEquals(StatusCode.ACTIVE.toString(),rr2.getStatusCode().toString(),
			() ->  rr2.getStatusCode().toString() + " should equal " + StatusCode.ACTIVE.toString());
	}
	
	
	
}
