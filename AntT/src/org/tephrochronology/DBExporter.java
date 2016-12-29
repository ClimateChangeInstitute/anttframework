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

	public void dumpDB(String dbName, File dir)
			throws ClassNotFoundException, SQLException, IOException {

		// Class.forName("org.postgresql.Driver");
		//
		//
		// Connection conn = DriverManager.getConnection(
		// "jdbc:postgresql://localhost:5432/" + dbName,
		// props.get(JDBC_USER), props.get(JDBC_PASSWORD));

		copyTableToFile(dbName, dir, "countries");
		copyTableToFile(dbName, dir, "subregions");
		copyTableToFile(dbName, dir, "regions");
		copyTableToFile(dbName, dir, "volcano_types");
		copyTableToFile(dbName, dir, "rock_types");
		copyTableToFile(dbName, dir, "tectonic_settings");

		copyTableToFile(dbName, dir, "volcanoes");

		copyTableToFile(dbName, dir, "site_types");
		copyTableToFile(dbName, dir, "sites");
		copyTableToFile(dbName, dir, "instruments");

		copyTableToFile(dbName, dir, "refs");

		copyTableToFile(dbName, dir, "samples_refs");

		copyTableToFile(dbName, dir, "grain_sizes");
		copyTableToFile(dbName, dir, "grain_sizes_refs");

		// TODO write out images to jpg files
		// copyTableToFile(dbName, dir, "images");

		File imageDir = new File(dir, "images");
		if (!imageDir.exists())
			imageDir.mkdir();

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory(dbName, props);
		EntityManager em = emf.createEntityManager();

		TypedQuery<Image> images = em.createQuery("SELECT i FROM Image i",
				Image.class);

		for (Image i : images.getResultList()) {
			try (FileOutputStream io = new FileOutputStream(
					new File(imageDir, i.getImageID()))) {
				io.write(i.getBytes());
				io.flush();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}

		copyTableToFile(dbName, dir, "samples_images");

		// TODO combine samples data for each type no need to write samples
		// table
		copyTableToFile(dbName, dir, "samples");

		copyTableToFile(dbName, dir, "icecore_samples");
		copyTableToFile(dbName, dir, "bia_samples");

		copyTableToFile(dbName, dir, "core_types");

		copyTableToFile(dbName, dir, "lake_samples");
		copyTableToFile(dbName, dir, "marine_samples");
		copyTableToFile(dbName, dir, "outcrop_samples");

		copyTableToFile(dbName, dir, "method_types");

		copyTableToFile(dbName, dir, "mm_elements");
		copyTableToFile(dbName, dir, "elements");
		copyTableToFile(dbName, dir, "units");
		copyTableToFile(dbName, dir, "mm_elements_data");

	}

	/**
	 * @param dbName
	 * @param dir
	 * @param tableName
	 * @param fileName
	 * @throws IOException
	 */
	private void copyTableToFile(String dbName, File dir, String tableName)
			throws IOException {

		String fileName = String.format("%s.csv", tableName);
		String user = props.get(JDBC_USER);
		String pass = props.get(JDBC_PASSWORD);

		String pgCommand = String.format(
				"\\copy %s TO '%s/%s' DELIMITER ',' CSV HEADER", tableName,
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
	}
}
