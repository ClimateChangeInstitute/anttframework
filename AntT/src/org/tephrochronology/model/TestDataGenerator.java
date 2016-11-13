/**
 * 
 */
package org.tephrochronology.model;

import static org.tephrochronology.DBProperties.setupProperties;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Generates sample data for the database to use with testing.
 * 
 * @author Mark Royer
 *
 */
public class TestDataGenerator {

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

			em.getTransaction().rollback(); // For now

			em.getTransaction().commit();

		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
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
		List<SiteType> siteTypes = createAndPersistType(em, SiteType.class, n);
		List<CoreType> coreTypes = createAndPersistType(em, CoreType.class, n);
		List<MethodType> methodTypes = createAndPersistType(em, MethodType.class, n);

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
