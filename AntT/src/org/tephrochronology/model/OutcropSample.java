/**
 * 
 */
package org.tephrochronology.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "outcrop_samples")
@DiscriminatorValue(value="O")
public class OutcropSample extends Sample {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Required by outcrop samples
	 */
	@Column(name = "volcano_number")
	private int volcanoNumber;

	public OutcropSample() {
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
