/**
 * 
 */
package org.tephrochronology.model;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Mark Royer
 *
 */
public class OutcropSample extends Sample {

	/**
	 * Required by outcrop samples
	 */
	private int volcanoNumber;

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
	 */
	public OutcropSample(String sampleID, String longName, String sampledBy,
			LocalDate collectionDate, String comments, Site site,
			Instrument instrument, List<Ref> refs, List<Image> images,
			int volcanoNumber) {
		super(sampleID, longName, sampledBy, collectionDate, comments, site,
				instrument, refs, images);
		this.volcanoNumber = volcanoNumber;
	}

	public int getVolcanoNumber() {
		return volcanoNumber;
	}

	public void setVolcanoNumber(int volcanoNumber) {
		this.volcanoNumber = volcanoNumber;
	}
	
}
