package org.tephrochronology;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;

/**
 * Start the program
 * 
 * @author Willie Stevenson
 *
 */
public class Runner {

	public static void main(String[] args) {
		final Path PROJECT_ROOT = Paths.get("").toAbsolutePath();
		final Path SOURCE_DIR = Paths.get(PROJECT_ROOT + "/template");

		// generate directories
		final Path OUTPUT_DIR = Paths.get(PROJECT_ROOT + "/output");
		final Path XML_SAMPLES = Paths.get(PROJECT_ROOT + "/output/XMLSamples");
		final Path SAMPLE_BIA = Paths
				.get(PROJECT_ROOT + "/output/XMLSamples/BIA");
		final Path ICE_CORE = Paths
				.get(PROJECT_ROOT + "/output/XMLSamples/IceCore");
		final Path AQUATIC_LAKE = Paths
				.get(PROJECT_ROOT + "/output/XMLSamples/Aquatic/Lake");
		final Path AQUATIC_MARINE = Paths
				.get(PROJECT_ROOT + "/output/XMLSamples/Aquatic/Marine");
		final Path OUTCROP = Paths
				.get(PROJECT_ROOT + "/output/XMLSamples/Outcrop");

		/*
		 * Put web layout in output folder
		 */
		// FileUtils.copyDirectory(SOURCE_DIR.toFile(), OUTPUT_DIR.toFile());

		/*
		 * Generate all site XMLSamples
		 * WebContent/XMLSamples/Samples/SiteFolder/*.xml
		 */

		OUTPUT_DIR.toFile().mkdir();
		XML_SAMPLES.toFile().mkdir();
		SAMPLE_BIA.toFile().mkdir();
		ICE_CORE.toFile().mkdir();
		AQUATIC_LAKE.toFile().mkdir();
		AQUATIC_MARINE.toFile().mkdir();
		OUTCROP.toFile().mkdir();

	}

}
