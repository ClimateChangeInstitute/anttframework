/**
 * 
 */
package org.tephrochronology;

import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_PASSWORD;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_USER;
import static org.tephrochronology.DBProperties.DEFAULT_PASSWORD_FILE;
import static org.tephrochronology.DBProperties.setupProperties;
import static org.tephrochronology.model.Image.QUERY_IMAGES_BY_ID;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
			args = DBProperties.findUserPassword(DEFAULT_PASSWORD_FILE);
			if (args.length != 2) {
				System.err.printf("Usage: java %s USER PASS\n",
						DBExporter.class.getName());
				System.err.printf("Optionally, create the %s file.\n",
						DEFAULT_PASSWORD_FILE.getPath());
				System.exit(-1);
			}
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

	/**
	 * Save all of the tables in the specified database to dataDir as CSV files.
	 * Images are exported as JPEG files in an images subfolder.
	 * 
	 * @param dbName
	 *            The database name (Not null)
	 * @param dataDir
	 *            The folder to save the data (Not null and must exist)
	 * @throws IOException
	 */
	public void dumpDB(String dbName, File dataDir) throws IOException {

		copyTableToFile(dbName, "countries", dataDir);
		copyTableToFile(dbName, "subregions", dataDir);
		copyTableToFile(dbName, "regions", dataDir);
		copyTableToFile(dbName, "volcano_types", dataDir);
		copyTableToFile(dbName, "rock_types", dataDir);
		copyTableToFile(dbName, "tectonic_settings", dataDir);

		copyTableToFile(dbName, "volcanoes", dataDir);

		copyTableToFile(dbName, "sites", dataDir);

		copyTableToFile(dbName, "areas", dataDir);
		copyTableToFile(dbName, "areas_sites", dataDir);

		copyTableToFile(dbName, "instruments", dataDir);

		copyTableToFile(dbName, "refs", dataDir);

		copyTableToFile(dbName, "samples_refs", dataDir);

		copyTableToFile(dbName, "grain_sizes", dataDir);
		copyTableToFile(dbName, "grain_sizes_refs", dataDir);

		copyTableToFile(dbName, "(SELECT image_id, comments FROM images)",
				"images.csv", dataDir);

		writeImages(dbName, dataDir);

		copyTableToFile(dbName, "samples_images", dataDir);

// @formatter:off
// WRITE OUT CATEGORIES !!!!!!!
		copyTableToFile(dbName, 
  "(SELECT c.category_id, site_id, "
	    + "drilled_by, drilling_dates, core_diameter, max_core_depth, core_age_range "
 + "FROM categories c, icecore_categories i "
 + "WHERE c.category_id = i.category_id "
 + "ORDER BY c.category_id)",
				"icecore_categories.csv", dataDir);
				
		copyTableToFile(dbName, 
  "(SELECT c.category_id, site_id, "
	    + "dip, thickness_cm, trend "
 + "FROM categories c, bia_categories b "
 + "WHERE c.category_id = b.category_id "
 + "ORDER BY c.category_id)",
				"bia_categories.csv", dataDir);

		copyTableToFile(dbName,
  "(SELECT c.category_id, site_id, "
	    + "corer_type, age, core_length_m, collection_date "
 + "FROM categories c, lake_categories l "
 + "WHERE c.category_id = l.category_id "
 + "ORDER BY c.category_id)",
				"lake_categories.csv", dataDir);
		
		copyTableToFile(dbName,
  "(SELECT c.category_id, site_id, "
	    + "corer_type, age, core_length_m, collection_date "
 + "FROM categories c, marine_categories m "
 + "WHERE c.category_id = m.category_id "
 + "ORDER BY c.category_id)",
				"marine_categories.csv", dataDir);
		
		copyTableToFile(dbName,
  "(SELECT c.category_id, site_id "
	    + " " // No additional properties right now
 + "FROM categories c, outcrop_categories o "
 + "WHERE c.category_id = o.category_id "
 + "ORDER BY c.category_id)",
				"outcrop_categories.csv", dataDir);

		
// WRITE OUT SAMPLES !!!!!!!!!!		
		copyTableToFile(dbName, 
  "(SELECT s.sample_id, secondary_id, sampled_by, collection_date, comments, category_id, iid,"
	    + "volcano_number, topdepth_m,bottomdepth_m,topyear_bp,bottomyear_bp "
 + "FROM samples s, icecore_samples i "
 + "WHERE s.sample_id = i.sample_id "
 + "ORDER BY s.sample_id)",
				"icecore_samples.csv", dataDir);
				
		copyTableToFile(dbName, 
 "(SELECT s.sample_id, secondary_id, sampled_by, collection_date, comments, category_id, iid, "
	   + "volcano_number "
+ "FROM samples s, bia_samples b "
+ "WHERE s.sample_id = b.sample_id "
+ "ORDER BY s.sample_id)",				
				"bia_samples.csv", dataDir);

		copyTableToFile(dbName,
  "(SELECT s.sample_id, secondary_id, sampled_by, collection_date, comments, category_id, iid,"
	    + "volcano_number,depth_m,thickness_cm "
 + "FROM samples s, lake_samples l "
 + "WHERE s.sample_id = l.sample_id	"
 + "ORDER BY s.sample_id)",
				"lake_samples.csv", dataDir);
		
		copyTableToFile(dbName,
 "(SELECT s.sample_id, secondary_id, sampled_by, collection_date, comments, category_id, iid,"
      + " volcano_number,depth_m,thickness_cm "
+ "FROM samples s, marine_samples m "
+ "WHERE s.sample_id = m.sample_id "
+ "ORDER BY s.sample_id)",
				"marine_samples.csv", dataDir);
		
		copyTableToFile(dbName,
 "(SELECT s.sample_id, secondary_id, sampled_by, collection_date, comments, category_id, iid,"
      + " volcano_number "
+ "FROM samples s, outcrop_samples o "
+ "WHERE s.sample_id = o.sample_id "
+ "ORDER BY s.sample_id)",				
				"outcrop_samples.csv", dataDir);

// @formatter:on

		copyTableToFile(dbName, "corer_types", dataDir);

		copyTableToFile(dbName, "method_types", dataDir);

		copyTableToFile(dbName, "mm_elements", dataDir);
		copyTableToFile(dbName, "chemistries", dataDir);
		copyTableToFile(dbName, "units", dataDir);
		copyTableToFile(dbName, "mm_elements_data", dataDir);

		System.out.printf("Dump completed.  Files are located at %s\n",
				dataDir.getAbsolutePath());
	}

	/**
	 * Write all of the images in the specified database to the given directory.
	 * 
	 * @param dbName
	 *            Database to get images from (Not null)
	 * @param dir
	 *            The directory to write the images to (Not null and will be
	 *            created if does not exist)
	 * @throws IOException
	 *             Thrown if there is an issue writing to the file
	 */
	private void writeImages(String dbName, File dir) throws IOException {
		File imageDir = new File(dir, "images");
		if (!imageDir.exists())
			imageDir.mkdir();

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory(dbName, props);
		EntityManager em = emf.createEntityManager();

		TypedQuery<Image> images = em.createNamedQuery(QUERY_IMAGES_BY_ID,
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
	 *            The name of the database (Not null)
	 * @param table
	 *            Table to save as CSV (Not null)
	 * @param dir
	 *            The directory to write the file (Not null and must exist)
	 * @throws IOException
	 *             Thrown if unable to write the file
	 */
	private void copyTableToFile(String dbName, String table, File dir)
			throws IOException {

		if (table.contains("("))
			throw new IOException("Table name shouldn't contain parens.  "
					+ "Use the method that specifies a file name instead.");

		copyTableToFile(dbName, table, String.format("%s.csv", table), dir);
	}

	/**
	 * Save the result of the given query to fileName in the given directory.
	 * 
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

		String pgCommand = String.format(
				"\\copy %s TO '%s/%s' DELIMITER ',' CSV HEADER", query,
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
			throw new IOException(
					"Copy failed to finish in allotted timeout period.");
		}

		if (p.exitValue() != 0) {
			throw new IOException(
					"Copy exited with nonzero value.  It's likely the write did not succeed.");
		}
	}
}
