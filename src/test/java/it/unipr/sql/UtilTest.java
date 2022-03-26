package test.java.it.unipr.sql;

import main.java.it.unipr.sql.*;

import org.junit.jupiter.api.BeforeAll;

public class UtilTest {
	
	private static SailingClub club;
		
	@BeforeAll
	public static void setUp() {
		club = new SailingClub();
	}
	
	public static SailingClub getClub() {
		return club;
	}
}
