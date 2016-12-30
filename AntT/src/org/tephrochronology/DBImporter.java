/**
 * 
 */
package org.tephrochronology;

import static org.tephrochronology.DBProperties.dBExists;
import static org.tephrochronology.DBProperties.setupProperties;

import java.io.File;
import java.util.Map;

/**
 * A collection of functions to import data to the database from a collection of
 * CSV files and images.
 * 
 * @author Mark Royer
 *
 */
public class DBImporter {

	private Map<String, String> props;

	/**
	 * Import the contents of antt-data folder to the database.
	 * 
	 * @param args
	 *            Must have args[0] == user and args[1] == pass (Not null)
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		if (args.length != 2) {
			System.err.printf("Usage: java %s USER PASS\n",
					DBImporter.class.getName());
			System.exit(-1);
		}

		Map<String, String> properties = setupProperties(args);

		String dbName = "antt";

		File dir = new File(String.format("%s-data", dbName));

		if (!dir.exists()) {
			System.out.printf(
					"The folder '%s' does not exist.  "
							+ "Please make sure that your data can be found at that location.",
					dir.getAbsolutePath());
		} else {

			if (dBExists(properties)) {
				// Drop and create from scratch for now
			} else {
				// run the setup script and populate from ant-data folder
				
				DBImporter dbi = new DBImporter(properties);

			}
		}
	}

	/**
	 * Create an importer with the given properties, which must contain standard
	 * properties for connecting to the database.
	 * 
	 * @param props
	 *            (Not null)
	 */
	public DBImporter(Map<String, String> props) {
		this.props = props;
	}

}
