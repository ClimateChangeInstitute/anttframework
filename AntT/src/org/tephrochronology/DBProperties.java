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
	 */
	public static Connection getJDBCConnection(Map<String, String> props) {

		Connection conn = null;

		try {
			Class.forName("org.postgresql.Driver");

			conn = DriverManager.getConnection(props.get(JDBC_URL),
					props.get(JDBC_USER), props.get(JDBC_PASSWORD));

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return conn;
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

}
