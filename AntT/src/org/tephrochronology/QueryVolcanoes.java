/**
 * 
 */
package org.tephrochronology;

import static org.tephrochronology.DBProperties.setupProperties;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.tephrochronology.model.OutcropSample;
import org.tephrochronology.model.Ref;
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

		TypedQuery<Ref> refQ = em.createQuery("SELECT r FROM Ref r", Ref.class);

		List<Ref> refs = refQ.getResultList();

		refs.stream().forEach(r -> System.out
				.println(r.getDoi() + " " + r.getGrainSizes().size()));

		// addOutcropExample(em);

		TypedQuery<OutcropSample> osQ = em.createQuery(
				"SELECT o FROM OutcropSample o WHERE o.volcanoNumber = 210010",
				OutcropSample.class);

		System.out.println(osQ.getSingleResult().getSampleID());

		em.getTransaction().commit();

		em.close();
		emf.close();

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

	/**
	 * @param em
	 */
	static void addOutcropExample(EntityManager em) {
		// OutcropSample os = new OutcropSample("sample1", "long sample1",
		// "test sampled", LocalDate.now(), "Just some test comments",
		// new Site("site1", new SiteType("site1Type"), 70, 50, 30,
		// "first site comment"),
		// new Instrument("inst1", "inst1 long", "inst1 loc",
		// "inst1 comment"),
		// new ArrayList<>(), new ArrayList<>(), 210010);
		//
		// em.persist(os);
	}

}
