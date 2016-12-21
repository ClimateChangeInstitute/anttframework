/**
 * 
 */
package org.tephrochronology.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "marine_samples")
@DiscriminatorValue(value = "Marine")
public class MarineSample extends AquaticSample {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MarineSample() {
	}

	/**
	 * @param sampleID
	 * @param longName
	 * @param sampledBy
	 * @param collectionDate
	 * @param comments
	 * @param site
	 * @param instrument
	 * @param refs
	 * @param images
	 * @param volcano
	 * @param coreType
	 * @param age
	 * @param coreLength
	 * @param samplingDate
	 * @param depth
	 * @param top
	 * @param thickness
	 */
	public MarineSample(String sampleID, String longName, String sampledBy,
			LocalDate collectionDate, String comments, Site site,
			Instrument instrument, List<Ref> refs, List<Image> images,
			Volcano volcano, CoreType coreType, String age, float coreLength,
			LocalDate samplingDate, float depth, float top, float thickness) {
		super(sampleID, longName, sampledBy, collectionDate, comments, site,
				instrument, refs, images, volcano, coreType, age, coreLength,
				samplingDate, depth, top, thickness);
	}

}
