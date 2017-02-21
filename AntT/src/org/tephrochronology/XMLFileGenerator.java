/**
 * 
 */
package org.tephrochronology;

import static org.tephrochronology.DBProperties.setupProperties;
import static org.tephrochronology.model.Chemistry.QUERY_CHEMISTRIES_BY_PREFERRED_ORDER;
import static org.tephrochronology.model.MMElement.QUERY_MMElements_ORDER_BY_ID;
import static org.tephrochronology.model.Sample.QUERY_GET_SAMPLE_INFO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.FileUtils;
import org.tephrochronology.model.Chemistries;
import org.tephrochronology.model.Chemistry;
import org.tephrochronology.model.Image;
import org.tephrochronology.model.MMElement;
import org.tephrochronology.model.MMElementInfo;
import org.tephrochronology.model.MMElements;
import org.tephrochronology.model.Sample;
import org.tephrochronology.model.SampleInfo;
import org.tephrochronology.model.Samples;
import org.tephrochronology.model.TephraSchemaOutputResolver;
import org.xml.sax.SAXException;

/**
 * @author willie
 * @author Mark Royer
 */
public class XMLFileGenerator {

	private EntityManagerFactory emf;

	private EntityManager em;

	public XMLFileGenerator(String user, String pass) {
		super();

		emf = Persistence.createEntityManagerFactory("antt",
				setupProperties(new String[] { user, pass }));
		em = emf.createEntityManager();

	}

	public <T extends Sample> void writeSampleXMLFiles(Path outputLocation,
			Path imagesPath, Path imageThumbnailsPath, Class<T> clazz)
			throws PropertyException, JAXBException, IOException, SAXException {

		System.out.printf("Generating %s sample files.", clazz.getSimpleName());

		TypedQuery<T> q = em.createQuery(
				String.format("SELECT s FROM %s s ORDER BY s.sampleID",
						clazz.getSimpleName()),
				clazz);

		List<T> samples = q.getResultList();

		for (T s : samples) {
			System.out.print('.');
			PrintStream out = new PrintStream(new FileOutputStream(
					outputLocation + "/" + s.getSampleID() + ".xml"));

			writeXMLAndXSDFiles(outputLocation, s.getSampleID() + ".xml",
					clazz.getSimpleName() + ".xsd", s, clazz);

			out.flush();
			out.close();

			final File imagesFolder = imagesPath.toFile();
			final File imageThumbnailsFolder = imageThumbnailsPath.toFile();
			for (Image i : s.getImages()) {
				FileUtils.writeByteArrayToFile(
						new File(imagesFolder, i.getImageID()), i.getBytes());
				FileUtils.writeByteArrayToFile(
						new File(imageThumbnailsFolder, i.getImageID()),
						i.getThumbBytes());
			}
		}
		System.out.println();

	}

	public void writeAllSamplesXMLFile(Path outputLocation)
			throws PropertyException, JAXBException, SAXException, IOException {

		final String ALLSAMPLES_FILENAME = "allSamples.xml";

		System.out.printf("Generating %s file.\n", ALLSAMPLES_FILENAME);

		TypedQuery<SampleInfo> q = em.createNamedQuery(QUERY_GET_SAMPLE_INFO,
				SampleInfo.class);

		List<SampleInfo> samples = q.getResultList();

		writeXMLAndXSDFiles(outputLocation, ALLSAMPLES_FILENAME,
				ALLSAMPLES_FILENAME.replace(".xml", ".xsd"),
				new Samples(samples), Samples.class);

	}

	/**
	 * Write an XML file and XSD (if it does not exist) to the specified
	 * locations.
	 * 
	 * @param outputLocation
	 *            Folder location (Not null)
	 * @param xmlFileName
	 *            The name of the XML file (Not null)
	 * @param xsdFileName
	 *            The name of the XSD file (Not null)
	 * @param obj
	 *            The object to write as XML (Not null)
	 * @param types
	 *            The types of objects involved for XML generation (Requires at
	 *            least 1)
	 * @throws JAXBException
	 *             If malformed XML
	 * @throws IOException
	 *             If problems writing to file
	 * @throws SAXException
	 *             If malformed XML
	 */
	private void writeXMLAndXSDFiles(Path outputLocation,
			final String xmlFileName, final String xsdFileName, Object obj,
			Class<?>... types) throws JAXBException, IOException, SAXException {
		PrintStream out = new PrintStream(new FileOutputStream(
				outputLocation + File.separator + xmlFileName));

		JAXBContext jc = JAXBContext.newInstance(types);

		File schemaFile = new File(outputLocation.toFile(), xsdFileName);

		// If the file doesn't exist, create it
		if (!schemaFile.exists()) {
			schemaFile = new File(outputLocation.toFile(), xsdFileName);
			jc.generateSchema(new TephraSchemaOutputResolver(schemaFile));
		}

		Marshaller marshaller = jc.createMarshaller();

		SchemaFactory factory = SchemaFactory
				.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema schema = factory.newSchema(schemaFile);
		marshaller.setSchema(schema);

		marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION,
				"./" + schemaFile.getName());
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(obj, out);

		out.flush();
		out.close();
	}

	public void writeAllMMElementXMLFile(Path outputLocation)
			throws JAXBException, IOException, SAXException {

		final String ALLMMELEMENTS_FILENAME = "allMMElements.xml";

		System.out.printf("Generating %s file.\n", ALLMMELEMENTS_FILENAME);

		TypedQuery<MMElement> q = em.createNamedQuery(
				QUERY_MMElements_ORDER_BY_ID, MMElement.class);

		List<MMElement> queryResult = q.getResultList();

		List<MMElementInfo> elementInfos = new ArrayList<>(queryResult.size());

		queryResult.stream()
				.forEachOrdered(e -> elementInfos.add(new MMElementInfo(e)));

		writeXMLAndXSDFiles(outputLocation, ALLMMELEMENTS_FILENAME,
				ALLMMELEMENTS_FILENAME.replace(".xml", ".xsd"),
				new MMElements(elementInfos), MMElements.class);

	}

	public void writeAllChemistriesFile(Path outputLocation)
			throws JAXBException, IOException, SAXException {
		
		final String ALLCHEMISTRIES_FILENAME = "allChemistries.xml";

		System.out.printf("Generating %s file.\n", ALLCHEMISTRIES_FILENAME);

		TypedQuery<Chemistry> q = em.createNamedQuery(
				QUERY_CHEMISTRIES_BY_PREFERRED_ORDER, Chemistry.class);

		List<Chemistry> queryResult = q.getResultList();
		
		List<Chemistry> chemistries = new ArrayList<>(queryResult.size());
		
		chemistries.addAll(queryResult);

		writeXMLAndXSDFiles(outputLocation, ALLCHEMISTRIES_FILENAME,
				ALLCHEMISTRIES_FILENAME.replace(".xml", ".xsd"),
				new Chemistries(chemistries), Chemistries.class);

		
	}

}
