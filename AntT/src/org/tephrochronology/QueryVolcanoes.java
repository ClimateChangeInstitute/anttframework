/**
 * 
 */
package org.tephrochronology;

import static org.tephrochronology.DBProperties.setupProperties;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.tephrochronology.model.Chemistry;
import org.tephrochronology.model.Instrument;
import org.tephrochronology.model.MMElement;
import org.tephrochronology.model.MMElementData;
import org.tephrochronology.model.MethodType;
import org.tephrochronology.model.OutcropSample;
import org.tephrochronology.model.Ref;
import org.tephrochronology.model.Unit;
import org.tephrochronology.model.Volcano;

/**
 * An example of how to use JPA to query the database.
 * 
 * @author Mark Royer
 *
 */
public class QueryVolcanoes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length != 2) {
			System.err.println("Usage: java QueryVolcanoes USER PASS");
			System.exit(-1);
		}

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("antt", setupProperties(args));
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		// queryVolcanoesExample(em);

		//@formatter:off
		TypedQuery<Ref> refQ = em.createQuery(
				  "SELECT r FROM Ref r "
				+ "WHERE r.doi < 'Ref100' "
				+ "ORDER BY r.doi", 
				Ref.class);
		//@formatter:on

		List<Ref> refs = refQ.getResultList();

		refs.stream()
				.forEach(r -> System.out.printf(
						"doi: %s, grain sizes count is: %d\n", r.getDoi(),
						r.getGrainSizes().size()));

		//@formatter:off
		TypedQuery<OutcropSample> osQ = em.createQuery(
				  "SELECT o "
				+ "FROM OutcropSample o "
				+ "WHERE o.volcano.volcanoNumber = 210010",
				OutcropSample.class);
		//@formatter:on

		osQ.getResultList().stream()
				.forEach(e -> System.out.println(e.getSampleID()));

		// createMMElement(em, osQ);

		//@formatter:off
		TypedQuery<MMElement> mmeQ = em.createQuery(
				  "SELECT m "
				+ "FROM MMElement m ",
				MMElement.class);
		//@formatter:on

		List<MMElement> mmeList = mmeQ.getResultList();

		for (MMElement m : mmeList) {
			m.getElementData().stream()
					.forEach(e -> System.out.println(e.getValue()));
		}

		em.getTransaction().commit();

		em.close();
		emf.close();

		System.out.println("Done");
	}

	static void createMMElement(EntityManager em,
			TypedQuery<OutcropSample> osQ) {
		List<MMElementData> data = new ArrayList<>();
		MMElement el = new MMElement(MMElement.class.getSimpleName() + "0",
				osQ.getSingleResult(), "Comment", new MethodType("MethodType0"),
				new Instrument(Instrument.class.getSimpleName() + "0", "", "",
						""),
				LocalDate.now().toString(), "Mark", 5, 3f, 2f,
				"instrument settings", data);
		Chemistry elem = new Chemistry("SiO2", "Silicon dioxide",
				"SIO<sub>2</sub>", 60.08f, null);
		data.add(new MMElementData(el, elem, 10f, 2f, 1f, new Unit("ppb")));
		elem = new Chemistry("TiO2", "Titanium dioxide", "TiO<sub>2</sub>",
				79.866f, null);
		data.add(new MMElementData(el, elem, 20f, 4f, 2f, new Unit("ppb")));
		elem = new Chemistry("SO2", "Sulfur dioxide", "SO<sub>2</sub>", 64.066f,
				null);
		data.add(new MMElementData(el, elem, 30f, 8f, 3f, new Unit("ppb")));

		em.persist(el);
	}

	/**
	 * @param em
	 */
	static void queryVolcanoesExample(EntityManager em) {
		TypedQuery<Volcano> q = em.createQuery("SELECT v FROM Volcano v",
				Volcano.class);

		List<Volcano> vs = q.getResultList();

		vs.stream().forEach(v -> System.out.println(v.getName()));
	}

}
