package degoudengids;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* A simple data source for getting database connections.
*/
public class Datasource	{

	private static String dbserver;
	private static String database;
	private static String username;
	private static String password;

	private static Connection activeConn;

	/**
	* Initialises the data source.
	*
	* Checks if MySQL Driver is found
	* contains the database driver,
	* Fill variables for the URL, database, username, and password
	*/
	private static void init() {
		String driver = "com.mysql.jdbc.Driver";

		try {
			Class.forName(driver);
		}
		catch (ClassNotFoundException ex) {
			Logger.getLogger(Datasource.class.getName()).log(Level.SEVERE, "Driver niet present", ex);
		}

		dbserver = "localhost";
		database = "ntu";
		username = "root";
		password = "scouting";
	}

	/**
	* Gets a connection to the database.
	* @return the database connection
	*/
	public static Connection getConnection() {
		if (activeConn==null) {
			init();
			activeConn=createConnection();
		}
		return activeConn;
	}

	private static Connection createConnection() {
			String connectionString = "jdbc:mysql://" + dbserver + "/" + database + "?" +
					"user=" + username + "&password=" + password;

		try {
			return DriverManager.getConnection(connectionString);
		}
		catch (SQLException ex) {
			Logger.getLogger(Datasource.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
}