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

}
