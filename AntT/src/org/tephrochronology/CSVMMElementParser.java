/**
 * 
 */
package org.tephrochronology;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		File inputFile = new File(args[0]);//"/home/mroyer/Desktop/MMElementExample.csv");

		File outputMMElementFile = new File(args[1]);//				"/home/mroyer/Desktop/MMElementExampleFilterd.csv");

		File outputMMElementDataFile = new File(args[2]); // 				"/home/mroyer/Desktop/MMElementExampleDataFilterd.csv");

		// SampleID,Extended Sample ID,Comments about samples,METHOD,Instrument,
		// P2O5,std_P2O5,me_P2O5,
		// SiO2,std_SiO2,me_SiO2,
		// SO2,std_SO2,me_SO2,
		// TiO2,std_TiO2,me_TiO2,
		// Al2O3,std_Al2O3,me_Al2O3,
		// MgO,std_MgO,me_MgO,
		// CaO,std_CaO,me_CaO,
		// MnO,std_MnO,me_MnO,
		// FeO,std_FeO,me_FeO,
		// Na2O,std_Na2O,me_Na2O,
		// K2O,std_K2O,me_K2O,
		// F,std_F,me_F,
		// Cl,std_Cl,me_Cl,
		// Number of measurements,CalculatedTotal,EMPA
		// notes,DatabaseInfo::DataBaseName

		String[] headers = new String[] { "SampleID", "Extended Sample ID",
				"Comments about samples", "METHOD", "Instrument",
				"Number of measurements", "CalculatedTotal", "EMPA notes" };
		String[] outputHeaders = new String[] { "sample_id", "longsample_id",
				"comments", "method_type", "iid", "number_of_measurements",
				"calculated_total", "instrument_settings" };

		String[] expectedHeaders = new String[] { "longsample_id", "sample_id",
				"comments", "method_type", "iid", "date_measured",
				"measured_by", "original_total", "calculated_total",
				"instrument_settings", "h2o_plus", "h2o_minus", "loi" };

		List<Map<String, String>> rows = new CSVMMElementParser()
				.partialReadCSV(inputFile, headers);

		ICsvListWriter mapWriter = new CsvListWriter(
				new FileWriter(outputMMElementFile),
				CsvPreference.STANDARD_PREFERENCE);

		mapWriter.writeHeader(expectedHeaders);
		Map<String, String> headersToOutputName = new HashMap<>(
				outputHeaders.length);
		for (int i = 0; i < outputHeaders.length; i++) {
			headersToOutputName.put(outputHeaders[i], headers[i]);
		}
		for (Map<String, String> r : rows) {
			Object[] arr = Arrays.stream(expectedHeaders)
					.map(e -> r.get(headersToOutputName.get(e))).toArray();
			mapWriter.write(arr);
			System.out.println(arr);
		}
		mapWriter.flush();
		mapWriter.close();


		// Required columns for mm_elements_data
		//
		// longsample_id, element, unit , val, std, me
		//
		//
		// Extended Sample ID, "p2o5" , "%", P2O5 ,std_P2O5,me_P2O5

		headers = new String[] { "Extended Sample ID", "p2o5", null, "P2O5",
				"std_P2O5", "me_P2O5" };

		List<List<String>> rowData = new CSVMMElementParser()
				.readMMElementData(inputFile, headers);

		ICsvListWriter dataWriter = new CsvListWriter(
				new FileWriter(outputMMElementDataFile),
				CsvPreference.STANDARD_PREFERENCE);

		dataWriter.writeHeader(new String[] { "longsample_id", "element",
				"unit", "val", "std", "me" });

		for (List<String> r : rowData) {
			dataWriter.write(r);
			System.out.println(r);
		}
		dataWriter.flush();
		dataWriter.close();

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
