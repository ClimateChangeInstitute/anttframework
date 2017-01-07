/**
 * 
 */
package org.tephrochronology.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "outcrop_samples")
@DiscriminatorValue(value = "O")
public class OutcropSample extends Sample {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Required by outcrop samples
	 */
	@JoinColumn(name = "volcano_number", nullable = false)
	private Volcano volcano;

	public OutcropSample() {
	}

	/**
	 * @param sampleID
	 * @param longName
	 * @param sampledBy
	 * @param collectionDate
	 * @param comments
	 * @param category
	 * @param instrument
	 * @param refs
	 * @param images
	 * @param volcano
	 *            Not null
	 */
	public OutcropSample(String sampleID, String longName, String sampledBy,
			LocalDate collectionDate, String comments, Category category,
			Instrument instrument, List<Ref> refs, List<Image> images,
			Volcano volcano) {
		super(sampleID, longName, sampledBy, collectionDate, comments, category,
				instrument, refs, images);
		this.volcano = volcano;
	}

	public Volcano getVolcano() {
		return volcano;
	}

	public void setVolcano(Volcano volcano) {
		this.volcano = volcano;
	}

}
