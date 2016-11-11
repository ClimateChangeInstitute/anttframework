/**
 * 
 */
package org.tephrochronology;

import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_DRIVER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_PASSWORD;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_URL;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_USER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_LEVEL;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_SESSION;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_THREAD;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_TIMESTAMP;
import static org.eclipse.persistence.config.PersistenceUnitProperties.TARGET_SERVER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.TRANSACTION_TYPE;
import static org.eclipse.persistence.logging.SessionLog.OFF_LABEL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.spi.PersistenceUnitTransactionType;

import org.eclipse.persistence.config.TargetServer;
import org.tephrochronology.model.Instrument;
import org.tephrochronology.model.OutcropSample;
import org.tephrochronology.model.Ref;
import org.tephrochronology.model.Site;
import org.tephrochronology.model.SiteType;
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

		TypedQuery<Volcano> q = em.createQuery("SELECT v FROM Volcano v",
				Volcano.class);

		List<Volcano> vs = q.getResultList();

		// vs.stream().forEach(v -> System.out.println(v.getName()));

		TypedQuery<Ref> refQ = em.createQuery("SELECT r FROM Ref r", Ref.class);

		List<Ref> refs = refQ.getResultList();

		refs.stream().forEach(r -> System.out
				.println(r.getDoi() + " " + r.getGrainSizes().size()));

//		OutcropSample os = new OutcropSample("sample1", "long sample1",
//				"test sampled", LocalDate.now(), "Just some test comments",
//				new Site("site1", new SiteType("site1Type"), 70, 50, 30,
//						"first site comment"),
//				new Instrument("inst1", "inst1 long", "inst1 loc",
//						"inst1 comment"),
//				new ArrayList<>(), new ArrayList<>(), 210010);
//		
//		em.persist(os);

		TypedQuery<OutcropSample> osQ = em.createQuery("SELECT o FROM OutcropSample o WHERE o.volcanoNumber = 210010", OutcropSample.class);

		System.out.println(osQ.getSingleResult().getSampleID());
		
		em.getTransaction().commit();

		em.close();
		emf.close();

	}

	private static Map<String, String> setupProperties(String[] args) {

		Map<String, String> properties = new HashMap<>();

		// Ensure RESOURCE_LOCAL transactions is used.
		properties.put(TRANSACTION_TYPE,
				PersistenceUnitTransactionType.RESOURCE_LOCAL.name());

		// Configure the internal EclipseLink connection pool
		properties.put(JDBC_DRIVER, "org.postgresql.Driver");
		properties.put(JDBC_URL, "jdbc:postgresql://localhost:5432/antt");
		properties.put(JDBC_USER, args[0]);
		properties.put(JDBC_PASSWORD, args[1]);

		// Configure logging. FINE ensures all SQL is shown
		properties.put(LOGGING_LEVEL, OFF_LABEL);
		properties.put(LOGGING_TIMESTAMP, "false");
		properties.put(LOGGING_THREAD, "false");
		properties.put(LOGGING_SESSION, "false");

		// Ensure that no server-platform is configured
		properties.put(TARGET_SERVER, TargetServer.None);

		return properties;

	}

}
