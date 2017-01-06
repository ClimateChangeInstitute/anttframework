/**
 * 
 */
package org.tephrochronology;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListReader;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

/**
 * Example parser to extract MMElement and MMElementData mixed output.
 * 
 * @author Mark Royer
 *
 */
public class CSVMMElementParser {

	/**
	 * Represents the headers to be read and what they will be written out as.  The 
	 * 
	 * @author Mark Royer
	 *
	 */
	static class Headers {

		String[] select;

		String[] rename;

		Map<String, String> toRenameMap;

		Map<String, String> toSelectMap;

		public Headers(String[] select, String[] rename) {
			this.select = select;
			this.rename = rename;
			toRenameMap = new HashMap<>();
			for (int i = 0; i < select.length; i++) {
				toRenameMap.put(select[i], rename[i]);
			}
			toSelectMap = new HashMap<>();
			for (int i = 0; i < select.length; i++) {
				toSelectMap.put(rename[i], select[i]);
			}
		}

		public String getRename(String header) {
			return toRenameMap.get(header);
		}

		public String getSelect(String header) {
			return toSelectMap.get(header);
		}

		public String[] getSelect() {
			return select;
		}

		public String[] getRename() {
			return rename;
		}

		public int length() {
			return select.length;
		}

	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		File inputFile = new File(args[0]);
		// Two line file
		// First line: columns to select
		// Second line: rename of columns
		File inputSelectFile = new File(args[1]);
		// Multiple line file containing a row for each selection
		// longsample_id, element, unit , val, std, me
		// The element and unit will be used as a literal
		File inputMMElementDataSelectFile = new File(args[2]);

		File outputMMElementFile = new File(args[3]);

		File outputMMElementDataFile = new File(args[4]);

		Headers headers = getSelectAndRenameHeaders(inputSelectFile);

		// These are from the database specification for mm_elements
		String[] expectedHeaders = new String[] { "longsample_id", "sample_id",
				"comments", "method_type", "iid", "date_measured",
				"measured_by", "original_total", "calculated_total",
				"instrument_settings", "h2o_plus", "h2o_minus", "loi" };

		List<Map<String, String>> rows = new CSVMMElementParser()
				.partialReadCSV(inputFile, headers.getSelect());

		ICsvListWriter mapWriter = new CsvListWriter(
				new FileWriter(outputMMElementFile),
				CsvPreference.STANDARD_PREFERENCE);

		mapWriter.writeHeader(expectedHeaders);

		for (Map<String, String> r : rows) {

			Object[] arr = Arrays.stream(expectedHeaders)
					.map(e -> r.get(headers.getSelect(e))).toArray();
			mapWriter.write(arr);
			System.out.println(Arrays.toString(arr));
		}
		mapWriter.flush();
		mapWriter.close();

		// Required columns for mm_elements_data
		//
		// longsample_id, element, unit , val, std, me
		//
		//
		// Extended Sample ID, "p2o5" , "%", P2O5 ,std_P2O5,me_P2O5

		writeMMElementDataFile(inputFile, inputMMElementDataSelectFile,
				outputMMElementDataFile);

	}

	/**
	 * @param inputFile
	 * @param inputMMElementDataSelectFile
	 * @param outputMMElementDataFile
	 * @throws Exception
	 * @throws IOException
	 */
	private static void writeMMElementDataFile(File inputFile,
			File inputMMElementDataSelectFile, File outputMMElementDataFile)
			throws Exception, IOException {

		Scanner in = new Scanner(inputMMElementDataSelectFile);
		String nextLine = in.nextLine();
		while (nextLine.startsWith("#") && in.hasNextLine()) {
			nextLine = in.nextLine();
		}

		ICsvListWriter dataWriter = new CsvListWriter(
				new FileWriter(outputMMElementDataFile),
				CsvPreference.STANDARD_PREFERENCE);

		dataWriter.writeHeader(new String[] { "longsample_id", "element",
				"unit", "val", "std", "me" });

		while (nextLine != null && !nextLine.isEmpty()) {

			String[] mmElementDataHeaders = trim(nextLine.split(","));
			// new String[] { "Extended Sample ID",
			// "p2o5", null, "P2O5", "std_P2O5", "me_P2O5" };

			List<List<String>> rowData = new CSVMMElementParser()
					.readMMElementData(inputFile, mmElementDataHeaders);

			for (List<String> r : rowData) {
				dataWriter.write(r);
				// System.out.println(r);
			}

			nextLine = in.hasNextLine() ? in.nextLine() : null;
		}
		dataWriter.flush();
		dataWriter.close();
		in.close();
	}

