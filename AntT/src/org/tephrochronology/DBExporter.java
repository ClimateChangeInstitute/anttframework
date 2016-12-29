/**
 * 
 */
package org.tephrochronology;

import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_PASSWORD;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_USER;
import static org.tephrochronology.DBProperties.setupProperties;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.tephrochronology.model.Image;

/**
 * A collection of functions for saving database objects as nicely structured
 * CSV and binary files.
 * 
 * @author Mark Royer
 *
 */
public class DBExporter {

	private Map<String, String> props;

	/**
	 * Export the contents of antt database to file.
	 * 
	 * @param args
	 *            Must have args[0] == user and args[1] == pass (Not null)
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		if (args.length != 2) {
			System.err.printf("Usage: java %s USER PASS\n",
					DBExporter.class.getName());
			System.exit(-1);
		}

		DBExporter dbe = new DBExporter(setupProperties(args));

		String dbName = "antt";

		File dir = new File(String.format("%s-data", dbName));

		if (!dir.exists()) {
			dir.mkdirs();
		} else {
			System.out.printf(
					"File '%s' exists and the contents will be overwritten.  Continue? (y/N):",
					dir.getAbsolutePath());
			Scanner scan = new Scanner(System.in);
			String ans = scan.nextLine();
			scan.close();
			if (!"y".equalsIgnoreCase(ans)) {
				System.out.println("Exiting");
				return;
			} else {
				System.out.printf("Files will be written to '%s'\n",
						dir.getAbsolutePath());
			}
		}

		dbe.dumpDB(dbName, dir);
	}

	/**
	 * Create an exporter with the given properties, which must contain standard
	 * properties for connecting to the database.
	 * 
	 * @param props
	 *            (Not null)
	 */
	public DBExporter(Map<String, String> props) {
		this.props = props;
	}

	public void dumpDB(String dbName, File dataDir)
			throws ClassNotFoundException, SQLException, IOException {

		// Class.forName("org.postgresql.Driver");
		//
		//
		// Connection conn = DriverManager.getConnection(
		// "jdbc:postgresql://localhost:5432/" + dbName,
		// props.get(JDBC_USER), props.get(JDBC_PASSWORD));

		copyTableToFile(dbName, "countries", dataDir);
		copyTableToFile(dbName, "subregions", dataDir);
		copyTableToFile(dbName, "regions", dataDir);
		copyTableToFile(dbName, "volcano_types", dataDir);
		copyTableToFile(dbName, "rock_types", dataDir);
		copyTableToFile(dbName, "tectonic_settings", dataDir);

		copyTableToFile(dbName, "volcanoes", dataDir);

		copyTableToFile(dbName, "site_types", dataDir);
		copyTableToFile(dbName, "sites", dataDir);
		copyTableToFile(dbName, "instruments", dataDir);

		copyTableToFile(dbName, "refs", dataDir);

		copyTableToFile(dbName, "samples_refs", dataDir);

		copyTableToFile(dbName, "grain_sizes", dataDir);
		copyTableToFile(dbName, "grain_sizes_refs", dataDir);

		copyTableToFile(dbName, "(SELECT image_id, comments FROM images)",
				"images.csv", dataDir);

		writeImages(dbName, dataDir);

		copyTableToFile(dbName, "samples_images", dataDir);

		// TODO combine samples data for each type no need to write samples
		// table
		copyTableToFile(dbName, "samples", dataDir);

		copyTableToFile(dbName, "icecore_samples", dataDir);
		copyTableToFile(dbName, "bia_samples", dataDir);

		copyTableToFile(dbName, "core_types", dataDir);

		copyTableToFile(dbName, "lake_samples", dataDir);
		copyTableToFile(dbName, "marine_samples", dataDir);
		copyTableToFile(dbName, "outcrop_samples", dataDir);

		copyTableToFile(dbName, "method_types", dataDir);

		copyTableToFile(dbName, "mm_elements", dataDir);
		copyTableToFile(dbName, "elements", dataDir);
		copyTableToFile(dbName, "units", dataDir);
		copyTableToFile(dbName, "mm_elements_data", dataDir);

		System.out.printf("Dump completed.  Files are located at %s\n",
				dataDir.getAbsolutePath());
	}

	/**
	 * @param dbName
	 * @param dir
	 * @throws IOException
	 */
	private void writeImages(String dbName, File dir) throws IOException {
		File imageDir = new File(dir, "images");
		if (!imageDir.exists())
			imageDir.mkdir();

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory(dbName, props);
		EntityManager em = emf.createEntityManager();

		TypedQuery<Image> images = em.createQuery("SELECT i FROM Image i",
				Image.class);

		System.out.print("Writing image files");

		for (Image i : images.getResultList()) {
			try (FileOutputStream io = new FileOutputStream(
					new File(imageDir, i.getImageID()))) {
				io.write(i.getBytes());
				io.flush();
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
			System.out.print(".");
		}
		System.out.println();
	}

	/**
	 * Performs the same action as
	 * {@link #copyTableToFile(String, String, String, File)} except uses the
	 * given table name as the file name with .csv appended.
	 * 
	 * @param dbName
	 * @param table
	 * @param dir
	 * @throws IOException
	 */
	private void copyTableToFile(String dbName, String table, File dir)
			throws IOException {

		if (table.contains("("))
			throw new IOException("Table name shouldn't contain parens.  "
					+ "Use the method that specifies a file name instead.");

		copyTableToFile(dbName, table, String.format("%s.csv", table), dir);
	}

	/**
	 * @param dbName
	 *            The name of the database (Not null)
	 * @param query
	 *            Typically just a table name, but can be a select query (Not
	 *            null)
	 * @param fileName
	 *            The name of the file to save to (Not null)
	 * @param dir
	 *            The directory to write the file (Not null and must exist)
	 * @throws IOException
	 */
	private void copyTableToFile(String dbName, String query, String fileName,
			File dir) throws IOException {

		String user = props.get(JDBC_USER);
		String pass = props.get(JDBC_PASSWORD);

		String specifiedColumns = "";
		// if (columns != null && columns.length > 0) {
		// specifiedColumns = String.format("(%s)", String.join(",", columns));
		// }

		String pgCommand = String.format(
				"\\copy %s TO '%s/%s' DELIMITER ',' CSV HEADER", query,
				// specifiedColumns,
				dir.getAbsolutePath(), fileName);

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
			e.printStackTrace();
		}

		if (p.exitValue() != 0) {
			throw new IOException("Copy failed. See output for details.");
		}
	}
}
