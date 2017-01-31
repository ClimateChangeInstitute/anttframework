package org.tephrochronology;

import static org.tephrochronology.DBProperties.DEFAULT_PASSWORD_FILE;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;

import org.apache.commons.io.FileUtils;
import org.tephrochronology.model.BIASample;
import org.tephrochronology.model.IceCoreSample;
import org.tephrochronology.model.LakeSample;
import org.tephrochronology.model.MarineSample;
import org.tephrochronology.model.OutcropSample;
import org.xml.sax.SAXException;

/**
 * Generate XML data from the database, and place the created files in the
 * WebContent/generated directory.
 * 
 * @author Willie Stevenson
 * @author Mark Royer
 */
public class StaticSiteDataGenerator {

	public static void main(String[] args)
			throws IOException, PropertyException, JAXBException, SAXException {

		if (args.length != 2) {
			args = DBProperties.findUserPassword(DEFAULT_PASSWORD_FILE);
			if (args.length != 2) {
				System.err.printf("Usage: java %s USER PASS\n",
						StaticSiteDataGenerator.class.getName());
				System.err.printf("Optionally, create the %s file.\n",
						DEFAULT_PASSWORD_FILE.getPath());
				System.exit(-1);
			}
		}

		final Path PROJECT_ROOT = Paths.get("").toAbsolutePath();
		final Path WEBCONTENT_DIR = Paths.get(PROJECT_ROOT + "/WebContent");

		// generate directories
		Path GEN_DIR = Paths.get(WEBCONTENT_DIR + "/generated");

		if (!GEN_DIR.toFile().mkdirs()) {
			System.out.printf(
					"The folder '%s' exists.\n"
							+ "The contents will be deleted and the folder recreated.\n"
							+ "Are you sure that you want to continue? (y/N).",
					GEN_DIR);
			try (Scanner in = new Scanner(System.in)) {
				if (!"y".equalsIgnoreCase(in.nextLine())) {
					System.out.println(
							"Okay; I won't overwrite the existing data.");
					System.exit(0);
				} else {
					FileUtils.deleteDirectory(GEN_DIR.toFile());
					GEN_DIR = appendChildAndCreateDir(WEBCONTENT_DIR,
							"/generated");
				}
			}
		}

		final Path XML_SAMPLES = appendChildAndCreateDir(GEN_DIR,
				"/XMLSamples");
		final Path SAMPLE_BIA = appendChildAndCreateDir(XML_SAMPLES, "/BIA");
		final Path ICE_CORE = appendChildAndCreateDir(XML_SAMPLES, "/IceCore");
		final Path AQUATIC_LAKE = appendChildAndCreateDir(XML_SAMPLES,
				"/Aquatic/Lake");
		final Path AQUATIC_MARINE = appendChildAndCreateDir(XML_SAMPLES,
				"/Aquatic/Marine");
		final Path OUTCROP = appendChildAndCreateDir(XML_SAMPLES, "/Outcrop");

		XMLFileGenerator fileGenerator = new XMLFileGenerator(args[0], args[1]);
		fileGenerator.writeSampleXMLFiles(SAMPLE_BIA, BIASample.class);
		fileGenerator.writeSampleXMLFiles(ICE_CORE, IceCoreSample.class);
		fileGenerator.writeSampleXMLFiles(AQUATIC_LAKE, LakeSample.class);
		fileGenerator.writeSampleXMLFiles(AQUATIC_MARINE, MarineSample.class);
		fileGenerator.writeSampleXMLFiles(OUTCROP, OutcropSample.class);

		fileGenerator.writeAllSamplesXMLFile(GEN_DIR);

		fileGenerator.writeAllMMElementXMLFile(GEN_DIR);

		System.out.println("Done generating xml sample files.");
	}

	private static Path appendChildAndCreateDir(final Path PROJECT_ROOT,
			String childPath) {
		Path p = Paths.get(PROJECT_ROOT + childPath);
		p.toFile().mkdirs();
		return p;
	}

}
