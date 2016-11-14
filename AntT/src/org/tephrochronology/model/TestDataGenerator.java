/**
 * 
 */
package org.tephrochronology.model;

import static java.util.stream.IntStream.range;
import static org.tephrochronology.DBProperties.setupProperties;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Generates sample data for the database to use with testing.
 * 
 * @author Mark Royer
 *
 */
public class TestDataGenerator {

	List<SiteType> siteTypes;

	List<CoreType> coreTypes;

	List<MethodType> methodTypes;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length != 2) {
			System.err.println("Usage: java QueryVolcanoes USER PASS");
			System.exit(-1);
		}

		new TestDataGenerator().execute(args);

	}

	/**
	 * @param args
	 */
	private void execute(String[] args) {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("antt", setupProperties(args));
		EntityManager em = emf.createEntityManager();

		try {

			em.getTransaction().begin();

			generateTypeData(em);

			generateSiteData(em);

			em.getTransaction().rollback(); // For now

			em.getTransaction().commit();

		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
	}

	private void generateSiteData(EntityManager em) {

		int n = 50;
		List<Site> sites = new ArrayList<>();

		range(0, n).forEach(i -> {
			Site s = new Site(Site.class.toString() + n,
					siteTypes.get(i % siteTypes.size()), (i * n) % 90,
					(i * n) % 180, i * n * 100, "Comment " + i);
			sites.add(s);
			em.persist(s);
		});

	}

	/**
	 * Generates DB type data, eg Site_types, core_types, etc.
	 * 
	 * @param em
	 * @throws ReflectiveOperationException
	 */
	private void generateTypeData(EntityManager em)
			throws ReflectiveOperationException {

		int n = 20;
		siteTypes = createAndPersistType(em, SiteType.class, n);
		coreTypes = createAndPersistType(em, CoreType.class, n);
		methodTypes = createAndPersistType(em, MethodType.class, n);

	}

	/**
	 * @param em
	 * @param n
	 * @throws ReflectiveOperationException
	 */
	private <T> List<T> createAndPersistType(EntityManager em, Class<T> clazz,
			int n) throws ReflectiveOperationException {
		List<T> types = createTypes(clazz, n);
		types.stream().forEach(e -> em.persist(e));
		return types;
	}

	/**
	 * @param type
	 * @param rows
	 * @return
	 * @throws ReflectiveOperationException
	 */
	private <T> List<T> createTypes(Class<T> type, int rows)
			throws ReflectiveOperationException {

		List<T> result = new ArrayList<T>();

		Constructor<T> constructor = type.getConstructor(String.class);

		for (int i = 0; i < rows; i++) {
			result.add(constructor.newInstance(type.toString() + i));
		}

		return result;
	}

}
