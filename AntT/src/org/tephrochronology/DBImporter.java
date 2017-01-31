/**
 * 
 */
package org.tephrochronology;

import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_PASSWORD;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_USER;
import static org.tephrochronology.DBProperties.DEFAULT_PASSWORD_FILE;
import static org.tephrochronology.DBProperties.dBExists;
import static org.tephrochronology.DBProperties.setupProperties;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.tephrochronology.model.Image;

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
			args = DBProperties.findUserPassword(DEFAULT_PASSWORD_FILE);
			if (args.length != 2) {
				System.err.printf("Usage: java %s USER PASS\n",
						DBImporter.class.getName());
				System.err.printf("Optionally, create the %s file.\n",
						DEFAULT_PASSWORD_FILE.getPath());
				System.exit(-1);
			}
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
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void importData(File dataDir)
			throws IOException, ClassNotFoundException, SQLException {

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
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private void importFiles(File dataDir, String dbName)
			throws IOException, SQLException, ClassNotFoundException {

		copyFileToTable(dbName, "countries", dataDir);
		copyFileToTable(dbName, "subregions", dataDir);
		copyFileToTable(dbName, "regions", dataDir);
		copyFileToTable(dbName, "volcano_types", dataDir);
		copyFileToTable(dbName, "rock_types", dataDir);
		copyFileToTable(dbName, "tectonic_settings", dataDir);

		copyFileToTable(dbName, "volcanoes", dataDir);

		copyFileToTable(dbName, "sites", dataDir);

		copyFileToTable(dbName, "areas", dataDir);
		copyFileToTable(dbName, "areas_sites", dataDir);

		copyFileToTable(dbName, "instruments", dataDir);

		copyFileToTable(dbName, "refs", dataDir);

		importImageFiles(dataDir);

		Connection conn = DBProperties.getJDBCConnection(props);

		Statement st = conn.createStatement();

		// @formatter:off
		st.execute("CREATE TABLE images_tmp("
				 + "image_id TEXT PRIMARY KEY, "
				 + "comments TEXT)");
		// @formatter:on

		copyFileToTable(dbName, "images_tmp", new File(dataDir, "images.csv"),
				dataDir);

		// @formatter:off
		st.executeUpdate(
				  "UPDATE images "
				+ "SET comments = t.comments "
				+ "FROM images_tmp t "
				+ "WHERE images.image_id = t.image_id");
		// @formatter:on

		st.executeUpdate("DROP TABLE images_tmp");

		loadCategoryData(dataDir, dbName, "icecore_categories",
				"drilled_by TEXT, drilling_dates TEXT,core_diameter TEXT,max_core_depth TEXT,core_age_range TEXT",
				"I",
				"category_id,drilled_by,drilling_dates,core_diameter,max_core_depth,core_age_range",
				conn);

		loadSampleData(dataDir, dbName, "icecore_samples",
				"volcano_number INTEGER,topdepth_m REAL,bottomdepth_m REAL,topyear_bp REAL,bottomyear_bp REAL",
				"I",
				"sample_id,volcano_number,topdepth_m,bottomdepth_m,topyear_bp,bottomyear_bp",
				conn);

		loadCategoryData(dataDir, dbName, "bia_categories",
				"deep TEXT,thickness_cm TEXT,trend TEXT", "B",
				"category_id,deep,thickness_cm,trend", conn);

		loadSampleData(dataDir, dbName, "bia_samples",
				"volcano_number INTEGER ", "B", "sample_id, volcano_number ",
				conn);

		copyFileToTable(dbName, "corer_types", dataDir);

		copyFileToTable(dbName, "method_types", dataDir);

		loadCategoryData(dataDir, dbName, "lake_categories",
				"corer_type TEXT,age TEXT,core_length_m REAL,collection_date DATE",
				"L", "category_id,corer_type,age,core_length_m,collection_date",
				conn);

		loadSampleData(dataDir, dbName, "lake_samples",
				"volcano_number INTEGER,depth_m REAL, thickness_cm REAL", "L",
				"sample_id, volcano_number, depth_m, thickness_cm", conn);

		loadCategoryData(dataDir, dbName, "marine_categories",
				"corer_type TEXT,age TEXT,core_length_m REAL,collection_date DATE",
				"M", "category_id,corer_type,age,core_length_m,collection_date",
				conn);

		loadSampleData(dataDir, dbName, "marine_samples",
				"volcano_number INTEGER, depth_m REAL, thickness_cm REAL", "M",
				"sample_id, volcano_number, depth_m, thickness_cm", conn);

		loadCategoryData(dataDir, dbName, "outcrop_categories", "", "O",
				"category_id", conn);

		loadSampleData(dataDir, dbName, "outcrop_samples",
				"volcano_number INTEGER", "O", "sample_id, volcano_number",
				conn);

		copyFileToTable(dbName, "samples_images", dataDir);

		copyFileToTable(dbName, "samples_refs", dataDir);

		copyFileToTable(dbName, "grain_sizes", dataDir);
		copyFileToTable(dbName, "grain_sizes_refs", dataDir);

		copyFileToTable(dbName, "mm_elements", dataDir);

		copyFileToTable(dbName, "chemistries", dataDir);
		copyFileToTable(dbName, "units", dataDir);

		copyFileToTable(dbName, "mm_elements_data", dataDir);

		System.out.printf(
				"File import completed.  Data can be found in the '%s' database.\n",
				dbName);
	}

	/**
	 * @param dataDir
	 * @param dbName
	 * @param st
	 * @throws SQLException
	 * @throws IOException
	 */
	private void loadCategoryData(File dataDir, String dbName, String tableName,
			String extraColumnsWithTypes, String typeChar, String extraColumns,
			Connection conn) throws SQLException, IOException {

		Statement st = conn.createStatement();

		String tmpTable = String.format("%s_tmp", tableName);

		String str = String.format(
				"CREATE TABLE %s(category_id TEXT PRIMARY KEY,site_id TEXT ",
				tmpTable) + (extraColumnsWithTypes.isEmpty() ? "" : ", ")
				+ extraColumnsWithTypes + ")";
		System.out.println(str);
		st.execute(str);

		copyFileToTable(dbName, tmpTable, new File(dataDir, tableName + ".csv"),
				dataDir);

		st.executeUpdate(String.format(
				"INSERT INTO categories (category_id, site_id, category_type) "
						+ "SELECT category_id, site_id, '%s' AS sample_type FROM %s",
				typeChar, tmpTable));

		st.executeUpdate("INSERT INTO " + tableName + " (" + extraColumns + ") "
				+ "SELECT " + extraColumns + "  FROM " + tmpTable);

		st.execute("DROP TABLE " + tmpTable);
	}

	/**
	 * @param dataDir
	 * @param dbName
	 * @param st
	 * @throws SQLException
	 * @throws IOException
	 */
	private void loadSampleData(File dataDir, String dbName, String tableName,
			String extraColumnsWithTypes, String typeChar, String extraColumns,
			Connection conn) throws SQLException, IOException {

		Statement st = conn.createStatement();

		String tmpTable = String.format("%s_tmp", tableName);

		st.execute(String.format(
				"CREATE TABLE %s(sample_id TEXT PRIMARY KEY,secondary_id TEXT,sampled_by TEXT,collection_date DATE,comments TEXT,category_id TEXT,iid TEXT,",
				tmpTable) + extraColumnsWithTypes + ")");

		copyFileToTable(dbName, tmpTable, new File(dataDir, tableName + ".csv"),
				dataDir);

		st.executeUpdate(String.format(
				"INSERT INTO samples (sample_id, secondary_id, sampled_by, collection_date, comments, category_id, iid, sample_type) "
						+ "SELECT sample_id, secondary_id, sampled_by, collection_date, comments, category_id, iid, '%s' AS sample_type FROM %s",
				typeChar, tmpTable));

		st.executeUpdate("INSERT INTO " + tableName + " (" + extraColumns + ") "
				+ "SELECT " + extraColumns + "  FROM " + tmpTable);

		st.execute("DROP TABLE " + tmpTable);
	}

	/**
	 * @param dataDir
	 * @throws IOException
	 */
	private void importImageFiles(File dataDir) throws IOException {
		File imageFolder = new File(dataDir, "images");

		List<File> images = getImages(imageFolder);

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("antt", props);
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		System.out.printf("Processing %d images (This may take a while).",
				images.size());
		for (File f : images) {
			BufferedImage fullSizeImage = ImageIO.read(f);
			BufferedImage thumbImage = Images.scaleAndCrop(fullSizeImage,
					Images.DEFAULT_THUMB_SIZE);

			Image image = new Image(f.getName(), "",
					Images.asOutputStream(fullSizeImage).toByteArray(),
					Images.asOutputStream(thumbImage).toByteArray(), null);

			em.persist(image);
			System.out.print(".");

		}

		em.getTransaction().commit();
		System.out.println();
	}

	/**
	 * Recursively find image files in the specified folder and return them.
	 * 
	 * @param imageFolder
	 *            The starting folder (Not null)
	 * @return All of the images found (including subfolders) found in the given
	 *         folder
	 */
	private List<File> getImages(File imageFolder) {

		final String[] accepted = { "jpg", "jpeg" };

		List<File> matchingFiles = new ArrayList<>();

		if (imageFolder.isFile()) {
			String fileName = imageFolder.getName();
			if (!fileName.isEmpty() && Arrays.stream(accepted)
					.anyMatch(e -> fileName.toLowerCase().endsWith(e))) {
				matchingFiles.add(imageFolder); // It's an image File!
			}
		} else { // Add the files from subdirectories...
			Arrays.stream(imageFolder.listFiles())
					.forEach(f -> matchingFiles.addAll(getImages(f)));
		}

		return matchingFiles;
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

		File csvFile = new File(dir, table + ".csv");

		copyFileToTable(dbName, table, csvFile, dir);

	}

	/**
	 * @param dbName
	 * @param table
	 * @param csvFile
	 * @throws IOException
	 */
	private void copyFileToTable(String dbName, String table, File csvFile,
			File parentDir) throws IOException {

		String user = props.get(JDBC_USER);
		String pass = props.get(JDBC_PASSWORD);

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
