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
 * Blue Ice Area sample.
 * 
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "bia_samples")
@DiscriminatorValue(value = "B")
public class BIASample extends Sample {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Not required
	 */
	@JoinColumn(name = "volcano_number", nullable = true)
	private Volcano volcano;

	public BIASample() {
	}

	public BIASample(String sampleID, String longName, String sampledBy,
			LocalDate collectionDate, String comments, Site site,
			Instrument instrument, List<Ref> refs, List<Image> images,
			Volcano volcano) {
		super(sampleID, longName, sampledBy, collectionDate, comments, site,
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
