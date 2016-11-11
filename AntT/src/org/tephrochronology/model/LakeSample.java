/**
 * 
 */
package org.tephrochronology.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "lake_samples")
public class LakeSample extends AquaticSample {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LakeSample() {
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
	 * @param volcanoNumber
	 * @param coreType
	 * @param age
	 * @param coreLength
	 * @param samplingDate
	 * @param depth
	 * @param top
	 * @param thickness
	 */
	public LakeSample(String sampleID, String longName, String sampledBy,
			LocalDate collectionDate, String comments, Site site,
			Instrument instrument, List<Ref> refs, List<Image> images,
			Integer volcanoNumber, CoreType coreType, String age,
			float coreLength, LocalDate samplingDate, float depth, float top,
			float thickness) {
		super(sampleID, longName, sampledBy, collectionDate, comments, site,
				instrument, refs, images, volcanoNumber, coreType, age,
				coreLength, samplingDate, depth, top, thickness);
	}

}
