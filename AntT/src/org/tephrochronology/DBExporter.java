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

	public void dumpDB(String dbName, File dataDir)
			throws ClassNotFoundException, SQLException, IOException {

		// Class.forName("org.postgresql.Driver");
		//
		//
		// Connection conn = DriverManager.getConnection(
		// "jdbc:postgresql://localhost:5432/" + dbName,
		// props.get(JDBC_USER), props.get(JDBC_PASSWORD));

		copyTableToFile(dbName, dataDir, "countries");
		copyTableToFile(dbName, dataDir, "subregions");
		copyTableToFile(dbName, dataDir, "regions");
		copyTableToFile(dbName, dataDir, "volcano_types");
		copyTableToFile(dbName, dataDir, "rock_types");
		copyTableToFile(dbName, dataDir, "tectonic_settings");

		copyTableToFile(dbName, dataDir, "volcanoes");

		copyTableToFile(dbName, dataDir, "site_types");
		copyTableToFile(dbName, dataDir, "sites");
		copyTableToFile(dbName, dataDir, "instruments");

		copyTableToFile(dbName, dataDir, "refs");

		copyTableToFile(dbName, dataDir, "samples_refs");

		copyTableToFile(dbName, dataDir, "grain_sizes");
		copyTableToFile(dbName, dataDir, "grain_sizes_refs");

		copyTableToFile(dbName, dataDir, "images", "image_id", "comments");

		writeImages(dbName, dataDir);

		copyTableToFile(dbName, dataDir, "samples_images");

		// TODO combine samples data for each type no need to write samples
		// table
		copyTableToFile(dbName, dataDir, "samples");

		copyTableToFile(dbName, dataDir, "icecore_samples");
		copyTableToFile(dbName, dataDir, "bia_samples");

		copyTableToFile(dbName, dataDir, "core_types");

		copyTableToFile(dbName, dataDir, "lake_samples");
		copyTableToFile(dbName, dataDir, "marine_samples");
		copyTableToFile(dbName, dataDir, "outcrop_samples");

		copyTableToFile(dbName, dataDir, "method_types");

		copyTableToFile(dbName, dataDir, "mm_elements");
		copyTableToFile(dbName, dataDir, "elements");
		copyTableToFile(dbName, dataDir, "units");
		copyTableToFile(dbName, dataDir, "mm_elements_data");

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
	 * @param dbName
	 * @param dir
	 * @param tableName
	 * @param fileName
	 * @throws IOException
	 */
	private void copyTableToFile(String dbName, File dir, String tableName,
			String... columns) throws IOException {

		String fileName = String.format("%s.csv", tableName);
		String user = props.get(JDBC_USER);
		String pass = props.get(JDBC_PASSWORD);

		String specifiedColumns = "";
		if (columns != null && columns.length > 0) {
			specifiedColumns = String.format("(%s)", String.join(",", columns));
		}

		String pgCommand = String.format(
				"\\copy %s %s TO '%s/%s' DELIMITER ',' CSV HEADER", tableName,
				specifiedColumns, dir.getAbsolutePath(), fileName);

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
