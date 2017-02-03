/**
 * 
 */
package org.tephrochronology.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "icecore_samples")
@DiscriminatorValue(value = "I")
public class IceCoreSample extends Sample {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Not required
	 */
	@JoinColumn(name = "volcano_number", nullable = true)
	private Volcano volcano;

	/**
	 * Meters
	 */
	@Column(name = "topdepth_m")
	private float topDepth;

	/**
	 * Meters
	 */
	@Column(name = "bottomdepth_m")
	private float bottomDepth;

	/**
	 * BP
	 */
	@Column(name = "topyear_bp")
	private float topYear;

	/**
	 * BP
	 */
	@Column(name = "bottomyear_bp")
	private float bottomYear;

	public IceCoreSample() {
	}

	public IceCoreSample(String sampleID, String longName, String sampledBy,
			String collectionDate, String comments, Category category,
			Instrument instrument, List<Ref> refs, List<Image> images,
			Volcano volcano, float topDepth, float bottomDepth, float topYear,
			float bottomYear) {
		super(sampleID, longName, sampledBy, collectionDate, comments, category,
				instrument, refs, images);
		this.volcano = volcano;
		this.topDepth = topDepth;
		this.bottomDepth = bottomDepth;
		this.topYear = topYear;
		this.bottomYear = bottomYear;
	}

	public Volcano getVolcano() {
		return volcano;
	}

	public void setVolcano(Volcano volcano) {
		this.volcano = volcano;
	}

	public float getTopDepth() {
		return topDepth;
	}

	public void setTopDepth(float topDepth) {
		this.topDepth = topDepth;
	}

	public float getBottomDepth() {
		return bottomDepth;
	}

	public void setBottomDepth(float bottomDepth) {
		this.bottomDepth = bottomDepth;
	}

	public float getTopYear() {
		return topYear;
	}

	public void setTopYear(float topYear) {
		this.topYear = topYear;
	}

	public float getBottomYear() {
		return bottomYear;
	}

	public void setBottomYear(float bottomYear) {
		this.bottomYear = bottomYear;
	}

}
