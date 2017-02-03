/**
 * 
 */
package org.tephrochronology.model;

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
			String collectionDate, String comments, Category category,
			Instrument instrument, List<Ref> refs, List<Image> images,
			Volcano volcano, float depth, float thickness) {
		super(sampleID, longName, sampledBy, collectionDate, comments, category,
				instrument, refs, images, volcano, depth, thickness);
	}

}
