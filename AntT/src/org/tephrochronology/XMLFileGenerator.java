/**
 * 
 */
package org.tephrochronology;

import static org.tephrochronology.DBProperties.setupProperties;

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

import org.tephrochronology.model.IceCoreSample;

/**
 * @author willie
 *
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

	public void writeIceCoreSampleXMLFiles(Path outputLocation)
			throws PropertyException, JAXBException, FileNotFoundException {
		TypedQuery<IceCoreSample> q = em.createQuery(
				"SELECT s FROM IceCoreSample s ORDER BY s.sampleID",
				IceCoreSample.class);

		List<IceCoreSample> ics = q.getResultList();

		for (IceCoreSample iceCoreSample : ics) {
			PrintStream out = new PrintStream(
					new FileOutputStream(outputLocation + "/"
							+ iceCoreSample.getSampleID() + ".xml"));
			writeObjectToXML(iceCoreSample, IceCoreSample.class, out);
			out.flush();
			out.close();
		}

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
