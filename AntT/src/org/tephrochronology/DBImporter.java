/**
 * 
 */
package org.tephrochronology;

import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_PASSWORD;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_USER;
import static org.tephrochronology.DBProperties.dBExists;
import static org.tephrochronology.DBProperties.setupProperties;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
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
	 * @param dataDir
	 * @throws IOException
	 */
	public void importData(File dataDir) throws IOException {

		String dbName = DBProperties.getDB(props);

		if (!dataDir.exists()) {
			System.out.printf(
					"The folder '%s' does not exist.  "
							+ "Please make sure that your data can be found at that location.",
					dataDir.getAbsolutePath());
		} else {

			if (dBExists(props)) {
				
				System.out.printf(
						"The '%s' database exists and the contents will be overwritten.\n"
						+ "Data found in the '%s' directory will be used.  Continue? (y/N):",
						dbName, dataDir.getAbsolutePath());
				Scanner scan = new Scanner(System.in);
				String ans = scan.nextLine();
				scan.close();
				if (!"y".equalsIgnoreCase(ans)) {
					System.out.println("Exiting");
					System.exit(-1);
				} else {
					dropDatabase();	
				}
				
			}

			createDatabase();
			importFiles(dataDir, dbName);

		}

	}

	/**
	 * Import the data from the files found in the dataDir folder.
	 * 
	 * @param dataDir
	 *            The folder to import data from (Not null and must exist)
	 * @param dbName
	 *            The name of the database to import to (Not null)
	 * @throws IOException
	 *             Thrown if unable to read from files in the specified folder
	 */
	private void importFiles(File dataDir, String dbName) throws IOException {

		copyFileToTable(dbName, "countries", dataDir);
		copyFileToTable(dbName, "subregions", dataDir);
		copyFileToTable(dbName, "regions", dataDir);
		copyFileToTable(dbName, "volcano_types", dataDir);
		copyFileToTable(dbName, "rock_types", dataDir);
		copyFileToTable(dbName, "tectonic_settings", dataDir);

		copyFileToTable(dbName, "volcanoes", dataDir);

		copyFileToTable(dbName, "site_types", dataDir);
		copyFileToTable(dbName, "sites", dataDir);
		copyFileToTable(dbName, "instruments", dataDir);

		copyFileToTable(dbName, "refs", dataDir);

//		 copyFileToTable(dbName, "grain_sizes", dataDir);
//		 copyFileToTable(dbName, "grain_sizes_refs", dataDir);

		// TODO Import images properly
		// copyTableToFile(dbName, "(SELECT image_id, comments FROM images)",
		// "images.csv", dataDir);
		//
		// writeImages(dbName, dataDir);

		// copyFileToTable(dbName, "samples_images", dataDir);

		// TODO This will be handled by samples subtypes
		// copyFileToTable(dbName, "samples_refs", dataDir);

		// TODO Make sure each sample type is loaded properly

// @formatter:off
//		copyTableToFile(dbName, 
//  "(SELECT s.sample_id, long_name, sampled_by, collection_date, comments, site_id, iid,"
//		+ "drilled_by, drilling_date,core_diameter,max_core_depth,core_age,core_age_range,topdepth_m,bottomdepth_m,topyear_bp,bottomyear_bp "
// + "FROM samples s, icecore_samples i "
// + "WHERE s.sample_id = i.sample_id "
// + "ORDER BY s.sample_id)",
//				"icecore_samples.csv", dataDir);
//				
//		copyTableToFile(dbName, 
// "(SELECT s.sample_id, long_name, sampled_by, collection_date, comments, site_id, iid, "
//   + "volcano_number, deep, sample_description,sample_media,unit_name,thickness_cm,trend "
//+ "FROM samples s, bia_samples b "
//+ "WHERE s.sample_id = b.sample_id "
//+ "ORDER BY s.sample_id)",				
//				"bia_samples.csv", dataDir);
//
//		copyTableToFile(dbName,
//  "(SELECT s.sample_id, long_name, sampled_by, collection_date, comments, site_id, iid,"
//		+ "volcano_number,corer_type,age,core_length_m,sampling_date,depth_m,top_m,thickness_cm "
// + "FROM samples s, lake_samples l "
// + "WHERE s.sample_id = l.sample_id	"
// + "ORDER BY s.sample_id)",
//				"lake_samples.csv", dataDir);
//		
//		copyTableToFile(dbName,
// "(SELECT s.sample_id, long_name, sampled_by, collection_date, comments, site_id, iid,"
//     + " volcano_number,corer_type,age,core_length_m,sampling_date,depth_m,top_m,thickness_cm "
//+ "FROM samples s, marine_samples m "
//+ "WHERE s.sample_id = m.sample_id "
//+ "ORDER BY s.sample_id)",
//				"marine_samples.csv", dataDir);
//		
//		copyTableToFile(dbName,
// "(SELECT s.sample_id, long_name, sampled_by, collection_date, comments, site_id, iid,"
//     + " volcano_number "
//+ "FROM samples s, outcrop_samples o "
//+ "WHERE s.sample_id = o.sample_id "
//+ "ORDER BY s.sample_id)",				
//				"outcrop_samples.csv", dataDir);

// @formatter:on

		copyFileToTable(dbName, "corer_types", dataDir);

		copyFileToTable(dbName, "method_types", dataDir);

		// TODO Must be written after samples are loaded
		// copyFileToTable(dbName, "mm_elements", dataDir);

		copyFileToTable(dbName, "elements", dataDir);
		copyFileToTable(dbName, "units", dataDir);

		// TODO link mm_elements_data after samples
		// copyFileToTable(dbName, "mm_elements_data", dataDir);

		System.out.printf(
				"File import completed.  Data can be found in the '%s' database.\n",
				dbName);
	}

	/**
	 * Create the database using the script etc/sql/setup.py schema.
	 * 
	 * @throws IOException
	 *             Thrown if unable to read or write any files
	 */
	private void createDatabase() throws IOException {
		// The -u option is critical to make Python flush buffers for
		// STDIN and STDOUT!
		ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c",
				"cd etc/sql && python -u ./setup.py schema");

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
	 * Delete the database using the Python script etc/sql/teardown.py.
	 * 
	 * @throws IOException
	 */
	private void dropDatabase() throws IOException {
		// The -u option is critical to make Python flush buffers for
		// STDIN and STDOUT!
		ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c",
				"cd etc/sql && python -u ./teardown.py");

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

	/**
	 * Copy the data from a file named table.csv to the specified table in the
	 * given database. The file must be in the specified directory.
	 * 
	 * @param dbName
	 *            The database (Not null)
	 * @param table
	 *            The name of the table to import to (Not null)
	 * @param dir
	 *            The directory containing the table.csv file (Not null)
	 * @throws IOException
	 *             Thrown if the file is not accessible
	 */
	private void copyFileToTable(String dbName, String table, File dir)
			throws IOException {

		String user = props.get(JDBC_USER);
		String pass = props.get(JDBC_PASSWORD);

		File csvFile = new File(dir, table + ".csv");

		String pgCommand = String.format(
				"\\copy %s FROM '%s' DELIMITER ',' CSV HEADER", table,
				csvFile.getAbsolutePath());

		String bashCommand = String.format(
				"PGPASSWORD=%s psql -d %s -h localhost -U %s -c \"%s\"", pass,
				dbName, user, pgCommand);

		System.out.println(bashCommand.replace(pass, "??????"));

		ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", bashCommand);

		pb.redirectErrorStream(true);
		Process p = pb.start();
		BufferedReader input = new BufferedReader(
				new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = input.readLine()) != null) {
			System.out.println(line);
		}
		input.close();

		try {
			p.waitFor(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new IOException(
					"Copy failed to finish in allotted timeout period.");
		}

		if (p.exitValue() != 0) {
			throw new IOException(
					"Copy exited with nonzero value.  It's likely the write did not succeed.");
		}

	}
}
