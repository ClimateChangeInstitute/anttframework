/**
 * 
 */
package org.tephrochronology.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;

/**
 * This class represents both lake and marine subtypes.
 * 
 * @author Mark Royer
 *
 */
@MappedSuperclass
public abstract class AquaticSample extends Sample {

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
	@Column(name = "depth_m")
	private String depth;

	/**
	 * Centimeters
	 */
	@Column(name = "thickness_cm")
	private String thickness;

	public AquaticSample() {
	}

	public AquaticSample(String sampleID, String secondaryID, String sampledBy,
			String collectionDate, String comments, Category category,
			Instrument instrument, List<Ref> refs, List<Image> images,
			Volcano volcano, String depth, String thickness) {
		super(sampleID, secondaryID, sampledBy, collectionDate, comments, category,
				instrument, refs, images);
		this.volcano = volcano;
		this.depth = depth;
		this.thickness = thickness;
	}

	public Volcano getVolcano() {
		return volcano;
	}

	public void setVolcano(Volcano volcano) {
		this.volcano = volcano;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public String getThickness() {
		return thickness;
	}

	public void setThickness(String thickness) {
		this.thickness = thickness;
	}

}
