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
@Table(name = "lake_samples")
@DiscriminatorValue(value = "L")
public class LakeSample extends AquaticSample {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LakeSample() {
	}

	public LakeSample(String sampleID, String secondaryID, String sampledBy,
			String collectionDate, String comments, Category category,
			Instrument instrument, List<Ref> refs, List<Image> images,
			Volcano volcano, String depth, String thickness) {
		super(sampleID, secondaryID, sampledBy, collectionDate, comments, category,
				instrument, refs, images, volcano, depth, thickness);
	}

}
