/**
 * 
 */
package org.tephrochronology;

import static org.tephrochronology.DBProperties.setupProperties;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.namespace.QName;

import org.tephrochronology.model.MMElement;
import org.tephrochronology.model.MMElements;
import org.tephrochronology.model.Sample;
import org.tephrochronology.model.SampleInfo;
import org.tephrochronology.model.Samples;

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
			throws PropertyException, JAXBException, FileNotFoundException {

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
			writeObjectToXML(s, clazz, out);
			out.flush();
			out.close();
		}
		System.out.println();

	}

	public void writeAllSamplesXMLFile(Path outputLocation)
			throws FileNotFoundException, PropertyException, JAXBException {

		final String ALLSAMPLES_FILENAME = "allSamples.xml";

		System.out.printf("Generating %s file.\n", ALLSAMPLES_FILENAME);

		//@formatter:off
		TypedQuery<SampleInfo> q = em.createQuery(
				  "SELECT NEW org.tephrochronology.model.SampleInfo(TYPE(s), "
				+ "s.sampleID, s.longName, s.sampledBy, s.comments, "
				+ "s.collectionDate, s.site.siteID, s.instrument.id) "
				+ "FROM Sample s "
				+ "ORDER BY TYPE(s), s.sampleID, s.collectionDate"
				, SampleInfo.class);
		//@formatter:on

		List<SampleInfo> samples = q.getResultList();//new ArrayList<>();

		PrintStream out = new PrintStream(new FileOutputStream(
				outputLocation + File.separator + ALLSAMPLES_FILENAME));

		JAXBContext jc = JAXBContext.newInstance(Samples.class);

		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(new Samples(samples), out);

		out.flush();
		out.close();
	}

	public void writeAllMMElementXMLFile(Path outputLocation)
			throws FileNotFoundException, JAXBException {

		final String ALLMMELEMENTS_FILENAME = "allMMEelements.xml";

		System.out.printf("Generating %s file.\n", ALLMMELEMENTS_FILENAME);

		//@formatter:off
		TypedQuery<MMElement> q = em.createQuery(
				  "SELECT mme "
				+ "FROM MMElement mme "
				+ "ORDER BY mme.longsampleID", MMElement.class);
		//@formatter:on

		List<MMElement> queryResult = q.getResultList();

//		//@formatter:off
//		TypedQuery<MMElementInfo> q = em.createQuery(
//				  "SELECT NEW org.tephrochronology.model.MMElementInfo("
//			    + "mme.longsampleID, mme.sample.sampleID, mme.comments, "
//			    + "mme.methodType, mme.instrument.id, mme.dateMeasured, mme.measuredBy, "
//			    + "mme.numberOfMeasurements, mme.originalTotal,	"
//			    + "mme.calculatedTotal, mme.instrumentSettings, mme.h2o_plus, "
//			    + "mme.h2o_minus, mme.loi, mme.elementData) "
//				+ "FROM MMElement mme "
//				+ "ORDER BY mme.longsampleID", MMElementInfo.class);
//		//@formatter:on
//				
//
//		List<MMElementInfo> queryResult = q.getResultList();
		
		PrintStream out = new PrintStream(new FileOutputStream(
				outputLocation + File.separator + ALLMMELEMENTS_FILENAME));

		JAXBContext jc = JAXBContext.newInstance(MMElements.class);

		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(new MMElements(queryResult), out);

		out.flush();
		out.close();

	}

	/**
	 * @param o
	 * @param clazz
	 * @param out
	 * @throws JAXBException
	 * @throws PropertyException
	 */
	private static <T> void writeObjectToXML(T o, Class<T> clazz,
			PrintStream out) throws JAXBException, PropertyException {

		JAXBContext jc = JAXBContext.newInstance(clazz);

		JAXBElement<T> je = new JAXBElement<>(new QName(clazz.getSimpleName()),
				clazz, o);

		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(je, out);
	}

}
