/**
 * 
 */
package org.tephrochronology;

import static org.tephrochronology.DBProperties.setupProperties;

import java.io.File;
import java.io.FileNotFoundException;
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
			Class<T> clazz)
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
		}
		System.out.println();

	}

	public void writeAllSamplesXMLFile(Path outputLocation)
			throws PropertyException, JAXBException, SAXException, IOException {

		final String ALLSAMPLES_FILENAME = "allSamples.xml";

		System.out.printf("Generating %s file.\n", ALLSAMPLES_FILENAME);

		//@formatter:off
		TypedQuery<SampleInfo> q = em.createQuery(
				  "SELECT NEW org.tephrochronology.model.SampleInfo(TYPE(s), "
				+ "s.sampleID, s.secondaryID, s.sampledBy, s.comments, "
				+ "s.collectionDate, s.category.categoryID, s.instrument.id) "
				+ "FROM Sample s "
				+ "ORDER BY TYPE(s), s.sampleID, s.collectionDate"
				, SampleInfo.class);
		//@formatter:on

		List<SampleInfo> samples = q.getResultList();

		writeXMLAndXSDFiles(outputLocation, ALLSAMPLES_FILENAME,
				ALLSAMPLES_FILENAME.replace(".xml", ".xsd"),
				new Samples(samples), Samples.class);

	}

	/**
	 * @param outputLocation
	 * @param xmlFileName
	 * @param xsdFileName
	 * @param obj
	 * @param types
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws SAXException
	 * @throws PropertyException
	 */
	private void writeXMLAndXSDFiles(Path outputLocation,
			final String xmlFileName, final String xsdFileName, Object obj,
			Class<?>... types) throws FileNotFoundException, JAXBException,
			IOException, SAXException, PropertyException {
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

		//@formatter:off
		TypedQuery<MMElement> q = em.createQuery(
				  "SELECT mme "
				+ "FROM MMElement mme "
				+ "ORDER BY mme.longsampleID", MMElement.class);
		//@formatter:on

		List<MMElement> queryResult = q.getResultList();

		List<MMElementInfo> elementInfos = new ArrayList<>(queryResult.size());

		queryResult.stream()
				.forEachOrdered(e -> elementInfos.add(new MMElementInfo(e)));

		writeXMLAndXSDFiles(outputLocation, ALLMMELEMENTS_FILENAME,
				ALLMMELEMENTS_FILENAME.replace(".xml", ".xsd"),
				new MMElements(elementInfos), MMElements.class);

		// PrintStream out = new PrintStream(new FileOutputStream(
		// outputLocation + File.separator + ALLMMELEMENTS_FILENAME));
		//
		// JAXBContext jc = JAXBContext.newInstance(MMElements.class);
		//
		// Marshaller marshaller = jc.createMarshaller();
		// marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		// marshaller.marshal(new MMElements(elementInfos), out);
		//
		// out.flush();
		// out.close();

	}

	/**
	 * @param o
	 * @param clazz
	 * @param out
	 * @throws JAXBException
	 * @throws PropertyException
	 */
	// private static <T> void writeObjectToXML(T o, Class<T> clazz,
	// PrintStream out) throws JAXBException, PropertyException {
	//
	// JAXBContext jc = JAXBContext.newInstance(clazz);
	//
	// JAXBElement<T> je = new JAXBElement<>(new QName(clazz.getSimpleName()),
	// clazz, o);
	//
	// Marshaller marshaller = jc.createMarshaller();
	// marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	// marshaller.marshal(je, out);
	// }

}
