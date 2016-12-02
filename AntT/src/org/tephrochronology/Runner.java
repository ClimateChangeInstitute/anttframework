package org.tephrochronology;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;

import org.tephrochronology.model.BIASample;
import org.tephrochronology.model.IceCoreSample;
import org.tephrochronology.model.LakeSample;
import org.tephrochronology.model.MarineSample;
import org.tephrochronology.model.OutcropSample;

/**
 * Start the program
 * 
 * @author Willie Stevenson
 *
 */
public class Runner {

	public static void main(String[] args)
			throws IOException, PropertyException, JAXBException {
		
		if (args.length != 2) {
			System.err.println("Usage: java Runner USER PASS");
			System.exit(-1);
		}
		
		final Path PROJECT_ROOT = Paths.get("").toAbsolutePath();
		final Path WEBCONTENT_DIR = Paths.get(PROJECT_ROOT + "/WebContent");

		// generate directories
		final Path GEN_DIR = appendChildAndCreateDir(WEBCONTENT_DIR,
				"/generated");
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
	}

	private static Path appendChildAndCreateDir(final Path PROJECT_ROOT,
			String childPath) {
		Path p = Paths.get(PROJECT_ROOT + childPath);
		p.toFile().mkdirs();
		return p;
	}

}
