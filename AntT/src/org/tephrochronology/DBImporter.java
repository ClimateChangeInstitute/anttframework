/**
 * 
 */
package org.tephrochronology;

import static org.tephrochronology.DBProperties.dBExists;
import static org.tephrochronology.DBProperties.setupProperties;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

		String dbName = DBProperties.getDB(properties);

		File dir = new File(String.format("%s-data", dbName));

		DBImporter dbi = new DBImporter(properties);

		dbi.importData(dir);

	}

	/**
	 * 
	 * @param dir
	 * @throws IOException
	 */
	public void importData(File dir) throws IOException {

		String dbName = DBProperties.getDB(props);

		if (!dir.exists()) {
			System.out.printf(
					"The folder '%s' does not exist.  "
							+ "Please make sure that your data can be found at that location.",
					dir.getAbsolutePath());
		} else {

			if (dBExists(props)) {
				// Delete the database
				dropDatabase();
			} else {
				// Run the setup script schema only
				System.out.printf("The %s DB does not exist.  Creating...",
						dbName);

				// TODO create database 
				
			}
			
			// TODO populate the datbase from ant-data folder

		}

	}

	/**
	 * Delete the database using the python script.
	 * 
	 * @throws IOException
	 */
	private void dropDatabase() throws IOException {
		// The -u option is critical to make Python flush buffers for
		// stdin and stdout!
		ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c",
				"python -u ./etc/sql/teardown.py");

		pb.redirectErrorStream(true);

		Process p = pb.start();

		BufferedReader input = new BufferedReader(
				new InputStreamReader(p.getInputStream()));

		PrintWriter out = new PrintWriter(p.getOutputStream());

		out.println("y");
		out.flush();

		String line = null;
		while ((line = input.readLine()) != null) {
			System.out.println(line);
		}

		input.close();

		try {
			p.waitFor(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new IOException(
					"Failed to finish in allotted timeout period.");
		}

		if (p.exitValue() != 0) {
			throw new IOException("Exited with nonzero value.");
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
