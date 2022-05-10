package test.java.it.unipr.sql;

import main.java.it.unipr.sql.*;

import org.junit.jupiter.api.BeforeAll;

/**
 * The class {@code UtilTest} defines general methods for testing.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class UtilTest {
	
	private static SailingClub club;
		
	/**
	 * Initializes the object from the class {@code SailingClub}.
	**/
	@BeforeAll
	public static void setUp() {
		club = new SailingClub();
	}
	
	/**
	 * Gets the object.
	 * 
	 * @return the object.
	**/
	public static SailingClub getClub() {
		return club;
	}
}
