/**
 * 
 */
package org.tephrochronology;

import static org.tephrochronology.DBProperties.setupProperties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.namespace.QName;

import org.tephrochronology.model.GrainSize;
import org.tephrochronology.model.GrainSizeID;
import org.tephrochronology.model.IceCoreSample;
import org.tephrochronology.model.Image;
import org.tephrochronology.model.Ref;
import org.tephrochronology.model.TestDataGenerator;
import org.tephrochronology.model.Volcano;

/**
 * This class requires that the test data has been generated from the
 * {@link TestDataGenerator} class.
 * 
 * @author Mark Royer
 *
 */
public class ExampleXMLOutput {

	public static void main(String[] args) throws JAXBException {

		if (args.length != 2) {
			System.err.println("Usage: java QueryVolcanoes USER PASS");
			System.exit(-1);
		}

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("antt", setupProperties(args));
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		displayObjectXML(em.find(Volcano.class, 210010), Volcano.class);

		displayObjectXML(em.find(IceCoreSample.class, "IceCoreSample9"),
				IceCoreSample.class);

		displayObjectXML(em.find(Ref.class, "Ref11"), Ref.class);

		displayObjectXML(em.find(Image.class, 11), Image.class);

		displayObjectXML(
				em.find(GrainSize.class,
						new GrainSizeID("OutcropSample13", "Instrument6")),
				GrainSize.class);

		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	/**
	 * @param v
	 * @throws JAXBException
	 * @throws PropertyException
	 */
	private static <T> void displayObjectXML(T o, Class<T> clazz)
			throws JAXBException, PropertyException {

		JAXBContext jc = JAXBContext.newInstance(clazz);

		JAXBElement<T> je = new JAXBElement<>(new QName(clazz.getSimpleName()),
				clazz, o);

		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(je, System.out);
	}
}