	private static Headers getSelectAndRenameHeaders(File inputSelectFile)
			throws FileNotFoundException {

		Headers result = null;

		try (Scanner in = new Scanner(inputSelectFile)) {

			String[] vals = trim(in.nextLine().split(","));
			String[] newVals = trim(in.nextLine().split(","));
			result = new Headers(vals, newVals);
		}

		return result;
	}

	private static String[] trim(String[] split) {
		String[] result = new String[split.length];
		for (int i = 0; i < split.length; i++) {
			result[i] = split[i].trim();
		}
		return result;
	}

	public List<Map<String, String>> partialReadCSV(final File csvFile,
			final String[] headersToRead) throws Exception {

		List<Map<String, String>> result = new ArrayList<>();
		ICsvListReader mapReader = null;
		try {
			mapReader = new CsvListReader(new FileReader(csvFile),
					CsvPreference.STANDARD_PREFERENCE);

			String[] headers = mapReader.getHeader(true); // skip past the
															// headers

			Map<String, Integer> headerMap = new HashMap<>();
			for (String s : headersToRead) {
				boolean found = false;
				for (int i = 0; i < headers.length; i++) {
					String h = headers[i];
					if (s.equals(h)) {
						headerMap.put(s, i);
						found = true;
						break;
					}
				}
				if (!found) {
					throw new Exception("Unable to find matching header for "
							+ s + " using as value");
				}
			}

			Map<String, String> rowData;
			List<String> row;
			while ((row = mapReader.read()) != null) {
				rowData = new HashMap<>(headersToRead.length);
				for (String s : headersToRead) {
					rowData.put(s, row.get(headerMap.get(s)));
				}
				result.add(rowData);
			}

		} finally {
			if (mapReader != null) {
				mapReader.close();
			}
		}

		return result;
	}

	public List<List<String>> readMMElementData(final File csvFile,
			final String[] expectedHeaders) throws Exception {

		List<List<String>> result = new ArrayList<>();
		ICsvListReader mapReader = null;
		try {
			mapReader = new CsvListReader(new FileReader(csvFile),
					CsvPreference.STANDARD_PREFERENCE);

			// Skip past the headers
			String[] headers = mapReader.getHeader(true);

			Map<String, Integer> headerMap = new HashMap<>();
			for (int i = 0; i < expectedHeaders.length; i++) {
				boolean found = false;
				String s = expectedHeaders[i];
				for (int j = 0; j < headers.length; j++) {
					String h = headers[j];
					if (i == 1 || i == 2) {
						found = true; // These are specified in the header
						break;
					} else if (s.equals(h)) {
						headerMap.put(s, j);
						found = true;
						break;
					}
				}
				if (!found) {
					throw new Exception("Unable to find matching header for "
							+ s + " using as value");
				}
			}

			List<String> row;
			while ((row = mapReader.read()) != null) {
				List<String> rowData = new ArrayList<>(expectedHeaders.length);
				for (int i = 0; i < expectedHeaders.length; i++) {
					if (i != 1 & i != 2)
						rowData.add(row.get(headerMap.get(expectedHeaders[i])));
					else
						rowData.add(expectedHeaders[i]);
				}
				result.add(rowData);
			}

		} finally {
			if (mapReader != null) {
				mapReader.close();
			}
		}

		return result;
	}
}
