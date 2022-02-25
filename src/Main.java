import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	
	private static final String DBURL = "jdbc:mysql://db4free.net:3306/circolovelico?";
	private static final String ARGS = "serverTimezone=UTC";
	private static final String LOGIN = "admincircolo";
	private static final String PASSWORD = "Melograno$";

	public static void main(String[] args) {
		
		System.out.println("Progetto Finale - Circolo Velico");
		
		try (Connection conn = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
				Statement stmt = conn.createStatement()){
			System.out.println("Connessione al dabatase avvenuta con successo.");			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

	}

}
