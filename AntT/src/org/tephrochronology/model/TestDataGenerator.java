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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	List<LakeSample> lakeSamples;

	List<MarineSample> marineSamples;

	List<OutcropSample> outcropSamples;

	List<GrainSize> grainSizes;

	List<Element> elements;

	List<MMElement> mmElements;

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

			elements = em.createQuery("SELECT e FROM Element e ORDER BY e.name",
					Element.class).getResultList();

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

			generateMMElementData(em);

			em.getTransaction().commit();

			System.out.println("Done generating test data.");

		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
	}

	private void generateMMElementData(EntityManager em) {
		System.out.printf("Generating %s data.\n",
				MMElement.class.getSimpleName());

		int n = 100;
		mmElements = new ArrayList<>();

		range(0, n).forEach(i -> {
			Map<Element, MMElementData> data = new HashMap<>();
			MMElement el = new MMElement(MMElement.class.getSimpleName() + i,
					outcropSamples.get(i % outcropSamples.size()),
					"Comment " + i, methodTypes.get(i % methodTypes.size()),
					instruments.get(i % instruments.size()),
					LocalDate.now(), "Mark", 5, 3f, 2f, "instrument settings for " + i,
					1f, -2f, 1f, data);
			range(0, i % elements.size()).forEach(j ->{
				Element elem = elements.get(j % elements.size());
				data.put(elem, new MMElementData(el, elem, j*10f, j * 2f, j*1f, "ppb"));
			});
			
			mmElements.add(el);
			em.persist(el);
		});

	}

	private void generateGrainSizeData(EntityManager em) {
		System.out.printf("Generating %s data.\n",
				GrainSize.class.getSimpleName());

		grainSizes = new ArrayList<>();

		range(0, outcropSamples.size()).forEach(i -> {
			range(0, instruments.size()).forEach(j -> {
				GrainSize gs = new GrainSize(
						outcropSamples.get(i % outcropSamples.size()),
						instruments.get(j % instruments.size()),
						GrainSize.class.getSimpleName() + i + " " + j,
						"range " + i, LocalDate.now(), getRefs(i));
				grainSizes.add(gs);
				em.persist(gs);
			});
		});

	}

	private void generateOutcropData(EntityManager em) {
		System.out.printf("Generating %s data.\n",
				OutcropSample.class.getSimpleName());

		int n = 100;
		outcropSamples = new ArrayList<>();

		Class<OutcropSample> c = OutcropSample.class;
		range(0, n).forEach(i -> {
			OutcropSample ls = new OutcropSample(c.getSimpleName() + i,
					c.getName() + i, "first last" + i, LocalDate.now(),
					"comment " + i, sites.get(i % sites.size()),
					instruments.get(i % instruments.size()), getRefs(i),
					getImages(i), volcanoes.get(i % volcanoes.size()));
			outcropSamples.add(ls);
			em.persist(ls);
		});

	}

	private void generateAquaticData(EntityManager em) {
		System.out.printf("Generating %s data.\n",
				AquaticSample.class.getSimpleName());

		int n = 100;
		lakeSamples = new ArrayList<>();
		marineSamples = new ArrayList<>();

		Class<LakeSample> lakeClazz = LakeSample.class;
		range(0, n).forEach(i -> {
			LakeSample ls = new LakeSample(lakeClazz.getSimpleName() + i,
					lakeClazz.getName() + i, "first last" + i, LocalDate.now(),
					"comment " + i, sites.get(i % sites.size()),
					instruments.get(i % instruments.size()), getRefs(i),
					getImages(i), volcanoes.get(i % volcanoes.size()),
					coreTypes.get(i % coreTypes.size()), "age " + i, i,
					LocalDate.now(), i * 10f, 0, i);
			lakeSamples.add(ls);
			em.persist(ls);
		});

		Class<MarineSample> marineClazz = MarineSample.class;
		range(0, n).forEach(i -> {
			MarineSample ms = new MarineSample(marineClazz.getSimpleName() + i,
					marineClazz.getName() + i, "first last" + i,
					LocalDate.now(), "comment " + i,
					sites.get(i % sites.size()),
					instruments.get(i % instruments.size()), getRefs(i),
					getImages(i), volcanoes.get(i % volcanoes.size()),
					coreTypes.get(i % coreTypes.size()), "age " + i, i,
					LocalDate.now(), i * 10f, 0, i);
			marineSamples.add(ms);
			em.persist(ms);
		});
	}

	private void generateBIASampleData(EntityManager em) {
		System.out.printf("Generating %s data.\n",
				BIASample.class.getSimpleName());

		int n = 100;
		biaSamples = new ArrayList<>();

		Class<BIASample> c = BIASample.class;
		range(0, n).forEach(i -> {
			BIASample bias = new BIASample(c.getSimpleName() + i,
					c.getName() + i, "first last" + i, LocalDate.now(),
					"comment " + i, sites.get(i % sites.size()),
					instruments.get(i % instruments.size()), getRefs(i),
					getImages(i), volcanoes.get(i % volcanoes.size()),
					"deep " + i, "sample description " + i, "sample media " + i,
					"unit " + i, i, "trend " + i);
			biaSamples.add(bias);
			em.persist(bias);
		});

	}

	private void generateIceCoreSampleData(EntityManager em) {
		System.out.printf("Generating %s data.\n",
				IceCoreSample.class.getSimpleName());

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
		System.out.printf("Generating %s data.\n", Ref.class.getSimpleName());

		int n = 1000;
		refs = new ArrayList<>();

		range(0, n).forEach(i -> {
			Ref ref = new Ref(Ref.class.getSimpleName() + i, null, null);
			refs.add(ref);
			em.persist(ref);
		});

	}

	private void generateInstrumentData(EntityManager em) {
		System.out.printf("Generating %s data.\n",
				Instrument.class.getSimpleName());

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
		System.out.printf("Generating %s data.\n", Site.class.getSimpleName());

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
		System.out.printf("Generating %s data.\n", clazz.getSimpleName());
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
