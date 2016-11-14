/**
 * 
 */
package org.tephrochronology.model;

import static java.util.stream.IntStream.range;
import static org.tephrochronology.DBProperties.setupProperties;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
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

	List<Volcano> volcanoes;

	List<SiteType> siteTypes;

	List<CoreType> coreTypes;

	List<MethodType> methodTypes;

	List<Site> sites;

	List<Instrument> instruments;

	List<Ref> refs;

	List<Image> images;

	List<IceCoreSample> iceCoreSamples;

	List<BIASample> biaSamples;

	List<GrainSize> grainSizes;

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

			volcanoes = em.createQuery(
					"SELECT v FROM Volcano v ORDER BY v.volcanoNumber",
					Volcano.class).getResultList();

			generateTypeData(em);

			generateSiteData(em);

			generateInstrumentData(em);

			generateReferenceData(em);

			generateImageData(em);

			generateIceCoreSampleData(em);

			generateBIASampleData(em);

			generateAquaticData(em);

			generateOutcropData(em);

			generateGrainSizeData(em);

			// em.getTransaction().rollback(); // For now

			em.getTransaction().commit();

		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
	}

	private void generateGrainSizeData(EntityManager em) {

		// TODO Finish
		// int n = 1000;
		// grainSizes = new ArrayList<>();
		//
		// range(0, n).forEach(i -> {
		// int startIndex = i % refs.size();
		// int endIndex = (i + 10) % refs.size();
		// if (endIndex < startIndex) {
		// int tmp = startIndex;
		// startIndex = endIndex;
		// endIndex = tmp;
		// }
		//
		// GrainSize gs = new GrainSize(GrainSize.class.toString() + i,
		// instruments.get(i % instruments.size()), "grain size " + i,
		// "range " + i, LocalDate.now(),
		// refs.subList(startIndex, endIndex));
		// grainSizes.add(gs);
		// em.persist(gs);
		// });

	}

	private void generateOutcropData(EntityManager em) {
		// TODO Auto-generated method stub

	}

	private void generateAquaticData(EntityManager em) {
		// TODO Auto-generated method stub

	}

	private void generateBIASampleData(EntityManager em) {

		int n = 100;
		biaSamples = new ArrayList<>();

		Class<BIASample> c = BIASample.class;
		range(0, n).forEach(i -> {
			BIASample ref = new BIASample(c.getSimpleName() + i,
					c.getName() + i, "first last" + i, LocalDate.now(),
					"comment " + i, sites.get(i % sites.size()),
					instruments.get(i % instruments.size()), getRefs(i),
					getImages(i), volcanoes.get(i % volcanoes.size()),
					"deep " + i, "sample description " + i, "sample media " + i,
					"unit " + i, i, "trend " + i);
			biaSamples.add(ref);
			em.persist(ref);
		});

	}

	private void generateIceCoreSampleData(EntityManager em) {

		int n = 100;
		iceCoreSamples = new ArrayList<>();

		Class<IceCoreSample> c = IceCoreSample.class;
		range(0, n).forEach(i -> {
			IceCoreSample ics = new IceCoreSample(c.getSimpleName() + i,
					c.getName() + i, "first last" + i, LocalDate.now(),
					"comment " + i, sites.get(i % sites.size()),
					instruments.get(i % instruments.size()), getRefs(i),
					getImages(i), volcanoes.get(i % volcanoes.size()),
					"drilled by " + i, LocalDate.now(), i, i * 10, i * 100,
					"0 - " + i, 0, i, 2016 - i, 1900 - i);
			iceCoreSamples.add(ics);
			em.persist(ics);
		});

	}

	/**
	 * @param i
	 */
	private List<Ref> getRefs(int i) {
		int startIndex = i % refs.size();
		int endIndex = (i + 10) % refs.size();
		if (endIndex < startIndex) {
			int tmp = startIndex;
			startIndex = endIndex;
			endIndex = tmp;
		}
		return refs.subList(startIndex, endIndex);
	}

	/**
	 * @param i
	 */
	private List<Image> getImages(int i) {
		int startIndex = i % images.size();
		int endIndex = (i + 10) % images.size();
		if (endIndex < startIndex) {
			int tmp = startIndex;
			startIndex = endIndex;
			endIndex = tmp;
		}
		return images.subList(startIndex, endIndex);
	}

	private void generateImageData(EntityManager em) {

		int n = 1000;
		images = new ArrayList<>();

		System.out.print("Generating image data (This may take a moment).");
		range(0, n).forEach(i -> {
			System.out.print(".");
			int w = 300;
			int h = 300;
			BufferedImage bi = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) bi.getGraphics();
			g.setColor(Color.red);
			g.fillRect(0, 0, w, h);
			g.setColor(Color.white);
			g.setFont(g.getFont().deriveFont(40f));
			g.drawString("image " + i, 0, h / 2);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				ImageIO.write(bi, "png", out);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Couldn't create image.");
			}

			Image img = new Image(0, "Test image comment " + i,
					out.toByteArray(), null);
			images.add(img);
			em.persist(img);
		});
		System.out.println();

	}

	private void generateReferenceData(EntityManager em) {

		int n = 1000;
		refs = new ArrayList<>();

		range(0, n).forEach(i -> {
			Ref ref = new Ref(Ref.class.getSimpleName() + i, null, null);
			refs.add(ref);
			em.persist(ref);
		});

	}

	private void generateInstrumentData(EntityManager em) {

		int n = 15;
		instruments = new ArrayList<>();

		range(0, n).forEach(i -> {
			Instrument inst = new Instrument(
					Instrument.class.getSimpleName() + i,
					Instrument.class.toString() + i + " long name ",
					"Location " + i, "Comment " + i);
			instruments.add(inst);
			em.persist(inst);
		});

	}

	private void generateSiteData(EntityManager em) {

		int n = 50;
		sites = new ArrayList<>();

		range(0, n).forEach(i -> {
			Site s = new Site(Site.class.getSimpleName() + i,
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
			result.add(constructor.newInstance(type.getSimpleName() + i));
		}

		return result;
	}

}
