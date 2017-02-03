/**
 * 
 */
package org.tephrochronology;

import static org.tephrochronology.DBProperties.DEFAULT_PASSWORD_FILE;
import static org.tephrochronology.DBProperties.dBExists;
import static org.tephrochronology.DBProperties.setupProperties;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
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
					"The folder '%s' does not exist.\n"
							+ "Please make sure that your data can be found at that location.\n",
					dataDir.getAbsolutePath());
			System.out.println("Data has not been imported.");
			System.exit(0);
		} else {

			if (dBExists(props)) {

				System.out.printf(
						"The '%s' database exists. Append to existing data tables? (Y/n):",
						dbName);

				Scanner scan = new Scanner(System.in);

				String ans = scan.nextLine();

				boolean append = true;
				if (!"".equals(ans) && !"y".equalsIgnoreCase(ans)) {
					append = false;
				}

				System.out.printf(
						"You have chosen to "
								+ (append ? "append to the database.\n"
										: "recreate the database. IMPORTANT: The current database will be deleted!\n")
								+ "Data found in the '%s' directory will be used.  Continue? (y/N):",
						dataDir.getAbsolutePath());

				ans = scan.nextLine();

				scan.close();

				if (!"y".equalsIgnoreCase(ans)) {
					System.out.println("Exiting");
					System.exit(0);
				}

				if (!append) { // Recreate database...
					dropDatabase();
					createDatabase();
				}

			} else {
				System.out.println(
						"The database does not exist.  It will be created.");
				createDatabase();
			}

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
	 * 
	 */
	private void importFiles(File dataDir, String dbName) {

		try (Connection conn = DBProperties.getJDBCConnection(props)) {

			conn.setAutoCommit(false); // Begin transaction

			copyFileToTable(conn, dataDir, "countries");
			copyFileToTable(conn, dataDir, "subregions");
			copyFileToTable(conn, dataDir, "regions");
			copyFileToTable(conn, dataDir, "volcano_types");
			copyFileToTable(conn, dataDir, "rock_types");
			copyFileToTable(conn, dataDir, "tectonic_settings");

			copyFileToTable(conn, dataDir, "volcanoes");

			copyFileToTable(conn, dataDir, "sites");

			copyFileToTable(conn, dataDir, "areas");
			copyFileToTable(conn, dataDir, "areas_sites");

			copyFileToTable(conn, dataDir, "instruments");

			copyFileToTable(conn, dataDir, "refs");

			importImageFiles(dataDir);

			Statement st = conn.createStatement();

			// @formatter:off
			st.execute("CREATE TABLE images_tmp("
					 + "image_id TEXT PRIMARY KEY, "
					 + "comments TEXT)");
			// @formatter:on

			copyFileToTable(conn, "images_tmp",
					new File(dataDir, "images.csv"));

			// @formatter:off
			st.executeUpdate(
					  "UPDATE images "
					+ "SET comments = t.comments "
					+ "FROM images_tmp t "
					+ "WHERE images.image_id = t.image_id");
			// @formatter:on

			st.executeUpdate("DROP TABLE images_tmp");

			loadCategoryData(dataDir, "icecore_categories",
					"drilled_by TEXT, drilling_dates TEXT,core_diameter TEXT,max_core_depth TEXT,core_age_range TEXT",
					"I",
					"category_id,drilled_by,drilling_dates,core_diameter,max_core_depth,core_age_range",
					conn);

			loadSampleData(dataDir, "icecore_samples",
					"volcano_number INTEGER,topdepth_m REAL,bottomdepth_m REAL,topyear_bp REAL,bottomyear_bp REAL",
					"I",
					"sample_id,volcano_number,topdepth_m,bottomdepth_m,topyear_bp,bottomyear_bp",
					conn);

			loadCategoryData(dataDir, "bia_categories",
					"deep TEXT,thickness_cm TEXT,trend TEXT", "B",
					"category_id,deep,thickness_cm,trend", conn);

			loadSampleData(dataDir, "bia_samples", "volcano_number INTEGER ",
					"B", "sample_id, volcano_number ", conn);

			copyFileToTable(conn, dataDir, "corer_types");

			copyFileToTable(conn, dataDir, "method_types");

			loadCategoryData(dataDir, "lake_categories",
					"corer_type TEXT,age TEXT,core_length_m REAL,collection_date TEXT",
					"L",
					"category_id,corer_type,age,core_length_m,collection_date",
					conn);

			loadSampleData(dataDir, "lake_samples",
					"volcano_number INTEGER,depth_m REAL, thickness_cm REAL",
					"L", "sample_id, volcano_number, depth_m, thickness_cm",
					conn);

			loadCategoryData(dataDir, "marine_categories",
					"corer_type TEXT,age TEXT,core_length_m REAL,collection_date TEXT",
					"M",
					"category_id,corer_type,age,core_length_m,collection_date",
					conn);

			loadSampleData(dataDir, "marine_samples",
					"volcano_number INTEGER, depth_m REAL, thickness_cm REAL",
					"M", "sample_id, volcano_number, depth_m, thickness_cm",
					conn);

			loadCategoryData(dataDir, "outcrop_categories", "", "O",
					"category_id", conn);

			loadSampleData(dataDir, "outcrop_samples", "volcano_number INTEGER",
					"O", "sample_id, volcano_number", conn);

			copyFileToTable(conn, dataDir, "samples_images");

			copyFileToTable(conn, dataDir, "samples_refs");

			copyFileToTable(conn, dataDir, "grain_sizes");
			copyFileToTable(conn, dataDir, "grain_sizes_refs");

			copyFileToTable(conn, dataDir, "mm_elements");

			copyFileToTable(conn, dataDir, "chemistries");
			copyFileToTable(conn, dataDir, "units");

			copyFileToTable(conn, dataDir, "mm_elements_data");

			conn.commit();

			System.out.printf(
					"File import completed.  Data can be found in the '%s' database.\n",
					dbName);

		} catch (SQLException | IOException | ClassNotFoundException e) {
			System.err.println(e.getMessage());
			System.err.println(
					"Unable to import data.  The reason is specified above.");
		}
	}

	/**
	 * @param dataDir
	 * @param dbName
	 * @param st
	 * @throws SQLException
	 * @throws IOException
	 */
	private void loadCategoryData(File dataDir, String tableName,
			String extraColumnsWithTypes, String typeChar, String extraColumns,
			Connection conn) throws SQLException, IOException {

		Statement st = conn.createStatement();

		String tmpTable = String.format("%s_tmp", tableName);

		String str = String.format(
				"CREATE TABLE %s(category_id TEXT PRIMARY KEY,site_id TEXT ",
				tmpTable) + (extraColumnsWithTypes.isEmpty() ? "" : ", ")
				+ extraColumnsWithTypes + ")";
		st.execute(str);

		copyFileToTable(conn, tmpTable, new File(dataDir, tableName + ".csv"));

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
	private void loadSampleData(File dataDir, String tableName,
			String extraColumnsWithTypes, String typeChar, String extraColumns,
			Connection conn) throws SQLException, IOException {

		Statement st = conn.createStatement();

		String tmpTable = String.format("%s_tmp", tableName);

		st.execute(String.format(
				"CREATE TABLE %s(sample_id TEXT PRIMARY KEY,secondary_id TEXT,sampled_by TEXT,collection_date TEXT,comments TEXT,category_id TEXT,iid TEXT,",
				tmpTable) + extraColumnsWithTypes + ")");

		copyFileToTable(conn, tmpTable, new File(dataDir, tableName + ".csv"));

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

		if (!imageFolder.exists())
			return;

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
	 * Copy the data from a file named <b>table</b>.csv to the specified table
	 * in the given database. The file must be in the specified directory.
	 * 
	 * @param conn
	 *            The database connection (Not null)
	 * @param dir
	 *            The directory containing the table.csv file (Not null)
	 * @param table
	 *            The name of the table to import to (Not null)
	 * @throws IOException
	 *             Thrown if the file is not accessible
	 * @throws SQLException
	 *             Thrown if any SQL issues
	 */
	private void copyFileToTable(Connection conn, File dir, String table)
			throws IOException, SQLException {

		File csvFile = new File(dir, table + ".csv");

		copyFileToTable(conn, table, csvFile);

	}

	/**
	 * Append the data in the CSV file to the specified database table. If the
	 * given file does not exist, then nothing happens, and the import is
	 * silently ignored.
	 * 
	 * @param conn
	 *            A database connection (Not null)
	 * @param table
	 *            A database table (Not null)
	 * @param csvFile
	 *            A CSV file with columns that match the database table (Not
	 *            null)
	 * @throws IOException
	 *             Thrown if the file is not accessible
	 * @throws SQLException
	 *             Thrown if any SQL issues
	 */
	private void copyFileToTable(Connection conn, String table, File csvFile)
			throws IOException, SQLException {
		if (!csvFile.exists())
			return;

		String pgCommand = String
				.format("COPY %s FROM STDIN DELIMITER ',' CSV HEADER", table);

		CopyManager copyManager = new CopyManager((BaseConnection) conn);

		FileReader fileReader = new FileReader(csvFile);

		System.out.println(pgCommand);

		long rowsInserted = copyManager.copyIn(pgCommand, fileReader);

		System.out.printf("Inserted %d rows into the %s table.\n", rowsInserted,
				table);

		fileReader.close();

	}
}
