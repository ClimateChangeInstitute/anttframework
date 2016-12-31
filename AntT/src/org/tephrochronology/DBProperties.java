package org.tephrochronology;

import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_DRIVER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_PASSWORD;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_URL;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_USER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_LEVEL;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_SESSION;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_THREAD;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_TIMESTAMP;
import static org.eclipse.persistence.config.PersistenceUnitProperties.TARGET_SERVER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.TRANSACTION_TYPE;
import static org.eclipse.persistence.logging.SessionLog.OFF_LABEL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.spi.PersistenceUnitTransactionType;

import org.eclipse.persistence.config.TargetServer;

/**
 * Database connection and setup.
 * 
 * @author Mark Royer
 *
 */
public class DBProperties {

	/**
	 * Returns a JDBC connection to the database. The JDBC_URL, JDBC_USER, and
	 * JDBC_PASSWORD must be specified in the given properties.
	 * 
	 * @param props
	 *            Database connection properties (Not null)
	 * @return A connection to the database or null if unable to connect
	 * @throws ClassNotFoundException
	 *             Thrown if unable to load the postgres driver
	 * @throws SQLException
	 *             Thrown if unable to connect to the database
	 */
	public static Connection getJDBCConnection(Map<String, String> props)
			throws ClassNotFoundException, SQLException {

		// Need to make sure the postgres driver has been loaded
		Class.forName("org.postgresql.Driver");

		return DriverManager.getConnection(props.get(JDBC_URL),
				props.get(JDBC_USER), props.get(JDBC_PASSWORD));
	}

	/**
	 * Create common database properties from the given arguments. The first
	 * argument must be the database user, and the second must be the database
	 * password.
	 * 
	 * @param args
	 *            (Not null)
	 * @return Common database properties
	 */
	public static Map<String, String> setupProperties(String[] args) {

		Map<String, String> properties = new HashMap<>();

		// Ensure RESOURCE_LOCAL transactions is used.
		properties.put(TRANSACTION_TYPE,
				PersistenceUnitTransactionType.RESOURCE_LOCAL.name());

		// Configure the internal EclipseLink connection pool
		properties.put(JDBC_DRIVER, "org.postgresql.Driver");
		properties.put(JDBC_URL, "jdbc:postgresql://localhost:5432/antt");
		properties.put(JDBC_USER, args[0]);
		properties.put(JDBC_PASSWORD, args[1]);

		// Configure logging. FINE ensures all SQL is shown
		properties.put(LOGGING_LEVEL, OFF_LABEL);
		properties.put(LOGGING_TIMESTAMP, "false");
		properties.put(LOGGING_THREAD, "false");
		properties.put(LOGGING_SESSION, "false");

		// Ensure that no server-platform is configured
		properties.put(TARGET_SERVER, TargetServer.None);

		return properties;

	}

	/**
	 * This function will return true only if the database with the given
	 * properties can be connected to with the supplied user and password.
	 * 
	 * @param properties
	 *            Database connection properties (Not null)
	 * @return true IFF the database for the provided properties exists and was
	 *         able to be connected to with the given user and password
	 */
	public static boolean dBExists(Map<String, String> properties) {

		boolean result = false;

		try (Connection conn = DBProperties.getJDBCConnection(properties)) {

			System.out.println(conn);

			PreparedStatement st = conn.prepareStatement(
					"SELECT EXISTS(SELECT 1 FROM pg_database WHERE datname=?)");
			st.setString(1, getDB(properties));
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result = rs.getBoolean(1);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// Ignore the result will be false
		}

		return result;
	}

	/**
	 * Return the database specified by the JDBC_URL. If no JDBC_URL is
	 * specified, null is returned.
	 * 
	 * @param properties
	 *            JDBC connection properties (Not null)
	 * @return The database name or null if not found
	 */
	public static String getDB(Map<String, String> properties) {
		// Looks like jdbc:postgresql://localhost:5432/antt
		String url = properties.get(JDBC_URL);
		if (url == null)
			return null;

		String[] parts = url.split("/");

		if (parts.length == 4)
			return parts[3];
		else
			return null;
	}

}
