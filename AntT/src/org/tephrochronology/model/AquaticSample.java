/**
 * 
 */
package org.tephrochronology.model;

import java.time.LocalDate;
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

	@JoinColumn(name = "core_type")
	private CoreType coreType;

	@Column(name = "age")
	private String age;

	/**
	 * Meters
	 */
	@Column(name = "core_length_m")
	private float coreLength;

	@Column(name = "sampling_date")
	private LocalDate samplingDate;

	/**
	 * Meters
	 */
	@Column(name = "depth_m")
	private float depth;

	/**
	 * Meters
	 */
	@Column(name = "top_m")
	private float top;

	/**
	 * Centimeters
	 */
	@Column(name = "thickness_cm")
	private float thickness;

	public AquaticSample() {
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
	 * @param volcano
	 * @param coreType
	 * @param age
	 * @param coreLength
	 * @param samplingDate
	 * @param depth
	 * @param top
	 * @param thickness
	 */
	public AquaticSample(String sampleID, String longName, String sampledBy,
			LocalDate collectionDate, String comments, Site site,
			Instrument instrument, List<Ref> refs, List<Image> images,
			Volcano volcano, CoreType coreType, String age, float coreLength,
			LocalDate samplingDate, float depth, float top, float thickness) {
		super(sampleID, longName, sampledBy, collectionDate, comments, site,
				instrument, refs, images);
		this.volcano = volcano;
		this.coreType = coreType;
		this.age = age;
		this.coreLength = coreLength;
		this.samplingDate = samplingDate;
		this.depth = depth;
		this.top = top;
		this.thickness = thickness;
	}

	public Volcano getVolcano() {
		return volcano;
	}

	public void setVolcano(Volcano volcano) {
		this.volcano = volcano;
	}

	public CoreType getCoreType() {
		return coreType;
	}

	public void setCoreType(CoreType coreType) {
		this.coreType = coreType;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public float getCoreLength() {
		return coreLength;
	}

	public void setCoreLength(float coreLength) {
		this.coreLength = coreLength;
	}

	public LocalDate getSamplingDate() {
		return samplingDate;
	}

	public void setSamplingDate(LocalDate samplingDate) {
		this.samplingDate = samplingDate;
	}

	public float getDepth() {
		return depth;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	public float getTop() {
		return top;
	}

	public void setTop(float top) {
		this.top = top;
	}

	public float getThickness() {
		return thickness;
	}

	public void setThickness(float thickness) {
		this.thickness = thickness;
	}

}
