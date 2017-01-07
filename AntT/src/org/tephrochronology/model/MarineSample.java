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
@DiscriminatorValue(value = "M")
public class MarineSample extends AquaticSample {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MarineSample() {
	}

	public MarineSample(String sampleID, String longName, String sampledBy,
			LocalDate collectionDate, String comments, Site site,
			Instrument instrument, List<Ref> refs, List<Image> images,
			Volcano volcano, float depth, float thickness) {
		super(sampleID, longName, sampledBy, collectionDate, comments, site,
				instrument, refs, images, volcano, depth, thickness);
	}

}
